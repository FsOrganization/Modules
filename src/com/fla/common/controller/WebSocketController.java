package com.fla.common.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.socket.TextMessage;

import com.fla.common.base.SuperController;
import com.fla.common.entity.ScanneInfo;
import com.fla.common.entity.SystemUser;
import com.fla.common.service.interfaces.ScanneServiceInterface;
import com.fla.common.webSocket.handler.SystemWebSocketHandler;

@Controller
@ServerEndpoint(value = "/webSocket/{userName}")
//@RequestMapping("/webSocket")
public class WebSocketController extends SuperController{
	
	private static final long serialVersionUID = -101754662325575746L;
	
	@Autowired
	private ScanneServiceInterface scanneService;
	@Bean
    public SystemWebSocketHandler systemWebSocketHandler() {
        return new SystemWebSocketHandler();
    }
	
	private static final ArrayList<Session> sessions = new ArrayList<Session>();
	private static final Map<String,Session> userSessions = Collections.synchronizedMap(new HashMap<String, Session>());
	private static final Map<String,SystemUser> systemUsers= Collections.synchronizedMap(new HashMap<String, SystemUser>());
    private static final long MAX_IDLE_TIME_OUT = 12 * 60 * 60 * 1000;
	
	@OnMessage
	public void onMessage(String message, Session session) throws IOException,InterruptedException {
		TextMessage tm = new TextMessage(message,false);
//		System.out.println(session.getUserPrincipal().getName());
//		System.out.println(session.getUserProperties());
		String[] logicMessage = message.split(":");
		String loginName = logicMessage[0];
		SystemUser user = systemUsers.get(loginName);
		Map<String, String> params = new HashMap<String, String>(1);
		params.put("shopCode", user.getServiceShopCode());
		ScanneInfo scanne = getScannerByShopCode(params);
		
//		systemWebSocketHandler().sendMessageToUsers(tm,se);
		if (scanne != null) {
			Session se = userSessions.get(scanne.getLoginName());
			if (se != null && se.isOpen()) {
				se.getBasicRemote().sendText(message);
			} else {
				session.getBasicRemote().sendText("请先登陆签字版.");
			}
		} else {
			session.getBasicRemote().sendText("未配置签字版.");
		}
//		for (Session s : sessions) {
//			if (s ==session ) {
////				systemWebSocketHandler().sendMessageToUsers(tm,s);
//			} else {
////				systemWebSocketHandler().sendMessageToUsers(tm,s);
//			}
//		}
//		for (String key : userSessions.keySet()) {
//			userSessions.get(key);
//		}
//		systemWebSocketHandler().handleMessage(session, message);
//		session.getBasicRemote().sendText(message);
	}

	@OnOpen
	public void onOpen(@PathParam(value = "userName") String userName,Session session) {
		if (scanneService == null) {
			WebApplicationContext  context = ContextLoader.getCurrentWebApplicationContext();
			scanneService = (ScanneServiceInterface) context.getBean("scanneService");
		}
		
		session.setMaxIdleTimeout(MAX_IDLE_TIME_OUT);
		System.out.println("userName:"+userName);
		sessions.add(session);
		userSessions.put(userName, session);
		handleSystemUser(userName);
		System.out.println("Client connected");
	}
	
	private void handleSystemUser(String loginName){
		Map<String, String> params = new HashMap<String, String>(1);
		params.put("loginName", loginName);
		SystemUser systemUser = this.getSystemUserByLoginName(params);
		systemUsers.put(loginName, systemUser);
	}

	@OnClose
	public void onClose(Session session, @PathParam(value = "userName") String userName) {
		sessions.remove(session);
		userSessions.remove(userName);
		System.out.println("Connection closed");
	}
	
	public ScanneInfo getScannerByShopCode(Map<String, String> params){
		try 
		{
			return scanneService.getScanneByShopCode(params);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public SystemUser getSystemUserByLoginName(Map<String, String> params){
		try 
		{
			return scanneService.getSystemUserByLoginName(params);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}

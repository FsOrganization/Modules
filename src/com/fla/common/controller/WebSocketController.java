package com.fla.common.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.fla.common.webSocket.handler.SystemWebSocketHandler;

@Controller
@ServerEndpoint(value = "/webSocket")
//@RequestMapping("/webSocket")
public class WebSocketController {
	
	@Bean
    public SystemWebSocketHandler systemWebSocketHandler() {
        return new SystemWebSocketHandler();
    }
	
	private static final ArrayList<Session> sessions = new ArrayList<Session>();
	
	@OnMessage
	public void onMessage(String message, Session session) throws IOException,InterruptedException {
		TextMessage tm = new TextMessage(message,false);
		System.out.println(tm);
		systemWebSocketHandler().sendMessageToUsers(tm,sessions);
//		systemWebSocketHandler().handleMessage(session, message);
//		session.getBasicRemote().sendText(message);
	}

	@OnOpen
	public void onOpen(Session session, @PathParam(value = "userName") String userName) {
		sessions.add(session);
		System.out.println("Client connected");
	}
//
	@OnClose
	public void onClose(Session session, @PathParam(value = "userName") String userName) {
		sessions.remove(session);
		System.out.println("Connection closed");
	}
	
}

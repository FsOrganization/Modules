package com.fla.common.webSocket.handler;

import java.io.IOException;
import java.util.ArrayList;

import javax.websocket.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

public class SystemWebSocketHandler implements WebSocketHandler {

	
	
	public SystemWebSocketHandler() {
		super();
	}

	private static final Logger logger;
	 
    private static final ArrayList<Session> users;
 
    static {
        users = new ArrayList<Session>();
        logger = LoggerFactory.getLogger(SystemWebSocketHandler.class);
    }

	@Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		logger.debug("connect to the websocket success......");
//        users.add(session);
    }

	@Override
	public void handleMessage(WebSocketSession session,WebSocketMessage<?> message) throws Exception {
		TextMessage tm = new TextMessage(message.toString(),false);
//		 sendMessageToUsers(tm);
	}

	@Override
	public void handleTransportError(WebSocketSession session,
			Throwable exception) throws Exception {
		if (session.isOpen()) {
			session.close();
		}
		users.remove(session);
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session,
			CloseStatus closeStatus) throws Exception {
		users.remove(session);
	}

	@Override
	public boolean supportsPartialMessages() {
		return false;
	}

	 /**
     * 给所有在线用户发送消息
     *
     * @param message
     */
	public void sendMessageToUsers(TextMessage message,ArrayList<Session> sessions) {
		for (Session s : sessions) {
			try 
			{
				if (s.isOpen() && s != this) {
					String msg = message.getPayload();
					s.getBasicRemote().sendText(msg);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
 
    /**
     * 给某个用户发送消息
     *
     * @param userName
     * @param message
     */
//	public void sendMessageToUser(String userName, TextMessage message) {
//		for (WebSocketSession user : users) {
//			if (user.getAttributes().equals(userName)) {
//				try {
//					if (user.isOpen()) {
//						user.sendMessage(message);
//					}
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//				break;
//			}
//		}
//	}
	
	public void sendMessageToUser(String userName, TextMessage message, WebSocketSession user) {
		try 
		{
			if (user.isOpen()) {
				user.sendMessage(message);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
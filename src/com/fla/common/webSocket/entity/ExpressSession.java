package com.fla.common.webSocket.entity;

import javax.websocket.Session;

public interface ExpressSession extends Session {
	public SocketUser getSocketUser();
	public void setSocketUser(SocketUser su);

}

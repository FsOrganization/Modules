package org.jeewx.api.wxaccount.model;

import java.net.URLEncoder;

public class WxQrcode {

	private String ticket;
	
	private String expireSeconds;
	
	private String url;

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}


	public String getExpireSeconds() {
		return expireSeconds;
	}

	public void setExpireSeconds(String expireSeconds) {
		this.expireSeconds = expireSeconds;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}

package com.fla.common.sms.entity;

import java.io.Serializable;
import java.util.Date;

public class SSmsRecord implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3150713588858406028L;
	
	public SSmsRecord() {
	}
	
	private Long senderId;
	private String userName;
	private Date sendTime;
	private String sendContent;
	private String mobiles;
	private Integer receiverCount;
	private String receiverIds;
	private String responseMessage;
	private String areaCode;
	private String shopCode;
	private String operator;
	
	
	public Long getSenderId() {
		return senderId;
	}
	public void setSenderId(Long senderId) {
		this.senderId = senderId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	public String getSendContent() {
		return sendContent;
	}
	public void setSendContent(String sendContent) {
		this.sendContent = sendContent;
	}
	public String getMobiles() {
		return mobiles;
	}
	public void setMobiles(String mobiles) {
		this.mobiles = mobiles;
	}
	public Integer getReceiverCount() {
		return receiverCount;
	}
	public void setReceiverCount(Integer receiverCount) {
		this.receiverCount = receiverCount;
	}
	public String getReceiverIds() {
		return receiverIds;
	}
	public void setReceiverIds(String receiverIds) {
		this.receiverIds = receiverIds;
	}
	public String getResponseMessage() {
		return responseMessage;
	}
	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getShopCode() {
		return shopCode;
	}
	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}

}

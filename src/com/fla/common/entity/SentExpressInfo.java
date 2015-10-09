/**
 * 
 */
package com.fla.common.entity;

import java.io.Serializable;

/**
 * @author Administrator
 *
 */
public class SentExpressInfo implements Serializable{

	private static final long serialVersionUID = 568085310411718606L;
	
	private Integer id;
    private String logistics;
    private String code;
    private String recipientName;
    private String phoneNumber;
    private String landlineNumber;
    private String senderName;
    private String senderNumber;
    private String senderLandlineNumber;
    private Integer expressServiceId;
    private String address;
    private String destination;
    private String operaTime;
    private String areaCode;
    private String serviceShopCode;
    private String operator;
    private String expressLocation;
    private String weight;
    private String dimensions;
    private String remark;
    private String res;
    private boolean type;
    private float price;
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getLogistics() {
		return logistics;
	}
	public void setLogistics(String logistics) {
		this.logistics = logistics;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getRecipientName() {
		return recipientName;
	}
	public void setRecipientName(String recipientName) {
		this.recipientName = recipientName;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getLandlineNumber() {
		return landlineNumber;
	}
	public void setLandlineNumber(String landlineNumber) {
		this.landlineNumber = landlineNumber;
	}
	public String getSenderName() {
		return senderName;
	}
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
	public String getSenderNumber() {
		return senderNumber;
	}
	public void setSenderNumber(String senderNumber) {
		this.senderNumber = senderNumber;
	}
	public String getSenderLandlineNumber() {
		return senderLandlineNumber;
	}
	public void setSenderLandlineNumber(String senderLandlineNumber) {
		this.senderLandlineNumber = senderLandlineNumber;
	}
	public Integer getExpressServiceId() {
		return expressServiceId;
	}
	public void setExpressServiceId(Integer expressServiceId) {
		this.expressServiceId = expressServiceId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getOperaTime() {
		return operaTime;
	}
	public void setOperaTime(String operaTime) {
		this.operaTime = operaTime;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getServiceShopCode() {
		return serviceShopCode;
	}
	public void setServiceShopCode(String serviceShopCode) {
		this.serviceShopCode = serviceShopCode;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getExpressLocation() {
		return expressLocation;
	}
	public void setExpressLocation(String expressLocation) {
		this.expressLocation = expressLocation;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getDimensions() {
		return dimensions;
	}
	public void setDimensions(String dimensions) {
		this.dimensions = dimensions;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getRes() {
		return res;
	}
	public void setRes(String res) {
		this.res = res;
	}
	public boolean isType() {
		return type;
	}
	public void setType(boolean type) {
		this.type = type;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float  price) {
		this.price = price;
	}

	
}

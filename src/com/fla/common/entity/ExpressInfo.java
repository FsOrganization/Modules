/**
 * 
 */
package com.fla.common.entity;

import java.io.Serializable;

import com.fla.common.base.PrimaryKey;

public class ExpressInfo implements Serializable{
	
	private static final long serialVersionUID = 2555443494570955651L;
	
	@PrimaryKey
	private Integer id;
	private String logistics;
	private String code;
	private String recipientName;
	private String phoneNumber;
	private String landlineNumber;
	private Integer expressServiceId;
	private String expressServiceName;
	private String address;
	private String serviceShopCode;
	private String areaCode;
	private String remark;
	private String operaTime;
	private String operator;
	private String expressLocation;
	private String inBatchNumber;
	private String outBatchNumber;
	private boolean type;
	private String tempOperaTime;
	
	public ExpressInfo() {
	}

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

	public Integer getExpressServiceId() {
		return expressServiceId;
	}

	public void setExpressServiceId(Integer expressServiceId) {
		this.expressServiceId = expressServiceId;
	}

	public String getExpressServiceName() {
		return expressServiceName;
	}

	public void setExpressServiceName(String expressServiceName) {
		this.expressServiceName = expressServiceName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOperaTime() {
		return operaTime;
	}

	public void setOperaTime(String operaTime) {
		this.operaTime = operaTime;
	}

	public String getServiceShopCode() {
		return serviceShopCode;
	}

	public void setServiceShopCode(String serviceShopCode) {
		this.serviceShopCode = serviceShopCode;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getInBatchNumber() {
		return inBatchNumber;
	}

	public void setInBatchNumber(String inBatchNumber) {
		this.inBatchNumber = inBatchNumber;
	}

	public String getOutBatchNumber() {
		return outBatchNumber;
	}

	public void setOutBatchNumber(String outBatchNumber) {
		this.outBatchNumber = outBatchNumber;
	}

	public String getExpressLocation() {
		return expressLocation;
	}

	public void setExpressLocation(String expressLocation) {
		this.expressLocation = expressLocation;
	}

	public boolean isType() {
		return type;
	}

	public void setType(boolean type) {
		this.type = type;
	}

	@Override
	public int hashCode() {
		return super.hashCode()+this.phoneNumber.hashCode();
	}

	@Override
	public String toString() {
		return super.toString();
	}

	public String getTempOperaTime() {
		return tempOperaTime;
	}

	public void setTempOperaTime(String tempOperaTime) {
		this.tempOperaTime = tempOperaTime;
	}
	
}

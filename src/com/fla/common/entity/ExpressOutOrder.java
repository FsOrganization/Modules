/**
 * 
 */
package com.fla.common.entity;

import java.io.Serializable;
import java.util.Date;

import com.fla.common.base.PrimaryKey;

public class ExpressOutOrder implements Serializable{
	
	private static final long serialVersionUID = -8598866549638207777L;
	
	@PrimaryKey
	private Integer id;
	private String batchNumber;
	private String areaCode;
	private String serviceShopCode;
	private String orderCode;
	private String orderPayType;
	private Date operaTime;
	private String orderFee;
	private String operator;
	private String remark;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getBatchNumber() {
		return batchNumber;
	}
	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
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
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public String getOrderPayType() {
		return orderPayType;
	}
	public void setOrderPayType(String orderPayType) {
		this.orderPayType = orderPayType;
	}
	public Date getOperaTime() {
		return operaTime;
	}
	public void setOperaTime(Date operaTime) {
		this.operaTime = operaTime;
	}

	public String getOrderFee() {
		return orderFee;
	}
	public void setOrderFee(String orderFee) {
		this.orderFee = orderFee;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}

/**
 * 
 */
package com.fla.common.entity;

import java.io.Serializable;
import java.util.Date;

import com.fla.common.base.PrimaryKey;

public class BarcodeExpress implements Serializable{
	
	private static final long serialVersionUID = -7784525068061385464L;
	@PrimaryKey
	private Integer id;
	private Integer expressId;
	private String barCode;
	private String areaCode;
	private String serviceShopCode;
	private String timestampSpe;
	private Date operaTime;
	private String operator;
	private String remark;
	private String orderCode;//transaction_id 订单号
	private String basketNumber;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getExpressId() {
		return expressId;
	}
	public void setExpressId(Integer expressId) {
		this.expressId = expressId;
	}
	public String getBarCode() {
		return barCode;
	}
	public void setBarCode(String barCode) {
		this.barCode = barCode;
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
	public String getTimestampSpe() {
		return timestampSpe;
	}
	public void setTimestampSpe(String timestampSpe) {
		this.timestampSpe = timestampSpe;
	}
	public Date getOperaTime() {
		return operaTime;
	}
	public void setOperaTime(Date operaTime) {
		this.operaTime = operaTime;
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
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public String getBasketNumber() {
		return basketNumber;
	}
	public void setBasketNumber(String basketNumber) {
		this.basketNumber = basketNumber;
	}
  
	
	
}

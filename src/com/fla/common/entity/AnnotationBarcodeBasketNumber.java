/**
 * 
 */
package com.fla.common.entity;

import java.io.Serializable;
import java.util.Date;

import com.fla.common.base.PrimaryKey;

public class AnnotationBarcodeBasketNumber implements Serializable{

	private static final long serialVersionUID = -3584971051697664018L;
	@PrimaryKey
	private int id;
	private String basketNumber;
	private String barCode;
	private String serviceShopCode;
	private Date operaTime;
	private String operator;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBasketNumber() {
		return basketNumber;
	}
	public void setBasketNumber(String basketNumber) {
		this.basketNumber = basketNumber;
	}
	public String getBarCode() {
		return barCode;
	}
	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}
	public String getServiceShopCode() {
		return serviceShopCode;
	}
	public void setServiceShopCode(String serviceShopCode) {
		this.serviceShopCode = serviceShopCode;
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
	
	
}

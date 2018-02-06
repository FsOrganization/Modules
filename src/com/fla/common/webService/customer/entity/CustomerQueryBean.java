package com.fla.common.webService.customer.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="CUSTOMER_QUERY_BEAN")
public class CustomerQueryBean {

	@XmlElement(name = "PHONE_NUMBER", required = true, nillable = false)
	private String phoneNumber;

	@XmlElement(name = "EXPRESS_TYPE", required = true, nillable = false)
	private String expressType;

	@XmlElement(name = "SHOP_CODE", required = true, nillable = false)
	private String shopCode;

	@XmlElement(name = "AREA_CODE", required = true, nillable = false)
	private String areaCode;

	@XmlElement(name = "ACCESS_CODE", required = true, nillable = false)
	private String accessCode;
	
	@XmlElement(name = "ACCESS_PASSWORD", required = true, nillable = false)
	private String accessPassword;
	
	@XmlElement(name = "ROW_SIZE", required = true, nillable = false)
	private Integer rowSize;
	
	@XmlElement(name = "PAGE_SIZE", required = true, nillable = false)
	private Integer pageSize;

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getExpressType() {
		return expressType;
	}

	public void setExpressType(String expressType) {
		this.expressType = expressType;
	}

	public String getShopCode() {
		return shopCode;
	}

	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getAccessCode() {
		return accessCode;
	}

	public void setAccessCode(String accessCode) {
		this.accessCode = accessCode;
	}

	public String getAccessPassword() {
		return accessPassword;
	}

	public void setAccessPassword(String accessPassword) {
		this.accessPassword = accessPassword;
	}

	public Integer getRowSize() {
		return rowSize;
	}

	public void setRowSize(Integer rowSize) {
		this.rowSize = rowSize;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

}

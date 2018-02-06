package com.fla.common.webService.customer.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="CUSTOMER_QUERY_BEAN")
public class CustomerBean {

	@XmlElement(name = "NAME", required = true, nillable = false)
	private String name;

	@XmlElement(name = "PHONE_NUMBER", required = true, nillable = false)
	private String phoneNumber;
	
	@XmlElement(name = "lANDLINE_NUMBER", required = true, nillable = false)
	private String landlineNumber;
	
	@XmlElement(name = "WEIXIN_ID", required = true, nillable = false)
	private String weixinId;
	
	@XmlElement(name = "IDENTITY_CARD", required = true, nillable = false)
	private String identityCard;
	
	@XmlElement(name = "AREA_CODE", required = true, nillable = false)
	private String areaCode;
	
	@XmlElement(name = "SERVICE_SHOP_CODE", required = true, nillable = false)
	private String serviceShopCode;
	
	@XmlElement(name = "SPELLING_CODE", required = true, nillable = false)
	private String spellingCode;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getWeixinId() {
		return weixinId;
	}

	public void setWeixinId(String weixinId) {
		this.weixinId = weixinId;
	}

	public String getIdentityCard() {
		return identityCard;
	}

	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard;
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

	public String getSpellingCode() {
		return spellingCode;
	}

	public void setSpellingCode(String spellingCode) {
		this.spellingCode = spellingCode;
	}
	
}

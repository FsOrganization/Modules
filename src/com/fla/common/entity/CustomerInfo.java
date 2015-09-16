/**
 * 
 */
package com.fla.common.entity;

import java.io.Serializable;

/**
 * @author Administrator
 *
 */
public class CustomerInfo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6380837878276571351L;
	
	private Integer id;
	private String name;
	private String phoneNumber;
	private String landlineNumber;
	private String weixinId;
	private String identityCard;
	private Character gender;
	private String address;
	private String areaCode;
	private String remark;
	private String initialsCode;
	private String spellingCode;
	private String shopCode;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
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
	public Character getGender() {
		return gender;
	}
	public void setGender(Character gender) {
		this.gender = gender;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getInitialsCode() {
		return initialsCode;
	}
	public void setInitialsCode(String initialsCode) {
		this.initialsCode = initialsCode;
	}
	public String getSpellingCode() {
		return spellingCode;
	}
	public void setSpellingCode(String spellingCode) {
		this.spellingCode = spellingCode;
	}
	public String getShopCode() {
		return shopCode;
	}
	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}

	
}

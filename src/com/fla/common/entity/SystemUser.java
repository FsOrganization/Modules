package com.fla.common.entity;

import java.io.Serializable;

import com.fla.common.base.PrimaryKey;

public class SystemUser implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6842795270336937771L;

	@PrimaryKey
	private Integer id ;
	private String loginName;
	private String password;
	private String areaCode;
	private String areaName;
	private String nickName;
	private String serviceShopCode;
	private String shopName;
	private String phoneNumber;
	private String type;
	private String remark;
	private String userMode;
	private String isOpenIM;
	private String createDate;
	
	
	public SystemUser() {
	}

	public static void main(String[] args) {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getServiceShopCode() {
		return serviceShopCode;
	}

	public void setServiceShopCode(String serviceShopCode) {
		this.serviceShopCode = serviceShopCode;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUserMode() {
		return userMode;
	}

	public void setUserMode(String userMode) {
		this.userMode = userMode;
	}

	public String getIsOpenIM() {
		return isOpenIM;
	}

	public void setIsOpenIM(String isOpenIM) {
		this.isOpenIM = isOpenIM;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

}

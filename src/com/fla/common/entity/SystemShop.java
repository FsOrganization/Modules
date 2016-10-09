package com.fla.common.entity;

import java.io.Serializable;

import org.springframework.stereotype.Service;

import com.fla.common.base.PrimaryKey;

public class SystemShop implements Serializable{

	private static final long serialVersionUID = -6842795270336937771L;
	
	@PrimaryKey
	private Integer id ;
	private String shopCode;
	private String name;
	private String type;
	private String areaCode;
	private String shopAddress;
	private String shopContacts;
	private String remark;
	
	
	public SystemShop() {
	}

	public static void main(String[] args) {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getShopCode() {
		return shopCode;
	}

	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public String getShopAddress() {
		return shopAddress;
	}

	public void setShopAddress(String shopAddress) {
		this.shopAddress = shopAddress;
	}

	public String getShopContacts() {
		return shopContacts;
	}

	public void setShopContacts(String shopContacts) {
		this.shopContacts = shopContacts;
	}
	

}

package com.fla.common.entity;

import java.io.Serializable;

import org.springframework.stereotype.Service;

import com.fla.common.base.PrimaryKey;

@Service
public class ExpressServiceProvider implements Serializable{

	private static final long serialVersionUID = -2012217503726900795L;
	
	@PrimaryKey
	private Integer id; 
	private String name;
	private String code;
	private String remark;
	private String logo;
	private String type;
	private String orderBy;
	private String contacts;
	private String phoneNumber;
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
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	public String getContacts() {
		return contacts;
	}
	public void setContacts(String contacts) {
		this.contacts = contacts;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	@Override
	public int hashCode() {
		return super.hashCode()+this.name.hashCode();
	}
	@Override
	public String toString() {
		return super.toString();
	}

}

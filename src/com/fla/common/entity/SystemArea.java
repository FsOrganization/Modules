package com.fla.common.entity;

import java.io.Serializable;

import org.springframework.stereotype.Service;

@Service
public class SystemArea implements Serializable{

	private static final long serialVersionUID = -6842795345457771L;

	private Integer id ;
	private String name;
	private String type;
	private String code;
	private String remark;
	
	
	public SystemArea() {
	}


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


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
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


}

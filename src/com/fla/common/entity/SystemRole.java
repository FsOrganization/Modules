package com.fla.common.entity;

import java.io.Serializable;

import com.fla.common.base.PrimaryKey;

public class SystemRole  implements Serializable {
	private static final long serialVersionUID = 3716259092470344289L;
	
	@PrimaryKey
	private Integer id;
	private String name;
	private String title;
	private String status;
	private Integer displayOrder;
	private String code;
	private Integer parentId;
	private String createdTime;
	private String createdOperator;
	private String lastDate;
	private String lastOperator;
	private String remark;
	
	public SystemRole() {
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	public String getCreatedOperator() {
		return createdOperator;
	}

	public void setCreatedOperator(String createdOperator) {
		this.createdOperator = createdOperator;
	}

	public String getLastDate() {
		return lastDate;
	}

	public void setLastDate(String lastDate) {
		this.lastDate = lastDate;
	}

	public String getLastOperator() {
		return lastOperator;
	}

	public void setLastOperator(String lastOperator) {
		this.lastOperator = lastOperator;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}

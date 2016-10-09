package com.fla.common.entity;

import java.io.Serializable;
import java.util.List;

import com.fla.common.base.PrimaryKey;

public class SysMenu implements Serializable {

	private static final long serialVersionUID = 73565927726533414L;
	
	@PrimaryKey
	private Integer id;
	private String name;
	private String title;
	private String status;
	private Integer level;
	private Integer displayOrder;
	private String url;
	private String code;
	private String className;
	private String icon;
	private Integer parentId;
	private String createdTime;
	private String createdOperator;
	private String lastDate;
	private String lastOperator;
	private String remark;
	private String abstractLevel;
	private List<SysMenu> childSysMenuNodes;
	
	public SysMenu() {
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

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
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

	public List<SysMenu> getChildSysMenuNodes() {
		return childSysMenuNodes;
	}

	public void setChildSysMenuNodes(List<SysMenu> childSysMenuNodes) {
		this.childSysMenuNodes = childSysMenuNodes;
	}

	public String getAbstractLevel() {
		return abstractLevel;
	}

	public void setAbstractLevel(String abstractLevel) {
		this.abstractLevel = abstractLevel;
	}
	

}

package com.fla.common.entity;

import java.io.Serializable;

import com.fla.common.base.PrimaryKey;

public class SysButtonLimitAuthority  implements Serializable {
	private static final long serialVersionUID = -5866802044171364299L;
	
	@PrimaryKey
	private Integer id;
	private Integer userId;
	private Integer menuId;
	private String licenseeAuthorize;
	private String status;
	private String code;
	private String limitPurview;
	private String limitCode;
	private String startDate;
	private String endDate;
	private String remark;
	
	public SysButtonLimitAuthority() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getMenuId() {
		return menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

	public String getLicenseeAuthorize() {
		return licenseeAuthorize;
	}

	public void setLicenseeAuthorize(String licenseeAuthorize) {
		this.licenseeAuthorize = licenseeAuthorize;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLimitPurview() {
		return limitPurview;
	}

	public void setLimitPurview(String limitPurview) {
		this.limitPurview = limitPurview;
	}

	public String getLimitCode() {
		return limitCode;
	}

	public void setLimitCode(String limitCode) {
		this.limitCode = limitCode;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	

}

package com.fla.common.entity;

import java.io.Serializable;

import com.fla.common.base.PrimaryKey;

public class SysUserRole  implements Serializable {
	private static final long serialVersionUID = -2174621108365727474L;
	
	@PrimaryKey
	private Integer id;
	private Integer userId;
	private String loginName;
	private String nickName;
	private Integer roleId;
	private String roleName;
	private String areaCode;
	private String status;
	private String code;
	private String limitPurview;
	private String limitCode;
	private String startDate;
	private String endDate;
	private String remark;
	
	public SysUserRole() {
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
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

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}

/**
 * 
 */
package com.fla.common.entity;

import java.io.Serializable;

import com.fla.common.base.PrimaryKey;

/**
 * @author Administrator
 *
 */
public class ScanneInfo implements Serializable{
	
	
	private static final long serialVersionUID = -3255484973996909529L;
	
	@PrimaryKey
	private Integer id;
	private String loginName;
	private String scanneName ;
	private String scanneCode;
	private String shopCode;
	private String scanneType;
	private String remark;
	
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
	public String getScanneName() {
		return scanneName;
	}
	public void setScanneName(String scanneName) {
		this.scanneName = scanneName;
	}
	public String getScanneCode() {
		return scanneCode;
	}
	public void setScanneCode(String scanneCode) {
		this.scanneCode = scanneCode;
	}
	public String getShopCode() {
		return shopCode;
	}
	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}
	public String getScanneType() {
		return scanneType;
	}
	public void setScanneType(String scanneType) {
		this.scanneType = scanneType;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}

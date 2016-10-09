package com.fla.common.entity;

import java.io.Serializable;

import com.fla.common.base.PrimaryKey;


public class Signature  implements Serializable {
	
	private static final long serialVersionUID = 3614231265071217290L;

	public Signature() {
	}
	
	@PrimaryKey
	private Integer id ;
	private String logistics;
	private String batchNumber;
	private String signatureImg;
	private String areaCode;
	private String serviceShopCode;
	private Character type;//1：入库，2：出库
	private Integer expressServiceId;
	private String operaTime;
	private String operator;
	private String remark;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getLogistics() {
		return logistics;
	}
	public void setLogistics(String logistics) {
		this.logistics = logistics;
	}
	public String getBatchNumber() {
		return batchNumber;
	}
	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}
	public String getSignatureImg() {
		return signatureImg;
	}
	public void setSignatureImg(String signatureImg) {
		this.signatureImg = signatureImg;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getServiceShopCode() {
		return serviceShopCode;
	}
	public void setServiceShopCode(String serviceShopCode) {
		this.serviceShopCode = serviceShopCode;
	}
	public Character getType() {
		return type;
	}
	public void setType(Character type) {
		this.type = type;
	}
	public Integer getExpressServiceId() {
		return expressServiceId;
	}
	public void setExpressServiceId(Integer expressServiceId) {
		this.expressServiceId = expressServiceId;
	}
	public String getOperaTime() {
		return operaTime;
	}
	public void setOperaTime(String operaTime) {
		this.operaTime = operaTime;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Override
	public int hashCode() {
		return super.hashCode()+this.operator.hashCode()+this.batchNumber.hashCode();
	}
	@Override
	public String toString() {
		return super.toString();
	}

}

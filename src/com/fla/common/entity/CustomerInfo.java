/**
 * 
 */
package com.fla.common.entity;

import java.io.Serializable;
import java.util.List;

import com.fla.common.base.PrimaryKey;

/**
 * @author Administrator
 *
 */
public class CustomerInfo implements Serializable {

	private static final long serialVersionUID = -6380837878276571351L;

	@PrimaryKey
	private Integer id;
	private String name;
	private String phoneNumber;
	private String landlineNumber;
	private String weixinId;
	private String identityCard;
	private String gender;
	private String address;
	private String areaCode;
	private String areaName;
	private String email;
	private String qq;
	private String remark;
	private String initialsCode;
	private String spellingCode;
	private String shopCode;
	private String shopName;
	private Short age;
	private String ageSection;
	private String whetherHaveCar;
	private String isInterest;
	private String password;
	private String loginName;
	private String version;
	private String loginFrequency;
	private String createDate;
	private List<ExpressProviderContacts> providerContacts;

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

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getLandlineNumber() {
		return landlineNumber;
	}

	public void setLandlineNumber(String landlineNumber) {
		this.landlineNumber = landlineNumber;
	}

	public String getWeixinId() {
		return weixinId;
	}

	public void setWeixinId(String weixinId) {
		this.weixinId = weixinId;
	}

	public String getIdentityCard() {
		return identityCard;
	}

	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public String getInitialsCode() {
		return initialsCode;
	}

	public void setInitialsCode(String initialsCode) {
		this.initialsCode = initialsCode;
	}

	public String getSpellingCode() {
		return spellingCode;
	}

	public void setSpellingCode(String spellingCode) {
		this.spellingCode = spellingCode;
	}

	public String getShopCode() {
		return shopCode;
	}

	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}

	public Short getAge() {
		return age;
	}

	public void setAge(Short age) {
		this.age = age;
	}

	public String getAgeSection() {
		return ageSection;
	}

	public void setAgeSection(String ageSection) {
		this.ageSection = ageSection;
	}

	public String getWhetherHaveCar() {
		return whetherHaveCar;
	}

	public void setWhetherHaveCar(String whetherHaveCar) {
		this.whetherHaveCar = whetherHaveCar;
	}

	@Override
	public int hashCode() {
		return super.hashCode() + this.phoneNumber.hashCode();
	}

	@Override
	public String toString() {
		return super.toString();
	}

	public String getIsInterest() {
		return isInterest;
	}

	public void setIsInterest(String isInterest) {
		this.isInterest = isInterest;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getLoginFrequency() {
		return loginFrequency;
	}

	public void setLoginFrequency(String loginFrequency) {
		this.loginFrequency = loginFrequency;
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

	public List<ExpressProviderContacts> getProviderContacts() {
		return providerContacts;
	}

	public void setProviderContacts(List<ExpressProviderContacts> providerContacts) {
		this.providerContacts = providerContacts;
	}

}

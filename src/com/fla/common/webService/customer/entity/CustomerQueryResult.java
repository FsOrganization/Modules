package com.fla.common.webService.customer.entity;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="EXPRESS_LIST")
public class CustomerQueryResult {

	@XmlElement(name = "RESULT_FLAG", required = true, nillable = false)
	private String resultFlag;

	@XmlElement(name = "RESULT_TEXT", required = true, nillable = false)
	private String resultText;

	@XmlElement(name = "RESULT_TYPE", required = true, nillable = false)
	private String resultType;

	@XmlElementWrapper(name = "CUSTOMER_BEAN_LIST", required = true, nillable = false)
	@XmlElement(name = "CUSTOMER", required = true, nillable = false)
	private List<CustomerBean> customerBeanList;

	public String getResultFlag() {
		return resultFlag;
	}

	public void setResultFlag(String resultFlag) {
		this.resultFlag = resultFlag;
	}

	public String getResultText() {
		return resultText;
	}

	public void setResultText(String resultText) {
		this.resultText = resultText;
	}

	public String getResultType() {
		return resultType;
	}

	public void setResultType(String resultType) {
		this.resultType = resultType;
	}

	public List<CustomerBean> getCustomerBeanList() {
		return customerBeanList;
	}

	public void setCustomerBeanList(List<CustomerBean> customerBeanList) {
		this.customerBeanList = customerBeanList;
	}

}

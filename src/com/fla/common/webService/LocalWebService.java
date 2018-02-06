package com.fla.common.webService;

import javax.jws.WebMethod;
import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.fla.common.entity.CustomerInfo;
import com.fla.common.service.interfaces.ExpressWebServiceInterface;

import net.sf.json.JSONArray;

@WebService(serviceName = "LocalWebService")
public class LocalWebService extends SpringBeanAutowiringSupport {

	@Autowired
	private ExpressWebServiceInterface expressWebService;

	public LocalWebService() {
	}
	
	@WebMethod
	public String getLocalCustomer(String userName,String pwd,String phoneNumber) {
		JSONArray ja = new JSONArray();
		CustomerInfo ci = new CustomerInfo();
		if (userName.equals("fallService") && pwd.equals("1fss3bsfbbb45tth56f")) {
			ci.setPhoneNumber(phoneNumber);
			ja = expressWebService.getCustomer(ci);
		}
		return ja.toString();
	}
}

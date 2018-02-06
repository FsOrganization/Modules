package com.fla.common.webService.customer.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jws.WebMethod;
import javax.jws.WebService;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.fla.common.entity.CustomerInfo;
import com.fla.common.service.interfaces.ICustomerService;
import com.fla.common.webService.customer.entity.CustomerBean;
import com.fla.common.webService.customer.entity.CustomerQueryBean;
import com.fla.common.webService.customer.entity.CustomerQueryResult;
import com.fla.common.webService.tools.JaxbXmlUtil;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

@WebService(serviceName = "CustomerInformationWebService")
public class CustomerInformationWebService extends SpringBeanAutowiringSupport {

	@Autowired
	private ICustomerService accessCustomerService;

	public CustomerInformationWebService() {
	}
	
	@WebMethod
	public String getLocalCustomer(String inParameter) {
		JSONObject ret = checkData(inParameter);
		String res = "";
		CustomerQueryResult cqr = new CustomerQueryResult();
		cqr.setResultFlag("S");
		cqr.setResultText("test");
		cqr.setResultType("0");
		if (ret.getBoolean("TAG")) {
			CustomerQueryBean cq = JaxbXmlUtil.xmlToBean(inParameter, CustomerQueryBean.class);
			String code = cq.getAccessCode();
			String password = cq.getAccessPassword();
			if (code.equals("fallService") && password.equals("1fss3bsfbbb45tth56f")) {
				Integer rowSize = cq.getRowSize();
				Integer pageSize = cq.getPageSize();
				PageBounds pageBounds = new PageBounds(pageSize, rowSize);
				Map<String,Object> params = new HashMap<String,Object>();
				List<CustomerInfo> customerBeanList =accessCustomerService.getCustomerInfoList(params, pageBounds);
				cqr.setCustomerBeanList(conversionData(customerBeanList));
			}
		} else {
			cqr.setResultFlag("E");
			cqr.setResultText("test");
			cqr.setResultType("-1");
		}
		
		res = JaxbXmlUtil.beanToXml(cqr);
		return res;
	}
	
	private JSONObject checkData(String inParameter) {
		JSONObject json = new JSONObject();
		json.put("MSG", null);	
		json.put("TAG", true);	
		if (inParameter == null || inParameter.trim().length() == 0) {
			json.put("MSG", "参数格式错误!");	
			json.put("TAG", false);	
		}
		return json;
		
	}
	
	private List<CustomerBean> conversionData(List<CustomerInfo> customerBeanList){
		List<CustomerBean> cbList = new ArrayList<CustomerBean>(customerBeanList.size());
		for (CustomerInfo ci : customerBeanList) {
			CustomerBean cb = new CustomerBean();
			cb.setAreaCode(ci.getAreaCode());
			cb.setIdentityCard(ci.getIdentityCard());
			cb.setLandlineNumber(ci.getLandlineNumber());
			cb.setName(ci.getName());
			cb.setPhoneNumber(ci.getPhoneNumber());
			cb.setServiceShopCode(ci.getShopCode());
			cb.setSpellingCode(ci.getSpellingCode());
			cb.setWeixinId(ci.getWeixinId());
			cbList.add(cb);
		}
		
		return cbList;
	}
}

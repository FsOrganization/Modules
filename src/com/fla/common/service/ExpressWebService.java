package com.fla.common.service;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fla.common.dao.interfaces.ExpressWebServiceDaoInterface;
import com.fla.common.entity.CustomerInfo;
import com.fla.common.service.interfaces.ExpressWebServiceInterface;
import com.fla.common.util.FlaJsonValueProcessor;

@Service
@Transactional
public class ExpressWebService implements ExpressWebServiceInterface{

	@Autowired
	private ExpressWebServiceDaoInterface expressWebServiceDao;

	public ExpressWebService() {
	}

	@Override
	public JSONArray getCustomer(CustomerInfo customer) {
		List<Map<String, Object>> inRowMap = expressWebServiceDao.getCustomerInfo(customer);
		JSONArray inJa = new JSONArray();
		if (inRowMap !=null) {
			makeJSONArray(inRowMap, inJa);
		}
		return inJa;
	}
	
	private void makeJSONArray(List<Map<String, Object>> rowMap, JSONArray ja) {
		for (Map<String, Object> map : rowMap) {
			JsonValueProcessor jv = new FlaJsonValueProcessor();
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(Date.class, jv);
			String jsonString = JSONSerializer.toJSON(map, jsonConfig).toString();
			JSONObject json = JSONObject.fromObject(jsonString);
			ja.add(json);
		}
	}
	
}

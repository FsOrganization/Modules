package com.fla.common.dao.interfaces;

import java.util.List;
import java.util.Map;

import com.fla.common.entity.CustomerInfo;

public interface ExpressWebServiceDaoInterface {
	
	public List<Map<String, Object>> getCustomerInfo(CustomerInfo customer);
	
	}

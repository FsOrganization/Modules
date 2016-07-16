/**
 * 
 */
package com.fla.common.service.interfaces;

import net.sf.json.JSONArray;

import com.fla.common.entity.CustomerInfo;

public interface ExpressWebServiceInterface {
	
	public JSONArray getCustomer(CustomerInfo customer);
	
}

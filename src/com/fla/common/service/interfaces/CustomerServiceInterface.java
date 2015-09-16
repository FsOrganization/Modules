/**
 * 
 */
package com.fla.common.service.interfaces;

import java.util.Map;

import net.sf.json.JSONArray;

/**
 * @author Administrator
 *
 */
public interface CustomerServiceInterface {
	
	public JSONArray getCustomerList(final int rowSize, final int pageSize, Map<String,String> params);
	
}

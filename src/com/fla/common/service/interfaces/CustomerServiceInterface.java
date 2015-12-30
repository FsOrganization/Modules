/**
 * 
 */
package com.fla.common.service.interfaces;

import java.sql.SQLException;
import java.util.Map;

import net.sf.json.JSONObject;

import com.fla.common.entity.CustomerInfo;
import com.fla.common.util.Pagination;

/**
 * @author Administrator
 *
 */
public interface CustomerServiceInterface {
	
	public Pagination getCustomerList(final int rowSize, final int pageSize, Map<String,String> params);
	
	public JSONObject getCustomerInfoByPhoneNumber(Map<String,String> params);
	
	public JSONObject modifyCustomerInfo(CustomerInfo customer) throws SQLException;
	
	public JSONObject registerCustomerByOpenId(CustomerInfo customer) throws SQLException;
	
	public JSONObject checkWechatOpenId(Map<String,String> params) throws SQLException;
	
	/**
	 * 更新客户性别
	 * @param phoneNumber
	 * @param sex
	 * @return
	 */
	public JSONObject updateCustomerGender(String phoneNumber,String sex);
	
	
	
}

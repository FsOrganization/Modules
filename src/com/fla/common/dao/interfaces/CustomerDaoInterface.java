package com.fla.common.dao.interfaces;

import java.sql.SQLException;
import java.util.Map;

import net.sf.json.JSONObject;

import com.fla.common.entity.CustomerInfo;
import com.fla.common.util.Pagination;

public interface CustomerDaoInterface {
	
	public Pagination getCustomerList(final int rowSize, final int pageSize, Map<String,String> params) throws SQLException;
	
	public void modifyCustomerInfo(CustomerInfo customer) throws SQLException;
	
	public void registerCustomerByOpenId(CustomerInfo customer) throws SQLException;
	
	/**
	 * 检查openid是否存在
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public JSONObject checkWechatOpenId(Map<String, String> params) throws SQLException;
	
	/**
	 * 通过shopCode查询areaCode
	 * @param shopCode
	 * @return
	 * @throws SQLException
	 */
	public String getAreaCodeByShopCode(String shopCode) throws SQLException;
	
	/**
	 * 更新客户性别
	 * @param phoneNumber
	 * @param sex
	 * @return
	 * @throws SQLException
	 */
	public JSONObject updateCustomerGender(String phoneNumber,String sex) throws SQLException;
	
	}

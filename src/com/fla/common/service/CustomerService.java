package com.fla.common.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fla.common.dao.interfaces.CustomerDaoInterface;
import com.fla.common.entity.CustomerInfo;
import com.fla.common.service.interfaces.CustomerServiceInterface;
import com.fla.common.util.Pagination;
import com.fla.common.util.ResultSetUtils;

@Service
@Transactional
public class CustomerService implements CustomerServiceInterface{

	@Autowired
	private CustomerDaoInterface customerDao;

	public CustomerService() {
	}
	
	@Override
	public Pagination getCustomerList(int rowSize, int pageSize,Map<String, String> params) {
		Pagination pageData = null;
		try 
		{
			pageData = customerDao.getCustomerList(rowSize, pageSize, params);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pageData;
	}

	@Override
	public JSONObject modifyCustomerInfo(CustomerInfo customer) throws SQLException {
		try 
		{
			customerDao.modifyCustomerInfo(customer);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public JSONObject registerCustomerByOpenId(CustomerInfo customer) throws SQLException {
		try 
		{
			String areaCode = customerDao.getAreaCodeByShopCode(customer.getShopCode());
			customer.setAreaCode(areaCode);
			customerDao.registerCustomerByOpenId(customer);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public JSONObject checkWechatOpenId(Map<String, String> params)
			throws SQLException {
		JSONObject j = null;
		try 
		{
			j = customerDao.checkWechatOpenId(params);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return j;
	}

	
	@Override
	public JSONObject updateCustomerGender(String phoneNumber, String sex) {
		try 
		{
			customerDao.updateCustomerGender(phoneNumber,sex);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public JSONObject getCustomerInfoByPhoneNumber(Map<String, String> params) {
		JSONObject json = new JSONObject();
		try 
		{
			json = customerDao.getCustomerInfoByPhoneNumber(params);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return json;
	}

	@Override
	public JSONArray getCustomerListByTxt(Map<String, String> params) {
		List<Map<String, Object>> rowMap = customerDao.getCustomerListByTxt(params);
		JSONArray ja = new JSONArray();
		if (rowMap !=null) {
			ResultSetUtils.makeJSONArray(rowMap, ja);
		}
		return ja;
	}

	@Override
	public JSONObject getOutExpressId(Map<String, String> params) {
		JSONObject json = new JSONObject();
		json = customerDao.getOutExpressId(params);
		return json;
	}

}

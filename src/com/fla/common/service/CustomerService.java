package com.fla.common.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fla.common.dao.interfaces.CustomerDaoInterface;
import com.fla.common.service.interfaces.CustomerServiceInterface;
import com.fla.common.util.ResultSetUtils;

@Service
@Transactional
public class CustomerService implements CustomerServiceInterface{

	@Autowired
	private CustomerDaoInterface customerDao;

	public CustomerService() {
	}
	
	@Override
	public JSONArray getCustomerList(int rowSize, int pageSize,Map<String, String> params) {
		JSONArray array = new JSONArray();
		try 
		{
			List<Map<String, Object>> mapList = customerDao.getCustomerList(rowSize, pageSize, params);
			if (mapList !=null) 
			{
				ResultSetUtils.makeJSONArray(mapList, array);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return array;
	}
	
	
}

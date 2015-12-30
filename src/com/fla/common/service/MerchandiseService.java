package com.fla.common.service;

import java.sql.SQLException;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fla.common.dao.MerchandiseDao;
import com.fla.common.service.interfaces.MerchandiseServiceInterface;
import com.fla.common.util.Pagination;

@Service
@Transactional
public class MerchandiseService implements MerchandiseServiceInterface{

	@Autowired
	private MerchandiseDao merchandiseDao;

	public MerchandiseService() {
	}

	@Override
	public Pagination getMerchandiseList(int rowSize, int pageSize,Map<String, String> params) {
		Pagination rowMap = null;
		try 
		{
			rowMap = merchandiseDao.getMerchandiseList(rowSize, pageSize, params);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rowMap;
	}

	@Override
	public JSONObject addMerchandise(Map<String, String> params) {
		JSONObject json = new JSONObject();
		try 
		{
			json=  merchandiseDao.addMerchandise(params);
		} catch (SQLException e) {
			e.printStackTrace();
			json.put("msg", e.getMessage());
		}
		return json;
	}

	@Override
	public JSONObject modifyMerchandiseInfo(Map<String, String> params) {
		JSONObject json = new JSONObject();
		try 
		{
			json=  merchandiseDao.modifyMerchandiseInfo(params);
		} catch (SQLException e) {
			e.printStackTrace();
			json.put("msg", e.getMessage());
		}
		return json;
	}
	
	
}

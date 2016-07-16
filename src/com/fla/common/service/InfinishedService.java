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

import com.fla.common.dao.interfaces.ExpressDaoInterface;
import com.fla.common.entity.SentExpressInfo;
import com.fla.common.service.interfaces.InfinishedServiceInterface;
import com.fla.common.util.FlaJsonValueProcessor;
import com.fla.common.util.Pagination;

@Service
@Transactional
public class InfinishedService implements InfinishedServiceInterface{

	@Autowired
	private ExpressDaoInterface expressDao;

	public InfinishedService() {
	}

	@Override
	public Pagination getSentExpressInfo(int rowSize, int pageSize,Map<String, String> params) {
		Pagination rowPage = expressDao.getSentExpressInfo(rowSize, pageSize,params);
		return rowPage;
	}
	
	@Override
	public JSONObject addSentExpressInfo(SentExpressInfo sei) {
		JSONObject json = new JSONObject();
		json.put("msg", "保存成功");
		try 
		{
			expressDao.insertSendExpressInfo(sei);
		} catch (Exception e) {
			e.printStackTrace();
			json.put("msg", e.getMessage());
		}
		return json;
	}
	
	@Override
	public JSONObject modifySentExpressInfo(SentExpressInfo sei) {
		JSONObject json = new JSONObject();
		json.put("msg", "保存成功");
		try 
		{
			expressDao.modifySentExpressInfo(sei);
		} catch (Exception e) {
			e.printStackTrace();
			json.put("msg", e.getMessage());
		}
		return json;
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

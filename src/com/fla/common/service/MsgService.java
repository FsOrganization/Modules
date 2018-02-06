package com.fla.common.service;

import java.sql.Date;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fla.common.dao.interfaces.MsgDaoInterface;
import com.fla.common.service.interfaces.MsgServiceInterface;
import com.fla.common.util.FlaJsonValueProcessor;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

@Service
@Transactional
public class MsgService implements MsgServiceInterface{

	@Autowired
	private MsgDaoInterface msgDao;

	public MsgService() {
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

	@Override
	public JSONArray getSendMsgShopList(Map<String, String> params)
			throws SQLException {
		List<Map<String, Object>> rowMap = msgDao.getSendMsgShopList(params);
		JSONArray array = new JSONArray();
		if (rowMap != null) {
			makeJSONArray(rowMap, array);
		}
		return array;
	}
	
	@Override
	public HashSet<String> getSendMsgShopStringList(Map<String, String> params) throws SQLException {
		List<Map<String, Object>> rowMap = msgDao.getSendMsgShopList(params);
		HashSet<String> list = new HashSet<String>();
		for (Map<String, Object> map : rowMap) {
			JsonConfig jsonConfig = new JsonConfig();
			String jsonString = JSONSerializer.toJSON(map, jsonConfig).toString();
			JSONObject json = JSONObject.fromObject(jsonString);
			String value;
			try 
			{
				value = json.get("VAlUE").toString();
			} catch (NullPointerException e) {
				continue;
			}
			list.add(value);
		}
		return list;
	}

}

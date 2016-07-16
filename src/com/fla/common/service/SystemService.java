package com.fla.common.service;

import java.sql.Date;
import java.sql.SQLException;
import java.util.HashMap;
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

import com.fla.common.dao.interfaces.SystemDaoInterface;
import com.fla.common.entity.ExpressServiceProvider;
import com.fla.common.entity.SystemArea;
import com.fla.common.entity.SystemShop;
import com.fla.common.entity.SystemUser;
import com.fla.common.service.interfaces.SystemServiceInterface;
import com.fla.common.util.FlaJsonValueProcessor;

@Service
@Transactional
public class SystemService implements SystemServiceInterface{

	@Autowired
	private SystemDaoInterface systemDao;

	public SystemService() {
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
	
	private Map<String,JSONObject> makeRowToMap(List<Map<String, Object>> rowMap) {
		Map<String,JSONObject> t = new HashMap<String,JSONObject>(rowMap.size());
		for (Map<String, Object> map : rowMap) {
			JsonValueProcessor jv = new FlaJsonValueProcessor();
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(Date.class, jv);
			String jsonString = JSONSerializer.toJSON(map, jsonConfig).toString();
			JSONObject json = JSONObject.fromObject(jsonString);
			t.put(json.get("ID").toString(), json);
		}
		return t;
	}
	
	private void makeJSONTreeArray(List<Map<String, Object>> rowMap, JSONArray ja) {
		Map<String, String> params = new HashMap<String, String>();
		for (Map<String, Object> map : rowMap) 
		{
			JsonValueProcessor jv = new FlaJsonValueProcessor();
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(Date.class, jv);
			String jsonString = JSONSerializer.toJSON(map, jsonConfig).toString();
			JSONObject json = JSONObject.fromObject(jsonString);
			params.put("configId", map.get("ID").toString());
			List<Map<String, Object>> childrenMaps =  systemDao.getConfigValuesList(params);
			JSONArray childrenArray = new JSONArray();
			makeJSONArray(childrenMaps, childrenArray);
			json.accumulate("children", childrenArray);
			ja.add(json);
		}
	}

	@Override
	public JSONArray getAreaInfoList(int rowSize, int pageSize,Map<String, String> params) {
		JSONArray array = new JSONArray();
		try 
		{
			List<Map<String, Object>> mapList = systemDao.getAreaInfoList(rowSize, pageSize, params);
			if (mapList !=null) 
			{
				makeJSONArray(mapList, array);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return array;
	}
	
	@Override
	public JSONArray getAreaInfoForSelect(Map<String, String> params) {
		JSONArray ja = new JSONArray();
		List<Map<String, Object>> mapList;
		try 
		{
			mapList = systemDao.getAreaInfoList(params);
			if (mapList != null && mapList.size() != 0) {
				for (Map<String, Object> map : mapList) {
					JSONObject json = new JSONObject();
					json.put("id", map.get("ID"));
					json.put("text", map.get("CODE"));
					json.put("desc", map.get("NAME"));
					ja.add(json);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ja;
	}
	@Override
	public JSONArray getShopInfoForSelect(Map<String, String> params) {
		JSONArray ja = new JSONArray();
		List<Map<String, Object>> mapList;
		try 
		{
			mapList = systemDao.getShopInfoList(params);
			if (mapList != null && mapList.size() != 0) {
				for (Map<String, Object> map : mapList) {
					JSONObject json = new JSONObject();
					json.put("id", map.get("ID"));
					json.put("text", map.get("SHOP_CODE"));
					json.put("desc", map.get("NAME"));
					ja.add(json);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ja;
	}
	
	@Override
	public JSONArray getSpecialShopInfoForSelect(Map<String, String> params) {
		JSONArray ja = new JSONArray();
		List<Map<String, Object>> mapList;
		try 
		{
			mapList = systemDao.getShopInfoList(params);
			if (mapList != null && mapList.size() != 0) {
				for (Map<String, Object> map : mapList) {
					JSONObject json = new JSONObject();
					json.put("value", map.get("SHOP_CODE"));
					json.put("text", map.get("NAME"));
					ja.add(json);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ja;
	}
	
	@Override
	public JSONArray getShopInfoList(int rowSize, int pageSize,Map<String, String> params) {
		JSONArray array = new JSONArray();
		try 
		{
			List<Map<String, Object>> mapList = systemDao.getShopInfoList(rowSize, pageSize, params);
			if (mapList !=null) 
			{
				makeJSONArray(mapList, array);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return array;
	}

	@Override
	public JSONArray getUserInfoList(int rowSize, int pageSize,Map<String, String> params) {
		JSONArray array = new JSONArray();
		try 
		{
			List<Map<String, Object>> mapList = systemDao.getUserInfoList(rowSize, pageSize, params);
			if (mapList !=null) 
			{
				makeJSONArray(mapList, array);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return array;
	}

	@Override
	public JSONArray getConfigInfoList(int rowSize, int pageSize,Map<String, String> params) {
		JSONArray array = new JSONArray();
		try 
		{
			List<Map<String, Object>> mapList = systemDao.getConfigInfoList(rowSize, pageSize, params);
			if (mapList !=null) 
			{
				makeJSONTreeArray(mapList, array);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return array;
	}
	
	@Override
	public JSONObject modifyAreaInfo(SystemArea area) {
		try 
		{
			systemDao.modifyAreaInfo(area);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public JSONObject modifyShopInfo(SystemShop shop) {
		try 
		{
			systemDao.modifyShopInfo(shop);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public JSONObject modifyUserInfo(SystemUser user) {
		try 
		{
			systemDao.modifyUserInfo(user);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public JSONObject modifyUserPassWord(SystemUser user) {
		try 
		{
			systemDao.modifyUserPassWord(user);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public JSONObject modifyUserInfo(SystemUser user,String tag) {
		try 
		{
			systemDao.modifyUserInfo(user,tag);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public JSONObject insertAreaInfo(SystemArea area, Map<String, String> params) {
		try 
		{
			systemDao.insertAreaInfoList(area, params);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
		
		
	}

	@Override
	public JSONObject insertShopInfo(SystemShop shop, Map<String, String> params) {
		try 
		{
			systemDao.insertShopInfoList(shop, params);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
		
	}

	@Override
	public JSONObject insertUserInfo(SystemUser user, Map<String, String> params) {
		try 
		{
			systemDao.insertUserInfoList(user, params);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
		
	}

	@Override
	public JSONObject deleteAreaInfo(Integer id, Map<String, String> params) {
		return null;
		
	}

	@Override
	public JSONObject deleteShopInfo(Integer id, Map<String, String> params) {
		return null;
		
	}

	@Override
	public JSONObject deleteUserInfo(Integer id, Map<String, String> params) {
		return null;
		
	}

	@Override
	public JSONObject getNewAreaCode(Map<String, String> params)
			throws SQLException {
		Map<String, Object> m = systemDao.getNewAreaCode();
		JSONObject json = new JSONObject();
		json.put("CODE", m.get("newAreaCode"));
		return json;
	}

	@Override
	public JSONObject getNewShopCode(Map<String, String> params)
			throws SQLException {
		Map<String, Object> m = systemDao.getNewShopCode();
		JSONObject json = new JSONObject();
		json.put("CODE", m.get("newShopCode"));
		return json;
	}

	@Override
	public JSONObject checkLoginNameUniqueness(String loginName)
			throws SQLException {
		Map<String, Object> m =systemDao.checkLoginNameUniqueness(loginName);
		JSONObject json = new JSONObject();
		if (m ==null) {
			json.put("UNIQUE", "true");
		} else {
			json.put("UNIQUE", "false");
		}
		return json;
		
	}

	
	
	@Override
	public JSONArray queryAreaInfos(String queryParams)  {
		JSONArray array = new JSONArray();
		try 
		{
			List<Map<String, Object>> mapList = systemDao.queryAreaInfos(queryParams);
			if (mapList !=null) 
			{
				makeJSONArray(mapList, array);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return array;
	}

	@Override
	public JSONArray queryShopInfos(String queryParams) {
		JSONArray array = new JSONArray();
		try 
		{
			List<Map<String, Object>> mapList = systemDao.queryShopInfos(queryParams);
			if (mapList !=null) 
			{
				makeJSONArray(mapList, array);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return array;
	}

	@Override
	public JSONArray queryUserInfos(String queryParams)  {
		JSONArray array = new JSONArray();
		try 
		{
			List<Map<String, Object>> mapList = systemDao.queryUserInfos(queryParams);
			if (mapList !=null) 
			{
				makeJSONArray(mapList, array);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return array;
	}

	@Override
	public String getShopAreaCode(String shopCode) throws SQLException {
		return systemDao.getShopAreaCode(shopCode);
	}

	@Override
	public synchronized JSONObject getLocationCodeByExpressType(String type,String shopCode) {
		Map<String, Object> m =null;
		try 
		{
			m = systemDao.getLocationCodeByExpressType(type,shopCode);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JSONObject j = new JSONObject();
		j.put("temporaryId", m.get("temporaryId"));
		return j;
	}

	@Override
	public JSONArray getExpressServiceProviderList(String areaCode,String shopCode) throws SQLException {
		List<Map<String, Object>> rowMap = systemDao.getExpressServiceProviderList(areaCode, shopCode);
		JSONArray array = new JSONArray();
		if (rowMap != null) {
			makeJSONArray(rowMap, array);
		}
		return array;
	}

	@Override
	public JSONObject insertExpressServiceProvider(
			ExpressServiceProvider esp, Map<String, String> params)
			throws SQLException {
		try 
		{
			systemDao.insertExpressServiceProvider(esp, params);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public JSONObject modifyExpressServiceProvider(ExpressServiceProvider esp) throws SQLException {
		try 
		{
			systemDao.modifyExpressServiceProvider(esp);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public JSONArray getExpressStatisticalArea(String areaCode)
			throws SQLException {
		JSONArray ja = new JSONArray();
		List<Map<String, Object>> rowMap = systemDao.getExpressStatisticalArea(areaCode);
		if (rowMap != null && rowMap.size() != 0) {
			for (Map<String, Object> map : rowMap) {
				JSONObject json = new JSONObject();
				json.put("id", map.get("ID"));
				json.put("text", map.get("NAME"));
				json.put("code", map.get("CODE"));
				json.put("type", "A");
				JSONArray cj = this.getAreaChildrenShops(map.get("CODE").toString());
				json.put("children", cj);
				ja.add(json);
			}
		}
		return ja;
	}
	
	public JSONArray getAreaChildrenShops(String areaCode) throws SQLException {
		JSONArray ja = new JSONArray();
		List<Map<String, Object>> rowMap = systemDao.getAreaChildrenShops(areaCode);
		if (rowMap != null && rowMap.size() != 0) {
			for (Map<String, Object> map : rowMap) {
				JSONObject json = new JSONObject();
				json.put("id", map.get("ID"));
				json.put("text", map.get("NAME"));
				json.put("code", map.get("SHOP_CODE"));
				json.put("type", "S");
				ja.add(json);
			}
		}
		return ja;
	}

	@Override
	public JSONArray getShopNumberOfPeopleGroupCount(String type,String code) throws SQLException {
		List<Map<String, Object>> rowMap = systemDao.getShopNumberOfPeopleGroupCount(type, code);
		JSONArray array = new JSONArray();
		if (rowMap != null) {
			makeJSONArray(rowMap, array);
		}
		return array;
	}
	
	@Override
	public JSONArray getShopInAndSendExpressGroupCount(String type,String code,String startDate,String endDate) throws SQLException {
		List<Map<String, Object>> rowMap = systemDao.getShopInAndSendExpressGroupCount(type, code,startDate,endDate);
		JSONArray array = new JSONArray();
		if (rowMap != null) {
			makeJSONArray(rowMap, array);
		}
		return array;
	}
	
	@Override
	public JSONArray getShopOutAndSendExpressGroupCount(Map<String, String> params) throws SQLException {
		List<Map<String, Object>> rowMap = systemDao.getShopOutAndSendExpressGroupCount(params);
		JSONArray array = new JSONArray();
		if (rowMap != null) {
			makeJSONArray(rowMap, array);
		}
		return array;
	}
	
	@Override
	public JSONArray getSendOutExpressByExpressGroup(Map<String, String> params) throws SQLException {
		List<Map<String, Object>> rowMap = systemDao.getSendOutExpressByExpressGroup(params);
		JSONArray array = new JSONArray();
		if (rowMap != null) {
			makeJSONArray(rowMap, array);
		}
		return array;
	}
	
	
	
	@Override
	public String getShopNameByCode(String shopCode) {
		Map<String,String> param = new HashMap<String,String>();
		param.put("shopCode", shopCode);
		String name="";
		try 
		{
			name = systemDao.getShopNameByCode(param);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return name;
	}

	@Override
	public JSONObject addServiceProviderContacts(Map<String, String> params) {
		try 
		{
			systemDao.addServiceProviderContacts(params);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public JSONArray queryExpressServiceProviderContactsList(String providerId, String shopCode) {
		List<Map<String, Object>> rowMap = systemDao.queryExpressServiceProviderContactsList(providerId, shopCode);
		JSONArray array = new JSONArray();
		if (rowMap != null) {
			makeJSONArray(rowMap, array);
		}
		return array;
	}

	@Override
	public JSONObject modifyServiceProviderContacts(Map<String, String> params)
			throws SQLException {
		try 
		{
			systemDao.modifyServiceProviderContacts(params);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public JSONObject deleteProviderContactsById(Map<String, String> params)
			throws SQLException {
		try 
		{
			systemDao.deleteProviderContactsById(params);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public JSONArray getSystemConfigInfo(Map<String, String> params)
			throws SQLException {
		List<Map<String, Object>> rowMap = systemDao.getSystemConfigInfo(params);
		JSONArray array = new JSONArray();
		if (rowMap != null) {
			makeJSONArray(rowMap, array);
		}
		return array;
	}
	
	@Override
	public JSONArray getSystemConfigValues(Map<String, String> params)
			throws SQLException {
		List<Map<String, Object>> rowMap = systemDao.getSystemConfigValues(params);
		JSONArray array = new JSONArray();
		if (rowMap != null) {
			makeJSONArray(rowMap, array);
		}
		return array;
	}

	@Override
	public Map<String,JSONObject> getAllConfigValues(Map<String, String> params) {
		List<Map<String, Object>> rowMap = systemDao.getAllConfigValues(params);
		Map<String,JSONObject>ff = null;
		if (rowMap != null) {
			ff = makeRowToMap(rowMap);
		}
		return ff;
	}
	
	
	
}

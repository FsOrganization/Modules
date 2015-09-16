package com.fla.common.service;

import java.sql.Date;
import java.sql.SQLException;
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

import com.fla.common.dao.LoginDao;
import com.fla.common.entity.CustomerInfo;
import com.fla.common.entity.ExpressInfo;
import com.fla.common.entity.Signature;
import com.fla.common.entity.SystemUser;
import com.fla.common.enums.Msg;
import com.fla.common.service.interfaces.LoginServiceInterface;
import com.fla.common.util.FlaJsonValueProcessor;

@Service
@Transactional
public class LoginService implements LoginServiceInterface{

	@Autowired
	private LoginDao loginDao;

	public LoginService() {
	}

	public JSONArray getExpressInfoList(final int rowSize, final int pageSize,Map<String,String> params) throws SQLException {
		List<Map<String, Object>> inRowMap = loginDao.getInExpressInfoList(rowSize, pageSize,params);
		JSONArray inJa = new JSONArray();
		if (inRowMap !=null) {
			makeJSONArray(inRowMap, inJa);
		}
		return inJa;
	}
	
	public JSONArray getInAndOutExpressInfoList(final int rowSize, final int pageSize,Map<String,String> params) throws SQLException {
		List<Map<String, Object>> inRowMap = loginDao.getInExpressInfoList(rowSize, pageSize,params);
		List<Map<String, Object>> outRowMap = loginDao.getOutExpressInfoList(rowSize, pageSize,params);
		JSONArray inJa = new JSONArray();
		JSONArray outJa = new JSONArray();
		if (inRowMap !=null) {
			makeJSONArray(inRowMap, inJa);
		}
		if (outRowMap !=null) {
			makeJSONArray(outRowMap, outJa);
		}
		inJa.addAll(outJa);
		return inJa;
	}
	
	@Override
	public JSONArray getExpressByBatchNumber(int rowSize, int pageSize,Map<String, String> params) throws SQLException {
		List<Map<String, Object>> rowMap = loginDao.getExpressByBatchNumber(rowSize, pageSize,params);
		JSONArray ja = new JSONArray();
		if (rowMap !=null) {
			makeJSONArray(rowMap, ja);
		}
		return ja;
	}

	public JSONObject editDataById(ExpressInfo ei) throws SQLException {
		JSONObject ja = new JSONObject();
		ja.put("msg", "修改成功");
		ja.put("type", Msg.success);
		try 
		{
			loginDao.editDataById(ei);
		} catch (Exception e) {
			ja.put("msg", e.getMessage());
			ja.put("type", Msg.error);
			throw new RuntimeException(e);
		}
		
		return ja;
	}

	@Override
	public JSONObject addExpressInfo(ExpressInfo ei) {
		JSONObject json = new JSONObject();
		json.put("msg", "保存成功");
		try 
		{
			loginDao.insertExpressInfo(ei);
		} catch (Exception e) {
			e.printStackTrace();
			json.put("msg", e.getMessage());
		}
		return json;
	}

	@Override
	public  synchronized  JSONObject getTemporaryStorage() {
		JSONObject json = new JSONObject();
		Integer id =  loginDao.getTemporaryStorage();
		json.put("temporaryId", id);
		return json;
	}

	@Override
	public JSONObject getOutStorehouseBatchNumber() throws SQLException {
		JSONObject json = new JSONObject();
		Integer id =  loginDao.getOutStorehouseBatchNumber();
		json.put("temporaryId", id);
		return json;
	}

	@Override
	public SystemUser checkLoginAction(String loginName) throws SQLException {
		Map<String, Object> m = loginDao.checkLoginAction(loginName);
		SystemUser su = new SystemUser();
		if (m!=null && m.size() != 0) {
			su.setAreaCode(m.get("AREA_CODE").toString());
			su.setId(Integer.valueOf(m.get("ID").toString()));
			su.setLoginName(m.get("LOGIN_NAME").toString());
			su.setNickName(m.get("NICK_NAME").toString());
			su.setPassword(m.get("PASSWORD").toString());
			su.setServiceShopCode(m.get("SERVICE_SHOP_CODE").toString());
//			su.setRemark(m.get("REMARK").toString());
		}
		return su;
		
	}
	
	@Override
	public JSONArray getCustomeInfoList(String areaCode,String shopCode) throws SQLException {
		JSONArray ja = new JSONArray();
		List<Map<String, Object>> rowMap = loginDao.getCustomeInfoList(areaCode,shopCode);
		if (rowMap != null && rowMap.size() != 0) {
			for (Map<String, Object> map : rowMap) {
				JSONObject json = new JSONObject();
				json.put("id", map.get("PHONE_NUMBER"));
				json.put("text", map.get("NAME"));
				json.put("desc",map.get("PHONE_NUMBER"));
				ja.add(json);
			}
		}
		return ja;
	}

	@Override
	public JSONObject insertCustomeInfo(CustomerInfo ci) throws SQLException {
		JSONObject json = new JSONObject();
		json.put("msg", "保存成功");
		try 
		{
			loginDao.insertCustomeInfo(ci);
		} catch (Exception e) {
			e.printStackTrace();
			json.put("msg", e.getMessage());
		}
		return json;
	}

	@Override
	public JSONArray getExpressServiceProviderInfo(String areaCode) throws SQLException {
		JSONArray ja = new JSONArray();
		JSONObject jsonEmpty = new JSONObject();
		jsonEmpty.put("id", "");
		jsonEmpty.put("text", "请选择快递服务商");
		jsonEmpty.put("desc", "请选择快递服务商");
		ja.add(jsonEmpty);
		List<Map<String, Object>> rowMap = loginDao.getExpressServiceProviderInfo(areaCode);
		if (rowMap != null && rowMap.size() != 0) {
			for (Map<String, Object> map : rowMap) {
				JSONObject json = new JSONObject();
				json.put("id", map.get("ID"));
				json.put("text", map.get("NAME"));
				json.put("desc", map.get("NAME")+","+map.get("REMARK"));
				ja.add(json);
			}
		}
		return ja;
	}

	@Override
	public JSONObject letExpressOutStorehouse(Integer eId)throws SQLException {
		JSONObject json = new JSONObject();
		json.put("msg", "操作成功");
		ExpressInfo ei = null;
		try 
		{
			ei =  loginDao.getExpressInfoById(eId);
			loginDao.letExpressOutStorehouse(ei);
		} catch (Exception e) {
			json.put("msg", e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public JSONObject letExpressOutStorehouse(List<Integer> eIds, String batchNumber) throws SQLException {
		JSONObject json = new JSONObject();
		json.put("msg", "操作成功");
		ExpressInfo ei = null;
		try
		{
			for (Integer id : eIds) {
				ei =  loginDao.getExpressInfoById(id);
				ei.setOutBatchNumber(batchNumber);
				loginDao.letExpressOutStorehouse(ei);
			}
		} catch (Exception e) {
			json.put("msg", e.getMessage());
			e.printStackTrace();
		}
		return json;
	}

	@Override
	public JSONArray getExpressInfoByFilterConditions(int rowSize,	int pageSize, Map<String, String> params) throws SQLException {
		List<Map<String, Object>> rowMap = loginDao.getExpressInfoByFilterConditions(rowSize, pageSize,params);
		JSONArray ja = new JSONArray();
		if (rowMap !=null) {
			makeJSONArray(rowMap, ja);
		}
		return ja;
	}
	
	@Override
	public JSONArray exportExpressInfoByFilterConditions(Map<String, String> params) throws SQLException {
		List<Map<String, Object>> rowMap = loginDao.exportExpressInfoByFilterConditions(params);
		JSONArray ja = new JSONArray();
		if (rowMap !=null) {
			makeJSONArray(rowMap, ja);
		}
		return ja;
	}
	
	@Override
	public JSONArray getNotOutExpressInfoByFilterConditions(int rowSize,int pageSize, Map<String, String> params) throws SQLException {
		List<Map<String, Object>> rowMap = loginDao.getNotOutExpressInfoByFilterConditions(rowSize, pageSize,params);
		JSONArray ja = new JSONArray();
		if (rowMap !=null) {
			makeJSONArray(rowMap, ja);
		}
		return ja;
	}
	
	@Override
	public JSONArray getSimplyConstructedNotOutExpressInfoByFilter(int rowSize,int pageSize, Map<String, String> params) throws SQLException {
		List<Map<String, Object>> rowMap = loginDao.getSimplyConstructedNotOutExpressInfoByFilter(rowSize, pageSize,params);
		JSONArray ja = new JSONArray();
		if (rowMap !=null) {
			makeJSONArray(rowMap, ja);
		}
		return ja;
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
	public JSONObject insertSignature(Signature sign) throws SQLException {
		JSONObject json = new JSONObject();
		json.put("msg", "保存成功");
		try 
		{
			loginDao.insertSignature(sign);
		} catch (Exception e) {
			e.printStackTrace();
			json.put("msg", e.getMessage());
		}
		return json;
	}

	@Override
	public JSONObject getSignatureByBatchNumber(String batchNumber, String type) {
		JSONObject json = new JSONObject();
		json.put("streamCode", "-1");
		try 
		{
			Signature sign = loginDao.getSignatureByBatchNumber(batchNumber, type);
			json.put("streamCode", sign.getSignatureImg());
		} catch (Exception e) {
			e.printStackTrace();
			json.put("streamCode", e.getMessage());
		}
		return json;
	}

	
	
}

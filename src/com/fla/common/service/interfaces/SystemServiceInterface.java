package com.fla.common.service.interfaces;

import java.sql.SQLException;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.fla.common.entity.ExpressServiceProvider;
import com.fla.common.entity.SystemArea;
import com.fla.common.entity.SystemShop;
import com.fla.common.entity.SystemUser;

public interface SystemServiceInterface {
	
	public JSONArray getAreaInfoForSelect(Map<String,String> params);
	
	public JSONArray getShopInfoForSelect(Map<String,String> params);
	
	public JSONArray getSpecialShopInfoForSelect(Map<String,String> params);
	
	public JSONArray getAreaInfoList(final int rowSize, final int pageSize, Map<String,String> params);
	
	public JSONArray getShopInfoList(final int rowSize, final int pageSize, Map<String,String> params);
	
	public JSONArray getUserInfoList(final int rowSize, final int pageSize, Map<String,String> params);
	
	public JSONArray getConfigInfoList(final int rowSize, final int pageSize, Map<String,String> params);
	
	public JSONObject modifyAreaInfo(SystemArea area) throws SQLException;
	
	public JSONObject modifyShopInfo(SystemShop shop) throws SQLException;
	
	public JSONObject modifyUserInfo(SystemUser user) throws SQLException;
	
	public JSONObject modifyUserInfo(SystemUser user,String tag) throws SQLException;
	
	public JSONObject insertAreaInfo(SystemArea area,Map<String,String> params) throws SQLException;
	
	public JSONObject insertShopInfo(SystemShop shop,Map<String,String> params) throws SQLException;
	
	public JSONObject insertUserInfo(SystemUser user,Map<String,String> params) throws SQLException;
	
	public JSONObject deleteAreaInfo(Integer id,Map<String,String> params) throws SQLException;
	
	public JSONObject deleteShopInfo(Integer id,Map<String,String> params) throws SQLException;
	
	public JSONObject deleteUserInfo(Integer id,Map<String,String> params) throws SQLException;
	
	public JSONObject getNewAreaCode(Map<String,String> params) throws SQLException;
	
	public JSONObject getNewShopCode(Map<String,String> params) throws SQLException;
	
	public JSONObject checkLoginNameUniqueness(String loginName) throws SQLException;
	
	public  String getShopAreaCode(String shopCode)throws SQLException;
	
	public JSONArray queryAreaInfos(String queryParams) ;
	
	public JSONArray queryShopInfos(String queryParams) ;
	
	public JSONArray queryUserInfos(String queryParams) ;
	
	public JSONObject getLocationCodeByExpressType(String type,String areaCode);
	
	public JSONArray getExpressServiceProviderList(String areaCode,String shopCode) throws SQLException ;
	
	public JSONObject insertExpressServiceProvider(ExpressServiceProvider esp,Map<String,String> params) throws SQLException;
	
	public JSONObject modifyExpressServiceProvider(ExpressServiceProvider esp) throws SQLException;
	
	public JSONArray getExpressStatisticalArea(String areaCode) throws SQLException;
	
	public JSONArray getShopNumberOfPeopleGroupCount(String type,String code) throws SQLException;
	
	public JSONArray getShopInAndSendExpressGroupCount(String type,String code,String startDate,String endDate) throws SQLException ;
	
}

package com.fla.common.dao.interfaces;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.fla.common.entity.ExpressServiceProvider;
import com.fla.common.entity.SystemArea;
import com.fla.common.entity.SystemShop;
import com.fla.common.entity.SystemUser;

public interface SystemDaoInterface {
	
	public List<Map<String, Object>> getAreaInfoList(final int rowSize, final int pageSize, Map<String,String> params) throws SQLException;
	
	public List<Map<String, Object>> getAreaInfoList(Map<String,String> params) throws SQLException;
	
	public List<Map<String, Object>> getShopInfoList(final int rowSize, final int pageSize, Map<String,String> params) throws SQLException;
	
	public List<Map<String, Object>> getShopInfoList(Map<String,String> params) throws SQLException;
	
	public List<Map<String, Object>> getUserInfoList(final int rowSize, final int pageSize, Map<String,String> params) throws SQLException;
	
	public List<Map<String, Object>> getConfigInfoList(final int rowSize, final int pageSize, Map<String,String> params) throws SQLException;
	
	public void modifyAreaInfo(SystemArea area) throws SQLException;
	
	public void modifyShopInfo(SystemShop shop) throws SQLException;
	
	public void modifyUserInfo(SystemUser user) throws SQLException;
	
	public void modifyUserPassWord(SystemUser user) throws SQLException;
	
	public void modifyUserInfo(SystemUser user,String tag) throws SQLException;
	
	public void insertAreaInfoList(SystemArea area,Map<String,String> params) throws SQLException;
	
	public void insertShopInfoList(SystemShop shop,Map<String,String> params) throws SQLException;
	
	public void insertUserInfoList(SystemUser user,Map<String,String> params) throws SQLException;
	
	public void deleteAreaInfoList(Integer id,Map<String,String> params) throws SQLException;
	
	public void deleteShopInfoList(Integer id,Map<String,String> params) throws SQLException;
	
	public void deleteUserInfoList(Integer id,Map<String,String> params) throws SQLException;
	
	public  Map<String, Object> getNewShopCode()throws SQLException;
	
	public  Map<String, Object> getNewAreaCode()throws SQLException;
	
	public  Map<String, Object> checkLoginNameUniqueness(String loginName)throws SQLException;
	
	public  String getShopAreaCode(String shopId)throws SQLException;
	
	public List<Map<String, Object>> queryAreaInfos(String queryParams) throws SQLException;
	
	public List<Map<String, Object>> queryShopInfos(String queryParams) throws SQLException;
	
	public List<Map<String, Object>> queryUserInfos(String queryParams) throws SQLException;
	
	public List<Map<String, Object>> getConfigValuesList(Map<String, String> params);
	
	public List<Map<String, Object>> getAllConfigValues(Map<String, String> params);
	
	public  Map<String, Object> getLocationCodeByExpressType(String type, String areaCode)throws SQLException;
	
	public List<Map<String, Object>> getExpressServiceProviderList(String areaCode,String shopCode)throws SQLException ;
	
	public void insertExpressServiceProvider(ExpressServiceProvider esp, Map<String, String> params) throws SQLException ;
	
	public void modifyExpressServiceProvider(ExpressServiceProvider esp) throws SQLException;
	
	public List<Map<String, Object>> getExpressStatisticalArea(Map<String, Object> params) throws SQLException;
	
	public List<Map<String, Object>> getShopGroupByArea(Map<String, Object> params) throws SQLException;
	
	public List<Map<String, Object>> getAreaChildrenShops(String areaCode) throws SQLException;
	
	public List<Map<String, Object>> getShopNumberOfPeopleGroupCount(String type,String areaCode) throws SQLException;
	
	public List<Map<String, Object>> getShopInAndSendExpressGroupCount(String type,String code,String startDate,String endDate) throws SQLException;
	
	public List<Map<String, Object>> getShopOutAndSendExpressGroupCount(Map<String, String> params) throws SQLException ;
	
	public List<Map<String, Object>> getShopOutAndSendExpressDaily(Map<String, String> params) throws SQLException ;
	
	public List<Map<String, Object>> getSendOutExpressByExpressGroup(Map<String, String> params) throws SQLException ;
	
	public String getShopNameByCode(Map<String, String> params) throws SQLException;
	
	public void addServiceProviderContacts(Map<String, String> params) throws SQLException;
	
	public List<Map<String, Object>> queryExpressServiceProviderContactsList(String providerId,String shopCode);
	
	public void modifyServiceProviderContacts(Map<String, String> params) throws SQLException;
	
	public void deleteProviderContactsById(Map<String, String> params) throws SQLException;
	
	public List<Map<String, Object>> getSystemConfigInfo(Map<String, String> params);
	
	public List<Map<String, Object>> getSystemConfigValues(Map<String, String> params);
	
	
	}

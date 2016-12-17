package com.fla.common.service.interfaces;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.fla.common.entity.CustomerInfo;
import com.fla.common.entity.ExpressInfo;
import com.fla.common.entity.Signature;
import com.fla.common.entity.SystemUser;
import com.fla.common.util.Pagination;

public interface LoginServiceInterface {
	
	public JSONArray getExpressByBatchNumber(final int rowSize, final int pageSize,Map<String,String> params) throws SQLException ;
	
	public Pagination getExpressInfoList(final int rowSize, final int pageSize,Map<String,String> params) throws SQLException ;
	
	public JSONObject editDataById(ExpressInfo ei) throws SQLException;
	
	public JSONObject addExpressInfo(ExpressInfo ei) throws SQLException;
	
	public JSONObject getTemporaryStorage() throws SQLException;
	
	public JSONObject getOutStorehouseBatchNumber() throws SQLException;
	
	public SystemUser checkLoginAction(String loginName) throws SQLException;
	
	public JSONArray getCustomeInfoList(String areaCode,String shopCode) throws SQLException ;
	
	public JSONObject insertCustomeInfo(CustomerInfo ci) throws SQLException;
	
	public JSONArray getExpressServiceProviderInfo(String areaCode) throws SQLException ;
	
	public JSONObject letExpressOutStorehouse(Integer eId) throws SQLException;
	
	public JSONObject letExpressOutStorehouse(List<Integer> eIds, String batchNumber) throws SQLException;
	
	public JSONObject letExpressOutStorehouseByExtractionCode(Integer id) throws SQLException;
	
	public JSONArray getExpressInfoByFilterConditions(final int rowSize, final int pageSize,Map<String,String> params) throws SQLException ;
	
	public Pagination getExpressInfoPagination(final int rowSize, final int pageSize,Map<String,String> params) throws SQLException ;
	
	public JSONArray exportExpressInfoByFilterConditions(Map<String, String> params) throws SQLException;
	
	public JSONArray getSimplyConstructedNotOutExpressInfoByFilter(int rowSize,int pageSize, Map<String, String> params) throws SQLException ;
	
	public JSONArray getSimplyConstructedNotOutExpressInfoByCustomerInput(Map<String, String> params) throws SQLException;
	
	public Pagination getNotOutExpressInfoByFilterConditions(final int rowSize, final int pageSize,Map<String,String> params) throws SQLException ;
	
	public Pagination searchExpressInfoByBarCode(final int rowSize, final int pageSize,Map<String,String> params) throws SQLException ;
	
	public JSONObject insertSignature(Signature sign) throws SQLException;
	
	public JSONObject getSignatureByBatchNumber(String batchNumber,  String type);
	
	public Map<String, Object>  checkExpressLocation(Map<String, String> params);
	
}

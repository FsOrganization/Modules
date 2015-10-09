package com.fla.common.dao.interfaces;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.fla.common.entity.CustomerInfo;
import com.fla.common.entity.ExpressInfo;
import com.fla.common.entity.Signature;
import com.fla.common.util.Pagination;

public interface LoginDaoInterface {
	
	/**
	 * 通过批号获取快件信息
	 * @param rowSize
	 * @param pageSize
	 * @param params
	 * @return
	 * @throws SQLException
	 */
		public List<Map<String, Object>> getExpressByBatchNumber(final int rowSize, final int pageSize, Map<String,String> params) throws SQLException;
		
		/**
		 * 获取未出库快件信息
		 * @param rowSize
		 * @param pageSize
		 * @param params
		 * @return
		 * @throws SQLException
		 */
		public Pagination getInExpressInfoList(final int rowSize, final int pageSize, Map<String,String> params) throws SQLException;
		
		/**
		 * 获取已出库快件信息
		 * @param rowSize
		 * @param pageSize
		 * @param params
		 * @return
		 * @throws SQLException
		 */
		public List<Map<String, Object>> getOutExpressInfoList(final int rowSize, final int pageSize, Map<String,String> params) throws SQLException;
		
		/**
		 * 按条件获取快件信息
		 * @param rowSize
		 * @param pageSize
		 * @param params
		 * @return
		 * @throws SQLException
		 */
		public List<Map<String, Object>> getExpressInfoByFilterConditions(final int rowSize, final int pageSize, Map<String,String> params) throws SQLException;
		
		/**
		 * 按条件导出快件信息
		 * @param params
		 * @return
		 * @throws SQLException
		 */
		public List<Map<String, Object>> exportExpressInfoByFilterConditions(Map<String, String> params) throws SQLException ;
		
		/**
		 * 获取未出库的快递
		 * @param rowSize
		 * @param pageSize
		 * @param params
		 * @return
		 * @throws SQLException
		 */
		public Pagination getNotOutExpressInfoByFilterConditions(final int rowSize, final int pageSize, Map<String,String> params);
		
		public List<Map<String, Object>> getSimplyConstructedNotOutExpressInfoByFilter(int rowSize, int pageSize, Map<String, String> params) throws SQLException ;
		
		/**
		 * 
		 * @param rs
		 * @return
		 * @throws SQLException
		 */
		public List<Map<String, Object>> checkResultSet(ResultSet rs) throws SQLException;
		
		/**
		 * 
		 * @param id
		 * @param name
		 * @throws Exception
		 */
		public void editDataById(ExpressInfo ei) throws Exception;
		
		/**
		 * 快件入库
		 * @param ei
		 * @throws Exception
		 */
		public void insertExpressInfo(ExpressInfo ei) throws Exception;
		
		/**
		 * 获取自动批号
		 * @return
		 */
		public  Integer getTemporaryStorage();
		
		/**
		 * 获取出库批号
		 * @return
		 */
		public  Integer getOutStorehouseBatchNumber();
		
		/**
		 * 登陆
		 * @param loginName
		 * @return
		 * @throws SQLException
		 */
		public Map<String, Object> checkLoginAction(String loginName) throws SQLException;
		
		
		
		/**
		 * 获取客户信息
		 * @param areaCode
		 * @return
		 * @throws SQLException
		 */
		public List<Map<String, Object>> getCustomeInfoList(String areaCode,String shopCode) throws SQLException;
		
		/**
		 * 获取快递服务商列表
		 * @param areaCode
		 * @return
		 * @throws SQLException
		 */
		public List<Map<String, Object>> getExpressServiceProviderInfo(String areaCode) throws SQLException;
		
		/**
		 * 添加客户信息 
		 * @param ci
		 * @throws Exception
		 */
		public void insertCustomeInfo(CustomerInfo ci) throws Exception;
	
		/**
		 *  出库
		 * @param ei
		 * @throws Exception
		 */
		public void letExpressOutStorehouse(ExpressInfo ei) throws Exception;
		
		/**
		 *  移除快件
		 * @param ei
		 * @throws Exception
		 */
		public void moveExpress(ExpressInfo ei) throws Exception;
		
		/**
		 * 获取一条快件信息
		 * @param eId
		 * @return
		 * @throws Exception
		 */
		public ExpressInfo getExpressInfoById(Integer eId) throws Exception;
		
		/**
		 * 获取ID
		 * @param seqName
		 * @return
		 */
		public Integer getSequenceByName(String seqName) ;
		
		/**
		 * 保存签名
		 * @param sign
		 * @throws Exception
		 */
		public void insertSignature(Signature sign) throws Exception;
		
		/**
		 * 获取签字
		 * @param batchNumber
		 * @param type
		 * @throws Exception
		 */
		public Signature getSignatureByBatchNumber(String batchNumber, String type) throws Exception;

		public Pagination getExpressInfoPagination(Integer rowSize, Integer pageSize, Map<String, String> params)throws SQLException;
		
	}

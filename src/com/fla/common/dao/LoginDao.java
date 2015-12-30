package com.fla.common.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.fla.common.dao.interfaces.LoginDaoInterface;
import com.fla.common.entity.CustomerInfo;
import com.fla.common.entity.ExpressInfo;
import com.fla.common.entity.Signature;
import com.fla.common.util.BaseUtil;
import com.fla.common.util.DateUtil;
import com.fla.common.util.Pagination;
import com.fla.common.util.SequenceManager;
import com.fla.common.util.connection.ConnectionManager;

@Service
public class LoginDao implements LoginDaoInterface {

	@Autowired
	public ConnectionManager connectionManager;
	
	@Autowired
	public JdbcTemplate jdbcTemplate;
	
	public LoginDao() {
	}

	public List<Map<String, Object>> getExpressByBatchNumber(final int rowSize, final int pageSize, Map<String,String> params) throws SQLException {
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement st = null;
		List<Map<String, Object>> t = null;
		try 
		{
			con = connectionManager.getConnection();//jdbcTemplate.getDataSource().getConnection();
			st = con.prepareStatement("select a.*,b.WEIXIN_ID from TF_EXPRESS_INFO a  "
					+ " left join tf_customer_info b on a.PHONE_NUMBER = b.PHONE_NUMBER "
					+ " where a.BATCH_NUMBER=? and a.SERVICE_SHOP_CODE=?  order by a.opera_time");
			st.setString(1, params.get("batchNumber"));
			st.setString(2, params.get("serviceShopCode"));
			rs = st.executeQuery();
			t = checkResultSet(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectionManager.closeResultSet(rs);
			connectionManager.closeStatement(st);
			connectionManager.closeConnection(con);
		}
		return t;

	}

	/**
	 * init page query
	 */
	@Override
	public Pagination getInExpressInfoList(int rowSize,int pageSize, Map<String, String> params) throws SQLException {
		Pagination page =null;
		String sql = new  String();
		try 
		{
			sql = ""
					+ " select a.ID,a.LOGISTICS,a.CODE,"
					+ " a.RECIPIENT_NAME,b.WEIXIN_ID,a.PHONE_NUMBER,a.LANDLINE_NUMBER,"
					+ " a.EXPRESS_SERVICE_ID,a.ADDRESS,a.REMARK,a.BATCH_NUMBER,"
					+ " date_format(a.OPERA_TIME,'%Y-%c-%d %H:%i:%s') OPERA_TIME,a.AREA_CODE,a.SERVICE_SHOP_CODE,"
					+ " a.OPERATOR,a.EXPRESS_lOCATION,1 TYPE,b.GENDER"
					+ " from TF_EXPRESS_INFO a left join tf_customer_info b on a.PHONE_NUMBER = b.PHONE_NUMBER "
					+ " where a.SERVICE_SHOP_CODE='"+params.get("serviceShopCode")+"'  order by a.opera_time desc";
			page=new Pagination(sql.toString(),pageSize,rowSize,getJdbcTemplate());
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return page;
	}
	
	@Override
	public List<Map<String, Object>> getOutExpressInfoList(int rowSize,int pageSize, Map<String, String> params) throws SQLException {
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement st = null;
		List<Map<String, Object>> t = null;
		try 
		{
			con = connectionManager.getConnection();//jdbcTemplate.getDataSource().getConnection();
			st = con.prepareStatement(""
					+ " select  ID,LOGISTICS,CODE,"
					+ " RECIPIENT_NAME,PHONE_NUMBER,LANDLINE_NUMBER,"
					+ " EXPRESS_SERVICE_ID,ADDRESS,REMARK,BATCH_NUMBER,OUT_BATCH_NUMBER,"
					+ " date_format(OPERA_TIME,'%Y-%c-%d %H:%i:%s') OPERA_TIME,AREA_CODE,SERVICE_SHOP_CODE,"
					+ " OPERATOR,EXPRESS_lOCATION,-1 TYPE "
					+ " from TF_EXPRESS_OUT_STOREHOUSE where AREA_CODE=?  order by opera_time");
			st.setString(1, params.get("areaCode"));
			rs = st.executeQuery();
			t = checkResultSet(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectionManager.closeResultSet(rs);
			connectionManager.closeStatement(st);
			connectionManager.closeConnection(con);
		}
		return t;
	}

	public Pagination queryPageBusiness(Integer currentPage,Integer numPerPage) {  
		String sql="SELECT * FROM business ORDER BY businessId ASC ";
		Pagination page=new Pagination(sql, currentPage, numPerPage,  getJdbcTemplate());
		return page;    
	}
	
	@Override
	public Pagination getExpressInfoPagination(Integer rowSize, Integer pageSize, Map<String, String> params) throws SQLException {
		Connection con = null;
		PreparedStatement st = null;
		Pagination page = null;
//		List<Map<String, Object>> t = null;
		try 
		{
			con = connectionManager.getConnection();//jdbcTemplate.getDataSource().getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" "
					+ " select ID, LOGISTICS, CODE, RECIPIENT_NAME, PHONE_NUMBER, "
					+ " LANDLINE_NUMBER, EXPRESS_SERVICE_ID, ADDRESS, REMARK, BATCH_NUMBER, ' ' OUT_BATCH_NUMBER, "
					+ " date_format(OPERA_TIME,'%Y-%c-%d %H:%i:%s') OPERA_TIME,' '  OUT_OPERA_TIME, AREA_CODE, SERVICE_SHOP_CODE, OPERATOR, EXPRESS_lOCATION,1 TYPE "
					+ " from TF_EXPRESS_INFO");
			sql.append(" where 1=1 ");
			sql.append(" and SERVICE_SHOP_CODE="+params.get("serviceShopCode"));
			if (params.get("queryParams") != null  &&  !params.get("queryParams").equals("")) {
				String queryParams = params.get("queryParams");
				sql.append(" "
						+ "	and (RECIPIENT_NAME like '%"+queryParams+"%'"
						+ "	or substring(PHONE_NUMBER, 8, 4) = '"+queryParams+"'"
						+ "	or trim(LOGISTICS) = '"+queryParams+"'"
						+ "	or PHONE_NUMBER = '"+queryParams+"')");
			}
			if (params.get("expressService") != null && !params.get("expressService").equals("")) {
				String expressService  =params.get("expressService");
				sql.append(" and EXPRESS_SERVICE_ID = "+expressService);
			}
			if (!BaseUtil.checkAllNull(params.get("startDate")) && !BaseUtil.checkAllNull(params.get("endDate"))) {
				String startDate = params.get("startDate");
				String endDate = params.get("endDate");
				sql.append(" and OPERA_TIME between '"+startDate+"' and '"+endDate+"'");
			} else  if(!BaseUtil.checkAllNull(params.get("startDate"))){
				String startDate = params.get("startDate");
				sql.append(" and OPERA_TIME >= '"+startDate+"'");
			} else  if(!BaseUtil.checkAllNull(params.get("endDate"))){
				String endDate = params.get("endDate");
				sql.append(" and OPERA_TIME <='"+endDate+"'");
			}
			StringBuilder sql2 = new StringBuilder();
			sql2.append(" "
					+ " select ID, LOGISTICS, CODE, RECIPIENT_NAME, PHONE_NUMBER, "
					+ " LANDLINE_NUMBER, EXPRESS_SERVICE_ID, ADDRESS, REMARK, BATCH_NUMBER, OUT_BATCH_NUMBER,  "
					+ " date_format(OPERA_TIME,'%Y-%c-%d %H:%i:%s') OPERA_TIME,OUT_OPERA_TIME, AREA_CODE, SERVICE_SHOP_CODE, OPERATOR, EXPRESS_lOCATION,-1 TYPE "
					+ " from TF_EXPRESS_OUT_STOREHOUSE");
			sql2.append(" where 1=1 ");
			sql2.append(" and SERVICE_SHOP_CODE="+params.get("serviceShopCode"));
			if (params.get("queryParams") != null  &&  !params.get("queryParams").equals("")) {
				String queryParams = params.get("queryParams");
				sql2.append(" "
						+ "	and (RECIPIENT_NAME like '%"+queryParams+"%'"
						+ "	or substring(PHONE_NUMBER, 8, 4) = '"+queryParams+"'"
						+ "	or trim(LOGISTICS) = '"+queryParams+"'"
						+ "	or PHONE_NUMBER = '"+queryParams+"')");
			}
			if (params.get("expressService") != null && !params.get("expressService").equals("")) {
				String expressService  =params.get("expressService");
				sql2.append(" and EXPRESS_SERVICE_ID = "+expressService);
			}
			if (!BaseUtil.checkAllNull(params.get("startDate")) && !BaseUtil.checkAllNull(params.get("endDate"))) {
				String startDate = params.get("startDate");
				String endDate = params.get("endDate");
				sql2.append(" and OPERA_TIME between '"+startDate+"' and '"+endDate+"'");
			} else  if(!BaseUtil.checkAllNull(params.get("startDate"))){
				String startDate = params.get("startDate");
				sql2.append(" and OPERA_TIME >= '"+startDate+"'");
			} else  if(!BaseUtil.checkAllNull(params.get("endDate"))){
				String endDate = params.get("endDate");
				sql2.append(" and OPERA_TIME <='"+endDate+"'");
			}
			sql2.append(" order by OPERA_TIME");
			String fsql = sql.toString() +" UNION ALL "+sql2.toString();
			page=new Pagination(fsql.toString(),pageSize,rowSize,getJdbcTemplate());
//			t = page.getResultList();
		} finally {
			connectionManager.closeStatement(st);
			connectionManager.closeConnection(con);
		}
		return page;
	}
	
	@Override
	public List<Map<String, Object>> getExpressInfoByFilterConditions(int rowSize, int pageSize, Map<String, String> params) throws SQLException {
 		ResultSet rs = null;
		Connection con = null;
		PreparedStatement st = null;
		List<Map<String, Object>> t = null;
		try 
		{
			con = connectionManager.getConnection();//jdbcTemplate.getDataSource().getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" "
					+ " select ID, LOGISTICS, CODE, RECIPIENT_NAME, PHONE_NUMBER, "
					+ " LANDLINE_NUMBER, EXPRESS_SERVICE_ID, ADDRESS, REMARK, BATCH_NUMBER, '' OUT_BATCH_NUMBER, "
					+ " date_format(OPERA_TIME,'%Y-%c-%d %H:%i:%s') OPERA_TIME,''  OUT_OPERA_TIME, AREA_CODE, SERVICE_SHOP_CODE, OPERATOR, EXPRESS_lOCATION,1 TYPE "
					+ " from TF_EXPRESS_INFO");
			sql.append(" where 1=1 ");
			sql.append(" and SERVICE_SHOP_CODE="+params.get("serviceShopCode"));
			if (params.get("queryParams") != null  &&  !params.get("queryParams").equals("")) {
				String queryParams = params.get("queryParams");
				sql.append(" "
						+ "	and (RECIPIENT_NAME like '%"+queryParams+"%'"
						+ "	or substring(PHONE_NUMBER, 8, 4) = '"+queryParams+"'"
						+ "	or trim(LOGISTICS) = '"+queryParams+"'"
						+ "	or PHONE_NUMBER = '"+queryParams+"')");
			}
			if (params.get("expressService") != null && !params.get("expressService").equals("")) {
				String expressService  =params.get("expressService");
				sql.append(" and EXPRESS_SERVICE_ID = "+expressService);
			}
			if (!BaseUtil.checkAllNull(params.get("startDate")) && !BaseUtil.checkAllNull(params.get("endDate"))) {
				String startDate = params.get("startDate");
				String endDate = params.get("endDate");
				sql.append(" and OPERA_TIME between '"+startDate+"' and '"+endDate+"'");
			} else  if(!BaseUtil.checkAllNull(params.get("startDate"))){
				String startDate = params.get("startDate");
				sql.append(" and OPERA_TIME >= '"+startDate+"'");
			} else  if(!BaseUtil.checkAllNull(params.get("endDate"))){
				String endDate = params.get("endDate");
				sql.append(" and OPERA_TIME <='"+endDate+"'");
			}
//			sql.append(" order by OPERA_TIME");
			StringBuilder sql2 = new StringBuilder();
			sql2.append(" "
					+ " select ID, LOGISTICS, CODE, RECIPIENT_NAME, PHONE_NUMBER, "
					+ " LANDLINE_NUMBER, EXPRESS_SERVICE_ID, ADDRESS, REMARK, BATCH_NUMBER, OUT_BATCH_NUMBER, "
					+ " date_format(OPERA_TIME,'%Y-%c-%d %H:%i:%s') OPERA_TIME,OUT_OPERA_TIME, AREA_CODE, SERVICE_SHOP_CODE, OPERATOR, EXPRESS_lOCATION,-1 TYPE "
					+ " from TF_EXPRESS_OUT_STOREHOUSE");
			sql2.append(" where 1=1 ");
			sql2.append(" and SERVICE_SHOP_CODE="+params.get("serviceShopCode"));
			if (params.get("queryParams") != null  &&  !params.get("queryParams").equals("")) {
				String queryParams = params.get("queryParams");
				sql2.append(" "
						+ "	and (RECIPIENT_NAME like '%"+queryParams+"%'"
						+ "	or substring(PHONE_NUMBER, 8, 4) = '"+queryParams+"'"
						+ "	or trim(LOGISTICS) = '"+queryParams+"'"
						+ "	or PHONE_NUMBER = '"+queryParams+"')");
			}
			if (params.get("expressService") != null && !params.get("expressService").equals("")) {
				String expressService  =params.get("expressService");
				sql2.append(" and EXPRESS_SERVICE_ID = "+expressService);
			}
			if (!BaseUtil.checkAllNull(params.get("startDate")) && !BaseUtil.checkAllNull(params.get("endDate"))) {
				String startDate = params.get("startDate");
				String endDate = params.get("endDate");
				sql2.append(" and OPERA_TIME between '"+startDate+"' and '"+endDate+"'");
			} else  if(!BaseUtil.checkAllNull(params.get("startDate"))){
				String startDate = params.get("startDate");
				sql2.append(" and OPERA_TIME >= '"+startDate+"'");
			} else  if(!BaseUtil.checkAllNull(params.get("endDate"))){
				String endDate = params.get("endDate");
				sql2.append(" and OPERA_TIME <='"+endDate+"'");
			}
			sql2.append(" order by OPERA_TIME");
			
			st = con.prepareStatement(sql.toString() +" UNION ALL "+sql2.toString());

			rs = st.executeQuery();
			t = checkResultSet(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectionManager.closeResultSet(rs);
			connectionManager.closeStatement(st);
			connectionManager.closeConnection(con);
		}
		return t;
	}
	
	@Override
	public List<Map<String, Object>> exportExpressInfoByFilterConditions(Map<String, String> params) throws SQLException {
 		ResultSet rs = null;
		Connection con = null;
		PreparedStatement st = null;
		List<Map<String, Object>> t = null;
		try 
		{
			con = connectionManager.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" "
					+ " select a.ID, LOGISTICS, a.CODE, RECIPIENT_NAME, a.PHONE_NUMBER, "
					+ " LANDLINE_NUMBER, b.name EXPRESS_SERVICE_ID, ADDRESS, a.REMARK, BATCH_NUMBER, '' OUT_BATCH_NUMBER, "
					+ " date_format(OPERA_TIME,'%Y-%c-%d %H:%i:%s') OPERA_TIME,''  OUT_OPERA_TIME, a.AREA_CODE, a.SERVICE_SHOP_CODE, OPERATOR, EXPRESS_lOCATION,1 TYPE "
					+ " from TF_EXPRESS_INFO a ,TF_EXPRESS_SERVICE_PROVIDER_INFO b");
			sql.append(" where 1=1 ");
			sql.append(" and a.EXPRESS_SERVICE_ID = b.id");
			sql.append(" and a.SERVICE_SHOP_CODE="+params.get("serviceShopCode"));
			if (params.get("queryParams") != null  &&  !params.get("queryParams").equals("")) {
				String queryParams = params.get("queryParams");
				sql.append(" "
						+ "	and (RECIPIENT_NAME like '%"+queryParams+"%'"
						+ "	or substring(PHONE_NUMBER, 8, 4) = '"+queryParams+"'"
						+ "	or trim(LOGISTICS) = '"+queryParams+"'"
						+ "	or PHONE_NUMBER = '"+queryParams+"')");
			}
			if (params.get("expressService") != null && !params.get("expressService").equals("")) {
				String expressService  =params.get("expressService");
				sql.append(" and EXPRESS_SERVICE_ID = "+expressService);
			}
			if (!BaseUtil.checkAllNull(params.get("startDate")) && !BaseUtil.checkAllNull(params.get("endDate"))) {
				String startDate = params.get("startDate");
				String endDate = params.get("endDate");
				sql.append(" and OPERA_TIME between '"+startDate+"' and '"+endDate+"'");
			} else  if(!BaseUtil.checkAllNull(params.get("startDate"))){
				String startDate = params.get("startDate");
				sql.append(" and OPERA_TIME >= '"+startDate+"'");
			} else  if(!BaseUtil.checkAllNull(params.get("endDate"))){
				String endDate = params.get("endDate");
				sql.append(" and OPERA_TIME <='"+endDate+"'");
			}
//			sql.append(" order by OPERA_TIME");
			StringBuilder sql2 = new StringBuilder();
			sql2.append(" "
					+ " select a.ID, LOGISTICS, a.CODE, RECIPIENT_NAME, a.PHONE_NUMBER, "
					+ " LANDLINE_NUMBER, b.name EXPRESS_SERVICE_ID, ADDRESS, a.REMARK, BATCH_NUMBER, OUT_BATCH_NUMBER, "
					+ " date_format(OPERA_TIME,'%Y-%c-%d %H:%i:%s') OPERA_TIME,OUT_OPERA_TIME, a.AREA_CODE, a.SERVICE_SHOP_CODE, OPERATOR, EXPRESS_lOCATION,-1 TYPE "
					+ " from TF_EXPRESS_OUT_STOREHOUSE a ,TF_EXPRESS_SERVICE_PROVIDER_INFO b");
			sql2.append(" where 1=1 ");
			sql2.append(" and a.EXPRESS_SERVICE_ID = b.id");
			sql2.append(" and a.SERVICE_SHOP_CODE="+params.get("serviceShopCode"));
			if (params.get("queryParams") != null  &&  !params.get("queryParams").equals("")) {
				String queryParams = params.get("queryParams");
				sql2.append(" "
						+ "	and (RECIPIENT_NAME like '%"+queryParams+"%'"
						+ "	or substring(PHONE_NUMBER, 8, 4) = '"+queryParams+"'"
						+ "	or trim(LOGISTICS) = '"+queryParams+"'"
						+ "	or PHONE_NUMBER = '"+queryParams+"')");
			}
			if (params.get("expressService") != null && !params.get("expressService").equals("")) {
				String expressService  =params.get("expressService");
				sql2.append(" and EXPRESS_SERVICE_ID = "+expressService);
			}
			if (!BaseUtil.checkAllNull(params.get("startDate")) && !BaseUtil.checkAllNull(params.get("endDate"))) {
				String startDate = params.get("startDate");
				String endDate = params.get("endDate");
				sql2.append(" and OPERA_TIME between '"+startDate+"' and '"+endDate+"'");
			} else  if(!BaseUtil.checkAllNull(params.get("startDate"))){
				String startDate = params.get("startDate");
				sql2.append(" and OPERA_TIME >= '"+startDate+"'");
			} else  if(!BaseUtil.checkAllNull(params.get("endDate"))){
				String endDate = params.get("endDate");
				sql2.append(" and OPERA_TIME <='"+endDate+"'");
			}
			sql2.append(" order by OPERA_TIME");
			
			st = con.prepareStatement(sql.toString() +" UNION ALL "+sql2.toString());

			rs = st.executeQuery();
			t = checkResultSet(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectionManager.closeResultSet(rs);
			connectionManager.closeStatement(st);
			connectionManager.closeConnection(con);
		}
		return t;
	}
	
	/**
	 * by params query
	 */
	@Override
	public Pagination  getNotOutExpressInfoByFilterConditions(int rowSize, int pageSize, Map<String, String> params)  {
		Pagination page = null;
		try 
		{
			StringBuilder sql = new StringBuilder();
			sql.append(" "
					+ " select a.ID, a.LOGISTICS, a.CODE,b.WEIXIN_ID,a.RECIPIENT_NAME, a.PHONE_NUMBER,"
					+ " a.LANDLINE_NUMBER, a.EXPRESS_SERVICE_ID, a.ADDRESS, a.REMARK, a.BATCH_NUMBER,"
					+ " date_format(a.OPERA_TIME,'%Y-%c-%d %H:%i:%s') OPERA_TIME, a.AREA_CODE, a.SERVICE_SHOP_CODE, a.OPERATOR, a.EXPRESS_lOCATION,1 TYPE "
					+ " from TF_EXPRESS_INFO a left join tf_customer_info b on a.PHONE_NUMBER = b.PHONE_NUMBER");
			sql.append(" where 1=1 ");
			sql.append(" and a.SERVICE_SHOP_CODE="+params.get("serviceShopCode"));
			if (params.get("queryParams") != null  &&  !params.get("queryParams").equals("")) {
				String queryParams = params.get("queryParams");
				sql.append(" "
						+ "	and (a.RECIPIENT_NAME like '%"+queryParams+"%'"
						+ "	or substring(a.PHONE_NUMBER, 8, 4) = '"+queryParams+"'"
						+ "	or trim(a.LOGISTICS) = '"+queryParams+"'"
						+ "	or b.INITIALS_CODE = '"+queryParams+"'"
						+ "	or a.PHONE_NUMBER = '"+queryParams+"')");
			}
			if (params.get("expressService") != null && !params.get("expressService").equals("")) {
				String expressService  =params.get("expressService");
				sql.append(" and a.EXPRESS_SERVICE_ID = "+expressService);
			}
			if (!BaseUtil.checkAllNull(params.get("startDate")) && !BaseUtil.checkAllNull(params.get("endDate"))) {
				String startDate = params.get("startDate");
				String endDate = params.get("endDate");
				sql.append(" and a.OPERA_TIME between '"+startDate+"' and '"+endDate+"'");
			} else  if(!BaseUtil.checkAllNull(params.get("startDate"))){
				String startDate = params.get("startDate");
				sql.append(" and a.OPERA_TIME >= '"+startDate+"'");
			} else  if(!BaseUtil.checkAllNull(params.get("endDate"))){
				String endDate = params.get("endDate");
				sql.append(" and a.OPERA_TIME <='"+endDate+"'");
			}
			sql.append(" order by a.OPERA_TIME desc");
			
			
			page=new Pagination(sql.toString(),pageSize,rowSize,getJdbcTemplate());
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return page;
	}
	
	@Override
	public List<Map<String, Object>> getSimplyConstructedNotOutExpressInfoByFilter(int rowSize, int pageSize, Map<String, String> params) throws SQLException {
 		ResultSet rs = null;
		Connection con = null;
		PreparedStatement st = null;
		List<Map<String, Object>> t = null;
		try 
		{
			con = connectionManager.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" "
					+ " select ID, LOGISTICS, RECIPIENT_NAME, PHONE_NUMBER, "
					+ " EXPRESS_SERVICE_ID,OPERA_TIME, AREA_CODE, SERVICE_SHOP_CODE, EXPRESS_lOCATION"
					+ " from TF_EXPRESS_INFO");
			sql.append(" where 1=1 ");
			if (params.get("queryParams") != null  &&  !params.get("queryParams").equals("")) {
				String queryParams = params.get("queryParams");
				sql.append(" "
						+ "	and (RECIPIENT_NAME = '"+queryParams+"'"
						+ "	or substring(PHONE_NUMBER, 8, 4) = '"+queryParams+"'"
						+ "	or trim(LOGISTICS) = '"+queryParams+"'"
						+ "	or PHONE_NUMBER = '"+queryParams+"')");
			}
			sql.append(" order by OPERA_TIME");
			st = con.prepareStatement(sql.toString());

			rs = st.executeQuery();
			t = checkResultSet(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectionManager.closeResultSet(rs);
			connectionManager.closeStatement(st);
			connectionManager.closeConnection(con);
		}
		return t;
	}
	
	@Override
	public List<Map<String, Object>> getSimplyConstructedNotOutExpressInfoByCustomerInput(Map<String, String> params) throws SQLException {
 		ResultSet rs = null;
		Connection con = null;
		PreparedStatement st = null;
		List<Map<String, Object>> t = null;
		try 
		{
			con = connectionManager.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" "
					+ " select LOGISTICS,trim(c.NAME) PROVIDER_NAME,OPERA_TIME, trim(b.NAME) SHOP_NAME "
					+ " from TF_EXPRESS_INFO a "
					+ " left join tf_shop_info b on a.SERVICE_SHOP_CODE = b.SHOP_CODE  "
					+ " left join tf_express_service_provider_info c on a.EXPRESS_SERVICE_ID = c.ID");
			sql.append(" where 1=1 ");
			if (params.get("queryParams") != null  &&  !params.get("queryParams").equals("")) {
				String queryParams = params.get("queryParams");
				sql.append(" "
						+ "	and (a.RECIPIENT_NAME = '"+queryParams+"'"
						+ "	or substring(a.PHONE_NUMBER, 8, 4) = '"+queryParams+"'"
						+ "	or trim(a.LOGISTICS) = '"+queryParams+"'"
						+ "	or a.PHONE_NUMBER = '"+queryParams+"')");
			}
			sql.append(" order by OPERA_TIME");
//			System.out.println(sql);
			st = con.prepareStatement(sql.toString());
			rs = st.executeQuery();
			t = checkResultSet(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectionManager.closeResultSet(rs);
			connectionManager.closeStatement(st);
			connectionManager.closeConnection(con);
		}
		return t;
	}

	public List<Map<String, Object>> checkResultSet(ResultSet rs) throws SQLException {
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>(rs.getRow());
		ResultSetMetaData m = rs.getMetaData();
		int count = m.getColumnCount();
		while (rs.next()) {
			Map<String,Object> map = new HashMap<String,Object>();
			for (int i = 1; i <= count; i++) {
				String cName = m.getColumnName(i);
				int d = Types.DATE;
				int  t = Types.TIME;
				int p = Types.TIMESTAMP;
				List<Integer> ts = new ArrayList<Integer>(3);
				ts.add(d);
				ts.add(t);
				ts.add(p);
				int  mt = m.getColumnType(i);
				if (ts.contains(mt)) {
					Object o = rs.getObject(i);
					if (o != null) {
						map.put(cName, sdf.format(o));
					} else {
						map.put(cName,"");
					}
				} else {
					map.put(cName, rs.getObject(i));
				}
			}
			list.add(map);
		}
		return list;
	}
	
	public Map<String, Object> checkOneResultSet(ResultSet rs)
			throws SQLException {
		if (!rs.next()) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>(
				rs.getRow());
		ResultSetMetaData m = rs.getMetaData();
		int count = m.getColumnCount();
		if (rs.first()) {
			Map<String, Object> map = new HashMap<String, Object>();
			for (int i = 1; i <= count; i++) {
				String cName = m.getColumnName(i);
				int d = Types.DATE;
				int t = Types.TIME;
				int p = Types.TIMESTAMP;
				List<Integer> ts = new ArrayList<Integer>(3);
				ts.add(d);
				ts.add(t);
				ts.add(p);
				int mt = m.getColumnType(i);
				if (ts.contains(mt)) {
					Object o = rs.getObject(i);
					if (o != null) {
						map.put(cName, sdf.format(o));
					} else {
						map.put(cName, "");
					}
				} else {
					map.put(cName, rs.getObject(i));
				}
			}
			list.add(map);
		}
		return list.get(0);
	}
	
	public void editDataById(ExpressInfo ei) throws Exception{
		Connection con = null;
		PreparedStatement st = null;
		String sql = "update TF_EXPRESS_INFO set LOGISTICS = ?, RECIPIENT_NAME=?, PHONE_NUMBER =?, EXPRESS_LOCATION = ?"
				+ "where ID=?";
		con = connectionManager.getConnection();//jdbcTemplate.getDataSource().getConnection();
        st=con.prepareStatement(sql);
		st.setString(1,ei.getLogistics());
		st.setString(2,ei.getRecipientName());
		st.setString(3,ei.getPhoneNumber());
		st.setString(4,ei.getExpressLocation());
		st.setLong(5, ei.getId());
		try 
		{
				st.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			connectionManager.closeStatement(st);
			connectionManager.closeConnection(con);
		}
		
	}
	
	public void insertExpressInfo(ExpressInfo ei) throws Exception{
		SequenceManager sm = SequenceManager.getInstance();
		Connection con = null;
		PreparedStatement st = null;
		con = connectionManager.getConnection();//jdbcTemplate.getDataSource().getConnection();
		Integer id = sm.getSequenceByName("seq_express_info_id",con);
		String insertSQL =
				"INSERT INTO TF_EXPRESS_INFO("+
				 "id,logistics,code,recipient_name,phone_number,"+
				 "landline_number,express_service_id,address,remark,opera_time,service_shop_code,area_code,operator,batch_number,express_Location)"+
			"VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        st=con.prepareStatement(insertSQL);
		st.setLong(1, id);
		st.setString(2, ei.getLogistics());
		st.setString(3, ei.getCode());
		st.setString(4, ei.getRecipientName());
		st.setString(5, ei.getPhoneNumber());
		st.setString(6, ei.getLandlineNumber());
		st.setLong(7, ei.getExpressServiceId());
		st.setString(8, ei.getAddress());
		st.setString(9, ei.getRemark());
		st.setString(10, DateUtil.formatDateToString(new Date()));
		st.setString(11, ei.getServiceShopCode());
		st.setString(12, ei.getAreaCode());
		st.setString(13, ei.getOperator());
		st.setString(14, ei.getInBatchNumber());
		st.setString(15, ei.getExpressLocation());
		try 
		{
			st.execute();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connectionManager.closeStatement(st);
			connectionManager.closeConnection(con);
		}
		
	}
	
	
	@Override
	public Map<String, Object> checkExpressLocation(Map<String, String> params)  {
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement st = null;
		Map<String, Object> t = null;
		try 
		{
			con = connectionManager.getConnection();//jdbcTemplate.getDataSource().getConnection();
			st = con.prepareStatement("select a.express_Location from TF_EXPRESS_INFO a  where a.express_Location=? and service_shop_code=? ");
			st.setString(1, params.get("expressLocation"));
			st.setString(2, params.get("shopCode"));
			rs = st.executeQuery();
			t = checkOneResultSet(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectionManager.closeResultSet(rs);
			connectionManager.closeStatement(st);
			connectionManager.closeConnection(con);
		}
		return t;
	}
	
	@Override
	public void insertCustomeInfo(CustomerInfo ci) throws Exception {
		SequenceManager sm = SequenceManager.getInstance();
		Connection con = null;
		PreparedStatement st = null;
		con = connectionManager.getConnection();//jdbcTemplate.getDataSource().getConnection();
		Integer id = sm.getSequenceByName("seq_custome_info_id",con);
		String insertSQL =
				" INSERT INTO TF_CUSTOMER_INFO("+
				 " ID, NAME, PHONE_NUMBER ,lANDLINE_NUMBER, WEIXIN_ID, IDENTITY_CARD, GENDER, ADDRESS, AREA_CODE, INITIALS_CODE, SPELLING_CODE,SERVICE_SHOP_CODE)"+
			    " SELECT ?,?,?,?,?,?,?,?,?,?,?,? FROM DUAL "
			+ " WHERE NOT EXISTS(SELECT NULL  FROM  TF_CUSTOMER_INFO  WHERE PHONE_NUMBER = ?) ";
        st=con.prepareStatement(insertSQL);
		st.setLong(1, id);
		st.setString(2, ci.getName());
		st.setString(3, ci.getPhoneNumber());
		st.setString(4, ci.getLandlineNumber());
		st.setString(5, ci.getWeixinId());
		st.setString(6, ci.getIdentityCard());
		st.setString(7, null);
		st.setString(8, ci.getAddress());
		st.setString(9, ci.getAreaCode());
		st.setString(10, ci.getInitialsCode());
		st.setString(11, ci.getSpellingCode());
		st.setString(12, ci.getShopCode());
		st.setString(13, ci.getPhoneNumber());
		try 
		{
			st.execute();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connectionManager.closeStatement(st);
			connectionManager.closeConnection(con);
		}
		
	}

	@Override
	public Integer getTemporaryStorage() {
		SequenceManager sm = SequenceManager.getInstance();
		Integer temporaryId = null;
		Connection con = null;
		try 
		{
			con = connectionManager.getConnection();//jdbcTemplate.getDataSource().getConnection();
			temporaryId = sm.getTemporaryStorage(con,"seq_temporary_storage_id");
		} finally {
		}
		return temporaryId;
	}

	@Override
	public synchronized Integer getOutStorehouseBatchNumber() {
		SequenceManager sm = SequenceManager.getInstance();
		Integer temporaryId = null;
		Connection con = null;
		try 
		{
			con = connectionManager.getConnection();//jdbcTemplate.getDataSource().getConnection();
			temporaryId = sm.getTemporaryStorage(con,"seq_temporary_out_storehouse_storage_id");
		} finally {
		}
		return temporaryId;
	}

	public Map<String, Object> checkLoginAction(String loginName) throws SQLException {
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement st = null;
		Map<String, Object> t = null;
		try 
		{
			con = connectionManager.getConnection();//jdbcTemplate.getDataSource().getConnection();
			st = con.prepareStatement("select * from tf_system_user  where login_name = ? and type='1' ");
			st.setString(1, loginName);
			rs = st.executeQuery();
			t = checkOneResultSet(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectionManager.closeResultSet(rs);
			connectionManager.closeStatement(st);
			connectionManager.closeConnection(con);
		}
		return t;

	}
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Map<String, Object>> getCustomeInfoList(String areaCode,String shopCode)throws SQLException {
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement st = null;
		List<Map<String, Object>> t = null;
		try 
		{
			con = connectionManager.getConnection();//jdbcTemplate.getDataSource().getConnection();
			st = con.prepareStatement("select  PHONE_NUMBER, NAME  from tf_customer_info  where SERVICE_SHOP_CODE = ?");
//			st.setString(1, areaCode);
			st.setString(1, shopCode);
			rs = st.executeQuery();
			t = checkResultSet(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectionManager.closeResultSet(rs);
			connectionManager.closeStatement(st);
			connectionManager.closeConnection(con);
		}
		return t;
	}
	
	@Override
	public List<Map<String, Object>> getExpressServiceProviderInfo(String areaCode) throws SQLException {
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement st = null;
		List<Map<String, Object>> t = null;
		try 
		{
			con = connectionManager.getConnection();//jdbcTemplate.getDataSource().getConnection();
			st = con.prepareStatement("select  ID, NAME, REMARK  from TF_EXPRESS_SERVICE_PROVIDER_INFO order by order_by asc");
//			st.setString(1, areaCode);
			rs = st.executeQuery();
			t = checkResultSet(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectionManager.closeResultSet(rs);
			connectionManager.closeStatement(st);
			connectionManager.closeConnection(con);
		}
		return t;
	}

	@Override
	public void letExpressOutStorehouse(ExpressInfo ei) {
		SequenceManager sm = SequenceManager.getInstance();
		Connection con = null;
		PreparedStatement st = null;
		try 
		{
			con = connectionManager.getConnection();//jdbcTemplate.getDataSource().getConnection();
			con.setAutoCommit(false);
			Integer id = sm.getSequenceByName("seq_express_out_storehouse_id",con);
			String insertSQL = "INSERT INTO TF_EXPRESS_OUT_STOREHOUSE("
					+ "id,logistics,code,recipient_name,phone_number,"
					+ "landline_number,express_service_id,address,remark,opera_time,service_shop_code,area_code,operator,batch_number,out_batch_number,out_opera_time)"
					+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			st = con.prepareStatement(insertSQL);
			st.setLong(1, id);
			st.setString(2, ei.getLogistics());
			st.setString(3, ei.getCode());
			st.setString(4, ei.getRecipientName());
			st.setString(5, ei.getPhoneNumber());
			st.setString(6, ei.getLandlineNumber());
			st.setLong(7, ei.getExpressServiceId());
			st.setString(8, ei.getAddress());
			st.setString(9, ei.getRemark());
			st.setString(10, ei.getOperaTime());
			st.setString(11, ei.getServiceShopCode());
			st.setString(12, ei.getAreaCode());
			st.setString(13, ei.getOperator());
			st.setString(14, ei.getInBatchNumber());
			st.setString(15, ei.getOutBatchNumber());
			st.setString(16, DateUtil.formatDateToString(new Date()));
			st.execute();
			moveExpress(ei);
			con.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try 
			{
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			 try 
			 {
				con.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			 connectionManager.closeStatement(st);
			 connectionManager.closeConnection(con);
		}
		
	}

	@Override
	public void moveExpress(ExpressInfo ei) throws SQLException {
		Connection con = null;
		PreparedStatement st = null;
		con = connectionManager.getConnection();//jdbcTemplate.getDataSource().getConnection();
		con.setAutoCommit(false);
		String insertSQL ="delete from TF_EXPRESS_INFO where id=?";
		 st=con.prepareStatement(insertSQL);
		 st.setLong(1, ei.getId());
		try 
		{
			st.execute();
			con.commit();
		} finally {
//			con.setAutoCommit(true);
//			connectionManager.closeStatement(st);
//			connectionManager.closeConnection(con);
		}
	}

	@Override
	public ExpressInfo getExpressInfoById(Integer eId) throws Exception {
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement st = null;
		Map<String, Object> t = null;
		try 
		{
			con = connectionManager.getConnection();//jdbcTemplate.getDataSource().getConnection();
			st = con.prepareStatement("select * from TF_EXPRESS_INFO where id=? ");
			st.setLong(1, eId);
			rs = st.executeQuery();
			t = checkOneResultSet(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectionManager.closeResultSet(rs);
			connectionManager.closeStatement(st);
			connectionManager.closeConnection(con);
		}
		return MapToEntity(t);
	}
	
	private ExpressInfo MapToEntity(Map<String, Object> t){
		ExpressInfo ei = new ExpressInfo();
		ei.setAddress(checkNull(t.get("ADDRESS")));
		ei.setAreaCode(checkNull(t.get("AREA_CODE")));
		ei.setInBatchNumber(checkNull(t.get("BATCH_NUMBER")));
		ei.setCode(checkNull(t.get("CODE")));
		ei.setExpressServiceId(Integer.valueOf(checkNull(t.get("EXPRESS_SERVICE_ID"))));
		ei.setId(Integer.valueOf(t.get("ID").toString()));
		ei.setLandlineNumber(checkNull(t.get("LANDLINE_NUMBER")));
		ei.setLogistics(checkNull(t.get("LOGISTICS")));
		ei.setOperaTime(checkNull(t.get("OPERA_TIME")));
		ei.setOperator(checkNull(t.get("OPERATOR")));
		ei.setPhoneNumber(checkNull(t.get("PHONE_NUMBER")));
		ei.setRecipientName(checkNull(t.get("RECIPIENT_NAME")));
		ei.setRemark(checkNull(t.get("REMARK")));
		ei.setServiceShopCode(checkNull(t.get("SERVICE_SHOP_CODE")));
		return ei;
	}
	
	private String checkNull(Object obj){
		if (obj == null || obj.equals("null")) {
			return null;
		} else {
			return obj.toString();
		}
	}
	
	public Integer getSequenceByName(String seqName) {
		Integer id=null;
		SequenceManager sm = SequenceManager.getInstance();
		Connection con = null;
		try 
		{
			con = connectionManager.getConnection();//jdbcTemplate.getDataSource().getConnection();
			id = sm.getSequenceByName(seqName, con);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connectionManager.closeConnection(con);
		}
		return id;
		
	}

	@Override
	public void insertSignature(Signature sign) throws Exception {
		Connection con = null;
		PreparedStatement st = null;
		con = connectionManager.getConnection();//jdbcTemplate.getDataSource().getConnection();
		String insertSQL =
				"INSERT INTO TD_EXPRESS_SIGNATURE("+
				 "ID, LOGISTICS, BATCH_NUMBER, SIGNATURE_IMG, AREA_CODE, SERVICE_SHOP_CODE, TYPE, EXPRESS_SERVICE_ID, OPERA_TIME, OPERATOR, REMARK)"+
			"VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        st=con.prepareStatement(insertSQL);
		st.setLong(1, sign.getId());
		st.setString(2, sign.getLogistics());
		st.setString(3, sign.getBatchNumber());
		st.setString(4, sign.getSignatureImg());
		st.setString(5, sign.getAreaCode());
		st.setString(6, sign.getServiceShopCode());
		st.setString(7, sign.getType().toString());
		st.setObject(8, null);
		st.setString(9, sign.getOperaTime());
		st.setString(10, sign.getOperator());
		st.setString(11, sign.getRemark());
		try 
		{
			st.execute();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connectionManager.closeStatement(st);
			connectionManager.closeConnection(con);
		}
		
	}

	@Override
	public Signature getSignatureByBatchNumber(String batchNumber, String type) throws Exception {
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement st = null;
		Map<String, Object> t = null;
		try 
		{
			con = connectionManager.getConnection();//jdbcTemplate.getDataSource().getConnection();
			st = con.prepareStatement("SELECT ID, SIGNATURE_IMG FROM TD_EXPRESS_SIGNATURE WHERE BATCH_NUMBER = ? AND TYPE = ?");
			st.setString(1, batchNumber);
			st.setString(2, type);
			rs = st.executeQuery();
			t = checkOneResultSet(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectionManager.closeResultSet(rs);
			connectionManager.closeStatement(st);
			connectionManager.closeConnection(con);
		}
		return MapToSignatureEntity(t);
	}
	
	private Signature MapToSignatureEntity(Map<String, Object> t){
		Signature sign = new Signature();
		try 
		{
			sign.setId(Integer.valueOf(t.get("ID").toString()));
			sign.setSignatureImg(checkNull(t.get("SIGNATURE_IMG")));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}catch (NullPointerException e) {
			sign.setRemark(e.getMessage());
		}
		return sign;
	}
	
}

package com.fla.common.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.fla.common.dao.interfaces.MerchandiseDaoInterface;
import com.fla.common.util.DateUtil;
import com.fla.common.util.Pagination;
import com.fla.common.util.connection.ConnectionManager;

import net.sf.json.JSONObject;

@Service
public class MerchandiseDao implements MerchandiseDaoInterface {

	@Autowired
	public ConnectionManager connectionManager;
	
	@Autowired
	public JdbcTemplate jdbcTemplate;
	
	public MerchandiseDao() {
	}
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	
	@Override
	public Pagination getMerchandiseList(int rowSize, int pageSize,Map<String, String> params) throws SQLException {
		Pagination page =null;
		String sql = new  String();
		try 
		{
			sql = ""
					+ " select ID, NAME, BARCODE, AREA_CODE, SHOP_CODE, "
					+ " VALIDITY, date_format(PRODUCTION_DATE,'%Y-%c-%d') PRODUCTION_DATE, PLACE_ORIGIN, "
					+ " PRODUCTION_IMAGE, INVENTORY, UNIT_PRICE, "
					+ " MARKETING_ACTIVITIES, BATCH_NUMBER, DESCRIPTION, "
					+ " BRAND, MANUFACTURER, SPECIFICATION, OPERA_TIME, REMARK ,VERSION "
					+ " from TD_MERCHANDISE_INFO ";
			page=new Pagination(sql.toString(),pageSize,rowSize,getJdbcTemplate());
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return page;
	}

	@Override
	public JSONObject addMerchandise(Map<String, String> params) throws SQLException {
		JSONObject json = new JSONObject();
		json.put("msg", "新增成功");
		Connection con = null;
		PreparedStatement st = null;
		con = connectionManager.getConnection();
		String insertSQL =
				"INSERT INTO td_merchandise_info("+
				 "NAME, BARCODE, AREA_CODE, SHOP_CODE, VALIDITY, PRODUCTION_DATE, PLACE_ORIGIN, PRODUCTION_IMAGE, INVENTORY, UNIT_PRICE, MARKETING_ACTIVITIES, BATCH_NUMBER, DESCRIPTION, BRAND, MANUFACTURER, SPECIFICATION, OPERA_TIME, REMARK, VERSION)"+
			"VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        st=con.prepareStatement(insertSQL);
		st.setString(1, params.get("name"));
		st.setString(2, params.get("barcode"));
		st.setString(3, params.get("areaCode"));
		st.setString(4, params.get("shopCode"));
		st.setString(5,null );//params.get("validity")
		st.setString(6, params.get("productionDate"));
		st.setString(7, params.get("placeOrigin"));
		st.setString(8, params.get("productionImage"));
		st.setString(9, null);//params.get("inventory")
		st.setString(10, params.get("unitPrice"));
		st.setString(11,params.get("marketingActivities"));
		st.setString(12, params.get("batchNumber"));
		st.setString(13, params.get("description"));
		st.setString(14, params.get("brand"));
		st.setString(15, params.get("manufacturer"));
		st.setString(16, params.get("specification"));
		st.setString(17, DateUtil.formatDateToString(new Date()));
		st.setString(18, params.get("remark"));
		st.setString(19, params.get("version"));
		
		try 
		{
			st.execute();
		} catch (Exception e) {
			json.put("msg", e.getMessage());
			e.printStackTrace();
		} finally {
			connectionManager.closeStatement(st);
			connectionManager.closeConnection(con);
		}
		return json;
	}

	
	
	@Override
	public JSONObject modifyMerchandiseInfo(Map<String, String> params)
			throws SQLException {
		JSONObject json = new JSONObject();
		json.put("msg", "修改成功");
		Connection con = null;
		PreparedStatement st = null;
		con = connectionManager.getConnection();
		String modifySQL =
				" update TD_MERCHANDISE_INFO set NAME=?,UNIT_PRICE=?,PRODUCTION_DATE=?,DESCRIPTION=?,REMARK=?"+
				 " where ID = ?";
        st=con.prepareStatement(modifySQL);
		st.setString(1, params.get("name"));
		st.setString(2, params.get("unitPrice"));
		st.setString(3, params.get("productionDate"));
		st.setString(4, params.get("description"));
		st.setString(5, params.get("remark"));
		st.setString(6, params.get("id"));
		
		try 
		{
			st.execute();
		} catch (Exception e) {
			json.put("msg", e.getMessage());
			e.printStackTrace();
		} finally {
			connectionManager.closeStatement(st);
			connectionManager.closeConnection(con);
		}
		return json;
		
	}

	private String checkStartDate(String startDate){
		if (startDate == null || startDate.equals("")) {
			startDate = DateUtil.dateAddToString(new Date(), Calendar.YEAR, -10);
		}
			return startDate;
		}
		
		private String checkEndDate(String endDate){
			if (endDate == null || endDate.equals("")) {
				endDate = DateUtil.dateAddToString(new Date(), Calendar.YEAR, 0);
			}
			return endDate;
	}
	
}

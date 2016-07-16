package com.fla.common.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.fla.common.dao.interfaces.ScanneDaoInterface;
import com.fla.common.entity.ScanneInfo;
import com.fla.common.entity.SystemUser;
import com.fla.common.util.Pagination;
import com.fla.common.util.ResultSetUtils;
import com.fla.common.util.connection.ConnectionManager;

@Service
public class ScanneDao implements ScanneDaoInterface {

	@Autowired
	public ConnectionManager connectionManager;
	
	@Autowired
	public JdbcTemplate jdbcTemplate;
	
	public ScanneDao() {
	}
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	
	@Override
	public Pagination getScanneList(int rowSize, int pageSize, Map<String, String> params) throws SQLException {
		Pagination page = null;
		StringBuilder sql = new StringBuilder();
		sql.append("select * from tf_scanne_info a  where a.SHOP_CODE = '"+params.get("shopCode")+"'");
		page = new Pagination(sql.toString(), pageSize, rowSize,getJdbcTemplate());
		return page;
	}

	@Override
	public ScanneInfo getScanneByShopCode(Map<String, String> params) {
		JSONObject json = new JSONObject();
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement st = null;
		Map<String, Object> t = null;
		try 
		{
			con = connectionManager.getConnection();
			st = con.prepareStatement("select * from tf_scanne_info a  where a.SHOP_CODE = ?");
			st.setString(1, params.get("shopCode"));
			rs = st.executeQuery();
			t = ResultSetUtils.checkOneResultSet(rs);
			json = JSONObject.fromObject(t);  
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectionManager.closeResultSet(rs);
			connectionManager.closeStatement(st);
			connectionManager.closeConnection(con);
		}
		
		if (json.size() !=0) {
			ScanneInfo si = new ScanneInfo();
			si.setId(json.getInt("ID"));
			si.setLoginName(json.getString("LOGIN_NAME"));
			si.setRemark(json.getString("REMARK"));
			si.setScanneCode(json.getString("SCANNE_CODE"));
			si.setScanneName(json.getString("SCANNE_NAME"));
			si.setScanneType(json.getString("SCANNE_TYPE"));
			si.setShopCode(json.getString("SHOP_CODE"));
			return si;
		} else {
			return null;
		}
		
	}
	
	@Override
	public SystemUser getSystemUserByLoginName(Map<String, String> params) {
		JSONObject json = new JSONObject();
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement st = null;
		Map<String, Object> t = null;
		try 
		{
			con = connectionManager.getConnection();
			st = con.prepareStatement("select * from tf_system_user a  where a.LOGIN_NAME = ?");
			st.setString(1, params.get("loginName"));
			rs = st.executeQuery();
			t = ResultSetUtils.checkOneResultSet(rs);
			json = JSONObject.fromObject(t);  
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connectionManager.closeResultSet(rs);
			connectionManager.closeStatement(st);
			connectionManager.closeConnection(con);
		}
		
		if (json.size() !=0) {
			SystemUser user = new SystemUser();
			user.setAreaCode(json.getString("AREA_CODE"));
//			user.setAreaName(json.getString("AREA_NAME"));
			user.setCreateDate(json.getString("CREATE_DATE"));
			user.setIsOpenIM(json.getString("OPEN_IM"));
			user.setLoginName(json.getString("LOGIN_NAME"));
			user.setNickName(json.getString("NICK_NAME"));
			user.setPhoneNumber(json.getString("PHONE_NUMBER"));
			user.setRemark(json.getString("REMARK"));
			user.setServiceShopCode(json.getString("SERVICE_SHOP_CODE"));
//			user.setShopName(json.getString("SHOP_NAME"));
			user.setType(json.getString("TYPE"));
			user.setUserMode(json.getString("USER_MODE"));
			return user;
		} else {
			return null;
		}
		
	}
	
	
	@Override
	public void insertScanne(ScanneInfo si) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void modifyScanne(ScanneInfo si) throws SQLException {
		// TODO Auto-generated method stub
		
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
	
	
}

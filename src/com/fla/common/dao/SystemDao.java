package com.fla.common.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.fla.common.dao.interfaces.SystemDaoInterface;
import com.fla.common.entity.ExpressServiceProvider;
import com.fla.common.entity.SystemArea;
import com.fla.common.entity.SystemShop;
import com.fla.common.entity.SystemUser;
import com.fla.common.util.DateUtil;
import com.fla.common.util.SequenceManager;
import com.fla.common.util.connection.ConnectionManager;

@Service
public class SystemDao implements SystemDaoInterface {
	
	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	@Autowired
	public ConnectionManager connectionManager;
	
	@Autowired
	public JdbcTemplate jdbcTemplate;
	
	public SystemDao() {
	}
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Map<String, Object>> getAreaInfoList(int rowSize, int pageSize,Map<String, String> params) throws SQLException {
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement st = null;
		List<Map<String, Object>> t = null;
		try 
		{
			con = connectionManager.getConnection();//jdbcTemplate.getDataSource().getConnection();
			st = con.prepareStatement("select ID,NAME,CODE,TYPE,REMARK from TF_AREA_INFO");
//			st.setString(1, areaCode);
			rs = st.executeQuery();
			t = checkResultSet(rs);
		} finally {
			connectionManager.closeResultSet(rs);
			connectionManager.closeStatement(st);
			connectionManager.closeConnection(con);
		}
		return t;
	}

	@Override
	public List<Map<String, Object>> getAreaInfoList(Map<String, String> params) throws SQLException {
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement st = null;
		List<Map<String, Object>> t = null;
		try 
		{
			con = connectionManager.getConnection();//jdbcTemplate.getDataSource().getConnection();
			String sql = "select ID,NAME,CODE,TYPE,REMARK from TF_AREA_INFO";
			String loginName = params.get("loginName");
			String appendSql = "";
			if (!loginName.equals("admin")) {
				appendSql = " where CODE='"+params.get("areaCode")+"'";
			} 
			sql = sql + appendSql;
			st = con.prepareStatement(sql);
			rs = st.executeQuery();
			t = checkResultSet(rs);
		} finally {
			connectionManager.closeResultSet(rs);
			connectionManager.closeStatement(st);
			connectionManager.closeConnection(con);
		}
		return t;
	}

	@Override
	public List<Map<String, Object>> getShopInfoList(int rowSize, int pageSize,Map<String, String> params) throws SQLException {
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement st = null;
		List<Map<String, Object>> t = null;
		try 
		{
			con = connectionManager.getConnection();//jdbcTemplate.getDataSource().getConnection();
			String sql = "select a.ID, a.SHOP_CODE,a.NAME,trim(b.NAME) as AREA_NAME,a.TYPE, a.AREA_CODE,a.SHOP_ADDRESS,a.SHOP_CONTACTS from tf_shop_info a,tf_area_info b where a.AREA_CODE = b.CODE";
			String loginName = params.get("loginName");
			String appendSql = "";
			if (!loginName.equals("admin")) {
				appendSql = " and a.AREA_CODE='"+params.get("areaCode")+"'";
			} 
			sql = sql + appendSql;
			st = con.prepareStatement(sql);
			rs = st.executeQuery();
			t = checkResultSet(rs);
		} finally {
			connectionManager.closeResultSet(rs);
			connectionManager.closeStatement(st);
			connectionManager.closeConnection(con);
		}
		return t;
	}
	
	@Override
	public List<Map<String, Object>> getShopInfoList(Map<String, String> params) throws SQLException {
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement st = null;
		List<Map<String, Object>> t = null;
		try 
		{
			con = connectionManager.getConnection();//jdbcTemplate.getDataSource().getConnection();
			String sql = "select a.ID, a.SHOP_CODE,a.NAME,trim(b.NAME) as AREA_NAME,a.TYPE, a.AREA_CODE from tf_shop_info a,tf_area_info b where a.AREA_CODE = b.CODE";
			String loginName = params.get("loginName");
			String appendSql = "";
			if (!loginName.equals("admin")) {
				appendSql = " and a.AREA_CODE='"+params.get("areaCode")+"'";
			} 
			sql = sql + appendSql;
			st = con.prepareStatement(sql);
//			st.setString(1, areaCode);
			rs = st.executeQuery();
			t = checkResultSet(rs);
		} finally {
			connectionManager.closeResultSet(rs);
			connectionManager.closeStatement(st);
			connectionManager.closeConnection(con);
		}
		return t;
	}
	
	@Override
	public List<Map<String, Object>> getUserInfoList(int rowSize, int pageSize,Map<String, String> params) throws SQLException {
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement st = null;
		List<Map<String, Object>> t = null;
		try 
		{
			con = connectionManager.getConnection();//jdbcTemplate.getDataSource().getConnection();
			String sql=""
					+ " select a.ID, a.LOGIN_NAME, a.PASSWORD, a.AREA_CODE, a.NICK_NAME, "
					+ " a.SERVICE_SHOP_CODE, a.REMARK, a.TYPE,trim(b.NAME) as SERVICE_SHOP_NAME,a.PHONE_NUMBER,CREATE_DATE,OPEN_IM"
					+" from tf_system_user a,tf_shop_info b where a.SERVICE_SHOP_CODE = b.SHOP_CODE and a.LOGIN_NAME <> 'admin' ";
			String loginName = params.get("loginName");
			String appendSql = "";
			if (!loginName.equals("admin")) {
				appendSql = " and b.AREA_CODE='"+params.get("areaCode")+"'";
			} 
			sql = sql + appendSql;
//			st.setString(1, params.get("areaCode"));
			st = con.prepareStatement(sql);
			rs = st.executeQuery();
			t = checkResultSet(rs);
		} finally {
			connectionManager.closeResultSet(rs);
			connectionManager.closeStatement(st);
			connectionManager.closeConnection(con);
		}
		return t;
	}
	
	@Override
	public List<Map<String, Object>> getConfigInfoList(int rowSize, int pageSize,Map<String, String> params) throws SQLException {
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement st = null;
		List<Map<String, Object>> t = null;
		try 
		{
			con = connectionManager.getConnection();
			st = con.prepareStatement(""
					+ " select a.ID, a.CONFIG_CODE, a.CONFIG_NAME, a.AREA_CODE, a.SERVICE_SHOP_CODE,"
					+ " a.VAlUE_TYPE,a.REMARK,a.TYPE,a.MODULE,a.SYSTEM_INITIALIZATION,b.name SHOP_NAME"
					+ " from tf_system_config a,tf_shop_info b where a.SERVICE_SHOP_CODE = b.SHOP_CODE");
//			st.setString(1, areaCode);
			rs = st.executeQuery();
			t = checkResultSet(rs);
		} finally {
			connectionManager.closeResultSet(rs);
			connectionManager.closeStatement(st);
			connectionManager.closeConnection(con);
		}
		return t;
		
	}
	
	@Override
	public List<Map<String, Object>> getConfigValuesList(Map<String, String> params) {
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement st = null;
		List<Map<String, Object>> t = null;
		try 
		{
			con = connectionManager.getConnection();
			st = con.prepareStatement("select trim(ID) VAlUE_ID,VAlUE,VAlUE_TYPE,CONFIG_ID,trim(VAlUE_NAME) as CONFIG_NAME, ORDER_NUMBER from tf_system_config_values where CONFIG_ID=? order by ORDER_NUMBER");
			st.setInt(1, Integer.valueOf(params.get("configId")));
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
	public List<Map<String, Object>> getAllConfigValues(Map<String, String> params) {
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement st = null;
		List<Map<String, Object>> t = null;
		try 
		{
			con = connectionManager.getConnection();
			st = con.prepareStatement("SELECT ID, "
					+ "CONFIG_ID, VAlUE, VAlUE_TYPE, "
					+ "CONFIG_CODE, CONFIG_NAME, "
					+ "ORDER_NUMBER, REMARK, STATUS, SERVICE_SHOP_CODE "
					+ "FROM TF_SYSTEM_CONFIG_VALUES "
					+ "WHERE STATUS=1 ORDER BY ORDER_NUMBER");
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
	public void modifyAreaInfo(SystemArea area) throws SQLException {
		Connection con = null;
		PreparedStatement st = null;
		String sql = "update tf_area_info set NAME = ?, TYPE=? where ID=?";
		con = connectionManager.getConnection();
        st=con.prepareStatement(sql);
		st.setString(1,area.getName());
		st.setString(2,area.getType());
		st.setLong(3, area.getId());
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

	@Override
	public void modifyShopInfo(SystemShop shop)
			throws SQLException {
		Connection con = null;
		PreparedStatement st = null;
		String sql = "update tf_shop_info set NAME = ?, TYPE=?, AREA_CODE =?, SHOP_ADDRESS =?,SHOP_CONTACTS=?"
				+ "where ID=?";
		con = connectionManager.getConnection();
        st=con.prepareStatement(sql);
		st.setString(1,shop.getName());
		st.setString(2,shop.getType());
		st.setString(3,shop.getAreaCode());
		st.setString(4,shop.getShopAddress());
		st.setString(5,shop.getShopContacts());
		st.setLong(6, shop.getId());
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

	@Override
	public void modifyUserInfo(SystemUser user)
			throws SQLException {
		Connection con = null;
		PreparedStatement st = null;
		String sql = "update tf_system_user set PASSWORD = ?, NICK_NAME=?, SERVICE_SHOP_CODE =?, TYPE = ?,PHONE_NUMBER=?,LOGIN_NAME = ?,OPEN_IM=? "
				+ "where ID=?";
		con = connectionManager.getConnection();
        st=con.prepareStatement(sql);
		st.setString(1,user.getPassword());
		st.setString(2,user.getNickName());
		st.setString(3,user.getServiceShopCode());
		st.setString(4,user.getType());
		st.setString(5,user.getPhoneNumber());
		st.setString(6, user.getLoginName());
		st.setString(7, user.getIsOpenIM());
		st.setLong(8, user.getId());
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
	
	@Override
	public void modifyUserPassWord(SystemUser user)	throws SQLException {
		Connection con = null;
		PreparedStatement st = null;
		String sql = "update tf_system_user set PASSWORD = ? where LOGIN_NAME=?";
		con = connectionManager.getConnection();
        st=con.prepareStatement(sql);
		st.setString(1,user.getPassword());
//		st.setString(2,user.getNickName());
//		st.setString(3,user.getPhoneNumber());
		st.setString(2, user.getLoginName());
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
	
	@Override
	public void modifyUserInfo(SystemUser user,String tag)
			throws SQLException {
		Connection con = null;
		PreparedStatement st = null;
		String sql = "update tf_system_user set NICK_NAME=?, SERVICE_SHOP_CODE =?, TYPE = ?,PHONE_NUMBER=?,OPEN_IM=? "
				+ "where ID=?";
		con = connectionManager.getConnection();
        st=con.prepareStatement(sql);
		st.setString(1,user.getNickName());
		st.setString(2,user.getServiceShopCode());
		st.setString(3,user.getType());
		st.setString(4,user.getPhoneNumber());
		st.setString(5, user.getIsOpenIM());
		st.setLong(6, user.getId());
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

	@Override
	public void insertAreaInfoList(SystemArea area, Map<String, String> params)
			throws SQLException {
		Connection con = null;
		PreparedStatement st = null;
		con = connectionManager.getConnection();
		con.setAutoCommit(false);
		String insertSQL = "INSERT INTO tf_area_info(ID, NAME, CODE, TYPE, REMARK) VALUES(?, ?, ?, ?, ?)";
		st = con.prepareStatement(insertSQL);
		Map<String, Object> m = getNewAreaCode();
		String newAreaCode = m.get("newAreaCode").toString();
		st.setLong(1,  area.getId());
		st.setString(2, area.getName());
		st.setString(3, newAreaCode);
		st.setString(4, area.getType());
		st.setString(5, area.getRemark());
		
		try 
		{
			st.execute();
			con.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connectionManager.closeStatement(st);
			connectionManager.closeConnection(con);
		}
	}
	
	public void insertLocationCodeByAreaCode(String shopCode, Map<String, String> params)
			throws SQLException {
		Connection con = null;
		PreparedStatement st = null;
		con = connectionManager.getConnection();
		List<String> keys = new ArrayList<String>(4);
		keys.add("seq_locationCodeByExpressType_S");
		keys.add("seq_locationCodeByExpressType_X");
		keys.add("seq_locationCodeByExpressType_D");
		keys.add("seq_locationCodeByExpressType_H");
		keys.add("seq_locationCodeByExpressType_Q");
		keys.add("seq_locationCodeByExpressType_R");
		keys.add("seq_locationCodeByExpressType_Y");
		
		String insertSQL = "INSERT INTO sequence(NAME, CURRENT_VALUE, INCREMENT, MODULE, CYCLE, MAX_VALUE, SHOP_CODE) VALUES(?, ?, ?, ?, ?, ?, ?)";
		st = con.prepareStatement(insertSQL);
		for (String k: keys) {
			st.setString(1, k);
			st.setInt(2, 1);
			st.setInt(3, 1);
			st.setString(4, null);
			st.setInt(5, 1);
			st.setInt(6, 999);
			st.setString(7, shopCode);
			st.addBatch();
		}
		try 
		{
			st.executeBatch();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connectionManager.closeStatement(st);
			connectionManager.closeConnection(con);
		}
	}

	@Override
	public void insertShopInfoList(SystemShop shop, Map<String, String> params)
			throws SQLException {
		Connection con = null;
		PreparedStatement st = null;
		con = connectionManager.getConnection();
		String insertSQL =
			"INSERT INTO tf_shop_info(ID, SHOP_CODE, NAME, TYPE, AREA_CODE, SHOP_ADDRESS, SHOP_CONTACTS, REMARK)"+
			"VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        st=con.prepareStatement(insertSQL);
    Map<String, Object> m = getNewShopCode();
    String newShopCode = m.get("newShopCode").toString();
		st.setLong(1, shop.getId());
		st.setString(2, newShopCode);
		st.setString(3, shop.getName());
		st.setString(4, shop.getType());
		st.setString(5, shop.getAreaCode());
		st.setString(6, shop.getShopAddress());
		st.setString(7, shop.getShopContacts());
		st.setString(8, shop.getRemark());
		
		try 
		{
			st.execute();
			insertLocationCodeByAreaCode(newShopCode, params);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connectionManager.closeStatement(st);
			connectionManager.closeConnection(con);
		}
	}

	@Override
	public void insertUserInfoList(SystemUser user, Map<String, String> params) throws SQLException {
		Connection con = null;
		PreparedStatement st = null;
		con = connectionManager.getConnection();
		String insertSQL =
			"INSERT INTO tf_system_user(ID, LOGIN_NAME, PASSWORD, AREA_CODE, NICK_NAME, SERVICE_SHOP_CODE, REMARK, TYPE, PHONE_NUMBER,OPEN_IM,CREATE_DATE)"+
			"VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?,?,?)";
        st=con.prepareStatement(insertSQL);
		st.setLong(1, user.getId());
		st.setString(2, user.getLoginName());
		st.setString(3, user.getPassword());
		st.setString(4, user.getAreaCode());
		st.setString(5, user.getNickName());
		st.setString(6, user.getServiceShopCode());
		st.setString(7, user.getRemark());
		st.setString(8, user.getType());
		st.setString(9, user.getPhoneNumber());
		st.setString(10, user.getIsOpenIM());
		st.setString(11, user.getCreateDate());
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
	public void deleteAreaInfoList(Integer id, Map<String, String> params)
			throws SQLException {
		
	}

	@Override
	public void deleteShopInfoList(Integer id, Map<String, String> params)
			throws SQLException {
		
	}

	@Override
	public void deleteUserInfoList(Integer id, Map<String, String> params)
			throws SQLException {
		
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

	@Override
	public Map<String, Object> getNewShopCode() throws SQLException {
		SequenceManager sm = SequenceManager.getInstance();
		Integer temporaryId = null;
		Connection con = null;
		Map<String, Object> v = new HashMap<String, Object>();
		con = connectionManager.getConnection();// jdbcTemplate.getDataSource().getConnection();
		temporaryId = sm.getTemporaryStorage(con, "seq_temporary_newShopCode");
		String newShopCode = temporaryId.toString();
		String value = StringUtils.leftPad(newShopCode, 4, "0");
		v.put("newShopCode", value);
		connectionManager.closeConnection(con);
		return v;
	}

	@Override
	public Map<String, Object> getNewAreaCode() throws SQLException {
		SequenceManager sm = SequenceManager.getInstance();
		Integer temporaryId = null;
		Connection con = null;
		Map<String, Object> v = new HashMap<String, Object>();
		con = connectionManager.getConnection();// jdbcTemplate.getDataSource().getConnection();
		temporaryId = sm.getTemporaryStorage(con, "seq_temporary_newAreaCode");
		String newAreaCode = temporaryId.toString();
		String value = StringUtils.leftPad(newAreaCode, 4, "0");
		v.put("newAreaCode", value);
		connectionManager.closeConnection(con);
		return v;
	}
	
	@Override
	public Map<String, Object> checkLoginNameUniqueness(String loginName) throws SQLException {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement st = null;
		Map<String, Object>  t = null;
		con = connectionManager.getConnection();
		String insertSQL ="select * from tf_system_user where LOGIN_NAME=?";
        st=con.prepareStatement(insertSQL);
		st.setString(1,loginName);
		try 
		{
			rs = st.executeQuery();
			t = checkOneResultSet(rs);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connectionManager.closeResultSet(rs);
			connectionManager.closeStatement(st);
			connectionManager.closeConnection(con);
		}
		return t;
		
	}
	
	@Override
	public String getShopAreaCode(String shopCode) throws SQLException {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement st = null;
		Map<String, Object>  t = null;
		con = connectionManager.getConnection();
		String insertSQL ="SELECT AREA_CODE FROM tf_shop_info where SHOP_CODE = ?";
        st=con.prepareStatement(insertSQL);
		st.setString(1,shopCode);
		try 
		{
			rs = st.executeQuery();
			t = checkOneResultSet(rs);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connectionManager.closeResultSet(rs);
			connectionManager.closeStatement(st);
			connectionManager.closeConnection(con);
		}
		String code = t.get("AREA_CODE").toString();
		return code;
		
	}

	
	
	@Override
	public List<Map<String, Object>> queryAreaInfos(String queryParams)
			throws SQLException {
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement st = null;
		List<Map<String, Object>> t = null;
		try 
		{
			con = connectionManager.getConnection();
			st = con.prepareStatement("select ID,NAME,CODE,TYPE,REMARK from TF_AREA_INFO where NAME like '%"+queryParams+"%' ");
//			st.setString(1, queryParams);
			rs = st.executeQuery();
			t = checkResultSet(rs);
		} finally {
			connectionManager.closeResultSet(rs);
			connectionManager.closeStatement(st);
			connectionManager.closeConnection(con);
		}
		return t;
	}

	@Override
	public List<Map<String, Object>> queryShopInfos(String queryParams)
			throws SQLException {
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement st = null;
		List<Map<String, Object>> t = null;
		try 
		{
			con = connectionManager.getConnection();
			st = con.prepareStatement("select a.ID, a.SHOP_CODE,a.NAME,trim(b.NAME) as AREA_NAME,a.TYPE, a.AREA_CODE,a.SHOP_ADDRESS,a.SHOP_CONTACTS from tf_shop_info a,tf_area_info b "
					+ "where a.AREA_CODE = b.CODE and a.NAME like '%"+queryParams+"%' ");
//			st.setString(1, queryParams);
			rs = st.executeQuery();
			t = checkResultSet(rs);
		} finally {
			connectionManager.closeResultSet(rs);
			connectionManager.closeStatement(st);
			connectionManager.closeConnection(con);
		}
		return t;
	}

	@Override
	public List<Map<String, Object>> queryUserInfos(String queryParams)
			throws SQLException {
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement st = null;
		List<Map<String, Object>> t = null;
		try 
		{
			con = connectionManager.getConnection();//jdbcTemplate.getDataSource().getConnection();
			st = con.prepareStatement(""
					+ " select a.ID, a.LOGIN_NAME, a.PASSWORD, a.AREA_CODE, a.NICK_NAME, "
					+ " a.SERVICE_SHOP_CODE, a.REMARK, a.TYPE,trim(b.NAME) as SERVICE_SHOP_NAME,a.PHONE_NUMBER"
					+" from tf_system_user a,tf_shop_info b where a.SERVICE_SHOP_CODE = b.SHOP_CODE and a.LOGIN_NAME <> 'admin' and a.LOGIN_NAME like '%"+queryParams+"%' ");
//			st.setString(1, queryParams);
			rs = st.executeQuery();
			t = checkResultSet(rs);
		} finally {
			connectionManager.closeResultSet(rs);
			connectionManager.closeStatement(st);
			connectionManager.closeConnection(con);
		}
		return t;
	}

	public static void main(String[] args) {
		System.out.println(StringUtils.leftPad("12", 4, "0"));
	}

	@Override
	public synchronized Map<String, Object> getLocationCodeByExpressType(String type, String shopCode)
			throws SQLException {
		SequenceManager sm = SequenceManager.getInstance();
		Integer temporaryId = null;
		Connection con = null;
		Map<String, Object> v = new HashMap<String, Object>();
		con = connectionManager.getConnection();// jdbcTemplate.getDataSource().getConnection();
		do 
		{
			temporaryId = sm.getTemporaryStorage(con, "seq_locationCodeByExpressType_"+type,shopCode);
			if (temporaryId==0) {
				break;
			}
		} while (this.checkLocationCode(type+StringUtils.leftPad(temporaryId.toString(), 3, "0"), shopCode));
		String newShopCode = temporaryId.toString();
		String value = StringUtils.leftPad(newShopCode, 3, "0");
		v.put("temporaryId", value);
		connectionManager.closeConnection(con);
		return v;
	}
	
	public synchronized boolean checkLocationCode(String locationCode,String shopCode) throws SQLException {
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement st = null;
		try 
		{
			con = connectionManager.getConnection();
			st = con.prepareStatement("SELECT EXPRESS_lOCATION FROM tf_express_info where EXPRESS_lOCATION = ? and SERVICE_SHOP_CODE = ?");
			st.setString(1, locationCode);
			st.setString(2, shopCode);
			rs = st.executeQuery();
			rs.last();
			int rowCount = rs.getRow();
			if (rowCount ==0) {
				return false;
			} else {
				return true;
			}
		} finally {
			connectionManager.closeResultSet(rs);
			connectionManager.closeStatement(st);
			connectionManager.closeConnection(con);
		}
	}

	@Override
	public List<Map<String, Object>> getExpressServiceProviderList(
			String areaCode, String shopCode) throws SQLException {
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement st = null;
		List<Map<String, Object>> t = null;
		try 
		{
			con = connectionManager.getConnection();//jdbcTemplate.getDataSource().getConnection();
			st = con.prepareStatement("select  *  from tf_express_service_provider_info");
//			st.setString(1, areaCode);
//			st.setString(2, shopCode);
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
	public void insertExpressServiceProvider(ExpressServiceProvider esp, Map<String, String> params) throws SQLException {
		Connection con = null;
		PreparedStatement st = null;
		con = connectionManager.getConnection();
		String insertSQL =
			"INSERT INTO tf_express_service_provider_info(ID, NAME, CODE, REMARK, LOGO, TYPE, ORDER_BY, CONTACTS, PHONE_NUMBER)"+
			"VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        st=con.prepareStatement(insertSQL);
		st.setLong(1, esp.getId());
		st.setString(2, esp.getName());
		st.setString(3, esp.getCode());
		st.setString(4, esp.getRemark());
		st.setString(5, esp.getLogo());
		st.setString(6, esp.getType());
		st.setString(7, esp.getOrderBy());
		st.setString(8, esp.getContacts());
		st.setString(9, esp.getPhoneNumber());
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
	public void modifyExpressServiceProvider(ExpressServiceProvider esp)
			throws SQLException {
		Connection con = null;
		PreparedStatement st = null;
		String sql = "update tf_express_service_provider_info set  NAME=?, CODE=?, REMARK=?, LOGO=?, TYPE=?, ORDER_BY=?, CONTACTS=?, PHONE_NUMBER=?"
				+ "where ID=?";
		con = connectionManager.getConnection();
        st=con.prepareStatement(sql);
		st.setString(1, esp.getName());
		st.setString(2, esp.getCode());
		st.setString(3, esp.getRemark());
		st.setString(4, esp.getLogo());
		st.setString(5, esp.getType());
		st.setString(6, esp.getOrderBy());
		st.setString(7, esp.getContacts());
		st.setString(8, esp.getPhoneNumber());
		st.setLong(9, esp.getId());
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

	@Override
	public List<Map<String, Object>> getExpressStatisticalArea(Map<String, Object> params) {
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement st = null;
		List<Map<String, Object>> t = null;
		try 
		{
			con = connectionManager.getConnection();//jdbcTemplate.getDataSource().getConnection();
			String sql = "select a.* from tf_area_info a";
			String loginName = (String) params.get("loginName");
			String appendSql = "";
			if (!loginName.equals("admin")) {
				appendSql = "  where a.CODE='"+params.get("areaCode")+"'";
			} 
			sql = sql + appendSql;
			st = con.prepareStatement(sql);
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
	public List<Map<String, Object>> getShopGroupByArea(Map<String, Object> params) {
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement st = null;
		List<Map<String, Object>> t = null;
		try 
		{
			con = connectionManager.getConnection();//jdbcTemplate.getDataSource().getConnection();
			String sql = "select a.* from tf_area_info a";
			st = con.prepareStatement(sql);
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
	public List<Map<String, Object>> getAreaChildrenShops(String areaCode) throws SQLException {
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement st = null;
		List<Map<String, Object>> t = null;
		try 
		{
			con = connectionManager.getConnection();
			st = con.prepareStatement("select  * from tf_shop_info a where a.AREA_CODE = ?");
			st.setString(1, areaCode);
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
	public List<Map<String, Object>> getShopNumberOfPeopleGroupCount(String type,String code) throws SQLException {
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement st = null;
		List<Map<String, Object>> t = null;
		String sql = new String();
		String sqlArea ="select b.name,count(1) COUNT from tf_customer_info a "+
				"	left join tf_shop_info b on a.SERVICE_SHOP_CODE = b.shop_code  where a.AREA_CODE =?"+ 
				" group by SERVICE_SHOP_CODE";
		
		String sqlShop ="select b.name,count(1) COUNT from tf_customer_info a "+
				"	left join tf_shop_info b on a.SERVICE_SHOP_CODE = b.shop_code"+
				"	where SERVICE_SHOP_CODE = ?"+
				"	group by SERVICE_SHOP_CODE";
		if (type.equals("A")) {
			sql = sqlArea;
		} else  if (type.equals("S")){
			sql = sqlShop;
		} else {
			return null;
		}
		
		try 
		{
			con = connectionManager.getConnection();
			st = con.prepareStatement(sql);
			st.setString(1, code);
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
	
	public String getCurrentQuarterStartTime() {
		Calendar c = Calendar.getInstance();
		int currentMonth = c.get(Calendar.MONTH) + 1;
		String now = null;
		try
		{
			if (currentMonth >= 1 && currentMonth <= 3) {
				c.set(Calendar.MONTH, 0);
			} else if (currentMonth >= 4 && currentMonth <= 6) {
				c.set(Calendar.MONTH, 3);
			} else if (currentMonth >= 7 && currentMonth <= 9) {
				c.set(Calendar.MONTH, 4);
			} else if (currentMonth >= 10 && currentMonth <= 12) {
				c.set(Calendar.MONTH, 9);
			} 
			c.set(Calendar.DATE, 1);
			now = sdf.format(c.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return now;
	}

	/**
	 * 
	 * @return
	 */
	public String getCurrentQuarterEndTime() {
		Calendar c = Calendar.getInstance();
		int currentMonth = c.get(Calendar.MONTH) + 1;
		String now = null;
		try 
		{
			if (currentMonth >= 1 && currentMonth <= 3) {
				c.set(Calendar.MONTH, 2);
				c.set(Calendar.DATE, 31);
			} else if (currentMonth >= 4 && currentMonth <= 6) {
				c.set(Calendar.MONTH, 5);
				c.set(Calendar.DATE, 30);
			} else if (currentMonth >= 7 && currentMonth <= 9) {
				c.set(Calendar.MONTH, 8);
				c.set(Calendar.DATE, 30);
			} else if (currentMonth >= 10 && currentMonth <= 12) {
				c.set(Calendar.MONTH, 11);
				c.set(Calendar.DATE, 31);
			}
			now = sdf.format(c.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return now;
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
	
	@Override
	public List<Map<String, Object>> getShopInAndSendExpressGroupCount(String type,String code,String startDate,String endDate) throws SQLException {
//		startDate = checkStartDate(startDate);
//		endDate = checkEndDate(endDate);
		
		startDate = getCurrentQuarterStartTime();
		endDate = getCurrentQuarterEndTime();
		
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement st = null;
		List<Map<String, Object>> t = null;
		String sql = new String();
		String sqlArea = "select c.NAME,"
				+ "	sum(IF(type = 'inExpress',eCount,0)) as 'ICOUNT',"
				+ " sum(IF(type = 'sendExpress',eCount,0)) as 'SCOUNT',"
				+ " sum(eCount) as 'TOTAL' "
				+ " from ("
				+ " select b.NAME,count(1) eCount,'inExpress' type"
				+ " from ("
				+ "	select PHONE_NUMBER,OPERA_TIME,AREA_CODE,SERVICE_SHOP_CODE from tf_express_out_storehouse where AREA_CODE = ? and OPERA_TIME >= '"+startDate+"' and OPERA_TIME <=  '"+endDate+"'"
				+ "	UNION ALL"
				+ "	select PHONE_NUMBER,OPERA_TIME,AREA_CODE,SERVICE_SHOP_CODE from tf_express_info where AREA_CODE =? and OPERA_TIME >=  '"+startDate+"' and OPERA_TIME <=  '"+endDate+"'"
				+ " ) a right join tf_shop_info b on a.SERVICE_SHOP_CODE = b.shop_code "
				+ "	where a.AREA_CODE = ? "
				+ " group by a.SERVICE_SHOP_CODE"
				+ "	UNION ALL"
				+ " select b.NAME,count(1) eCount ,'sendExpress' type"
				+ " from tf_sent_express_info a "
				+ " right join tf_shop_info b on a.SERVICE_SHOP_CODE = b.shop_code"
				+ " where a.AREA_CODE =? and OPERA_TIME >=  '"+startDate+"' and OPERA_TIME <=  '"+endDate+"'"
				+ "	group by SERVICE_SHOP_CODE ) c group by c.name";
		
		String sqlShop = "select c.NAME,"
				+ " sum(IF(type = 'inExpress',eCount,0)) as 'ICOUNT',"
				+ " sum(IF(type = 'sendExpress',eCount,0)) as 'SCOUNT',"
				+ " sum(eCount) as 'TOTAL' "
				+ " from ("
				+ " select b.NAME,count(1) eCount,'inExpress' type"
				+ " from ("
				+ "	select PHONE_NUMBER,OPERA_TIME,AREA_CODE,SERVICE_SHOP_CODE from tf_express_out_storehouse where SERVICE_SHOP_CODE = ? and OPERA_TIME >=  '"+startDate+"' and OPERA_TIME <=  '"+endDate+"'"
				+ "	UNION ALL"
				+ "	select PHONE_NUMBER,OPERA_TIME,AREA_CODE,SERVICE_SHOP_CODE from tf_express_info where SERVICE_SHOP_CODE = ? and OPERA_TIME >=  '"+startDate+"' and OPERA_TIME <=  '"+endDate+"'"
				+ " ) a right join tf_shop_info b on a.SERVICE_SHOP_CODE = b.shop_code "
				+ "	where SERVICE_SHOP_CODE = ? "
				+ " group by a.SERVICE_SHOP_CODE"
				+ "	UNION ALL"
				+ " select b.NAME,count(1) eCount ,'sendExpress' type"
				+ " from tf_sent_express_info a "
				+ " right join tf_shop_info b on a.SERVICE_SHOP_CODE = b.shop_code"
				+ " where SERVICE_SHOP_CODE = ? and OPERA_TIME >=  '"+startDate+"' and OPERA_TIME <=  '"+endDate+"'"
				+ "	group by SERVICE_SHOP_CODE ) c group by c.name";
		if (type.equals("A")) {
			sql = sqlArea;
		} else  if (type.equals("S")){
			sql = sqlShop;
		} else {
			return null;
		}
		
		try 
		{
			con = connectionManager.getConnection();
			st = con.prepareStatement(sql);
			st.setString(1, code);
			st.setString(2, code);
			st.setString(3, code);
			st.setString(4, code);
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
	public List<Map<String, Object>> getShopOutAndSendExpressGroupCount(Map<String, String> params) throws SQLException {
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement st = null;
		List<Map<String, Object>> t = null;
		String sql = new String();
		
		sql = "select "
		+ " c.TT, "
		+ " sum(IF(type = 'outExpress',eCount,0)) as 'OUTCOUNT',"
		+ " sum(IF(type = 'sendExpress',eCount,0)) as 'SENDCOUNT'," 
    + " sum(eCount) as 'TOTAL'  "
    + " from ( "
    + " select date_format(OUT_OPERA_TIME,'%d') TT,count(distinct PHONE_NUMBER) eCount,'outExpress' type"
    + " from tf_express_out_storehouse "
    + " where SERVICE_SHOP_CODE = ? "
    + " and date_format(OUT_OPERA_TIME,'%Y-%m') = ? "
    + " group by TT,SERVICE_SHOP_CODE"
    + " UNION ALL "
    + " select date_format(OPERA_TIME,'%d') TT,count(distinct PHONE_NUMBER) eCount,'sendExpress' type"
    + " from tf_sent_express_info "
    + " where SERVICE_SHOP_CODE = ? "
    + " and date_format(OPERA_TIME,'%Y-%m') = ? "
    + " group by TT,SERVICE_SHOP_CODE "
    + " ) c group by c.TT";
		
		try 
		{
			con = connectionManager.getConnection();
			st = con.prepareStatement(sql);
			st.setString(1, params.get("shopCode"));
			st.setString(2, params.get("limitTime"));
			st.setString(3, params.get("shopCode"));
			st.setString(4, params.get("limitTime"));
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
	public List<Map<String, Object>> getShopOutAndSendExpressDaily(Map<String, String> params) throws SQLException {
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement st = null;
		List<Map<String, Object>> t = null;
		String sql = new String();
		
		sql = "select "
		+ " c.TT, "
		+ " sum(IF(type = 'outExpress',eCount,0)) as 'OUTCOUNT',"
		+ " sum(IF(type = 'sendExpress',eCount,0)) as 'SENDCOUNT'," 
    + " sum(eCount) as 'TOTAL'  "
    + " from ( "
    + " select date_format(OUT_OPERA_TIME,'%d') TT,count(distinct PHONE_NUMBER) eCount,'outExpress' type"
    + " from tf_express_out_storehouse "
    + " where SERVICE_SHOP_CODE = ? "
    + " and date_format(OUT_OPERA_TIME,'%Y-%m') = ? "
    + " group by TT,SERVICE_SHOP_CODE"
    + " UNION ALL "
    + " select date_format(OPERA_TIME,'%d') TT,count(distinct PHONE_NUMBER) eCount,'sendExpress' type"
    + " from tf_sent_express_info "
    + " where SERVICE_SHOP_CODE = ? "
    + " and date_format(OPERA_TIME,'%Y-%m') = ? "
    + " group by TT,SERVICE_SHOP_CODE "
    + " ) c group by c.TT";
		
		try 
		{
			con = connectionManager.getConnection();
			st = con.prepareStatement(sql);
			st.setString(1, params.get("shopCode"));
			st.setString(2, params.get("limitTime"));
			st.setString(3, params.get("shopCode"));
			st.setString(4, params.get("limitTime"));
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
	public List<Map<String, Object>> getSendOutExpressByExpressGroup(Map<String, String> params) throws SQLException {
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement st = null;
		List<Map<String, Object>> t = null;
		String sql = new String();
		
		sql =  "select " 
		+ "c.TT,  "
    + "sum(IF(EXPRESS_SERVICE_ID = '2' and type = 'outExpress',eCount,0)) as 'SF', "
    + "sum(IF(EXPRESS_SERVICE_ID = '3' and type = 'outExpress',eCount,0)) as 'JD', "
    + "sum(IF(EXPRESS_SERVICE_ID = '4' and type = 'outExpress',eCount,0)) as 'YT', "
    + "sum(IF(EXPRESS_SERVICE_ID = '5' and type = 'outExpress',eCount,0)) as 'EMS', "
    + "sum(IF(EXPRESS_SERVICE_ID = '6' and type = 'outExpress',eCount,0)) as 'TTKD', "
    + "sum(IF(EXPRESS_SERVICE_ID = '7' and type = 'outExpress',eCount,0)) as 'ST', "
    + "sum(IF(EXPRESS_SERVICE_ID = '8' and type = 'outExpress',eCount,0)) as 'ZT', "
    + "sum(IF(EXPRESS_SERVICE_ID = '9' and type = 'outExpress',eCount,0)) as 'YD', "
   + " sum(IF(EXPRESS_SERVICE_ID = '10' and type = 'outExpress',eCount,0)) as 'ZTKY', "
   + " sum(IF(EXPRESS_SERVICE_ID = '11' and type = 'outExpress',eCount,0)) as 'ZJS', "
   + " sum(IF(EXPRESS_SERVICE_ID = '12' and type = 'outExpress',eCount,0)) as 'HT', "
   + " sum(IF(EXPRESS_SERVICE_ID = '13' and type = 'outExpress',eCount,0)) as 'YZ', "
   + "sum(IF(EXPRESS_SERVICE_ID = '14' and type = 'outExpress',eCount,0)) as 'KJ', "
    + "sum(IF(EXPRESS_SERVICE_ID = '15' and type = 'outExpress',eCount,0)) as 'YS', " 
    + "sum(IF(EXPRESS_SERVICE_ID = '16' and type = 'outExpress',eCount,0)) as 'QT', "
    + "sum(IF(EXPRESS_SERVICE_ID = '17' and type = 'outExpress',eCount,0)) as 'GT', "
   + " sum(IF(EXPRESS_SERVICE_ID = '18' and type = 'outExpress',eCount,0)) as 'WPH', "
    + "sum(IF(type = 'outExpress',eCount,0)) as 'TOTAL',"
    + "sum(IF(type = 'sendExpress',eCount,0)) as 'SENDCOUNT', "
   + " sum(IF(type = 'sendExpress',price,0))/100 as 'PRICE' "
+ "from (  "
  + "  select date_format(OUT_OPERA_TIME,'%d') TT,EXPRESS_SERVICE_ID,count(*) eCount,0 price,'outExpress' type "
  + "  from tf_express_out_storehouse "
   + " where SERVICE_SHOP_CODE = ? "
  + "  and date_format(OUT_OPERA_TIME,'%Y-%m') = ? "
  + "  group by TT,SERVICE_SHOP_CODE,EXPRESS_SERVICE_ID "
  + "  UNION ALL  "
   + " select date_format(OPERA_TIME,'%d') TT,EXPRESS_SERVICE_ID,count(*) eCount,price,'sendExpress' type "
  + "  from tf_sent_express_info  "
  + "  where SERVICE_SHOP_CODE = ? "
   + " and date_format(OPERA_TIME,'%Y-%m') = ? "
   + " group by TT,SERVICE_SHOP_CODE,EXPRESS_SERVICE_ID "
   + ") c group by c.TT";
		
		try 
		{
			con = connectionManager.getConnection();
			st = con.prepareStatement(sql);
			st.setString(1, params.get("shopCode"));
			st.setString(2, params.get("limitTime"));
			st.setString(3, params.get("shopCode"));
			st.setString(4, params.get("limitTime"));
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
	public String getShopNameByCode(Map<String, String> params) throws SQLException {
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement st = null;
		String name = "";
		try 
		{
			con = connectionManager.getConnection();//jdbcTemplate.getDataSource().getConnection();
			st = con.prepareStatement("select NAME from tf_shop_info where SHOP_CODE=?");
			st.setString(1, params.get("shopCode"));
			rs = st.executeQuery();
			while (rs.next()) {
				name =rs.getString(1);//index start with 1  
			}
		} finally {
			connectionManager.closeResultSet(rs);
			connectionManager.closeStatement(st);
			connectionManager.closeConnection(con);
		}
		return name;
	}

	@Override
	public void addServiceProviderContacts(Map<String, String> params) throws SQLException {
		Connection con = null;
		PreparedStatement st = null;
		con = connectionManager.getConnection();
		SequenceManager sm = SequenceManager.getInstance();
		Integer id = sm.getSequenceByName("seq_express_service_provider_contacts_id", con);
		String insertSQL =
			"INSERT INTO tf_express_service_provider_contacts(ID, NAME, PHONE_NUMBER, AREA_CODE, SERVICE_SHOP_CODE, TYPE, REMARK,PROVIDER_ID)"+
			"VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        st=con.prepareStatement(insertSQL);
		st.setLong(1, id);
		st.setString(2, params.get("providerContacts"));
		st.setString(3, params.get("providerPhoneNumber"));
		st.setString(4, params.get("areaCode"));
		st.setString(5, params.get("shopCode"));
		st.setString(6, null);
		st.setString(7, params.get("providerRemark"));
		st.setInt(8, Integer.valueOf(params.get("providerId")));
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
	public List<Map<String, Object>> queryExpressServiceProviderContactsList(String providerId, String shopCode) {
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement st = null;
		List<Map<String, Object>> t = null;
		try 
		{
			con = connectionManager.getConnection();
			st = con.prepareStatement("select * from tf_express_service_provider_contacts where PROVIDER_ID = ? and SERVICE_SHOP_CODE = ?");
			st.setString(1, providerId);
			st.setString(2, shopCode);
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
	public void modifyServiceProviderContacts(Map<String, String> params)
			throws SQLException {
		Connection con = null;
		PreparedStatement st = null;
		String sql = "update tf_express_service_provider_contacts set NAME = ?, PHONE_NUMBER=?, REMARK=? where ID=?";
		con = connectionManager.getConnection();
        st=con.prepareStatement(sql);
		st.setString(1,params.get("providerContacts"));
		st.setString(2,params.get("providerPhoneNumber"));
		st.setString(3, params.get("providerRemark"));
		st.setInt(4, Integer.parseInt(params.get("id")));
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

	@Override
	public void deleteProviderContactsById(Map<String, String> params) throws SQLException {
		Connection con = null;
		PreparedStatement st = null;
		String sql = "delete from tf_express_service_provider_contacts where ID=?";
		con = connectionManager.getConnection();
        st=con.prepareStatement(sql);
		st.setInt(1, Integer.parseInt(params.get("id")));
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

	@Override
	public List<Map<String, Object>> getSystemConfigInfo(Map<String, String> params) {
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement st = null;
		List<Map<String, Object>> t = null;
		try 
		{
			con = connectionManager.getConnection();
			st = con.prepareStatement("select * from tf_system_config a where a.STATUS = 1");
//			st.setString(1, params.get("configId"));
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
	public List<Map<String, Object>> getSystemConfigValues(Map<String, String> params) {
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement st = null;
		List<Map<String, Object>> t = null;
		try 
		{
			con = connectionManager.getConnection();
			st = con.prepareStatement("select * from tf_system_config_values a where a.CONFIG_ID = ?");
			st.setString(1, params.get("configId"));
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
	
	
}

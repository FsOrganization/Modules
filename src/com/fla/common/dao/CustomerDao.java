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
import org.springframework.transaction.annotation.Transactional;

import com.fla.common.dao.interfaces.CustomerDaoInterface;
import com.fla.common.entity.CustomerInfo;
import com.fla.common.util.Pagination;
import com.fla.common.util.ResultSetUtils;
import com.fla.common.util.SequenceManager;
import com.fla.common.util.connection.ConnectionManager;

@Service
public class CustomerDao implements CustomerDaoInterface {

	@Autowired
	public ConnectionManager connectionManager;
	
	@Autowired
	public JdbcTemplate jdbcTemplate;
	
	public CustomerDao() {
	}
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	
	@Override
	public Pagination getCustomerList(int rowSize, int pageSize,
			Map<String, String> params) throws SQLException {
		Pagination page = null;
		StringBuilder sql = new StringBuilder();
		sql.append(""
				+ " select a.ID,a.NAME,a.GENDER,a.WEIXIN_ID,a.ADDRESS,"
				+ " a.AGE_SECTION,a.SERVICE_SHOP_CODE,"
				+ " a.WHETHER_HAVE_CAR,a.PHONE_NUMBER,"
				+ " a.AREA_CODE,IS_INTEREST "
//				+ " IFNULL(b.eCount,0) eCount,IFNULL(c.sCount,0) sCount"
				+ "	from tf_customer_info a "
//				+ "	left join ( "
//				+ "	select PHONE_NUMBER,sum(eCount) eCount"
//				+ "	from ("
//				+ "			select PHONE_NUMBER,count(1) eCount  from tf_express_info a group by PHONE_NUMBER"
//				+ "			UNION ALL"
//				+ "			select PHONE_NUMBER,count(1) eCount from tf_express_out_storehouse a group by PHONE_NUMBER"
//				+ "	) e group by PHONE_NUMBER"
//				+ "	) b on a.PHONE_NUMBER = b.PHONE_NUMBER"
//				+ "	left join ("
//				+ "	select SENDER_NUMBER,count(1) sCount  "
//				+ " from tf_sent_express_info a group by SENDER_NUMBER) c on a.PHONE_NUMBER = c.SENDER_NUMBER"
				+ " ");
		if (params.get("queryParams") != null && !params.get("queryParams").equals("")) {
			sql.append( "	where a.PHONE_NUMBER ='"+params.get("queryParams")+"' or substring(a.PHONE_NUMBER, 8, 4) ='"+params.get("queryParams")+"' or a.NAME = '"+params.get("queryParams")+"'");
			sql.append("  and a.SERVICE_SHOP_CODE="+params.get("shopCode"));
		} else {
			sql.append(" where a.SERVICE_SHOP_CODE="+params.get("shopCode"));
		}
//		sql.append(" order by SERVICE_SHOP_CODE,eCount desc");
		page = new Pagination(sql.toString(), pageSize, rowSize,getJdbcTemplate());
		return page;
	}

	@Override
	public void modifyCustomerInfo(CustomerInfo customer) throws SQLException {
		Connection con = null;
		PreparedStatement st = null;
		String sql = "update TF_CUSTOMER_INFO set "
				+ " GENDER = ?, WHETHER_HAVE_CAR=? , "
				+ " AGE_SECTION=?, NAME=? , PHONE_NUMBER =? , ADDRESS =?, IS_INTEREST =? "
				+ " where ID=?";
		con = connectionManager.getConnection();
        st=con.prepareStatement(sql);
		st.setString(1,customer.getGender());
		st.setString(2,customer.getWhetherHaveCar());
		st.setString(3, customer.getAgeSection());
		st.setString(4, customer.getName());
		st.setString(5, customer.getPhoneNumber());
		st.setString(6, customer.getAddress());
		st.setString(7, customer.getIsInterest());
		st.setInt(8, customer.getId());
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
	
	
	/**
	 * 用户注册
	 */
	@Override
	@Transactional
	public void registerCustomerByOpenId(CustomerInfo customer) throws SQLException {
		Connection con = null;
		PreparedStatement st = null;
		boolean tag = checkCustomerByPhoneNumber(customer);
		if (tag ) {
			String sql = "update TF_CUSTOMER_INFO set "
					+ " GENDER = ?, WEIXIN_ID=? , "
					+ " AGE_SECTION=? ,"
					+ " IS_INTEREST=?"
					+ " where PHONE_NUMBER=?";
			con = connectionManager.getConnection();
	        st=con.prepareStatement(sql);
			st.setString(1,customer.getGender());
			st.setString(2,customer.getWeixinId());
			st.setString(3, customer.getAgeSection());
			st.setString(4, "Y");
			st.setString(5, customer.getPhoneNumber());
			try 
			{
					st.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				connectionManager.closeStatement(st);
				connectionManager.closeConnection(con);
			}
		} else {
			SequenceManager sm = SequenceManager.getInstance();
			con = connectionManager.getConnection();
			Integer id = sm.getSequenceByName("seq_custome_info_id",con);
			String insertSQL =
					"INSERT INTO TF_CUSTOMER_INFO("+
					 "ID, NAME, PHONE_NUMBER ,lANDLINE_NUMBER, WEIXIN_ID, IDENTITY_CARD, GENDER,AGE_SECTION, ADDRESS, AREA_CODE, INITIALS_CODE, SPELLING_CODE,SERVICE_SHOP_CODE,IS_INTEREST)"+
				"SELECT ?,?,?,?,?,?,?,?,?,?,?,?,?,? FROM DUAL "
				+ "WHERE NOT EXISTS(SELECT NULL  FROM  TF_CUSTOMER_INFO  WHERE PHONE_NUMBER = ?) ";
	        st=con.prepareStatement(insertSQL);
			st.setLong(1, id);
			st.setString(2, customer.getName());
			st.setString(3, customer.getPhoneNumber());
			st.setString(4, customer.getLandlineNumber());
			st.setString(5, customer.getWeixinId());
			st.setString(6, customer.getIdentityCard());
			st.setString(7, customer.getGender());
			st.setString(8, customer.getAgeSection());
			st.setString(9, customer.getAddress());
			st.setString(10, customer.getAreaCode());
			st.setString(11, customer.getInitialsCode());
			st.setString(12, customer.getSpellingCode());
			st.setString(13, customer.getShopCode());
			st.setString(14, "Y");
			st.setString(15, customer.getPhoneNumber());
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
		
	}
	
	public synchronized   boolean checkCustomerByPhoneNumber(CustomerInfo customer) throws SQLException {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement st = null;
		String sql = "select count(*) COUNT from TF_CUSTOMER_INFO where PHONE_NUMBER = ?";
		con = connectionManager.getConnection();
        st=con.prepareStatement(sql);
		st.setString(1,customer.getPhoneNumber());
		Integer count =0;
		try 
		{
			rs = st.executeQuery();
			while (rs.next()) {
				count =rs.getInt(1);//index start with 1  
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			connectionManager.closeStatement(st);
			connectionManager.closeConnection(con);
		}
		if (count >0) {
			return true;
		} else {
			return false;
		}
		
	}

	@Override
	public JSONObject checkWechatOpenId(Map<String, String> params) throws SQLException {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement st = null;
		String sql = "select count(*) COUNT from TF_CUSTOMER_INFO where WEIXIN_ID = ?";
		con = connectionManager.getConnection();
        st=con.prepareStatement(sql);
		st.setString(1,params.get("openId"));
		Integer count =0;
		try 
		{
			rs = st.executeQuery();
			while (rs.next()) {
				count =rs.getInt(1);//index start with 1  
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			connectionManager.closeStatement(st);
			connectionManager.closeConnection(con);
		}
		JSONObject j = new JSONObject();
		if (count >0) {
			j.put("tag", true);
			j.put("msg", "openId已注册");
		} else {
			j.put("tag", false);
			j.put("msg", "openId未注册,可以进行注册");
		}
		return j;
	}

	@Override
	public String getAreaCodeByShopCode(String shopCode) throws SQLException {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement st = null;
		String areaCode = null;
		String sql = "select AREA_CODE from tf_shop_info a where a.SHOP_CODE = ?";
		con = connectionManager.getConnection();
        st=con.prepareStatement(sql);
		st.setString(1,shopCode);
		try 
		{
			rs = st.executeQuery();
			while (rs.next()) {
				areaCode =rs.getString(1);//index start with 1  
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			connectionManager.closeStatement(st);
			connectionManager.closeConnection(con);
		}
		return areaCode;

	}

	@Override
	public JSONObject updateCustomerGender(String phoneNumber, String sex) throws SQLException {
		Connection con = null;
		PreparedStatement st = null;
		String sql = "update TF_CUSTOMER_INFO set  GENDER = ? "
				+ " where PHONE_NUMBER=?";
		con = connectionManager.getConnection();
        st=con.prepareStatement(sql);
		st.setString(1,sex);
		st.setString(2,phoneNumber);
		try 
		{
				st.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			connectionManager.closeStatement(st);
			connectionManager.closeConnection(con);
		}
		return null;
	}

	@Override
	public JSONObject getCustomerInfoByPhoneNumber(Map<String, String> params)
			throws SQLException {
		JSONObject json = new JSONObject();
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement st = null;
		Map<String, Object> t = null;
		try 
		{
			con = connectionManager.getConnection();//jdbcTemplate.getDataSource().getConnection();
			st = con.prepareStatement(""
					+ "	select a.PHONE_NUMBER,a.WEIXIN_ID,"
					+ "	a.SERVICE_SHOP_CODE,b.NAME "
					+ " from TF_CUSTOMER_INFO a "
					+ " left join tf_shop_info b on a.SERVICE_SHOP_CODE = b.SHOP_CODE where a.PHONE_NUMBER = ?");
			st.setString(1, params.get("phoneNumber"));
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
		return json;
	}

	@Override
	public List<Map<String, Object>> getCustomerListByTxt(Map<String, String> params) {
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement st = null;
		List<Map<String, Object>> t = null;
		try 
		{
			con = connectionManager.getConnection();//jdbcTemplate.getDataSource().getConnection();
			st = con.prepareStatement(""
					+ " select a.name,a.phone_number,a.is_interest "
					+ " from TF_CUSTOMER_INFO a "
					+ " where 1=1"
					+ " and a.SERVICE_SHOP_CODE = '"+params.get("shopCode")+"'"
					+ " and (a.NAME like '%" + params.get("queryTxt")+ "%' "
					+ " or a.INITIALS_CODE like '%" + params.get("queryTxt")+ "%'"
					+ " or a.SPELLING_CODE like '%" + params.get("queryTxt")+ "%'"
					+ " or substring(PHONE_NUMBER, 8, 4) = '"+params.get("queryTxt")+"'"
					+ " or a.PHONE_NUMBER like '%" + params.get("queryTxt")+ "%' )");
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
}

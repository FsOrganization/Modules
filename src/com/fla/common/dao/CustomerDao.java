package com.fla.common.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fla.common.dao.interfaces.CustomerDaoInterface;
import com.fla.common.entity.CustomerInfo;
import com.fla.common.util.Pagination;
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
				+ " select a.ID,a.NAME,a.GENDER,a.WEIXIN_ID,a.ADDRESS,a.AGE_SECTION,a.SERVICE_SHOP_CODE,a.WHETHER_HAVE_CAR,a.PHONE_NUMBER,a.AREA_CODE,IFNULL(b.eCount,0) eCount,IFNULL(c.sCount,0) sCount"
				+ "	from tf_customer_info a "
				+ "	left join ( "
				+ "	select PHONE_NUMBER,sum(eCount) eCount"
				+ "	from ("
				+ "			select PHONE_NUMBER,count(1) eCount  from tf_express_info a group by PHONE_NUMBER"
				+ "			UNION ALL"
				+ "			select PHONE_NUMBER,count(1) eCount from tf_express_out_storehouse a group by PHONE_NUMBER"
				+ "	) e group by PHONE_NUMBER"
				+ "	) b on a.PHONE_NUMBER = b.PHONE_NUMBER"
				+ "	left join ("
				+ "	select SENDER_NUMBER,count(1) sCount  "
				+ " from tf_sent_express_info a group by SENDER_NUMBER) c on a.PHONE_NUMBER = c.SENDER_NUMBER ");
		if (params.get("queryParams") != null && !params.get("queryParams").equals("")) {
			sql.append( "	where a.PHONE_NUMBER ='"+params.get("queryParams")+"' or substring(a.PHONE_NUMBER, 8, 4) ='"+params.get("queryParams")+"' or a.NAME = '"+params.get("queryParams")+"'");
			sql.append("  and a.SERVICE_SHOP_CODE="+params.get("shopCode"));
		} else {
			sql.append(" where a.SERVICE_SHOP_CODE="+params.get("shopCode"));
		}
		sql.append(" order by SERVICE_SHOP_CODE,eCount desc");
		page = new Pagination(sql.toString(), pageSize, rowSize,getJdbcTemplate());
		return page;
	}

	@Override
	public void modifyCustomerInfo(CustomerInfo customer) throws SQLException {
		Connection con = null;
		PreparedStatement st = null;
		String sql = "update TF_CUSTOMER_INFO set "
				+ " GENDER = ?, WHETHER_HAVE_CAR=? , "
				+ " AGE_SECTION=?, NAME=? , PHONE_NUMBER =? , ADDRESS =? "
				+ " where ID=?";
		con = connectionManager.getConnection();
        st=con.prepareStatement(sql);
		st.setString(1,customer.getGender());
		st.setString(2,customer.getWhetherHaveCar());
		st.setString(3, customer.getAgeSection());
		st.setString(4, customer.getName());
		st.setString(5, customer.getPhoneNumber());
		st.setString(6, customer.getAddress());
		st.setInt(7, customer.getId());
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
	@Transactional
	public void registerCustomerByOpenId(CustomerInfo customer) throws SQLException {
		Connection con = null;
		PreparedStatement st = null;
		boolean tag = checkCustomerByPhoneNumber(customer);
		if (tag ) {
			String sql = "update TF_CUSTOMER_INFO set "
					+ " GENDER = ?, WEIXIN_ID=? , "
					+ " AGE_SECTION=?"
					+ " where PHONE_NUMBER=?";
			con = connectionManager.getConnection();
	        st=con.prepareStatement(sql);
			st.setString(1,customer.getGender());
			st.setString(2,customer.getWeixinId());
			st.setString(3, customer.getAgeSection());
			st.setString(4, customer.getPhoneNumber());
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
					 "ID, NAME, PHONE_NUMBER ,lANDLINE_NUMBER, WEIXIN_ID, IDENTITY_CARD, GENDER,AGE_SECTION, ADDRESS, AREA_CODE, INITIALS_CODE, SPELLING_CODE,SERVICE_SHOP_CODE)"+
				"SELECT ?,?,?,?,?,?,?,?,?,?,?,?,? FROM DUAL "
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
			st.setString(14, customer.getPhoneNumber());
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
	
}

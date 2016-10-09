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

import com.fla.common.dao.interfaces.ExpressWebServiceDaoInterface;
import com.fla.common.entity.CustomerInfo;
import com.fla.common.entity.SentExpressInfo;
import com.fla.common.util.BaseUtil;
import com.fla.common.util.DateUtil;
import com.fla.common.util.SequenceManager;
import com.fla.common.util.connection.ConnectionManager;

@Service
public class ExpressWebServiceDao implements ExpressWebServiceDaoInterface {

	@Autowired
	public ConnectionManager connectionManager;
	
	@Autowired
	public JdbcTemplate jdbcTemplate;
	
	public ExpressWebServiceDao() {
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Map<String, Object>> getCustomerInfo(CustomerInfo customer) {
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement st = null;
		List<Map<String, Object>> t = null;
		try 
		{
			con = connectionManager.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("select NAME,PHONE_NUMBER,WEIXIN_ID,GENDER,AGE_SECTION,WHETHER_HAVE_CAR,SERVICE_SHOP_CODE from TF_CUSTOMER_INFO a where a.PHONE_NUMBER = ? limit 1");
			st = con.prepareStatement(sql.toString());
			st.setString(1,customer.getPhoneNumber());
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

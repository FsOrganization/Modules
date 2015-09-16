package com.fla.common.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.fla.common.dao.interfaces.CustomerDaoInterface;
import com.fla.common.util.ResultSetUtils;
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
	public List<Map<String, Object>> getCustomerList(int rowSize, int pageSize,Map<String, String> params) throws SQLException {
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement st = null;
		List<Map<String, Object>> t = null;
		try 
		{
			con = connectionManager.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("select a.id,a.name,a.PHONE_NUMBER,a.AREA_CODE,IFNULL(b.eCount,0) eCount,IFNULL(c.sCount,0) sCount"+
													 "	from tf_customer_info a "+
													 "	left join ( "+
													 "	select PHONE_NUMBER,sum(eCount) eCount"+ 
													 "	from ("+
													 "			select PHONE_NUMBER,count(1) eCount  from tf_express_info a group by PHONE_NUMBER"+
													 "			UNION ALL"+
													 "			select PHONE_NUMBER,count(1) eCount from tf_express_out_storehouse a group by PHONE_NUMBER"+
													 "	) e group by PHONE_NUMBER"+
													 "	) b on a.PHONE_NUMBER = b.PHONE_NUMBER"+ 
													 "	left join ("+
													 "	select SENDER_NUMBER,count(1) sCount  "+
													 " from tf_sent_express_info a group by SENDER_NUMBER) c on a.PHONE_NUMBER = c.SENDER_NUMBER order by eCount desc ");
			st = con.prepareStatement(sql.toString());
			rs = st.executeQuery();
			t = ResultSetUtils.checkResultSet(rs);
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

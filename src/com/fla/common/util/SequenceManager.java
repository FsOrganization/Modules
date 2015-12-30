package com.fla.common.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fla.common.util.connection.ConnectionManager;

@Repository
public class SequenceManager {
	
	@Autowired
	public ConnectionManager connectionManager = ConnectionManager.getInstance();
	
	private SequenceManager() {
	}  
	
	private static SequenceManager sequenceManager=null;  
	
	 public static SequenceManager getInstance() {  
         synchronized (SequenceManager.class) {
			if (sequenceManager == null) {
				sequenceManager = new SequenceManager();
			}
		}
		return sequenceManager;  
    }  

	public synchronized Integer getTemporaryStorage(Connection conn,String name) {
		PreparedStatement st = null;
		Integer val = null;
		ResultSet rs = null;
		try 
		{
			st = conn.prepareStatement("SELECT  nextval('"+name+"') current_value");
			rs = st.executeQuery();
			ResultSetMetaData m = rs.getMetaData();
			int count = m.getColumnCount();
			Map<String, Object> map = new HashMap<String, Object>();
			while (rs.next()) {
				for (int i = 1; i <= count; i++) {
					String cName = m.getColumnName(i);
					map.put(cName, rs.getObject(i));
				}
			}
			val = (Integer) map.get("current_value");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connectionManager.closeStatement(st);
			connectionManager.closeResultSet(rs);
//			connectionManager.closeConnection(conn);
		}
		return val;
	}
	
	public synchronized Integer getTemporaryStorage(Connection conn,String name, String shopCode) {
		PreparedStatement st = null;
		Integer val = null;
		ResultSet rs = null;
		try 
		{
			st = conn.prepareStatement("SELECT  nextvalByShopCode('"+name+"','"+shopCode+"') current_value");
			rs = st.executeQuery();
			ResultSetMetaData m = rs.getMetaData();
			int count = m.getColumnCount();
			Map<String, Object> map = new HashMap<String, Object>();
			while (rs.next()) {
				for (int i = 1; i <= count; i++) {
					String cName = m.getColumnName(i);
					map.put(cName, rs.getObject(i));
				}
			}
			val = (Integer) map.get("current_value");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connectionManager.closeStatement(st);
			connectionManager.closeResultSet(rs);
//			connectionManager.closeConnection(conn);
		}
		return val;
	}
	 
	public synchronized Integer getSequenceByName(String sequenceName, Connection conn) {
		PreparedStatement st = null;
		Integer val = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT  nextval('" + sequenceName+ "') current_value");
			rs = st.executeQuery();
			ResultSetMetaData m = rs.getMetaData();
			int count = m.getColumnCount();
			Map<String, Object> map = new HashMap<String, Object>();
			while (rs.next()) 
			{
				for (int i = 1; i <= count; i++) 
				{
					String cName = m.getColumnName(i);
					map.put(cName, rs.getObject(i));
				}
			}
			val = (Integer) map.get("current_value");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connectionManager.closeStatement(st);
			connectionManager.closeResultSet(rs);
//			connectionManager.closeConnection(conn);
		}
		return val;
	}
	
//	public List<Map<String, Object>> getExpressServiceProviderInfo(String areaCode) throws SQLException {
//		ResultSet rs = null;
//		Connection con = null;
//		PreparedStatement st = null;
//		List<Map<String, Object>> t = null;
//		try 
//		{
//			con = connectionManager.getConnection();//jdbcTemplate.getDataSource().getConnection();
//			st = con.prepareStatement("select  ID, NAME, REMARK  from TF_EXPRESS_SERVICE_PROVIDER_INFO");
////			st.setString(1, areaCode);
//			rs = st.executeQuery();
//			t = checkResultSet(rs);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			connectionManager.closeResultSet(rs);
//			connectionManager.closeStatement(st);
//			connectionManager.closeConnection(con);
//		}
//		return t;
//	}
//	
//	public List<Map<String, Object>> checkResultSet(ResultSet rs) throws SQLException {
//		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>(rs.getRow());
//		ResultSetMetaData m = rs.getMetaData();
//		int count = m.getColumnCount();
//		while (rs.next()) {
//			Map<String,Object> map = new HashMap<String,Object>();
//			for (int i = 1; i <= count; i++) {
//				String cName = m.getColumnName(i);
//				int d = Types.DATE;
//				int  t = Types.TIME;
//				int p = Types.TIMESTAMP;
//				List<Integer> ts = new ArrayList<Integer>(3);
//				ts.add(d);
//				ts.add(t);
//				ts.add(p);
//				int  mt = m.getColumnType(i);
//				if (ts.contains(mt)) {
//					Object o = rs.getObject(i);
//					if (o != null) {
//						map.put(cName, sdf.format(o));
//					} else {
//						map.put(cName,"");
//					}
//				} else {
//					map.put(cName, rs.getObject(i));
//				}
//			}
//			list.add(map);
//		}
//		return list;
//	}
	 
}

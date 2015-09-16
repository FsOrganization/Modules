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

import com.fla.common.dao.interfaces.ExpressDaoInterface;
import com.fla.common.entity.SentExpressInfo;
import com.fla.common.util.BaseUtil;
import com.fla.common.util.DateUtil;
import com.fla.common.util.SequenceManager;
import com.fla.common.util.connection.ConnectionManager;

@Service
public class ExpressDao implements ExpressDaoInterface {

	@Autowired
	public ConnectionManager connectionManager;
	
	@Autowired
	public JdbcTemplate jdbcTemplate;
	
	public ExpressDao() {
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Map<String, Object>> getSentExpressInfo(int rowSize,int pageSize, Map<String, String> params) {
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement st = null;
		List<Map<String, Object>> t = null;
		try 
		{
			con = connectionManager.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("select ID,LOGISTICS,CODE,"
					+ " RECIPIENT_NAME,PHONE_NUMBER,LANDLINE_NUMBER,SENDER_NAME,SENDER_NUMBER,"
					+ " DESTINATION,EXPRESS_SERVICE_ID,ADDRESS,REMARK,SENDER_LANDLINE_NUMBER,"
					+ " OPERA_TIME,AREA_CODE,SERVICE_SHOP_CODE,RES,"
					+ " OPERATOR,EXPRESS_lOCATION,TYPE"
					+ " from tf_sent_express_info ");
			sql.append(" where 1=1 ");
			sql.append(" and SERVICE_SHOP_CODE="
					+ params.get("serviceShopCode"));
			if (params.get("queryParams") != null
					&& !params.get("queryParams").equals("")) {
				String queryParams = params.get("queryParams");
				sql.append(
						" and (SENDER_NAME like '%" + queryParams+ "%'"
						+ " or substring(SENDER_NUMBER, 8, 4) = '"+queryParams + "'"
						+ "or trim(LOGISTICS) = '"+ queryParams + "'" 
						+ "	or PHONE_NUMBER = '"+ queryParams + "')");
			}
			if (params.get("expressService") != null
					&& !params.get("expressService").equals("")) {
				String expressService = params.get("expressService");
				sql.append(" and EXPRESS_SERVICE_ID = " + expressService);
			}
			if (!BaseUtil.checkAllNull(params.get("startDate"))
					&& !BaseUtil.checkAllNull(params.get("endDate"))) {
				String startDate = params.get("startDate");
				String endDate = params.get("endDate");
				sql.append(" and OPERA_TIME between '" + startDate + "' and '"
						+ endDate + "'");
			} else if (!BaseUtil.checkAllNull(params.get("startDate"))) {
				String startDate = params.get("startDate");
				sql.append(" and OPERA_TIME >= '" + startDate + "'");
			} else if (!BaseUtil.checkAllNull(params.get("endDate"))) {
				String endDate = params.get("endDate");
				sql.append(" and OPERA_TIME <='" + endDate + "'");
			}
			sql.append(" order by OPERA_TIME desc");
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
	
	public void insertSendExpressInfo(SentExpressInfo ei) throws Exception{
		SequenceManager sm = SequenceManager.getInstance();
		Connection con = null;
		PreparedStatement st = null;
		con = connectionManager.getConnection();//jdbcTemplate.getDataSource().getConnection();
		Integer id = sm.getSequenceByName("seq_sent_express_info_id",con);
		String insertSQL =
				"INSERT INTO TF_SENT_EXPRESS_INFO("+
				 "ID,LOGISTICS,CODE,RECIPIENT_NAME,PHONE_NUMBER,LANDLINE_NUMBER,"+
				 "SENDER_NAME,SENDER_NUMBER,SENDER_LANDLINE_NUMBER,EXPRESS_SERVICE_ID,"+
				 "ADDRESS,DESTINATION,OPERA_TIME,AREA_CODE,SERVICE_SHOP_CODE,OPERATOR,"+
				 "EXPRESS_lOCATION,WEIGHT,DIMENSIONS,REMARK,RES,TYPE)"+
			"VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        st=con.prepareStatement(insertSQL);
		st.setLong(1, id);
		st.setString(2, ei.getLogistics());
		st.setString(3, ei.getCode());
		st.setString(4, ei.getRecipientName());
		st.setString(5, ei.getPhoneNumber());
		st.setString(6, ei.getLandlineNumber());
		st.setString(7, ei.getSenderName());
		st.setString(8, ei.getSenderNumber());
		st.setString(9, ei.getSenderLandlineNumber());
		st.setInt(10, ei.getExpressServiceId());
		st.setString(11, ei.getAddress());
		st.setString(12, ei.getDestination());
		st.setString(13, DateUtil.formatDateToString(new Date()));
		st.setString(14, ei.getAreaCode());
		st.setString(15, ei.getServiceShopCode());
		st.setString(16, ei.getOperator());
		st.setString(17, ei.getExpressLocation());
		st.setString(18, ei.getWeight());
		st.setString(19, ei.getDimensions());
		st.setString(20, ei.getRemark());
		st.setString(21, ei.getRes());
		st.setString(22, ei.isType()==false?"1":"0");
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
	
	public void modifySentExpressInfo(SentExpressInfo ei) throws Exception{
//		SequenceManager sm = SequenceManager.getInstance();
		Connection con = null;
		PreparedStatement st = null;
		con = connectionManager.getConnection();//jdbcTemplate.getDataSource().getConnection();
//		Integer id = sm.getSequenceByName("seq_sent_express_info_id",con);
		String modifySQL =
				"UPDATE  TF_SENT_EXPRESS_INFO set "+
				 "	 LOGISTICS=?,CODE=?,RECIPIENT_NAME=?,PHONE_NUMBER=?,LANDLINE_NUMBER=?,"+
				 " SENDER_NAME=?,SENDER_NUMBER=?,SENDER_LANDLINE_NUMBER=?,EXPRESS_SERVICE_ID=?,"+
				 "	 ADDRESS=?,DESTINATION=?,OPERA_TIME=?,AREA_CODE=?,SERVICE_SHOP_CODE=?,OPERATOR=?,"+
				 "	 EXPRESS_lOCATION=?,WEIGHT=?,DIMENSIONS=?,REMARK=?,RES=?,TYPE=?"+
				 " where id = ?";
        st=con.prepareStatement(modifySQL);
		
		st.setString(1, ei.getLogistics());
		st.setString(2, ei.getCode());
		st.setString(3, ei.getRecipientName());
		st.setString(4, ei.getPhoneNumber());
		st.setString(5, ei.getLandlineNumber());
		st.setString(6, ei.getSenderName());
		st.setString(7, ei.getSenderNumber());
		st.setString(8, ei.getSenderLandlineNumber());
		st.setInt(9, ei.getExpressServiceId());
		st.setString(10, ei.getAddress());
		st.setString(11, ei.getDestination());
		st.setString(12, DateUtil.formatDateToString(new Date()));
		st.setString(13, ei.getAreaCode());
		st.setString(14, ei.getServiceShopCode());
		st.setString(15, ei.getOperator());
		st.setString(16, ei.getExpressLocation());
		st.setString(17, ei.getWeight());
		st.setString(18, ei.getDimensions());
		st.setString(19, ei.getRemark());
		st.setString(20, ei.getRes());
		st.setString(21, ei.isType()==false?"1":"0");
		st.setLong(22, ei.getId());
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

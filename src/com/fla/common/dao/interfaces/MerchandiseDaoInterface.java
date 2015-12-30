package com.fla.common.dao.interfaces;

import java.sql.SQLException;
import java.util.Map;

import net.sf.json.JSONObject;

import com.fla.common.util.Pagination;

public interface MerchandiseDaoInterface {
	
	public Pagination getMerchandiseList(final int rowSize, final int pageSize, Map<String,String> params) throws SQLException;
	
	public JSONObject addMerchandise(Map<String,String> params) throws SQLException;
	
	public JSONObject modifyMerchandiseInfo(Map<String,String> params) throws SQLException;
	
	}

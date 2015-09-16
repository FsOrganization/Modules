package com.fla.common.dao.interfaces;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface CustomerDaoInterface {
	
	public List<Map<String, Object>> getCustomerList(final int rowSize, final int pageSize, Map<String,String> params) throws SQLException;
	
	}

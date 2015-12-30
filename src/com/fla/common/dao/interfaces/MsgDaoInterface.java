package com.fla.common.dao.interfaces;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface MsgDaoInterface {
	
	public List<Map<String, Object>> getSendMsgShopList(Map<String, String> params) throws SQLException;
	
	
	}

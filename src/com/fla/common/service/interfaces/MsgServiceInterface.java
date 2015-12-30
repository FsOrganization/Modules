package com.fla.common.service.interfaces;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Map;

import net.sf.json.JSONArray;

public interface MsgServiceInterface {
	
	public JSONArray getSendMsgShopList(Map<String, String> params) throws SQLException;
	
	public HashSet<String> getSendMsgShopStringList(Map<String, String> params) throws SQLException;
	
}

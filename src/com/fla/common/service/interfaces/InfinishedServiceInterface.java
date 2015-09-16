package com.fla.common.service.interfaces;

import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.fla.common.entity.SentExpressInfo;

public interface InfinishedServiceInterface {
	
	public JSONArray getSentExpressInfo(final int rowSize, final int pageSize, Map<String,String> params);
	
	public JSONObject addSentExpressInfo(SentExpressInfo sei);
	
	public JSONObject modifySentExpressInfo(SentExpressInfo sei);
	
}

package com.fla.common.service.interfaces;

import java.util.Map;

import net.sf.json.JSONObject;

import com.fla.common.entity.SentExpressInfo;
import com.fla.common.util.Pagination;

public interface InfinishedServiceInterface {
	
	public Pagination getSentExpressInfo(final int rowSize, final int pageSize, Map<String,String> params);
	
	public JSONObject addSentExpressInfo(SentExpressInfo sei);
	
	public JSONObject modifySentExpressInfo(SentExpressInfo sei);
	
}

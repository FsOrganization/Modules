package com.fla.common.service.interfaces;

import java.util.Map;

import net.sf.json.JSONObject;

import com.fla.common.util.Pagination;

public interface MerchandiseServiceInterface {

	public Pagination getMerchandiseList(final int rowSize, final int pageSize, Map<String,String> params);
	
	public JSONObject addMerchandise(Map<String,String> params);
	
	public JSONObject modifyMerchandiseInfo(Map<String,String> params);
	
}

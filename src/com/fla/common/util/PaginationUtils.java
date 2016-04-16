package com.fla.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONSerializer;

public class PaginationUtils {

	public PaginationUtils() {
	}

	public static String getData(int currentPage, int numPerPage,Pagination page) {
		String message = "";
		List<Map<String, Object>> businessList = null;
		Map<String, Object> map = new HashMap<String, Object>();
		businessList = new ArrayList<Map<String, Object>>();
		try 
		{
			List<Map<String, Object>> list = page.getResultList();
			businessList = new ArrayList<Map<String, Object>>();
			for (int i = 0, length = list.size(); i < length; i++) {
				Map<String, Object> m = (Map<String, Object>) list.get(i);
				businessList.add(m);
			}
			map.put("message", message);
			map.put("pages", page.getTotalPages());
			map.put("currentPage", page.getCurrentPage());
			map.put("total", page.getTotalRows());
			map.put("numPerPage", page.getNumPerPage());
			map.put("rows", businessList);
			message = "success";
		} catch (Exception e1) {
			e1.printStackTrace();
			message = "failure";
		} finally {
			businessList = null;
			message = null;
		}
		return JSONSerializer.toJSON(map).toString();
	}
	
}

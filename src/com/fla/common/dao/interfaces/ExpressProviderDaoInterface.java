package com.fla.common.dao.interfaces;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.fla.common.entity.ExpressServiceProvider;
import com.fla.common.entity.SystemArea;
import com.fla.common.entity.SystemShop;
import com.fla.common.entity.SystemUser;

public interface ExpressProviderDaoInterface {
	
	public List<Map<String, Object>> getAreaInfoList(final int rowSize, final int pageSize, Map<String,String> params) throws SQLException;
	public List<Map<String, Object>> getAreaInfoList(Map<String,String> params) throws SQLException;
	
	public void modifyAreaInfo(SystemArea area) throws SQLException;
	public void insertAreaInfoList(SystemArea area,Map<String,String> params) throws SQLException;
	public void deleteAreaInfoList(Integer id,Map<String,String> params) throws SQLException;
	
	}

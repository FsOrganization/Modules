package com.fla.common.service.interfaces;

import java.sql.SQLException;
import java.util.Map;

import com.fla.common.entity.ScanneInfo;
import com.fla.common.entity.SystemUser;
import com.fla.common.util.Pagination;

public interface ScanneServiceInterface {
	
public Pagination getScanneList(final int rowSize, final int pageSize, Map<String,String> params) throws SQLException;
	
	public ScanneInfo getScanneByShopCode(Map<String, String> params) throws SQLException;
	
	public void insertScanne(ScanneInfo si) throws SQLException;
	
	public void modifyScanne(ScanneInfo si) throws SQLException;
	
	public SystemUser getSystemUserByLoginName(Map<String, String> params) throws SQLException;
	
}

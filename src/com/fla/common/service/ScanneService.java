package com.fla.common.service;

import java.sql.SQLException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fla.common.dao.ScanneDao;
import com.fla.common.entity.ScanneInfo;
import com.fla.common.entity.SystemUser;
import com.fla.common.service.interfaces.ScanneServiceInterface;
import com.fla.common.util.Pagination;

@Service
@Transactional
public class ScanneService implements ScanneServiceInterface{

	@Autowired
	private ScanneDao scanneDao;

	public ScanneService() {
	}

	@Override
	public Pagination getScanneList(int rowSize, int pageSize,
			Map<String, String> params) throws SQLException {
		return scanneDao.getScanneList(rowSize, pageSize, params);
	}

	@Override
	public ScanneInfo getScanneByShopCode(Map<String, String> params)
			throws SQLException {
		return scanneDao.getScanneByShopCode(params);
	}

	@Override
	public void insertScanne(ScanneInfo si) throws SQLException {
		scanneDao.insertScanne(si);		
	}

	@Override
	public void modifyScanne(ScanneInfo si) throws SQLException {
		scanneDao.modifyScanne(si);
	}

	@Override
	public SystemUser getSystemUserByLoginName(Map<String, String> params)
			throws SQLException {
		return scanneDao.getSystemUserByLoginName(params);
	}
	
}

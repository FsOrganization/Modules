package com.fla.common.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fla.common.base.SuperServiceAdapter;
import com.fla.common.dao.interfaces.SQLPlusDaoInterface;
import com.fla.common.service.interfaces.SQLPlusServiceInterface;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

@Service
@Transactional
public class SQLPlusService extends SuperServiceAdapter<SQLPlusDaoInterface> implements SQLPlusServiceInterface{
	
	@Autowired
	@Override
	public void setMapper(SQLPlusDaoInterface mapper) {
		this.mapper = mapper;
	}

	public SQLPlusService() {
	}

	@Override
	public List<Map<String, Object>> execute(Map<String, Object> params,PageBounds pageBounds) {
		return mapper.execute(params, pageBounds);
	}

	@Override
	public void executeDML(Map<String, Object> params) {
		mapper.executeDML(params);
	}

	@Override
	public void executeDDL(Map<String, Object> params) {
		mapper.executeDDL(params);
	}

	@Override
	public void executeDCL(Map<String, Object> params) {
		mapper.executeDCL(params);
	}

	@Override
	public void executeTCL(Map<String, Object> params) {
		mapper.executeTCL(params);
	}

}

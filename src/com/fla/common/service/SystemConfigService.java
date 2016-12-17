package com.fla.common.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fla.common.base.ServiceSaveDelegate;
import com.fla.common.base.SuperServiceAdapter;
import com.fla.common.dao.interfaces.SystemConfigDaoInterface;
import com.fla.common.entity.SystemConfig;
import com.fla.common.service.interfaces.SystemConfigServiceInterface;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

@Service
@Transactional
public class SystemConfigService extends SuperServiceAdapter<SystemConfigDaoInterface> implements SystemConfigServiceInterface {

	public SystemConfigService() {}
	
	@Autowired
	@Override
	public void setMapper(SystemConfigDaoInterface mapper) {
		this.mapper = mapper;
	}

	@Override
	public List<SystemConfig> getSystemConfigList(Map<String, Object> params,
			PageBounds pageBounds) {
		return mapper.getSystemConfigList(params, pageBounds);
	}

	@Override
	public SystemConfig getSystemConfigById(SystemConfig config) {
		return mapper.getSystemConfigById(config);
	}

	@Override
	public <A> void saveEntity(A entity, ServiceSaveDelegate... delegate) {
		super.saveEntity(entity, delegate);
	}

	@Override
	public <K, A> K findEntity(A... param) {
		return super.findEntity(param);
	}

	@Override
	public <K, A> List<K> findEntityListForPages(A param, PageBounds pageBounds) {
		return super.findEntityListForPages(param, pageBounds);
	}

	@Override
	public SystemConfig getSystemConfigByCode(Map<String,Object> params) {
		return mapper.getConfigValueByCode(params);
	}
	
}

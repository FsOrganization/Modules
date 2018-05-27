package com.fla.common.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fla.common.base.SuperServiceAdapter;
import com.fla.common.dao.interfaces.APPConfigDaoInterface;
import com.fla.common.entity.CustomerInfo;
import com.fla.common.entity.ExpressProviderContacts;
import com.fla.common.service.interfaces.APPConfigServiceInterface;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

@Service
@Transactional
public class APPConfigService extends SuperServiceAdapter<APPConfigDaoInterface> implements APPConfigServiceInterface {

	@Autowired
	@Override
	public void setMapper(APPConfigDaoInterface mapper) {
		this.mapper = mapper;
	}

	public APPConfigService() {
	}

	@Override
	public List<CustomerInfo> getRegisteredCustomers(Map<String, Object> params, PageBounds pageBounds) {
		return mapper.getRegisteredCustomers(params, pageBounds);
	}

	@Override
	public List<ExpressProviderContacts> getProviderContacts(Map<String, Object> params) {
		return mapper.getProviderContacts(params);
	}

	@Override
	public void setCustomerBeProviderContacts(ExpressProviderContacts expressProviderContacts) {
		mapper.setCustomerBeProviderContacts(expressProviderContacts);
	}

}

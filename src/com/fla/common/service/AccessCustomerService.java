package com.fla.common.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fla.common.base.SuperServiceAdapter;
import com.fla.common.dao.interfaces.ICustomerDao;
import com.fla.common.entity.CustomerInfo;
import com.fla.common.service.interfaces.ICustomerService;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

@Service
@Transactional
public class AccessCustomerService extends SuperServiceAdapter<ICustomerDao> implements ICustomerService {

	public AccessCustomerService() {
	}

	@Autowired
	@Override
	public void setMapper(ICustomerDao mapper) {
		this.mapper = mapper;
	}
	@Override
	public List<CustomerInfo> getCustomerInfoList(Map<String, Object> params,
			PageBounds pageBounds) {
		return mapper.getCustomerInfoList(params, pageBounds);
	}
	
}

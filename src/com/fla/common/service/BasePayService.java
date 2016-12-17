package com.fla.common.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fla.common.base.SuperServiceAdapter;
import com.fla.common.dao.interfaces.BasePayDaoInterface;
import com.fla.common.entity.ExpressOutOrder;
import com.fla.common.service.interfaces.BasePayServiceInterface;

@Service
@Transactional
public class BasePayService extends SuperServiceAdapter<BasePayDaoInterface> implements BasePayServiceInterface{

	@Autowired
    private ApplicationContext applicationContext;
	
	@Autowired
	@Override
	public void setMapper(BasePayDaoInterface mapper) {
		this.mapper = mapper;
	}

	public BasePayService() {
	}

	@Override
	public void insert(ExpressOutOrder barcode) {
		mapper.insert(barcode);
	}

	@Override
	public Integer checkOrderPaymentStatus(Map<String, Object> params) {
		return mapper.checkOrderPaymentStatus(params);
	}
	
}

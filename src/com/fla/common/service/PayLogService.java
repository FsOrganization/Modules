package com.fla.common.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fla.common.base.SuperServiceAdapter;
import com.fla.common.dao.interfaces.PayLogDaoInterface;
import com.fla.common.entity.PayLog;
import com.fla.common.service.interfaces.PayLogServiceInterface;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

@Service
@Transactional
public class PayLogService extends SuperServiceAdapter<PayLogDaoInterface> implements PayLogServiceInterface{

	@Autowired
    private ApplicationContext applicationContext;
	
	@Autowired
	@Override
	public void setMapper(PayLogDaoInterface mapper) {
		this.mapper = mapper;
	}

	public PayLogService() {
	}

	@Override
	public PayLog getPayLogByOrderId(Map<String, Object> params) {
		return mapper.getPayLogByOrderId(params);
	}

	@Override
	public void insert(PayLog payLog) {
		mapper.insert(payLog);
	}

	@Override
	public List<PayLog> getPayLogs(Map<String, Object> params, PageBounds pageBounds) {
		return mapper.getPayLogs(params, pageBounds);
	}
	
}

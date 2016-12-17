package com.fla.common.service.interfaces;

import java.util.List;
import java.util.Map;

import com.fla.common.base.SuperService;
import com.fla.common.dao.interfaces.PayLogDaoInterface;
import com.fla.common.entity.PayLog;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

public interface PayLogServiceInterface extends SuperService<PayLogDaoInterface> {
	public PayLog getPayLogByOrderId(Map<String,Object> params);
	public List<PayLog> getPayLogs(Map<String,Object> params,PageBounds pageBounds);
	public void insert(PayLog payLog);
}

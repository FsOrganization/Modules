package com.fla.common.dao.interfaces;

import java.util.List;
import java.util.Map;

import com.fla.common.base.MapperAnnotation;
import com.fla.common.base.SuperMapper;
import com.fla.common.entity.PayLog;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

@MapperAnnotation
public interface PayLogDaoInterface extends SuperMapper{
	public PayLog getPayLogByOrderId(Map<String,Object> params);
	public List<PayLog> getPayLogs(Map<String,Object> params,PageBounds pageBounds);
	public void insert(PayLog payLog);
	public List<PayLog> getBarCodeByExpress(Map<String,Object> params,PageBounds pageBounds);
}

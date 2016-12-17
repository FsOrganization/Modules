package com.fla.common.dao.interfaces;

import java.util.Map;

import com.fla.common.base.MapperAnnotation;
import com.fla.common.base.SuperMapper;
import com.fla.common.entity.ExpressOutOrder;

@MapperAnnotation
public interface BasePayDaoInterface extends SuperMapper{
	public void insert(ExpressOutOrder basePay);
	public Integer checkOrderPaymentStatus(Map<String, Object> params);

}

package com.fla.common.service.interfaces;

import java.util.Map;

import com.fla.common.base.SuperService;
import com.fla.common.dao.interfaces.BasePayDaoInterface;
import com.fla.common.entity.ExpressOutOrder;

public interface BasePayServiceInterface extends SuperService<BasePayDaoInterface> {
	public void insert(ExpressOutOrder barcode);
	public Integer checkOrderPaymentStatus(Map<String,Object> params);
	
}

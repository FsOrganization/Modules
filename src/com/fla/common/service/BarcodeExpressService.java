package com.fla.common.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fla.common.base.SuperServiceAdapter;
import com.fla.common.dao.interfaces.BarcodeExpressDaoInterface;
import com.fla.common.entity.BarcodeExpress;
import com.fla.common.entity.ExpressInfo;
import com.fla.common.service.interfaces.BarcodeExpressServiceInterface;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

@Service
@Transactional
public class BarcodeExpressService extends SuperServiceAdapter<BarcodeExpressDaoInterface> implements BarcodeExpressServiceInterface{

	@Autowired
    private ApplicationContext applicationContext;
	
	@Autowired
	@Override
	public void setMapper(BarcodeExpressDaoInterface mapper) {
		this.mapper = mapper;
	}

	public BarcodeExpressService() {
	}

	@Override
	public List<BarcodeExpress> getBarcodeExpressList(Map<String, Object> params, PageBounds pageBounds) {
		return mapper.getBarcodeExpressList(params, pageBounds);
	}
	
	@Override
	public List<BarcodeExpress> getBarcodeExpressByBarCode(Map<String, Object> params) {
		return mapper.getBarcodeExpressByBarCode(params);
	}

	@Override
	public BarcodeExpress getBarcodeExpressById(Map<String, Object> params) {
		return mapper.getBarcodeExpressById(params);
	}

	@Override
	public void insert(BarcodeExpress barcode) {
		mapper.insert(barcode);
	}

	@Override
	public List<ExpressInfo> getExpressInfoByParams(Map<String, Object> params) {
		return mapper.getExpressInfoByParams(params);
	}

	@Override
	public Integer checkOrderPaymentStatus(Map<String, Object> params) {
		return mapper.checkOrderPaymentStatus(params);
	}

	@Override
	public Integer checkExpressIdPrint(Map<String, Object> params) {
		return mapper.checkExpressIdPrint(params);
	}

	@Override
	public Integer checkMember(Map<String, Object> params) {
		return mapper.checkMember(params);
	}
	
	
}

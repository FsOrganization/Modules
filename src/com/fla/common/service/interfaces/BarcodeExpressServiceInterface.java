package com.fla.common.service.interfaces;

import java.util.List;
import java.util.Map;

import com.fla.common.base.SuperService;
import com.fla.common.dao.interfaces.BarcodeExpressDaoInterface;
import com.fla.common.entity.BarcodeExpress;
import com.fla.common.entity.ExpressInfo;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

public interface BarcodeExpressServiceInterface extends SuperService<BarcodeExpressDaoInterface> {
	public List<BarcodeExpress> getBarcodeExpressList(Map<String,Object> params,PageBounds pageBounds);
	public BarcodeExpress getBarcodeExpressById(Map<String,Object> params);
	public void insert(BarcodeExpress barcode);
	public List<BarcodeExpress> getBarcodeExpressByBarCode(Map<String,Object> params);
	public List<ExpressInfo> getExpressInfoByParams(Map<String,Object> params);
	public Integer checkOrderPaymentStatus(Map<String,Object> params);
	public Integer checkExpressIdPrint(Map<String,Object> params);
	public Integer checkMember(Map<String,Object> params);
	
}

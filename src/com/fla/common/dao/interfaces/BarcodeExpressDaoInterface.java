package com.fla.common.dao.interfaces;

import java.util.List;
import java.util.Map;

import com.fla.common.base.MapperAnnotation;
import com.fla.common.base.SuperMapper;
import com.fla.common.entity.BarcodeExpress;
import com.fla.common.entity.ExpressInfo;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

@MapperAnnotation
public interface BarcodeExpressDaoInterface extends SuperMapper{
	public List<BarcodeExpress> getBarcodeExpressList(Map<String,Object> params,PageBounds pageBounds);
	public BarcodeExpress getBarcodeExpressById(Map<String,Object> params);
	public List<BarcodeExpress> getBarcodeExpressByBarCode(Map<String,Object> params);
	public void insert(BarcodeExpress barcode);
	public List<ExpressInfo> getExpressInfoByParams(Map<String,Object> params);
	public Integer checkOrderPaymentStatus(Map<String, Object> params);
	public Integer checkExpressIdPrint(Map<String, Object> params);
	public Integer checkMember(Map<String,Object> params);
}

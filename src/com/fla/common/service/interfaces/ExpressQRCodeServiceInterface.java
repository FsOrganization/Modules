package com.fla.common.service.interfaces;

import java.util.List;
import java.util.Map;

import com.fla.common.base.SuperService;
import com.fla.common.dao.interfaces.ExpressQRCodeDaoInterface;
import com.fla.common.entity.AnnotationBarcodeBasketNumber;
import com.fla.common.entity.AnnotationBarcodeLocation;
import com.fla.common.entity.BarcodeExpress;
import com.fla.common.entity.ExpressInfo;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

public interface ExpressQRCodeServiceInterface extends SuperService<ExpressQRCodeDaoInterface> {
	public List<BarcodeExpress> getBarCodeByExpress(Map<String,Object> params,PageBounds pageBounds);
	public List<ExpressInfo> getExpressByBarCode(Map<String,Object> params);
	
	public void deleteBarcodeLocationAnnotation(AnnotationBarcodeLocation annotation);
	public void newBarcodeLocationAnnotation(AnnotationBarcodeLocation annotation);
	public AnnotationBarcodeLocation getBarcodeLocationAnnotation(Map<String,Object> params);
	public List<AnnotationBarcodeLocation> getBarcodeLocationAnnotationList(Map<String,Object> params);
	
	public void deleteNumberingBarcode(Map<String,Object> params);
	public void updateNumberingBarcode(Map<String,Object> params);
	public void numberingBarcode(AnnotationBarcodeBasketNumber annotationBarcodeBasketNumber);
	public AnnotationBarcodeBasketNumber getBarcodeExpressNumber(Map<String,Object> params);
	public List<AnnotationBarcodeBasketNumber> getBarcodeExpressNumberList(Map<String,Object> params);
	public void releaseNumberingByBarcode(Map<String,Object> params);
	public void releaseNumberingByElapsed(Map<String,Object> params);
}

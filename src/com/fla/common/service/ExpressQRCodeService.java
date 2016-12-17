package com.fla.common.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fla.common.base.SuperServiceAdapter;
import com.fla.common.dao.interfaces.ExpressQRCodeDaoInterface;
import com.fla.common.entity.AnnotationBarcodeBasketNumber;
import com.fla.common.entity.AnnotationBarcodeLocation;
import com.fla.common.entity.BarcodeExpress;
import com.fla.common.entity.ExpressInfo;
import com.fla.common.service.interfaces.ExpressQRCodeServiceInterface;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

@Service
@Transactional
public class ExpressQRCodeService extends SuperServiceAdapter<ExpressQRCodeDaoInterface> implements ExpressQRCodeServiceInterface{

	@Autowired
    private ApplicationContext applicationContext;
	
	@Autowired
	@Override
	public void setMapper(ExpressQRCodeDaoInterface mapper) {
		this.mapper = mapper;
	}

	public ExpressQRCodeService() {
	}

	@Override
	public List<BarcodeExpress> getBarCodeByExpress(Map<String, Object> params,
			PageBounds pageBounds) {
		return mapper.getBarCodeByExpress(params, pageBounds);
	}

	@Override
	public List<ExpressInfo> getExpressByBarCode(Map<String, Object> params) {
		return mapper.getExpressByBarCode(params);
	}

	@Override
	public void deleteBarcodeLocationAnnotation(AnnotationBarcodeLocation annotation) {
		mapper.deleteBarcodeLocationAnnotation(annotation);
	}

	@Override
	public void newBarcodeLocationAnnotation(AnnotationBarcodeLocation annotation) {
		mapper.newBarcodeLocationAnnotation(annotation);
	}

	@Override
	public AnnotationBarcodeLocation getBarcodeLocationAnnotation(
			Map<String, Object> params) {
		return mapper.getBarcodeLocationAnnotation(params);
	}

	@Override
	public List<AnnotationBarcodeLocation> getBarcodeLocationAnnotationList(
			Map<String, Object> params) {
		return mapper.getBarcodeLocationAnnotationList(params);
	}

	@Override
	public void deleteNumberingBarcode(Map<String, Object> params) {
		mapper.deleteNumberingBarcode(params);
	}

	@Override
	public void updateNumberingBarcode(Map<String, Object> params) {
		mapper.updateNumberingBarcode(params);
	}

	@Override
	public void numberingBarcode(AnnotationBarcodeBasketNumber annotationBarcodeBasketNumber) {
		mapper.numberingBarcode(annotationBarcodeBasketNumber);
	}

	@Override
	public AnnotationBarcodeBasketNumber getBarcodeExpressNumber(Map<String, Object> params) {
		return mapper.getBarcodeExpressNumber(params);
	}

	@Override
	public List<AnnotationBarcodeBasketNumber> getBarcodeExpressNumberList(Map<String, Object> params) {
		return mapper.getBarcodeExpressNumberList(params);
	}

	@Override
	public void releaseNumberingByBarcode(Map<String, Object> params) {
		mapper.releaseNumberingByBarcode(params);
	}

	@Override
	public void releaseNumberingByElapsed(Map<String, Object> params) {
		mapper.releaseNumberingByElapsed(params);
	}

}

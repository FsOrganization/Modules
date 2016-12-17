package com.fla.common.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fla.common.base.SuperController;
import com.fla.common.entity.AnnotationBarcodeBasketNumber;
import com.fla.common.entity.AnnotationBarcodeLocation;
import com.fla.common.entity.BarcodeExpress;
import com.fla.common.entity.ExpressInfo;
import com.fla.common.entity.SystemUser;
import com.fla.common.service.interfaces.ExpressQRCodeServiceInterface;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

@Controller
public class TakeExpressController extends SuperController{

	private static final long serialVersionUID = 4261410489019839536L;

	@Autowired
	private ExpressQRCodeServiceInterface expressQRCodeService;
	
	public TakeExpressController() {
	}
	
	@ResponseBody
	@RequestMapping("/pages/system/takeByQR/getBarCodeByExpress.light")
	public void getBarCodeByExpress(String barCode, Integer page, Integer rows, HttpServletRequest request, HttpServletResponse response) {
		PrintWriter printWriter = null;
		SystemUser systemUser = getSystemUser(request, response);
		Map<String, Object> params = new HashMap<String, Object>();
		PageBounds pageBounds = new PageBounds(page, rows);
		try 
		{
			params.put("barCode", barCode);
			params.put("serviceShopCode", systemUser.getServiceShopCode());
			List<BarcodeExpress> bList = expressQRCodeService.getBarCodeByExpress(params, pageBounds);
			JSONArray jsonArray = JSONArray.fromObject(bList);
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
			printWriter = response.getWriter();
			printWriter.write(jsonArray.toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			printWriter.flush();
			printWriter.close();
		}
	}
	
	@ResponseBody
	@RequestMapping("/pages/system/takeByQR/getExpressByBarCode.light")
	public void getExpressByBarCode(String barCode, HttpServletRequest request, HttpServletResponse response) {
		PrintWriter printWriter = null;
//		SystemUser systemUser = getSystemUser(request, response);
		Map<String, Object> params = new HashMap<String, Object>();
		try 
		{
			params.put("barCode", barCode);
//			params.put("serviceShopCode", systemUser.getServiceShopCode());
			List<ExpressInfo> bList = expressQRCodeService.getExpressByBarCode(params);
			JSONArray jsonArray = JSONArray.fromObject(bList);
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
			printWriter = response.getWriter();
			printWriter.write(jsonArray.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			printWriter.flush();
			printWriter.close();
		}
	}
	
	@ResponseBody
	@RequestMapping("/pages/system/takeByQR/newBarcodeLocationAnnotation.light")
	public void newBarcodeLocationAnnotation(AnnotationBarcodeLocation annotation, HttpServletRequest request, HttpServletResponse response) {
		PrintWriter printWriter = null;
		SystemUser systemUser = getSystemUser(request, response);
		JSONObject json = new JSONObject();
		try 
		{
			annotation.setOperaTime(new Date());
			annotation.setOperator(systemUser.getLoginName());
			annotation.setServiceShopCode(systemUser.getServiceShopCode());
			expressQRCodeService.newBarcodeLocationAnnotation(annotation);
			json.put("msg", "标注成功");
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
			printWriter = response.getWriter();
			printWriter.write(json.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			printWriter.flush();
			printWriter.close();
		}
	}
	
	@ResponseBody
	@RequestMapping("/pages/system/takeByQR/getBarcodeLocationAnnotationList.light")
	public void getBarcodeLocationAnnotationList(AnnotationBarcodeLocation annotation, HttpServletRequest request, HttpServletResponse response) {
		PrintWriter printWriter = null;
		SystemUser systemUser = getSystemUser(request, response);
		JSONObject json = new JSONObject();
		Map<String, Object> params = new HashMap<String, Object>();
		try 
		{
//			annotation.setServiceShopCode(systemUser.getServiceShopCode());
			params.put("barCode", annotation.getBarCode());
			params.put("serviceShopCode", systemUser.getServiceShopCode());
			List<AnnotationBarcodeLocation> annList= expressQRCodeService.getBarcodeLocationAnnotationList(params);
			AnnotationBarcodeBasketNumber annNum = expressQRCodeService.getBarcodeExpressNumber(params);
			List<AnnotationBarcodeBasketNumber> annNumList= expressQRCodeService.getBarcodeExpressNumberList(params);
			JSONArray jsonArray = JSONArray.fromObject(annList);
			JSONArray jsonArrayAnnNumList = JSONArray.fromObject(annNumList);
			json.put("annList", jsonArray);
			json.put("annNum", annNum == null ? "" : annNum.getBasketNumber());
			json.put("annNumList", jsonArrayAnnNumList);
//			json.put("msg", "标注成功");
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
			printWriter = response.getWriter();
			printWriter.write(json.toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			printWriter.flush();
			printWriter.close();
		}
	}
	
	@ResponseBody
	@RequestMapping("/pages/system/takeByQR/deleteBarcodeLocationAnnotation.light")
	public void deleteBarcodeLocationAnnotation(AnnotationBarcodeLocation annotation, HttpServletRequest request, HttpServletResponse response) {
		PrintWriter printWriter = null;
		SystemUser systemUser = getSystemUser(request, response);
		JSONObject json = new JSONObject();
		try 
		{
			annotation.setServiceShopCode(systemUser.getServiceShopCode());
			expressQRCodeService.deleteBarcodeLocationAnnotation(annotation);
			json.put("msg", "清除成功");
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
			printWriter = response.getWriter();
			printWriter.write(json.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			printWriter.flush();
			printWriter.close();
		}
	}
	
	@ResponseBody
	@RequestMapping("/pages/system/takeByQR/getBarcodeExpressNumber.light")
	public void getBarcodeExpressNumber(AnnotationBarcodeBasketNumber basketNumber, HttpServletRequest request, HttpServletResponse response) {
		PrintWriter printWriter = null;
		SystemUser systemUser = getSystemUser(request, response);
		JSONObject json = new JSONObject();
		Map<String, Object> params = new HashMap<String, Object>();
		try 
		{
//			annotation.setServiceShopCode(systemUser.getServiceShopCode());
			params.put("barCode", basketNumber.getBarCode());
			params.put("serviceShopCode", systemUser.getServiceShopCode());
			AnnotationBarcodeBasketNumber annotationBarcodeBasketNumber= expressQRCodeService.getBarcodeExpressNumber(params);
			json.put("basketNumber", annotationBarcodeBasketNumber.getBasketNumber());
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
			printWriter = response.getWriter();
			printWriter.write(json.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			printWriter.flush();
			printWriter.close();
		}
	}
	
	@ResponseBody
	@RequestMapping("/pages/system/takeByQR/numberingBarcode.light")
	public void numberingBarcode(AnnotationBarcodeBasketNumber ann, HttpServletRequest request, HttpServletResponse response) {
		PrintWriter printWriter = null;
		SystemUser systemUser = getSystemUser(request, response);
		JSONObject json = new JSONObject();
		Map<String, Object> params = new HashMap<String, Object>();
		try 
		{
			ann.setOperaTime(new Date());
			ann.setOperator(systemUser.getLoginName());
			ann.setServiceShopCode(systemUser.getServiceShopCode());
			params.put("serviceShopCode", ann.getServiceShopCode());
			params.put("barCode", ann.getBarCode());
			expressQRCodeService.deleteNumberingBarcode(params);
			expressQRCodeService.numberingBarcode(ann);
			json.put("msg", "标注成功");
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
			printWriter = response.getWriter();
			printWriter.write(json.toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			printWriter.flush();
			printWriter.close();
		}
	}
	
	@ResponseBody
	@RequestMapping("/pages/system/takeByQR/releaseNumberingByBarcode.light")
	public void releaseNumberingByBarcode(AnnotationBarcodeLocation annotation, HttpServletRequest request, HttpServletResponse response) {
		PrintWriter printWriter = null;
		SystemUser systemUser = getSystemUser(request, response);
		JSONObject json = new JSONObject();
		Map<String, Object> params = new HashMap<String, Object>();
		try 
		{
			params.put("serviceShopCode", systemUser.getServiceShopCode());
			params.put("barCode", annotation.getBarCode());
			expressQRCodeService.releaseNumberingByBarcode(params);
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
			printWriter = response.getWriter();
			printWriter.write(json.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			printWriter.flush();
			printWriter.close();
		}
	}
	
}

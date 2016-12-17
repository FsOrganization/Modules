package com.fla.common.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fla.common.base.SuperController;
import com.fla.common.dao.LoginDao;
import com.fla.common.entity.SystemUser;
import com.fla.common.service.interfaces.MerchandiseServiceInterface;
import com.fla.common.util.DateUtil;
import com.fla.common.util.Pagination;
import com.fla.common.util.PaginationUtils;
import com.fla.payment.weixinPay.util.pay.Pay;
import com.fla.payment.weixinPay.util.pay.WxPayDto;

@Controller
public class MerchandiseController extends SuperController{

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private LoginDao loginDao;
	
	@Autowired
	private MerchandiseServiceInterface merchandiseService;
	
	@org.springframework.web.bind.annotation.InitBinder
	public void InitBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, null, new CustomDateEditor(dateFormat, true));
	}

	@ResponseBody
	@RequestMapping("/pages/system/getMerchandiseList.light")
	public void getMerchandiseList(HttpServletRequest request,HttpServletResponse response) throws SQLException, IOException {
		PrintWriter printWriter = null;
		SystemUser s = getSystemUser(request, response);
		try 
		{
			String rows = request.getParameter("rows"); 
			String page = request.getParameter("page");
			final int rowSize=Integer.valueOf(rows); 
			final int pageSize=Integer.valueOf(page);
			String areaCode = s.getAreaCode();
			String shopCode = s.getServiceShopCode();
			Map<String,String> params = new HashMap<String,String>(2);
			params.put("areaCode", areaCode);
			params.put("shopCode", shopCode);
			Pagination data = merchandiseService.getMerchandiseList(rowSize, pageSize, params);
			String d = PaginationUtils.getData(pageSize, rowSize, data);
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
            printWriter = response.getWriter();
            printWriter.write(d.toString());
		} finally {
			printWriter.flush();
			printWriter.close();
		}

	}
	
	@ResponseBody
	@RequestMapping("/pages/system/getWeixinPayCodeURL.light")
	public void getWeixinPayCodeURL(HttpServletRequest request,HttpServletResponse response) throws SQLException, IOException {
		PrintWriter printWriter = null;
		String name = request.getParameter("name");
		String fee = request.getParameter("fee");
		JSONObject json = new JSONObject();
		System.out.println("name:"+name);
		try 
		{
			// 扫码支付
			WxPayDto tpWxPay1 = new WxPayDto();
			tpWxPay1.setBody(name);
			tpWxPay1.setOrderId(Pay.getNonceStr());
			tpWxPay1.setSpbillCreateIp("121.41.76.133");// 订单生成的机器 IP
			tpWxPay1.setTotalFee(fee);//fee
			String codeUrl = Pay.getCodeurl(tpWxPay1);
			json.put("codeUrl", codeUrl);
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
            printWriter = response.getWriter();
            printWriter.write(json.toString());
		} finally {
			printWriter.flush();
			printWriter.close();
		}

	}
	
	@ResponseBody
	@RequestMapping("/pages/system/addMerchandise.light")
	public void addMerchandise(HttpServletRequest request,HttpServletResponse response) throws SQLException, IOException {
		PrintWriter printWriter = null;
		JSONObject json = new JSONObject();
		SystemUser s = getSystemUser(request, response);
		try 
		{
			String areaCode = s.getAreaCode();
			String shopCode = s.getServiceShopCode();
			Map<String,String> params = new HashMap<String,String>(2);
			params = getData(request);
			params.put("areaCode", areaCode);
			params.put("shopCode", shopCode);
			json = merchandiseService.addMerchandise(params);
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
            printWriter = response.getWriter();
            printWriter.write(json.toString());
		} finally {
			printWriter.flush();
			printWriter.close();
		}

	}
	
	@ResponseBody
	@RequestMapping("/pages/system/modifyMerchandiseInfo.light")
	public void modifyMerchandiseInfo(HttpServletRequest request,HttpServletResponse response) throws SQLException, IOException {
		PrintWriter printWriter = null;
		JSONObject json = new JSONObject();
		SystemUser s = getSystemUser(request, response);
		try 
		{
			String areaCode = s.getAreaCode();
			String shopCode = s.getServiceShopCode();
			Map<String,String> params = new HashMap<String,String>(2);
			params = getData(request);
			params.put("areaCode", areaCode);
			params.put("shopCode", shopCode);
			json = merchandiseService.modifyMerchandiseInfo(params);
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
            printWriter = response.getWriter();
            printWriter.write(json.toString());
		} finally {
			printWriter.flush();
			printWriter.close();
		}

	}
	
	private Map<String, String> getData(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", request.getParameter("id"));
		map.put("name", request.getParameter("name"));
		map.put("barcode", request.getParameter("barcode"));
//		map.put("areaCode", request.getParameter("areaCode"));
//		map.put("shopCode", request.getParameter("shopCode"));
		map.put("validity", request.getParameter("validity"));
		map.put("productionDate", request.getParameter("productionDate"));
		map.put("placeOrigin", request.getParameter("placeOrigin"));
		map.put("productionImage", request.getParameter("productionImage"));
		map.put("inventory", request.getParameter("inventory"));
		map.put("unitPrice", request.getParameter("unitPrice"));
		map.put("marketingActivities",request.getParameter("marketingActivities"));
		map.put("batchNumber", request.getParameter("batchNumber"));
		map.put("description", request.getParameter("description"));
		map.put("brand", request.getParameter("brand"));
		map.put("manufacturer", request.getParameter("manufacturer"));
		map.put("specification", request.getParameter("specification"));
		map.put("operaTime", DateUtil.formatDateToString(new Date()));
		map.put("remark", request.getParameter("remark"));
		map.put("version", "1");
		return map;
	}
	
}
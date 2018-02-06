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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fla.common.base.SuperController;
import com.fla.common.controller.Config.ControllerSite;
import com.fla.common.controller.Config.PropertiesConfig;
import com.fla.common.service.interfaces.BasePayServiceInterface;
import com.fla.common.service.interfaces.LoginServiceInterface;
import com.fla.payment.weixinPay.util.pay.Pay;
import com.fla.payment.weixinPay.util.pay.WxPayDto;

@Controller
public class BasePayController extends SuperController{

	private static final long serialVersionUID = -9188166050181372461L;

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(BasePayController.class);
	
	private static final String IP = PropertiesConfig.getPropertiesByKey("system.ip");
	
	/**
	 * dateFormatStyle1:yyyy-MM-dd
	 */
	public static final SimpleDateFormat dateFormatStyle1= new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 * dateFormatStyle2:yyyy-MM-dd HH:mm:ss
	 */
	public static final SimpleDateFormat dateFormatStyle2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Autowired
	private BasePayServiceInterface basePayService;
	
	@Autowired
	private LoginServiceInterface loginService;
	
	@org.springframework.web.bind.annotation.InitBinder
	public void InitBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
		dateFormatStyle2.setLenient(false);
		binder.registerCustomEditor(Date.class, null, new CustomDateEditor(dateFormatStyle2, true));
	}
	
	@ResponseBody
	@RequestMapping("/pages/system/base/getPayCodeURL.light")
	public void getPayCodeURL(HttpServletRequest request,HttpServletResponse response) throws SQLException, IOException {
		PrintWriter printWriter = null;
		String name = request.getParameter("name");
		String fee = request.getParameter("fee");
		JSONObject json = new JSONObject();
		String nonceStr = Pay.getNonceStr();
		try 
		{
			// 扫码支付
			WxPayDto tpWxPay1 = new WxPayDto();
			tpWxPay1.setBody(name);
			tpWxPay1.setOrderId(nonceStr);
			tpWxPay1.setSpbillCreateIp(IP);// 订单生成的机器 IP
			tpWxPay1.setTotalFee(fee);//fee
			String codeUrl = Pay.getCodeurl(tpWxPay1);
			json.put("codeUrl", codeUrl);
			json.put("orderCode", nonceStr);
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
	@RequestMapping("/pages/system/base/checkOrderPaymentStatus.light")
	public void  checkOrderPaymentStatus(String orderCode,Integer page, Integer rows,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("orderCode", orderCode);
		Integer paymentCount = basePayService.checkOrderPaymentStatus(params);
		JSONObject json = new JSONObject(); 
		if (paymentCount >0) {
			json.put("payment", "YES");
		} else {
			json.put("payment", "NO");
		}
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		printWriter.write(json.toString());
        printWriter.flush(); 
        printWriter.close();
	}
	
}
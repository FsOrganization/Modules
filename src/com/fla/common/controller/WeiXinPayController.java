package com.fla.common.controller;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fla.common.base.SuperController;
import com.fla.common.controller.Config.ReceiveXmlAnalyze;
import com.fla.common.entity.PayLog;
import com.fla.common.service.interfaces.PayLogServiceInterface;
import com.fla.payment.weixinPay.util.pay.WxPayResult;

@Controller
public class WeiXinPayController extends SuperController{
	
	private static final long serialVersionUID = -8658436453785031478L;
	
	@Autowired
	private PayLogServiceInterface payLogService;

	@org.springframework.web.bind.annotation.InitBinder
	public void InitBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, null, new CustomDateEditor(dateFormat, true));
	}
	
	@ResponseBody
	@RequestMapping("/pages/system/weixin/notify.light")
	public void getSimplyConstructedQueryPage(HttpServletRequest request,HttpServletResponse response) {
		String result = "";
		try 
		{
			InputStream inStream = request.getInputStream();
			ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = inStream.read(buffer)) != -1) {
				outSteam.write(buffer, 0, len);
			}
			outSteam.close();
			inStream.close();
			result = new String(outSteam.toByteArray(), "utf-8");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			String resXml = "";
			PayLog log = new PayLog();
			BufferedOutputStream out = null;
			try 
			{
				WxPayResult res = ReceiveXmlAnalyze.parseXml(result);
				log.setContent(result);
				log.setFee(res.getTotalFee());
				log.setOrderId(res.getOutTradeNo());
				log.setPhoneNumber("");
				log.setRemark(res.getFeeType());
				log.setServiceIdentifier(res.getOpenid());
				log.setServiceName("微信支付");
				log.setStatus(res.getResultCode());
				log.setTitle(res.getBankType());
				log.setType("微信扫码支付");
				if("SUCCESS".equals(res.getResultCode())) {
		            resXml = "<xml>" 
		            					+ "<return_code><![CDATA[SUCCESS]]></return_code>"
		            					+ "<return_msg><![CDATA[OK]]></return_msg>" 
		            			+ "</xml> ";
		        } else {
		            resXml = "<xml>" 
		            					+ "<return_code><![CDATA[FAIL]]></return_code>"
		            					+ "<return_msg><![CDATA[报文解析错误]]></return_msg>" 
		            			+ "</xml> ";
		        }
		        payLogService.insert(log);
		        out = new BufferedOutputStream(response.getOutputStream());
		        out.write(resXml.getBytes());
			} catch (Exception e) {
				log.setContent(e.getMessage());
				payLogService.insert(log);
			} finally {
				try 
				{
					out.flush();
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
		
	}

}
package com.fla.common.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fla.common.entity.SystemUser;
import com.fla.common.sms.MessageServer;

@Controller
public class SendMsgController{
	
	private final static String head="【源信幸福驿站】";
	
	@ResponseBody
	@RequestMapping("/pages/system/sms/sendRemindersToCustomer.light")
	public synchronized void sendRemindersToCustomer(HttpServletRequest request,HttpServletResponse response) {
		SystemUser s = (SystemUser) request.getSession().getAttribute("systemUser");
		try 
		{
			String mobile = request.getParameter("PHONE_NUMBER");
//			String areaCode = request.getParameter("AREA_CODE");
//			String shopCode = s.getServiceShopCode();
			String logistics = request.getParameter("LOGISTICS");
//			String  expressService= request.getParameter("EXPRESS_SERVICE");
//			String  operaTime= request.getParameter("OPERA_TIME");
			StringBuffer msg = new StringBuffer();
			msg.append("尊敬的客户！您的快递:");
			msg.append(logistics);
//			msg.append("(");
//			msg.append(expressService);
//			msg.append(")");
			msg.append(" 已到达");
//			msg.append(shopCode);
			msg.append("源信幸福驿站，请尽快领取。");
			MessageServer ms = new MessageServer(head);
			ms.send(mobile,msg.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//	 public static void main(String[] args)throws Exception{
//		 MessageServer ms = new MessageServer(head);
//		 ms.send("13980051144","傅傅");
//	 }

}

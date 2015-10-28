package com.fla.common.controller;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fla.common.entity.SystemUser;
import com.fla.common.entity.Test;
import com.fla.common.service.interfaces.SystemServiceInterface;
import com.fla.common.sms.MessageServer;

@Controller
public class SendMsgController{
	
	private final static String head="【源信幸福驿站】";
	
	@Autowired
	private SystemServiceInterface systemServiceInterface;
	
	@ResponseBody
	@RequestMapping("/pages/system/sms/sendRemindersToCustomer.light")
	public synchronized void sendRemindersToCustomer(HttpServletRequest request,HttpServletResponse response) {
		SystemUser s = (SystemUser) request.getSession().getAttribute("systemUser");
		try 
		{
			String mobile = request.getParameter("PHONE_NUMBER");
//			String areaCode = request.getParameter("AREA_CODE");
			String shopCode = s.getServiceShopCode();
			String shopName = systemServiceInterface.getShopNameByCode(shopCode);
			String logistics = request.getParameter("LOGISTICS");
//			String  expressService= request.getParameter("EXPRESS_SERVICE");
//			String  operaTime= request.getParameter("OPERA_TIME");
			StringBuffer msg = new StringBuffer();
			msg.append("尊敬的客户！您的快递单号为:");
			msg.append(logistics);
//			msg.append("(");
//			msg.append(expressService);
//			msg.append(")");
			msg.append(" 的包裹已到 ");
			msg.append(shopName);
			msg.append(" 源信幸福驿站，请尽快领取。如有疑问请致电：18311599537");
			MessageServer ms = new MessageServer(head);
			ms.send(mobile,msg.toString());
//			System.out.println(msg.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	 public static void main(String[] args)throws Exception{
//		 MessageServer ms = new MessageServer(head);
	 }

}

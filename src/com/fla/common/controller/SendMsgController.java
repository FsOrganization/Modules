package com.fla.common.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fla.common.entity.SystemUser;
import com.fla.common.service.interfaces.MsgServiceInterface;
import com.fla.common.service.interfaces.SystemServiceInterface;
import com.fla.common.util.Automatic.AutomaticMsgUtils;

@Controller
public class SendMsgController{
	
	private final static String head="【源信幸福驿站】";
	
	@Autowired
	private SystemServiceInterface systemService;
	
	@Autowired
	private MsgServiceInterface msgService;
	
	@ResponseBody
	@RequestMapping("/pages/system/sms/sendRemindersToCustomer.light")
	public  void sendRemindersToCustomer(HttpServletRequest request,HttpServletResponse response) {
		SystemUser systemUser = (SystemUser) request.getSession().getAttribute("systemUser");
		boolean msgTag = (boolean) request.getSession().getAttribute("msgTag");
 		if (!msgTag) {
			return;
		}
 		String shopCode = systemUser.getServiceShopCode();
		String shopName = systemService.getShopNameByCode(shopCode);
 		String phoneNumber = request.getParameter("PHONE_NUMBER");
 		String logistics = request.getParameter("LOGISTICS");
 		Map<String,String> paraMap = new HashMap<String,String>();
 		paraMap.put("phoneNumber",phoneNumber);
 		paraMap.put("shopName",shopName);
 		paraMap.put("logistics",logistics);
 		AutomaticMsgUtils.sendRemindersToCustomer(paraMap);
	}

}

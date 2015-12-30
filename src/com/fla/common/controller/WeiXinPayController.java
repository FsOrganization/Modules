package com.fla.common.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fla.common.base.SuperController;
import com.fla.common.weixin.util.WechatProcess;
import com.fla.common.weixin.util.xml.ReceiveXmlEntity;

@Controller
public class WeiXinPayController extends SuperController{
	
	private static final long serialVersionUID = -8658436453785031478L;

	@org.springframework.web.bind.annotation.InitBinder
	public void InitBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, null, new CustomDateEditor(dateFormat, true));
	}
	
	@ResponseBody
	@RequestMapping("/pages/system/weixin/notify.light")
	public void getSimplyConstructedQueryPage(HttpServletRequest request,HttpServletResponse response) {
		try 
		{
			InputStream inStream = request.getInputStream();
			ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = inStream.read(buffer)) != -1) {
				outSteam.write(buffer, 0, len);
			}
			System.out.println("-----------------------付款成功-----------------------");
			outSteam.close();
			inStream.close();
			String result = new String(outSteam.toByteArray(), "utf-8");
//			ReceiveXmlEntity rxe = new WechatProcess().processWechatMag(result);
			System.out.println("支付返回："+result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
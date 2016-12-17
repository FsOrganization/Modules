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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fla.common.base.SuperController;
import com.fla.common.entity.PayLog;
import com.fla.common.service.interfaces.LoginServiceInterface;
import com.fla.common.service.interfaces.PayLogServiceInterface;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

@Controller
public class PayController extends SuperController{

	private static final long serialVersionUID = -9188166050181372461L;

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(PayController.class);
	
	@Autowired
	private PayLogServiceInterface payLogService;
	
	@Autowired
	private LoginServiceInterface loginService;
	
	@org.springframework.web.bind.annotation.InitBinder
	public void InitBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
		simpleDateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, null, new CustomDateEditor(simpleDateFormat, true));
	}

	@ResponseBody
	@RequestMapping("/pages/system/pay/getPayLogs.light")
	public void  getPayLogs(String queryParams,Integer page, Integer rows,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		PageBounds pageBounds = new PageBounds(page, rows);
		Map<String,Object> params = new HashMap<String,Object>();
		List<PayLog> payLogList = payLogService.getPayLogs(params, pageBounds);
		JSONArray jsonArray = JSONArray.fromObject(payLogList);
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		printWriter.write(jsonArray.toString());
        printWriter.flush(); 
        printWriter.close();
        
	}
	
}
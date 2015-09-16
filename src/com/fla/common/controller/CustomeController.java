package com.fla.common.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fla.common.base.SuperController;
import com.fla.common.entity.SystemUser;
import com.fla.common.service.interfaces.CustomerServiceInterface;

@Controller
public class CustomeController extends SuperController{

	private static final long serialVersionUID = 4261410489019839536L;

	@Autowired
	private CustomerServiceInterface customerService;
	
	public CustomeController() {
	}
	
	@ResponseBody
	@RequestMapping("/pages/system/customer/getCustomerList.light")
	public ModelAndView getCustomerList(HttpServletRequest request,HttpServletResponse response){
		SystemUser systemUser = (SystemUser) request.getSession().getAttribute("systemUser");
		if (systemUser ==null) {
			return JumpModelAndView();
		}
		String rows = request.getParameter("rows"); 
		String page = request.getParameter("page");
		int rowSize = 0;
		int pageSize = 1;
		if (rows != null ) {
			rowSize=Integer.valueOf(rows); 
		}
		if (page != null ) {
			pageSize=Integer.valueOf(page);
		}
		Map<String,String> params = new HashMap<String,String>();
		JSONArray jsonArray = customerService.getCustomerList(rowSize, pageSize, params);
		PrintWriter printWriter =null;
		try 
		{
			response.setCharacterEncoding("utf-8");          
			response.setContentType("text/html; charset=utf-8");
            printWriter = response.getWriter();
            printWriter.write(jsonArray.toString());
		} catch(IOException e) {
		} finally { 
			printWriter.flush();
			printWriter.close();
		}
		return null;
	}

}

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
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.InternalResourceView;
import com.fla.common.base.SuperController;
import com.fla.common.service.interfaces.LoginServiceInterface;
import com.fla.common.service.interfaces.SystemServiceInterface;

@Controller
public class HighchartsController extends SuperController{
	
	private static final long serialVersionUID = 8137315174834581896L;
	
	@Autowired
	private SystemServiceInterface systemServiceInterface;
	
	@Autowired
	private LoginServiceInterface loginServiceInterface;
	
	@org.springframework.web.bind.annotation.InitBinder
	public void InitBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, null, new CustomDateEditor(dateFormat, true));
	}
	
	@ResponseBody
	@RequestMapping("/pages/system/getHighchartsController.light")
	public ModelAndView getAreaInfoList(HttpServletRequest request,HttpServletResponse response) {
		InternalResourceView iv = new InternalResourceView("/pages/business/test/weixinQueryPage.jsp");
		ModelAndView model = new ModelAndView(iv);
		model.addObject("time", System.currentTimeMillis());
		model.addObject("tag", 1);
		return model;
	}
	
	@ResponseBody
	@RequestMapping("/pages/system/getHighchartsControllerFilter.light")
	public void  getHighchartsControllerFilter(String queryParams,int tag,
			HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		if (tag == 1) {
//			String rows = request.getParameter("rows"); 
//			String page = request.getParameter("page");
			final int rowSize=10;
			final int pageSize=1;
			Map<String,String> params = new HashMap<String,String>();
			params.put("endDate", "");
			params.put("startDate", "");
			params.put("queryParams", queryParams);
			params.put("expressService", "");
			params.put("areaCode", "");
			params.put("serviceShopCode", "");
			JSONArray ja = loginServiceInterface.getSimplyConstructedNotOutExpressInfoByFilter(rowSize, pageSize,params);
			response.setCharacterEncoding("utf-8");          
			response.setContentType("text/html; charset=utf-8");
			 PrintWriter printWriter = response.getWriter();
			printWriter.write(ja.toString()); 
	        printWriter.flush(); 
	        printWriter.close(); 
		} 

	}

}
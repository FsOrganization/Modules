package com.fla.common.controller;

import it.sauronsoftware.base64.Base64;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
<<<<<<< HEAD
import net.sf.json.JSONObject;
=======
>>>>>>> d0b5484a9bee2dc897836974fbc92e4f813785b1

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fla.common.base.SuperController;
import com.fla.common.service.interfaces.SQLPlusServiceInterface;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

@Controller
public class SQLPlusController extends SuperController{

	private static final long serialVersionUID = -474271871208322282L;

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(SQLPlusController.class);
	
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	@Autowired
	private SQLPlusServiceInterface SQLPlusService;
	
	@org.springframework.web.bind.annotation.InitBinder
	public void InitBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, null, new CustomDateEditor(dateFormat, true));
	}

	@ResponseBody
	@RequestMapping("/pages/system/sql/execute.light")
<<<<<<< HEAD
	public void  execute(String queryParams, String dynamicPassword, Integer page, Integer rows,
			HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		PageBounds pageBounds = new PageBounds(page, rows);
		Map<String,Object> params = new HashMap<String,Object>();
		String exeSql = null;
		if (queryParams != null) {
			exeSql = Base64.decode(queryParams.trim());
		}
=======
	public void  execute(String queryParams,Integer page, Integer rows,
			HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		PageBounds pageBounds = new PageBounds(page, rows);
		Map<String,Object> params = new HashMap<String,Object>();
		String exeSql = Base64.decode(queryParams);
>>>>>>> d0b5484a9bee2dc897836974fbc92e4f813785b1
		if (exeSql.startsWith("select") || exeSql.startsWith("SELECT")) {
			params.put("privatesql", Base64.decode(queryParams));
			List<Map<String,Object>> list = SQLPlusService.execute(params, pageBounds);
			JSONArray jsonArray = JSONArray.fromObject(list);
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
			PrintWriter printWriter = response.getWriter();
			printWriter.write(jsonArray.toString());
	        printWriter.flush(); 
	        printWriter.close();
<<<<<<< HEAD
		} else if(exeSql.startsWith("update") || exeSql.startsWith("UPDATE")){
			JSONObject json = new JSONObject();
			if (dynamicPassword == null || !dynamicPassword.equals("n48gj3")) {
				json.put("msg", "请求被拒绝!");
			} else {
				params.put("privatesql", Base64.decode(queryParams));
				SQLPlusService.executeDML(params);
				json.put("msg", "操作成功!");
			}
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
			PrintWriter printWriter = response.getWriter();
			printWriter.write(json.toString());
	        printWriter.flush(); 
	        printWriter.close();
		} else if(exeSql.startsWith("insert") || exeSql.startsWith("INSERT")){
			JSONObject json = new JSONObject();
			if (dynamicPassword == null || !dynamicPassword.equals("a5376852cc3797d31d513c8d2c4eca52")) {
				json.put("msg", "请求被拒绝!");
			} else {
				params.put("privatesql", Base64.decode(queryParams));
				SQLPlusService.executeDML(params);
				json.put("msg", "操作成功!");
			}
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
			PrintWriter printWriter = response.getWriter();
			printWriter.write(json.toString());
	        printWriter.flush(); 
	        printWriter.close();
=======
>>>>>>> d0b5484a9bee2dc897836974fbc92e4f813785b1
		} else {
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
			PrintWriter printWriter = response.getWriter();
			printWriter.write("错误或不支持的语法格式！");
	        printWriter.flush(); 
	        printWriter.close();
		}
		
	}

}
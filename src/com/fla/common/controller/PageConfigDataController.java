package com.fla.common.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fla.common.base.SuperController;
import com.fla.common.entity.SystemUser;
import com.fla.common.service.interfaces.LoginServiceInterface;
import com.fla.common.service.interfaces.SystemServiceInterface;

@Controller
public class PageConfigDataController extends SuperController{

	private static final long serialVersionUID = -222343456360345672L;
//	private static final Logger logger = LoggerFactory.getLogger(PageToolsController.class);

	@Autowired
	private LoginServiceInterface loginServiceInterface;
	
	@Autowired
	private SystemServiceInterface systemServiceInterface;
	
	@org.springframework.web.bind.annotation.InitBinder
	public void InitBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, null, new CustomDateEditor(dateFormat, true));
	}
	
	@ResponseBody
	@RequestMapping("/pages/system/pageconfig/getPageGender.light")
	public void getPageGender(HttpServletRequest request,HttpServletResponse response) {
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonMale = new JSONObject();
		jsonMale.put("value", "male");
		jsonMale.put("text", "男");
		JSONObject jsonFemale = new JSONObject();
		jsonFemale.put("value", "female");
		jsonFemale.put("text", "女");
		JSONObject jsonSecrecy = new JSONObject();
		jsonSecrecy.put("value", "secrecy");
		jsonSecrecy.put("text", "保密");
		JSONObject jsonUnknown = new JSONObject();
		jsonUnknown.put("value", "unknown");
		jsonUnknown.put("text", "未知");
		jsonArray.add(jsonMale);
		jsonArray.add(jsonFemale);
		jsonArray.add(jsonSecrecy);
		jsonArray.add(jsonUnknown);
		PrintWriter printWriter =null;
		try 
		{
			response.setCharacterEncoding("utf-8");          
			response.setContentType("text/html; charset=utf-8");
            printWriter = response.getWriter();
            printWriter.write(jsonArray.toString());
		} catch(IOException e) {
			e.printStackTrace();
		} finally { 
			printWriter.flush();
			printWriter.close();
		}
	}
	
	@ResponseBody
	@RequestMapping("/pages/system/pageconfig/getPageGenderForWechat.light")
	public void getPageGenderForWechat(HttpServletRequest request,HttpServletResponse response) {
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonMale = new JSONObject();
		jsonMale.put("value", "male");
		jsonMale.put("text", "男");
		JSONObject jsonFemale = new JSONObject();
		jsonFemale.put("value", "female");
		jsonFemale.put("text", "女");
		jsonArray.add(jsonMale);
		jsonArray.add(jsonFemale);
		PrintWriter printWriter =null;
		try 
		{
			response.setCharacterEncoding("utf-8");          
			response.setContentType("text/html; charset=utf-8");
            printWriter = response.getWriter();
            printWriter.write(jsonArray.toString());
		} catch(IOException e) {
			e.printStackTrace();
		} finally { 
			printWriter.flush();
			printWriter.close();
		}
	}
	
	@ResponseBody
	@RequestMapping("/pages/system/pageconfig/getAgeSection.light")
	public void getAgeSection(HttpServletRequest request,HttpServletResponse response) {
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonSecton_1 = new JSONObject();
		jsonSecton_1.put("value", "secton_1");
		jsonSecton_1.put("text", "14岁以下");
		JSONObject jsonSecton_2  = new JSONObject();
		jsonSecton_2.put("value", "secton_2");
		jsonSecton_2.put("text", "15至29岁");
		JSONObject jsonSecton_3  = new JSONObject();
		jsonSecton_3.put("value", "secton_3");
		jsonSecton_3.put("text", "30至44岁");
		JSONObject jsonSecton_4  = new JSONObject();
		jsonSecton_4.put("value", "secton_4");
		jsonSecton_4.put("text", "45至59岁");
		JSONObject jsonSecton_5  = new JSONObject();
		jsonSecton_5.put("value", "secton_5");
		jsonSecton_5.put("text", "60岁以上");
		JSONObject jsonUnknown  = new JSONObject();
		jsonUnknown.put("value", "unknown");
		jsonUnknown.put("text", "未知");
		jsonArray.add(jsonSecton_1);
		jsonArray.add(jsonSecton_2);
		jsonArray.add(jsonSecton_3);
		jsonArray.add(jsonSecton_4);
		jsonArray.add(jsonSecton_5);
		jsonArray.add(jsonUnknown);
		PrintWriter printWriter =null;
		try 
		{
			response.setCharacterEncoding("utf-8");          
			response.setContentType("text/html; charset=utf-8");
            printWriter = response.getWriter();
            printWriter.write(jsonArray.toString());
		} catch(IOException e) {
			e.printStackTrace();
		} finally { 
			printWriter.flush();
			printWriter.close();
		}
	}
	
	@ResponseBody
	@RequestMapping("/pages/system/pageconfig/getAgeSectionForWechat.light")
	public void getAgeSectionForWechat(HttpServletRequest request,
			HttpServletResponse response) {
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonSecton_1 = new JSONObject();
		jsonSecton_1.put("value", "secton_1");
		jsonSecton_1.put("text", "14岁以下");
		JSONObject jsonSecton_2 = new JSONObject();
		jsonSecton_2.put("value", "secton_2");
		jsonSecton_2.put("text", "15至29岁");
		JSONObject jsonSecton_3 = new JSONObject();
		jsonSecton_3.put("value", "secton_3");
		jsonSecton_3.put("text", "30至44岁");
		JSONObject jsonSecton_4 = new JSONObject();
		jsonSecton_4.put("value", "secton_4");
		jsonSecton_4.put("text", "45至59岁");
		JSONObject jsonSecton_5 = new JSONObject();
		jsonSecton_5.put("value", "secton_5");
		jsonSecton_5.put("text", "60岁以上");
		jsonArray.add(jsonSecton_1);
		jsonArray.add(jsonSecton_2);
		jsonArray.add(jsonSecton_3);
		jsonArray.add(jsonSecton_4);
		jsonArray.add(jsonSecton_5);
		PrintWriter printWriter = null;
		try 
		{
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
			printWriter = response.getWriter();
			printWriter.write(jsonArray.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			printWriter.flush();
			printWriter.close();
		}
	}
	
	@ResponseBody
	@RequestMapping("/pages/system/pageconfig/getWhetherHaveCar.light")
	public void getWhetherHaveCar(HttpServletRequest request,HttpServletResponse response) {
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonSecton_1 = new JSONObject();
		jsonSecton_1.put("value", "yes");
		jsonSecton_1.put("text", "有");
		JSONObject jsonSecton_2  = new JSONObject();
		jsonSecton_2.put("value", "no");
		jsonSecton_2.put("text", "无");
		JSONObject jsonUnknown = new JSONObject();
		jsonUnknown.put("value", "unknown");
		jsonUnknown.put("text", "未知");
		jsonArray.add(jsonSecton_1);
		jsonArray.add(jsonSecton_2);
		jsonArray.add(jsonUnknown);
		PrintWriter printWriter =null;
		try 
		{
			response.setCharacterEncoding("utf-8");          
			response.setContentType("text/html; charset=utf-8");
            printWriter = response.getWriter();
            printWriter.write(jsonArray.toString());
		} catch(IOException e) {
			e.printStackTrace();
		} finally { 
			printWriter.flush();
			printWriter.close();
		}
	}
	
	@ResponseBody
	@RequestMapping("/pages/system/pageconfig/getServiceShopName.light")
	public void getServiceShopName(HttpServletRequest request,HttpServletResponse response) {
		PrintWriter printWriter = null;
		SystemUser s = getSystemUser(request, response);
		Map<String, String> params =  new HashMap<String, String>();
		params.put("loginName", s.getLoginName());
		if (!s.getLoginName().equals("admin")) {
			params.put("areaCode", s.getAreaCode());
		}
		JSONArray jsonArray = systemServiceInterface.getShopInfoList(0, 0, params);
		try 
		{
			response.setCharacterEncoding("utf-8");          
			response.setContentType("text/html; charset=utf-8");
            printWriter = response.getWriter();
            printWriter.write(jsonArray.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			printWriter.flush();
			printWriter.close();
		}
	}
	
}
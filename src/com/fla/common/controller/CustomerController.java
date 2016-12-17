package com.fla.common.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fla.common.base.SuperController;
import com.fla.common.entity.CustomerInfo;
import com.fla.common.entity.SystemUser;
import com.fla.common.service.interfaces.CustomerServiceInterface;
import com.fla.common.util.ChinesePinYin;
import com.fla.common.util.Pagination;
import com.fla.common.util.PaginationUtils;

@Controller
public class CustomerController extends SuperController{

	private static final long serialVersionUID = 4261410489019839536L;

	@Autowired
	private CustomerServiceInterface customerService;
	
	public CustomerController() {
	}
	
	@ResponseBody
	@RequestMapping("/pages/system/customer/getCustomerListByTxt.light")
	public void getCustomerListByTxt(HttpServletRequest request, HttpServletResponse response,String queryTxt) {
		PrintWriter printWriter = null;
		SystemUser systemUser =getSystemUser(request, response);
		Map<String, String> params = new HashMap<String, String>();
//		String queryTxt = request.getParameter("queryTxt");
		try 
		{
			queryTxt = URLDecoder.decode(queryTxt, "UTF-8");
			params.put("queryTxt", queryTxt);
			params.put("shopCode", systemUser.getServiceShopCode());
			JSONArray ja = customerService.getCustomerListByTxt(params);
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
			printWriter = response.getWriter();
			printWriter.write(ja.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			printWriter.flush();
			printWriter.close();
		}
	}
	
	@ResponseBody
	@RequestMapping("/pages/system/customer/getCustomerList.light")
	public void getCustomerList(Integer page, Integer rows,
			HttpServletRequest request, HttpServletResponse response) {
		PrintWriter printWriter = null;
		SystemUser systemUser = getSystemUser(request, response);
		Map<String, String> params = new HashMap<String, String>();
		String queryParams = request.getParameter("queryParams");
		String pageShopCode = request.getParameter("shopCode");
		try 
		{
			queryParams = URLDecoder.decode(queryParams, "UTF-8");
			params.put("queryParams", queryParams);
			if (pageShopCode == null || pageShopCode.trim().length() == 0) {
				params.put("shopCode", systemUser.getServiceShopCode());
			} else {
				params.put("shopCode", pageShopCode);
			}
			Pagination data = customerService.getCustomerList(rows, page,params);
			String d = PaginationUtils.getData(page, rows, data);
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
			printWriter = response.getWriter();
			printWriter.write(d);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			printWriter.flush();
			printWriter.close();
		}
	}
	
	@ResponseBody
	@RequestMapping("/pages/system/modifyCustomer.light")
	public void modifyCustomer(HttpServletRequest request,HttpServletResponse response)  throws SQLException, IOException {
		JSONObject json = new JSONObject();
		CustomerInfo customer=null;
		try 
		{
			customer = jsonToCustomerEntity(request);
			customerService.modifyCustomerInfo(customer);
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		json.put("msg", "保存成功");
		response.setCharacterEncoding("utf-8");          
		response.setContentType("text/html; charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		printWriter.write(json.toString()); 
        printWriter.flush();
        printWriter.close(); 
	}
	
	@ResponseBody
	@RequestMapping("/pages/system/registerCustomerByOpenId.light")
	public void registerCustomerByOpenId(HttpServletRequest request,HttpServletResponse response)  throws SQLException, IOException {
		JSONObject json = new JSONObject();
		CustomerInfo customer=null;
		try 
		{
			customer = jsonToCustomerEntityByWechat(request);
			customerService.registerCustomerByOpenId(customer);
		} catch (Exception e) {
			e.printStackTrace();
		}
		json.put("msg", "注册成功");
		response.setCharacterEncoding("utf-8");          
		response.setContentType("text/html; charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		printWriter.write(json.toString()); 
        printWriter.flush();
        printWriter.close();
	}
	
	@ResponseBody
	@RequestMapping("/pages/system/customer/updateCustomerGender.light")
	public void updateCustomerGender(HttpServletRequest request,HttpServletResponse response)  throws SQLException, IOException {
		String phoneNumber = request.getParameter("phoneNumber");
		String sex = request.getParameter("sex");
		try 
		{
			customerService.updateCustomerGender(phoneNumber,sex);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@ResponseBody
	@RequestMapping("/pages/system/addCustomer.light")
	public void addCustomer(HttpServletRequest request,HttpServletResponse response)  throws SQLException, IOException {
		JSONObject json = new JSONObject();
		CustomerInfo customer=null;
		try 
		{
			customer = jsonToCustomerEntity(request);
			customerService.modifyCustomerInfo(customer);
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		json.put("msg", "保存成功");
		response.setCharacterEncoding("utf-8");          
		response.setContentType("text/html; charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		printWriter.write(json.toString()); 
        printWriter.flush();
        printWriter.close(); 
	}
	
	private CustomerInfo jsonToCustomerEntity(HttpServletRequest request) throws UnsupportedEncodingException{
		String name = request.getParameter("name");
		String address = request.getParameter("address");
		String ageSection = request.getParameter("ageSection");
		String gender = request.getParameter("gender");
		String whetherHaveCar = request.getParameter("whetherHaveCar");
		String phoneNumber = request.getParameter("phoneNumber");
		String isInterest = request.getParameter("isInterest");
		
		CustomerInfo cc = new CustomerInfo();
		String customerId  = request.getParameter("id");
		cc.setId(Integer.valueOf(customerId));
		cc.setName(name);
		cc.setAddress(address);
		cc.setAgeSection(ageSection);
		cc.setWhetherHaveCar(whetherHaveCar);
		cc.setGender(gender);
		cc.setPhoneNumber(phoneNumber);
		cc.setIsInterest(isInterest);
		return cc;
	}
	
	private CustomerInfo jsonToCustomerEntityByWechat(HttpServletRequest request) throws UnsupportedEncodingException{
		String openId = request.getParameter("openId");
		String shopCode = request.getParameter("shopCode");
		String phoneNumber = request.getParameter("phoneNumber");
//		String gender = request.getParameter("gender");
//		String ageSection = request.getParameter("ageSection");
		String name = request.getParameter("name");
		
		CustomerInfo cc = new CustomerInfo();
		cc.setWeixinId(openId);
		cc.setShopCode(shopCode);
		cc.setPhoneNumber(phoneNumber);
		cc.setGender("unknown");
		cc.setAgeSection("unknown");
		cc.setWhetherHaveCar("unknown");
		cc.setName(name);
		String initialsCode = ChinesePinYin.initials(name);
		cc.setInitialsCode(initialsCode);
		String spellingCode = ChinesePinYin.spelling(name);
		cc.setSpellingCode(spellingCode);
		return cc;
	}

}

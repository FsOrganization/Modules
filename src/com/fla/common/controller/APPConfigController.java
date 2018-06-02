package com.fla.common.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fla.common.base.SuperController;
import com.fla.common.entity.CustomerInfo;
import com.fla.common.entity.ExpressProviderContacts;
import com.fla.common.entity.SentExpressInfo;
import com.fla.common.entity.SystemRole;
import com.fla.common.entity.SystemUser;
import com.fla.common.service.interfaces.APPConfigServiceInterface;
import com.fla.common.service.interfaces.SystemServiceInterface;
import com.fla.common.util.ChinesePinYin;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class APPConfigController extends SuperController {
	private static final long serialVersionUID = -5706874180405978990L;

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(APPConfigController.class);

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	@Autowired
	private APPConfigServiceInterface appConfigService;
	
	@Autowired
	private SystemServiceInterface systemService;

	@org.springframework.web.bind.annotation.InitBinder
	public void InitBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, null, new CustomDateEditor(dateFormat, true));
	}

	@ResponseBody
	@RequestMapping("/pages/app/config/registeredCustomers.light")
	public void getRegisteredCustomers(String phoneNumber, String shopCode,
			Integer page, Integer rows, 
			HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException {
		SystemUser s = getSystemUser(request, response);
		PageBounds pageBounds = new PageBounds(page, rows);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("phoneNumber", phoneNumber);
		params.put("shopCode", shopCode);
		params.put("areaCode", s.getAreaCode());
		List<CustomerInfo> list = appConfigService.getRegisteredCustomers(params, pageBounds);
		for (CustomerInfo cusm : list) {
			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("phoneNumber", cusm.getPhoneNumber());
			List<ExpressProviderContacts> epcs = appConfigService.getProviderContacts(queryMap);
			if(epcs != null && epcs.size() != 0) {
				cusm.setProviderContacts(epcs);
			}
		}
		JSONArray jsonArray = JSONArray.fromObject(list);
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		PageList<CustomerInfo> pageList = (PageList<CustomerInfo>)list;
		JSONObject dataJson = new JSONObject();
		dataJson.put("total", pageList.getPaginator().getTotalCount());
		dataJson.put("message", "");
		dataJson.put("numPerPage", pageList.getPaginator().getLimit());
		dataJson.put("pages", pageList.getPaginator().getTotalPages());
		dataJson.put("currentPage", pageList.getPaginator().getPrePage());
		dataJson.put("rows", jsonArray);
		printWriter.write(dataJson.toString());
		printWriter.flush();
		printWriter.close();
	}
	
	@ResponseBody
	@RequestMapping("/pages/app/config/getShopGroupByArea.light")
	public void getShopGroupByArea(String str, HttpServletRequest request,HttpServletResponse response) throws SQLException, IOException {
		SystemUser s = getSystemUser(request, response);
		PrintWriter printWriter = null;
		Map<String, Object> params = new HashMap<String, Object>();
		if (s != null) {
			params.put("areaCode", s.getAreaCode());
			params.put("loginName", s.getLoginName());
		}
//		JSONArray jsonArray = systemService.getShopGroupByArea(params);
		JSONArray jsonArray = systemService.getExpressStatisticalArea(params);
		try 
		{
			response.setCharacterEncoding("utf-8");          
			response.setContentType("text/html; charset=utf-8");
            printWriter = response.getWriter();
            printWriter.write(jsonArray.toString());
		} finally {
			printWriter.flush();
			printWriter.close();
		}
	}
	
	@ResponseBody
	@RequestMapping("/pages/app/config/setCustomerBeProviderContacts.light")
	public void setCustomerBeProviderContacts(ExpressProviderContacts expressProviderContacts, HttpServletRequest request,HttpServletResponse response)  throws SQLException, IOException {
		JSONObject js = new JSONObject();
		try {
			appConfigService.setCustomerBeProviderContacts(expressProviderContacts);
			js.put("msg", "设置成功");
		} catch (Exception e) {
			js.put("msg", e.getMessage());
		}
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		printWriter.write(js.toString());
		printWriter.flush();
		printWriter.close();
	}
	
	@ResponseBody
	@RequestMapping("/pages/app/config/getCustomerProviderContacts.light")
	public void getCustomerProviderContacts(ExpressProviderContacts expressProviderContacts, HttpServletRequest request,HttpServletResponse response)  throws SQLException, IOException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("phoneNumber", expressProviderContacts.getPhoneNumber());
		List<ExpressProviderContacts> proList = appConfigService.getProviderContacts(params);
		JSONArray jsonArray = JSONArray.fromObject(proList);
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		printWriter.write(jsonArray.toString());
		printWriter.flush();
		printWriter.close();
	} 
}
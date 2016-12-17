package com.fla.common.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import com.fla.common.entity.SystemConfig;
import com.fla.common.entity.SystemUser;
import com.fla.common.service.interfaces.SystemConfigServiceInterface;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

@Controller
public class SystemConfigController extends SuperController {

	private static final long serialVersionUID = 1L;

	@Autowired
	private SystemConfigServiceInterface systemConfigService;

	@org.springframework.web.bind.annotation.InitBinder
	public void InitBinder(HttpServletRequest request,ServletRequestDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, null, new CustomDateEditor(dateFormat, true));
	}

	@ResponseBody
	@RequestMapping("/pages/system/config/getSystemConfigList.light")
	public String getSystemConfigList(Integer page, Integer rows, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> params = new HashMap<String, Object>();
		String configName = request.getParameter("queryParams");
		PageBounds pageBounds = new PageBounds(page, rows);
		params.put("status", "1");
		params.put("configName", configName == null ? null : configName);
		System.out.println(params);
		List<SystemConfig> configList = systemConfigService.getSystemConfigList(params, pageBounds);
		JSONArray jsonArray = JSONArray.fromObject(configList);
		return jsonArray.toString();
	}
	
	@ResponseBody
	@RequestMapping("/pages/system/config/saveSystemConfig.light")
	public String saveMenu(HttpServletRequest request,HttpServletResponse response,SystemConfig config)  {
		JSONObject json = new JSONObject();
		SystemUser systemUser = getSystemUser(request, response);
		try 
		{
			config.setRemark(systemUser.getLoginName() + " " + simpleDateFormat.format(new Date()));
			systemConfigService.saveEntity(config);
			json.put("msg", "保存成功");
		} catch (Exception e) {
			json.put("msg", e.getMessage());
			e.printStackTrace();
		}
		return json.toString();
	}
	
}
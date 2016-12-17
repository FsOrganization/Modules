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

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

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
import com.fla.common.entity.SysMenu;
import com.fla.common.entity.SystemUser;
import com.fla.common.service.interfaces.MenuServiceInterface;
import com.fla.common.util.ChinesePinYin;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

@Controller
public class MenuController extends SuperController{

	private static final long serialVersionUID = -8384196233388218070L;
	
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(MenuController.class);
	
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	@Autowired
	private MenuServiceInterface menuService;
	
	@org.springframework.web.bind.annotation.InitBinder
	public void InitBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, null, new CustomDateEditor(dateFormat, true));
	}

	@ResponseBody
	@RequestMapping("/pages/system/menu/getMenuList.light")
	public void  getMenuList(String queryParams,Integer page, Integer rows,
			HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		PageBounds pageBounds = new PageBounds(page, rows);
		SysMenu menu=new SysMenu();
		if (queryParams  != null && !queryParams.equals("")) {
			menu.setName(queryParams);
		}
		menu.setLevel(1);
		List<SysMenu> list = menuService.getMainLevelMenuList(menu, pageBounds);
		JSONArray jsonArray = JSONArray.fromObject(list);
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		printWriter.write(jsonArray.toString());
        printWriter.flush(); 
        printWriter.close();
	}
	
	@ResponseBody
	@RequestMapping("/pages/system/menu/getMenuListByCombobox.light")
	public void  getMenuListByCombobox(String abstractLevel,Integer page, Integer rows,
			HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		SysMenu menu=new SysMenu();
		menu.setAbstractLevel(abstractLevel);
		JSONArray ja = new JSONArray();
		List<SysMenu> list = menuService.getAbstractMenuList(menu);
		for (SysMenu s : list) {
			JSONObject json = new JSONObject();
			json.put("id", s.getId());
			json.put("text", s.getName());
			json.put("desc", s.getAbstractLevel().equals("Y")?"抽象菜单":"可点击菜单");
			ja.add(json);
		}
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		printWriter.write(ja.toString());
        printWriter.flush(); 
        printWriter.close();
	}
	
	@ResponseBody
	@RequestMapping("/pages/system/menu/getMenuListByParentId.light")
	public void  getMenuListByParentId(String parentId,Integer page, Integer rows,
			HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		PageBounds pageBounds = new PageBounds(page, rows);
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("parentId", parentId);
		params.put("status", "Y");
		List<SysMenu> list = menuService.getMenuListByParentId(params, pageBounds);
		JSONArray jsonArray = JSONArray.fromObject(list);
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		printWriter.write(jsonArray.toString());
        printWriter.flush(); 
        printWriter.close();
	}
	
	
	@ResponseBody
	@RequestMapping("/pages/system/menu/saveMenu.light")
	public void saveMenu(HttpServletRequest request,HttpServletResponse response)  throws SQLException, IOException {
		JSONObject json = new JSONObject();
		SysMenu menu=null;
		try 
		{
			menu = jsonToMenuEntity(request);
			menuService.saveEntity(menu);
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
	
	private SysMenu jsonToMenuEntity(HttpServletRequest request) throws UnsupportedEncodingException{
		String id = request.getParameter("id");
		String parentId = request.getParameter("parentId");
		String url = request.getParameter("url");
		String name = request.getParameter("name");
		String displayOrder = request.getParameter("displayOrder");
		String status = request.getParameter("status");
		String level = request.getParameter("level");
		String abstractLevel = request.getParameter("abstractLevel");
		SysMenu menu = new SysMenu();
		if (parentId != null && !parentId.equals("")) {
			menu.setParentId(Integer.valueOf(parentId));
		}
		if (id != null && !id.equals("")) {
			menu.setId(Integer.valueOf(id));
		}
		menu.setUrl(url);
		menu.setName(name);
		if (displayOrder != null && !displayOrder.equals("")) {
			menu.setDisplayOrder(Integer.valueOf(displayOrder));
		}
		if (level != null && !level.equals("")) {
			menu.setLevel(Integer.valueOf(level));
		}
		menu.setStatus(status);
		menu.setAbstractLevel(abstractLevel);
		menu.setCode(ChinesePinYin.spelling(name));
		SystemUser systemUser = getSystemUser(request, null);
		menu.setLastDate(dateFormat.format(new Date()));
		menu.setLastOperator(systemUser.getLoginName());
		return menu;
	}

}
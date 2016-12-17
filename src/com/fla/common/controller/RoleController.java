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
import com.fla.common.entity.SysMenu;
import com.fla.common.entity.SysRoleMenu;
import com.fla.common.entity.SystemRole;
import com.fla.common.entity.SystemUser;
import com.fla.common.service.interfaces.MenuServiceInterface;
import com.fla.common.service.interfaces.RoleServiceInterface;
import com.fla.common.util.ChinesePinYin;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

@Controller
public class RoleController extends SuperController{
	private static final long serialVersionUID = -5706874180405978990L;

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(RoleController.class);
	
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	@Autowired
	private RoleServiceInterface roleService;
	
	@Autowired
	private MenuServiceInterface menuService;
	
	@org.springframework.web.bind.annotation.InitBinder
	public void InitBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, null, new CustomDateEditor(dateFormat, true));
	}

	@ResponseBody
	@RequestMapping("/pages/system/role/getRoleList.light")
	public void  getMenuList(String queryParams,Integer page, Integer rows,
			HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		PageBounds pageBounds = new PageBounds(page, rows);
		Map<String,Object> params = new HashMap<String,Object>();
		if (queryParams != null && !queryParams.equals("")) {
			params.put("name",queryParams);
		}
		List<SystemRole> list = roleService.getSysRoleList(params, pageBounds);
		JSONArray jsonArray = JSONArray.fromObject(list);
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		printWriter.write(jsonArray.toString());
        printWriter.flush(); 
        printWriter.close();
	}
	
	@ResponseBody
	@RequestMapping("/pages/system/role/addRole.light")
	public void saveMenu(HttpServletRequest request,HttpServletResponse response)  throws SQLException, IOException {
		JSONObject json = new JSONObject();
		SystemRole role=null;
		try 
		{
			role = jsonToRoleEntity(request);
			roleService.saveEntity(role);
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
	@RequestMapping("/pages/system/role/menuInRole.light")
	public void menuInRole(HttpServletRequest request,HttpServletResponse response)  throws SQLException, IOException {
		JSONObject json = new JSONObject();
		SystemRole role=null;
		try 
		{
			String menuId = request.getParameter("menuId");
//			String parentId = request.getParameter("parentId");
			String roleId = request.getParameter("roleId");
			SysRoleMenu srm = new SysRoleMenu();
			srm.setRoleId(Integer.valueOf(roleId));
			srm.setMenuId(Integer.valueOf(menuId));
			srm.setStatus("Y");
			roleService.addMenuInRole(srm);
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
	@RequestMapping("/pages/system/role/menuOutRole.light")
	public void menuOutRole(HttpServletRequest request,HttpServletResponse response)  throws SQLException, IOException {
		JSONObject json = new JSONObject();
		SystemRole role=null;
		try 
		{
			String menuId = request.getParameter("menuId");
			String roleId = request.getParameter("roleId");
			SysRoleMenu srm = new SysRoleMenu();
			srm.setRoleId(Integer.valueOf(roleId));
			srm.setMenuId(Integer.valueOf(menuId));
			roleService.menuOutRole(srm);
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		json.put("msg", "移除成功");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		printWriter.write(json.toString());
        printWriter.flush();
        printWriter.close();
	}
	
	@ResponseBody
	@RequestMapping("/pages/system/role/getMenuTree.light")
	public void getMenuTree(String roleId,Integer page, Integer rows,
			HttpServletRequest request, HttpServletResponse response) throws IOException   {
//		PageBounds pageBounds = new PageBounds(page, rows);
		SysMenu ss = new SysMenu(); 
		ss.setLevel(1);
		List<SysMenu> list = menuService.getMainLevelMenuList(ss);
		Map<String,Object> roleMap = new HashMap<String,Object>(1);
		roleMap.put("roleId", roleId);
		List<Integer> menuList = roleService.getMenuListByRoleId(roleMap);
		JSONArray tree = new JSONArray();
		Map<String,Object> params = new HashMap<String,Object>(2);
 		for (SysMenu sm : list) {
 			JSONArray cArray = new JSONArray();
 			params.put("parentId", sm.getId());
 			params.put("status", "Y");
 			JSONObject json = new JSONObject();
 			json.put("id", sm.getId());
 			json.put("name", sm.getName());
 			json.put("parentId", "");
 			json.put("isSelect", menuList.contains(sm.getId())?"Y":"N");
 			json.put("type", "folder");
// 			JSONArray tArray = new JSONArray();
 			List<SysMenu> childNodes = menuService.getMenuListByParentId(params);
 			for (SysMenu cNode : childNodes) {
 				JSONObject js = new JSONObject();
 	 			js.put("id", cNode.getId());
 	 			js.put("name", cNode.getName());
 	 			js.put("parentId", sm.getId());
 	 			js.put("isSelect", menuList.contains(cNode.getId())?"Y":"N");
 	 			js.put("type", "folder");
 	 			Map<String,Object> params2 = new HashMap<String,Object>(2);
 				params2.put("parentId", cNode.getId());
 				params2.put("status", "Y");
 	 			List<SysMenu> cNodeChildNodes = menuService.getMenuListByParentId(params2);
 	 			JSONArray endArray = new JSONArray();
 	 			for (SysMenu tm : cNodeChildNodes) {
 	 				JSONObject tjs = new JSONObject();
 	 				tjs.put("id", tm.getId());
 	 				tjs.put("name", tm.getName());
 	 				tjs.put("parentId", cNode.getId());
 	 				tjs.put("isSelect", menuList.contains(tm.getId())?"Y":"N");
 	 				tjs.put("type", "pack");
 	 				endArray.add(tjs);
				}
 	 			js.put("children", endArray);
 	 			cArray.add(js);
			}
 			json.put("children", cArray);
 			tree.add(json);
		}
//		JSONArray jsonArray = JSONArray.fromObject(list);
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		printWriter.write(tree.toString());
        printWriter.flush(); 
        printWriter.close();
	}
	
	private SystemRole jsonToRoleEntity(HttpServletRequest request) throws UnsupportedEncodingException{
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String displayOrder = request.getParameter("displayOrder");
		String status = request.getParameter("status");
		SystemRole role = new SystemRole();
		if (id != null && !id.equals("")) {
			role.setId(Integer.valueOf(id));
		}
		role.setName(name);
		if (displayOrder != null && !displayOrder.equals("")) {
			role.setDisplayOrder(Integer.valueOf(displayOrder));
		}
		role.setStatus(status);
		role.setCode(ChinesePinYin.spelling(name));
		SystemUser systemUser = getSystemUser(request, null);
		role.setLastDate(dateFormat.format(new Date()));
		role.setLastOperator(systemUser.getLoginName());
		return role;
	}

}
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
import com.fla.common.entity.SysUserRole;
import com.fla.common.entity.SystemRole;
import com.fla.common.entity.SystemUser;
import com.fla.common.service.interfaces.MenuServiceInterface;
import com.fla.common.service.interfaces.RoleServiceInterface;
import com.fla.common.service.interfaces.UserRoleServiceInterface;
import com.fla.common.util.ChinesePinYin;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

@Controller
public class UserRoleController extends SuperController{
	private static final long serialVersionUID = -5706874180405978990L;

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(UserRoleController.class);
	
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	@Autowired
	private RoleServiceInterface roleService;
	
	@Autowired
	private MenuServiceInterface menuService;
	
	@Autowired
	private UserRoleServiceInterface userRoleService;
	
	@org.springframework.web.bind.annotation.InitBinder
	public void InitBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, null, new CustomDateEditor(dateFormat, true));
	}

	@ResponseBody
	@RequestMapping("/pages/system/userRole/getUserRoleList.light")
	public void  getUserRoleList(String queryUserName, String queryRoleName, Integer page, Integer rows,
			HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		PageBounds pageBounds = new PageBounds(page, rows);
		Map<String,Object> params = new HashMap<String,Object>();
		if (queryUserName != null && queryUserName.length() != 0) {
			params.put("nickName", queryUserName);
		}
		if (queryRoleName != null && queryRoleName.length() != 0) {
			params.put("roleName", queryRoleName);
		}
		params.put("status", "Y");
		List<SysUserRole> list = userRoleService.getUserRoleList(params, pageBounds);
		JSONArray jsonArray = JSONArray.fromObject(list);
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		printWriter.write(jsonArray.toString());
        printWriter.flush();
        printWriter.close();
	}
	
	@ResponseBody
	@RequestMapping("/pages/system/userRole/deleteUserRole.light")
	public void deleteUserRole(HttpServletRequest request, HttpServletResponse response){
		JSONObject json = new JSONObject();
		Map<String, Object> params = new HashMap<String, Object>();
		String id = request.getParameter("id");
		String userId = request.getParameter("userId");
		String roleId = request.getParameter("roleId");
		PrintWriter printWriter = null;
		params.put("id", id);
		params.put("userId", userId);
		params.put("roleId", roleId);
		try 
		{
			userRoleService.deleteUserRole(params);
			json.put("msg", "删除成功");
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
			printWriter = response.getWriter();
			printWriter.write(json.toString());
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			printWriter.flush();
			printWriter.close();
		}
	}
	
	@ResponseBody
	@RequestMapping("/pages/system/userRole/saveConfig.light")
	public void saveConfig(HttpServletRequest request, HttpServletResponse response){
		JSONObject json = new JSONObject();
		PrintWriter printWriter = null;
		Map<String, Object> params = new HashMap<String, Object>();
		String users = request.getParameter("users");
		String roles = request.getParameter("roles");
		String[] userList = users.split(",");
		String[] roleList = roles.split(",");
		try 
		{
			for (String uId : userList) {
				for (String rId : roleList) {
					params.put("userId", uId);
					params.put("roleId", rId);
					params.put("status", "Y");
					Integer count = userRoleService.checkUserRoleCount(params);
					if (count != null && count == 0) {
						userRoleService.addUserRole(params);
					}
				}
			}
			json.put("msg", "删除成功");
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
			printWriter = response.getWriter();
			printWriter.write(json.toString());
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			printWriter.flush();
			printWriter.close();
		}
	}
	
	@ResponseBody
	@RequestMapping("/pages/system/userRole/getRoleList.light")
	public void  getRoleList(String roleSearch,Integer page, Integer rows,
			HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		PageBounds pageBounds = new PageBounds(page, rows);
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("name", roleSearch);
		List<SystemRole> list = userRoleService.getRoleList(params, pageBounds);
		JSONArray jsonArray = JSONArray.fromObject(list);
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		printWriter.write(jsonArray.toString());
        printWriter.flush();
        printWriter.close();
	}
	
	@ResponseBody
	@RequestMapping("/pages/system/userRole/getUserList.light")
	public void  getUserList(String userSearch,Integer page, Integer rows,
			HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		PageBounds pageBounds = new PageBounds(page, rows);
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("nickName", userSearch);
		params.put("loginName", "admin");
		List<SystemUser> list = userRoleService.getUserList(params, pageBounds);
		JSONArray jsonArray = JSONArray.fromObject(list);
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		printWriter.write(jsonArray.toString());
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
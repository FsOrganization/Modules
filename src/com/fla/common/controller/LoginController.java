package com.fla.common.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;
import net.sourceforge.barbecue.output.OutputException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.InternalResourceView;

import com.fla.common.base.SuperController;
import com.fla.common.dao.interfaces.SystemConfigDaoInterface;
import com.fla.common.entity.ExpressInfo;
import com.fla.common.entity.SysMenu;
import com.fla.common.entity.SystemUser;
import com.fla.common.service.interfaces.LoginServiceInterface;
import com.fla.common.service.interfaces.MenuServiceInterface;
import com.fla.common.service.interfaces.MsgServiceInterface;
import com.fla.common.service.interfaces.ScanneServiceInterface;
import com.fla.common.service.interfaces.SystemServiceInterface;
import com.fla.common.service.interfaces.UserRoleServiceInterface;
import com.fla.common.util.MD5Utils;

@Controller
public class LoginController extends SuperController{

	private static final long serialVersionUID = -92227420050300172L;
	
	@Autowired
	private ScanneServiceInterface scanneService;
	
	@Autowired
	private MenuServiceInterface menuService;
	
	@Autowired
	private UserRoleServiceInterface userRoleService;
	
	@Autowired
	private SystemConfigDaoInterface systemConfigDao;
	
	@Autowired
	public MsgServiceInterface msgService;
	
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private LoginServiceInterface loginService;
	
	@Autowired
	private SystemServiceInterface systemServiceInterface;
	
	@org.springframework.web.bind.annotation.InitBinder
	public void InitBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, null, new CustomDateEditor(dateFormat, true));
	}
	
	@ResponseBody
//	@RequestMapping(value = "/login/{name:.+}", method = RequestMethod.GET)
	@RequestMapping("/pages/system/welcome.light")
	public ModelAndView welcome(HttpServletRequest request, HttpServletResponse response,String name) throws SQLException {
		request.getSession().setAttribute("systemUser", null);
		// logger.debug("welcome() - name {}", name);
		InternalResourceView iv = new InternalResourceView("/pages/login.jsp");
		ModelAndView model = new ModelAndView(iv);
		model.addObject("name", name);
		model.addObject("time", System.currentTimeMillis());
		return model;
	}

	private void checkRememberMe(ModelAndView model , String rememberMe, String name){
		if ("on".equalsIgnoreCase(rememberMe)) {
			model.addObject("remember_me", "checked");
			model.addObject("name", name);
		} else {
			model.addObject("remember_me", false);
			model.addObject("name", "");
		}
	}
	
	private InternalResourceView serviceViewApp(){
		InternalResourceView iv = new InternalResourceView("/pages/business/test/autoDimensionalCode/serviceViewApp.jsp");
		return iv;
	}
	
	/**
	 * 登录验证&参数初始化
	 * @param request
	 * @param response
	 * @return
	 * @throws SQLException
	 */
	@ResponseBody
	@RequestMapping("/pages/system/login.light")
	public ModelAndView  login(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		String name  = request.getParameter("name");
		String pd  = request.getParameter("password");
		String rememberMe  = request.getParameter("remember_me");
		InternalResourceView iv = new InternalResourceView("/pages/index.jsp");
		ModelAndView model = new ModelAndView(iv);
		checkRememberMe(model, rememberMe, name);
		SystemUser systemUser = loginService.checkLoginAction(name);
		if (systemUser.getLoginName() != null) 
		{
			String pageCode = MD5Utils.encodeMd5(pd, name);
			if (!pageCode.equals(systemUser.getPassword())) {
				iv = new InternalResourceView("/pages/login.jsp");
				model = new ModelAndView(iv);
				model.addObject("msg", "用户名或密码错误");
				model.addObject("msgType", "-1");
			} else {
				model.addObject("loginName",name);
				model.addObject("nickName",systemUser.getNickName());
				model.addObject("userMode",systemUser.getUserMode());
				model.addObject("shopName", systemUser.getShopName());
				model.addObject("areaName", systemUser.getAreaName());
				request.getSession().setAttribute("systemUser", systemUser);
				HashSet <String>list = msgService.getSendMsgShopStringList(new HashMap<String, String>());
				if (list.contains(systemUser.getServiceShopCode())) {
					request.getSession().setAttribute("msgTag", true);
				}else {
					request.getSession().setAttribute("msgTag", false);
				}
<<<<<<< HEAD
//				model = new ModelAndView(iv);
				String lateFeeLimitUpper = (String) request.getSession().getAttribute("lateFeeLimitUpper");
				if (lateFeeLimitUpper == null || lateFeeLimitUpper.trim().equals("")) {
					Map<String,Object> params = new HashMap<String,Object>();
					params.put("configCode", "lateFeeLimitUpper");
					params.put("status", "1");
					lateFeeLimitUpper =systemConfigDao.getLateFeeLimitUpper(params);
					request.getSession().setAttribute("lateFeeLimitUpper", lateFeeLimitUpper);
				}
				model.addObject("lateFeeLimitUpper", lateFeeLimitUpper);
				if (name.lastIndexOf("_C_A_F") != -1) {
					iv = serviceViewApp();
=======
				if (name.lastIndexOf("_C_A_F") != -1) {
					iv = serviceViewApp();
					model = new ModelAndView(iv);
					String lateFeeLimitUpper = (String) request.getSession().getAttribute("lateFeeLimitUpper");
					if (lateFeeLimitUpper == null || lateFeeLimitUpper.trim().equals("")) {
						Map<String,Object> params = new HashMap<String,Object>();
						params.put("configCode", "lateFeeLimitUpper");
						params.put("status", "1");
						lateFeeLimitUpper =systemConfigDao.getLateFeeLimitUpper(params);
						request.getSession().setAttribute("lateFeeLimitUpper", lateFeeLimitUpper);
					}
					model.addObject("lateFeeLimitUpper", lateFeeLimitUpper);
>>>>>>> d0b5484a9bee2dc897836974fbc92e4f813785b1
//					request.getSession();
				} else {
					List<SysMenu> mList = getMenu(request);
			 		model.addObject("sysMainMenuList", mList);
				}
			}
		} else {
			iv = new InternalResourceView("/pages/login.jsp");
			model = new ModelAndView(iv);
			model.addObject("msg", "用户名或密码错误");
			model.addObject("msgType", "-1");
		}
		model.addObject("time", System.currentTimeMillis());
		return model;
	}
	
	/**
	 * 登录验证&参数初始化
	 * @param request
	 * @param response
	 * @return
	 * @throws SQLException
	 */
	@ResponseBody
	@RequestMapping("/pages/system/index.light")
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		String name  = request.getParameter("name");
		String pd  = request.getParameter("password");
		String rememberMe  = request.getParameter("remember_me");
		InternalResourceView iv = new InternalResourceView("/pages/newIndex.jsp");
		ModelAndView model = new ModelAndView(iv);
		checkRememberMe(model, rememberMe, name);
		SystemUser systemUser = loginService.checkLoginAction(name);
		if (systemUser.getLoginName() != null) 
		{
			String pageCode = MD5Utils.encodeMd5(pd, name);
			if (!pageCode.equals(systemUser.getPassword())) {
				iv = new InternalResourceView("/pages/login.jsp");
				model = new ModelAndView(iv);
				model.addObject("msg", "用户名或密码错误");
				model.addObject("msgType", "-1");
			} else {
				model.addObject("loginName",name);
				model.addObject("nickName",systemUser.getNickName());
				model.addObject("userMode",systemUser.getUserMode());
				model.addObject("shopName", systemUser.getShopName());
				model.addObject("areaName", systemUser.getAreaName());
				request.getSession().setAttribute("systemUser", systemUser);
				HashSet <String>list = msgService.getSendMsgShopStringList(new HashMap<String, String>());
				if (list.contains(systemUser.getServiceShopCode())) {
					request.getSession().setAttribute("msgTag", true);
				}else {
					request.getSession().setAttribute("msgTag", false);
				}
<<<<<<< HEAD
				model = new ModelAndView(iv);
				String lateFeeLimitUpper = (String) request.getSession().getAttribute("lateFeeLimitUpper");
				if (lateFeeLimitUpper == null || lateFeeLimitUpper.trim().equals("")) {
					Map<String,Object> params = new HashMap<String,Object>();
					params.put("configCode", "lateFeeLimitUpper");
					params.put("status", "1");
					lateFeeLimitUpper =systemConfigDao.getLateFeeLimitUpper(params);
					request.getSession().setAttribute("lateFeeLimitUpper", lateFeeLimitUpper);
				}
				model.addObject("lateFeeLimitUpper", lateFeeLimitUpper);
				if (name.lastIndexOf("_C_A_F") != -1) {
					iv = serviceViewApp();
=======
				if (name.lastIndexOf("_C_A_F") != -1) {
					iv = serviceViewApp();
					model = new ModelAndView(iv);
					String lateFeeLimitUpper = (String) request.getSession().getAttribute("lateFeeLimitUpper");
					if (lateFeeLimitUpper == null || lateFeeLimitUpper.trim().equals("")) {
						Map<String,Object> params = new HashMap<String,Object>();
						params.put("configCode", "lateFeeLimitUpper");
						params.put("status", "1");
						lateFeeLimitUpper =systemConfigDao.getLateFeeLimitUpper(params);
						request.getSession().setAttribute("lateFeeLimitUpper", lateFeeLimitUpper);
					}
					model.addObject("lateFeeLimitUpper", lateFeeLimitUpper);
>>>>>>> d0b5484a9bee2dc897836974fbc92e4f813785b1
//					request.getSession();
				} else {
					List<SysMenu> mList = getMenu(request);
			 		model.addObject("sysMainMenuList", mList);
				}
			}
		} else {
			iv = new InternalResourceView("/pages/login.jsp");
			model = new ModelAndView(iv);
			model.addObject("msg", "用户名或密码错误");
			model.addObject("msgType", "-1");
		}
		model.addObject("time", System.currentTimeMillis());
		return model;
	}
	
	private List<SysMenu> getMenu(HttpServletRequest request){
		SystemUser s = getSystemUser(request, null);
		Integer userId = s.getId();
		List<SysMenu> mList = null;
		if (s.getLoginName().equals("admin")) {
			SysMenu ss = new SysMenu(); 
			ss.setLevel(1);
			mList = menuService.getMainLevelMenuList(ss);
			Map<String,Object> params = new HashMap<String,Object>(2);
	 		for (SysMenu sm : mList) {
	 			params.put("parentId", sm.getId());
	 			params.put("status", "Y");
	 			List<SysMenu> childNodes = menuService.getMenuListByParentId(params);
	 			for (SysMenu cNode : childNodes) {
	 				Map<String,Object> params2 = new HashMap<String,Object>(2);
	 				params2.put("parentId", cNode.getId());
	 				params2.put("status", "Y");
	 				List<SysMenu> cNodeChildNodes = menuService.getMenuListByParentId(params2);
	 				cNode.setChildSysMenuNodes(cNodeChildNodes);
				}
	 			sm.setChildSysMenuNodes(childNodes);
			}
		} else {
			Map<String,Object> smMap = new HashMap<String,Object>(2);
			smMap.put("userId", userId);
			smMap.put("level", 1);
			mList = userRoleService.getRoleMenuListByUserId(smMap);
			Map<String,Object> params = new HashMap<String,Object>(2);
	 		for (SysMenu sm : mList) {
	 			params.put("parentId", sm.getId());
	 			params.put("userId", userId);
	 			params.put("status", "Y");
	 			init(request, sm);
	 			List<SysMenu> childNodes = userRoleService.getRoleMenuListByParentId(params);
	 			for (SysMenu cNode : childNodes) {
	 				Map<String,Object> params2 = new HashMap<String,Object>(2);
	 				params2.put("parentId", cNode.getId());
	 				params2.put("userId", userId);
	 				params2.put("status", "Y");
	 				init(request, cNode);
	 				List<SysMenu> cNodeChildNodes = userRoleService.getRoleMenuListByParentId(params2);
	 				cNode.setChildSysMenuNodes(cNodeChildNodes);
				}
	 			sm.setChildSysMenuNodes(childNodes);
			}
		}
 		return mList;
	}
	
	private  void init(HttpServletRequest request,SysMenu menu) {
		String lateFeeLimitUpper = (String) request.getSession().getAttribute("lateFeeLimitUpper");
		if (lateFeeLimitUpper == null || lateFeeLimitUpper.trim().equals("")) {
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("configCode", "lateFeeLimitUpper");
			params.put("status", "1");
			lateFeeLimitUpper =systemConfigDao.getLateFeeLimitUpper(params);
			request.getSession().setAttribute("lateFeeLimitUpper", lateFeeLimitUpper);
		}
		
		String lateDayLimit = (String) request.getSession().getAttribute("lateDayLimit");
		if (lateDayLimit == null || lateDayLimit.trim().equals("")) {
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("configCode", "lateDayLimit");
			params.put("status", "1");
			lateDayLimit =systemConfigDao.getLateDayLimit(params);
			request.getSession().setAttribute("lateDayLimit", lateDayLimit);
		}
		
		String memberLateFeeLimitUpper = (String) request.getSession().getAttribute("memberLateFeeLimitUpper");
		if (memberLateFeeLimitUpper == null || memberLateFeeLimitUpper.trim().equals("")) {
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("configCode", "memberLateFeeLimitUpper");
			params.put("status", "1");
			lateDayLimit =systemConfigDao.getMemberLateFeeAddition(params);
			request.getSession().setAttribute("memberLateFeeLimitUpper", memberLateFeeLimitUpper);
		}
		
		String memberLateDayLimit = (String) request.getSession().getAttribute("memberLateDayLimit");
		if (memberLateDayLimit == null || memberLateDayLimit.trim().equals("")) {
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("configCode", "memberLateDayLimit");
			params.put("status", "1");
			lateDayLimit =systemConfigDao.getMemberLateDayAddition(params);
			request.getSession().setAttribute("memberLateDayLimit", memberLateDayLimit);
		}
		
		String url = menu.getUrl();
		String tag = "${lateFeeLimitUpper}";
		if (url.contains(tag)) {
			menu.setUrl(url.replace(tag, lateFeeLimitUpper));
		}
		
		String tag2 = "${lateDayLimit}";
		if (url.contains(tag2)) {
			menu.setUrl(url.replace(tag2, lateDayLimit));
		}
		
		String tag3 = "${memberLateFeeLimitUpper}";
		if (url.contains(tag3)) {
			menu.setUrl(url.replace(tag3, memberLateFeeLimitUpper));
		}
		
		String tag4 = "${memberLateDayLimit}";
		if (url.contains(tag4)) {
			menu.setUrl(url.replace(tag4, memberLateFeeLimitUpper));
		}
		
	}
	
	@ResponseBody
	@RequestMapping("/pages/system/getExpressByBatchNumber.light")
	public void  getExpressByBatchNumber(String batchNumber,HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		SystemUser s = getSystemUser(request, response);
		if (s !=null) {
			String rows = request.getParameter("rows"); 
			String page = request.getParameter("page");
			final int rowSize=Integer.valueOf(rows); 
			final int pageSize=Integer.valueOf(page);
			Map<String,String> params = new HashMap<String,String>();
			params.put("batchNumber", batchNumber);
			params.put("areaCode", s.getAreaCode());
			params.put("serviceShopCode", s.getServiceShopCode());
			JSONArray ja = loginService.getExpressByBatchNumber(rowSize, pageSize,params);
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
			PrintWriter printWriter = response.getWriter();
			printWriter.write(ja.toString()); 
	        printWriter.flush(); 
	        printWriter.close();
		} 

	}

	@ResponseBody
	@RequestMapping("/pages/system/editDataById.light")
	public void  editDataById(String str,HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		SystemUser s = getSystemUser(request, response);
		if (s != null) {
			String id = request.getParameter("id");
			String logistics = request.getParameter("logistics");
			String recipientName = request.getParameter("recipientName");
			String phoneNumber = request.getParameter("phoneNumber");
			String expressLocation = request.getParameter("expressLocation");
			ExpressInfo ei = new ExpressInfo();
			ei.setId(Integer.valueOf(id));
			ei.setLogistics(logistics);
			ei.setRecipientName(recipientName);
			ei.setPhoneNumber(phoneNumber);
			ei.setExpressLocation(expressLocation);
			JSONObject ja = loginService.editDataById(ei);
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
			PrintWriter printWriter = response.getWriter();
			printWriter.write(ja.toString());
			printWriter.flush();
			printWriter.close();
		} 
		
	}

	@ResponseBody
	@RequestMapping("/pages/system/getBarCode.light")
	public void  getBarCode(String str,HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		SystemUser s = getSystemUser(request, response);
		if (s !=null) {
			String id = request.getParameter("id"); 
//			String name = request.getParameter("name");
//			String webRootPath = "WEB-INF";
			String webPageTemp = "temp";
			String separator = System.getProperty ("file.separator");//File.separatorChar;
			separator = separator.replace("\\", "/");
			File file =  null;
			PrintWriter printWriter = null;
			 JSONObject json = new JSONObject();
			 Barcode barcode = null;
	        try 
	        {
	        	barcode = BarcodeFactory.createEAN128(id);
	        	barcode.setBarHeight(40);
	        	String tempPath = request.getSession().getServletContext().getRealPath(separator.toString())+ separator+webPageTemp;
	        	file = new File(tempPath+separator+id+"_temp.png");
				BarcodeImageHandler.savePNG(barcode, file);
	            json.put("type", "0");
	            json.put("PATH", separator+webPageTemp+separator+file.getName());
	            printWriter = response.getWriter();
	            printWriter.write(json.toString());
			} catch (BarcodeException e) {
				e.printStackTrace();
			} catch (OutputException e) {
				e.printStackTrace();
			} finally {
				if (file.isFile() && file.exists()) {
				}
				barcode = null;
		        printWriter.flush(); 
		        printWriter.close(); 
		        json = null;
			}
		} 
		
	}
	
	@ResponseBody
	@RequestMapping("/pages/system/getLateFeeLimitUpper.light")
	public void getLateFeeLimitUpper(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		String lateFeeLimitUpper = (String) request.getSession().getAttribute("lateFeeLimitUpper");
		if (lateFeeLimitUpper == null || lateFeeLimitUpper.trim().equals("")) {
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("configCode", "lateFeeLimitUpper");
			params.put("status", "1");
			lateFeeLimitUpper =systemConfigDao.getLateFeeLimitUpper(params);
			request.getSession().setAttribute("lateFeeLimitUpper", lateFeeLimitUpper);
		}
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		 PrintWriter printWriter = response.getWriter();
		printWriter.write(lateFeeLimitUpper);
        printWriter.flush(); 
        printWriter.close();
	}
	
}
package com.fla.common.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
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
import org.springframework.web.servlet.ModelAndView;

import com.fla.common.base.SuperController;
import com.fla.common.dao.LoginDao;
import com.fla.common.entity.ExpressServiceProvider;
import com.fla.common.entity.SystemArea;
import com.fla.common.entity.SystemShop;
import com.fla.common.entity.SystemUser;
import com.fla.common.service.interfaces.SystemServiceInterface;
import com.fla.common.util.MD5Utils;

@Controller
public class SystemController extends SuperController {

	private static final long serialVersionUID = 1L;

	@Autowired
	private LoginDao loginDao;

	@Autowired
	private SystemServiceInterface systemServiceInterface;

	@org.springframework.web.bind.annotation.InitBinder
	public void InitBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, null, new CustomDateEditor(
				dateFormat, true));
	}

	@ResponseBody
	@RequestMapping("/pages/system/getAreaInfoList.light")
	public ModelAndView getAreaInfoList(Integer page, Integer rows,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> params = new HashMap<String, String>();
		JSONArray jsonArray = systemServiceInterface.getAreaInfoList(rows,
				page, params);
		PrintWriter printWriter = null;
		try {
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
		return null;
	}

	@ResponseBody
	@RequestMapping("/pages/system/getAreaInfoForSelect.light")
	public ModelAndView getAreaInfoForSelect(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> params = new HashMap<String, String>();
		SystemUser s = getSystemUser(request, response);
		params.put("areaCode", s.getAreaCode());
		params.put("loginName", s.getLoginName());
		JSONArray jsonArray = systemServiceInterface.getAreaInfoForSelect(params);
		PrintWriter printWriter = null;
		try {
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
			printWriter = response.getWriter();
			printWriter.write(jsonArray.toString());
		} catch (IOException e) {

		} finally {
			printWriter.flush();
			printWriter.close();
		}
		return null;
	}

	@ResponseBody
	@RequestMapping("/pages/system/getShopInfoForSelect.light")
	public ModelAndView getShopInfoForSelect(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, String> params = new HashMap<String, String>();
		SystemUser s = getSystemUser(request, response);
		params.put("areaCode", s.getAreaCode());
		params.put("loginName", s.getLoginName());
		JSONArray jsonArray = systemServiceInterface.getShopInfoForSelect(params);
		PrintWriter printWriter = null;
		try {
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
			printWriter = response.getWriter();
			printWriter.write(jsonArray.toString());
		} catch (IOException e) {

		} finally {
			printWriter.flush();
			printWriter.close();
		}
		return null;
	}

	@ResponseBody
	@RequestMapping("/pages/system/getShopInfoForSelectForWechat.light")
	public ModelAndView getShopInfoForSelectForWechat(
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> params = new HashMap<String, String>();
		JSONArray jsonArray = systemServiceInterface.getSpecialShopInfoForSelect(params);
		PrintWriter printWriter = null;
		try {
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
			printWriter = response.getWriter();
			printWriter.write(jsonArray.toString());
		} catch (IOException e) {

		} finally {
			printWriter.flush();
			printWriter.close();
		}
		return null;
	}

	@ResponseBody
	@RequestMapping("/pages/system/getShopInfoList.light")
	public ModelAndView getShopInfoList(HttpServletRequest request,
			HttpServletResponse response) {
		String rows = request.getParameter("rows");
		String page = request.getParameter("page");
		int rowSize = 0;
		int pageSize = 1;
		if (rows != null) {
			rowSize = Integer.valueOf(rows);
		}
		if (page != null) {
			pageSize = Integer.valueOf(page);
		}
		Map<String, String> params = new HashMap<String, String>();
		SystemUser s = getSystemUser(request, response);
		params.put("loginName", s.getLoginName());
		if (!s.getLoginName().equals("admin")) {
			params.put("areaCode", s.getAreaCode());
		}
		JSONArray jsonArray = systemServiceInterface.getShopInfoList(rowSize,pageSize, params);
		PrintWriter printWriter = null;
		try {
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
			printWriter = response.getWriter();
			printWriter.write(jsonArray.toString());
		} catch (IOException e) {

		} finally {
			printWriter.flush();
			printWriter.close();
		}
		return null;
	}

	@ResponseBody
	@RequestMapping("/pages/system/getUserInfoList.light")
	public void getUserInfoList(HttpServletRequest request, HttpServletResponse response) {
		String rows = request.getParameter("rows");
		String page = request.getParameter("page");
		int rowSize = 0;
		int pageSize = 1;
		if (rows != null) {
			rowSize = Integer.valueOf(rows);
		}
		if (page != null) {
			pageSize = Integer.valueOf(page);
		}
		SystemUser s = getSystemUser(request, response);
		Map<String, String> params = new HashMap<String, String>();
		params.put("loginName", s.getLoginName());
		if (!s.getLoginName().equals("admin")) {
			params.put("areaCode", s.getAreaCode());
		}
		JSONArray jsonArray = systemServiceInterface.getUserInfoList(rowSize,
				pageSize, params);
		PrintWriter printWriter = null;
		try {
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
	@RequestMapping("/pages/system/addArea.light")
	public ModelAndView addArea(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException {
		JSONObject json = new JSONObject();
		// SystemUser s = (SystemUser)
		// request.getSession().getAttribute("systemUser");
		SystemArea area = null;
		try {
			area = jsonToSystemAreaEntity(request);
			systemServiceInterface.insertAreaInfo(area, null);
		} catch (NullPointerException e) {
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
			PrintWriter printWriter = response.getWriter();
			printWriter.write("NEED_LOGIN");
			printWriter.flush();
			printWriter.close();
		}
		json.put("msg", "保存成功");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		printWriter.write(json.toString());
		printWriter.flush();
		printWriter.close();
		return null;

	}

	@ResponseBody
	@RequestMapping("/pages/system/modifyArea.light")
	public ModelAndView modifyArea(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException {
		JSONObject json = new JSONObject();
		SystemArea area = null;
		try {
			area = jsonToSystemAreaEntity(request);
			systemServiceInterface.modifyAreaInfo(area);
		} catch (NullPointerException e) {
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
			PrintWriter printWriter = response.getWriter();
			printWriter.write("NEED_LOGIN");
			printWriter.flush();
			printWriter.close();
		}
		json.put("msg", "保存成功");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		printWriter.write(json.toString());
		printWriter.flush();
		printWriter.close();
		return null;

	}

	@ResponseBody
	@RequestMapping("/pages/system/addShop.light")
	public ModelAndView addShop(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException {
		JSONObject json = new JSONObject();
		SystemShop shop = null;
		try {
			shop = jsonToSystemShopEntity(request);
			systemServiceInterface.insertShopInfo(shop, null);
		} catch (NullPointerException e) {
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
			PrintWriter printWriter = response.getWriter();
			printWriter.write("NEED_LOGIN");
			printWriter.flush();
			printWriter.close();
		}
		json.put("msg", "保存成功");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		printWriter.write(json.toString());
		printWriter.flush();
		printWriter.close();
		return null;

	}

	@ResponseBody
	@RequestMapping("/pages/system/modifyShop.light")
	public ModelAndView modifyShop(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException {
		JSONObject json = new JSONObject();
		SystemShop shop = null;
		try {
			shop = jsonToSystemShopEntity(request);
			systemServiceInterface.modifyShopInfo(shop);
		} catch (NullPointerException e) {
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
			PrintWriter printWriter = response.getWriter();
			printWriter.write("NEED_LOGIN");
			printWriter.flush();
			printWriter.close();
		}
		json.put("msg", "保存成功");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		printWriter.write(json.toString());
		printWriter.flush();
		printWriter.close();
		return null;

	}

	@ResponseBody
	@RequestMapping("/pages/system/addUser.light")
	public ModelAndView addUser(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException {
		JSONObject json = new JSONObject();
		SystemUser su = null;
		try {
			su = jsonToSystemUserEntity(request);
			String code = systemServiceInterface.getShopAreaCode(su
					.getServiceShopCode());
			su.setAreaCode(code);
			systemServiceInterface.insertUserInfo(su, null);
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
		return null;

	}

	@ResponseBody
	@RequestMapping("/pages/system/modifyUser.light")
	public ModelAndView modifyUser(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException {
		JSONObject json = new JSONObject();
		SystemUser user = null;
		try {
			user = jsonToSystemUserEntity(request);
			String code = systemServiceInterface.getShopAreaCode(user
					.getServiceShopCode());
			user.setAreaCode(code);
			String password = request.getParameter("password");
			if (password == null || password.equals("")) {
				systemServiceInterface.modifyUserInfo(user, null);
			} else {
				systemServiceInterface.modifyUserInfo(user);
			}
		} catch (NullPointerException e) {
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
			PrintWriter printWriter = response.getWriter();
			printWriter.write("NEED_LOGIN");
			printWriter.flush();
			printWriter.close();
		}
		json.put("msg", "保存成功");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		printWriter.write(json.toString());
		printWriter.flush();
		printWriter.close();
		return null;

	}

	@ResponseBody
	@RequestMapping("/pages/system/modifyUserPassWord.light")
	public void modifyUserPassWord(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException {
		JSONObject json = new JSONObject();
		SystemUser user = new SystemUser();
		try {
			String loginName = request.getParameter("loginName");
			String password = request.getParameter("password");
			String confirmPw = request.getParameter("confirmPw");
			if (password.equals(confirmPw)) {
				user.setLoginName(loginName);
				user.setPassword(MD5Utils.encodeMd5(password, loginName));
				systemServiceInterface.modifyUserPassWord(user);
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
			json.put("msg", e.getMessage());
		}
		json.put("msg", "保存成功");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		printWriter.write(json.toString());
		printWriter.flush();
		printWriter.close();

	}

	private ExpressServiceProvider jsonToExpressServiceProviderEntity(
			HttpServletRequest request) throws UnsupportedEncodingException {
		String name = request.getParameter("name");
		String contacts = request.getParameter("contacts");
		String phoneNumber = request.getParameter("phoneNumber");
		String orderBy = request.getParameter("orderBy");
		String remark = request.getParameter("remark");
		String isCheck = request.getParameter("isCheck");

		ExpressServiceProvider esp = new ExpressServiceProvider();
		String espId = request.getParameter("id");
		Integer id = null;
		if (espId == null || espId.equals("") || espId.equalsIgnoreCase("null")) {
			id = loginDao.getSequenceByName("seq_express_service_provider_id");
		} else {
			id = Integer.valueOf(espId);
		}
		esp.setId(id);
		esp.setName(name);
		esp.setContacts(contacts);
		esp.setPhoneNumber(phoneNumber);
		if (orderBy == null || orderBy.equals("")
				|| orderBy.equalsIgnoreCase("null")) {
			esp.setOrderBy(null);
		} else {
			esp.setOrderBy(orderBy);
		}
		esp.setRemark(remark);
		if ("YES".equalsIgnoreCase(isCheck)) {
			esp.setType("1");
		} else if ("NO".equalsIgnoreCase(isCheck)) {
			esp.setType("0");
		} else {
			esp.setType("0");
		}

		return esp;
	}

	private SystemArea jsonToSystemAreaEntity(HttpServletRequest request)
			throws UnsupportedEncodingException {
		String areaCode = request.getParameter("areaCode");
		String areaName = request.getParameter("areaName");
		String isCheck = request.getParameter("isCheck");
		SystemArea area = new SystemArea();
		String areaId = request.getParameter("id");
		Integer id = null;
		if (areaId == null || areaId.equals("")
				|| areaId.equalsIgnoreCase("null")) {
			id = loginDao.getSequenceByName("seq_area_info_id");
		} else {
			id = Integer.valueOf(areaId);
		}
		area.setId(id);
		area.setCode(areaCode);
		area.setName(areaName);
		if ("YES".equalsIgnoreCase(isCheck)) {
			area.setType("1");
		} else if ("NO".equalsIgnoreCase(isCheck)) {
			area.setType("0");
		} else {
			area.setType("0");
		}

		return area;
	}

	private SystemShop jsonToSystemShopEntity(HttpServletRequest request)
			throws UnsupportedEncodingException {
		String shopCode = request.getParameter("shopCode");
		String areaCode = request.getParameter("areaCode");
		String shopName = request.getParameter("shopName");
		String shopAddress = request.getParameter("shopAddress");
		String shopContacts = request.getParameter("shopContacts");
		String isCheck = request.getParameter("isCheck");
		SystemShop ss = new SystemShop();
		String shopId = request.getParameter("id");
		Integer id = null;
		if (shopId == null || shopId.equals("")
				|| shopId.equalsIgnoreCase("null")) {
			id = loginDao.getSequenceByName("seq_shop_info_id");
		} else {
			id = Integer.valueOf(shopId);
		}
		ss.setId(id);
		ss.setAreaCode(areaCode);
		ss.setName(shopName);
		ss.setShopCode(shopCode);
		ss.setShopAddress(shopAddress);
		ss.setShopContacts(shopContacts);
		if ("YES".equalsIgnoreCase(isCheck)) {
			ss.setType("1");
		} else if ("NO".equalsIgnoreCase(isCheck)) {
			ss.setType("0");
		} else {
			ss.setType("0");
		}

		return ss;
	}

	private SystemUser jsonToSystemUserEntity(HttpServletRequest request)
			throws UnsupportedEncodingException {
		String shopCode = request.getParameter("shopCode");
		String loginName = request.getParameter("loginName");
		String nickName = request.getParameter("nickName");
		String password = request.getParameter("password");
		String phoneNumber = request.getParameter("phoneNumber");
		String isCheck = request.getParameter("isCheck");
		String isOpenIM = request.getParameter("isOpenIM");
		SystemUser su = new SystemUser();
		String userId = request.getParameter("id");
		Integer id = null;
		if (userId == null || userId.equals("")
				|| userId.equalsIgnoreCase("null")) {
			id = loginDao.getSequenceByName("seq_system_user_id");
		} else {
			id = Integer.valueOf(userId);
		}
		su.setId(id);
		su.setLoginName(loginName);
		su.setNickName(nickName);
		su.setPassword(MD5Utils.encodeMd5(password, loginName));
		su.setPhoneNumber(phoneNumber);
		su.setServiceShopCode(shopCode);
		su.setCreateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(new Date()));
		if ("YES".equalsIgnoreCase(isCheck)) {
			su.setType("1");
		} else if ("NO".equalsIgnoreCase(isCheck)) {
			su.setType("0");
		} else {
			su.setType("0");
		}

		if ("YES".equalsIgnoreCase(isOpenIM)) {
			su.setIsOpenIM("O");
		} else if ("NO".equalsIgnoreCase(isOpenIM)) {
			su.setIsOpenIM("C");
		} else {
			su.setIsOpenIM("C");
		}

		return su;
	}

	@ResponseBody
	@RequestMapping("/pages/system/queryAreaInfos.light")
	public ModelAndView queryAreaInfos(String queryParams,
			HttpServletRequest request, HttpServletResponse response) {
		SystemUser s = getSystemUser(request, response);
		if (s == null) {
			return JumpModelAndView();
		}
		JSONArray jsonArray = systemServiceInterface
				.queryAreaInfos(queryParams);
		PrintWriter printWriter = null;
		try {
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
			printWriter = response.getWriter();
			printWriter.write(jsonArray.toString());
		} catch (IOException e) {

		} finally {
			printWriter.flush();
			printWriter.close();
		}
		return null;
	}

	@ResponseBody
	@RequestMapping("/pages/system/queryShopInfos.light")
	public ModelAndView queryShopInfos(String queryParams,
			HttpServletRequest request, HttpServletResponse response) {
		SystemUser s = (SystemUser) request.getSession().getAttribute(
				"systemUser");
		if (s == null) {
			return JumpModelAndView();
		}
		JSONArray jsonArray = systemServiceInterface
				.queryShopInfos(queryParams);
		PrintWriter printWriter = null;
		try {
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
			printWriter = response.getWriter();
			printWriter.write(jsonArray.toString());
		} catch (IOException e) {

		} finally {
			printWriter.flush();
			printWriter.close();
		}
		return null;
	}

	@ResponseBody
	@RequestMapping("/pages/system/queryUserInfos.light")
	public ModelAndView queryUserInfos(String queryParams,
			HttpServletRequest request, HttpServletResponse response) {
		SystemUser s = (SystemUser) request.getSession().getAttribute(
				"systemUser");
		if (s == null) {
			return JumpModelAndView();
		}
		JSONArray jsonArray = systemServiceInterface
				.queryUserInfos(queryParams);
		PrintWriter printWriter = null;
		try {
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
			printWriter = response.getWriter();
			printWriter.write(jsonArray.toString());
		} catch (IOException e) {

		} finally {
			printWriter.flush();
			printWriter.close();
		}
		return null;
	}

	@ResponseBody
	@RequestMapping("/pages/system/getConfigInfoList.light")
	public ModelAndView getConfigInfoList(HttpServletRequest request,
			HttpServletResponse response) {
		SystemUser s = (SystemUser) request.getSession().getAttribute(
				"systemUser");
		if (s == null) {
			return JumpModelAndView();
		}
		String rows = request.getParameter("rows");
		String page = request.getParameter("page");
		int rowSize = 0;
		int pageSize = 1;
		if (rows != null) {
			rowSize = Integer.valueOf(rows);
		}
		if (page != null) {
			pageSize = Integer.valueOf(page);
		}
		Map<String, String> params = new HashMap<String, String>();
		JSONArray jsonArray = systemServiceInterface.getConfigInfoList(rowSize,
				pageSize, params);
		PrintWriter printWriter = null;
		try {
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
			printWriter = response.getWriter();
			printWriter.write(jsonArray.toString());
		} catch (IOException e) {

		} finally {
			printWriter.flush();
			printWriter.close();
		}
		return null;
	}

	@ResponseBody
	@RequestMapping("/pages/system/getLocationCodeByExpressType.light")
	public ModelAndView getLocationCodeByExpressType(String type,
			HttpServletRequest request, HttpServletResponse response) {
		SystemUser s = getSystemUser(request, response);
		String shopCode = s.getServiceShopCode();
		JSONObject json = systemServiceInterface.getLocationCodeByExpressType(type, shopCode);
		PrintWriter printWriter = null;
		try {
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
			printWriter = response.getWriter();
			printWriter.write(json.toString());
		} catch (IOException e) {

		} finally {
			printWriter.flush();
			printWriter.close();
		}
		return null;
	}

	@ResponseBody
	@RequestMapping("/pages/system/getExpressServiceProviderList.light")
	public ModelAndView getExpressServiceProviderList(
			HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException {
		PrintWriter printWriter = null;
		SystemUser s = (SystemUser) request.getSession().getAttribute(
				"systemUser");
		if (s == null) {
			return JumpModelAndView();
		}
		try {
			String areaCode = s.getAreaCode();
			String shopCode = s.getServiceShopCode();
			JSONArray jsonArray = systemServiceInterface
					.getExpressServiceProviderList(areaCode, shopCode);
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
			printWriter = response.getWriter();
			printWriter.write(jsonArray.toString());
		} finally {
			printWriter.flush();
			printWriter.close();
		}
		return null;
	}

	@ResponseBody
	@RequestMapping("/pages/system/addExpressServiceProvider.light")
	public ModelAndView addExpressServiceProvider(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException {
		JSONObject json = new JSONObject();
		SystemUser s = (SystemUser) request.getSession().getAttribute(
				"systemUser");
		ExpressServiceProvider expressServiceProvider = null;
		try {
			expressServiceProvider = jsonToExpressServiceProviderEntity(request);
			systemServiceInterface.insertExpressServiceProvider(
					expressServiceProvider, null);
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
		return null;

	}

	@ResponseBody
	@RequestMapping("/pages/system/addServiceProviderContacts.light")
	public ModelAndView addServiceProviderContacts(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException {
		JSONObject json = new JSONObject();
		SystemUser s = (SystemUser) request.getSession().getAttribute(
				"systemUser");
		Map<String, String> params = new HashMap<String, String>();
		String areaCode = s.getAreaCode();
		String shopCode = s.getServiceShopCode();
		String providerContacts = request.getParameter("providerContacts");
		String providerPhoneNumber = request
				.getParameter("providerPhoneNumber");
		String providerRemark = request.getParameter("providerRemark");
		String providerId = request.getParameter("providerId");

		try {
			params.put("areaCode", areaCode);
			params.put("shopCode", shopCode);
			params.put("providerContacts", providerContacts);
			params.put("providerPhoneNumber", providerPhoneNumber);
			params.put("providerRemark", providerRemark);
			params.put("providerId", providerId);
			systemServiceInterface.addServiceProviderContacts(params);
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
		return null;

	}

	@ResponseBody
	@RequestMapping("/pages/system/modifyExpressServiceProvider.light")
	public void modifyExpressServiceProvider(HttpServletRequest request,
			HttpServletResponse response) throws SQLException, IOException {
		JSONObject json = new JSONObject();
		SystemUser s = (SystemUser) request.getSession().getAttribute(
				"systemUser");
		ExpressServiceProvider esp = null;
		try {
			esp = jsonToExpressServiceProviderEntity(request);
			systemServiceInterface.modifyExpressServiceProvider(esp);
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
	@RequestMapping("/pages/system/queryExpressServiceProviderContactsList.light")
	public void queryExpressServiceProviderContactsList(
			HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException {
		PrintWriter printWriter = null;
		SystemUser s = (SystemUser) request.getSession().getAttribute(
				"systemUser");
		try {
			String shopCode = s.getServiceShopCode();
			String providerId = request.getParameter("providerId");
			JSONArray jsonArray = systemServiceInterface
					.queryExpressServiceProviderContactsList(providerId,
							shopCode);
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
	@RequestMapping("/pages/system/modifyServiceProviderContacts.light")
	public void modifyServiceProviderContacts(HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject json = new JSONObject();
		SystemUser s = (SystemUser) request.getSession().getAttribute(
				"systemUser");
		Map<String, String> params = new HashMap<String, String>();
		String id = request.getParameter("id");
		String providerContacts = request.getParameter("providerContacts");
		String providerPhoneNumber = request
				.getParameter("providerPhoneNumber");
		String providerRemark = request.getParameter("providerRemark");
		PrintWriter printWriter = null;
		params.put("id", id);
		params.put("providerContacts", providerContacts);
		params.put("providerPhoneNumber", providerPhoneNumber);
		params.put("providerRemark", providerRemark);
		try {
			systemServiceInterface.modifyServiceProviderContacts(params);
			json.put("msg", "保存成功");
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
			printWriter = response.getWriter();
			printWriter.write(json.toString());
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			printWriter.flush();
			printWriter.close();
		}

	}

	@ResponseBody
	@RequestMapping("/pages/system/deleteProviderContactsById.light")
	public void deleteProviderContactsById(HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject json = new JSONObject();
		Map<String, String> params = new HashMap<String, String>();
		String id = request.getParameter("id");
		PrintWriter printWriter = null;
		params.put("id", id);
		try {
			systemServiceInterface.deleteProviderContactsById(params);
			json.put("msg", "删除成功");
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
			printWriter = response.getWriter();
			printWriter.write(json.toString());
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			printWriter.flush();
			printWriter.close();
		}

	}
	
	@ResponseBody
	@RequestMapping("/pages/system/getSystemConfigInfo.light")
	public void getSystemConfigInfo(HttpServletRequest request,HttpServletResponse response){
		Map<String,String> params = new HashMap<String,String>();
		PrintWriter printWriter =null;
		try 
		{
			JSONArray jsonArray = systemServiceInterface.getSystemConfigInfo(params);
			response.setCharacterEncoding("utf-8");          
			response.setContentType("text/html; charset=utf-8");
            printWriter = response.getWriter();
            printWriter.write(jsonArray.toString());
		} catch(IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally { 
			printWriter.flush();
			printWriter.close();
		}
	}
	
	@ResponseBody
	@RequestMapping("/pages/system/getSystemConfigValues.light")
	public void getSystemConfigValues(HttpServletRequest request,HttpServletResponse response){
		Map<String,String> params = new HashMap<String,String>();
		PrintWriter printWriter =null;
		try 
		{
			String configId = request.getParameter("queryParams");
			params.put("configId", configId);
			JSONArray jsonArray = systemServiceInterface.getSystemConfigValues(params);
			response.setCharacterEncoding("utf-8");          
			response.setContentType("text/html; charset=utf-8");
            printWriter = response.getWriter();
            printWriter.write(jsonArray.toString());
		} catch(IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally { 
			printWriter.flush();
			printWriter.close();
		}
	}


}
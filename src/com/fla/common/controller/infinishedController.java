package com.fla.common.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.springframework.web.servlet.ModelAndView;
import com.fla.common.base.SuperController;
import com.fla.common.dao.LoginDao;
import com.fla.common.entity.CustomerInfo;
import com.fla.common.entity.ExpressInfo;
import com.fla.common.entity.SentExpressInfo;
import com.fla.common.entity.Signature;
import com.fla.common.entity.SystemUser;
import com.fla.common.service.interfaces.InfinishedServiceInterface;
import com.fla.common.service.interfaces.LoginServiceInterface;
import com.fla.common.util.ChinesePinYin;
import com.fla.common.util.DateUtil;
import com.fla.common.util.Pagination;
import com.fla.common.util.PaginationUtils;

@Controller
public class infinishedController extends SuperController{

	private static final long serialVersionUID = -8384196233388218070L;
	
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(infinishedController.class);

	@Autowired
	private LoginServiceInterface loginServiceInterface;
	
	@Autowired
	private InfinishedServiceInterface infinishedServiceInterface;
	
	
	@Autowired
	private LoginDao loginDao;
	
	@org.springframework.web.bind.annotation.InitBinder
	public void InitBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, null, new CustomDateEditor(dateFormat, true));
	}

	@ResponseBody
	@RequestMapping("/pages/system/showInfinishedData.light")
	public void  showInfinishedData(String batchNumber,Integer page, Integer rows,
			HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		SystemUser systemUser = (SystemUser) request.getSession().getAttribute("systemUser");
		Map<String,String> params = new HashMap<String,String>();
		params.put("batchNumber", batchNumber);
		params.put("serviceShopCode", systemUser.getAreaCode());
		Pagination data  = loginServiceInterface.getExpressInfoList(rows, page,params);
		String d = PaginationUtils.getData(page, rows, data);
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		 PrintWriter printWriter = response.getWriter();
		printWriter.write(d);
        printWriter.flush(); 
        printWriter.close();
	}
	
	@ResponseBody
	@RequestMapping("/pages/system/addSentExpressInfo.light")
	public void addSentExpressInfo(HttpServletRequest request,HttpServletResponse response)  throws SQLException, IOException {
		SystemUser systemUser = (SystemUser) request.getSession().getAttribute("systemUser");
		SentExpressInfo sei=null;
		try 
		{
			String ssc = systemUser.getServiceShopCode();
			String areaCode = systemUser.getAreaCode();
			String operator = systemUser.getLoginName();
			sei = jsonToSentExpressInfoEntity(request,areaCode);
			sei.setServiceShopCode(ssc);
			sei.setOperator(operator);
		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONObject jo = infinishedServiceInterface.addSentExpressInfo(sei);
		CustomerInfo ci = new CustomerInfo();
		ci.setName(sei.getSenderName());
		ci.setPhoneNumber(sei.getSenderNumber());
		ci.setGender("unknown");
		ci.setWhetherHaveCar("unknown");
		ci.setAgeSection("unknown");
		addCustomeInfo(ci,request);
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		printWriter.write(jo.toString());
		printWriter.flush();
		printWriter.close();
	}
	
	@ResponseBody
	@RequestMapping("/pages/system/modifySentExpressInfo.light")
	public void modifySentExpressInfo(HttpServletRequest request,HttpServletResponse response)  throws SQLException, IOException {
		SystemUser systemUser = (SystemUser) request.getSession().getAttribute("systemUser");
		SentExpressInfo sei=null;
		try 
		{
			String ssc = systemUser.getServiceShopCode();
			String areaCode = systemUser.getAreaCode();
			String operator = systemUser.getLoginName();
			sei = jsonToSentExpressInfoEntity(request,areaCode);
			sei.setId(Integer.valueOf(request.getParameter("id")));
			sei.setServiceShopCode(ssc);
			sei.setOperator(operator);
		} catch (NullPointerException e) {
			
		}
		JSONObject jo = infinishedServiceInterface.modifySentExpressInfo(sei);
		addCustomeInfo(request, response);
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		printWriter.write(jo.toString());
		printWriter.flush();
		printWriter.close();
	}
	
	@ResponseBody
	@RequestMapping("/pages/system/addExpressInfo.light")
	public void addExpressInfo(HttpServletRequest request,HttpServletResponse response)  throws SQLException, IOException {
		SystemUser systemUser = (SystemUser) request.getSession().getAttribute("systemUser");
		ExpressInfo ei=null;
		try 
		{
			String ssc = systemUser.getServiceShopCode();
			String areaCode = systemUser.getAreaCode();
			String operator = systemUser.getLoginName();
			ei = jsonToExpressInfoEntity(request);
			ei.setServiceShopCode(ssc);
			ei.setAreaCode(areaCode);
			ei.setOperator(operator);
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		JSONObject jo = loginServiceInterface.addExpressInfo(ei);
		addCustomeInfo(request, response);
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		printWriter.write(jo.toString());
		printWriter.flush();
		printWriter.close();
	}
	
	public void addCustomeInfo(HttpServletRequest request,HttpServletResponse response)  throws SQLException, IOException {
		SystemUser systemUser = (SystemUser) request.getSession().getAttribute("systemUser");
		CustomerInfo ci=null;
		String areaCode = systemUser.getAreaCode();
		String shopCode = systemUser.getServiceShopCode();
		ci = jsonToCustomeInfoEntity(request);
		ci.setAreaCode(areaCode);
		ci.setShopCode(shopCode);
		ci.setGender("unknown");
		ci.setWhetherHaveCar("unknown");
		ci.setAgeSection("unknown");
		loginServiceInterface.insertCustomeInfo(ci);
	}
	
	public void addCustomeInfo(CustomerInfo ci,HttpServletRequest request)  throws SQLException, IOException {
		SystemUser s = (SystemUser) request.getSession().getAttribute("systemUser");
		String areaCode = s.getAreaCode();
		String shopCode = s.getServiceShopCode();
		ci.setAreaCode(areaCode);
		ci.setShopCode(shopCode);
		loginServiceInterface.insertCustomeInfo(ci);
	}
	
	private ExpressInfo jsonToExpressInfoEntity(HttpServletRequest request) throws UnsupportedEncodingException{
		ExpressInfo ei = new ExpressInfo();
		ei.setAddress(request.getParameter("address"));
		ei.setCode(request.getParameter("code"));
		ei.setExpressServiceId(Integer.valueOf(request .getParameter("expressServiceId")));
		ei.setLandlineNumber(request.getParameter("landlineNumber"));
		ei.setLogistics(request.getParameter("logistics"));
		ei.setOperaTime(request.getParameter("operaTime"));
		ei.setPhoneNumber(request.getParameter("phoneNumber"));
		ei.setRecipientName(request.getParameter("recipientName"));
		ei.setRemark(request.getParameter("remark"));
		ei.setExpressLocation(request.getParameter("expressLocation"));
		ei.setInBatchNumber(request.getParameter("batchNumber"));
		return ei;
	}
	
	private SentExpressInfo jsonToSentExpressInfoEntity(HttpServletRequest request,String areaCode) throws UnsupportedEncodingException{
		SentExpressInfo sei = new SentExpressInfo();
		sei.setAddress(request.getParameter("address"));
		sei.setAreaCode(areaCode);
		sei.setCode(request.getParameter("code"));
		sei.setDestination(request.getParameter("destination"));
		sei.setDimensions(request.getParameter("dimensions"));
		sei.setExpressLocation(request.getParameter("expressLocation"));
		sei.setExpressServiceId(Integer.valueOf(request .getParameter("expressServiceId")));
		sei.setLandlineNumber(request.getParameter("landlineNumber"));
		sei.setLogistics(request.getParameter("logistics"));
		sei.setOperaTime(request.getParameter("operaTime"));
		sei.setOperator(request.getParameter("operator"));
		sei.setPhoneNumber(request.getParameter("phoneNumber"));
		sei.setRecipientName(request.getParameter("recipientName"));
		sei.setRemark(request.getParameter("remark"));
		sei.setRes(request.getParameter("res"));
		sei.setSenderLandlineNumber(request.getParameter("senderLandlineNumber"));
		sei.setSenderName(request.getParameter("senderName"));
		sei.setSenderNumber(request.getParameter("senderNumber"));
		sei.setServiceShopCode(request.getParameter("serviceShopCode"));
		sei.setWeight(request.getParameter("weight"));
		String price = request.getParameter("price");
		if (price != null  && !price.equals("")) {
			float m = Float.valueOf(price);
			sei.setPrice(m*100);
		}
		
		return sei;
	}
	
	private CustomerInfo jsonToCustomeInfoEntity(HttpServletRequest request) throws UnsupportedEncodingException{
		CustomerInfo ci = new CustomerInfo();
		ci.setLandlineNumber(request.getParameter("landlineNumber"));
		ci.setPhoneNumber(request.getParameter("phoneNumber"));
		ci.setName(request.getParameter("recipientName"));
		String initialsCode = ChinesePinYin.initials(request.getParameter("recipientName"));
		ci.setInitialsCode(initialsCode);
		String spellingCode = ChinesePinYin.spelling(request.getParameter("recipientName"));
		ci.setSpellingCode(spellingCode);
		return ci;
	}

	@ResponseBody
	@RequestMapping("/pages/system/letExpressOutStorehouse.light")
	public void letExpressOutStorehouse(HttpServletRequest request,HttpServletResponse response, String ids,String signatureImg, Character type)  throws SQLException, IOException {
		List<Integer > idList  = new ArrayList<Integer>();
		String[] arr = ids.split(",");
		for (String id : arr) 
		{
			Integer i = Integer.valueOf(id);
			idList.add(i);
		}
		JSONObject batchNumberJson = null;
		synchronized (request) 
		{
			batchNumberJson = loginServiceInterface.getOutStorehouseBatchNumber();
		}
		String batchNumber = batchNumberJson.get("temporaryId").toString();
		JSONObject jo = loginServiceInterface.letExpressOutStorehouse(idList,batchNumber);
		Signature sign = makeSignatureEntity(signatureImg, type, batchNumber, request);
		loginServiceInterface.insertSignature(sign);
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		printWriter.write(jo.toString());//temporaryId
		printWriter.flush();
		printWriter.close();
	}
	
	@ResponseBody
	@RequestMapping("/pages/system/getNotOutExpressInfoByFilterConditions.light")
	public void  getNotOutExpressInfoByFilterConditions(String endDate,String startDate,String queryParams,String expressService,
			Integer page, Integer rows,HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		SystemUser systemUser = (SystemUser) request.getSession().getAttribute("systemUser");
		Map<String, String> params = new HashMap<String, String>();
		params.put("endDate", endDate);
		params.put("startDate", startDate);
		params.put("queryParams", queryParams);
		params.put("expressService", expressService);
		params.put("areaCode", systemUser.getAreaCode());
		params.put("serviceShopCode", systemUser.getServiceShopCode());
		Pagination data = loginServiceInterface.getNotOutExpressInfoByFilterConditions(rows, page,params);
		String d = PaginationUtils.getData(page, rows, data);
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		printWriter.write(d);
		printWriter.flush();
		printWriter.close();
	}
	

	@ResponseBody
	@RequestMapping("/pages/system/getExpressInfoList.light")
	public void getExpressInfoList(String batchNumber,Integer page, Integer rows,
			HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		SystemUser systemUser = (SystemUser) request.getSession().getAttribute("systemUser");
		if (systemUser !=null) {
			Map<String,String> params = new HashMap<String,String>();
			params.put("batchNumber", batchNumber);
			params.put("serviceShopCode", systemUser.getServiceShopCode());
			Pagination data = loginServiceInterface.getExpressInfoList(rows, page,params);
			String d = PaginationUtils.getData(page, rows, data);
			response.setCharacterEncoding("utf-8");          
			response.setContentType("text/html; charset=utf-8");
			 PrintWriter printWriter = response.getWriter();
			printWriter.write(d.toString()); 
	        printWriter.flush(); 
	        printWriter.close();
		} 
	}
	
	@ResponseBody
	@RequestMapping("/pages/system/getExpressInfoByFilterConditions.light")
	public void getExpressInfoByFilterConditions(String endDate,String startDate, String queryParams, String expressService,
			Integer page, Integer rows,HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException {
		SystemUser systemUser = (SystemUser) request.getSession().getAttribute("systemUser");
		Map<String, String> params = new HashMap<String, String>();
		params.put("endDate", endDate);
		params.put("startDate", startDate);
		params.put("queryParams", queryParams);
		params.put("expressService", expressService);
		params.put("serviceShopCode", systemUser.getServiceShopCode());
		Pagination data = loginServiceInterface.getExpressInfoPagination(rows, page, params);
		String d = PaginationUtils.getData(page, rows, data);
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		printWriter.write(d);
		printWriter.flush();
		printWriter.close();
	}
	
	@ResponseBody
	@RequestMapping("/pages/system/getSentExpressInfo.light")
	public void getSentExpressInfo(String endDate,String startDate,String queryParams,String expressService,
			HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		SystemUser systemUser = (SystemUser) request.getSession().getAttribute("systemUser");
		String rows = request.getParameter("rows");
		String page = request.getParameter("page");
		final int rowSize = Integer.valueOf(rows);
		final int pageSize = Integer.valueOf(page);
		Map<String, String> params = new HashMap<String, String>();
		params.put("endDate", endDate);
		params.put("startDate", startDate);
		params.put("queryParams", queryParams);
		params.put("expressService", expressService);
		params.put("serviceShopCode", systemUser.getServiceShopCode());
		JSONArray ja = infinishedServiceInterface.getSentExpressInfo(rowSize,pageSize, params);
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		printWriter.write(ja.toString());
		printWriter.flush();
		printWriter.close();
	}
	
	
	@ResponseBody
	@RequestMapping("/pages/system/saveSignature.light")
	public void saveSignature(String signatureImg, Character type, String batchNumber, HttpServletRequest request,HttpServletResponse response)  throws SQLException, IOException {
		SystemUser s = (SystemUser) request.getSession().getAttribute("systemUser");
		Signature sign = makeSignatureEntity(signatureImg, type, batchNumber, request);
		loginServiceInterface.insertSignature(sign);
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		printWriter.flush();
		printWriter.close();
	}
	
	@ResponseBody
	@RequestMapping("/pages/system/confirmExpressInStorehouse.light")
	public void confirmExpressInStorehouse(HttpServletRequest request,HttpServletResponse response, String batchNumber,String signatureImg, Character type)  throws SQLException, IOException {
		JSONObject json = new JSONObject();
		Signature sign = makeSignatureEntity(signatureImg, type, batchNumber, request);
		loginServiceInterface.insertSignature(sign);
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		json.put("msg", "确认成功");
		printWriter.write(json.toString());
		printWriter.flush();
		printWriter.close();
	}
	
	private Signature makeSignatureEntity(String signatureImg, Character type, String batchNumber, HttpServletRequest request) {
		Signature sign = new Signature();
		Integer id = loginDao.getSequenceByName("seq_express_signature_id");
		SystemUser s = (SystemUser) request.getSession().getAttribute("systemUser");
		String areaCode = s.getAreaCode();
		String loginName = s.getLoginName();
		String serviceShopCode = s.getServiceShopCode();
		sign.setId(id);
		sign.setAreaCode(areaCode);
		sign.setServiceShopCode(serviceShopCode);
		sign.setSignatureImg(signatureImg);
		sign.setOperator(loginName);
		sign.setOperaTime(DateUtil.formatDateToString(new Date()));
		sign.setBatchNumber(batchNumber);
		sign.setType(type);
		return sign;
	}

}
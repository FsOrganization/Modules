package com.fla.common.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
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

import sun.misc.BASE64Encoder;

import com.fla.common.base.SuperController;
import com.fla.common.dao.LoginDao;
import com.fla.common.entity.SystemUser;
import com.fla.common.service.interfaces.LoginServiceInterface;
import com.fla.common.service.interfaces.SystemServiceInterface;
import com.fla.common.util.ExcelExportTools;
import com.fla.common.verification.cage.Cage;
import com.fla.common.verification.cage.GCage;

@Controller
public class PageToolsController extends SuperController{

	private static final long serialVersionUID = -222343456360345672L;
//	private static final Logger logger = LoggerFactory.getLogger(PageToolsController.class);

	@Autowired
	private LoginServiceInterface loginServiceInterface;
	
	@Autowired
	private LoginDao loginDao;
	
	@Autowired
	private SystemServiceInterface systemServiceInterface;
	
	@org.springframework.web.bind.annotation.InitBinder
	public void InitBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, null, new CustomDateEditor(dateFormat, true));
	}
	
	@ResponseBody
	@RequestMapping("/pages/system/getVerificationCode.light")
	public  void getVerificationCode(String str, HttpServletRequest request,HttpServletResponse response) throws SQLException, IOException {
		PrintWriter printWriter = null;
		JSONObject json = new JSONObject();
		String code = null;
		try 
		{
			final Cage cage = new GCage();
    		synchronized (request) 
    		{
				code = getVerificationCode(cage, code);
			}
			code = code.substring(0, 6);
			BASE64Encoder encoder = new BASE64Encoder();
			byte[] ddd= cage.draw(code);
            json.put("TYPE", "0");
            json.put("CODE", code);
            encoder.encode(ddd);// 返回Base64编码过的字节数组字符串
            json.put("PATH", encoder.encode(ddd));
            printWriter = response.getWriter();
            printWriter.write(json.toString());
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			printWriter.flush();
			printWriter.close();
			json = null;
		}

	}
	
	@ResponseBody
	@RequestMapping("/pages/system/getNewAreaCode.light")
	public void getNewAreaCode(String str, HttpServletRequest request,HttpServletResponse response) throws SQLException, IOException {
		JSONObject jo = systemServiceInterface.getNewAreaCode(null);
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		printWriter.write(jo.toString());
		printWriter.flush();
		printWriter.close();
	}
	
	@ResponseBody
	@RequestMapping("/pages/system/getNewShopCode.light")
	public void getNewShopCode(String str, HttpServletRequest request,HttpServletResponse response) throws SQLException, IOException {
		JSONObject jo = systemServiceInterface.getNewShopCode(null);
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		printWriter.write(jo.toString());//temporaryId
		printWriter.flush();
		printWriter.close();
	}
	
	@ResponseBody
	@RequestMapping("/pages/system/checkLoginNameUniqueness.light")
	public void checkLoginNameUniqueness(String str, HttpServletRequest request,HttpServletResponse response) throws SQLException, IOException {
		String name = request.getParameter("loginName");
		JSONObject json = systemServiceInterface.checkLoginNameUniqueness(name);
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		printWriter.write(json.toString());
		printWriter.flush();
		printWriter.close();
		
	}
	
	private synchronized String getVerificationCode(Cage cage, String code) {
		code = cage.getTokenGenerator().next();
		return code;
	}
	
	@ResponseBody
	@RequestMapping("/pages/system/getToolTipMsgById.light")
	public void getToolTipMsgById(String str, HttpServletRequest request,HttpServletResponse response) throws SQLException, IOException {
		PrintWriter printWriter = null;
		JSONObject json = new JSONObject();
		String msg = "签字板功能仅支持IE浏览器，其他业务可使用其他浏览器！";
		try 
		{
			response.setCharacterEncoding("utf-8");          
			response.setContentType("text/html; charset=utf-8");
            json.put("TYPE", "0");
            json.put("CODE", "我们在项目中用到了Spring、Apache、JQuery等开源项目,感谢为开源世界提供服务的团体或个人;我们确定会在将来为开源世界贡献力量");
            printWriter = response.getWriter();
            printWriter.write(msg);
		} finally {
			printWriter.flush();
			printWriter.close();
			json = null;
		}
	}
	
	@ResponseBody
	@RequestMapping("/pages/system/getExpressServiceProviderInfo.light")
	public void getExpressServiceProviderInfo(String str, HttpServletRequest request,HttpServletResponse response) throws SQLException, IOException {
		SystemUser s = getSystemUser(request, response);
		PrintWriter printWriter = null;
		String areaCode = null;
		if (s != null) {
			areaCode = s.getAreaCode();
		}
		JSONArray jsonArray = loginServiceInterface.getExpressServiceProviderInfo(areaCode);
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
	@RequestMapping("/pages/system/getExpressStatisticalArea.light")
	public void getExpressStatisticalArea(String str, HttpServletRequest request,HttpServletResponse response) throws SQLException, IOException {
		SystemUser s = getSystemUser(request, response);
		PrintWriter printWriter = null;
		Map<String, Object> params = new HashMap<String, Object>();
		if (s != null) {
			params.put("areaCode", s.getAreaCode());
			params.put("loginName", s.getLoginName());
		}
		JSONArray jsonArray = systemServiceInterface.getExpressStatisticalArea(params);
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
	@RequestMapping("/pages/system/getShopNumberOfPeopleGroupCount.light")
	public void getShopNumberOfPeopleGroupCount(HttpServletRequest request,HttpServletResponse response) throws SQLException, IOException {
		PrintWriter printWriter = null;
		String type = request.getParameter("type");
		String code = request.getParameter("code");
		JSONArray jsonArray = systemServiceInterface.getShopNumberOfPeopleGroupCount(type,code);
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
	@RequestMapping("/pages/system/getShopInAndSendExpressGroupCount.light")
	public void getShopInAndSendExpressGroupCount(HttpServletRequest request,HttpServletResponse response) throws SQLException, IOException {
		PrintWriter printWriter = null;
		String type = request.getParameter("type");
		String code = request.getParameter("code");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		JSONArray jsonArray = systemServiceInterface.getShopInAndSendExpressGroupCount(type,code,startDate,endDate);
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
	@RequestMapping("/pages/system/getShopOutAndSendExpressGroupCount.light")
	public void getShopOutAndSendExpressGroupCount(HttpServletRequest request,HttpServletResponse response) throws SQLException, IOException {
		PrintWriter printWriter = null;
		String shopCode = request.getParameter("shopCode");
		String limitTime = request.getParameter("limitTime");
		Map<String, String> params = new HashMap<String, String>(4);
		params.put("limitTime", limitTime);
		params.put("shopCode", shopCode);
		JSONArray jsonArray = systemServiceInterface.getShopOutAndSendExpressGroupCount(params);
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
	@RequestMapping("/pages/system/getSendOutExpressByExpressGroup.light")
	public void getSendOutExpressByExpressGroup(HttpServletRequest request,HttpServletResponse response) throws SQLException, IOException {
		PrintWriter printWriter = null;
		String shopCode = request.getParameter("shopCode");
		String limitTime = request.getParameter("limitTime");
		Map<String, String> params = new HashMap<String, String>(4);
		params.put("limitTime", limitTime);
		params.put("shopCode", shopCode);
		JSONArray jsonArray = systemServiceInterface.getSendOutExpressByExpressGroup(params);
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
	@RequestMapping("/pages/system/initExpressServiceProviders.light")
	public void initExpressServiceProviders(String str, HttpServletRequest request,HttpServletResponse response) throws SQLException, IOException {
		PrintWriter printWriter = null;
		SystemUser s = getSystemUser(request, response);
		String areaCode = null;
		if (s != null) {
			areaCode = s.getAreaCode();
		}
		JSONArray jsonArray = loginServiceInterface.getExpressServiceProviderInfo(areaCode);
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
	
	/**
	 * 页面客户下拉列表
	 * @param request
	 * @param response
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("/pages/system/getCustomeInfoList.light")
	public void getCustomeInfoList(HttpServletRequest request,HttpServletResponse response) throws SQLException, IOException {
		PrintWriter printWriter = null;
		SystemUser s = getSystemUser(request, response);
		try 
		{
			String areaCode = s.getAreaCode();
			String shopCode = s.getServiceShopCode();
			JSONArray jsonArray = loginServiceInterface.getCustomeInfoList(areaCode,shopCode);
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
	@RequestMapping("/pages/system/getTemporaryStorage.light")
	public void getTemporaryStorage(HttpServletRequest request,HttpServletResponse response)  throws SQLException, IOException {
		JSONObject jo = loginServiceInterface.getTemporaryStorage();
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		printWriter.write(jo.toString());//temporaryId
		printWriter.flush();
		printWriter.close();
	}
	
	@ResponseBody
	@RequestMapping("/pages/system/getOutStorehouseBatchNumber.light")
	public void getOutStorehouseBatchNumber(HttpServletRequest request,HttpServletResponse response)  throws SQLException, IOException {
		JSONObject jo = loginServiceInterface.getOutStorehouseBatchNumber();
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		printWriter.write(jo.toString());//temporaryId
		printWriter.flush();
		printWriter.close();
	}
	
	@ResponseBody
	@RequestMapping("/pages/system/getSignatureByBatchNumber.light")
	public void getSignatureByBatchNumber(HttpServletRequest request,HttpServletResponse response, String batchNumber, String type)  throws SQLException, IOException {
		JSONObject json = null;
		json = loginServiceInterface.getSignatureByBatchNumber(batchNumber, type);
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		printWriter.write(json.toString());
		printWriter.flush();
		printWriter.close();
	}
	
	@ResponseBody
	@RequestMapping("/pages/system/downExpressInfoByFilterConditions.light")
	public void downExpressInfoByFilterConditions(
			HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		SystemUser s = getSystemUser(request, response);
		String endDate = request.getParameter("down_endDate");
		String startDate = request.getParameter("down_startDate");
		Map<String, String> params = new HashMap<String, String>();
		params.put("endDate", endDate);
		params.put("startDate", startDate);
		params.put("queryParams", request.getParameter("down_queryParams"));
		params.put("expressService",request.getParameter("down_expressService"));
		params.put("serviceShopCode", s.getServiceShopCode());
		JSONArray ja = loginServiceInterface.exportExpressInfoByFilterConditions(params);
		String fileName = "快递导出模板.xlsx";
		String name = getExcelPath(request, fileName);
		File file = new File(name);
		File model = new File(getExcelModelPath(request, fileName));
		ExcelExportTools eet = new ExcelExportTools();
		eet.exportFile(startDate, endDate, model, file, ja, "TITLE");
		downFile(request, response, file);
	}
	
	@ResponseBody
	@RequestMapping("/pages/system/downShopInAndSendGroupCount.light")
	public void downShopInAndSendGroupCount(
			HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		String type = request.getParameter("down_type");
		String code = request.getParameter("down_code");
		String startDate = request.getParameter("down_startDate");
		String endDate = request.getParameter("down_endDate");
		JSONArray jsonArray = systemServiceInterface.getShopInAndSendExpressGroupCount(type,code,startDate,endDate);
		String fileName = "网点收寄件统计.xlsx";
		String name = getExcelPath(request, fileName);
		File file = new File(name);
		File model = new File(getExcelModelPath(request, fileName));
		ExcelExportTools eet = new ExcelExportTools();
		eet.exportQueryDataWithInAndSend(startDate, endDate, model, file, jsonArray, "TITLE");
		downFile(request, response, file);
	}
	
	@ResponseBody
	@RequestMapping("/pages/system/downOutAndSendExpressGroupCount.light") 
	public void downOutAndSendExpressGroupCount(
			HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		String shopCode = request.getParameter("down_shop_code");
		String paramDate = request.getParameter("down_paramDate");
		String shopName = request.getParameter("down_shopName");
		shopName = java.net.URLDecoder.decode(shopName,"UTF-8");
		String dateDesc = request.getParameter("down_dateDesc");
		Map<String, String> params = new HashMap<String, String>(4);
		params.put("limitTime", paramDate);
		params.put("shopCode", shopCode);
		JSONArray jsonArray = systemServiceInterface.getShopOutAndSendExpressGroupCount(params);
		String fileName = "取件寄件人数月报统计.xlsx";
		String name = getExcelPath(request, fileName);
		File file = new File(name);
		File model = new File(getExcelModelPath(request, fileName));
		ExcelExportTools eet = new ExcelExportTools();
		eet.exportOutAndSendExpressGroupCount(dateDesc,shopName,model, file, jsonArray, "TITLE");
		downFile(request, response, file);
	}
	
	@ResponseBody
	@RequestMapping("/pages/system/downShopOutAndSendExpressGroup.light") 
	public void downShopOutAndSendExpressGroup(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		String shopCode = request.getParameter("down_shop_code");
		String paramDate = request.getParameter("down_paramDate");
		String shopName = request.getParameter("down_shopName");
		shopName = java.net.URLDecoder.decode(shopName,"UTF-8");
		String dateDesc = request.getParameter("down_dateDesc");
		Map<String, String> params = new HashMap<String, String>(4);
		params.put("limitTime", paramDate);
		params.put("shopCode", shopCode);
		JSONArray jsonArray = systemServiceInterface.getSendOutExpressByExpressGroup(params);
		String fileName = "收寄件分组统计.xlsx";
		String name = getExcelPath(request, fileName);
		File file = new File(name);
		File model = new File(getExcelModelPath(request, fileName));
		ExcelExportTools eet = new ExcelExportTools();
		eet.exportSendOutExpressByExpressGroup(dateDesc,shopName,model, file, jsonArray, "TITLE");
		downFile(request, response, file);
	}
	
	
	@ResponseBody
	@RequestMapping("/pages/system/downShopCustomerCount.light")
	public void downShopCustomerCount(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		String type = request.getParameter("down_type");
		String code = request.getParameter("down_code");
		JSONArray jsonArray = systemServiceInterface.getShopNumberOfPeopleGroupCount(type, code);
		String fileName = "网点人数统计表.xlsx";
		String name = getExcelPath(request, fileName);
		File file = new File(name);
		File model = new File(getExcelModelPath(request, fileName));
		ExcelExportTools eet = new ExcelExportTools();
		eet.exportQueryDataWithCustomerCount(model, file, jsonArray, "TITLE");
		downFile(request, response, file);
	}
	
}
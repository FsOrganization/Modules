package com.fla.common.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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
import org.springframework.web.servlet.ModelAndView;

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

	/**
	 * 
	 */
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
	public void getVerificationCode(String str, HttpServletRequest request,HttpServletResponse response) throws SQLException, IOException {
		String webPageTemp = "temp";
		String separator = System.getProperty ("file.separator");//File.separatorChar;
		separator = separator.replace("\\", "/");
 		File file = null;
		PrintWriter printWriter = null;
		JSONObject json = new JSONObject();
		String code = null;
		try 
		{
			final Cage cage = new GCage();
        	String tempPath = request.getSession().getServletContext().getRealPath(separator.toString())+ separator+webPageTemp;
        	file = new File(tempPath+separator+System.currentTimeMillis()+"_temp.png");
    		final OutputStream os = new FileOutputStream(file, false);
    		synchronized (os) 
    		{
				code = getVerificationCode(cage, code);
			}
			code = code.substring(0, 6);
			cage.draw(code, os);
            json.put("TYPE", "0");
            json.put("CODE", code);
            json.put("PATH", separator+webPageTemp+separator+file.getName());
            printWriter = response.getWriter();
            printWriter.write(json.toString());
		} finally {
			if (file.isFile() && file.exists()) {
				// file.delete();
			}
			printWriter.flush();
			printWriter.close();
			json = null;
		}

	}
	
	@ResponseBody
	@RequestMapping("/pages/system/getNewAreaCode.light")
	public ModelAndView getNewAreaCode(String str, HttpServletRequest request,HttpServletResponse response) throws SQLException, IOException {
		SystemUser systemUser = (SystemUser) request.getSession().getAttribute("systemUser");
		if (systemUser ==null) {
			return JumpModelAndView();
		}
		JSONObject jo = systemServiceInterface.getNewAreaCode(null);
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		printWriter.write(jo.toString());
		printWriter.flush();
		printWriter.close();
		return null;
	}
	
	@ResponseBody
	@RequestMapping("/pages/system/getNewShopCode.light")
	public ModelAndView getNewShopCode(String str, HttpServletRequest request,HttpServletResponse response) throws SQLException, IOException {
		SystemUser systemUser = (SystemUser) request.getSession().getAttribute("systemUser");
		if (systemUser ==null) {
			return JumpModelAndView();
		}
		JSONObject jo = systemServiceInterface.getNewShopCode(null);
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		printWriter.write(jo.toString());//temporaryId
		printWriter.flush();
		printWriter.close();
		return null;
	}
	
	@ResponseBody
	@RequestMapping("/pages/system/checkLoginNameUniqueness.light")
	public ModelAndView checkLoginNameUniqueness(String str, HttpServletRequest request,HttpServletResponse response) throws SQLException, IOException {
		SystemUser systemUser = (SystemUser) request.getSession().getAttribute("systemUser");
		if (systemUser ==null) {
			return JumpModelAndView();
		}
		String name = request.getParameter("loginName");
		JSONObject json = systemServiceInterface.checkLoginNameUniqueness(name);
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		printWriter.write(json.toString());
		printWriter.flush();
		printWriter.close();
		return null;
		
	}
	
	private String getVerificationCode(Cage cage, String code) {
		code = cage.getTokenGenerator().next();
		return code;
	}
	
	@ResponseBody
	@RequestMapping("/pages/system/getToolTipMsgById.light")
	public ModelAndView getToolTipMsgById(String str, HttpServletRequest request,HttpServletResponse response) throws SQLException, IOException {
		SystemUser systemUser = (SystemUser) request.getSession().getAttribute("systemUser");
		if (systemUser ==null) {
			return JumpModelAndView();
		}
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
		return null;

	}
	
	@ResponseBody
	@RequestMapping("/pages/system/getExpressServiceProviderInfo.light")
	public ModelAndView getExpressServiceProviderInfo(String str, HttpServletRequest request,HttpServletResponse response) throws SQLException, IOException {
		SystemUser systemUser = (SystemUser) request.getSession().getAttribute("systemUser");
		if (systemUser ==null) {
			return JumpModelAndView();
		}
		PrintWriter printWriter = null;
		String areaCode = null;
		if (systemUser != null) {
			areaCode = systemUser.getAreaCode();
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
		return null;
	}
	
	@ResponseBody
	@RequestMapping("/pages/system/initExpressServiceProviders.light")
	public ModelAndView initExpressServiceProviders(String str, HttpServletRequest request,HttpServletResponse response) throws SQLException, IOException {
		PrintWriter printWriter = null;
		SystemUser systemUser = (SystemUser) request.getSession().getAttribute("systemUser");
//		if (systemUser ==null) {
//			return JumpModelAndView();
//		}
		String areaCode = null;
		if (systemUser != null) {
			areaCode = systemUser.getAreaCode();
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
		return null;
	}
	
	@ResponseBody
	@RequestMapping("/pages/system/getCustomeInfoList.light")
	public ModelAndView getCustomeInfoList(HttpServletRequest request,HttpServletResponse response) throws SQLException, IOException {
		PrintWriter printWriter = null;
		SystemUser systemUser = (SystemUser) request.getSession().getAttribute("systemUser");
		if (systemUser ==null) {
			return JumpModelAndView();
		}
		try 
		{
			String areaCode = systemUser.getAreaCode();
			String shopCode = systemUser.getServiceShopCode();
			JSONArray jsonArray = loginServiceInterface.getCustomeInfoList(areaCode,shopCode);
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
	@RequestMapping("/pages/system/getTemporaryStorage.light")
	public ModelAndView getTemporaryStorage(HttpServletRequest request,HttpServletResponse response)  throws SQLException, IOException {
		SystemUser systemUser = (SystemUser) request.getSession().getAttribute("systemUser");
		if (systemUser ==null) {
			return JumpModelAndView();
		}
		JSONObject jo = loginServiceInterface.getTemporaryStorage();
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		printWriter.write(jo.toString());//temporaryId
		printWriter.flush();
		printWriter.close();
		
		return null;
	}
	
	@ResponseBody
	@RequestMapping("/pages/system/getOutStorehouseBatchNumber.light")
	public ModelAndView getOutStorehouseBatchNumber(HttpServletRequest request,HttpServletResponse response)  throws SQLException, IOException {
		SystemUser systemUser = (SystemUser) request.getSession().getAttribute("systemUser");
		if (systemUser ==null) {
			return JumpModelAndView();
		}
		JSONObject jo = loginServiceInterface.getOutStorehouseBatchNumber();
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		printWriter.write(jo.toString());//temporaryId
		printWriter.flush();
		printWriter.close();
		return null;
	}
	
	@ResponseBody
	@RequestMapping("/pages/system/getSignatureByBatchNumber.light")
	public ModelAndView getSignatureByBatchNumber(HttpServletRequest request,HttpServletResponse response, String batchNumber, String type)  throws SQLException, IOException {
		SystemUser systemUser = (SystemUser) request.getSession().getAttribute("systemUser");
		if (systemUser ==null) {
			return JumpModelAndView();
		}
		JSONObject json = null;
		json = loginServiceInterface.getSignatureByBatchNumber(batchNumber, type);
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		printWriter.write(json.toString());
		printWriter.flush();
		printWriter.close();
		return null;
	}
	
	@ResponseBody
	@RequestMapping("/pages/system/downExpressInfoByFilterConditions.light")
	public ModelAndView downExpressInfoByFilterConditions(
			HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		SystemUser systemUser = (SystemUser) request.getSession().getAttribute("systemUser");
//		if (systemUser == null) {
//			return JumpModelAndView();
//		}
		String endDate = request.getParameter("down_endDate");
		String startDate = request.getParameter("down_startDate");
		Map<String, String> params = new HashMap<String, String>();
		params.put("endDate", endDate);
		params.put("startDate", startDate);
		params.put("queryParams", request.getParameter("down_queryParams"));
		params.put("expressService",request.getParameter("down_expressService"));
		params.put("serviceShopCode", systemUser.getServiceShopCode());
		JSONArray ja = loginServiceInterface.exportExpressInfoByFilterConditions(params);
		String fileName = "快递导出模板.xlsx";
		String name = getExcelPath(request, fileName);
		File file = new File(name);
		File model = new File(getExcelModelPath(request, fileName));
		ExcelExportTools eet = new ExcelExportTools();
		eet.exportFile(startDate, endDate, model, file, ja, "TITLE");
		downFile(request, response, file);
		return null;

	}

}
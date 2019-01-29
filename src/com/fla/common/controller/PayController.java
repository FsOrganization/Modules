package com.fla.common.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fla.common.base.SuperController;
import com.fla.common.entity.PayLog;
import com.fla.common.service.interfaces.LoginServiceInterface;
import com.fla.common.service.interfaces.PayLogServiceInterface;
import com.fla.common.util.ExcelExportTools;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

@Controller
public class PayController extends SuperController {

	private static final long serialVersionUID = -9188166050181372461L;

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(PayController.class);

	@Autowired
	private PayLogServiceInterface payLogService;

	@Autowired
	private LoginServiceInterface loginService;

	@org.springframework.web.bind.annotation.InitBinder
	public void InitBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
		simpleDateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, null, new CustomDateEditor(simpleDateFormat, true));
	}

	@ResponseBody
	@RequestMapping("/pages/system/pay/getPayLogs.light")
	public void getPayLogs(String queryParams, Integer page, Integer rows, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		PageBounds pageBounds = new PageBounds(page, rows);
		String shopCode = request.getParameter("shopCode");
		String limitTime = request.getParameter("limitTime");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("shopCode", shopCode);
		params.put("limitTime", limitTime);
		List<PayLog> payLogList = payLogService.getPayLogs(params, pageBounds);
		JSONArray jsonArray = JSONArray.fromObject(payLogList);
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		printWriter.write(jsonArray.toString());
		printWriter.flush();
		printWriter.close();

	}

	@ResponseBody
	@RequestMapping("/pages/system/pay/downPayDetail.light")
	public void downPayDetail(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException {
		PageBounds pageBounds = new PageBounds(1, 50000);
		String down_shopCode = request.getParameter("down_shopCode");
		String down_limitTime = request.getParameter("down_limitTime");
		Map<String, Object> params = new HashMap<String, Object>(2);
		params.put("shopCode", down_shopCode);
		params.put("limitTime", down_limitTime);
		JSONArray jsonArray = payLogService.getPayDetail(params, pageBounds);
//		System.out.println("downPayDetail:" + params);
//		System.out.println("jsonArray size:" + jsonArray.size());
		String fileName = "支付明细统计.xlsx";
		String name = getExcelPath(request, fileName);
		File file = new File(name);
		File model = new File(getExcelModelPath(request, fileName));
		ExcelExportTools eet = new ExcelExportTools();
		eet.exportPayDetail("支付明细统计", "东欣彩虹城", model, file, jsonArray, "支付明细");
//		System.out.println();
		downFile(request, response, file);
	}

}
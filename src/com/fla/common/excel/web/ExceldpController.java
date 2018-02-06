package com.fla.common.excel.web;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fla.common.excel.entity.ExcelExportParams;
import com.fla.common.excel.entity.ExcelImportParams;
import com.fla.common.excel.entity.ExcelMapper;
import com.fla.common.excel.entity.ExceldpResult;
import com.fla.common.excel.entity.NameValueEntry;
import com.fla.common.excel.service.ExceldpCallbackService;
import com.fla.common.excel.utils.ExcelConfigUtils;
import com.fla.common.excel.utils.ExcelReadUtils;
import com.fla.common.excel.utils.ExcelWriteUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Controller
@RequestMapping("/dialog/excelUpLoad")
public class ExceldpController {
	public static final String EXCEL_EXPORT_COMPLETE = "EXCEL_EXPORT_COMPLETE";
	private static Logger logger = LoggerFactory.getLogger(ExceldpController.class);

	@Autowired
	private ApplicationContext applicationContext;
	
	// 下载导入模板
	@RequestMapping(value = "downloadTemplate.light", method = RequestMethod.GET)
	public void downloadTemplate(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String mapperFileName = request.getParameter("mapperFileName");
		ExcelMapper excelMapper = ExcelConfigUtils.generateExcelMapper(mapperFileName);
		String importTemplate = excelMapper.getImportTemplate();
		String resPath = String.format("/exceldp/template/%s", importTemplate);
		InputStream isTemplate = this.getClass().getResourceAsStream(resPath);
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/octet-stream;charset=UTF-8");
		String encodedFileName = URLEncoder.encode(importTemplate, "UTF8");
		String contentDisposition = String.format("attachment;fileName=\"%s\"",encodedFileName);
		String userAgent = request.getHeader("User-Agent");
		if (userAgent != null) {
			userAgent = userAgent.toLowerCase();
			if (userAgent.indexOf("firefox") != -1) {
				contentDisposition = String.format("attachment;fileName*=UTF-8''%s", encodedFileName);
			}
		}
		response.setHeader("Content-Disposition", contentDisposition);
		OutputStream os = response.getOutputStream();
		try 
		{
			byte[] b = new byte[1024];
			int length;
			while ((length = isTemplate.read(b)) > 0) {
				os.write(b, 0, length);
			}
		} finally {
			isTemplate.close();
			os.close();
		}
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "speInitImportDialog.light", method = RequestMethod.GET)
	public String speInitImportDialog(HttpServletRequest request, Model model) {
		List<NameValueEntry> queryParamList = Lists.newArrayList();
		Enumeration enumeration = request.getParameterNames();
		while (enumeration.hasMoreElements()) {
			String userDataParamName = (String) enumeration.nextElement();
			Object userDataParamValue = request.getParameter(userDataParamName);
			NameValueEntry nameValueEntry = new NameValueEntry();
			nameValueEntry.setName(userDataParamName);
			nameValueEntry.setValue((String) userDataParamValue);
			queryParamList.add(nameValueEntry);
		}
		model.addAttribute("queryParamList", queryParamList);
		return "/common/excel/speModalHtml";
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "initImportDialog.light", method = RequestMethod.GET)
	public String initialDialog(HttpServletRequest request, Model model) {
		List<NameValueEntry> queryParamList = Lists.newArrayList();
		Enumeration enumeration = request.getParameterNames();
		while (enumeration.hasMoreElements()) {
			String userDataParamName = (String) enumeration.nextElement();
			Object userDataParamValue = request.getParameter(userDataParamName);
			NameValueEntry nameValueEntry = new NameValueEntry();
			nameValueEntry.setName(userDataParamName);
			nameValueEntry.setValue((String) userDataParamValue);
			queryParamList.add(nameValueEntry);
		}
		// InternalResourceView iv = new
		// InternalResourceView("/common/excel/modalHtml.jsp");
		// ModelAndView model = new ModelAndView(iv);
		// model.addObject("queryParamList", queryParamList);
		model.addAttribute("queryParamList", queryParamList);
		return "/common/excel/modalHtml";
	}

	/**
	 * 通用的excel导入文件对话框
	 */
	@RequestMapping(value = "initGenericImportDialog.light", method = RequestMethod.GET)
	public String initGenericImportDialog(HttpServletRequest request,
			Model model) {
		List<NameValueEntry> queryParamList = Lists.newArrayList();

		Enumeration enumeration = request.getParameterNames();
		while (enumeration.hasMoreElements()) {
			String userDataParamName = (String) enumeration.nextElement();
			Object userDataParamValue = request.getParameter(userDataParamName);
			NameValueEntry nameValueEntry = new NameValueEntry();
			nameValueEntry.setName(userDataParamName);
			nameValueEntry.setValue((String) userDataParamValue);
			queryParamList.add(nameValueEntry);
		}

		model.addAttribute("queryParamList", queryParamList);
		return "/common/excel/genericModalHtml";
	}

	// 导入Excel文件
	@RequestMapping(value = "importExcelHandle", produces = "application/json")
	@ResponseBody
	public ResponseEntity<ExceldpResult> importExcelHandle(
			@RequestParam("excelMapper") String excelMapperName,
			@RequestParam("file") MultipartFile file) {
		ExceldpResult result;
		// boolean isMultipart = FileUpload.isMultipartContent(request);
		try 
		{
			// String excelMapperFileName = excelImportParams.getExcelMapper();
			ExcelMapper excelMapper = ExcelConfigUtils.generateExcelMapper(excelMapperName);
			String fileName = file.getOriginalFilename();
			boolean isExcel2003 = StringUtils.endsWithIgnoreCase(fileName,".xls");
			boolean isExcel2007 = StringUtils.endsWithIgnoreCase(fileName,".xlsx");
			if (!isExcel2003 && !isExcel2007) {
				throw new Exception("不支持的文件格式:" + fileName);
			}
			Workbook workbook = null;
			if (isExcel2003) {
				workbook = new HSSFWorkbook(file.getInputStream());
			} else {
				workbook = new XSSFWorkbook(file.getInputStream());
			}
			Map excelData = ExcelReadUtils.parseExcel(excelMapper, workbook);
			ExceldpCallbackService service = (ExceldpCallbackService) applicationContext.getBean(Class.forName(excelMapper.getCallbackBean()));
			result = service.afterImportExcel(null, excelData);
		} catch (Throwable e) {
			result = new ExceldpResult(false, String.format("文件导入异常:%s",e.getMessage()));
			logger.error("文件导入异常", e);
		}
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.TEXT_PLAIN);
		return new ResponseEntity<ExceldpResult>(result, headers, HttpStatus.OK);
	}

	// 导入Excel文件
	@RequestMapping(value = "importExcel", produces = "application/json")
	public ResponseEntity<ExceldpResult> importExcel(@ModelAttribute("queryParams") ExcelImportParams excelImportParams) {
		ExceldpResult result;
		// boolean isMultipart = FileUpload.isMultipartContent(request);
		try 
		{
			String excelMapperFileName = excelImportParams.getExcelMapper();
			ExcelMapper excelMapper = ExcelConfigUtils.generateExcelMapper(excelMapperFileName);
			MultipartFile file = excelImportParams.getExcelfile();
			String fileName = file.getOriginalFilename();
			boolean isExcel2003 = StringUtils.endsWithIgnoreCase(fileName,".xls");
			boolean isExcel2007 = StringUtils.endsWithIgnoreCase(fileName,".xlsx");
			if (!isExcel2003 && !isExcel2007) {
				throw new Exception("不支持的文件格式:" + fileName);
			}
			Workbook workbook = null;
			if (isExcel2003) {
				workbook = new HSSFWorkbook(file.getInputStream());
			} else {
				workbook = new XSSFWorkbook(file.getInputStream());
			}
			Map excelData = ExcelReadUtils.parseExcel(excelMapper, workbook);
			ExceldpCallbackService service = (ExceldpCallbackService) applicationContext.getBean(Class.forName(excelMapper.getCallbackBean()));
			result = service.afterImportExcel(excelImportParams.getQueryParams(), excelData);
		} catch (Throwable e) {
			result = new ExceldpResult(false, String.format("文件导入异常:%s",e.getMessage()));
			logger.error("文件导入异常", e);
		}
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.TEXT_PLAIN);
		return new ResponseEntity<ExceldpResult>(result, headers, HttpStatus.OK);
	}
	
	// Multi 导入Excel文件
		@RequestMapping(value = "importMultiExcel", method=RequestMethod.POST)
		 public void importExcel(@RequestParam("file") MultipartFile[] files) {
			String fileName = null;
	    	String msg = "";
	    	if (files != null && files.length >0) {
	    		for(int i =0 ;i< files.length; i++){
		            try 
		            {
		                fileName = files[i].getOriginalFilename();
		                byte[] bytes = files[i].getBytes();
		                BufferedOutputStream buffStream = 
		                		new BufferedOutputStream(new FileOutputStream(new File("F:/duomi/" + fileName)));
		                buffStream.write(bytes);
		                buffStream.close();
		                msg += "You have successfully uploaded " + fileName +"<br/>";
		            } catch (Exception e) {
		                e.printStackTrace();
		            }
	    		}
	        }
		}

	// 导出Excel文件
	@RequestMapping(value = "exportExcel.light")
	public void exportExcel(@ModelAttribute("exportParams") ExcelExportParams excelExportParams,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// 设置导出完成标志
		HttpSession session = request.getSession();
		session.setAttribute(EXCEL_EXPORT_COMPLETE, "N");
		String excelMapperFile = excelExportParams.getExcelMapper();
		ExcelMapper excelMapper = ExcelConfigUtils.generateExcelMapper(excelMapperFile);
		String templateFile = excelMapper.getExportTemplate();
		boolean isExcel2003 = StringUtils.endsWithIgnoreCase(templateFile,".xls");
		boolean isExcel2007 = StringUtils.endsWithIgnoreCase(templateFile,".xlsx");
		if (!isExcel2003 && !isExcel2007) {
			throw new Exception("模板不是有效的Excel文件名.");
		}

		// 获取要导出的数据
		ExceldpCallbackService service = (ExceldpCallbackService) applicationContext.getBean(Class.forName(excelMapper.getCallbackBean()));
		Map exportData = service.beforeExportExcelGetData(excelExportParams.getQueryParams());
		List sheetNames = service.beforeExportExcelGetSheetName(excelExportParams.getQueryParams());
		Workbook wbTarget = null;
		InputStream is = null;
		try 
		{
			is = this.getClass().getResourceAsStream(String.format("/exceldp/template/%s", templateFile));
			if (isExcel2003) {
				wbTarget = new HSSFWorkbook(is);
			} else {
				wbTarget = new XSSFWorkbook(is);
			}
			ExcelWriteUtils.exportExcel(excelMapper, wbTarget, exportData,sheetNames);
		} catch (Exception e) {
			throw new Exception(String.format("文件导出异常:%s", e.getMessage()));
		} finally {
			is.close();
		}

		// 下载Excel文件
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/octet-stream;charset=UTF-8");
		String fileName = String.format("%s.%s", excelExportParams.getTargetFileName(), isExcel2003 ? "xls" : "xlsx");
		String encodedFileName = URLEncoder.encode(fileName, "UTF8");
		String contentDisposition = String.format("attachment;fileName=\"%s\"",encodedFileName);
		String userAgent = request.getHeader("User-Agent");
		if (userAgent != null) {
			userAgent = userAgent.toLowerCase();
			if (userAgent.indexOf("firefox") != -1) {
				contentDisposition = String.format("attachment;fileName*=UTF-8''%s", encodedFileName);
			}
		}
		response.setHeader("Content-Disposition", contentDisposition);
		OutputStream os = response.getOutputStream();
		try 
		{
			wbTarget.write(os);
		} finally {
			os.close();
		}
		// 设置导出完成标志
		session.setAttribute(EXCEL_EXPORT_COMPLETE, "Y");
	}

	@RequestMapping(value = "exportListener.light", produces = "application/json")
	@ResponseBody
	public Map<String, String> exportListener(HttpSession session) {
		String flag = (String) session.getAttribute(EXCEL_EXPORT_COMPLETE);
		if (StringUtils.isEmpty(flag)) {
			flag = "N";
		} else if (flag.equals("Y")) {
			session.removeAttribute(EXCEL_EXPORT_COMPLETE);
		}
		Map<String, String> result = Maps.newHashMap();
		result.put("flag", flag);
		return result;
	}
}

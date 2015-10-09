package com.fla.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.fla.common.base.SuperController;
import com.fla.common.service.interfaces.LoginServiceInterface;

@Controller
public class ExcelExportTools extends SuperController{

	private static final long serialVersionUID = 4829045195527990172L;

	public ExcelExportTools() {
	}
	
	@Autowired
	private LoginServiceInterface loginServiceInterface;
	
	
	
	/**
	 * 快递数据导出
	 */
	public   void exportFile(String startDate,String endDate, File model,File file,JSONArray ja,String exportTitle) throws IOException {
		copyFile(model, file);
		InputStream input = new FileInputStream(file);
		Workbook workBook = new XSSFWorkbook(input); 
		Sheet sheet = workBook.getSheet("sheet1");
		Row row;
		Cell cell;
		CellStyle style = workBook.createCellStyle();// 创建样式对象
		Font font = workBook.createFont();// 创建字体对象
		setStyle(font,style);
		row = sheet.createRow(0);
		cell = row.createCell(0);
		cell.setCellStyle(style);
		cell.setCellValue(exportTitle);
		
		int rowIndex = 2;
		//int cellIndex = 0;
		for (int i = 0; i < ja.size(); i++) {
			row = sheet.createRow(rowIndex++);
			JSONObject json = ja.getJSONObject(i);
			String logistics = json.get("LOGISTICS").toString();
			cell = row.createCell(0);
			cell.setCellValue(logistics);
			String recipientName = json.get("RECIPIENT_NAME").toString();
			cell = row.createCell(1);
			cell.setCellValue(recipientName);
			String phoneNumber = json.get("PHONE_NUMBER").toString();
			cell = row.createCell(2);
			cell.setCellValue(phoneNumber);
			String expressLocation = json.get("EXPRESS_lOCATION").toString();
			cell = row.createCell(3);
			cell.setCellValue(expressLocation);
			String expressService = json.get("EXPRESS_SERVICE_ID").toString();
			cell = row.createCell(4);
			cell.setCellValue(expressService);
			String operator = json.get("OPERATOR").toString();
			cell = row.createCell(5);
			cell.setCellValue(operator);
			String operaTime = json.get("OPERA_TIME").toString();
			cell = row.createCell(6);
			cell.setCellValue(operaTime);
			String outOperaTime = json.get("OUT_OPERA_TIME").toString();
			cell = row.createCell(7);
			cell.setCellValue(outOperaTime);
			String batchNumber = json.get("BATCH_NUMBER").toString();
			cell = row.createCell(8);
			cell.setCellValue(batchNumber);
			String outBatchNumber = json.get("OUT_BATCH_NUMBER").toString();
			cell = row.createCell(9);
			cell.setCellValue(outBatchNumber);
			String type = json.get("TYPE").toString();
			cell = row.createCell(10);
			cell.setCellValue(type.equals("1") ==true?"未取件":"已取件");
			String remark = json.get("REMARK").toString();
			cell = row.createCell(11);
			cell.setCellValue(BaseUtil.checkAllNull(remark)==true?"":remark);
//			for (Object  key : json.keySet()) {
//				cell = row.createCell(cellIndex++);
//				Object data = json.get(key);
//				cell.setCellValue(data.toString());
//			}
			//cellIndex = 0;
		}

		FileOutputStream os = new FileOutputStream(file);
		workBook.write(os);
		os.close();
		input.close();
		workBook.close();
	}
	
	/**
	 * 收寄件统计导出
	 * @param startDate
	 * @param endDate
	 * @param model
	 * @param file
	 * @param ja
	 * @param exportTitle
	 * @throws IOException
	 */
	public   void exportQueryDataWithInAndSend(String startDate,String endDate, File model,File file,JSONArray ja,String exportTitle) throws IOException {
		copyFile(model, file);
		InputStream input = new FileInputStream(file);
		Workbook workBook = new XSSFWorkbook(input); 
		Sheet sheet = workBook.getSheet("sheet1");
		Row row;
		Cell cell;
		CellStyle style = workBook.createCellStyle();// 创建样式对象
		Font font = workBook.createFont();// 创建字体对象
		setStyle(font,style);
		row = sheet.createRow(0);
		cell = row.createCell(0);
		cell.setCellStyle(style);
		cell.setCellValue(exportTitle);
		
		int rowIndex = 2;
		for (int i = 0; i < ja.size(); i++) {
			row = sheet.createRow(rowIndex++);
			JSONObject json = ja.getJSONObject(i);
			String name = json.get("NAME").toString();
			cell = row.createCell(0);
			cell.setCellValue(name);
			String icount = json.get("ICOUNT").toString();
			cell = row.createCell(1);
			cell.setCellValue(icount);
			String scount = json.get("SCOUNT").toString();
			cell = row.createCell(2);
			cell.setCellValue(scount);
			String total = json.get("TOTAL").toString();
			cell = row.createCell(3);
			cell.setCellValue(total);
		}

		FileOutputStream os = new FileOutputStream(file);
		workBook.write(os);
		os.close();
		input.close();
		workBook.close();
	}
	
	public   void exportQueryDataWithCustomerCount(File model,File file,JSONArray ja,String exportTitle) throws IOException {
		copyFile(model, file);
		InputStream input = new FileInputStream(file);
		Workbook workBook = new XSSFWorkbook(input); 
		Sheet sheet = workBook.getSheet("sheet1");
		Row row;
		Cell cell;
		CellStyle style = workBook.createCellStyle();// 创建样式对象
		Font font = workBook.createFont();// 创建字体对象
		setStyle(font,style);
		row = sheet.createRow(0);
		cell = row.createCell(0);
		cell.setCellStyle(style);
		cell.setCellValue(exportTitle);
		
		int rowIndex = 2;
		for (int i = 0; i < ja.size(); i++) {
			row = sheet.createRow(rowIndex++);
			JSONObject json = ja.getJSONObject(i);
			String name = json.get("NAME").toString();
			cell = row.createCell(0);
			cell.setCellValue(name);
			String count = json.get("COUNT").toString();
			cell = row.createCell(1);
			cell.setCellValue(count);
		}

		FileOutputStream os = new FileOutputStream(file);
		workBook.write(os);
		os.close();
		input.close();
		workBook.close();
	}
	
	public static void removeRow(Sheet sheet, int rowIndex) {
		int lastRowNum = sheet.getLastRowNum();
 		sheet.shiftRows(rowIndex, lastRowNum, -1);
		if (rowIndex == lastRowNum) {
			Row removingRow = sheet.getRow(rowIndex);
			if (removingRow != null) {
				sheet.removeRow(removingRow);
			}
		}
	}
	
	/**
	 * 拷贝文件
	 * @param in 源文件，包括整个文件实体
	 * @param out 目的文件，该文件对象只有文件路径与文件名
	 * @throws IOException 如果发生输入输出错误，如无源文件、路径错误等
	 */
	public static void copyFile(File in, File out) throws IOException {
		FileInputStream fis = new FileInputStream(in);
		FileOutputStream fos = new FileOutputStream(out);
		byte[] buf = new byte[1024];
		int i = 0;
		while ((i = fis.read(buf)) != -1) {
			fos.write(buf, 0, i);
		}
		fis.close();
		fos.close();
	}

	
	/**
	 * 设置表头
	 * @param sheet
	 * @param index
	 * @param m
	 * @param fs
	 */
	private static void setExcelTitle(Sheet sheet, int index,Map<String,String> m ,Field[] fs){
		Row row = sheet.createRow(index);
		int cellIndex = 0;
		for (Field f : fs) {
			Cell cell = row.createCell(cellIndex++);
			String title = m.get(f.getName()).toString();
			cell.setCellValue(title);
		}
	}
	
	public static void setStyle(Font font,CellStyle style) {  
		font.setFontHeightInPoints((short) 18);// 设置字体大小
		font.setFontName("黑体");
//		style.setFont(font);// 将字体加入到样式对象    
		// 设置对齐方式     
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);// 水平居中    
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直居中
		style.setFont(font);
		// 设置边框     
//		style.setBorderTop(HSSFCellStyle.BORDER_HAIR);// 顶部边框粗线    
//		style.setTopBorderColor(HSSFColor.BLUE_GREY.index);// 设置index color   
//		style.setBorderBottom(HSSFCellStyle.BORDER_DOUBLE);// 底部边框双线    
//		style.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);// 左边边框    
//		style.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);// 右边边框    
		// 格式化日期     
//		style.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));
		
	}
	
	/**
	 * 属性描述
	 * @return
	 */
	public static Map<String,String> excelTitle(){
		Map<String,String> m = new HashMap<String,String>();
		m.put("name", "姓名");
		m.put("sex", "性别");
		m.put("cardId", "身份证号码");
		m.put("lastSfTime", "最近一次随访日期");
		m.put("nextSfTime", "下次随访时间");
		m.put("patientCode", "病人治疗编码");
		m.put("firstDate", "抗病毒治疗日期");
		m.put("nextCd4CheckTime", "下次CD4检测日期");
		m.put("nextVlCheckTime", "下次病毒载量检测日期");
		m.put("nextOtherCheckTime", "下次其它检测日期");
		m.put("remainingDays", "剩余天数");
		m.put("overdueDays", "逾期天数");
		return m;
	}
	
	private static Field getField(Field[] fileds,String field) {
		for (Field f : fileds) {
			String name = f.getName();
			if (name.equals(field)) {
				return f;
			}
		}
		return null;
	}
	
	
	/***
	 * 判断一个字符串是不是纯数字
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}
	
	/***
	 * 判断一个字符是不是纯字母
	 * @param str
	 * @return
	 */
	public static boolean isCharacter(char str) {
		Pattern pattern = Pattern.compile("[a-z]*|[A-Z]*");
		Matcher isCha = pattern.matcher(String.valueOf(str));
		if (!isCha.matches()) {
			return false;
		}
		return true;
	}

	/**
	 * 是否是小数
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isDecimal(String str) {
		Pattern pattern = Pattern.compile("\\d+\\.\\d+$|-\\d+\\.\\d+$");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;

	}
	
	/**
	 * 判读是否是一个整数或是小数，
	 * @return
	 */
	public static boolean isNumber(String str){
		if(!nullOrEmpty(str) && (isNumeric(str) || isDecimal(str))){
			return true;
		}
		return false;
	}
	
	public static boolean nullOrEmpty(String str) {
		return str == null || str.trim().isEmpty();
	}
	
}

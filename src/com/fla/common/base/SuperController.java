package com.fla.common.base;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.InternalResourceView;
import com.fla.common.entity.SystemUser;
import com.fla.common.util.BaseUtil;

public class SuperController  implements Serializable {

	private static final long serialVersionUID = 4424574217359313952L;
	
	public static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	protected void setResponse(HttpServletResponse response) {
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("text/html;charset=UTF-8");
	}
	
	public String getExcelPath(HttpServletRequest request, String fileName){
		return request.getSession().getServletContext().getRealPath("/")+ "/WEB-INF/model/excel/" + fileName;
	}
	
	public String getExcelModelPath(HttpServletRequest request, String fileName){
		return request.getSession().getServletContext().getRealPath("/")+ "/WEB-INF/model/" + fileName;
	}
	
	public void downFile(HttpServletRequest request,HttpServletResponse response, File file) {
		// 清空response
		response.reset();
		response.setContentType("text/html; charset=UTF-8");
		try
		{
			// 设置response的Header
			response.addHeader("Content-Disposition", "attachment;filename="+BaseUtil.getStringDate()+".xlsx");
			response.addHeader("Content-Length", "" + file.length());
			// 以流的形式下载文件
			InputStream fis = new BufferedInputStream(new FileInputStream(file));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();

			OutputStream os = new BufferedOutputStream(response.getOutputStream());
			os.write(buffer);
			os.flush();
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			file = null;
			request = null;
			response = null;
		}
	}
	
	public void downAppFile(HttpServletRequest request,HttpServletResponse response, File file) {
		// 清空response
		response.reset();
		response.setContentType("text/html; charset=UTF-8");
		try
		{
			// 设置response的Header
			response.addHeader("Content-Disposition", "attachment;filename="+file.getName());
			response.addHeader("Content-Length", "" + file.length());
			// 以流的形式下载文件
			InputStream fis = new BufferedInputStream(new FileInputStream(file));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();

			OutputStream os = new BufferedOutputStream(response.getOutputStream());
			os.write(buffer);
			os.flush();
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			file = null;
			request = null;
			response = null;
		}
	}
	
	public ModelAndView JumpModelAndView() {
		InternalResourceView iv = new InternalResourceView("/pages/system/login.light");
		ModelAndView model = new ModelAndView(iv);
		model = new ModelAndView(iv);
		model.addObject("msg", "用户名或密码错误");
		model.addObject("msgType", "-1");
		return model;
	}
	
	public void afterRequest(HttpServletResponse response, String json) {
		PrintWriter printWriter = null;
		try 
		{
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
			printWriter = response.getWriter();
			printWriter.write(json);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			printWriter.flush();
			printWriter.close();
		}
	}
	
	public SystemUser getSystemUser(HttpServletRequest request, HttpServletResponse response){
		SystemUser systemUser = (SystemUser) request.getSession().getAttribute("systemUser");
		if (systemUser == null) {
			throw new RuntimeException("");
		}
		return systemUser;
	}
	
}
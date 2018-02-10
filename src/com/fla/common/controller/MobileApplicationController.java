package com.fla.common.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fla.common.base.SuperController;

@Controller
public class MobileApplicationController extends SuperController{

	private static final long serialVersionUID = 5769469404255763088L;
	private static final String applicationName = "exshop-app.apk";

	@ResponseBody
	@RequestMapping("/pages/mobile/down/MobileApk.light")
	public void downShopCustomerCount(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
//		URL fileUrl  = MobileApplicationController.class.getClassLoader().getResource("/mobile/"+applicationName);
//		File file = new File(fileUrl.getFile());
		File file = new File("C:\\application-apk\\"+applicationName);
		downAppFile(request, response, file);
	}
	
}
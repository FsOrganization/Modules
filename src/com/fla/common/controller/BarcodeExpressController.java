package com.fla.common.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
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

import sun.misc.BASE64Encoder;

import com.fla.common.base.SuperController;
import com.fla.common.entity.BarcodeExpress;
import com.fla.common.entity.ExpressInfo;
import com.fla.common.entity.SystemUser;
import com.fla.common.print.MatrixToImageWriter;
import com.fla.common.service.interfaces.BarcodeExpressServiceInterface;
import com.fla.common.service.interfaces.LoginServiceInterface;
import com.fla.payment.weixinPay.util.TenpayUtil;
import com.fla.payment.weixinPay.util.pay.Pay;
import com.fla.payment.weixinPay.util.pay.WxPayDto;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

@Controller
public class BarcodeExpressController extends SuperController{

	private static final long serialVersionUID = -9188166050181372461L;

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(BarcodeExpressController.class);
	
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	private static final String IP = "121.41.76.133";

	@Autowired
	private BarcodeExpressServiceInterface barcodeExpressService;
	
	@Autowired
	private LoginServiceInterface loginService;
	
	@org.springframework.web.bind.annotation.InitBinder
	public void InitBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, null, new CustomDateEditor(dateFormat, true));
	}

	@ResponseBody
	@RequestMapping("/pages/system/barcode/getBarcodeExpress.light")
	public void  getMenuList(String queryParams,Integer page, Integer rows,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		PageBounds pageBounds = new PageBounds(page, rows);
		Map<String,Object> params = new HashMap<String,Object>();
		List<BarcodeExpress> list = barcodeExpressService.getBarcodeExpressList(params, pageBounds);
		JSONArray jsonArray = JSONArray.fromObject(list);
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		printWriter.write(jsonArray.toString());
        printWriter.flush(); 
        printWriter.close();
	}
	
	@ResponseBody
	@RequestMapping("/pages/system/barcode/checkOrderPaymentStatus.light")
	public void  checkOrderPaymentStatus(String orderCode,Integer page, Integer rows,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("orderCode", orderCode);
		Integer paymentCount = barcodeExpressService.checkOrderPaymentStatus(params);
		JSONObject json = new JSONObject(); 
		if (paymentCount >0) {
			json.put("payment", "YES");
		} else {
			json.put("payment", "NO");
		}
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		printWriter.write(json.toString());
        printWriter.flush(); 
        printWriter.close();
	}
	
	@ResponseBody
	@RequestMapping("/pages/system/barcode/saveBarCodeExpress.light")
	public void saveMenu(String expressIdArrays, String orderCode, String barCode, HttpServletRequest request,HttpServletResponse response) throws IOException {
		JSONObject json = new JSONObject();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date currDate = new Date();
		String dsp = sdf.format(currDate);
		SystemUser systemUser = getSystemUser(request, response);
		String areaCode= systemUser.getAreaCode();
		String serviceShopCode= systemUser.getServiceShopCode();
		
		try 
		{
			String[] expressStrIds = expressIdArrays.split(",");
			for (String id : expressStrIds) {
				Integer expressId = Integer.valueOf(id);
				BarcodeExpress barcodeExpress = new BarcodeExpress();
				barcodeExpress.setExpressId(expressId);
				barcodeExpress.setAreaCode(areaCode);
				barcodeExpress.setServiceShopCode(serviceShopCode);
				barcodeExpress.setBarCode(barCode);
				barcodeExpress.setTimestampSpe(dsp);
				barcodeExpress.setOperaTime(currDate);
				barcodeExpress.setOperator(systemUser.getLoginName());
				barcodeExpress.setOrderCode(orderCode);
				barcodeExpressService.insert(barcodeExpress);
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		} 
		json.put("msg", "保存成功");
		json.put("barCode", barCode);
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		printWriter.write(json.toString());
        printWriter.flush();
        printWriter.close();
	}
	
	@ResponseBody
	@RequestMapping("/pages/system/barcode/initBarCode.light")
	public void initBarCode(String expressIdArrays, String orderCode, HttpServletRequest request,HttpServletResponse response) throws IOException {
		JSONObject json = new JSONObject();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String dsp = sdf.format(new Date());
		String barCode = getBarCode(request, dsp);
		json.put("initBarCode", barCode);
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		printWriter.write(json.toString());
        printWriter.flush();
        printWriter.close();
	}
	
	
	public String random(Integer a) {
		int s = (int) (Math.random()*a);
		return s+"";
	}
	
	private String getBarCode(HttpServletRequest request, String timestampSpe){
		SystemUser systemUser = getSystemUser(request, null);
		String areaCode= systemUser.getAreaCode();
		String serviceShopCode= systemUser.getServiceShopCode();
		String currTime = TenpayUtil.getCurrTime();
		String barCode = currTime+areaCode+random(1000)+serviceShopCode;
		return barCode;
	}
	
	@ResponseBody
	@RequestMapping("/pages/system/barcode/getBarCode.light")
	public void  getBarCode(String str,HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		SystemUser s = getSystemUser(request, response);
		if (s !=null) {
			String barCode = request.getParameter("barCode"); 
			String webPageTemp = "temp";
			String separator = System.getProperty ("file.separator");//File.separatorChar;
			separator = separator.replace("\\", "/");
			File file =  null;
			PrintWriter printWriter = null;
			 JSONObject json = new JSONObject();
			 Barcode barcode = null;
	        try 
	        {
	        	barcode = BarcodeFactory.createEAN128(barCode);
	        	barcode.setBarHeight(40);
	        	String tempPath = request.getSession().getServletContext().getRealPath(separator.toString())+ separator+webPageTemp;
	        	file = new File(tempPath+separator+barCode+"_temp.png");
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
	@RequestMapping("/pages/system/barcode/getExpressInfoByFilter.light")
	public void  getSimplyConstructedNotOutExpressInfoByFilter(String queryParams,int tag, HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		JSONObject json = new JSONObject();
		if (tag == 1) {
			SystemUser s = getSystemUser(request, response);
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("endDate", "");
			params.put("startDate", "");
			params.put("queryParams", queryParams);
			params.put("expressService", "");
			params.put("areaCode", s.getAreaCode());
			params.put("serviceShopCode", s.getServiceShopCode());
			System.out.println(params);
			List<ExpressInfo> eList = barcodeExpressService.getExpressInfoByParams(params);
			JSONArray jsonArray = JSONArray.fromObject(eList);
			json.put("eList", jsonArray);
			Map<String,Object> checkMap = new HashMap<String,Object>();
			checkMap.put("phoneNumber", queryParams);
			checkMap.put("isInterest", "Y");
			Integer count = barcodeExpressService.checkMember(checkMap);
			json.put("member", count);
			response.setCharacterEncoding("utf-8");          
			response.setContentType("text/html; charset=utf-8");
			 PrintWriter printWriter = response.getWriter();
			printWriter.write(json.toString()); 
	        printWriter.flush(); 
	        printWriter.close(); 
		} 

	}
	
	@ResponseBody
	@RequestMapping("/pages/system/barcode/getPayCodeURL.light")
	public void getWeixinPayCodeURL(HttpServletRequest request,HttpServletResponse response) throws SQLException, IOException {
		PrintWriter printWriter = null;
		String name = request.getParameter("name");
		String fee = request.getParameter("fee");
		JSONObject json = new JSONObject();
		String nonceStr = Pay.getNonceStr();
		try 
		{
			// 扫码支付
			WxPayDto tpWxPay1 = new WxPayDto();
			tpWxPay1.setBody(name);
			tpWxPay1.setOrderId(nonceStr);
			tpWxPay1.setSpbillCreateIp(IP);// 订单生成的机器 IP
			tpWxPay1.setTotalFee(fee);//fee
			String codeUrl = Pay.getCodeurl(tpWxPay1);
			json.put("codeUrl", codeUrl);
			json.put("orderCode", nonceStr);
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
            printWriter = response.getWriter();
            printWriter.write(json.toString());
		} finally {
			printWriter.flush();
			printWriter.close();
		}

	}
	
	@ResponseBody
	@RequestMapping("/pages/system/barcode/checkExpressIdPrint.light")
	public void checkExpressIdPrint(HttpServletRequest request,HttpServletResponse response) throws SQLException, IOException {
		PrintWriter printWriter = null;
		String expressId = request.getParameter("expressId");
		JSONObject json = new JSONObject();
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("expressId", Integer.valueOf(expressId));
		try 
		{
			Integer count = barcodeExpressService.checkExpressIdPrint(params);
			json.put("count", count);
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
            printWriter = response.getWriter();
            printWriter.write(json.toString());
		} finally {
			printWriter.flush();
			printWriter.close();
		}

	}
	
	@ResponseBody
	@RequestMapping("/pages/system/barcode/getPayBase64Code.light")
	public void getPayBase64Code(HttpServletRequest request,HttpServletResponse response) throws SQLException, IOException {
		PrintWriter printWriter = null;
		String contentCode = request.getParameter("contentCode");
		JSONObject json = new JSONObject();
		ByteArrayOutputStream outputStream = null;
		BufferedImage bImage = null;
		BufferedImage bImageFinal = null;
		try 
		{
			bImage = getBufferedImageByContent(contentCode);
			outputStream = new ByteArrayOutputStream();
			bImageFinal = handleGraphics2D(bImage);
			ImageIO.write(bImageFinal, "PNG", outputStream);
//			File f = new File("d://dfasdf.PNG");
//			ImageIO.write(bImageFinal, "PNG", f);
			BASE64Encoder encoder = new BASE64Encoder();
			String barBase64Code = encoder.encode(outputStream.toByteArray());
			json.put("barBase64Code", barBase64Code);
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html; charset=utf-8");
            printWriter = response.getWriter();
            printWriter.write(json.toString());
		} finally {
			printWriter.flush();
			printWriter.close();
			outputStream.close();
			bImage = null;
		}

	}
	
	public Font getFont(String font,int style,int fontSize) {
		return new Font(font, style, fontSize);
	}
	
	private BufferedImage handleGraphics2D(BufferedImage bImage){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String dateStr = sdf.format(new Date());
		BufferedImage bi = new BufferedImage(180,260, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics = bi.createGraphics();
		graphics .setColor( Color.WHITE );
		graphics .fillRect(0,0,180,260);
        graphics.setFont(getFont("Default",Font.BOLD, 13));
        graphics .setColor( Color.BLACK );
        graphics.drawString("源信幸福快递", 50, 18);
        graphics .setColor( Color.WHITE );
        graphics.setFont(getFont("Default",Font.PLAIN, 7));
        graphics .setColor( Color.BLACK );
        graphics.drawString("--------------------------------------------------------------------", 25, 30);
        graphics .setColor( Color.WHITE );
        graphics.drawImage(bImage, 27,30,null);
        graphics .setColor( Color.BLACK );
        graphics.drawString("--------------------------------------------------------------------", 25, 155);  
        graphics .setColor( Color.WHITE );
        graphics.setFont(getFont("Default",Font.PLAIN, 11));
        graphics .setColor( Color.BLACK );
        graphics.drawString("*打印时间:" +dateStr+"*", 35, 165);
        graphics .setColor( Color.WHITE );
        graphics.setFont(getFont("Default",Font.ITALIC, 10));  
        graphics .setColor( Color.BLACK );
        graphics.drawString("二维码仅当天有效", 50, 177);
        graphics .setColor( Color.WHITE );
        graphics.dispose();
		return bi;  
	}
	
	private BufferedImage getBufferedImageByContent(String content) {
		int qrcodeWidth = 135;
        int qrcodeHeight = 135;
        HashMap<EncodeHintType, String> hints = new HashMap<EncodeHintType, String>();
		MultiFormatWriter mfw = new MultiFormatWriter();
		BitMatrix bitMatrix = null;
		BufferedImage bufferedImage = null;
		try 
		{
			bitMatrix = mfw.encode(content, BarcodeFormat.QR_CODE, qrcodeWidth, qrcodeHeight, hints);
			bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
		} catch (WriterException e) {
			e.printStackTrace();
		} finally {
			bitMatrix.clear();
		}
		return bufferedImage;
	}
	
}
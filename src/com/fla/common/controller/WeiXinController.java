package com.fla.common.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.WechatToken.com.qq.weixin.mp.aes.SHATools;
import org.jeewx.api.core.common.WxstoreUtils;
import org.jeewx.api.core.exception.WexinReqException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.InternalResourceView;
import org.sword.wechat4j.common.Config;
import org.sword.wechat4j.token.TokenProxy;

import com.fla.common.base.SuperController;
import com.fla.common.service.interfaces.CustomerServiceInterface;
import com.fla.common.service.interfaces.LoginServiceInterface;
import com.fla.common.weixin.util.WechatProcess;
import com.fla.common.weixin.util.xml.ReceiveXmlEntity;

@Controller
public class WeiXinController extends SuperController{
	
	private static final long serialVersionUID = 8137315174834581896L;
//	private static Logger logger = Logger.getLogger(WeiXinController.class.getName());
//	@Autowired
//	public SystemUser systemUser;
	
	@Autowired
	private CustomerServiceInterface customerService;
	
	@Autowired
	private LoginServiceInterface loginServiceInterface;
	
	@org.springframework.web.bind.annotation.InitBinder
	public void InitBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, null, new CustomDateEditor(dateFormat, true));
	}
	
	@ResponseBody
	@RequestMapping("/pages/system/getSimplyConstructedQueryPage.light")
	public ModelAndView getSimplyConstructedQueryPage(HttpServletRequest request,HttpServletResponse response) {
		InternalResourceView iv = new InternalResourceView("/pages/business/test/weixinQueryPage.jsp");
		ModelAndView model = new ModelAndView(iv);
		model.addObject("time", System.currentTimeMillis());
		model.addObject("tag", 1);
		return model;
	}
	
	@ResponseBody
	@RequestMapping("/pages/system/getShopMapQueryPage.light")
	public ModelAndView getShopMapQueryPage(HttpServletRequest request,HttpServletResponse response) {
		InternalResourceView iv = new InternalResourceView("/map/weixinMapQueryPage.jsp");
		ModelAndView model = new ModelAndView(iv);
		model.addObject("time", System.currentTimeMillis());
		model.addObject("tag", 1);
		return model;
	}
	
	@ResponseBody
	@RequestMapping("/pages/system/getSimplyConstructedNotOutExpressInfoByFilter.light")
	public void  getSimplyConstructedNotOutExpressInfoByFilter(String queryParams,int tag,
			HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
//		SystemUser s = (SystemUser) request.getSession().getAttribute("systemUser");
		if (tag == 1) {
			final int rowSize=10;
			final int pageSize=1;
			Map<String,String> params = new HashMap<String,String>();
			params.put("endDate", "");
			params.put("startDate", "");
			params.put("queryParams", queryParams);
			params.put("expressService", "");
			params.put("areaCode", "");
			params.put("serviceShopCode", "");
			JSONArray ja = loginServiceInterface.getSimplyConstructedNotOutExpressInfoByFilter(rowSize, pageSize,params);
			response.setCharacterEncoding("utf-8");          
			response.setContentType("text/html; charset=utf-8");
			 PrintWriter printWriter = response.getWriter();
			printWriter.write(ja.toString()); 
	        printWriter.flush(); 
	        printWriter.close(); 
		} 

	}
	
	/**
	 * 手机座机号码验证
	 * @param input
	 * @return 当条件满足时，将返回true，否则返回false
	 */
	private static boolean checkCustomerInput(String input) {
		  Pattern pattern = Pattern.compile("[0]([0-9]{2,3})?[0-9]{7,8}|(^[1][345678][0-9]{9}$)");
		  Matcher matcher = pattern.matcher(input);
		  boolean b= matcher.matches();
		  return b;
	}
	
	@ResponseBody
	@RequestMapping("/pages/system/weixin.light")
	public void wechatServlet(HttpServletRequest request,HttpServletResponse response) throws SQLException, IOException {
		String token = Config.instance().getToken();
    	String accessToken = TokenProxy.accessToken();
		// 微信加密签名
		String signature = request.getParameter("signature");
		// 随机字符串
		String echostr = request.getParameter("echostr");
		// 时间戳
		String timestamp = request.getParameter("timestamp");
		// 随机数
		String nonce = request.getParameter("nonce");
		
		//key
//		String key = request.getParameter("key");
		String[] str = { token, timestamp, nonce };
		Arrays.sort(str); // 字典序排序
		String bigStr = str[0] + str[1] + str[2];
		// SHA1加密
		String digest = new SHATools().getDigestOfString(bigStr.getBytes()).toLowerCase();
		String s = "";
		String result = "";
		ReceiveXmlEntity rxe = null;
		// 确认请求来至微信
		if (digest.equals(signature)) {
			try 
			{
				request.setCharacterEncoding("UTF-8");
				response.setCharacterEncoding("UTF-8");
				/** 读取接收到的xml消息 */
				StringBuffer sb = new StringBuffer();
				InputStream is = request.getInputStream();
				InputStreamReader isr = new InputStreamReader(is, "UTF-8");
				BufferedReader br = new BufferedReader(isr);
				
				while ((s = br.readLine()) != null) 
				{
					sb.append(s);
				}
				String xml = sb.toString(); // 次即为接收到微信端发送过来的xml数据
				/** 判断是否是微信接入激活验证，只有首次接入验证时才会收到echostr参数，此时需要把它直接返回 */
				if (echostr != null && echostr.length() > 1) {
					result = echostr;
				} else {
					// 正常的微信处理流程
					rxe = new WechatProcess().processWechatMag(xml);
				}
//				String responseXml =  getRegisterPageXml(rxe);
				String funcKey =  rxe.getEventKey();
				switch (funcKey) {
				case "WECHAT_USER_REGISTRATION_1":
					JSONObject jo = checkWechatOpenId(rxe.getFromUserName(), null, null);
					boolean tag =  (boolean) jo.get("tag");
					String msg = null;
					try 
					{
						if (tag) {
							msg="尊敬的客户，你已经是幸福快递的会员";
						} else {
							msg="<a href='http://121.41.76.133/Express/pages/system/getWechatRegisterPage.light?openId="+rxe.getFromUserName()+"'>开始注册[点击开始]</a>";
						}
						sendMessage(accessToken, msg, rxe.getFromUserName());
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;
				case "WECHAT_YX_ASPECT_1":
					sendMessage(accessToken, "建设中...", rxe.getFromUserName());
					break;
				case "WECHAT_EXPRESS_SITE_1":
					break;
				case "WECHAT_EXPRESS_INQUIRY_1":
					break;
				default:
					String phoneNumberOrLogistics = rxe.getContent();
					if(checkCustomerInput(phoneNumberOrLogistics)) {
						Map<String,String> params = new HashMap<String,String>();
						params.put("queryParams", phoneNumberOrLogistics);
						JSONArray ja = loginServiceInterface.getSimplyConstructedNotOutExpressInfoByCustomerInput(params);
						String ss = new String("尊敬的客户,你有来至；"+"\n");
						StringBuilder sub = new StringBuilder();
						String SHOP_NAME = null;
						for (Object obj : ja) {
							JSONObject j = JSONObject.fromObject(obj);
							String LOGISTICS = j.get("LOGISTICS").toString();
							String PROVIDER_NAME = j.get("PROVIDER_NAME").toString();
							String OPERA_TIME = j.get("OPERA_TIME").toString();
							SHOP_NAME  =  j.get("SHOP_NAME").toString();
							String ff = PROVIDER_NAME+" 的快递："+LOGISTICS+" 收件时间："+OPERA_TIME+"\n";
							sub.append(ff);
						}
						String mmms = ss + sub.toString()+"请尽快到 "+SHOP_NAME+" 幸福快递网点领取!";
						sendMessage(accessToken, mmms, rxe.getFromUserName());
					} else {
						String m = "输入快递运单号或手机号码查询";
						sendMessage(accessToken, m, rxe.getFromUserName());
					}
					break;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@ResponseBody
	@RequestMapping("/pages/system/getWechatRegisterPage.light")
	public ModelAndView testRegisterPage(String openId) {
		InternalResourceView iv = new InternalResourceView("/pages/business/wechat/wechatRegisterPage.jsp");
		ModelAndView model = new ModelAndView(iv);
		model.addObject("openId", openId);
		return model;
	}
	
	public static void sendMessage(String accessToken, String msg,String touser) {
		try 
		{
			String url = MessageFormat.format("https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token={0}",accessToken);
			try 
			{
				net.sf.json.JSONObject obj = new net.sf.json.JSONObject();
				net.sf.json.JSONObject text = new net.sf.json.JSONObject();
				obj.put("touser", touser);
				text.put("content", msg);
				obj.put("text", text);
				obj.put("msgtype", "text");
				net.sf.json.JSONObject result = WxstoreUtils.httpRequest(url,"POST", obj.toString());
				System.out.println("微信响应结果："+result);
			} catch (Exception e) {
				throw new WexinReqException(e);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
		}
		
	}
	
	@ResponseBody
	@RequestMapping("/pages/system/wechat/sendMessage.light")
	public static void sendMessageLight(String msg,String touser,HttpServletRequest request, HttpServletResponse response) {
		String accessToken = TokenProxy.accessToken();
		try 
		{
			String url = MessageFormat.format("https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token={0}",accessToken);
			try 
			{
				net.sf.json.JSONObject obj = new net.sf.json.JSONObject();
				net.sf.json.JSONObject text = new net.sf.json.JSONObject();
				obj.put("touser", touser);
				text.put("content", msg);
				obj.put("text", text);
				obj.put("msgtype", "text");
				net.sf.json.JSONObject result = WxstoreUtils.httpRequest(url,"POST", obj.toString());
				System.out.println("微信响应结果："+result);
				response.setCharacterEncoding("utf-8");          
				response.setContentType("text/html; charset=utf-8");
				 PrintWriter printWriter = response.getWriter();
				printWriter.write(result.toString()); 
		        printWriter.flush(); 
		        printWriter.close();
			} catch (Exception e) {
				throw new WexinReqException(e);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
		}
		
	}

	public JSONObject  checkWechatOpenId(String openId,String phoneNumber,String shopCode) throws SQLException, IOException {
			Map<String,String> params = new HashMap<String,String>();
			params.put("phoneNumber", phoneNumber);
			params.put("shopCode", shopCode);
			params.put("openId", openId);
			JSONObject ja = customerService.checkWechatOpenId(params);
			return ja;
		}
	
}
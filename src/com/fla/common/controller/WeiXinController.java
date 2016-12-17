package com.fla.common.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
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
import org.sword.wechat4j.user.AccountManager;
import org.sword.wechat4j.user.Qrcode;

import com.fla.common.base.SuperController;
import com.fla.common.dao.interfaces.SystemConfigDaoInterface;
import com.fla.common.entity.SystemUser;
import com.fla.common.service.interfaces.CustomerServiceInterface;
import com.fla.common.service.interfaces.LoginServiceInterface;
import com.fla.common.service.interfaces.SystemServiceInterface;
import com.fla.common.util.Automatic.AutomaticMsgUtils;
import com.fla.common.weixin.util.WechatProcess;
import com.fla.common.weixin.util.xml.ReceiveXmlEntity;

@Controller
public class WeiXinController extends SuperController{
	
	private static final long serialVersionUID = 8137315174834581896L;
	
	public static final String SYS_HTTP = "http://121.41.76.133";
	
	@Autowired
	private SystemConfigDaoInterface systemConfigDao;
	
	public static final ArrayList<String> openIds = new ArrayList<String>(
			Arrays.asList(
					"oharnspby0RvSzYT7BhqhHgqTlSU",
					"oharnsu4Pnmz-Zq5fuugdVeKZHLg",
					"oharnsicBub9Pff8MQ95uPxhBems",
					"oharnsuEaG8_UJIivL4hWTyQOcec",
					 "oharnss9hPn2O4clpj2SmqyrSh2M",
					 "oharnsh4zkPqowYb3Z3G40uvH14w",
					 "oharnsnabPa0n62wmrUHt5O_xY_w",
					 "oharnsuRu6EHSFiWeHWq3rDh5XJU",
					 "oharnsmRyc0_dEvFwrYpM90lldNo",
					 "oharnsme0mwTznN-NGwec-sU-DKs",
					 "oharnskiWaeEosX3v8OL4NQwteus",
					 "oharnsq-h6ezWgFXC-T_QIyGOPRM",
					 "oharnsrFdJunlMWegTJbPG39GLDM",
					 "oharnsugKoZ1OSWVUd1itKMepGZs",
					"oharnsjZ0z_l9XwUxVud_5cMC0FM",
					"oharnssr95wXIzf1UKkx7sNmXBf4",
					"oharnsgQyUW6m_9pboksY8kcnr6w",
					"oharnsgCA4p9F7bM6_h8ixWxhIX4",
					"oharnsk2PVwruhWEPfqdw1sGxUQ4",
					"oharnslvF2ButDe-dS3lPdl0n1OE"
		));
	
	@Autowired
	private CustomerServiceInterface customerService;
	
	@Autowired
	private LoginServiceInterface loginServiceInterface;
	
	@Autowired
	private SystemServiceInterface systemServiceInterface;
	
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
	@RequestMapping("/pages/system/weixin/getWeixinExtractionHandle.light")
	public ModelAndView getWeixinExtractionHandle(HttpServletRequest request,HttpServletResponse response,String extractionCode) {
		InternalResourceView iv = null;
		ModelAndView model = null;
		Integer id = null;
		String recipientName = null;
		String expressLocation = null;
		String inOperaTime = null;
		JSONObject json = null;
		if (extractionCode != null && !extractionCode.equals("")) {
			String[] ss = extractionCode.split("-");
			String eCode = ss[0];
			String phoneNumber = ss[1];
			Map m = new HashMap();
			m.put("extractionCode", eCode);
			m.put("phoneNumber", phoneNumber);
			json = customerService.getOutExpressId(m);
			if (json.isNullObject()) {
				iv = new InternalResourceView("/pages/business/test/weixinExtraction-master/errorPage.jsp");
				model = new ModelAndView(iv);
				model.addObject("msg", "快递已领取！");
			} else {
				iv = new InternalResourceView("/pages/business/test/weixinExtraction-master/extractionHandle.jsp");
				model = new ModelAndView(iv);
				id = Integer.valueOf(json.get("ID").toString());
				recipientName = json.get("RECIPIENT_NAME").toString();
				inOperaTime = json.get("OPERA_TIME").toString();
				expressLocation = eCode.substring(eCode.length()-4,eCode.length());
				model.addObject("id", id);
				model.addObject("expressLocation", expressLocation);
				model.addObject("recipientName", recipientName);
				model.addObject("inOperaTime", inOperaTime);
				String lateFeeLimitUpper = (String) request.getSession().getAttribute("lateFeeLimitUpper");
				if (lateFeeLimitUpper == null || lateFeeLimitUpper.trim().equals("")) {
					Map<String,Object> params = new HashMap<String,Object>();
					params.put("configCode", "lateFeeLimitUpper");
					params.put("status", "1");
					lateFeeLimitUpper =systemConfigDao.getLateFeeLimitUpper(params);
					request.getSession().setAttribute("lateFeeLimitUpper", lateFeeLimitUpper);
				}
				
				String lateDayLimit = (String) request.getSession().getAttribute("lateDayLimit");
				if (lateDayLimit == null || lateDayLimit.trim().equals("")) {
					Map<String,Object> params = new HashMap<String,Object>();
					params.put("configCode", "lateDayLimit");
					params.put("status", "1");
					lateDayLimit =systemConfigDao.getLateDayLimit(params);
					request.getSession().setAttribute("lateDayLimit", lateDayLimit);
				}
				
				String memberLateFeeLimitUpper = (String) request.getSession().getAttribute("memberLateFeeLimitUpper");
				if (memberLateFeeLimitUpper == null || memberLateFeeLimitUpper.trim().equals("")) {
					Map<String,Object> params = new HashMap<String,Object>();
					params.put("configCode", "memberLateFeeLimitUpper");
					params.put("status", "1");
					lateDayLimit =systemConfigDao.getMemberLateFeeAddition(params);
				}
				
				String memberLateDayLimit = (String) request.getSession().getAttribute("memberLateDayLimit");
				if (memberLateDayLimit == null || memberLateDayLimit.trim().equals("")) {
					Map<String,Object> params = new HashMap<String,Object>();
					params.put("configCode", "memberLateDayLimit");
					params.put("status", "1");
					lateDayLimit =systemConfigDao.getMemberLateDayAddition(params);
				}
				
				model.addObject("lateFeeLimitUpper", lateFeeLimitUpper);
				model.addObject("lateDayLimit", lateDayLimit);
				model.addObject("memberLateFeeLimitUpper", memberLateFeeLimitUpper);
				model.addObject("memberLateDayLimit", memberLateDayLimit);
			}
		}
		return model;
	}
	
	
	/**
	 * 二维码取件
	 * @param request
	 * @param response
	 * @param extractionCode
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/pages/system/weixin/outByExtractionCode.light")
	public ModelAndView outByExtractionCode(HttpServletRequest request,HttpServletResponse response) {
		InternalResourceView iv = new InternalResourceView("/pages/business/test/weixinExtraction-master/endPage.jsp");
		ModelAndView model = new ModelAndView(iv);
		String idStr = request.getParameter("id");
		Integer id = Integer.valueOf(idStr);
		try 
		{
			JSONObject json = loginServiceInterface.letExpressOutStorehouseByExtractionCode(id);
			model.addObject("msg", json);
			model.addObject("type", true);
		} catch (SQLException e) {
			model.addObject("type", false);
			model.addObject("msg", "操作失败!");
			e.printStackTrace();
		}
		return model;
	}
	
	@ResponseBody
	@RequestMapping("/pages/system/weixin/getQRCode.light")
	public ModelAndView getQRCode(HttpServletRequest request,
			HttpServletResponse response, String extractionCode, String contactNumber) {
		InternalResourceView iv = new InternalResourceView("/pages/business/test/weixinExtraction-master/extractionQRCode.jsp");
		ModelAndView model = new ModelAndView(iv);
//		String accessToken = TokenProxy.accessTokenRealTime();
		String img = "";
		String url = SYS_HTTP+"/Express/pages/system/weixin/getWeixinExtractionHandle.light?extractionCode="+extractionCode+"&contactNumber="+contactNumber;
		AccountManager am = new AccountManager();
		Qrcode qr = am.createQrcodePerpetualstr(extractionCode+"-"+contactNumber,url);
		img = AccountManager.getQrcodeByBASE64Encoder(qr.getTicket());
		model.addObject("img", img);
		model.addObject("extractionCode", extractionCode);
		model.addObject("contactNumber", contactNumber);
		return model;
	}
	
	@ResponseBody
	@RequestMapping("/pages/system/weixin/rejectOperatingAuthorityPage.light")
	public ModelAndView rejectOperatingAuthorityPage(HttpServletRequest request,HttpServletResponse response) {
		InternalResourceView iv = new InternalResourceView("/pages/business/test/weixinExtraction-master/rejectOperatingAuthorityPage.jsp");
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
	
	private static boolean checkCustomerInputNumber(String input) {
		  Pattern pattern = Pattern.compile("^[0-9]*$");
		  Matcher matcher = pattern.matcher(input);
		  boolean b= matcher.matches();
		  return b;
	}
	
	@ResponseBody
	@RequestMapping("/pages/system/weixin.light")
	public void wechatServlet(HttpServletRequest request,HttpServletResponse response) throws SQLException, IOException, ServletException {
		String token = Config.instance().getToken();
		String accessToken = TokenProxy.accessTokenRealTime();
		// 微信加密签名
		String signature = request.getParameter("signature");
		// 随机字符串
		String echostr = request.getParameter("echostr");
		// 时间戳
		String timestamp = request.getParameter("timestamp");
		// 随机数
		String nonce = request.getParameter("nonce");
		// key
		// String key = request.getParameter("key");
		String[] str = { token, timestamp, nonce };
		Arrays.sort(str); // 字典序排序
		String bigStr = str[0] + str[1] + str[2];
		// SHA1加密
		String digest = new SHATools().getDigestOfString(bigStr.getBytes()).toLowerCase();
		String s = "";
		String result = "";
		ReceiveXmlEntity rxe = null;
		// 确认请求来至微信
		if (digest.equals(signature)) 
		{
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
				
				/** 判断是否是微信接入激活验证，只有首次接入验证时才会收到echostr参数，此时需要把它直接返回 */
				if (echostr != null && echostr.length() > 1) {
					//验证后台接口
					result = echostr;
					response.getWriter().print(echostr);
				} else {
					String xml = sb.toString(); // 次即为接收到微信端发送过来的xml数据
					System.out.println("xml="+xml);
					// 正常的微信处理流程
					rxe = new WechatProcess().processWechatMag(xml);
				}
				// String responseXml = getRegisterPageXml(rxe);
				if (rxe != null) {
					System.out.println(rxe.getEvent() + "," + rxe.getEventKey());
					System.out.println("fromUserName="+rxe.getFromUserName());
					String funcKey = rxe.getEventKey();
					String event = rxe.getEvent();
					if (rxe.getContent()!= null && rxe.getContent().equals("团购")) {
						String test = "http://pintuan.yxzhgroup.com/app/index.php?i=2&c=entry&do=index&m=feng_fightgroups&openId="+ rxe.getFromUserName();
						sendMessage(accessToken, test, rxe.getFromUserName());
					}
					if (event.equalsIgnoreCase("SCAN")) {
						String operaOpenId =  rxe.getFromUserName();
						if (openIds.contains(operaOpenId)) {
							String extractionCode = funcKey;
							String url = SYS_HTTP+"/Express/pages/system/weixin/getWeixinExtractionHandle.light?extractionCode="+extractionCode;
							String touser = rxe.getFromUserName();
							Map<String,String> map = new HashMap<String,String>(2);
							map.put("touser", touser);
							map.put("url", url);
							AutomaticMsgUtils.sendWechatTemplateMsgByExtractionCode(map);
						} else {
							String url = SYS_HTTP+"/Express/pages/system/weixin/rejectOperatingAuthorityPage.light";
							String touser = rxe.getFromUserName();
							Map<String,String> map = new HashMap<String,String>(2);
							map.put("touser", touser);
							map.put("url", url);
							AutomaticMsgUtils.sendWechatTemplateMsgByExtractionCode(map);
						}
					} 
					if (funcKey.equals("WECHAT_USER_REGISTRATION_1")) {
						JSONObject jo = checkWechatOpenId(rxe.getFromUserName(), null, null);
						boolean tag = (boolean) jo.get("tag");
						String msg = null;
						try 
						{
							if (tag) {
								msg = "尊敬的客户，你已经是幸福快递的会员";
							} else {
								msg = "<a href='"+SYS_HTTP+"/Express/pages/system/getWechatRegisterPage.light?openId="+ rxe.getFromUserName()+ "'>开始注册[点击开始]</a>";
							}
							sendMessage(accessToken, msg, rxe.getFromUserName());
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else if (funcKey.equals("WECHAT_YX_ASPECT_1")) {
						// System.out.println("WECHAT_YX_ASPECT_1:"+funcKey);
						sendMessage(accessToken, "建设中...",rxe.getFromUserName());
					} else if (funcKey.equals("WECHAT_EXPRESS_APP")) {
						
					} else if (funcKey.equals(SYS_HTTP+"/Express/pages/system/getShopMapQueryPage.light")) {
						// System.out.println("no funcKey:"+funcKey);
					} else if (funcKey.equals(SYS_HTTP+"/Express/pages/system/getSimplyConstructedQueryPage.light")) {
						// System.out.println("no funcKey:"+funcKey);
					} else {
						String phoneNumberOrLogistics = rxe.getContent();
						if (checkCustomerInput(phoneNumberOrLogistics)|| checkCustomerInputNumber(phoneNumberOrLogistics)) {
							Map<String, String> params = new HashMap<String, String>();
							params.put("queryParams", phoneNumberOrLogistics);
							JSONArray ja = loginServiceInterface.getSimplyConstructedNotOutExpressInfoByCustomerInput(params);
							if (ja == null || ja.size() == 0) {
								String m = "当前没有您的快递!";
								sendMessage(accessToken, m,rxe.getFromUserName());
							} else {
								String ss = new String("尊敬的客户！您有来至:" + "\n");
								StringBuilder sub = new StringBuilder();
								String SHOP_NAME = null;
								for (Object obj : ja) {
									JSONObject json = JSONObject.fromObject(obj);
									// System.out.println("json:"+json+",ja.size()"+ja.size());
									String LOGISTICS = json.get("LOGISTICS").toString();
									String PROVIDER_NAME = json.get("PROVIDER_NAME").toString();
									String OPERA_TIME = json.get("OPERA_TIME").toString();
									SHOP_NAME = json.get("SHOP_NAME").toString();
									String ff = PROVIDER_NAME + " 的快递："+ LOGISTICS + " 到达时间：" + OPERA_TIME+ "\n";
									sub.append(ff);
									sub.append("- - - - - - - - - - - - - - - - - -  ");
									sub.append("\n");
								}
								String mmms = ss + sub.toString() + "请尽快到 "+ SHOP_NAME + " 幸福驿站领取!";
								sendMessage(accessToken, mmms,rxe.getFromUserName());
							}
						} else {
							String m = "输入快递运单号或手机号码查询";
							sendMessage(accessToken, m, rxe.getFromUserName());
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@ResponseBody
	@RequestMapping("/pages/system/getWechatRegisterPage.light")
	public ModelAndView getWechatRegisterPage(String openId) {
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
				System.out.println("查询响应："+result);
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
		String accessToken = TokenProxy.accessTokenRealTime();
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
				System.out.println("推送微信信息："+result);
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
	
	@ResponseBody
	@RequestMapping("/pages/system/wechat/sendWechatMsgAutomatic.light")
	public  void sendWechatMsgAutomatic(String msg, String phoneNumber,String expServiceName,
			String expServiceId,String expressLocation,String logistics,HttpServletRequest request, HttpServletResponse response) {
		SystemUser s = getSystemUser(request, response);
		Map<String,String> params = new HashMap<String,String>(1);
		params.put("phoneNumber", phoneNumber);
		params.put("loginName", s.getLoginName());
		JSONObject customerObj =  customerService.getCustomerInfoByPhoneNumber(params);
		String shopName =null;
		if (s == null ||s.getShopName() == null || s.getShopName().equals("")) {
			shopName= "幸福驿站";
		} else {
			shopName = s.getShopName();//customerObj.getString("NAME");
		}
		String touser = customerObj.getString("WEIXIN_ID");
//		String serviceShopCode = customerObj.getString("SERVICE_SHOP_CODE");
		String shopPhoneNumber = customerObj.getString("SHOP_PHONE_NUMBER");
		if (touser == null || touser.length() == 0 || touser.trim().equals("") || touser.trim().equalsIgnoreCase("null")) {
			return;
		} else {
			Map<String,String> paraMap = new HashMap<String,String>();
			paraMap.put("touser",touser);
			paraMap.put("phoneNumber",phoneNumber);
			paraMap.put("shopName",shopName);
			paraMap.put("serviceShopCode",s.getServiceShopCode());//获取入库操作人的网点编号
			paraMap.put("expServiceName",expServiceName);
			paraMap.put("expServiceId",expServiceId);
			paraMap.put("expressLocation",expressLocation);
			paraMap.put("msg",msg);
			paraMap.put("expLogistics",logistics);
			paraMap.put("shopPhoneNumber",shopPhoneNumber);
//			AutomaticMsgUtils.sendWechatMsgAutomatic(paraMap);//文本消息
			AutomaticMsgUtils.sendWechatTemplateMsgAutomatic(paraMap); //模板消息
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
package com.fla.common.util.Automatic;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.jeewx.api.core.common.WxstoreUtils;
import org.jeewx.api.core.exception.WexinReqException;
import org.jeewx.api.core.req.model.message.TemplateData;
import org.jeewx.api.wxaccount.wxQrcode;
import org.jeewx.api.wxaccount.model.WxQrcode;
import org.sword.wechat4j.token.TokenProxy;
import org.sword.wechat4j.user.AccountManager;

import com.fla.common.controller.Config.ControllerSite;
import com.fla.common.sms.MessageServer;
import com.fla.common.util.DateUtil;

public class AutomaticMsgUtils {
	private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private final static String head="【源信幸福驿站】";
	public AutomaticMsgUtils() {
	}

	/**
	 * 推送短信通知
	 * @param map
	 */
	public static void sendRemindersToCustomer(Map<String,String> map) {
		try 
		{
			String mobile = map.get("phoneNumber");
			String shopName = map.get("shopName");
			String logistics = map.get("logistics");
			StringBuffer msg = new StringBuffer();
			msg.append("亲！您的宝贝:");
			msg.append(logistics);
			msg.append("已到");
			msg.append(shopName);
			msg.append("，请速取。电话：18311599537");
			MessageServer ms = new MessageServer(head);
			ms.send(mobile,msg.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 推送微信通知
	 * @param map
	 */
	public static void sendWechatMsgAutomatic(Map<String,String> map) {
		String accessToken = TokenProxy.accessTokenRealTime();
		String touser = map.get("touser");
		String phoneNumber = map.get("phoneNumber");
		String shopName = map.get("shopName");
		String msg = map.get("msg");
		String strDate = DateUtil.dateToNohmsStr(new Date(), null);
		
		String sMsg = replaceMsg(msg, shopName, strDate);
		String httpInterface = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token={0}";
		try 
		{
			String url = MessageFormat.format(httpInterface,accessToken);
			try 
			{
				net.sf.json.JSONObject obj = new net.sf.json.JSONObject();
				net.sf.json.JSONObject text = new net.sf.json.JSONObject();
				obj.put("touser", touser);
				text.put("content", sMsg);
				obj.put("text", text);
				obj.put("msgtype", "text");
				net.sf.json.JSONObject result = WxstoreUtils.httpRequest(url,"POST", obj.toString());
				System.out.println("微信响应结果："+phoneNumber+",touser:"+touser+"," + result);
			} catch (Exception e) {
				throw new WexinReqException(e);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
				
		}
	}
	
	
	
	public static void sendWechatTemplateMsgAutomatic(Map<String,String> map) {
		String accessToken = TokenProxy.accessTokenRealTime();
		String touser = map.get("touser");
		String phoneNumber = map.get("phoneNumber");
		String shopName = map.get("shopName");
		String serviceShopCode = map.get("serviceShopCode");
		String expLogistics = map.get("expLogistics");
		String expServiceName = map.get("expServiceName");
		String shopPhoneNumber = map.get("shopPhoneNumber");
//		String expServiceId = map.get("expServiceId");
		String expressLocation = map.get("expressLocation");
//		String serviceShopCodeStr= Integer.toHexString(Integer.valueOf(serviceShopCode));
		String httpInterface = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token={0}";
		String extractionCode = serviceShopCode+expressLocation;
		try 
		{
			String url = MessageFormat.format(httpInterface,accessToken);
			try 
			{
				String obj = getTemplateMessage(touser, expLogistics, shopName,shopPhoneNumber,expServiceName, phoneNumber,extractionCode);
				net.sf.json.JSONObject result = WxstoreUtils.httpRequest(url,"POST", obj.toString());
				System.out.println("微信响应结果："+phoneNumber+",touser:"+touser+"," + result);
			} catch (Exception e) {
				throw new WexinReqException(e);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
				
		}
	}
	
	public static void sendWechatTemplateMsgByExtractionCode(Map<String,String> map) {
		String accessToken = TokenProxy.accessTokenRealTime();
		String touser = map.get("touser");
		String operaUrl = map.get("url");
		String httpInterface = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token={0}";
		try 
		{
			String url = MessageFormat.format(httpInterface,accessToken);
			try 
			{
				String obj = getTemplateMessageByExtractionCode(touser, operaUrl);
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
	
	/**
     * 发送模板消息
     * openId 用户标识
     */
	public static String getTemplateMessage(String openId,String expLogistics,String shopName,String shopPhoneNumber,String expServiceName,String contactNumber,String extractionCode) {
		WxTemplate temp = new WxTemplate();
		temp.setUrl(ControllerSite.SYS_HTTP+"/Express/pages/system/weixin/getQRCode.light?extractionCode="+extractionCode+"&contactNumber="+contactNumber);
		temp.setTouser(openId);
		temp.setTopcolor("#000000");
		temp.setTemplate_id("Kgk_Ek6MiBfUse0yMYXBKJyQlKugsSadGLtuShQq7hI");
		Map<String, TemplateData> m = new HashMap<String, TemplateData>();
		TemplateData first = new TemplateData();
		first.setColor("#000000");
		first.setValue("亲，您的快递已到达幸福驿站，请尽快领取！");
		m.put("first", first);
		TemplateData expressCompany = new TemplateData();
		expressCompany.setColor("#000000");
		expressCompany.setValue(expServiceName);
		m.put("keyword1", expressCompany);
		TemplateData logistics = new TemplateData();
		logistics.setColor("#000000");
		logistics.setValue(expLogistics);
		m.put("keyword2", logistics);
		TemplateData limitTime= new TemplateData();
		limitTime.setColor("#000000");
		limitTime.setValue("请尽快领取");
		m.put("keyword3", limitTime);
		TemplateData shopAddr = new TemplateData();
		shopAddr.setColor("#000000");
		shopAddr.setValue(shopName);
		m.put("keyword4", shopAddr);
		TemplateData contact = new TemplateData();
		contact.setColor("#000000");
		contact.setValue(shopPhoneNumber);
		m.put("keyword5", contact);
		TemplateData remark = new TemplateData();
		remark.setColor("#000000");
//		remark.setValue("温馨提示：快递件长期存放易造成货物堆积、丢失，故暂存一日，超期按1元每件/天收取，如有不妥，请谅解！！！");
		remark.setValue("温馨提示：快递件长期存放易造成货物堆积、丢失，请尽快领取！！！");
		m.put("remark", remark);
		temp.setData(m);
		String jsonString = JSONObject.fromObject(temp).toString();
		return jsonString;
	}
	
	
	/**
     * 发送二维码取件模板消息
     * openId 用户标识
     */
	public static String getTemplateMessageByExtractionCode(String openId,String url) {
		WxTemplate temp = new WxTemplate();
		temp.setUrl(url);
		temp.setTouser(openId);
		temp.setTopcolor("#000000");
		temp.setTemplate_id("wKycDm5kkp3vVShHKzdPwCU46yx5jQl43j_THa_j_7g");
		Map<String, TemplateData> m = new HashMap<String, TemplateData>();
		TemplateData first = new TemplateData();
		first.setColor("#000000");
		first.setValue("扫码成功，点击本页操作！");
		m.put("first", first);
		TemplateData extractionType = new TemplateData();
		extractionType.setColor("#000000");
		extractionType.setValue("扫码取件");
		m.put("keyword1", extractionType);
		TemplateData operaDate = new TemplateData();
		operaDate.setColor("#000000");
		operaDate.setValue(sdf.format(new Date()));
		m.put("keyword2", operaDate);
		TemplateData remark = new TemplateData();
		remark.setColor("#000000");
		remark.setValue("温馨提示：请认真核对取件信息！！！");
		m.put("remark", remark);
		temp.setData(m);
		String jsonString = JSONObject.fromObject(temp).toString();
		return jsonString;
	}
	
	public static void main(String[] args){
//		String accessToken = TokenProxy.accessTokenRealTime();
//		try 
//		{
//			WxQrcode wq = wxQrcode.createQrcode(accessToken, "30", wxQrcode.QRCODE_TYPE_LIMIT, "7200");
//			AccountManager.getQrcodeByBASE64Encoder(wq.getTicket());
//		} catch (WexinReqException e) {
//			e.printStackTrace();
//		}
		String expServiceIdHex1= Integer.toHexString(Integer.valueOf("1011"));
		String expServiceIdHex2= Integer.toHexString(Integer.valueOf("1012"));
		String expServiceIdHex3= Integer.toHexString(Integer.valueOf("1013"));
		String expServiceIdHex4= Integer.toHexString(Integer.valueOf("1014"));
		System.out.println(expServiceIdHex1+expServiceIdHex2+expServiceIdHex3+expServiceIdHex4);
	}
	
	private static String replaceMsg(String msg,String shopName,String operaTime) {
		StringBuilder sbMsg = new StringBuilder();
		sbMsg.append(msg.replace("@shopName", shopName).replace("@operaTime", operaTime));
		return sbMsg.toString();
	}
}

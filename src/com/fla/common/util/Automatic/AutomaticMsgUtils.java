package com.fla.common.util.Automatic;

import java.text.MessageFormat;
import java.util.Date;
import java.util.Map;

import org.jeewx.api.core.common.WxstoreUtils;
import org.jeewx.api.core.exception.WexinReqException;
import org.sword.wechat4j.token.TokenProxy;

import com.fla.common.sms.MessageServer;
import com.fla.common.util.DateUtil;

public class AutomaticMsgUtils {
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
		String accessToken = TokenProxy.accessToken();
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
	
	private static String replaceMsg(String msg,String shopName,String operaTime) {
		StringBuilder sbMsg = new StringBuilder();
		sbMsg.append(msg.replace("@shopName", shopName).replace("@operaTime", operaTime));
		return sbMsg.toString();
	}
}

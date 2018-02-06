
package org.sword.wechat4j.token;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.sword.lang.HttpUtils;
import org.sword.wechat4j.common.Config;
import org.sword.wechat4j.token.server.AccessTokenServer;
import org.sword.wechat4j.token.server.JsApiTicketServer;
import org.sword.wechat4j.token.server.TicketServer;
import org.sword.wechat4j.token.server.TokenServer;

import com.alibaba.fastjson.JSONObject;


/**
 * AccessToken代理
 * 所有获取accessToken的地方都通过此代理获得
 * 获得方法String token = AccessTokenProxy.token()
 */
public class TokenProxy {
	private static Logger logger = Logger.getLogger(TokenProxy.class);
	private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential";
	/**
	 * 通过代理得到accessToken的串
	 */
	public static String accessToken(){
		TokenServer accessTokenServer = new AccessTokenServer();
		return accessTokenServer.token();
	}
	
	protected  static String accessTokenUrl() {
		String appid = Config.instance().getAppid();
		String appsecret = Config.instance().getAppSecret();
		String url = ACCESS_TOKEN_URL + "&appid=" + appid + "&secret=" + appsecret;
		logger.info("创建获取access_token url");
		return url;
	}
	/**
	 * 获取新的accessToken串
	 */
	public static String accessTokenRealTime(){
			String url = accessTokenUrl();
			String result = HttpUtils.get(url);
			return parseData(result);
			}

	/**
	 * 通过代理得到jsapi_ticket
	 */
	public static String jsApiTicket(){
		TicketServer ticketServer = new JsApiTicketServer();
		return ticketServer.ticket();
	}
	
	
	/**
	 * 
	 * @param data
	 * @return
	 */
	private  static String parseData(String data){
		JSONObject jsonObject = JSONObject.parseObject(data);//token json
		String token = jsonObject.get("access_token").toString();
			return token;
	}
	
	
}

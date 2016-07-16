package org.jeewx.api.wxbase.wxtoken;

import net.sf.json.JSONObject;

import org.jeewx.api.core.exception.WexinReqException;
import org.jeewx.api.core.req.WeiXinReqService;
import org.jeewx.api.core.req.model.AccessToken;

/**
 * 微信--token信息
 * 
 * @author lizr
 * 
 */
public class JwTokenAPI {

	private static AccessToken atoken = null;

	/**
	 * 获取权限令牌信息
	 * @param appid (开发者应用ID)
	 * @param appscret (开发者应用密钥)
	 * @return 
	 * @throws WexinReqException
	 */
	public static String getAccessToken(String appid, String appscret) throws WexinReqException{
		String newAccessToken = "";
		atoken = new AccessToken();
		atoken.setAppid(appid);
		atoken.setSecret(appscret);
		JSONObject result = WeiXinReqService.getInstance().doWeinxinReqJson(atoken);
		// 正常返回
		newAccessToken = result.getString("access_token");;
		return newAccessToken;
	}
	 
	
	public static void main(String[] args){
		 
		try {
			String s = JwTokenAPI.getAccessToken("wxc016c959d3870b52","c7843e15394196bb9840b03eb1a03cfc");
			System.out.println(s);
		} catch (WexinReqException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

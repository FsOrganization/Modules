package com.fla.common.weixin.menu;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.sword.wechat4j.token.TokenProxy;

import com.fla.common.weixin.util.HttpClientConnectionManager;

public class MenuUtil {  
	  
    public static String APPID,APPSECRET;  
      
    //http客户端    
    public static DefaultHttpClient httpclient;  
      
	static {
		httpclient = new DefaultHttpClient();
		httpclient = (DefaultHttpClient) HttpClientConnectionManager.getSSLInstance(httpclient); // 接受任何证书的浏览器客户端
//		APPID = "";// 你的APPID
//		APPSECRET = ""; // 你的APPSECRET
	} 
      
    /** 
     * 创建菜单  
     */  
	public static String createMenu(String params, String accessToken) throws Exception {
		HttpPost httpost = HttpClientConnectionManager.getPostMethod("https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+ accessToken);
		httpost.setEntity(new StringEntity(params, "UTF-8"));
		HttpResponse response = httpclient.execute(httpost);
		String jsonStr = EntityUtils.toString(response.getEntity(), "utf-8");
		JSONObject demoJson = new JSONObject(jsonStr);
		return demoJson.getString("errmsg");
	}
 
    /** 
     * 获取accessToken  
     */  
    public static String getAccessToken(String appid, String secret) throws Exception {    
        HttpGet get = HttpClientConnectionManager.getGetMethod("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appid + "&secret=" + secret);    
        HttpResponse response = httpclient.execute(get);    
        String jsonStr = EntityUtils.toString(response.getEntity(), "utf-8");  
        JSONObject demoJson = new JSONObject(jsonStr);  
        return demoJson.getString("access_token");  
    }    
    /** 
     * 查询菜单 
     */  
    public static String getMenuInfo(String accessToken) throws Exception {    
        HttpGet get = HttpClientConnectionManager.getGetMethod("https://api.weixin.qq.com/cgi-bin/menu/get?access_token=" + accessToken);    
        HttpResponse response = httpclient.execute(get);    
        String jsonStr = EntityUtils.toString(response.getEntity(), "utf-8");    
        return jsonStr;    
    }    
      
    /**  
     * 删除菜单  
     */    
    public static String deleteMenuInfo(String accessToken) throws Exception {    
        HttpGet get = HttpClientConnectionManager.getGetMethod("https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=" + accessToken);    
        HttpResponse response = httpclient.execute(get);    
        String jsonStr = EntityUtils.toString(response.getEntity(), "utf-8");    
        JSONObject demoJson = new JSONObject(jsonStr);  
        return demoJson.getString("errmsg");    
    }    
      
	/*
	 * 测试初始化微信菜单
	 */
	public static void main(String[] args) {
		String user_define_menu = 
		"{\"button\":["
				+ "{\"type\":\"view\",\"name\":\"幸福拼团\",\"key\":\"WECHAT_EXPRESS_APP\",\"url\":\"http://pintuan.yxzhgroup.com/app/index.php?i=2&c=entry&do=index&m=feng_fightgroups\"},"
				+ "{\"type\":\"click\",\"name\":\"用户注册\",\"key\":\"WECHAT_USER_REGISTRATION_1\"},"
				+ "{\"type\":\"view\",\"name\":\"快递查询\",\"key\":\"WECHAT_EXPRESS_INQUIRY_1\",\"url\":\"http://121.41.76.133/Express/pages/system/getSimplyConstructedQueryPage.light\"},"
//				+ "{\"name\":\"源信服务\",\"sub_button\":["
//								+ "{\"type\":\"view\",\"name\":\"快递站点\",\"key\":\"WECHAT_EXPRESS_SITE_1\",\"url\":\"http://121.41.76.133/Express/pages/system/getShopMapQueryPage.light\"}"
//								+ "{\"type\":\"view\",\"name\":\"快递查询\",\"key\":\"WECHAT_EXPRESS_INQUIRY_1\",\"url\":\"http://121.41.76.133/Express/pages/system/getSimplyConstructedQueryPage.light\"},"
//								+ "]"
//				+ "}"
			+ "]"
		+ "}";
		try 
		{
			String accessToken = TokenProxy.accessTokenRealTime();
			String res = "";
			res = createMenu(user_define_menu, accessToken);
			System.out.println(res);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
  
}  
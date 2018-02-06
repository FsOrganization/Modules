package com.fla.common.weixin;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jeewx.api.core.common.WxstoreUtils;
import org.jeewx.api.core.exception.WexinReqException;
import org.jeewx.api.wxsendmsg.model.SendMessageResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import org.sword.wechat4j.token.TokenProxy;
import org.sword.wechat4j.user.Group;


public class Weixin {
	public static Log log = LogFactory.getLog(Weixin.class);
	private static class TrustAnyTrustManager implements X509TrustManager {
		 
        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }
 
        public void checkServerTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }
 
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[] {};
        }
    }
 
    private static class TrustAnyHostnameVerifier implements HostnameVerifier {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }
 
    /**
     * post方式请求服务器(https协议)
     * @param url 请求地址
     * @param content 参数
     * @param charset 编码
     * @return
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     * @throws IOException
     */
	public static byte[] post(String url, String content, String charset) throws NoSuchAlgorithmException, KeyManagementException,IOException {
		SSLContext sc = SSLContext.getInstance("SSL");
		sc.init(null, new TrustManager[] { new TrustAnyTrustManager()},new java.security.SecureRandom());
		URL console = new URL(url);
		HttpsURLConnection conn = (HttpsURLConnection) console.openConnection();
		conn.setSSLSocketFactory(sc.getSocketFactory());
		conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
		conn.setDoOutput(true);
		conn.connect();
		DataOutputStream out = new DataOutputStream(conn.getOutputStream());
		out.write(content.getBytes(charset));
		// 刷新、关闭
		out.flush();
		out.close();
		InputStream is = conn.getInputStream();
		if (is != null) {
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = is.read(buffer)) != -1) {
				outStream.write(buffer, 0, len);
			}
			is.close();
			return outStream.toByteArray();
		}
		return null;
	}
    
    public static String getUserInfo(String accessToken,String openId){
    	String getUserInfoUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token="+accessToken+"&openid="+openId+"&lang=zh_CN";
    	return getUserInfoUrl;
    }
    public static String getContent() {
		StringBuilder sb = new StringBuilder();
		sb.append("<xml>" + 
				"<ToUserName><![CDATA[13980051144]]></ToUserName>"
				+ "<FromUserName><![CDATA[fromUser]]></FromUserName>"
				+ "<CreateTime>1348831860</CreateTime>"
				+ "<MsgType><![CDATA[0]]></MsgType>"
				+ "<Content><![CDATA[this is a test]]></Content>"
				+ " <MsgId>1234567890123456</MsgId>" + " </xml>");

		return sb.toString();
	}
    
	public static void main(String[] args) throws KeyManagementException,
			NoSuchAlgorithmException, IOException {
		// String accessTokenUrl = getAccessTokenUrl();
//		String accessToken = TokenProxy.accessTokenRealTime();// AccessTokenMemServer.instance().token();
//		 String openId = "oharnsu4Pnmz-Zq5fuugdVeKZHLg";
//		 String charset = "utf-8";
//		 byte[] c = post(getUserInfo(accessToken, openId), getContent(), charset);
//		 String s = new String(c, "UTF-8");
//		 System.out.println(s);
//		String msg = null;
//		String url = getUserInfo(accessToken, openId);
//		Weixin wx = new Weixin();
//		System.out.println(wx.getOneWeiXinUser(url));
//		List list = getAllWeiXinUser(accessToken);
//		List<Group> dd= wx.getAllGroups(accessToken);
//		for (Group group : dd) {
//			System.out.println(group.getName());
//		}
//		for (Object obj : list) {
////			msg = "源信快递 邀请您加入幸福快递俱乐部！<a href='http://121.41.76.133/Express/pages/system/getWechatRegisterPage.light?openId="+ obj.toString() + "'>开始注册[点击开始]</a>";
////			wx.sendMessage(accessToken, msg, obj.toString());
//			System.out.println(obj);
//		}
		net.sf.json.JSONArray ss = new net.sf.json.JSONArray();
		net.sf.json.JSONObject ffs = new net.sf.json.JSONObject();
		ffs.put("LOGISTICS", "345476856634523435");
		ffs.put("PROVIDER_NAME", "冻豆腐");
		ffs.put("OPERA_TIME", "2015-01-01");
		ffs.put("SHOP_NAME", "地方是");
		net.sf.json.JSONObject ffs3 = new net.sf.json.JSONObject();
		ffs3.put("LOGISTICS", "3454768523435");
		ffs3.put("PROVIDER_NAME", "顺分");
		ffs3.put("OPERA_TIME", "2015-01-06");
		ffs3.put("SHOP_NAME", "鬼画符");
		ss.add(ffs);
		ss.add(ffs3);
		test(ss);
	}
    
    /**
     * @desc  给一位关注者推送信息
     * @param token
     * @param msg
     * @return
     */
	public SendMessageResponse sendMessage(String token, String msg,String touser) {
		SendMessageResponse response = null;
		try 
		{
			log.info("sendMessage start.token:" + token + ",msg:" + msg);
			String url = MessageFormat.format("https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token={0}",token);
			System.out.println(url);
			try 
			{
				net.sf.json.JSONObject obj = new net.sf.json.JSONObject();
				net.sf.json.JSONObject text = new net.sf.json.JSONObject();
				obj.put("touser", touser);
				text.put("content", msg);
				obj.put("text", text);
				obj.put("msgtype", "text");
				net.sf.json.JSONObject result = WxstoreUtils.httpRequest(url,"POST", obj.toString());
				System.out.println("微信返回的结果：" + result.toString());
				response = (SendMessageResponse) net.sf.json.JSONObject.toBean(result, SendMessageResponse.class);
			} catch (Exception e) {
				throw new WexinReqException(e);
			}
			return response;
		} catch (Exception e) {
			log.error("get user info exception:", e);
			return null;
		} finally {
			
		}
		
	}
	
	/**
     * @desc  给所有关注者推送信息
     * @param token
     * @param msg
     * @return
     */
	public SendMessageResponse sendMessageToAll(String token, String msg) {
		SendMessageResponse response = null;
		List list = getAllWeiXinUser(token);
		String url = MessageFormat.format("https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token={0}",token);
		try 
		{
			for (Object touser : list) {
				log.info("sendMessage start. token:" + token + ",msg:" + msg);
				try 
				{
					net.sf.json.JSONObject obj = new net.sf.json.JSONObject();
					net.sf.json.JSONObject text = new net.sf.json.JSONObject();
					obj.put("touser", touser);
					text.put("content", msg);
					obj.put("text", text);
					obj.put("msgtype", "text");
					net.sf.json.JSONObject result = WxstoreUtils.httpRequest(url,"POST", obj.toString());
					System.out.println("微信返回的结果：" + result.toString());
					response = (SendMessageResponse) net.sf.json.JSONObject.toBean(result, SendMessageResponse.class);
				} catch (Exception e) {
					throw new WexinReqException(e);
				}finally {
					
				}
			}
			return response;
		} catch (Exception e) {
			log.error("get user info exception", e);
			return null;
		}
		
	}
    
    /**
     * @deprecated
     * @return
     */
    public static String getIpUrl(String accessToken){
    	String url = "https://api.weixin.qq.com/cgi-bin/getcallbackip?access_token="+accessToken;
    	return url;
    }
    
	public static byte[] getAccessTokenByAppid(String url) throws NoSuchAlgorithmException,
			KeyManagementException, IOException {
		SSLContext sc = SSLContext.getInstance("SSL");
		sc.init(null, new TrustManager[] { new TrustAnyTrustManager() },new java.security.SecureRandom());

		URL console = new URL(url);
		HttpsURLConnection conn = (HttpsURLConnection) console.openConnection();
		conn.setSSLSocketFactory(sc.getSocketFactory());
		conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
		conn.setDoOutput(true);
		conn.connect();
		DataOutputStream out = new DataOutputStream(conn.getOutputStream());
		out.flush();
		out.close();
		InputStream is = conn.getInputStream();
		if (is != null) {
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = is.read(buffer)) != -1) 
			{
				outStream.write(buffer, 0, len);
			}
			is.close();
			return outStream.toByteArray();
		}
		return null;
	}
	
	public static List<String> getAllWeiXinUser(String accessToken) {
		List<String> openIds = new ArrayList<String>();
		String action = "https://api.weixin.qq.com/cgi-bin/user/get?access_token="+ accessToken;
		try 
		{
			URL urlGet = new URL(action);
			HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
			http.setRequestMethod("GET"); // 必须是get方式请求
			http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
			http.setDoOutput(true);
			http.setDoInput(true);
			System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
			System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
			http.connect();
			InputStream is = http.getInputStream();
			int size = is.available();
			byte[] jsonBytes = new byte[size];
			is.read(jsonBytes);
			String result = new String(jsonBytes, "UTF-8");
			JSONObject jsonObj = new JSONObject(result);
			JSONObject json1 = new JSONObject(jsonObj.get("data").toString());
			JSONArray json2 = new JSONArray(json1.get("openid").toString());
			for (int i = 0; i < json2.length(); i++) 
			{
				openIds.add(i, json2.getString(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return openIds;
	}
	
	/**
	 * 获取所有的用户组
	 * @param accessToken
	 * @return
	 */
	public List<Group> getAllGroups(String accessToken) {
		List<Group> groups = new ArrayList<Group>();
		String action = "https://api.weixin.qq.com/cgi-bin/groups/get?access_token="+ accessToken;
		try 
		{
			URL urlGet = new URL(action);
			HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
			http.setRequestMethod("GET"); // 必须是get方式请求
			http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
			http.setDoOutput(true);
			http.setDoInput(true);
			System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
			System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
			http.connect();
			InputStream is = http.getInputStream();
			int size = is.available();
			byte[] jsonBytes = new byte[size];
			is.read(jsonBytes);
			String result = new String(jsonBytes, "UTF-8");
			JSONObject jsonObj = new JSONObject(result);
			JSONArray json2 = new JSONArray(jsonObj.get("groups").toString());
			for (int i = 0; i < json2.length(); i++) {
				System.out.println(json2.get(i).toString());
				JSONObject json = new JSONObject(json2.get(i).toString());
				System.out.println(json.get("id"));
				Group p = new Group();
				p.setCount(Integer.valueOf(json.get("count").toString()));
				p.setId(Integer.valueOf(json.get("id").toString()));
				p.setName(json.get("name").toString());
				groups.add(p);
			}
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return groups;
	}
	
	public static void test(net.sf.json.JSONArray ja){
		String ss = new String("尊敬的客户，你有来至；"+"\n");
		StringBuilder sub = new StringBuilder();
		String SHOP_NAME = null;
		for (Object obj : ja) {
			net.sf.json.JSONObject j = net.sf.json.JSONObject.fromObject(obj);
			String LOGISTICS = j.get("LOGISTICS").toString();
			String PROVIDER_NAME = j.get("PROVIDER_NAME").toString();
			String OPERA_TIME = j.get("OPERA_TIME").toString();
			SHOP_NAME  =  j.get("SHOP_NAME").toString();
			String ff = "   "+PROVIDER_NAME+",的快递:"+LOGISTICS+" 收件时间："+OPERA_TIME+"\n";
			sub.append(ff);
		}
		String mmms = ss + sub.toString()+"请尽快到  "+SHOP_NAME+" 幸福快递网点领取!";
		System.out.println(mmms);
	}
	
}

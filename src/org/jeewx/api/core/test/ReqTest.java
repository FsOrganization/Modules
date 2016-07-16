package org.jeewx.api.core.test;

import java.io.IOException;
import java.io.InputStream;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jeewx.api.core.exception.WexinReqException;
import org.jeewx.api.core.req.WeiXinReqService;
import org.jeewx.api.core.req.model.AccessToken;
import org.jeewx.api.core.req.model.DownloadMedia;
import org.jeewx.api.core.req.model.ServiceIP;
import org.jeewx.api.core.req.model.UploadMedia;
import org.jeewx.api.core.req.model.kfaccount.KfaccountList;
import org.junit.Test;

public class ReqTest {

	/**
	 * 测试获取token
	 * @return
	 * @throws WexinReqException
	 */
	@Test
	public String getToken() throws WexinReqException{
		String appId = "wxc016c959d3870b52";
		String appSecret = "c7843e15394196bb9840b03eb1a03cfc";
		AccessToken token = new AccessToken();
		token.setAppid(appId);
		token.setSecret(appSecret);
		String strtoken = WeiXinReqService.getInstance().doWeinxinReq(token);
		System.out.println(strtoken);
		return strtoken;
	}
	
	public static void main(String[] args) throws WexinReqException, Exception, IOException {
		InputStream is = WeiXinReqService.class.getResourceAsStream("weixin-reqcongfig.xml");
		System.out.println(is);
		SAXBuilder xmlBuilder = new SAXBuilder();
		Document doc = xmlBuilder.build(is);
		Element objRoot = doc.getRootElement();
		
	}
	
	
	/**
	 * 测试获取服务器ip
	 * @return
	 * @throws WexinReqException
	 */
	public String getServieIp(String token) throws WexinReqException{
		ServiceIP ip = new ServiceIP();
		ip.setAccess_token(token); 
		String strip = WeiXinReqService.getInstance().doWeinxinReq(ip);
		System.out.println(strip);
		return strip;
	}
	
	/**
	 * 测试上传文件
	 * @return
	 * @throws WexinReqException
	 */
	public String getUploadMedia(String token) throws WexinReqException{
		UploadMedia media = new UploadMedia();
		media.setAccess_token(token);
		media.setType("image");
		media.setFilePathName("C:/Users/sfli.sir/Desktop/temp/0020380102.jpg");
		String tokenFFF = WeiXinReqService.getInstance().doWeinxinReq(media);
		System.out.println(tokenFFF);
		return tokenFFF;
	}
	
	
	/**
	 * 测试上传文件
	 * @return
	 * @throws WexinReqException
	 */
	public String getDownMedia(String token) throws WexinReqException{
		DownloadMedia media = new DownloadMedia();
		media.setAccess_token(token);
		media.setMedia_id("fV1ivFheJ-YsMIV8luw04Anu_kw1tfUmYY6ALV7gZi17Uo1n3RSlCiTgIlQRibLF");
		media.setFilePath("G:/temp");
		String tokenFFF = WeiXinReqService.getInstance().doWeinxinReq(media);
		return tokenFFF;
	}
	
	
	/**
	 * 测试获取token
	 * @return
	 * @throws WexinReqException
	 */
	public String getKfaccountList(String access_token) throws WexinReqException{
		KfaccountList kf = new KfaccountList();
		kf.setAccess_token(access_token);
		String strtoken = WeiXinReqService.getInstance().doWeinxinReq(kf);
		System.out.println(strtoken);
		return strtoken;
	}
	
}

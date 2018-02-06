package org.WechatToken;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.WechatToken.com.qq.weixin.mp.aes.WXBizMsgCrypt;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;


public class Program {

	public static void main(String[] args) throws Exception {
		// 第三方回复公众平台
		// 需要加密的明文
		String encodingAesKey = "3LCyVM2RHKlslGa4ADv1KJilR640Ajl3eCqriXt8hgy";
		String token = "CkjycTk3Zd5g3g6ZqFT5gdg3BXE42f5g";
		String timestamp = "1409304348";
		String nonce = "me2rb36o7n53yh32dg9bn";
		String appId = "wxc016c959d3870b52";
		String replyMsg = 
				"<xml>"
				+ "<ToUserName><![CDATA[oia2TjjewbmiOUlr6X-1crbLOvLw]]></ToUserName>"
				+ "<FromUserName><![CDATA[gh_7f083739789a]]></FromUserName>"
				+ "<CreateTime>1407743423</CreateTime>"
				+ "<MsgType><![CDATA[text]]></MsgType>"
				+ "<Content><![CDATA[this is a test message]]></Content>"
				+ "<MsgId>6060349595123187712</MsgId>"
				+ "</xml>";

		WXBizMsgCrypt pc = new WXBizMsgCrypt(token, encodingAesKey, appId);
		String mingwen = pc.encryptMsg(replyMsg, timestamp, nonce);
		System.out.println("加密后: " + mingwen);

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		StringReader sr = new StringReader(mingwen);
		InputSource is = new InputSource(sr);
		Document document = db.parse(is);

		Element root = document.getDocumentElement();
		NodeList nodelist1 = root.getElementsByTagName("Encrypt");
		NodeList nodelist2 = root.getElementsByTagName("MsgSignature");

		String encrypt = nodelist1.item(0).getTextContent();
		String msgSignature = nodelist2.item(0).getTextContent();

		String format = 
				"<xml>"
				+ "<ToUserName><![CDATA[toUser]]></ToUserName>"
				+ "<Encrypt><![CDATA[%1$s]]></Encrypt>"
				+ "</xml>";
		String fromXML = String.format(format, encrypt);
		//
		// 公众平台发送消息给第三方，第三方处理

		// 第三方收到公众号平台发送的消息
		String result2 = pc.decryptMsg(msgSignature, timestamp, nonce, fromXML);
		System.out.println("解密后明文: " + result2);
		
		//pc.verifyUrl(null, null, null, null);
	}
}
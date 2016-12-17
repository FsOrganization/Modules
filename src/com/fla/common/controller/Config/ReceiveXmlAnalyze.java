package com.fla.common.controller.Config;

import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

import com.fla.payment.weixinPay.util.pay.WxPayResult;

public class ReceiveXmlAnalyze {

	public ReceiveXmlAnalyze() {
		
	}
	
	public static WxPayResult parseXml(String notityXmlResult) throws Exception {
		Map<String,Object> m = parseXmlToList(notityXmlResult);
        WxPayResult wpr = new WxPayResult();
        wpr.setAppid(m.get("appid").toString());
        wpr.setBankType(m.get("bank_type").toString());
        wpr.setCashFee(m.get("cash_fee").toString());
        wpr.setFeeType(m.get("fee_type").toString());
        wpr.setIsSubscribe(m.get("is_subscribe").toString());
        wpr.setMchId(m.get("mch_id").toString());
        wpr.setNonceStr(m.get("nonce_str").toString());
        wpr.setOpenid(m.get("openid").toString());
        wpr.setOutTradeNo(m.get("out_trade_no").toString());
        wpr.setResultCode(m.get("result_code").toString());
        wpr.setReturnCode(m.get("return_code").toString());
        wpr.setSign(m.get("sign").toString());
        wpr.setTimeEnd(m.get("time_end").toString());
        wpr.setTotalFee(m.get("total_fee").toString());
        wpr.setTradeType(m.get("trade_type").toString());
        wpr.setTransactionId(m.get("transaction_id").toString());
        return wpr;
	}
	
	 private static Map<String,Object> parseXmlToList(String xml) throws Exception {
	        Map<String,Object> retMap = new HashMap<String,Object>();
	        try 
	        {
	            StringReader read = new StringReader(xml);
	            // 创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
	            InputSource source = new InputSource(read);
	            // 创建一个新的SAXBuilder
	            SAXBuilder sb = new SAXBuilder();
	            // 通过输入源构造一个Document
	            Document doc = (Document) sb.build(source);
	            Element root = doc.getRootElement();// 指向根节点
	            List<Element> es = root.getChildren();
	            if (es != null && es.size() != 0) {
	                for (Element element : es) {
	                    retMap.put(element.getName(), element.getValue());
	                }
	            }
	        } catch (Exception e) {
	            throw e;
	        }
	        return retMap;
	    }

}

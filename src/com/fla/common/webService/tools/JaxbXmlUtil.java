package com.fla.common.webService.tools;

import it.sauronsoftware.base64.Base64;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class JaxbXmlUtil {

	/**
	 * xmlStr to java bean by string
	 * @param xmlStr
	 * @param c
	 * @return
	 */
	public static <T>  T xmlToBean(String xmlStr, Class<T> c) {
		//xmlStr = base64Decode(xmlStr, "UTF-8");
		try
		{
			JAXBContext context = JAXBContext.newInstance(c);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			StringReader sr = new StringReader(xmlStr);
			XMLInputFactory xmlFactory  = XMLInputFactory.newInstance();
	        XMLStreamReader reader = xmlFactory.createXMLStreamReader(sr);
			T t = (T) unmarshaller.unmarshal(reader);
			return t;
		} catch (JAXBException e) {
			e.printStackTrace();
			return null;
		} catch (XMLStreamException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static <T>  T xmlToBeanNotIncludedRoot(String xmlStr, Class<T> c, String rootName) {
		//xmlStr = base64Decode(xmlStr, "UTF-8");
		try
		{
			JAXBContext context = JAXBContext.newInstance(c);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			StringReader sr = new StringReader(xmlStr.replaceFirst("<"+rootName+">", "").replace("</"+rootName+">", ""));
			XMLInputFactory xmlFactory  = XMLInputFactory.newInstance();
	        XMLStreamReader reader = xmlFactory.createXMLStreamReader(sr);
			T t = (T) unmarshaller.unmarshal(reader);
			return t;
		} catch (JAXBException e) {
			e.printStackTrace();
			return null;
		} catch (XMLStreamException e) {
			e.printStackTrace();
			return null;
		}
	}


	/**
	 * xmlFile to java bean by File
	 * @param xmlStr
	 * @param c
	 * @return
	 */
	public static <T>  T xmlToBean(File xmlFile,Class<T> c) {
		return JAXB.unmarshal(xmlFile, c);
	}

	/**
	 * is to java bean by InputStream
	 * @param is
	 * @param c
	 * @return
	 */
	public static <T>  T xmlToBean(InputStream is, Class<T> c) {
		try
		{
			JAXBContext context = JAXBContext.newInstance(c);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			XMLInputFactory xmlFactory  = XMLInputFactory.newInstance();
	        XMLStreamReader reader = xmlFactory.createXMLStreamReader(is);
			T t = (T) unmarshaller.unmarshal(reader);
			return t;
		} catch (JAXBException e) {
			e.printStackTrace();
			return null;
		} catch (XMLStreamException e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * java bean to xmlStr
	 * @param object
	 * @param encoding
	 * @return
	 */
	public static String beanToXml(Object object, String encoding) {
		try
		{
			StringWriter writer = new StringWriter();
			JAXBContext context = JAXBContext.newInstance(object.getClass());
			Marshaller marshal = context.createMarshaller();
			marshal.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshal.setProperty(Marshaller.JAXB_FRAGMENT, false);
			marshal.setProperty(Marshaller.JAXB_ENCODING, encoding);
			marshal.marshal(object, writer);
			String xmlContext = new String(writer.getBuffer());
			return xmlContext;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}





	/**
	 * default encoding utf-8
	 * @param object
	 * @return
	 */
	public static String beanToXml(Object object) {
		String encoding = "UTF-8";
		try
		{
			StringWriter writer = new StringWriter();
			JAXBContext context = JAXBContext.newInstance(object.getClass());
			Marshaller marshal = context.createMarshaller();
			marshal.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshal.setProperty(Marshaller.JAXB_FRAGMENT, false);
			marshal.setProperty(Marshaller.JAXB_ENCODING, encoding);
			marshal.marshal(object, writer);
			String xmlContext = new String(writer.getBuffer());
			return xmlContext;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String beanToXmlRoot(Object object,String rootName) {
		String encoding = "UTF-8";
		try
		{
			StringWriter writer = new StringWriter();
			JAXBContext context = JAXBContext.newInstance(object.getClass());
			Marshaller marshal = context.createMarshaller();
			marshal.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshal.setProperty(Marshaller.JAXB_ENCODING, encoding);
			marshal.setProperty(Marshaller.JAXB_FRAGMENT, true);// �Ƿ�ʡ��xmͷ������Ϣ
			marshal.marshal(object, writer);
			String xmlContext = new String(writer.getBuffer());
			String xml = rootJAXBElementHandle(xmlContext,rootName,encoding);
//			System.out.println("base64������ǰxml��\n"+xml);
			/*xml = base64Encode(xml, encoding);
			System.out.println("base64�����ܺ�xml��\n"+xml);*/
			return xml;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public static String beanToXmlRoot(Object object, String rootName, String encoding) {
		try
		{
			StringWriter writer = new StringWriter();
			JAXBContext context = JAXBContext.newInstance(object.getClass());
			Marshaller marshal = context.createMarshaller();
			marshal.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshal.setProperty(Marshaller.JAXB_ENCODING, encoding);
			marshal.setProperty(Marshaller.JAXB_FRAGMENT, true);// �Ƿ�ʡ��xmͷ������Ϣ
			marshal.marshal(object, writer);
			String xmlContext = new String(writer.getBuffer());
			String xml = rootJAXBElementHandle(xmlContext,rootName,encoding);
//			System.out.println("base64������ǰxml��\n"+xml);
			/*xml = base64Encode(xml, encoding);
			System.out.println("base64�����ܺ�xml��\n"+xml);*/
			return xml;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * encode by string
	 * @param str
	 * @param encoding
	 * @return
	 */
	public static String  base64Encode(String str,String encoding) {
		String encoded = Base64.encode(str, encoding);
		return encoded;
	}

	/**
	 * encode by InputStream
	 * @param is
	 * @param out
	 * @param encoding
	 */
	public static void base64Encode(InputStream is,OutputStream out){
        try
        {
			Base64.encode(is, out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * decode by string
	 * @param encoded
	 * @param encoding
	 * @return
	 */
	public static String base64Decode(String encoded,String encoding) {
	    String decoded = Base64.decode(encoded,encoding);
	    return decoded;
	}

	/**
	 * decode by InputStream
	 * @param encoded
	 * @param encoding
	 * @return
	 */
	public static void base64Decode(InputStream is,OutputStream out) {
		try
        {
			Base64.decode(is, out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static String rootJAXBElementHandle(String context, String rootName, String encoding){
		String xmlDef = "<?xml version=\"1.0\" encoding=\""+encoding+"\"?>";
		String rootStartTag = "<"+rootName+">";
		String rootEndTag = "</"+rootName+">";
		StringBuilder sb = new StringBuilder();
		sb.append(xmlDef);
		sb.append("\n");
		sb.append(rootStartTag);
		sb.append("\n");
		sb.append(context);
		sb.append("\n");
		sb.append(rootEndTag);
		return sb.toString();
	}

	public static Map<String, Object> introspect(Object obj) throws Exception {
	    Map<String, Object> result = new HashMap<String, Object>();
	    BeanInfo info = Introspector.getBeanInfo(obj.getClass());
	    for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
	        Method reader = pd.getReadMethod();
	        if (reader != null)
	            result.put(pd.getName(), reader.invoke(obj));
	    }
	    return result;
	}

}
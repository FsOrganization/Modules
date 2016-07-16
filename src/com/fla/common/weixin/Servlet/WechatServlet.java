package com.fla.common.weixin.Servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.WechatToken.com.qq.weixin.mp.aes.SHATools;
import org.sword.wechat4j.common.Config;

import com.fla.common.weixin.util.WechatProcess;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

public class WechatServlet extends HttpServlet {

	public WechatServlet() {
		super();
	}
	
	private static final long serialVersionUID = 7224300062639991032L;

	/**
	 * The doGet method of the servlet. <br>
	 * This method is called when a form has its tag value method equals to get.
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (checkToken(request, response)) {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			/** 读取接收到的xml消息 */
			StringBuffer sb = new StringBuffer();
			InputStream is = request.getInputStream();
			InputStreamReader isr = new InputStreamReader(is, "UTF-8");
			BufferedReader br = new BufferedReader(isr);
			String s = "";
			while ((s = br.readLine()) != null) 
			{
				sb.append(s);
			}
			String xml = sb.toString(); // 次即为接收到微信端发送过来的xml数据
			String result = "";
			/** 判断是否是微信接入激活验证，只有首次接入验证时才会收到echostr参数，此时需要把它直接返回 */
			String echostr = request.getParameter("echostr");
			if (echostr != null && echostr.length() > 1) {
				result = echostr;
			} else {
				// 正常的微信处理流程
				result = new WechatProcess().processWechatMag(xml).getFromUserName();
			}
			System.out.println("微信客户端到服务器的请求：FromUserName(openId):"+result);
			try 
			{
//				ByteOutputStream b = new ByteOutputStream();
				ExpressWechat  ew = new ExpressWechat(request);
				String res = ew.execute();
				response.getOutputStream().write((res+result).getBytes());
				OutputStream os = response.getOutputStream();
				os.write(result.getBytes("UTF-8"));
				os.flush();
				os.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * The doPost method of the servlet. <br>
	 * This method is called when a form has its tag value method equals to post.
	 * @param request the request send by the client to the server
	 * @param response  the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
		ExpressWechat  ew = new ExpressWechat(request);
		String res = ew.execute();
		response.getOutputStream().write(res.getBytes());
	}
	
	private static boolean checkToken(HttpServletRequest request,HttpServletResponse response) {
		String token = Config.instance().getToken();
		// 微信加密签名
		String signature = request.getParameter("signature");
		// 随机字符串
		String echostr = request.getParameter("echostr");
		// 时间戳
		String timestamp = request.getParameter("timestamp");
		// 随机数
		String nonce = request.getParameter("nonce");
		String[] str = { token, timestamp, nonce };
		Arrays.sort(str); // 字典序排序
		String bigStr = str[0] + str[1] + str[2];
		// SHA1加密
		String digest = new SHATools().getDigestOfString(bigStr.getBytes()).toLowerCase();
		// 确认请求来至微信
		if (digest.equals(signature)) {
			try 
			{
				System.out.println("checkToken:"+echostr);
				response.getWriter().print(echostr);
				return true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		} 
		return false;
		
	}

}
package org.WechatToken;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.WechatToken.com.qq.weixin.mp.aes.SHATools;

@SuppressWarnings("serial")
public class WechatCallbackApi extends HttpServlet {
    // 自定义 token
    private String TOKEN = "CkjycTk3Zd6ZqFT5g3BXE42f";

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 微信加密签名
        String signature = request.getParameter("signature");
        // 随机字符串
        String echostr = request.getParameter("echostr");
        // 时间戳
        String timestamp = request.getParameter("timestamp");
        // 随机数
        String nonce = request.getParameter("nonce");
        String[] str = { TOKEN, timestamp, nonce };
        Arrays.sort(str); // 字典序排序
        String bigStr = str[0] + str[1] + str[2];
        // SHA1加密
        String digest = new SHATools().getDigestOfString(bigStr.getBytes()).toLowerCase();
        // 确认请求来至微信
        if (digest.equals(signature)) {
            	response.getWriter().print(echostr);
        }
    }
}
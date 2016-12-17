package com.fla.common.security.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import com.fla.common.entity.SystemUser;

public class SessionTimeoutInterceptor implements HandlerInterceptor {
//	
//	@Autowired
//	public SystemUser systemUser;

	@Override
	public void afterCompletion(HttpServletRequest request,HttpServletResponse response, Object obj, Exception e)throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response,Object obj, ModelAndView mv) throws Exception {
	        //response.sendRedirect(request.getContextPath()+"/pages/system/welcome.light");  
	}

	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		SystemUser s = (SystemUser) request.getSession().getAttribute("systemUser");
		HttpSession session = request.getSession();
		if (s == null || s.getServiceShopCode() == null) {
			if (request.getHeader("x-requested-with") != null && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {
				response.setHeader("sessionstatus", "timeout");
				response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY); 
				response.setCharacterEncoding("utf-8");
				response.setContentType("text/html; charset=utf-8");
//				PrintWriter printWriter = response.getWriter();
//				JSONObject j = new JSONObject();
				session.setAttribute("redirect", request.getContextPath()+"/pages/login.jsp");
//				j.put("redirect", request.getContextPath()+"/pages/login.jsp");
//				printWriter.write(j.toString());
//				printWriter.flush();
//				printWriter.close();
				return false;
			}
		} else {
			session.setAttribute("redirect", request.getContextPath()+"/pages/login.jsp");
		}
		return true;
		
	}

}

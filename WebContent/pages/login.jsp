<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/base/base.jsp" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang='zh'>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <title>源信幸福快递-登陆</title>
<link rel="StyleSheet" href="<%=contextPath%>/pages/css/login.css" type="text/css" media="screen">
<style type="text/css">
.msg{
	color:red;
}
</style>
<meta name="robots" content="noindex,follow">
<script src="<%=contextPath%>/pages/js/login.js" type="text/javascript"></script>
<script type="text/javascript">
var msgType = '${msgType}';
var msg = '${msg}';
</script>
</head>
<body style="height: 90%;">
  <div class="container">
    <div class="login">
      <h1>源信幸福快递</h1>
      <form id="loginForm" method="post" action="<%=contextPath%>/pages/system/login.light">
        <p><input type="text" id="name" name="name" value="${name}" placeholder="用户名"> </p>
        <p><input type="password" id="password" name="password" value="" placeholder="密码"> </p>
        <div id="codeDiv" style="margin: 20px 70px;display: none">
        	<p>
        		<input type="text" id="code" name="code" value="" placeholder="验证码" style="margin: 8px -65px;">
        		<iputt type="hidden" id="hiddenCode" />
        	</p>
            <img id="VerificationCode" src=""></img>
            
        </div>
        <div id="useruname-error" style="display: none"><span id="msg" class="msg">${msg}</span></div>
        <p class="remember_me">
          <label>
            <input type="checkbox" name="remember_me" id="remember_me" ${remember_me}>
				记住用户名
          </label>
        </p>
        <p class="submit">
			<input type="button" id="loginFormCommit" name="loginFormCommit" value="登 陆">
		</p>
      </form>
    </div>

    <div class="login-help">
      <p>我忘记了密码? <a href="#">申请重置密码</a>.</p>
    </div>
  </div>
</body></html>
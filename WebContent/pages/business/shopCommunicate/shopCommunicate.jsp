<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/base/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang='zh'>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/jQueryChatBox/css/style.css">
<script src="<%=contextPath%>/pages/business/shopCommunicate/js/shopCommunicate.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=contextPath%>/jQueryChatBox/js/phpFunctions.js"></script>
<script type="text/javascript" src="<%=contextPath%>/jQueryChatBox/js/chatScripts.js"></script>
<style type="text/css">
#connect-container {
	float: left;
	width: 400px
}

#connect-container div {
	padding: 5px;
}

#console-container {
	float: left;
	margin-left: 15px;
	width: 400px;
}

#console {
	border: 1px solid #CCCCCC;
	border-right-color: #999999;
	border-bottom-color: #999999;
	height: 170px;
	overflow-y: scroll;
	padding: 5px;
	width: 100%;
}

#console p {
	padding: 0;
	margin: 0;
}
</style>
<script type="text/javascript">
</script>
</head>
<body style="background: #f2f2f2;">
	<div id="header" style="width: inherit;">
	<label>互动平台</label>
	</div>
	<div id="console"></div>
	<div id="consoles" style="width: inherit;">
		<textarea id="message" autofocus placeholder="Enter your message here..." style="width: inherit;height: 6%;"></textarea>
		<button id="echo" onclick="echo();" disabled="disabled">发送消息</button>
		<br/>
		<input type="checkbox" checked id="enter"/>
		<label for="enter">Send on enter</label>
	</div>
		<input id="radio1" type="radio" name="group1" onclick="updateUrl('<%=contextPath%>/webSocket');">
           <label for="radio1">W3C WebSocket</label>
       <br>
       <input id="radio2" type="radio" name="group1" onclick="updateUrl('/spring-websocket-test/sockjs/echo');">
           <label for="radio2">SockJS</label>
       <div id="sockJsTransportSelect" style="visibility:hidden;">
           <span>SockJS transport:</span>
           <select onchange="updateTransport(this.value)">
             <option value="all">all</option>
             <option value="websocket">websocket</option>
             <option value="xhr-polling">xhr-polling</option>
             <option value="jsonp-polling">jsonp-polling</option>
             <option value="xhr-streaming">xhr-streaming</option>
             <option value="iframe-eventsource">iframe-eventsource</option>
             <option value="iframe-htmlfile">iframe-htmlfile</option>
           </select>
       </div>
       <div>
           <button id="connect" onclick="connect();">开启对话</button>
           <button id="disconnect" disabled="disabled" onclick="disconnect();">关闭对话</button>
       </div>
</body>
</html>
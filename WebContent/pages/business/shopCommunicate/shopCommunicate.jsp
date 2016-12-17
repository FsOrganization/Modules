<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/base/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang='zh'>
<head>
<meta http-equiv="Content-Type" name="viewport" content="text/html; charset=UTF-8;width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=0"/>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/jQueryChatBox/css/style.css">
<link rel="stylesheet" href="<%=contextPath%>/common/jquery.mobile/jquery.mobile-1.2.0.min.css" />
<script src="<%=contextPath%>/pages/business/shopCommunicate/js/shopCommunicate.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=contextPath%>/jQueryChatBox/js/phpFunctions.js"></script>
<script type="text/javascript" src="<%=contextPath%>/jQueryChatBox/js/chatScripts.js"></script>
<script src="<%=contextPath%>/common/jquery.mobile/jquery.mobile-1.2.0.min.js"></script>
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
body {
		margin:0px;
		width:100%;
		height:100%;
		overflow:hidden;
        /* prevent text selection on ui */
        user-select: none;
        -webkit-user-select: none;
        -moz-user-select: none;
        -ms-user-select: none;
        /* prevent scrolling in windows phone */
        -ms-touch-action: none;
	  }
	  #content {
          overflow:hidden;
		  background-color:#ddd;
	  }
	  #canvas{
		cursor:crosshair ;
        background-color:#fff;
	  }
	  .palette-case {
		width:260px;
		margin:auto;
		text-align:center;
	  }
	  .palette-box {
		float:left;
		padding:2px 6px 2px 6px;
	  }
	  .palette {
		border:2px solid #777;
		height:36px;
		width:36px;
	  }
	  .red{
		background-color:#c22;
	  }
	  .blue{
		background-color:#22c;
	  }
	  .green{
		background-color:#2c2;
	  }
	  .white{
		background-color:#fff;
	  }
	  .black{
		background-color:#000;
		border:2px dashed #fff;
	  }
</style>
<script type="text/javascript">
</script>
</head>
<body style="background: #f2f2f2;">
<div data-role="page" id="page1" style="height: 300px;">
    <div data-theme="a" data-role="header">
        <h3>Sketch Pad</h3>
		<a id="new" data-role="button" data-theme="b" class="ui-btn-left">New</a>
    </div>
    <div id="content"><p style="text-align:center">Loading Canvas...</p></div>
    <div data-theme="a" data-role="footer" data-position="fixed">
		<div class="palette-case">
			<div class="palette-box">
				<div class="palette white"></div>
			</div>	
			<div class="palette-box">
				<div class="palette red"></div>
			</div>
			<div class="palette-box">
				<div class="palette blue"></div>
			</div>
			<div class="palette-box">
				<div class="palette green"></div>
			</div>
			<div class="palette-box">
				<div class="palette black"></div>
			</div>		
			<div style="clear:both"></div>
		</div>
    </div>
</div>
	<div id="console"></div>
	<div id="consoles" style="width: inherit;">
		<textarea id="message" autofocus placeholder="Enter your message here..." style="width: inherit;height: 6%;"></textarea>
		<button id="echo" onclick="echo();" disabled="disabled">发送消息</button>
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
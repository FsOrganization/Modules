<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/base/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang='zh'>
<head>
<title>Home :: 幸福快递</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<!-- <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" /> -->
<meta name="viewport" content="height=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=0">
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/mobile-style/css/style.css" />
<script type="text/javascript" src="<%=contextPath%>/pages/E-commerce/temp/jquery.qrcode.js"></script>
<script type="text/javascript" src="<%=contextPath%>/pages/E-commerce/temp/qrcode.js"></script>
<script type="text/javascript" src="<%=contextPath%>/pages/E-commerce/temp/canvasjs.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/pages/E-commerce/temp/jquery.canvasjs.min.js"></script>
<style type="text/css">
.options{
	float:right;
}
.options ul{
	margin:0;
	padding:0;
}
.options li{
	float:left;
	background:url('images/options.png') no-repeat;
	width:60px;
	height:60px;
	display:inline-block;
	cursor:pointer;
	border-left:1px solid #ccc;
	text-indent:-1000em;
}
.options li.active,
.active{
	background-color:#f2f2f2;
}
.options li:first-child{
	background-position: 22px 20px;
}
.options li:last-child{
	background-position: -17px 20px;
}
</style>
<script src="<%=contextPath%>/pages/business/test/js/weixinExtractionCode.js" type="text/javascript"></script>
</head>
<body id="queryBody">
<!-- start main -->
	<div class="main" id="mainDiv" style="text-align: center;">
		<div id="qrcode" style="margin: 2px 2px;"></div>
	</div>
<!-- start footer -->
	<div class="footer">
		<div class="copy">	
			<p class="w3-link">Copyright 光艺软件科技. 2013 All Rights Reserved.&nbsp; <a href="#"> 光艺软件科技</a></p>
		</div>
		<div class="clear"></div>
	</div>
</body>
</html>
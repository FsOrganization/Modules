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

/* latin */
@font-face {
  font-family: 'Roboto Condensed';
  font-style: normal;
  font-weight: 300;
  src: local('Roboto Condensed Light'), local('RobotoCondensed-Light'), url(<%=contextPath%>/pages/business/test/weixinExtraction-master/fonts/robotoCondensed-Light.woff2) format('woff2');
  unicode-range: U+0000-00FF, U+0131, U+0152-0153, U+02C6, U+02DA, U+02DC, U+2000-206F, U+2074, U+20AC, U+2212, U+2215, U+E0FF, U+EFFD, U+F000;
}

h1 {
    text-transform: uppercase;
    font-weight: 700;
    font-weight: 300;
    font-size: 22px;
    font-family: 'Roboto Condensed', sans-serif;
    color: #0bf397;
}


</style>
<script src="<%=contextPath%>/pages/business/test/weixinExtraction-master/js/extractionQRCode.js" type="text/javascript"></script>
</head>
<body id="queryBody">
<!-- start main -->
	<div class="main" id="mainDiv" style="text-align: center;">
<!-- 		<div id="qrcode" style="margin: 35px 2px;"></div> -->
		<img alt="" src="data:image/jpg;base64,${img}">
		<div id="txt" style="margin: 2px 2px;"><h1>请出示提取码:${extractionCode}</h1></div>
	</div>
<!-- start footer -->
	<div class="footer">
		<div class="copy">	
			<p class="w3-link">Copyright 光艺软件科技. 2013 All Rights Reserved.</p>
		</div>
		<div class="clear"></div>
	</div>
</body>
</html>
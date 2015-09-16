<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/base/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang='zh'>
<head>
<title>快递查询 |Home :: 幸福快递</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/mobile-style/css/style.css" />
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
<script src="<%=contextPath%>/pages/business/test/js/weixinQueryPage.js" type="text/javascript"></script>
</head>
<body id="queryBody">
<!-- start sub-header -->
<div class="header_sub_bg">
		<div class="hdr-nav">
			<div class="search-box">
		    	<form action="#">
		    		<input id="queryParams" name="queryParams" style="height: 80px;width: 79%;font-size: 2.1em;" placeholder="手机号码 、快件运单号">
		    		<input id="queryButton" name="queryButton" style="height: 80px;width: 19%;font-size: 2.1em;" value="查询" type="button" onclick="searchExpressInfo()">
		    	</form>
		    	<div class="clear"></div>
		    </div>
		</div>
		<div class="clear"></div>
</div>
<!-- start main -->
	<div class="main" id="mainDiv">	
<!-- 		<div class="content"> -->
<!-- 			<div class="list_img"> -->
<%-- 				<img src="<%=contextPath%>/mobile-style/images/pic1.jpg"/>	 --%>
<!-- 			</div>	 -->
<!-- 		    <h2 class="style"><a href="">天天快递 运单号:25458655419565</a></h2> -->
<!-- 			<h3 class="style">收件人:傅中  收件时间:2015-05-15</h3> -->
<!-- 		</div> -->
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
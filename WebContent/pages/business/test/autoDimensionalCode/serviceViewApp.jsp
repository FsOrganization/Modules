<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/base/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang='zh'>
<head>
<title>快递查询 |Home :: 幸福快递</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="height=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=0">
<%--     <link rel="stylesheet" type="text/css" href="<%=contextPath%>/mobile-style/css/style.css" /> --%>
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/pages/business/test/autoDimensionalCode/css/af.ui.css"/>
    <link rel="stylesheet" type="text/css" href="<%=contextPath%>/pages/business/test/autoDimensionalCode/css/icons.css" />
    <script type="text/javascript" charset="utf-8" src="<%=contextPath%>/pages/business/test/autoDimensionalCode/js/appframework.ui.min.js" ></script>
	
	<script type="text/javascript" src="<%=contextPath%>/pages/E-commerce/temp/jquery.qrcode.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/pages/E-commerce/temp/qrcode.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/pages/E-commerce/temp/canvasjs.min.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/pages/E-commerce/temp/jquery.canvasjs.min.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/pages/business/test/autoDimensionalCode/js/customerSelfService.js"></script>
	<script language="javascript" src="<%=contextPath%>/js/print/LodopFuncs.js"></script>
<style>
.grid-photo {
	margin: 3px -7px;
}

.grid-photo:after {
	content: '';
	display: block;
	clear: both;
}

.grid-photo li {
	position: relative;
	display: block;
	float: left;
	width: 10%;
	padding-bottom: 10%;
}

.grid-photo .grid-photo-box {
	position: absolute;
	left: 3px;
	right: 3px;
	top: 3px;
	bottom: 3px;
	background-color: rgba(128, 128, 128, 0.2);
}

.grid-photo img {
	width: 40%;
	height: 40%
}

@media only screen and (max-width : 1024px) {
	.grid-photo li {
		width: 12.5%;
		padding-bottom: 12.5%;
	}
}

@media only screen and (max-width : 768px) {
	.grid-photo li {
		width: 16.6%;
		padding-bottom: 16.6%;
	}
}

@media only screen and (max-width : 480px) {
	.grid-photo li {
		width: 25%;
		padding-bottom: 25%;
	}
}

@media only screen and (max-width : 320px) {
	.grid-photo li {
		width: 33.3%;
		padding-bottom: 33.3%;
	}
}

.options {
	float: right;
}

.options ul {
	margin: 0;
	padding: 0;
}

.options li {
	float: left;
	background: url('images/options.png') no-repeat;
	width: 60px;
	height: 60px;
	display: inline-block;
	cursor: pointer;
	border-left: 1px solid #ccc;
	text-indent: -1000em;
}

.options li.active, .active {
	background-color: #f2f2f2;
}

.options li:first-child {
	background-position: 22px 20px;
}

.options li:last-child {
	background-position: -17px 20px;
}
/* after checked */
[type="checkbox"]:not (:checked ), [type="checkbox"]:checked {
	position: absolute;
	left: -9999px;
}

[type="checkbox"]:not (:checked ) +label, [type="checkbox"]:checked+label
	{
	position: relative;
	padding-left: 25px;
	cursor: pointer;
}

/* checkbox aspect */
[type="checkbox"]:not (:checked ) +label:before, [type="checkbox"]:checked+label:before
	{
	content: '';
	position: absolute;
	left: 0;
	top: 2px;
	width: 22px;
	height: 22px;
	border: 1px solid #aaa;
	background: #f8f8f8;
	border-radius: 3px;
	box-shadow: inset 0 1px 3px rgba(0, 0, 0, .3)
}
/* checked mark aspect */
[type="checkbox"]:not (:checked ) +label:after, [type="checkbox"]:checked+label:after
	{
	content: '✔';
	position: absolute;
	top: 0px;
	left: 4px;
	font-size: 25px;
	color: #D7520C;
	transition: all 0.1s cubic-bezier(1, .18, .01, .39) 0s;
	margin: 2px 0px;
	background: #f2f5f6;
}
/* checked mark aspect changes */
[type="checkbox"]:not (:checked ) +label:after {
	opacity: 0;
	transform: scale(0);
}

[type="checkbox"]:checked+label:after {
	opacity: 1;
	transform: scale(1);
}
/* disabled checkbox */
[type="checkbox"]:disabled:not (:checked ) +label:before, [type="checkbox"]:disabled:checked+label:before
	{
	box-shadow: none;
	border-color: #bbb;
	background-color: #ddd;
}

[type="checkbox"]:disabled:checked+label:after {
	color: #999;
}

[type="checkbox"]:disabled+label {
	color: #aaa;
}
/* accessibility */
[type="checkbox"]:checked:focus+label:before, [type="checkbox"]:not (:checked
	):focus+label:before {
	border: 1px dotted blue;
}

/* hover style just for information */
label:hover:before {
	border: 1px solid #4778d9 !important;
}
#queryParams {
    display: block;
    border: none;
    border-bottom: 2px dotted rgba(0,0,0,0.2);
    padding: 1.2em 0.2em 0.4em;
    text-align: center;
    font-size: 1.2em;
    width: 100%;
    color: rgba(0,0,0,0.6);
    letter-spacing: 0.01em;
    box-sizing: border-box;
    background: transparent;
    font-family: 'Roboto Mono';
}
@font-face {
  font-family: 'Roboto Condensed';
  font-style: normal;
  font-weight: 300;
  src: local('Roboto Condensed Light'), local('RobotoCondensed-Light'), url(<%=contextPath%>/pages/business/test/weixinExtraction-master/fonts/robotoCondensed-Light.woff2) format('woff2');
  unicode-range: U+0000-00FF, U+0131, U+0152-0153, U+02C6, U+02DA, U+02DC, U+2000-206F, U+2074, U+20AC, U+2212, U+2215, U+E0FF, U+EFFD, U+F000;
}

#paytitle {
    text-transform: uppercase;
    font-weight: 700;
    font-weight: 300;
    font-size: 14px;
    font-family: 'Roboto Condensed', sans-serif;
    color: #00BCD4;
}
</style>
</head>
<body id="customerSelfServiceBody">
<!-- <applet code="com.fla.common.print.Prient.class" width="200" height="200"   codebase="."> </applet> -->
<div id="afui">
	<input type="hidden" id="lateFeeLimitUpper" value="${lateFeeLimitUpper}">
	<input type="hidden" id="orderCode">
	<input type="hidden" id="barCode">
    <div id="content" style="">
    <!--Grid View Page-->
        <div class="panel" title="幸福快递" id="gridview" selected="true">
            <header>
                <h1>幸福快递</h1>
                <a href="#" class="button icon refresh" style="float:right"></a>
            </header>
            <div style="margin: 5px;">
            	<form action="#">
		    		<input type="text" id="queryParams" name="queryParams" style="height: 80px;width: 100%;font-size: 3.1em;font-family: 'Roboto Condensed', sans-serif;" placeholder="手机号码 ">
<!-- 		    		<input id="queryButton" name="queryButton" style="height: 80px;width: 19%;font-size: 2.1em;" value="查询" type="button" onclick="searchExpressInfo()"> -->
		    	</form>
		    	<div class="main grid-photo" id="mainDiv" class="grid-photo">
				</div>
				
				<div id="more" class="button block">
					<input id="checkButton" name="checkButton" style="height: 80px;font-size: 2.1em;display: none;" value="全选" type="button" onclick="checkAllSelected()">
					<input id="queryButton" name="queryButton" style="height: 80px;width: 100%;font-size: 2.1em;" value="打印取件条码" type="button" onclick="handleExpress()">
				</div>
			</div>
         </div>
          <div id="imgDetail" title="扫码支付" style="">
          	<table style="border-collapse:separate; border-spacing:0px 10px;">
          		<tr>
          			<td>
          				<div id="qrcode" style="position: relative;margin: 20px; float:left;"></div>
          			</td>
          			<td>
          				<img alt="" style="margin: -45px;" src="<%=contextPath%>/pages/business/test/autoDimensionalCode/img/wxpay.jpg">
          			</td>
          		</tr>
          		<tr>
          			<td colspan="2">
          				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span id="paytitle" style="font-weight: bold;font-size: 14px;"></<span>
          			</td>
          		</tr>
          		<tr>
          			<td colspan="2">
          				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span id="paymentType" style="color: black;font-style: italic;font-size: 14px;text-decoration:underline;"></span>
          			</td>
          		</tr>
          	</table>
		   </div>
<!-- 		    <div id="dlg-buttons"> -->
<!-- 		        <a id="submitBtn" class="easyui-linkbutton">打印条码</a> -->
<!-- 		        <a id="close" class="easyui-linkbutton" onclick="javascript:$('#imgDetail').dialog('close')">Close</a> -->
<!-- 		    </div> -->
        </div>
  
    </div>
</div>
</body>
</html>    
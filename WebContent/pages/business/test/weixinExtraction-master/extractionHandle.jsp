<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/base/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang='zh'>
<head>
    <title>二维码取件</title>
    <meta charset="utf-8"/>
        <meta name="viewport" content="user-scalable=no, width=device-width" />
        <meta name="apple-mobile-web-app-capable" content="yes" />
        <meta name="apple-mobile-web-app-status-bar-style" content="black" />
<%--         <link rel="stylesheet" type="text/css" href="<%=contextPath%>/mobile-style/css/scroll.css"/> --%>
        <link rel="stylesheet" type="text/css" href="<%=contextPath%>/mobile-style/css/style.css" />
    <style type="text/css">
/* latin */
@font-face {
	font-family: 'Roboto Condensed';
	font-style: normal;
	font-weight: 300;
	src: local('Roboto Condensed Light'), local('RobotoCondensed-Light'),
		url(<%=contextPath%>/pages/business/test/weixinExtraction-master/fonts/robotoCondensed-Light.woff2)
		format('woff2');
	unicode-range: U+0000-00FF, U+0131, U+0152-0153, U+02C6, U+02DA, U+02DC,
		U+2000-206F, U+2074, U+20AC, U+2212, U+2215, U+E0FF, U+EFFD, U+F000;
}

h1 {
	text-transform: uppercase;
	font-weight: 700;
	font-weight: 300;
	font-size: 22px;
	font-family: 'Roboto Condensed', sans-serif;
	color: #0bf397;
}

#scroller {
	overflow-y: auto;
	-webkit-overflow-scrolling: touch;
	/* -webkit-overflow-scrolling: auto; */
}

header, footer {
	position: fixed;
}
body, div, header, footer, ul, li {
    padding:0;
    margin:0;
    border:0;
}

body {
    -webkit-user-select:none;
    -webkit-text-size-adjust:none;
    -webkit-tap-highlight-color:rgba(0,0,0,0);
    font-size:20px;
    font-family:helvetica;
}

header, #header {
    position:absolute;
    z-index:2;
    top:0; left:0;
    width:100%;
    height:48px;
    line-height:48px;
    background-color:#666;
    background-image:-webkit-gradient(linear, 0 0, 0 100%, color-stop(0, #999), color-stop(0.02, #666), color-stop(1, #222));
    text-align:center;
    color:#fff;
    font-size: 16px;
    font-family: 'Roboto Condensed', sans-serif;
}

footer, #footer {
    position:absolute;
    z-index:2;
    bottom:0; left:0;
    width:100%;
    height:48px;
    line-height:48px;
    background-color:#666;
    background-image:-webkit-gradient(linear, 0 0, 0 100%, color-stop(0, #999), color-stop(0.02, #666), color-stop(1, #222));
    text-align:center;
    color:#fff;
    font-size: 14px;
    font-family: 'Roboto Condensed', sans-serif;
}

#wrapper {
    position:absolute;
    z-index:1;
    top:48px; bottom:48px; left:0;
    background:#aaa;
    width:100%;
}

#scroller {
    position:absolute;
    z-index:1;
    width:100%;
}

#scroller ul {
    list-style:none;
    width:100%;
    text-align:left;
}

#scroller li {
    padding:0 10px;
    height:60px;
    line-height:60px;
    border-bottom:1px solid #ccc;
    border-top:1px solid #fff;
    background:#fff;
}

#submitBtn:active{
	background-color: #E6E6E6;
	text-shadow: 0px 1px 1px rgba(255,255,255,0.4);
}

#submitBtn:hover{
	background-color: #E6E6E6;
	text-shadow: 0px 1px 1px rgba(255,255,255,0.4);
	cursor:hand;
}
</style>
<script type="text/javascript">
//* spe date */
var tempSpeDate = '2016-3-31';

/* 延期天数限制 */
var lateDayLimit = null;

var lateFeeLimitUpper = null;

var memberLateFeeLimitUpper = null;

var memberLateDayLimit = null;

$(document).ready(function() {
	$("#submitBtn").click(function() {
		$("#submitForm").submit();
	});
	
	var expDelayDays = getDelayDays($("#inOperaTime").val());
	
	/* 单间滞纳金上限 */
	lateFeeLimitUpper = $("#lateFeeLimitUpper").val();
	
	/* 快递存放天数上限 */
	lateDayLimit = $("#lateDayLimit").val();
	
	/* 单间滞纳金上限 */
	memberLateDayLimit = $("#memberLateDayLimit").val();
	
	/* 快递存放天数上限 */
	memberLateFeeLimitUpper = $("#memberLateFeeLimitUpper").val();
	
	var msg = "";
	if (expDelayDays <= 0 ) {
		msg = "未延期";
	} else {
		if (parseInt(expDelayDays) > parseInt(lateFeeLimitUpperHidden)) {
			msg = "延期"+expDelayDays+"天,"+"请付"+lateFeeLimitUpperHidden+"元 (封顶)";
		} else {
			msg = "延期"+expDelayDays+"天,"+"请付"+expDelayDays+"元";
		}
	}
	$('#inOperaTime').html(msg);
});


function getDelayDays(date){
	var expressDate = date.split(' ');
	var tempDate = expressDate[0];
	var delayDays = parseInt(getDays(tempDate,getCurrDateFormat()));
	var tag = checkSpeDate(tempSpeDate, expressDate[0]);
	delayDays = delayDays - lateDayLimit;
	if (tag) {
		delayDays = 0;
	}
	return delayDays;
}

function checkSpeDate(speDate,expressDate) {
	var d1 = getDateForStr(speDate);
	var d2 = getDateForStr(expressDate);
	if (expressDate == speDate) {
		return true;
	}
	var date3 = d1.getTime()-d2.getTime();  //时间差的毫秒数
	//计算出相差天数
	var days=Math.floor(date3/(24*3600*1000));
	if (days > 0) {
		return true;
	} else {
		return false;
	}
}

function getDays(strDateStart, strDateEnd) {
	var strSeparator = "-"; // 日期分隔符
	var oDate1;
	var oDate2;
	var days;
	oDate1 = strDateStart.split(strSeparator);
	oDate2 = strDateEnd.split(strSeparator);
	var strDateS = new Date(oDate1[0], oDate1[1] - 1, oDate1[2]);
	var strDateE = new Date(oDate2[0], oDate2[1] - 1, oDate2[2]);
	days = parseInt(Math.abs(strDateS-strDateE)/1000/60/60/24);/**把相差的毫秒数转换为天数*/
	return days;
}

</script>
</head>
    <body>
	    <form action="<%=contextPath%>/pages/system/weixin/outByExtractionCode.light" method="post" id="submitForm">
	        <header>幸福驿站</header>
	        <div id="wrapper">
	            <div id="scroller">
	                <ul>
	                    <li style="font-size: 18px;text-align: center;">客户姓名：${recipientName}</li>
	                    <li style="font-size: 18px;text-align: center;">货位号：${expressLocation}</li>
	                    <li id="inOperaTime" style="font-size: 18px;text-align: center;"></li>
	                     <input type="hidden" id="inOperaTime" name="inOperaTime" value="${inOperaTime}" />
	                     <input type="hidden" id="lateFeeLimitUpper" name="lateFeeLimitUpper" value="${lateFeeLimitUpper}" />
	                     <input type="hidden" id="memberLateDayLimit" name="memberLateDayLimit" value="${memberLateDayLimit}" />
	                     <input type="hidden" id="memberLateFeeLimitUpper" name="memberLateFeeLimitUpper" value="${memberLateFeeLimitUpper}" />
	                    <li id="submitBtn" style="font-size: 18px;text-align: center;">确认取件</li>
	                </ul>
	            </div>
	        </div>
	        <input type="hidden" id="id" name="id" value="${id}" />
	        <footer class="animated pulse">Copyright 光艺软件科技. 2013 All Rights Reserved.</footer>
	    </form>
    </body>
</html>
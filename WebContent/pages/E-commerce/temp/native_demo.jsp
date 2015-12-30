<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/base/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang='zh'>
 <head>
  <title>扫码支付前台</title>
  <meta name="content-type" content="text/html;charset=utf-8">
  <script type="text/javascript" src="<%=contextPath%>/pages/E-commerce/temp/jquery.qrcode.js"></script>
  <script type="text/javascript" src="<%=contextPath%>/pages/E-commerce/temp/qrcode.js"></script>
  <script type="text/javascript" src="<%=contextPath%>/pages/E-commerce/temp/canvasjs.min.js"></script>
  <script type="text/javascript" src="<%=contextPath%>/pages/E-commerce/temp/jquery.canvasjs.min.js"></script>
  <script type="text/javascript" src="<%=contextPath%>/pages/E-commerce/temp/js/native_demo.js"></script>
  <script>
	$.ajax({
		url : contextPath + "/pages/system/getWeixinPayCodeURL.light",
		type : "POST",
		dataType : 'json',
		data : {
		},
		success : function(data) {
//			if(navigator.appName.indexOf("Microsoft") != -1){
//				 $("#qrcode").qrcode({ 
//					    render: "table", //设置渲染方式canvas/table  
//					    width: 300, //宽度 
//					    height:300, //高度 
//					    correctLevel : QRErrorCorrectLevel.H,
//					    text: data
//					});
//			 } else {
//				 $("#qrcode").qrcode({ 
//					    render: "canvas", //设置渲染方式canvas/table  
//					    width: 300, //宽度 
//					    height:300, //高度 
//					    correctLevel : QRErrorCorrectLevel.H,
//					    text: data
//					});
//			 }
			 $("#qrcode").qrcode({ 
				    render: "table", //设置渲染方式canvas/table  
				    width: 500, //宽度 
				    height:500, //高度 
				    correctLevel : QRErrorCorrectLevel.H,
				    text: data
				});
		},
		error : function(data) {
		}
	});
 </script>
 </head>

 <body>
	<div id="qrcode"></div>
 </body>

 
</html>

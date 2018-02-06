<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="/base/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang='zh'>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/form-style/css/demo.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/form-style/css/style3.css" />
<link href="<%=contextPath%>/pages/business/QRCode/css/toggle-checkbox.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=contextPath%>/js/base64.min.js"></script>
<style type="text/css">
#queryParams{
 	width: 100%;
 	height: 220px;
 	background-attachment: fixed;
	display: block;
	border: 1px solid #dedcdc;
	font-size: 18px;
 } 
</style>
<script type="text/javascript">
</script>
<script src="<%=contextPath%>/pages/private/js/sqlplus.js" type="text/javascript"></script>
</head>
<body style="overflow: hidden;background: #f2f2f2;">
	<div style="margin: 0px;background: #f2f2f2;">
<!-- 		<input id="queryParams" name="queryParams" class="inputQueryEle" style="width: 100%;height: 75px;font-size: 2.5em;" /> -->
		<textarea id="queryParams" name="queryParams" placeholder="www.sasit.cc"></textarea>
	</div>
	<table id="sqldata" style="height: auto;"></table>
</body>
</html>
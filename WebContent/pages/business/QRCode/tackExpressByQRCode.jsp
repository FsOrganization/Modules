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
<script type="text/javascript" src="<%=contextPath%>/common/form-style/js/modernizr.custom.04022.js"></script>
<style type="text/css">
@font-face {
  font-family: 'Roboto Condensed';
  font-style: normal;
  font-weight: 300;
  src: local('Roboto Condensed Light'), local('RobotoCondensed-Light'), url(<%=contextPath%>/pages/business/test/weixinExtraction-master/fonts/robotoCondensed-Light.woff2) format('woff2');
  unicode-range: U+0000-00FF, U+0131, U+0152-0153, U+02C6, U+02DA, U+02DC, U+2000-206F, U+2074, U+20AC, U+2212, U+2215, U+E0FF, U+EFFD, U+F000;
}
table.contacts { 
	width: 100%;
	background-color: #fafafa;
	border: 0px #000000 solid;
	border-collapse: collapse;
	border-spacing: 0px; 
	height: inherit;
}

td.contact { 
	border-bottom: 1px #6699CC dotted;
	text-align: left;
	font-family: Verdana, sans-serif, Arial;
	font-weight: normal;
	font-size: .7em;
	color: #404040;
	background-color: #fafafa;
	padding-top: 4px;
	padding-bottom: 4px;
	padding-left: 8px;
	padding-right: 0px; 
}

.contactinput:hover {
  background: #e6e6e6;
}
/* after checked */
[type="checkbox"]:not(:checked),[type="checkbox"]:checked {
  position: absolute;
  left: -9999px;
}
[type="checkbox"]:not(:checked) + label,
[type="checkbox"]:checked + label {
  position: relative;
  padding-left: 25px;
  cursor: pointer;
}

/* checkbox aspect */
[type="checkbox"]:not(:checked) + label:before,
[type="checkbox"]:checked + label:before {
  content: '';
  position: absolute;
  left:0; top: 2px;
  width: 14px; height: 14px;
  border: 1px solid #aaa;
  background: #f8f8f8;
  border-radius: 3px;
  box-shadow: inset 0 1px 3px rgba(0,0,0,.3)
}
/* checked mark aspect */
[type="checkbox"]:not(:checked) + label:after,
[type="checkbox"]:checked + label:after {
  content: '✔';
  position: absolute;
  top: 0; left: 4px;
  font-size: 12px;
  color: #D7520C;
  transition: all 0.1s cubic-bezier(1,.18,.01,.39) 0s;
  margin: 2px 0px;
}
/* checked mark aspect changes */
[type="checkbox"]:not(:checked) + label:after {
  opacity: 0;
  transform: scale(0);
}
[type="checkbox"]:checked + label:after {
  opacity: 1;
  transform: scale(1);
}
/* disabled checkbox */
[type="checkbox"]:disabled:not(:checked) + label:before,
[type="checkbox"]:disabled:checked + label:before {
  box-shadow: none;
  border-color: #bbb;
  background-color: #ddd;
}
[type="checkbox"]:disabled:checked + label:after {
  color: #999;
}
[type="checkbox"]:disabled + label {
  color: #aaa;
}
/* accessibility */
[type="checkbox"]:checked:focus + label:before,
[type="checkbox"]:not(:checked):focus + label:before {
  border: 1px dotted blue;
}

/* hover style just for information */
label:hover:before {
  border: 1px solid #4778d9!important;
}
</style>
<script type="text/javascript">
</script>
<script src="<%=contextPath%>/pages/business/QRCode/js/tackExpressByQRCode.js" type="text/javascript"></script>
</head>
<body style="overflow-y: hidden;background: #f2f2f2;">
	<div style="margin: 0px;background: #f2f2f2;">
       	&nbsp;&nbsp;二维码信息：<input id="queryParams" name="queryParams" class="inputQueryEle" placeholder="请扫描">
	</div>
	<table id="expressBarCodeGrid" style="height: auto;"></table>
    <div id="expressListDetail" class="container" style="min-height: 220px;overflow:hidden;">
<!--     	&nbsp;&nbsp;客户姓名：<span id="cName"></span> -->
    	<table id="expressDetailGrid" style="height: auto;"></table>
    </div>
	<!-- 条码显示 -->
    <div id="imgDetail" class="easyui-dialog" title="Complex Toolbar on Dialog" style="width:400px;height:200px;padding:10px;"
            data-options="buttons: '#dlg-buttons',closed: true">
            <div style="margin: 20px 30px;">
            	<img id="barimg" src=""></img>
                <span id="fileName" style="margin: 0px 80px;font-weight: 300;font-size: 18px;"></span>
            </div>
    </div>
    <div id="dlg-buttons">
        <a id="submitBtn" class="easyui-linkbutton">打印条码</a>
        <a id="close" class="easyui-linkbutton" onclick="javascript:$('#imgDetail').dialog('close')">Close</a>
    </div>
    
    <!-- 显示签名 -->
    <div id="signatureDetail" style="text-align: center;"></div>
    <div id="right_menu" class="easyui-menu" style="height:200px;text-align:center;width: 612px;box-shadow: 2px 2px 5px -1px;">
		<input type="hidden" id="currentIndex"/>
		<input type="hidden" id="currentName"/>
	</div>
<!--     <div id="dlg-buttons"> -->
<!--         <a id="submitBtn" class="easyui-linkbutton">打印签名</a> -->
<!--         <a id="close" class="easyui-linkbutton" onclick="javascript:$('#signatureDetail').dialog('close')">Close</a> -->
<!--     </div> -->
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/base/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang='zh'>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css"
	href="<%=contextPath%>/common/form-style/css/demo.css" />
<link rel="stylesheet" type="text/css"
	href="<%=contextPath%>/common/form-style/css/style3.css" />
<script type="text/javascript"
	src="<%=contextPath%>/common/form-style/js/modernizr.custom.04022.js"></script>
<style type="text/css">
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
	width: 14px;
	height: 14px;
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
	top: 0;
	left: 4px;
	font-size: 12px;
	color: #D7520C;
	transition: all 0.1s cubic-bezier(1, .18, .01, .39) 0s;
	margin: 2px 0px;
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
.ul-main-cs {
	font-size: 26px;
	background-color:#f4fbff;
	height:90px;
	width:100%;
	padding: 35px 55px;
	margin: 1px auto;
	border-radius: 3px;
}

.ul-li-cs {
	color: #2f2d31;
}
</style>
<script type="text/javascript">
	
</script>
<script
	src="<%=contextPath%>/pages/business/registered/js/registeredCustomersConfig.js"
	type="text/javascript"></script>
</head>
<body style="overflow-y: hidden; background: #f2f2f2;">
	<div style="margin: 0px; background: #f2f2f2;">
		<span> 
			&nbsp;&nbsp;请选择网点： <input id="statisticalArea" name="statisticalArea" class="inputQueryEle" />
		</span> 
		<span>
			&nbsp;&nbsp;<input id="queryParams" name="queryParams" class="inputQueryEle" placeholder="手机号码">
		</span>
	</div>

	<table id="registeredCustomersConfigGrid" style="height: auto;"></table>
	<div id="setConfig" class="container" style="min-height: 240px; overflow: hidden; height: 240">
		<section class="af-wrapper">
		<h1></h1>
		<label for="af-showreq" class="af-show" style="margin: -15px 40px;">*突出必填项</label>
		<input id="af-showreq" class="af-show-input" type="checkbox" name="showreq" /> 
		<input type="hidden" name="id" id="id">
		<form class="af-form" id="af-form" novalidate style="margin: 45px -15px;">
			<div class="af-outer af-required">
				<div class="af-inner af-required">
					<label for="name">快递服务商:</label> 
					<input id="expressServiceId" name="expressServiceId" style="width: 150px;border-style: solid;border-color: antiquewhite;" class="easyui-combobox">
					<span style="color:red;">&nbsp;(*必填项)</span>
				</div>
			</div>
			<input type="hidden" name="phoneNumber" id="phoneNumber" />
			<input type="hidden" name="shopCode" id="shopCode" />
			<input type="hidden" name="areaCode" id="areaCode" />
			<input type="hidden" name="customerName" id="customerName" />
		</form>
		<div style="text-align: center;">
			<input id="saveBtn" type="button" value="保存"
				style="height: 30px; width: 49px;" /> <input id="cancelBtn"
				type="button" value="关闭" style="height: 30px; width: 49px;" />
		</div>
		</section>
	</div>
	<div id="customerContainer" class="container" style="min-height: 260px; overflow-x: hidden;">
		<ul id="infolis" style="font-size: 36px;"></ul>
	</div>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/base/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang='zh'>
<head>
<title>用户注册 |Home :: 幸福快递</title>
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/form-style/css/demo.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/form-style/css/style3.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/mobile-style/css/style.css" />
<script type="text/javascript" src="<%=contextPath%>/common/form-style/js/modernizr.custom.04022.js"></script>
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

.wechatTitle{
	font-weight: 700;font-size: 1.9em;width: 100%;height: 35px;padding: 15px 2px;background-color: #F8FCFF;
}
</style>
<script src="<%=contextPath%>/pages/business/wechat/js/wechatRegisterPage.js" type="text/javascript"></script>
</head>
<body id="queryBody">
	<!-- start sub-header -->
	<div class="header_sub_bg">
			<div>	
				<p style="font-size: 1.9em;">幸福快递 用户注册<input type="button" value="关闭窗口" onclick="WeixinJSBridge.call('closeWindow');" /></p>
			</div>
			<div class="hdr-nav" style="margin: -50px 0px;">
				<div class="search-box">
			    	<form action="#">
			    		<input id="openId" name="openId" type="hidden" value="${openId}">
			    		<div style="margin: 69px -8px;">
			    		<div>
				    		<div class="wechatTitle">幸福网点:</div>
				    		<input id="shopCodeList" class="easyui-combobox" name="shopCodeList" data-options="
										                url: '<%=contextPath%>/pages/system/getShopInfoForSelectForWechat.light',
										                method: 'get',
										                valueField: 'value',
										                textField: 'text',
										                panelWitdh: 380,
										                panelHeight: 150,
										                width:fixWidth(),
										                height: 60,
										                formatter: formatItem" />
						</div>
						<div>
				    		<div class="wechatTitle">手机号码:</div>
				    		<input id="phoneNumber" name="phoneNumber" style="height: 60px;width: 100%;font-size: 1.9em;" placeholder="手机号码 ">
				    	</div>
				    	<div>
				    		<div class="wechatTitle">姓	名:</div>
				    		<input id="name" name="name" style="height: 60px;width: 100%;font-size: 1.9em;" placeholder="姓名">
				    	</div>
						</div>
				    	<input id="registerSubmit" name="registerSubmit" style="height: 80px;width: 99%;font-size: 1.9em;margin: -50px 0px;" value="提交注册" type="button" onclick="register()">
			    	</form>
			    	<div class="clear"></div>
			    </div>
			</div>
			<div class="clear"></div>
	</div>
<!-- start footer -->
<!-- 	<div class="footer"> -->
<!-- 		<div class="copy">	 -->
<!-- 			<p class="w3-link">Copyright 光艺软件科技. 2013 All Rights Reserved.&nbsp; <a href="#"> 光艺软件科技</a></p> -->
<!-- 		</div> -->
<!-- 		<div class="clear"></div> -->
<!-- 	</div> -->
</body>
</html>
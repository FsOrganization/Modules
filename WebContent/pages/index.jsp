
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/base/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/pages/css/main.css" />
<style type="text/css">
.speli {
	margin: 2px 25px;
}

#slideout {
			position: fixed;
			top: 220px;
			right: 0;
			width: 22px;
			padding: 15px 0;
			text-align: center;
			background: none repeat scroll 0% 0% rgba(63, 139, 190, 0.58);
			-webkit-transition-duration: 0.3s;
			-moz-transition-duration: 0.3s;
			-o-transition-duration: 0.3s;
			transition-duration: 0.3s;
			-webkit-border-radius: 5 0px 0px 5;
			-moz-border-radius: 5 0px 0px 5;
			border-radius: 5 0px 0px 5;
}
#slideout_inner {
			position: fixed;
			top: 220px;
			right: -250px;
			background: none repeat scroll 0% 0% rgba(63, 139, 190, 0.58);
			width: 200px;
			padding: 25px;
			height: 130px;
			-webkit-transition-duration: 0.3s;
			-moz-transition-duration: 0.3s;
			-o-transition-duration: 0.3s;
			transition-duration: 0.3s;
			text-align: left;
			-webkit-border-radius: 0 0 0px 5;
			-moz-border-radius: 0 0 0px 5;
			border-radius: 0 0 0px 5;
}
#slideout_inner textarea {
			width: 190px;
			height: 100px;
			margin-bottom: 6px;
}
#slideout:hover {
			right: 250px;
}
#slideout:hover #slideout_inner {
			right: 0;
}

</style>
<title>源信幸福快递</title>
<script type="text/javascript" src="<%=contextPath%>/pages/js/menu.js"></script>
<script type="text/javascript" src="<%=contextPath%>/pages/js/slideshow.js"></script>
<script type="text/javascript" src="<%=contextPath%>/pages/js/cufon-yui.js"></script>
<script type="text/javascript" src="<%=contextPath%>/pages/js/Arial.font.js"></script>
<script type="text/javascript" src="<%=contextPath%>/pages/js/index.js"></script>
</head>
<body id="indexbody" style="background: #fff;overflow-y:auto;overflow-x:hidden;" topmargin="0">
	<table border="0" cellpadding="0" cellspacing="0" width="100%" height="473" style="border-collapse: collapse" bordercolor="#111111">
		<tr>
			<td height="1"><img src="<%=contextPath%>/pages/images/spacer.gif" width="10" height="1" border="0"></td>
			<td height="1"><img src="<%=contextPath%>/pages/images/spacer.gif" width="5" height="1" border="0"></td>
			<td height="1"><img src="<%=contextPath%>/pages/images/spacer.gif" width="127" height="1" border="0"></td>
			<td height="1"><img src="<%=contextPath%>/pages/images/spacer.gif" width="6" height="1" border="0"></td>
			<td height="1"><img src="<%=contextPath%>/pages/images/spacer.gif" width="13" height="1" border="0"></td>
			<td height="1"><img src="<%=contextPath%>/pages/images/spacer.gif" width="6" height="1" border="0"></td>
			<td height="1"><img src="<%=contextPath%>/pages/images/spacer.gif" width="102" height="1" order="0"></td>
			<td height="1"><img src="<%=contextPath%>/pages/images/spacer.gif" width="343" height="1" border="0"></td>
			<td height="1"><img src="<%=contextPath%>/pages/images/spacer.gif" width="6" height="1" border="0"></td>
			<td height="1"><img src="<%=contextPath%>/pages/images/spacer.gif" width="12" height="1" border="0"></td>
			<td height="1"><img src="<%=contextPath%>/pages/images/spacer.gif" border="0" width="1" height="1"></td>
		</tr>
		<tr>
			<td colspan="10" background="<%=contextPath%>/pages/images/top-back.gif">
				<img name="index_r1_c1" src="<%=contextPath%>/pages/images/headbg_1826.png" style="border: 0; width: 100%;height: 40px;">
			</td>
			<td colspan="3"
				background="<%=contextPath%>/pages/images/top-back.gif" height="40px">
			</td>
		</tr>
		<tr>
			<td colspan="10" style="background: #3F8BBE;" valign="top" height="2"></td>
		</tr>
		<tr>
			<td rowspan="4" style="background: #3F8BBE;" valign="top" height="319">
				
			</td>
			<td valign="top" height="5">
				<p align="left">
					<img name="index_r3_c2"
						src="<%=contextPath%>/pages/images/index_r3_c2.gif" border="0"
						width="5" height="5">
			</td>
			<td rowspan="2" style="background: #fff;" valign="top" height="6"></td>
			<td rowspan="2" valign="top" height="6">
				<p align="right">
					<img name="index_r3_c4"
						src="<%=contextPath%>/pages/images/index_r3_c4.gif" border="0"
						width="6" height="6">
			</td>
			<td rowspan="4" style="background: #3F8BBE;" valign="top" height="319"></td>
			<td rowspan="2" valign="top" height="6">
				<p align="left">
					<img name="index_r3_c6"
						src="<%=contextPath%>/pages/images/index_r3_c6.gif" border="0"
						width="6" height="6">
				</p>
			</td>
			<td rowspan="2" colspan="2" style="background: #fff;" valign="top" height="6">
			</td>
			<td rowspan="2" valign="top" height="6">
				<p align="right">
					<img name="index_r3_c9" src="<%=contextPath%>/pages/images/index_r3_c9.gif" border="0" width="6" height="6">
			</td>
<!-- 			<td rowspan="4" style="background: #3F8BBE;" valign="top" height="319"></td> -->
		</tr>
		<tr>
			<td rowspan="2" style="background: #fff;" valign="top" height="307"></td>
		</tr>
		<tr>
			<td style="background: #fff;" valign="top" height="100%">
				<div align="center">
					<center>
						<table border="1" cellpadding="0" cellspacing="0" style="border-collapse: collapse; border-width: 0" bordercolor="#111111" width="89%" id="AutoNumber1">
							<tr>
								<td width="100%"
									style="border-left: medium none #111111; border-right: medium none #111111; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium"
									bgcolor="#1B536D" align="center">
									<p style="margin-top: 2; margin-bottom: 2">
										<b><font face="Arial" size="2" color="#FFFFFF">入库管理</font></b>
								</td>
							</tr>
							<tr>
								<td width="100%"
									style="border-left: 1px none #111111; border-right: 1px none #111111; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium">
									<p style="margin: 8px 5px;" dir="ltr">
										<font face="Arial" size="2" color="#111111">
											<a href="javascript:void(0);" onclick='addTab("快件入库", "<%=contextPath%>/pages/business/test/infinished.jsp","icon-sz",undefined,"infinished");'>快件入库</a>
										</font>
								</td>
							</tr>
							<tr>
								<td width="100%"
									style="border-left: 1px none #111111; border-right: 1px none #111111; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium">
									<p style="margin: 8px 5px;" dir="ltr">
										<font face="Arial" size="2" color="#111111">
											<a href="javascript:void(0);" onclick='addTab("快件出库", "<%=contextPath%>/pages/business/test/NewFile.jsp","icon-sz");'>快件出库</a>
										</font>
								</td>
							</tr>
							<tr>
								<td width="100%"
									style="border-left: 1px none #111111; border-right: 1px none #111111; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium">
									<p style="margin: 8px 5px;" dir="ltr">
										<font face="Arial" size="2" color="#111111">
											<a href="javascript:void(0);" onclick='addTab("快件日志", "<%=contextPath%>/pages/business/test/expressInfo.jsp","icon-sz");'>快件日志</a>
										</font>
								</td>
							</tr>
							<tr>
								<td width="100%"
									style="border-left: 1px none #111111; border-right: 1px none #111111; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium">
									<p style="margin: 8px 5px;" dir="ltr">
										<font face="Arial" size="2" color="#111111">
											<a href="javascript:void(0);" onclick='addTab("寄件管理", "<%=contextPath%>/pages/business/test/sentExpress.jsp","icon-sz");'>寄件管理</a>
										</font>
								</td>
							</tr>
						</table>
					</center>
				</div>
				<p style="margin-top: 0; margin-bottom: 0">&nbsp;</p>
				<div align="center" id="sysSeting">
					<input type="hidden" id="loginTag" value="${loginName}"/>
					<center>
						<table border="1" cellpadding="0" cellspacing="0"
							style="border-collapse: collapse; border-width: 0"
							bordercolor="#111111" width="89%" id="AutoNumber2">
							<tr>
								<td width="100%"
									style="border-style: none; border-width: medium"
									bgcolor="#1B536D" align="center" height="18">
									<p style="margin-top: 2; margin-bottom: 2">
										<b><font face="Arial" size="2" color="#FFFFFF">系统设置</font></b>
								</td>
							</tr>
							<tr>
								<td width="100%"
									style="border-left: 1px none #111111; border-right: 1px none #111111; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium">
									<p style="margin: 8px 5px;" dir="ltr">
										<font face="Arial" size="2" color="#111111">
											<a href="javascript:void(0);" onclick='addTab("区域设置", "<%=contextPath%>/pages/business/systemSetUp/areaSetUp.jsp","icon-sz",undefined,"infinished");'>区域设置</a>
										</font>
								</td>
							</tr>
							<tr>
								<td width="100%"
									style="border-left: 1px none #111111; border-right: 1px none #111111; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium">
									<p style="margin: 8px 5px;" dir="ltr">
										<font face="Arial" size="2" color="#111111">
											<a href="javascript:void(0);" onclick='addTab("网点设置", "<%=contextPath%>/pages/business/systemSetUp/shopSetUp.jsp","icon-sz",undefined,"infinished");'>网点设置</a>
										</font>
								</td>
							</tr>
							<tr>
								<td width="100%"
									style="border-left: 1px none #111111; border-right: 1px none #111111; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium">
									<p style="margin: 8px 5px;" dir="ltr">
										<font face="Arial" size="2" color="#111111">
											<a href="javascript:void(0);" onclick='addTab("用户设置", "<%=contextPath%>/pages/business/systemSetUp/userSetUp.jsp","icon-sz",undefined,"infinished");'>用户设置</a>
										</font>
								</td>
							</tr>
							<tr>
								<td width="100%"
									style="border-left: 1px none #111111; border-right: 1px none #111111; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium">
									<p style="margin: 8px 5px;" dir="ltr">
										<font face="Arial" size="2" color="#111111">
											<a href="javascript:void(0);" onclick='addTab("快递服务商设置", "<%=contextPath%>/pages/business/systemSetUp/expressServiceProviderSetUp.jsp","icon-sz",undefined,"infinished");'>快递服务商设置</a>
										</font>
								</td>
							</tr>
							<tr>
								<td width="100%"
									style="border-left: 1px none #111111; border-right: 1px none #111111; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium">
									<p style="margin: 8px 5px;" dir="ltr">
										<font face="Arial" size="2" color="#111111">
											<a href="javascript:void(0);" onclick='addTab("客户信息管理", "<%=contextPath%>/pages/business/customer/customerInfo.jsp","icon-sz",undefined,"infinished");'>客户信息管理</a>
										</font>
								</td>
							</tr>
						</table>
					</center>
				</div>
				<div align="center" id="configSeting" style="display: none">
<%-- 					<input type="hidden" id="loginTag" value="${loginName}"/> --%>
					<center>
						<table border="1" cellpadding="0" cellspacing="0"
							style="border-collapse: collapse; border-width: 0"
							bordercolor="#111111" width="89%" id="AutoNumber2">
							<tr>
								<td width="100%"
									style="border-style: none; border-width: medium"
									bgcolor="#1B536D" align="center" height="18">
									<p style="margin-top: 2; margin-bottom: 2">
										<b><font face="Arial" size="2" color="#FFFFFF">客户管理</font></b>
								</td>
							</tr>
							<tr style="display: none;">
								<td width="100%"
									style="border-left: 1px none #111111; border-right: 1px none #111111; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium">
									<p style="margin: 8px 5px;" dir="ltr">
										<font face="Arial" size="2" color="#111111">
											<a href="javascript:void(0);" onclick='addTab("系统参数设置", "<%=contextPath%>/pages/business/systemSetUp/configSetUp.jsp","icon-sz",undefined,"infinished");'>系统参数设置</a>
										</font>
								</td>
							</tr>
							<tr>
								<td width="100%"
									style="border-left: 1px none #111111; border-right: 1px none #111111; border-top-style: none; border-top-width: medium; border-bottom-style: none; border-bottom-width: medium">
									<p style="margin: 8px 5px;" dir="ltr">
										<font face="Arial" size="2" color="#111111">
											<a href="javascript:void(0);" onclick='addTab("客户信息", "<%=contextPath%>/pages/business/systemSetUp/configSetUp.jsp","icon-sz",undefined,"infinished");'>客户信息</a>
										</font>
								</td>
							</tr>
						</table>
					</center>
				</div>
			</td>
			<td style="background: #fff;" valign="top" height="100%"></td>
			<td style="background: #fff;" valign="top" height="100%"></td>
			<td colspan="2" style="background: #fff;" valign="top" height="100%">
				<table border="1" cellpadding="0" cellspacing="0" style="border-collapse: collapse; border-width: 0;" id="AutoNumber3">
					<div id="tabs" class="easyui-tabs" style="width: 1695px;height: 877px;">
						<div title="特别说明" style="padding:10px">
<!-- 							<ul> -->
<!-- 								<li class="speli" style="font-size: large;">签字板功能仅支持IE浏览器 -->
<!-- 	 								<span class="formInfo"> -->
<%-- 										<a href="<%=contextPath%>/pages/system/getToolTipMsgById.light" class="jTip" id="one" name="重要提示!">!</a> --%>
<!-- 									</span> -->
<!-- 								</li> -->
<!-- 							</ul> -->
								<div style="margin: 5px 5px;">你好：${loginName}</div>
						</div>
					</div>
				</table>
			</td>
			<td style="background: #fff;" valign="top" height="306"></td>
		</tr>
		<tr>
			<td valign="bottom" height="7">
				<p align="left">
					<img name="index_r6_c2" src="<%=contextPath%>/pages/images/index_r6_c2.gif" border="0" width="5" height="7">
			</td>
			<td style="background: #fff;" valign="top" height="7"></td>
			<td valign="bottom" height="7">
				<p align="right">
					<img name="index_r6_c4" src="<%=contextPath%>/pages/images/index_r6_c4.gif" border="0" width="6" height="7">
			</td>
			<td valign="bottom" height="7">
				<p align="left">
					<img name="index_r6_c6" src="<%=contextPath%>/pages/images/index_r6_c6.gif" border="0" width="6" height="7">
			</td>
			<td colspan="2" style="background: #fff;" valign="top" height="7">
				
			</td>
			<td valign="bottom" height="7">
				<p align="right">
					<img name="index_r6_c9" src="<%=contextPath%>/pages/images/index_r6_c9.gif" border="0" width="6" height="7">
			</td>
		</tr>
		<tr>
			<td colspan="10" style="background: #3F8BBE;" valign="top" height="5"></td>
		</tr>
		<tr>
			<td colspan="10" bgcolor="#000;" height="26">
				<p align="center">
					<b><font size="1" color="#FFFFFF" face="Verdana">Copyright 光艺软件科技. 2013 All Rights Reserved.</font></b>
			</td>
		</tr>
	</table>

</body>
<div id="slideout">
  <img src="<%=contextPath%>/pages/images/systemset.png" alt="Feedback" />
  <div id="slideout_inner">
    <form action="<%=contextPath%>/pages/system/welcome.light" method="post">
		<div style="margin: 5px 5px;">你好：${loginName}</div>
		<input type="submit" style="height: 30px;width: 60px;" class="l-btn-text" value="退出系统">
		<input type="button" style="height: 30px;width: 60px;" class="l-btn-text" value="修改密码">
	</form>
  </div>
</div>

<!-- <div id="slideout_2"> -->
<%--   <img src="<%=contextPath%>/pages/images/feedback.png" alt="Feedback" /> --%>
<!--   <div id="slideout_inner_2"> -->
<!-- </div> -->
<!-- </div> -->

</html>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/base/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/pages/css/main.css" />
<link rel="stylesheet" href="<%=contextPath%>/pages/menuStyle/style.css" type="text/css" />

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

.pwModfiy *{
	font: 12px Open Sans,Arial,sans-serif;
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
	<input type="hidden" id="loginTag" value="${loginName}"/>
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
						<ul id="css3menu1" class="topmenu">
									<input type="checkbox" id="css3menu-switcher" class="switchbox">
									<label onclick="" class="switch" for="css3menu-switcher"></label>	
									<li class="topmenu"><a href="#" style="width:125px;height:25px;line-height:25px;cursor:default;"><img src="<%=contextPath%>/pages/menuStyle/refresh.png" alt=""/>快件管理</a></li>
									<li class="topmenu"><a style="font-weight: 100;text-align: center;" href="javascript:void(0);" onclick='addTab("快件入库", "<%=contextPath%>/pages/business/test/infinished.jsp","icon-sz",undefined,"infinished");'>快件入库</a></li>
									<li class="topmenu"><a style="font-weight: 100;text-align: center;" href="javascript:void(0);" onclick='addTab("快件出库", "<%=contextPath%>/pages/business/test/NewFile.jsp","icon-sz");'>快件出库</a></li>
									<li class="topmenu"><a style="font-weight: 100;text-align: center;" href="javascript:void(0);" onclick='addTab("寄件管理", "<%=contextPath%>/pages/business/test/sentExpress.jsp","icon-sz");'>寄件管理</a></li>
									<li class="topmenu"><a style="font-weight: 100;text-align: center;" href="javascript:void(0);" onclick='addTab("快件日志", "<%=contextPath%>/pages/business/test/expressInfo.jsp","icon-sz");'>快件日志</a></li>
						</ul>
					</center>
				</div>
				<div align="center" id="customerSeting">
					<center>
							<ul id="css3menu1" class="topmenu">
								<input type="checkbox" id="css3menu-switcher" class="switchbox">
								<label onclick="" class="switch" for="css3menu-switcher"></label>	
								<li class="topmenu"><a href="#" style="width:125px;height:25px;line-height:25px;cursor:default;"><img src="<%=contextPath%>/pages/menuStyle/users.png" alt=""/>客户管理</a></li>
								<li class="topmenu"><a style="font-weight: 100;text-align: center;" href="javascript:void(0);" onclick='addTab("客户信息管理", "<%=contextPath%>/pages/business/customer/customerInfo.jsp","icon-sz",undefined,"customer");'>客户信息管理</a></li>
							</ul>
					</center>
				</div>
				<div align="center" id="statistics">
					<center>
						<ul id="css3menu1" class="topmenu">
									<input type="checkbox" id="css3menu-switcher" class="switchbox">
									<label onclick="" class="switch" for="css3menu-switcher"></label>	
									<li class="topmenu"><a href="#" style="width:125px;height:25px;line-height:25px;cursor:default;"><img src="<%=contextPath%>/pages/menuStyle/chart.png" alt=""/>统计分析 </a></li>
									<li class="topmenu"><a style="font-weight: 100;text-align: center;" href="javascript:void(0);" onclick='addTab("网点人数统计表", "<%=contextPath%>/pages/report/shopNumberOfPeopleStatistics.jsp","icon-sz",undefined,"statistics");'>网点人数统计表</a></li>
									<li class="topmenu"><a style="font-weight: 100;text-align: center;" href="javascript:void(0);" onclick='addTab("网点收寄件统计", "<%=contextPath%>/pages/report/shopInAndSendExpressGroupCount.jsp","icon-sz",undefined,"statistics");'>网点收寄件统计</a></li>
								</ul>
					</center>
				</div>
				
				<div align="center" id="configSeting">
					<center>
						<ul id="css3menu1" class="topmenu">
							<input type="checkbox" id="css3menu-switcher" class="switchbox">
							<label onclick="" class="switch" for="css3menu-switcher"></label>	
							<li class="topmenu"><a href="#" style="width:125px;height:25px;line-height:25px;cursor:default;"><img src="<%=contextPath%>/pages/menuStyle/process1.png" alt=""/>系统设置</a></li>
							<li class="topmenu"><a href="#" style="width:125px;text-align: center;"><span>区域&网点设置</span></a>
								<ul>
									<li><a href="javascript:void(0);" onclick='addTab("区域设置", "<%=contextPath%>/pages/business/systemSetUp/areaSetUp.jsp","icon-sz",undefined,"infinished");'>区域设置</a></li>
									<li><a style="font-weight: 100;" href="javascript:void(0);" onclick='addTab("网点设置", "<%=contextPath%>/pages/business/systemSetUp/shopSetUp.jsp","icon-sz",undefined,"shopSet");'>网点设置</a></li>
								</ul>
							</li>
							<li class="topmenu"><a style="font-weight: 100;text-align: center;" href="javascript:void(0);" onclick='addTab("用户设置", "<%=contextPath%>/pages/business/systemSetUp/userSetUp.jsp","icon-sz",undefined,"systemSet");'>用户设置</a></li>
<%-- 							<li class="topmenu"><a style="font-weight: 100;text-align: center;" href="javascript:void(0);" onclick='addTab("快递服务商设置", "<%=contextPath%>/pages/business/systemSetUp/expressServiceProviderSetUp.jsp","icon-sz",undefined,"infinished");'>快递服务商设置</a></li> --%>
							<li class="topmenu"><a style="font-weight: 100;text-align: center;" href="javascript:void(0);" onclick='addTab("短信监控配置", "http://sms.sms.cn/login.php","icon-sz",undefined,"sms");'>短信监控配置</a></li>
						</ul>
					</center>
				</div>
				<div align="center">
					<center>
						<ul id="css3menu1" class="topmenu">
							<input type="checkbox" id="css3menu-switcher" class="switchbox">
							<label onclick="" class="switch" for="css3menu-switcher"></label>	
							<li class="topmenu"><a href="#" style="width:125px;height:25px;line-height:25px;cursor:default;"><img src="<%=contextPath%>/pages/menuStyle/process1.png" alt=""/>参数设置</a></li>
							<li class="topmenu"><a style="font-weight: 100;text-align: center;" href="javascript:void(0);" onclick='addTab("快递服务商设置", "<%=contextPath%>/pages/business/systemSetUp/expressServiceProviderSetUp.jsp?loginName=${loginName}","icon-sz",undefined,"provider");'>快递服务商设置</a></li>
						</ul>
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
		<input id="modfiyPassWord" type="button" style="height: 30px;width: 60px;" class="l-btn-text" value="修改密码">
	</form>
  </div>
</div>
<div id="modfiyPassWordWindow" class="container" style="min-height: 150px;overflow:hidden;">
	<input type="hidden" name="loginName" id="loginName" value="${loginName}">
	<div style="margin: 10px 10px;text-align:center;" class="pwModfiy">
			<table>
				<tr>
					<td align="right">
							<label for="pw">新密码：</label>
							<input type='password' id="pw" name="pw" style="width: 210px;height:30px;border-style: solid;border-color: antiquewhite;" placeholder="新密码">
					</td>
					
				<tr>
				<tr>
					<td align="right">
						<label for="confirmPw">确认新密码：</label>
							<input type="password" name="confirmPw" id="confirmPw" style="width: 210px;height:30px;border-style: solid;border-color: antiquewhite;" placeholder="确认新密码">
					</td>
				</tr>
<!-- 				<tr> -->
<!-- 					<td align="right"> -->
<!-- 							<label for="nickName">昵称：</label> -->
<!-- 							<input id="nickName" name="nickName" style="width: 210px;height:30px;border-style: solid;border-color: antiquewhite;" placeholder=""> -->
<!-- 					</td> -->
					
<!-- 				<tr> -->
<!-- 				<tr> -->
<!-- 					<td align="right"> -->
<!-- 						<label for="phoneNumber">联系电话：</label> -->
<!-- 							<input type="text" name="phoneNumber" id="phoneNumber" style="width: 210px;height:30px;border-style: solid;border-color: antiquewhite;" placeholder=""> -->
<!-- 					</td> -->
<!-- 				</tr> -->
			</table>
	</div>
	<div style="text-align:center;margin: 15px 0px;">
	  	<input id="saveBtn" type="button" value="保存" style="height: 30px;width: 49px;"/>
		<input id="cancelBtn" type="button" value="关闭" style="height: 30px;width: 49px;"/>
	</div>
</div>

</html>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/base/base.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/pages/css/main.css" />
<link rel="stylesheet" href="<%=contextPath%>/pages/menuStyle/style.css" type="text/css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/mochaui-0.9.7/themes/default/css/Content.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/mochaui-0.9.7/themes/default/css/Core.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/mochaui-0.9.7/themes/default/css/Layout.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/mochaui-0.9.7/themes/default/css/Dock.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/mochaui-0.9.7/themes/default/css/Window.css" />
<%-- <link rel="stylesheet" type="text/css" href="<%=contextPath%>/mochaui-0.9.7/themes/default/css/Tabs.css" /> --%>

<!--[if IE]>
<script type="text/javascript" src="<%=contextPath%>/mochaui-0.9.7/scripts/excanvas_r43.js"></script>
<![endif]-->
<%-- <script type="text/javascript" src="<%=contextPath%>/mochaui-0.9.7/scripts/mootools-1.2.4-core-yc.js"></script> --%>
<%-- <script type="text/javascript" src="<%=contextPath%>/mochaui-0.9.7/scripts/mootools-1.2.4-more-yc.js"></script> --%>
<%-- <script type="text/javascript" src="<%=contextPath%>/mochaui-0.9.7/scripts/mocha.js"></script> --%>
<%-- <script type="text/javascript" src="<%=contextPath%>/mochaui-0.9.7/scripts/mocha-init.js"></script> --%>

	
<style type="text/css">
.speli {
	margin: 2px 25px;
}

</style>
<title>源信幸福快递</title>
<script type="text/javascript" src="<%=contextPath%>/pages/js/menu.js"></script>
<%-- <script type="text/javascript" src="<%=contextPath%>/pages/js/slideshow.js"></script> --%>
<%-- <script type="text/javascript" src="<%=contextPath%>/pages/js/cufon-yui.js"></script> --%>
<%-- <script type="text/javascript" src="<%=contextPath%>/pages/js/Arial.font.js"></script> --%>
<script type="text/javascript" src="<%=contextPath%>/pages/js/index.js"></script>
<script type="text/javascript" src="<%=contextPath%>/js/ZeroClipboard.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/js/sockjs-0.3.min.js"></script>
<script type="text/javascript">	
	
	function logout(){
		$('#logout').submit();
	}
</script>  
</head>
<body id="indexbody" style="background: #fff;overflow-y:auto;overflow-x:hidden;" topmargin="0">
	<input type="hidden" id="loginTag" value="${loginName}"/>
	<table border="0" cellpadding="0" cellspacing="0" width="100%" height="473" style="border-collapse: collapse" bordercolor="#111111">
		<tr>
			<td colspan="10">
	<div id="desktopHeader">
		<div id="desktopTitlebarWrapper">
			<div id="desktopTitlebar">
				<h2 class="tagline">
					<span class="taglineEm">
						<h2>源信幸福快递 </h2>
					</span>
				</h2>
				<div id="topNav">
					<ul class="menu-right" style="margin: 0px 16px 9px 16px;">
						<h4><li>您好 <a onclick="MochaUI.notification('${loginName}');return false;">${loginName}</a></li>
						<li><a id="modfiyPassWord">修改密码</a></li>
						<li><a onclick="logout();">退出系统</a></li></h4>
					</ul>
				</div>
			</div>
		</div>
	
		<div id="desktopNavbar">	
<!-- 			<ul> -->
<!-- 				<li><a class="returnFalse" href="">File</a>	 -->
<!-- 					<ul> -->
<!-- 						<li><a href="#" onclick="MochaUI.notification('Do Something');return false;">Open</a></li>				 -->
<!-- 						<li class="divider"><a class="returnFalse arrow-right" href="">Tests</a> -->
<!-- 							<ul> -->
<!-- 								<li><a id="windoweventsLinkCheck" href="pages/events.html">Window Events</a></li> -->
<!-- 								<li><a id="containertestLinkCheck" href="pages/lipsum.html">Container Test</a></li> -->
<!-- 								<li><a id="iframetestsLinkCheck" href="pages/iframetests.html">Iframe Tests</a></li> -->
<!-- 								<li><a id="formtestsLinkCheck" href="pages/formtests.html">Form Tests</a></li>						 -->
<!-- 								<li><a id="noCanvasLinkCheck" href="pages/lipsum.html">No Canvas Body</a></li> -->
<!-- 							</ul> -->
<!-- 						</li> -->
<!-- 						<li class="divider"><a class="returnFalse arrow-right" href="">Starters</a> -->
<!-- 							<ul> -->
<!-- 								<li><a target="_blank" href="demo-virtual-desktop.html">Virtual Desktop</a></li> -->
<!-- 								<li><a target="_blank" href="demo-fixed-width.html">Fixed Width</a></li> -->
<!-- 								<li><a target="_blank" href="demo-dock-only.html">Fixed Width 2</a></li> -->
<!-- 								<li><a target="_blank" href="demo-no-toolbars.html">No Toolbars</a></li> -->
<!-- 								<li><a target="_blank" href="demo-no-desktop.html">No Desktop</a></li> -->
<!-- 								<li><a target="_blank" href="demo-modal-only.html">Modal Only</a></li> -->
<!-- 							</ul> -->
<!-- 						</li> -->
<!-- 					</ul> -->
<!-- 				</li> -->
<!-- 				<li><a class="returnFalse" href="">Help</a> -->
<!-- 					<ul> -->
<!-- 						<li><a id="featuresLinkCheck" href="pages/features.html">Features</a></li> -->
<!-- 						<li class="divider"><a target="_blank" href="http://mochaui.com/docs/">Documentation</a></li> -->
<!-- 						<li class="divider"><a id="aboutLink" href="pages/about.html">About</a></li> -->
<!-- 					</ul> -->
<!-- 				</li> -->
<!-- 			</ul> -->
			<div class="toolbox divider">
				<div id="spinnerWrapper"><div id="spinner"></div></div>
			</div>

<!-- 			<div class="toolbox divider" style="margin: 5px 0px;">	 -->
<!-- 				<select id="themeControl" name="themeControl" size="1" onchange="MochaUI.Themes.init(this.options[this.selectedIndex].value)"> -->
<!-- 					<option id="chooseTheme" value="" selected>活跃</option> -->
<!-- 					<option value="default">睡眠</option> -->
<!-- 				</select>	 -->
<!-- 			</div> -->
			
		</div><!-- desktopNavbar end -->
	</div><!-- desktopHeader end -->
			</td>
		</tr>
		<tr>
			<td style="background: #f2f2f2;" valign="top" height="100%">
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
								<li class="topmenu"><a style="font-weight: 100;text-align: center;" href="javascript:void(0);" onclick='addTab("客户信息管理", "<%=contextPath%>/pages/business/customer/customerInfo.jsp?loginName=${loginName}","icon-sz",undefined,"customer");'>客户信息管理</a></li>
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
									<li class="topmenu"><a style="font-weight: 100;text-align: center;" href="javascript:void(0);" onclick='addTab("收寄件人数月报", "<%=contextPath%>/pages/report/shopOutAndSendExpressMonthly.jsp","icon-sz",undefined,"statistics");'>收寄件人数月报</a></li>
									<li class="topmenu"><a style="font-weight: 100;text-align: center;" href="javascript:void(0);" onclick='addTab("收寄件分组统计", "<%=contextPath%>/pages/report/shopOutAndSendExpressGroup.jsp","icon-sz",undefined,"statistics");'>收寄件分组统计</a></li>
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
<!-- 							<li class="topmenu"><a style="font-weight: 100;text-align: center;" href="javascript:void(0);" onclick='addTab("短信监控配置", "http://sms.sms.cn/login.php","icon-sz",undefined,"sms");'>短信监控配置</a></li> -->
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
							<li class="topmenu"><a style="font-weight: 100;text-align: center;" href="javascript:void(0);" onclick='addTab("商品管理", "<%=contextPath%>/pages/E-commerce/itemQRCode.jsp?loginName=${loginName}","icon-sz",undefined,"provider");'>商品管理</a></li>
<%-- 							<li class="topmenu"><a style="font-weight: 100;text-align: center;" href="javascript:void(0);" onclick='addTab("网点间通信平台", "<%=contextPath%>/pages/business/shopCommunicate/shopCommunicate.jsp?loginName=${loginName}","icon-sz",undefined,"provider");'>网点间通信平台</a></li> --%>
						</ul>
					</center>
				</div>
			</td>
			<td colspan="2" style="background: #fff;" valign="top" height="100%">
				<table border="1" cellpadding="0" cellspacing="0" style="border-collapse: collapse; border-width: 0;" id="AutoNumber3">
					<div id="tabs" class="easyui-tabs">
						<div title="特别说明" style="padding:10px">
<!-- 							<ul> -->
<!-- 								<li class="speli" style="font-size: large;">签字板功能仅支持IE浏览器 -->
<!-- 	 								<span class="formInfo"> -->
<%-- 										<a href="<%=contextPath%>/pages/system/getToolTipMsgById.light" class="jTip" id="one" name="重要提示!">!</a> --%>
<!-- 									</span> -->
<!-- 								</li> -->
<!-- 							</ul> -->
								<div style="margin: 5px 5px;">你好：${nickName} [${userMode}]</div>
						</div>
					</div>
				</table>
			</td>
		</tr>
	</table>
	<div id="desktopFooterWrapper">
		<div id="desktopFooter">			
			<p align="right" style="margin: 1px 0px;" ><a target="_blank" style="margin: 0px 205px;font-weight: 900;">Copyright 光艺软件科技. 2013-2016 All Rights Reserved.</a></p>
		</div>
	</div>
	<div id="modfiyPassWordWindow">
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
				</table>
		</div>
		<div style="text-align:center;margin: 15px 0px;">
		  	<input id="saveBtn" type="button" value="保存" style="height: 30px;width: 49px;"/>
			<input id="cancelBtn" type="button" value="关闭" style="height: 30px;width: 49px;"/>
		</div>
	</div>
</body>


<div id="right_menu" class="easyui-menu" style="width:240px;">
	<input type="hidden" id="currentIndex"/>
	<input type="hidden" id="currentName"/>
    <div id="m-refresh">刷新<span style="padding: 115px;color: #BFBFBF;">Ctrl+R</span></div>
    <div class="menu-sep" style="margin: 0px 2px 5px 2px;"></div>
    <div id="m-close-currentLabel">关闭标签页</div>
    <div id="m-close-otherlabel">关闭其他标签页</div>
    <div id="m-close-all">关闭全部标签页</div>
    <div class="menu-sep" style="margin: 0px 2px 5px 2px;"></div>
    <div id="m-copyLabelName">复制标签页名称<span style="padding: 25px;color: #BFBFBF;">Ctrl+Shift+C</span></div>
    <div id="m-new-label">新建标签页<span style="padding: 49px;color: #BFBFBF;">Ctrl+Shift+N</span></div>
    <div id="m-favorites-label">收藏标签页<span style="padding: 49px;color: #BFBFBF;">Ctrl+Shift+V</span></div>
</div>
<div style="display: none;">
  <div>
    <form action="<%=contextPath%>/pages/system/welcome.light" method="post" id='logout'>
		<input type="submit" style="height: 30px;width: 60px;" class="l-btn-text" value="退出系统">
	</form>
  </div>
</div>
</html>
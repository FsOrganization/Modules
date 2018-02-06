<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/pages/menuStyle/menuButton/menuButtonStyle.css" />
<style type="text/css">
.speli {
	margin: 2px 25px;
}

.als-container {
	position: relative;
	width: 100%;
	margin: 0px auto;
	z-index: 0;
}

.als-viewport {
	position: relative;
	overflow: hidden;
	margin: 0px auto;
}

.als-wrapper {
	position: relative;
	list-style: none;
}

.als-item {
	position: relative;
	display: block;
	text-align: center;
	cursor: pointer;
	float: left;
}

.als-prev, .als-next {
	position: absolute;
	cursor: pointer;
	clear: both;
}
/*************************************
			 * specific styling for #demo3
			 ************************************/
#demo3 {
	margin: 40px auto;
}

#demo3 .als-item {
	margin: 0px 5px;
	padding: 4px 0px;
	min-height: 120px;
	min-width: 100px;
	text-align: center;
}

#demo3 .als-item img {
	display: block;
	margin: 0 auto;
	vertical-align: middle;
}

#demo3 .als-prev, #demo3 .als-next {
	top: 40px;
}

#demo3 .als-prev {
	left: 200px;
}

#demo3 .als-next {
	right: 200px;
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
<%-- <script type="text/javascript" src="<%=contextPath%>/pages/js/jquery.als-1.7.js"></script> --%>

<script type="text/javascript">
	function logout(){
		$('#logout').submit();
	}
	$(document).ready(function() {
// 		var list = $('#MenuData').val();
// 		alert(list.length);
// 		$.each(list, function(i,item){
// 			$('#MenuData').val(list);
// 		});
// 		$("#demo3").als({
// 			visible_items: 4,
// 			scrolling_items: 2,
// 			orientation: "horizontal",
// 			circular: "yes",
// 			autoscroll: "yes",
// 			interval: 4000
// 		});
	});
</script>  
</head>
<body id="indexbody" style="background: #fff;overflow: hidden;" topmargin="0">
	<input type="hidden" id="loginTag" value="${loginName}"/>
	<input type="hidden" id="MenuData" value="${sysMenuList}"/>
	<input type="hidden" id="redirectTag" value="${redirect}"/>
	<input type="hidden" id="lateFeeLimitUpper" value="${lateFeeLimitUpper}"/>
	<input type="hidden" id="lateDayLimit" value="${lateDayLimit}"/>
	<input type="hidden" id="memberLateFeeLimitUpper" value="${memberLateFeeLimitUpper}"/>
	<input type="hidden" id="memberLateDayLimit" value="${memberLateDayLimit}"/>
	<div style="overflow-y:hidden;overflow-x:hidden;">
		<div id="desktopHeader">
			<div id="desktopTitlebarWrapper"> <!-- id="desktopTitlebarWrapper" -->
				<div id="desktopTitlebar">
					<h2 class="tagline">
						<span class="taglineEm">
							<h2>源信幸福快递 </h2>
						</span>
					</h2>
					<div id="topNav">
						<ul class="menu-right" style="margin: 0px 16px 9px 16px;">
							<h4><li style='color: #6dbdc7;font-weight: 900;font-size: 12px;font-family: 微软雅黑;'>您好 <span>${loginName} </span><span>[${areaName}-></span><span>${shopName}]</span></li>
							<li><a style='color: #6dbdc7;font-weight: 900;font-size: 12px;font-family: 微软雅黑;' id="modfiyPassWord">修改密码</a></li>
							<li><a style='color: #6dbdc7;font-weight: 900;font-size: 12px;font-family: 微软雅黑;' onclick="logout();">退出系统</a></li></h4>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div style="overflow-y:auto;overflow-x:hidden;">
		<table id="mainBody" style="border-collapse:collapse;border:0px;cellpadding:0px;cellspacing:0px;width:100%;bordercolor:#111111;">
			<tr>
				<td style="background: #f2f2f2;height:520px;" valign="top">
					<div id="menuTdDiv">
					<c:forEach items="${sysMainMenuList}" var="mainMenu" varStatus="status">
						<div align="center">
							<center>
								<ul id="css3menu1" class="topmenu">
									<input type="checkbox" id="css3menu-switcher" class="switchbox">
									<label onclick="" class="switch" for="css3menu-switcher"></label>	
									<li class="topmenu">
										<a href="#" style="width:125px;height:25px;line-height:25px;cursor:default;">
											<img src="<%=contextPath%>${mainMenu.className}" alt=""/>${mainMenu.name}
										</a>
									</li>
									<c:if test="${mainMenu.childSysMenuNodes.size() != 0}">
										<c:forEach items="${mainMenu.childSysMenuNodes}" var="child" varStatus="status">
											<c:if test="${child.abstractLevel == 'Y'}">
												<li class="topmenu"><a href="#" style="width:125px;text-align: center;"><span>${child.name}</span></a>
													<ul>
														<c:forEach items="${child.childSysMenuNodes}" var="endNode" varStatus="status">
															<li>
																<a href="javascript:void(0);" onclick='addTab("${endNode.name}", "<%=contextPath%>${endNode.url}","${endNode.icon}",undefined,"infinished");'>${endNode.name}</a>
															</li>
	           											</c:forEach>
         											</ul>
												</li>
		                    				</c:if>
		                    				<c:if test="${child.abstractLevel == 'N'}">
												<li class="topmenu">
													<a style="font-weight: 100;text-align: center;" href="javascript:void(0);" onclick='addTab("${child.name}", "<%=contextPath%>${child.url}","${endNode.icon}",undefined,"statistics");'>${child.name}</a>
												</li>
		                    				</c:if>
							            </c:forEach>
                    				</c:if>
								</ul>
							</center>
						</div>
		            </c:forEach>
				</td>
				<td colspan="2" style="background: #fff;" valign="top" height="100%">
					<table border="1" cellpadding="0" cellspacing="0" style="border-collapse: collapse; border-width: 0;" id="AutoNumber3">
						<div id="tabs" class="easyui-tabs">
							<div title="特别说明" style="padding:10px">
								<div style="margin: 5px 5px;">你好：${nickName} [${userMode}]</div>
							</div>
						</div>
					</table>
				</td>
			</tr>
		</table>
	</div>
	<div id="modfiyPassWordWindow">
		<input type="hidden" name="loginName" id="loginName" value="${loginName}">
		<div style="margin: 10px 10px;text-align:center;font-size: 12px; " class="pwModfiy">
			<table>
				<tr>
					<td align="right">
						<label for="pw" style="font-size: 12px;">新密码：</label>
						<input type='password' id="pw" name="pw" style="width: 210px;height:30px;border-style: solid;border-color: antiquewhite;" placeholder="新密码">
					</td>
				<tr>
				<tr>
					<td align="right">
						<label for="confirmPw" style="font-size: 12px;">确认新密码：</label>
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
		<input type="submit" style="height: 30px;width: 60px;" class="l-btn-text btn" value="退出系统">
	</form>
  </div>
</div>
</html>
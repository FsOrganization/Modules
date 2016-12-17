<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	String contextPath = request.getContextPath();
%>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/pages/css/jquery-tooltip/css/global.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/css/base.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/pages/css/input.css" />

<%-- <link rel="stylesheet" href="<%=contextPath%>/common/ajaxMagicContainer/css/styles.css"> --%>

<script type="text/javascript" src="<%=contextPath%>/js/jquery-2.1.3.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/js/imagesloaded.pkgd.js"></script>

<%-- <script src="<%=contextPath%>/js/ModulesImport.js"></script> --%>
<script type="text/javascript" src="<%=contextPath%>/js/ModulesEasyUI.js"></script>

<%-- menu index mode --%>
<%-- <script type="text/javascript" src="<%=contextPath%>/common/index-menu-master/Gruntfile.js"></script> --%>

<script type="text/javascript" src="<%=contextPath%>/EasyUI_1.4.1/locale/easyui-lang-zh_CN.js"></script>

<script type="text/javascript" src="<%=contextPath%>/js/util.js"></script>
<script type="text/javascript" src="<%=contextPath%>/common/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath%>/pages/css/jquery-tooltip/js/jtip.js"></script> 
<script type="text/javascript" src="<%=contextPath%>/common/jQuery-Plugins/jquery.jqprint-0.3.js"></script>

<!-- page form validate -->
<%-- <script type="text/javascript" src="<%=contextPath%>/common/jQuery-validate/jquery.validate.js"></script> --%>

<!-- page block -->
<script src="<%=contextPath%>/common/JQuery-blockUI/JQuery.blockUI.2.70.0.js"></script>

<!-- business util js -->
<script src="<%=contextPath%>/pages/js/util.js"></script>
<!--easyui extend -->
<%-- <script src="<%=contextPath%>/pages/js/easyuiDatagridMoveRow.js"></script> --%>

<script src="<%=contextPath%>/js/expressDateUtil.js"></script>

<!--ajaxMagicContainer -->
<%-- <script type="text/javascript" src="<%=contextPath%>/common/ajaxMagicContainer/jquery.mockjax.js"></script> --%>

<!--导入导出 -->
<%-- <script type="text/javascript" src="<%=contextPath%>/js/namespace.js" type="text/javascript"></script> --%>
<%-- <script type="text/javascript" src="<%=contextPath%>/js/exceldp.js" type="text/javascript"></script> --%>

<script>
	var contextPath = "<%=contextPath%>";
//  	$(document).ready(function(){});
</script>
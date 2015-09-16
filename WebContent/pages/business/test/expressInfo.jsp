<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/base/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang='zh'>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
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
</style>
<script type="text/javascript">
</script>
<script src="<%=contextPath%>/pages/business/test/js/expressInfo.js" type="text/javascript"></script>
</head>
<body id="expressBody">
	<div style="margin: 4px;">
       	&nbsp;&nbsp;客户信息：<input id="queryParams" name="queryParams" style="width: 210px;height:30px;border-style: solid;border-color: antiquewhite;" placeholder="手机后四位、客户姓名、快件运单号">
       	&nbsp;&nbsp;快递服务商：<input id="expressServiceId" name="expressServiceId" style="width: 150px;border-style: solid;border-color: antiquewhite;" class="easyui-combobox">
       	&nbsp;&nbsp;开始日期：<input id="startDateId" name="startDateId" style="width: 120px;height:30px;border-style: solid;border-color: antiquewhite;" onclick="WdatePicker()" >
       	&nbsp;&nbsp;截止日期:<input id="endDateId" name="endDateId"  style="width: 120px;height:30px;border-style: solid;border-color: antiquewhite;" onclick="WdatePicker()" >
	</div>
	<table id="areaCodeGrid" class="easyui-datagrid" style="height: auto;"></table>
    <form id="downFile" action="<%=contextPath%>/pages/system/downExpressInfoByFilterConditions.light" method="post" style="height: inherit;display: none;">
    	<input type="hidden" id="down_endDate" name="down_endDate"/>
    	<input type="hidden" id="down_startDate" name="down_startDate"/>
    	<input type="hidden" id="down_queryParams" name="down_queryParams"/>
    	<input type="hidden" id="down_expressService" name="down_expressService"/>
    </form>
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
    <div id="signatureDetail" class="easyui-dialog" title="Complex Toolbar on Dialog" style="width:600px;height:430px;padding:10px;overflow: hidden;"
            data-options="buttons: '#dlg-buttons',closed: true">
            <div style="margin: 8px 5px;">
            	<img id="signatureImg" src=""></img>
                <span id="fileName" style="margin: 0px 80px;font-weight: 300;font-size: 18px;"></span>
            </div>
                
    </div>
    <div id="dlg-buttons">
        <a id="submitBtn" class="easyui-linkbutton">打印签名</a>
        <a id="close" class="easyui-linkbutton" onclick="javascript:$('#signatureDetail').dialog('close')">Close</a>
    </div>
</body>
</html>
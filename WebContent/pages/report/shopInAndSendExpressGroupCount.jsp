<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/base/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang='zh'>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/form-style/css/demo.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/form-style/css/style3.css" />
<script type="text/javascript" src="<%=contextPath%>/common/form-style/js/modernizr.custom.04022.js"></script>
<script src="<%=contextPath%>/pages/report/js/shopInAndSendExpressGroupCount.js" type="text/javascript"></script>
<style type="text/css">
</style>
<script type="text/javascript">
</script>
</head>
<body style="overflow-y: hidden;background: #f2f2f2;margin: 4px 0px;">
	<div style="margin: 4px;font-weight: 700;background: #f2f2f2;">
		<span>
       		&nbsp;&nbsp;请选择统计区域：<input id="statisticalArea" name="statisticalArea"/>
       	</span>
       	<span id="speCycle" style="display: none;">
       		&nbsp;&nbsp;统计周期：<input id="dateStyle" name="dateStyle" value="">
       	</span>
       	
       	<span id="speDate" style="display: none;">
       		&nbsp;&nbsp;开始日期：<input id="startDateId" name="startDateId" style="width: 120px;height:30px;border-style: solid;border-color: antiquewhite;" onclick="WdatePicker()" >
       		&nbsp;&nbsp;截止日期：<input id="endDateId" name="endDateId"  style="width: 120px;height:30px;border-style: solid;border-color: antiquewhite;" onclick="WdatePicker()" >
		</span>
		<span id="speQueryBtu" style="display: none;">
			<input id="queryBtu" name="queryBtu" value="查询" type="button" style="margin: 0px 5px;height: 30px;width: 40px;">
		</span>
		<span id="exportQueryExcel" style="display: none;">
			<input id="exportQueryExcelBtu" name="exportQueryExcelBtu" value="导出Excel" type="button" style="margin: 0px 5px;height: 30px;width: 80px;">
			<form id="downFile" action="<%=contextPath%>/pages/system/downShopInAndSendGroupCount.light" method="post" style="height: inherit;display: none;">
		    	<input type="hidden" id="down_type" name="down_type"/>
		    	<input type="hidden" id="down_code" name="down_code"/>
		    	<input type="hidden" id="down_startDate" name="down_startDate"/>
		    	<input type="hidden" id="down_endDate" name="down_endDate"/>
    		</form>
		</span>
	</div>
	<table id="shopNumberOfPeopleStatisticsGrid" style="height: auto;"></table>
</body>
</html>
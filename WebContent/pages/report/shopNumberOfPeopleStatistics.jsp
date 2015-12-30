<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/base/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang='zh'>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/form-style/css/demo.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/form-style/css/style3.css" />
<script type="text/javascript" src="<%=contextPath%>/common/form-style/js/modernizr.custom.04022.js"></script>
<script src="<%=contextPath%>/pages/report/js/shopNumberOfPeopleStatistics.js" type="text/javascript"></script>
<style type="text/css">
</style>
<script type="text/javascript">
</script>
</head>
<body style="background: #f2f2f2;">
	<div style="margin: 4px;font-weight: 700;background: #f2f2f2;">
		<span>
       		&nbsp;&nbsp;请选择统计区域：<input id="statisticalArea" name="statisticalArea"/>
       	</span>
       	<span id="exportQueryExcel" style="display: none;">
			<input id="exportQueryExcelBtu" name="exportQueryExcelBtu" value="导出Excel" type="button" style="margin: 0px 5px;height: 30px;width: 80px;">
			<form id="downFile" action="<%=contextPath%>/pages/system/downShopCustomerCount.light" method="post" style="height: inherit;display: none;">
		    	<input type="hidden" id="down_type" name="down_type"/>
		    	<input type="hidden" id="down_code" name="down_code"/>
    		</form>
		</span>
	</div>
	<table id="shopNumberOfPeopleStatisticsGrid" style="height: auto;"></table>
</body>
</html>
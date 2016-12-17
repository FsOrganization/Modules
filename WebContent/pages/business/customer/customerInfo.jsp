<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/base/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang='zh'>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/form-style/css/demo.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/form-style/css/style3.css" />
<%-- <link rel="stylesheet" type="text/css" href="<%=contextPath%>/css/bootstrap.min.css" /> --%>
<script type="text/javascript" src="<%=contextPath%>/common/form-style/js/modernizr.custom.04022.js"></script>
<script src="<%=contextPath%>/pages/business/customer/js/customerInfo.js" type="text/javascript"></script>
<%-- <link rel="stylesheet" type="text/css" href="<%=contextPath%>/css/bootstrap.min.css" /> --%>

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
[type="checkbox"]:not(:checked),[type="checkbox"]:checked {
  position: absolute;
  left: -9999px;
}
[type="checkbox"]:not(:checked) + label,
[type="checkbox"]:checked + label {
  position: relative;
  padding-left: 25px;
  cursor: pointer;
}

/* checkbox aspect */
[type="checkbox"]:not(:checked) + label:before,
[type="checkbox"]:checked + label:before {
  content: '';
  position: absolute;
  left:0; top: 2px;
  width: 14px; height: 14px;
  border: 1px solid #aaa;
  background: #f8f8f8;
  border-radius: 3px;
  box-shadow: inset 0 1px 3px rgba(0,0,0,.3)
}
/* checked mark aspect */
[type="checkbox"]:not(:checked) + label:after,
[type="checkbox"]:checked + label:after {
  content: '✔';
  position: absolute;
  top: 0; left: 4px;
  font-size: 12px;
  color: #D7520C;
  transition: all 0.1s cubic-bezier(1,.18,.01,.39) 0s;
  margin: 2px 0px;
}
/* checked mark aspect changes */
[type="checkbox"]:not(:checked) + label:after {
  opacity: 0;
  transform: scale(0);
}
[type="checkbox"]:checked + label:after {
  opacity: 1;
  transform: scale(1);
}
/* disabled checkbox */
[type="checkbox"]:disabled:not(:checked) + label:before,
[type="checkbox"]:disabled:checked + label:before {
  box-shadow: none;
  border-color: #bbb;
  background-color: #ddd;
}
[type="checkbox"]:disabled:checked + label:after {
  color: #999;
}
[type="checkbox"]:disabled + label {
  color: #aaa;
}
/* accessibility */
[type="checkbox"]:checked:focus + label:before,
[type="checkbox"]:not(:checked):focus + label:before {
  border: 1px dotted blue;
}

/* hover style just for information */
label:hover:before {
  border: 1px solid #4778d9!important;
}
</style>
<script type="text/javascript">
</script>

</head>
<body style="overflow-y: hidden;background: #f2f2f2;">
	<div style="margin: 0px;">
       	&nbsp;&nbsp;用户信息：<input id="queryParams" name="queryParams" class="inputQueryEle" placeholder="姓名、电话">
       	<span id="shopSpan" style="display: none;">
       		&nbsp;&nbsp;网点：<input id="expressServiceId" name="expressServiceId" style="width: 150px;border-style: solid;border-color: antiquewhite;" class="easyui-combobox">
       	</span>
<!--        	<div class="btn-group" style=""> -->
<!-- 	         <button id="importUsingClass" type="button" class="btn btn-link" title=""> -->
<!-- 	             <span class="glyphicon glyphicon-import" >导入</span> -->
<!-- 	         </button> -->
<!-- 	         <button id="exportUsingClass" type="button" class="btn btn-link" > -->
<!-- 	             <span class="glyphicon glyphicon-export">导出</span> -->
<!-- 	         </button> -->
	
<!-- 	         <button id="downloadTemplateUsingClass" type="button" class="btn btn-link"> -->
<!-- 	             <span class="glyphicon glyphicon-download">下载模板</span> -->
<!-- 	         </button> -->
<!--     	</div> -->
	</div>
	
	<table id="customerGrid"></table>
    <div id="addUser" class="container" style="min-height: 300px;overflow:hidden;">
			<section class="af-wrapper">
	            <h1></h1>
	            <label for="af-showreq" class="af-show" style="margin: -15px 40px;">*突出必填项</label>
				<input id="af-showreq" class="af-show-input" type="checkbox" name="showreq"/>
				<input type="hidden" name="customerId" id="customerId">
				<form class="af-form" id="af-form" novalidate style="margin: 16px -15px;">
<!-- 					<div class="af-outer af-required"> -->
<!-- 						<div class="af-inner"> -->
<!-- 						  <label for="shopCodeList">所属网点 :</label> -->
<%-- 						  <input id="shopCodeList" class="easyui-combobox" name="shopCodeList" data-options=" --%>
<%-- 								                url: '<%=contextPath%>/pages/system/getShopInfoForSelect.light', --%>
<!-- 								                method: 'get', -->
<!-- 								                valueField: 'id', -->
<!-- 								                textField: 'text', -->
<!-- 								                panelWitdh: 380, -->
<!-- 								                panelHeight: 260, -->
<!-- 								                width:280, -->
<!-- 								                height: 35, -->
<%-- 								                formatter: formatItem" /> --%>
<!-- 						</div> -->
<!-- 					</div> -->
					<div class="af-outer af-required">
						<div class="af-inner">
							<label for="shopName">姓名:</label>
							<input type="text" name="name" id="name" style="width: 279px;">
						</div>
					</div>
					<div class="af-outer">
						<div class="af-inner">
							<label for="gender">性别:</label>
							<input id="gender" class="easyui-combobox" name="gender" data-options="
								                url: '<%=contextPath%>/pages/system/pageconfig/getPageGender.light',
								                method: 'get',
								                valueField: 'value',
								                textField: 'text',
								                panelWitdh: 380,
								                panelHeight: 95,
								                width:280,
								                height: 35,
								                formatter: formatGender" />
						</div>
					</div>
					<div class="af-outer af-required">
						<div class="af-inner">
							<label for="ageSection">年龄段:</label>
							<input id="ageSection" class="easyui-combobox" name="ageSection" data-options="
								                url: '<%=contextPath%>/pages/system/pageconfig/getAgeSection.light',
								                method: 'get',
								                valueField: 'value',
								                textField: 'text',
								                panelWitdh: 380,
								                panelHeight: 95,
								                width:280,
								                height: 35,
								                formatter: formatAgeSection" />
						</div>
					</div>
					<div class="af-outer af-required">
						<div class="af-inner">
							<label for="whetherHaveCar">是否有车:</label>
							<input id="whetherHaveCar" class="easyui-combobox" name="whetherHaveCar" data-options="
								                url: '<%=contextPath%>/pages/system/pageconfig/getWhetherHaveCar.light',
								                method: 'get',
								                valueField: 'value',
								                textField: 'text',
								                panelWitdh: 380,
								                panelHeight: 70,
								                width:280,
								                height: 35,
								                formatter: formatWhetherHaveCar" />
						</div>
					</div>
					<div class="af-outer">
						<div class="af-inner">
							<label for="shopContacts">联系电话:</label>
							<input type="text" name="phoneNumber" id="phoneNumber" style="width: 279px;"/>
						</div>
					</div>
					<div class="af-outer">
						<div class="af-inner">
							<label for="address">地址:</label>
							<input type="text" name="address" id="address" style="width: 279px;"/>
						</div>
					</div>
					<div class="af-outer af-required">
						<div class="af-inner">
						  <label for="isInterest">是否已关注微信:</label>
						  <input type="checkbox" name="isInterest" value="Y" id="isInterest"/>
						  <label for="isInterest" style="margin: 8px 15px; font-size:12px;"></label>
						</div>
					</div>
				</form>
				<div style="text-align:center;">
				  	<input id="saveBtn" type="button" value="保存" style="height: 30px;width: 49px;"/>
					<input id="cancelBtn" type="button" value="关闭" style="height: 30px;width: 49px;"/>
				</div>
			</section>
        </div>
</body>
</html>
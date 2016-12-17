<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/base/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang='zh'>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/form-style/css/demo.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/form-style/css/style3.css" />
<script type="text/javascript" src="<%=contextPath%>/common/form-style/js/modernizr.custom.04022.js"></script>
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
<script src="<%=contextPath%>/pages/menu/js/menuSetUp.js" type="text/javascript"></script>
</head>
<body style="overflow-y: hidden;background: #f2f2f2;">
	<div style="margin: 0px;background: #f2f2f2;">
       	&nbsp;&nbsp;菜单名称：<input id="queryParams" name="queryParams" class="inputQueryEle" placeholder="区域名称">
	</div>
	<table id="menuGrid" style="height: auto;"></table>
    <div id="addMenu" class="container" style="min-height: 260px;overflow:hidden;">
			<section class="af-wrapper">
	            <h1></h1>
	            <label for="af-showreq" class="af-show" style="margin: -15px 40px;">*突出必填项</label>
				<input id="af-showreq" class="af-show-input" type="checkbox" name="showreq"/>
				<input type="hidden" name="id" id="id">
				<input type="hidden" name="level" id="level">
				<form class="af-form" id="af-form" novalidate style="margin: 16px -15px;">
					<div class="af-outer af-required">
						<div class="af-inner af-required">
							<label for="name">菜单名称:</label>
							<input type="text" name="name" id="name" style="width: 320px;">
						</div>
					</div>
					<div class="af-outer af-required">
						<div class="af-inner af-required">
							<label for="url">URL:</label>
							<input type="text" name="url" id="url" style="width: 320px;">
						</div>
					</div>
					<div class="af-outer">
						<div class="af-inner">
							<label for="displayOrder">排序号:</label>
							<input type="text" name="displayOrder" id="displayOrder" style="width: 320px;">
						</div>
					</div>
					<div class="af-outer af-required">
						<div class="af-inner">
						  <label for="abstractLevel">是否可点击:</label>
						  <input type="checkbox" name="abstractLevel" value="N" id="abstractLevel"/>
						  <label for="abstractLevel" style="margin: 8px 15px; font-size:12px;"></label>
						</div>
					</div>
					<div class="af-outer af-required">
						<div class="af-inner">
						  <label for="status">是否启用:</label>
						  <input type="checkbox" name="status" value="Y" id="status"/>
						  <label for="status" style="margin: 8px 15px; font-size:12px;"></label>
						</div>
					</div>
				</form>
				<div style="text-align:center;">
				  	<input id="saveBtn" type="button" value="保存" style="height: 30px;width: 49px;"/>
					<input id="cancelBtn" type="button" value="关闭" style="height: 30px;width: 49px;"/>
				</div>
			</section>
        </div>
        <div id="childMenuList" class="container" style="min-height: 260px;overflow:hidden;">
       		<input type="hidden" name="parentId" id="parentId">
			<table id="childMenuGrid" style="height: auto;"></table>
        </div>
        <div id="addChildMenu" class="container" style="min-height: 260px;overflow:hidden;">
			<section class="af-wrapper">
	            <h1></h1>
	            <label for="af-showreq" class="af-show" style="margin: -15px 40px;">*突出必填项</label>
				<input id="af-showreq" class="af-show-input" type="checkbox" name="showreq"/>
				<input type="hidden" name="childId" id="childId">
				<input type="hidden" name="childLevel" id="childLevel">
				<form class="af-form" id="af-form" novalidate style="margin: 16px -15px;">
					<div class="af-outer af-required">
						<div class="af-inner af-required">
							<label for="parentName">父级菜单:</label>
							<input type="text" name="parentName" id="parentName" style="border-style: solid;border-color: antiquewhite;" class="easyui-combobox">
						</div>
					</div>
					<div class="af-outer af-required">
						<div class="af-inner af-required">
							<label for="childName">菜单名称:</label>
							<input type="text" name="childName" id="childName" style="width: 320px;">
						</div>
					</div>
					<div class="af-outer af-required">
						<div class="af-inner af-required">
							<label for="childUrl">URL:</label>
							<input type="text" name="childUrl" id="childUrl" style="width: 320px;">
						</div>
					</div>
					<div class="af-outer">
						<div class="af-inner">
							<label for="childDisplayOrder">排序号:</label>
							<input type="text" name="childDisplayOrder" id="childDisplayOrder" style="width: 320px;">
						</div>
					</div>
					<div class="af-outer af-required">
						<div class="af-inner">
						  <label for="childAbstractLevel">是否可点击:</label>
						  <input type="checkbox" name="childAbstractLevel" value="N" id="childAbstractLevel"/>
						  <label for="childAbstractLevel" style="margin: 8px 15px; font-size:12px;"></label>
						</div>
					</div>
					<div class="af-outer af-required">
						<div class="af-inner">
						  <label for="childStatus">是否启用:</label>
						  <input type="checkbox" name="childStatus" value="Y" id="childStatus"/>
						  <label for="childStatus" style="margin: 8px 15px; font-size:12px;"></label>
						</div>
					</div>
				</form>
				<div style="text-align:center;">
				  	<input id="twoChildSaveBtn" type="button" value="保存" style="height: 30px;width: 49px;"/>
					<input id="twoChildCancelBtn" type="button" value="关闭" style="height: 30px;width: 49px;"/>
				</div>
			</section>
        </div>
        <div id="twoChildMenuList" class="container" style="min-height: 260px;overflow:hidden;">
       		<input type="hidden" name="childParentId" id="childParentId">
			<table id="twoChildMenuGrid" style="height: auto;"></table>
        </div>
        <div id="addThreeChildMenu" class="container" style="min-height: 260px;overflow:hidden;">
			<section class="af-wrapper">
	            <h1></h1>
	            <label for="af-showreq" class="af-show" style="margin: -15px 40px;">*突出必填项</label>
				<input id="af-showreq" class="af-show-input" type="checkbox" name="showreq"/>
				<input type="hidden" name="threeChildId" id="threeChildId">
				<input type="hidden" name="threeChildLevel" id="threeChildLevel">
				<form class="af-form" id="af-form" novalidate style="margin: 16px -15px;">
					<div class="af-outer af-required">
						<div class="af-inner af-required">
							<label for="twoParentName">父级菜单:</label>
							<input type="text" name="twoParentName" id="twoParentName" style="border-style: solid;border-color: antiquewhite;" class="easyui-combobox">
						</div>
					</div>
					<div class="af-outer af-required">
						<div class="af-inner af-required">
							<label for="threeChildName">菜单名称:</label>
							<input type="text" name="threeChildName" id="threeChildName" style="width: 320px;">
						</div>
					</div>
					<div class="af-outer af-required">
						<div class="af-inner af-required">
							<label for="threeChildUrl">URL:</label>
							<input type="text" name="threeChildUrl" id="threeChildUrl" style="width: 320px;">
						</div>
					</div>
					<div class="af-outer">
						<div class="af-inner">
							<label for="threeChildDisplayOrder">排序号:</label>
							<input type="text" name="threeChildDisplayOrder" id="threeChildDisplayOrder" style="width: 320px;">
						</div>
					</div>
					<div class="af-outer af-required">
						<div class="af-inner">
						  <label for="threeChildAbstractLevel">是否可点击:</label>
						  <input type="checkbox" name="threeChildAbstractLevel" value="N" id="threeChildAbstractLevel"/>
						  <label for="threeChildAbstractLevel" style="margin: 8px 15px; font-size:12px;"></label>
						</div>
					</div>
					<div class="af-outer af-required">
						<div class="af-inner">
						  <label for="threeChildStatus">是否启用:</label>
						  <input type="checkbox" name="threeChildStatus" value="Y" id="threeChildStatus"/>
						  <label for="threeChildStatus" style="margin: 8px 15px; font-size:12px;"></label>
						</div>
					</div>
				</form>
				<div style="text-align:center;">
				  	<input id="thirdLevelSaveBtn" type="button" value="保存" style="height: 30px;width: 49px;"/>
					<input id="thirdLevelCancelBtn" type="button" value="关闭" style="height: 30px;width: 49px;"/>
				</div>
			</section>
        </div>
</body>
</html>
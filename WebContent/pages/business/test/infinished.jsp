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

</style>

<script src="<%=contextPath%>/pages/business/test/js/infinished.js" type="text/javascript"></script>
</head>
<body style="overflow-y: hedden;" id="allBodyDiv" >
	<div style="width: auto; overflow: hidden;background: white;">
		<div style="margin: 4px;">
		       	&nbsp;&nbsp;批次号：<input id="query_batchNumber" name="query_batchNumber" style="width: 150px;height:30px;border-style: solid;border-color: antiquewhite;">
		</div>
		<table id="infinishedGrid" style="height: auto;"></table>
		<div id="detail" class="easyui-dialog" title="修改快件信息" style="width:460px;height:350px;padding:10px;overflow: hidden;"
	            data-options="
	                iconCls: 'icon-save',
	                toolbar: '#dlg-toolbar',
	                buttons: '#dlg-buttons',closed: true">
		    <form id="ff" action="<%=contextPath%>/pages/system/editDataById.light" method="post" class="af-form" style="height: inherit;">
				<div class="af-outer">
							<div class="af-inner">
								<label for="modify_logistics">快件运单号:</label>
								<input type="text" name="modify_logistics" id="modify_logistics" style="width: 220px;margin:-10px 0px;" readonly="readonly">
								<input type="hidden" id="id" />
								<span style="color:red;"> (*必填项)</span>
							</div>
				</div>
				<div class="af-outer">
							<div class="af-inner">
								<label for="modify_recipientName">收件人姓名:</label>
								<input type="text" name="modify_recipientName" id="modify_recipientName" style="width: 220px;margin:-10px 0px;">
								<span style="color:red;"> (*必填项)</span>
							</div>
				</div>
				<div class="af-outer">
							<div class="af-inner">
								<label for="modify_phoneNumber">手机号码:</label>
								<input type="text" name="modify_phoneNumber" id="modify_phoneNumber" style="width: 220px;margin:-10px 0px;">
								<span style="color:red;"> (*必填项)</span>
							</div>
				</div>
				<div class="af-outer">
							<div class="af-inner">
								<label for="modify_expressLocation">货位:</label>
								<input type="text" name="modify_expressLocation" id="modify_expressLocation" style="width: 220px;margin:-10px 0px;">
								<span style="color:red;"> (*必填项)</span>
							</div>
				</div>
			</form>
	    </div>
    </div>
    <div id="presentSelfForm" class="container" style="min-height: 300px;overflow:auto;">
			<section class="af-wrapper">
				<label id='showBatchNumber' class="af-show" style="margin: -14px 152px;width: 100px;">*显示批次号</label>
	            <label for="af-showreq" class="af-show" style="margin:-15px -45px;">*突出必填项</label>
				<input id="af-showreq" class="af-show-input" type="checkbox" name="showreq"/>
				<form class="af-form" id="af-form" novalidate style="margin: 16px -15px;">
					<div class="af-outer af-required">
						<div class="af-inner">
						  <label for="expressServiceId">快递服务商:</label>
						  <input id="expressServiceId" class="easyui-combobox" name="expressServiceId" data-options="
								                url: '<%=contextPath%>/pages/system/getExpressServiceProviderInfo.light',
								                method: 'get',
								                valueField: 'id',
								                textField: 'text',
								                panelWitdh: 380,
								                panelHeight: 260,
								                width:280,
								                height: 35,
								                formatter: formatItem" />
								                <span style="color:red;"> (*必填项)</span>
						</div>
					</div>
					<div class="af-outer">
						<div class="af-inner">
							<label for="logistics">快件运单号:</label>
							<input type="text" name="logistics" id="logistics" style="width: 279px;">
							<span style="color:red;"> (*必填项)</span>
						</div>
					</div>
				
					<div class="af-outer af-required">
						<div class="af-inner">
							<label for="phoneNumberphoneNumber">收件人手机号码:</label>
							<input type="text" name="phoneNumber" id="phoneNumber" style="width: 279px;">
							<span style="color:red;"> (*必填项)</span>
						</div>
					</div>
					
					<div class="af-outer af-required">
						<div class="af-inner">
							<label for="recipientName">收件人姓名:</label>
							<input type="text" name="recipientName" id="recipientName" style="width: 279px;"/>
							<span style="color:red;"> (*必填项)</span>
						</div>
					</div>
					
					<div class="af-outer af-required" id="expressLocationDiv">
						<div class="af-inner">
							<label for="expressType">货位:</label>
							<div style="padding: inherit;margin: -5px 0px;">
								<input type="radio" name="expressType" value="S" id="S"/> 上层
								<input type="radio" name="expressType" value="X" id="X"/> 下层
<!-- 								<input type="radio" name="expressType" value="X" id="X"/> 小件 -->
<!-- 								<input type="radio" name="expressType" value="Q" id="Q"/> 其他 -->
								<span style="  margin: 0px 20px;font-weight: 900;">货位号：<span id="expressLocationTitle" style="font-size: 22px;"></span></span>
<!-- 								<span style="color:red;"> (*必填项)</span> -->
							</div>
							<input type="hidden" name="expressLocation" id="expressLocation" style="width: 279px;">
						</div>
					</div>
					
					<div class="af-outer af-required" style="display: none;">
						<div class="af-inner">
						  <label for="input-country">地址:</label>
						  <input type="text" name="address" id="address" style="width: 279px;">
						</div>
					</div>
					
					<div style="display: none;" class="af-outer" id="batchNumberDiv">
						<div class="af-inner">
						  <label for="input-catname">批次号:</label>
						  <input type="text" name="batchNumber" id="batchNumber" style="width: 279px;">
						  <span style="color:red;"> (*必填项)</span>
						</div>
					</div>
				</form>
				<div style="text-align:center;">
				  	<input id="saveBtn" type="button" value="保存" style="height: 30px;width: 49px;"/>
<!-- 					<input id="cancelSelfBtn" type="button" value="取消" style="height: 30px;width: 49px;"/> -->
					<input id="cancelSelfBtn" type="button" value="关闭" style="height: 30px;width: 49px;"/>
				</div>
			</section>
        </div>
        
        <!-- 条码显示 -->
<!-- 	    <div id="imgDetail" class="easyui-dialog" title="Complex Toolbar on Dialog" style="width:400px;height:200px;padding:10px;" -->
<!-- 	            data-options="buttons: '#dlg-buttons',closed: true"> -->
<!-- 	            <div style="margin: 15px 10px;"> -->
<!-- 	            	<img id="barimg" src=""></img> -->
<!-- 	                <span id="fileName" style="margin: 2px 58px;letter-spacing: 7px;"></span> -->
<!-- 	            </div> -->
	                
<!-- 	    </div> -->
	    <div id="dlg-buttons">
	        <a id="submitBtn" class="easyui-linkbutton">Save</a>
	        <a id="close" class="easyui-linkbutton" onclick="javascript:$('#detail').dialog('close')">Close</a>
	    </div>
<!-- 	    <div id="dlg-buttons"> -->
<!-- 	        <a id="submitBtn" class="easyui-linkbutton">打印条码</a> -->
<!-- 	        <a id="close" class="easyui-linkbutton" onclick="javascript:$('#imgDetail').dialog('close')">Close</a> -->
<!-- 	    </div> -->
		<!-- 签字区域 -->
	<div id="signatureRegion" style="overflow: hidden;">
		<table width="90%" border="0" align="center" cellpadding="3"
			cellspacing="0">
			<tr class="style7">
				<td align="left">
					<object id="HWPenSign" name="HWPenSign"
						classid="clsid:E8F5278C-0C72-4561-8F7E-CCBC3E48C2E3" width="550"
						height="320"> 
					</object>
				</td>
			</tr>
		</table>
		<div style="margin: 2px 2px;position: absolute;">
		  <input id="button1" type="button" value="初始化设备" onclick ="initializationSignatureRegion();" style="margin: -1px; width: 130px; height: 35px;"/>
          <input id="button2" type="button" value="关闭设备" onclick ="closeSignatureRegion();" style="margin: -1px; width: 130px; height: 35px;"/>
          <input id="Button3" type="button" value="重新签名" onclick="clearSignatureRegion();" style="margin: -1px; width: 130px; height: 35px;"/>
          <input id="Button4" type="button" value="确认收件" onclick="signComplete();" style="margin: -1px; width: 150px; height: 35px;"/>
		</div>
	</div>
</body>
</html>
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
<script type="text/javascript">
</script>
<script src="<%=contextPath%>/pages/business/test/js/NewFile.js" type="text/javascript"></script>
</head>
<body style="overflow-y: hidden;background: white;">
	<div style="margin: 4px;">
       	&nbsp;&nbsp;客户信息：<input id="queryParams" name="queryParams" style="width: 210px;height:30px;border-style: solid;border-color: antiquewhite;" placeholder="手机后四位、客户姓名、快件运单号">
       	&nbsp;&nbsp;快递服务商：<input id="expressServiceId" name="expressServiceId" style="width: 150px;border-style: solid;border-color: antiquewhite;" class="easyui-combobox">
       	&nbsp;&nbsp;开始日期：<input id="startDateId" name="startDateId" style="width: 120px;height:30px;border-style: solid;border-color: antiquewhite;" onclick="WdatePicker()" >
       	&nbsp;&nbsp;截止日期:<input id="endDateId" name="endDateId"  style="width: 120px;height:30px;border-style: solid;border-color: antiquewhite;" onclick="WdatePicker()" >
	</div>
	<table id="areaCodeGrid" class="easyui-datagrid" style="height: auto;"></table>
	<div id="detail" class="easyui-dialog" title="修改快件信息" style="width:460px;height:350px;padding:10px;overflow: hidden;"
	            data-options="
	                iconCls: 'icon-save',
	                toolbar: '#dlg-toolbar',
	                buttons: '#dlg-buttons',closed: true">
		    <form id="ff" action="<%=contextPath%>/pages/system/editDataById.light" method="post" class="af-form" style="height: inherit;">
				<div class="af-outer">
							<div class="af-inner">
								<label for="input-title">快件运单号:</label>
								<input type="text" name="modify_logistics" id="modify_logistics" style="width: 220px;margin:-10px 0px;" readonly="readonly">
								<input type="hidden" id="id" />
							</div>
				</div>
				<div class="af-outer">
							<div class="af-inner">
								<label for="input-title">收件人姓名:</label>
								<input type="text" name="modify_recipientName" id="modify_recipientName" style="width: 220px;margin:-10px 0px;">
							</div>
				</div>
				<div class="af-outer">
							<div class="af-inner">
								<label for="input-title">手机号码:</label>
								<input type="text" name="modify_phoneNumber" id="modify_phoneNumber" style="width: 220px;margin:-10px 0px;">
							</div>
				</div>
				<div class="af-outer">
							<div class="af-inner">
								<label for="input-title">货位:</label>
								<input type="text" name="modify_expressLocation" id="modify_expressLocation" style="width: 220px;margin:-10px 0px;">
							</div>
				</div>
			</form>
	</div>
    <div id="dlg-buttons">
        <a id="modifyBtn" class="easyui-linkbutton">Save</a>
        <a id="close" class="easyui-linkbutton" onclick="javascript:$('#detail').dialog('close')">Close</a>
    </div>
    
	<!-- 条码显示 -->
    <div id="imgDetail" class="easyui-dialog" title="Complex Toolbar on Dialog" style="width:400px;height:200px;padding:10px;"
            data-options="buttons: '#dlg-buttons',closed: true">
            <div style="margin: 20px 70px;">
            	<img id="barimg" src=""></img>
                <span id="fileName" style="margin: inherit;"></span>
            </div>
                
    </div>
    <div id="dlg-buttons">
        <a id="submitBtn" class="easyui-linkbutton">打印条码</a>
        <a id="close" class="easyui-linkbutton" onclick="javascript:$('#imgDetail').dialog('close')">Close</a>
    </div>
    
    <!-- 签字区域 -->
    <div id="signatureRegion" style="overflow: hidden;">
    	<table width="90%" border="0" align="center" cellpadding="3" cellspacing="0">           
	       <tr class="style7">
	           <td  align="left">
	              <object id="HWPenSign"
	                    name="HWPenSign"
	                    classid="clsid:E8F5278C-0C72-4561-8F7E-CCBC3E48C2E3"
	                    width="550"
	                    height="320">
	              </object>
		   		</td>
	       </tr>
	    </table>
	    <div style="margin: 2px 2px;position: absolute;">
		  <input id="button1" type ="button" class="" value="初始化设备" onclick ="initializationSignatureRegion();" style="margin: -1px; width: 130px; height: 35px;"/>
          <input id="button2" type ="button" value="关闭设备" onclick ="closeSignatureRegion();" style="margin: -1px; width: 130px; height: 35px;"/>
          <input id="Button3" type="button" value="重新签名" onclick="clearSignatureRegion();" style="margin: -1px; width: 130px; height: 35px;"/>
          <input id="Button4" type="button" value="确认取件" onclick="signComplete();" style="margin: -1px; width: 150px; height: 35px;"/>
		</div>
	</div> 
</body>
</html>
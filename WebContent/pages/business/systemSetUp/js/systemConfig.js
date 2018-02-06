var operatingTag=null;
$(document).ready(function() {
		//输入框按回车
		$("#queryParams").bind("keydown",function(e){
			var keycode = e.which;
			if(keycode == 13){//输入回车判定
				searchExpressInfo();
				e.preventDefault();						
			}
		});
		
		$('#addSystemConfig').window({
			title:'参数选项',
		    width:580,
		    height:517,
		    modal:true,
		    closed:true,
		    left:340,    
		    top:30,
		    onBeforeClose:function(){
		    }
		}); 
		
		$("#cancelBtn").click(function(){
			$('#addSystemConfig').window('close');
		});
		$("#saveBtn").click(function() {
			saveForm();
		});
		
		$('#systemConfig').datagrid({
			dataType : 'json',
			url : contextPath + '/pages/system/config/getSystemConfigList.light',
			width : $(window).width()*0.99,
			height :($(window).height())*1,
			singleSelect : true,
			rownumbers : true,
			pagination : true,
			striped : true,
			idField : 'id',
			pageSize : 20,
			queryParams: {
				batchNumber: ''
			},
			toolbar: [
	        {
				text:'新增选项',
				iconCls: 'icon-add',
				handler: function(){
					clearFormData();
					addConfigOption();
				}
			},
<<<<<<< HEAD
//			{
//	            text: 
//	            	'&nbsp;&nbsp;参数名称：<input id="queryParams" name="queryParams" style="width: 150px;height:22px;" placeholder="请输入...">'
//	        },
//	        {
//				text:'查询',
//				iconCls: 'icon-search',
//				handler: function(){
//					searchExpressInfo();
//				}
//			}
			],
=======
			{
	            text: 
	            	'&nbsp;&nbsp;参数名称：<input id="queryParams" name="queryParams" style="width: 150px;height:22px;" placeholder="请输入...">'
	        },{
				text:'查询',
				iconCls: 'icon-search',
				handler: function(){
					searchExpressInfo();
				}
			}],
>>>>>>> d0b5484a9bee2dc897836974fbc92e4f813785b1
			columns : [ [ {
				field : 'configId',
				title : 'configId',
				width : 180,
				align : 'center',
				hidden : true
			},{
				field : 'configName',
				title : '参数名称',
				width : 180,
				align : 'center',
				hidden : false
			},{
				field : 'configCode',
				title : '参数编码',
				width : 220,
				align : 'center',
				hidden : false
			},{
				field : 'valueType',
				title : '参数类型',
				width : 120,
				align : 'center',
				hidden : false
			},{
				field : 'value',
				title : '值',
				width : 120,
				align : 'center',
				hidden : false
			},{
				field : 'serviceShopCode',
				title : '网点编号',
				width : 120,
				align : 'center',
				hidden : false
			},{
				field : 'status',
				title : '是否启用',
				width : 60,
				align : 'center',
				hidden : false,formatter : function(value, row, index) {
					if (row.status == '1') {
						return "<img title='已启用' style='width: 22px;' src='"+contextPath+"/pages/images/icon/check.png'/>";
					} else {
						return "<img title='未启用' style='width: 22px;' src='"+contextPath+"/pages/images/icon/unCheck.png'/>";
					}
				}
			}] ],
			onLoadSuccess : function(data) {
			},
			onLoadError : function() {
				parent.location.href=contextPath+'/pages/system/welcome.light';
			},
			onDblClickRow : function(rowIndex, rowData) {
				openWindow(rowIndex, rowData);
				operatingTag = true;
			},
			loadFilter : pagerFilter
		});
		
});
		

function getSelections() {
	var rows = $('#systemConfig').datagrid('getSelections');
	return rows;
}

function openWindow(rowIndex, rowData) {
	clearFormData();
	var id = rowData.id;
	var serviceShopCode = rowData.serviceShopCode;
	var configId = rowData.configId;
	var configName = rowData.configName;
	var configCode = rowData.configCode;
	var valueType = rowData.valueType;
	var value = rowData.value;
	var operator = rowData.operator;
	var isCheck = rowData.status;
	$('#id').val(id);
	$('#configId').val(configId);
	$('#configName').val(configName);
	$('#configCode').val(configCode);
	$('#valueType').val(valueType);
	$('#value').val(value);
	$('#operator').val(operator);
	$('#serviceShopCode').val(serviceShopCode);
	serviceShopCode
	if (isCheck === '1') {
		$("input[id='isCheck']").prop("checked",true);
	}
	
//	$('#shopCodeList').combobox('setValue', serviceShopCode);
	$('#addSystemConfig').window('open');
	
}

function addConfigOption() {
	$('#addSystemConfig').window('open');
}


function clearFormData() {
	$('#id').val('');
	$('#configId').val('');
	$('#configName').val('');
	$('#configCode').val('');
	$('#valueType').val('');
	$('#value').val('');
	$('#serviceShopCode').val('');
//	$('#value').combobox('setValue', '');
	$("input[id='isCheck']").prop("checked",false);
//	$("input[id='isOpenIM']").prop("checked",false);
}

function searchExpressInfo() {
	var queryParams = $('#queryParams').val();
	$('#systemConfig').datagrid("loadData", []);
	$('#systemConfig').datagrid("clearSelections");
	//		
	$('#systemConfig').datagrid({
		url : contextPath+ "/pages/system/config/getSystemConfigList.light?queryParams="+ queryParams 
	});

	var paper = $('#systemConfig').datagrid('getPager');
	$(paper).pagination('refresh', {
		pageNumber : 1
	});
	
}

function checkAdminLoginName(loginName) {
	if (loginName === 'admin') {
		$.messager.show({
            title:'提示',
            msg:'<div class="messager-icon messager-info"></div>'+loginName +'为管理员工号不能添加',
            timeout:3800,
            showType:'slide'
		});
		$('#saveBtn').hide();
		return true;
	} else {
		return false;
	}
}

function saveForm() {
	var value = $('#value').val();
	if (value == '' ) {
		$('#af-showreq').click();  
		return;
	}
	var id = $('#id').val();
	var configId = $('#configId').val();
	var serviceShopCode = $('#serviceShopCode').val();
	var configName = $('#configName').val();
	var configCode = $('#configCode').val();
	var valueType = $('#valueType').val();
	var value = $('#value').val();
	var isCheckTemp = "";
	if ($(":checked[name=isCheck]").val() == undefined) {
		isCheckTemp = "0";
	} else {
		isCheckTemp = $(":checked[name=isCheck]").val();
	}
	$.ajax({
		url : contextPath+"/pages/system/config/saveSystemConfig.light",
		type: "POST",
		dataType:'json',
		data:
		{
			"id":id,
			"configId":configId,
			"configName":configName,
			"configCode":configCode,
			"valueType":valueType,
			"value":value,
			"serviceShopCode":serviceShopCode,
			"status":isCheckTemp
		}, success : function(data) {
			$('#systemConfig').datagrid("reload");
			$('#addSystemConfig').window('close');
			$.messager.show({
                title:'提示',
                msg:'<div class="messager-icon messager-info"></div>'+data.msg,
                timeout:1500,
                showType:'slide'
			});
		}, error : function(data) {
			$.messager.show({
                title:'提示',
                msg:'<div class="messager-icon messager-info"></div>'+data.msg,
                timeout:1500,
                showType:'slide'
		  });
		  }
	});
	
}

function addSystemConfig() {
	$('#addSystemConfig').window('open');
}


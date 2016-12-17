var operatingTag=null;
$(document).ready(function() {
		$('#id').prop('readonly', true);
		$("#name").bind("keydown",function(e){
			var keycode = e.which;
			//输入回车判定
			if(keycode == 13){
				submitForm();
				e.preventDefault();						
			}
		});
		
		$("#submitBtn").click(function() {
			submitForm();
		});
		
		$("#saveBtn").click(function() {
			if (operatingTag) {
				modifyForm();
			} else {
				saveForm();
			}
		});
		$("#cancelBtn").click(function(){
			$('#addArea').window('close');
		});
		$('#addArea').window({
			title:'新增区域',
		    width:580,
		    height:240,
		    modal:true,
		    closed:true,
		    left:340,    
		    top:30,
		    onBeforeClose:function(){
		    }
		}); 

		$("#queryParams").bind("keydown",function(e){
			var keycode = e.which;
			if(keycode == 13){//输入回车判定
				searchExpressInfo();
				e.preventDefault();
			}
		});
		
		$('#areaGrid').datagrid({
			dataType : 'json',
			url : contextPath + '/pages/system/getAreaInfoList.light',//getNotOutExpressInfoByFilterConditions
			width : $(window).width() * 0.99,
			height :($(window).height()-32)*0.99,
			singleSelect : true,
			rownumbers : true,
			pagination : true,
			striped : true,
			idField : 'ID',
			pageSize : 20,
			queryParams: {
				batchNumber: ''
			},
			toolbar: [
//			{
//	            text: 
//	            	'&nbsp;&nbsp;开始日期：<input id="startDateId" name="startDateId" style="width: 100px;height:25px;" onclick="WdatePicker()" >&nbsp;&nbsp;'+
//	            	'&nbsp;&nbsp;截止日期:<input id="endDateId" name="endDateId"  style="width: 100px;height:25px;" onclick="WdatePicker()" >'+
//	            	'&nbsp;&nbsp;客户信息：<input id="queryParams" name="queryParams" style="width: 150px;height:25px;" placeholder="手机后四位、客户姓名">'+
//	            	'&nbsp;&nbsp;快递服务商：<input id="expressServiceId" name="expressServiceId" style="width: 150px;" class="easyui-combobox" value=2> '
//	        },
	        {
				text:'新增区域',
				iconCls: 'icon-add',
				handler: function(){
					clearFormData();
					addArea();
					operatingTag = false;
				}
			}],
			columns : [ [ {
				field : 'CODE',
				title : '区域编号',
				width : 150,
				align : 'center'
			},{
				field : 'NAME',
				title : '区域名称',
				width : 120,
				align : 'center',
				hidden : false
			},{  
				field : 'opara',
				title : '操作',
				width : 100, 
				align : 'center',
				hidden : true,
				formatter : function(value,row,index){
					return "<button style='width: inherit;' onclick=\"letExpressOutStorehouse("+row.ID+",'"+row.RECIPIENT_NAME+"','"+row.PHONE_NUMBER+"')\">添加网点</button>";
				}
			},{
				field : 'TYPE',
				title : '是否启用',
				width : 60,
				align : 'center',
				hidden : false,formatter : function(value, row, index) {
					if (row.TYPE == 1) {
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
	
function addArea() {
	$('#addArea').window('open');
}

function formatColumnTitle(value){
	if (value===2){
		return '顺丰';
	} else if (value===3){
		return '京东';
	}else if (value===4){
		return '圆通';
	}else if (value===5){
		return 'EMS';
	}else if (value===6){
		return '天天快递';
	}else if (value===7){
		return '申通';
	}else if (value===8){
		return '中通';
	}else if (value===9){
		return '韵达';
	}else if (value===10){
		return '中铁物流';
	}else if (value===11){
		return '宅急送';
	}else if (value===12){
		return '汇通';
	}else if (value===13){
		return '邮政';
	}else if (value===14){
		return '快捷';
	}else if (value===15){
		return '优速';
	}else if (value===16){
		return '其他';
	}else{
		return '未知';
	}
}

function getAreaCode() {
	$.ajax({
		url : contextPath+"/pages/system/getNewAreaCode.light",
		type: "POST",
		dataType:'json',
		sync:false,
		data:
		{},
		success : function(data) {
			$('#areaCode').val(data.CODE);
		}
	});
}

function saveForm() {
	//getAreaCode();
	var areaCode = $('#areaCode').val();
	var areaName = $('#areaName').val();
	var isCheckTemp = "";
	if ($(":checked[name=isCheck]").val() == undefined) {
		isCheckTemp = "NO";
	} else {
		isCheckTemp = $(":checked[name=isCheck]").val();
	}
	if (areaName == '') {
		$('#af-showreq').click();
		return;
	}

	$.ajax({
		url : contextPath+"/pages/system/addArea.light",
		type: "POST",
		dataType:'json',
		data:
		{
			"areaCode":areaCode,
			"areaName":areaName,
			"isCheck":isCheckTemp
		},
		success : function(data) {
			$('#areaGrid').datagrid("reload");
			$('#addArea').window('close');
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

function modifyForm() {
	var id = $('#areaId').val();
	var areaCode = $('#areaCode').val();
	var areaName = $('#areaName').val();
	var isCheckTemp = "";
	
	if ($(":checked[name=isCheck]").val() == undefined) {
		isCheckTemp = "NO";
	} else {
		isCheckTemp = $(":checked[name=isCheck]").val();
	}
	if (areaName == '') {
		$('#af-showreq').click();
		return;
	}

	$.ajax({
		url : contextPath+"/pages/system/modifyArea.light",
		type: "POST",
		dataType:'json',
		data:
		{
			"id":id,
			"areaCode":areaCode,
			"areaName":areaName,
			"isCheck":isCheckTemp
		},
		success : function(data) {
			$('#areaGrid').datagrid("reload");
			$('#addArea').window('close');
			$.messager.show({
                title:'提示',
                msg:'<div class="messager-icon messager-info"></div>'+data.msg,
                timeout:1500,
                showType:'slide'
			});
			clearFormData();
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

function getBarCode(LOGISTICS, RECIPIENT_NAME) {
	$.ajax({
		url : contextPath + "/pages/system/getBarCode.light",
		type : "POST",
		dataType : 'json',
		data : {
			"id" : LOGISTICS,
			"name" : RECIPIENT_NAME
		},
		success : function(data) {
			$('#barimg').attr('src', contextPath + data.PATH);
			$('#fileName').html(LOGISTICS);
			openBarCodeWindow(id);
		},
		error : function(data) {
			$.messager.show({
				title : '错误',
				msg : '<div class="messager-icon messager-info"></div>'
						+ data.PATH,
				timeout : 2000,
				showType : 'slide'
			});
		}
	});
}

function getSignatureCode(batchNumber, type) {
	$.ajax({
		url : contextPath + "/pages/system/getSignatureByBatchNumber.light",
		type : "POST",
		dataType : 'json',
		data : {
			"batchNumber" : batchNumber,
			"type" : type
		},
		success : function(data) {
			$('#signatureImg').attr("src",
					'data:image/jpg;base64,' + data.streamCode);
			openSignatureCodeWindow();
		},
		error : function(data) {
		}
	});
}

function openBarCodeWindow(id) {
	$('#imgDetail').dialog('open');
}

function openSignatureCodeWindow() {
	$('#signatureDetail').dialog('open');
}

function deleteRow(id, name) {
	$.ajax({
		url : contextPath + "/pages/system/deleteCode.light",
		type : "POST",
		dataType : 'json',
		data : {
			"id" : id,
			"name" : name
		},
		success : function(data) {
			// $.messager.show({
			// title:'提示',
			// msg:'<div class="messager-icon messager-info"></div>'+data.PATH,
			// timeout:3000,
			// showType:'slide'
			// });
			$('#barimg').attr('src', contextPath + data.PATH);
			// $('#fileName').html(id);
			openBarCodeWindow(id);
		},
		error : function(data) {
			$.messager.show({
				title : '错误',
				msg : '<div class="messager-icon messager-info"></div>'
						+ data.PATH,
				timeout : 2000,
				showType : 'slide'
			});
		}
	});
}

function getSelections() {
	var rows = $('#areaGrid').datagrid('getSelections');
	return rows;
}
function openWindow(rowIndex, rowData) {
	clearFormData();
	var id = rowData.ID;
	var name = rowData.NAME;
	var isCheck = rowData.TYPE;
	$('#areaId').val(id);
	$('#areaName').val(name);
	if (isCheck === '1') {
		$("input[id='isCheck']").prop("checked",true);
	}
	$('#addArea').window('open');
}

function clearFormData() {
	$('#areaCode').val('');
	$('#areaId').val('');
	$('#areaName').val('');
	$("input[id='isCheck']").prop("checked",false);
}

function submitForm() {
	$.ajax({
		url : contextPath + "/pages/system/editDataById.light",
		type : "POST",
		dataType : 'json',
		data : {
			"id" : $('#id').val(),
			"name" : $('#name').val()
		},
		success : function(data) {
			$('#areaGrid').datagrid('reload');
			$.messager.show({
				title : '提示',
				msg : '<div class="messager-icon messager-info"></div>'
						+ data.msg,
				timeout : 500,
				showType : 'slide'
			});
			$('#detail').dialog('close');
			clearFormData();
		},
		error : function(data) {
			$.messager.show({
				title : '提示',
				msg : '<div class="messager-icon messager-info"></div>'
						+ data.msg,
				timeout : 500,
				showType : 'slide'
			});
		}
	});
}

function searchExpressInfo() {
	var queryParams = $("#queryParams").val();
	queryParams = encodeURI(queryParams);
	var obj = {
		"queryParams" : queryParams
	};
	$('#areaGrid').datagrid("loadData", []);
	$('#areaGrid').datagrid("clearSelections");
	//		
	$('#areaGrid').datagrid({
		url : contextPath+ "/pages/system/queryAreaInfos.light?queryParams="+ queryParams 
	});

	var paper = $('#areaGrid').datagrid('getPager');
	$(paper).pagination('refresh', {
		pageNumber : 1
	});
	
}

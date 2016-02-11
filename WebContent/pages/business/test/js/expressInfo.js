var expressServiceMap = {};
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
		
		//输入框按回车
		$("#queryParams").bind("keydown",function(e){
			var keycode = e.which;
			if(keycode == 13){//输入回车判定
				searchExpressInfo();
				e.preventDefault();						
			}
		});
		
		initExpressServiceProviders();
		
		$('#expressServiceId').combobox({
			url : contextPath + "/pages/system/getExpressServiceProviderInfo.light",
			valueField : "id",
			textField : "text",
			panelWitdh : 180,
			panelHeight : 260,
			width : 180,
			height : 30,
			value : "",
			formatter :  function(row){
				var ip = $("#expressServiceId").parent().find('.combo').children().eq(1);
				var comb = $(this).combobox('options');
				$(ip).click(function(){
					$('#expressServiceId').combo('showPanel');	
				});
			    var s = '<span style="font-weight:bold">' + row.text + '</span><br/>' +
			            '<span style="color:#888">' + row.desc + '</span>';
			    return s;
			}
		});
		
		$('#areaCodeGrid').datagrid({
//			dataType : 'json',
			url : contextPath + '/pages/system/getExpressInfoByFilterConditions.light',//getNotOutExpressInfoByFilterConditions
			width : $(window).width(),
			height :($(window).height()-32)*0.99,
			singleSelect : true,
			rownumbers : true,
			pagination : true,
			striped : true,
			method : 'post',
			idField : 'ID',
			pageSize : 20,
//			queryParams: {
//				batchNumber: '',
//				endDate:'', 
//				startDate:'',
//				queryParams:'',
//				expressService:''
//			},
			toolbar: [
	        {
				text:'查询快件',
				iconCls: 'icon-search',
				handler: function(){
					searchExpressInfo();
				}
			},{
				text:'导出',
				iconCls: 'icon-search',
				handler: function(){
					exportExpressList();
				}
			}],
			columns : [ [ {
				field : 'LOGISTICS',
				title : '快件运单号',
				width : 150,
				align : 'center'
			},{
				field : 'RECIPIENT_NAME',
				title : '收件人',
				width : 120,
				align : 'center',
				hidden : false
			},{  field : 'showCustomeSignature',
				title : '取件签名',
				width : 100, 
				align : 'center',
				hidden : false,
				formatter : function(value,row,index) {
					if (row.TYPE == 1) {
						return "未取件";
					} else {
						return "<button onclick=\"getSignatureCode("+row.OUT_BATCH_NUMBER+",'1')\">取件人签名</button>";
					}
				}
			},{
				field : 'PHONE_NUMBER',
				title : '手机号码',
				width : 120,
				align : 'center',
				hidden : false
			},{
				field : 'EXPRESS_lOCATION',
				title : '货位',
				width : 120,
				align : 'center',
				hidden : false
			},{
				field : 'EXPRESS_SERVICE_ID',
				title : '快件服务商',
				width : 100,
				align : 'center',
				hidden : false,formatter : function(value, row, index) {
					return formatColumnTitle(value);
				}
			},{  field : 'showServiceSignature', 
				title : '快递服务商签名',
				width : 100, 
				align : 'center',
				hidden : false,
				formatter : function(value,row,index) {
					return "<button onclick=\"getSignatureCode("+row.BATCH_NUMBER+",'0')\">快递员签名</button>";
				}
			},{
				field : 'ADDRESS',
				title : '地址',
				width : 100,
				align : 'center',
				hidden : true
			},{
				field : 'OPERATOR',
				title : '操作人员',
				width : 100,
				align : 'center',
				hidden : false
			},{
				field : 'OPERA_TIME',
				title : '入库时间',
				width : 160,
				align : 'center',
				hidden : false
			},{
				field : 'OUT_OPERA_TIME',
				title : '出库时间',
				width : 180,
				align : 'center',
				hidden : false
			},{
				field : 'REMARK',
				title : '备注',
				width : 60,
				align : 'center',
				hidden : true
			},{
				field : 'BATCH_NUMBER',
				title : '入库批次号',
				width : 70,
				align : 'center',
				hidden : false
			},{
				field : 'OUT_BATCH_NUMBER',
				title : '出库批次号',
				width : 70,
				align : 'center',
				hidden : false
			},{
				field : 'TYPE',
				title : '快件状态',
				width : 80,
				align : 'center',
				hidden : false,
				formatter : function(value, row, index) {
					if(value == 1) {
						return '未取件';
					} else if(value = -1){
						return '已取件';
					} else {
						return value;
					}
				}
			},{  field : 'showBarCode', 
				title : '查看条码',
				width : 100, 
				align : 'center',
				hidden : true,
				formatter : function(value,row,index) {
					return "<button onclick=\"getBarCode("+row.LOGISTICS+",'"+row.RECIPIENT_NAME+"')\">查看条码</button>";
				}
			}] ],
			onLoadSuccess : function(data) {
				$(".pagination-info").css("margin","-2px 20px");
			},
			onDblClickRow : function(rowIndex, rowData) {
//				openWindow(rowIndex, rowData);
			}
		});
	});
	
function initExpressServiceProviders() {
	$.ajax({
		url : contextPath + "/pages/system/initExpressServiceProviders.light",
		type : "POST",
		dataType : 'json',
		sync:false,
		data : {
			"shop_code" : ""
		},
		success : function(data) {
			$.each(data, function(i) {    
		           //alert(data[i].text);
				expressServiceMap[data[i].id] = data[i].text;
		     });
		},error : function(data) {
			parent.location.href=contextPath+'/pages/system/welcome.light';
		}
	});
}

function formatColumnTitle(value){
	return expressServiceMap[value];
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
		block("expressBody", null,900);
		$.ajax({
			url : contextPath + "/pages/system/getSignatureByBatchNumber.light",
			type : "POST",
			dataType : 'json',
			data : {
				"batchNumber" : batchNumber,
				"type" : type
			},
			success : function(data) {
				$('#signatureImg').attr("src", 'data:image/jpg;base64,'+data.streamCode);
				//closeSignatureCodeWindow();
				openSignatureCodeWindow();
				unblock("expressBody");
			},
			error : function(data) {
				unblock("expressBody");
			}
		});
	}
	


function openBarCodeWindow(id) {
	$('#imgDetail').dialog('open');
}

function openSignatureCodeWindow() {
	$('#signatureDetail').dialog('open');
}

function closeSignatureCodeWindow() {
	$('#signatureDetail').dialog('close');
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
			// 				$.messager.show({
			// 	                title:'提示',
			// 	                msg:'<div class="messager-icon messager-info"></div>'+data.PATH,
			// 	                timeout:3000,
			// 	                showType:'slide'
			// 				});
			$('#barimg').attr('src', contextPath + data.PATH);
			// 				$('#fileName').html(id);
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
	var rows = $('#areaCodeGrid').datagrid('getSelections');
	return rows;
}
function openWindow(rowIndex, rowData) {
	var id = rowData.id;
	var name = rowData.patient_name;
	$('#id').val(id);
	$('#name').val(name);
	$('#detail').dialog('open');
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
			$('#areaCodeGrid').datagrid('reload');
			$.messager.show({
				title : '提示',
				msg : '<div class="messager-icon messager-info"></div>'
						+ data.msg,
				timeout : 500,
				showType : 'slide'
			});
			$('#detail').dialog('close');
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

//
function searchExpressInfo() {
	var endDate = $("#endDateId").val();
	var startDate = $("#startDateId").val();
	var queryParams = $("#queryParams").val();
	queryParams = encodeURI(queryParams);
	var expressService = $("#expressServiceId").combobox('getValue');
	var obj = {
		"endDate" : endDate,
		"startDate" : startDate,
		"queryParams" : queryParams,
		"expressService" : expressService
	};
	$('#areaCodeGrid').datagrid("loadData", []);
	$('#areaCodeGrid').datagrid("clearSelections");
	//		
	$('#areaCodeGrid').datagrid({
		url : contextPath
				+ "/pages/system/getExpressInfoByFilterConditions.light?endDate="+ endDate 
				+ "&startDate=" + startDate
				+ "&queryParams=" + queryParams
				+ "&expressService=" + expressService
	});
	var paper = $('#areaCodeGrid').datagrid('getPager');
	$(paper).pagination('refresh', {
		pageNumber : 1
	});
	
}

function exportExpressList() {
	var endDate = $("#endDateId").val();
	var startDate = $("#startDateId").val();
	var queryParams = $("#queryParams").val();
	queryParams = encodeURI(queryParams);
	var expressService = $("#expressServiceId").combobox('getValue');
	if(endDate==''&& startDate==''&&queryParams==''&&expressService=='') {
		$.messager.show({
			title : '提示',
			msg : '<div class="messager-icon messager-info"></div>'+'请选择查询条件',
			timeout : 3800,
			showType : 'slide'
		});
		return ;
	}
	$("#down_endDate").val(endDate);
	$("#down_startDate").val(startDate);
	$("#down_queryParams").val(queryParams);
	$("#down_expressService").val(expressService);
	$("#downFile").submit();
}

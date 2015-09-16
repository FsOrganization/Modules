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
		
		$("#saveBtn").click(function() {
			if (operatingTag) {
				modifyForm();
			} else {
				saveForm();
			}
		});
		
		$('#addExpressServiceProvider').window({
			title:'新增快递服务商',
		    width:580,
		    height:302,
		    modal:true,
		    closed:true,
		    left:340,    
		    top:30,
		    onBeforeClose:function(){
		    }
		}); 
		
		$("#cancelBtn").click(function(){
			$('#addExpressServiceProvider').window('close');
		});
		
		//输入框按回车
		$("#queryParams").bind("keydown",function(e){
			var keycode = e.which;
			if(keycode == 13){
				searchExpressInfo();
				e.preventDefault();						
			}
		});
		
		$('#expressServiceProviderGrid').datagrid({
			dataType : 'json',
			url : contextPath + '/pages/system/getExpressServiceProviderList.light',
			width : $(window).width() * 1,
			height :($(window).height()) * 0.99,
			singleSelect : true,
			rownumbers : true,
			pagination : true,
			striped : true,
			idField : 'ID',
			pageSize : 30,
			queryParams: {
				batchNumber: ''
			},
			toolbar: [
	        {
				text:'新增服务商',
				iconCls: 'icon-search',
				handler: function(){
					clearFormData();
					addExpressServiceProvider();
					operatingTag = false;
				}
			}],
			columns : [ [{
				field : 'NAME',
				title : '服务商名称',
				width : 150,
				align : 'center'
			},{
				field : 'CONTACTS',
				title : '联系人',
				width : 150,
				align : 'center',
				hidden : true
			},{
				field : 'PHONE_NUMBER',
				title : '联系电话',
				width : 150,
				align : 'center',
				hidden : true
			},{
				field : 'ORDER_BY',
				title : '顺序',
				width : 150,
				align : 'center'
			},{
				field : 'REMARK',
				title : '备注',
				width : 150,
				align : 'center'
			},{
				field : 'TYPE',
				title : '是否启用',
				width : 60,
				align : 'center',
				hidden : false,
				formatter : function(value, row, index) {
					if (row.TYPE == 1) {
						return "<img title='已启用' style='width: 22px;' src='"+contextPath+"/pages/images/icon/check.png'/>";
					} else {
						return "<img title='未启用' style='width: 22px;' src='"+contextPath+"/pages/images/icon/unCheck.png'/>";
					}
				}
			}] ],
			onLoadSuccess : function(data) {

			},
			onDblClickRow : function(rowIndex, rowData) {
				openWindow(rowIndex, rowData);
				operatingTag = true;
			},
			loadFilter : pagerFilter
		});
	});

function clearFormData() {
	$('#expressServiceProviderId').val('');
	$('#name').val('');
	$('#contacts').val('');
	$('#phoneNumber').val('');
	$('#orderBy').val('');
	$('#remark').val('');
	$("input[id='isCheck']").prop("checked",true);
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
				$('#signatureImg').attr("src", 'data:image/jpg;base64,'+data.streamCode);
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
	var rows = $('#expressServiceProviderGrid').datagrid('getSelections');
	return rows;
}
function openWindow(rowIndex, rowData) {
	clearFormData();
	var id = rowData.ID;
	var name = rowData.NAME;
	var contacts = rowData.CONTACTS;
	var isCheck = rowData.TYPE;
	var remark = rowData.REMARK;
	var phoneNumber = rowData.PHONE_NUBMER;
	var orderBy = rowData.ORDER_BY;

	$('#expressServiceProviderId').val(id);
	$('#name').val(name);
	$('#contacts').val(contacts);
	$('#phoneNumber').val(phoneNumber);
	$('#orderBy').val(orderBy);
	if (isCheck === '1') {
		$("input[id='isCheck']").prop("checked",true);
	}
	$('#remark').val(remark);
	$('#addExpressServiceProvider').window('open');
}

function getShopCode() {
	$.ajax({
		url : contextPath+"/pages/system/getNewShopCode.light",
		type: "POST",
		dataType:'json',
		sync:false,
		data:
		{},
		success : function(data) {
			$('#shopCode').val(data.CODE);
		}
	});
}

function modifyForm() {
	var id = $('#expressServiceProviderId').val();
	var name = $('#name').val();
	var contacts = $('#contacts').val();
	var phoneNumber = $('#phoneNumber').val();
	var orderBy = $('#orderBy').val();
	var remark = $('#remark').val();
	
	var isCheckTemp = "";
	if ($(":checked[name=isCheck]").val() == undefined) {
		isCheckTemp = "NO";
	} else {
		isCheckTemp = $(":checked[name=isCheck]").val();
	}

	$.ajax({
		url : contextPath+"/pages/system/modifyExpressServiceProvider.light",
		type: "POST",
		dataType:'json',
		data:
		{
			"id":id,
			"name":name,
			"contacts":contacts,
			"phoneNumber":phoneNumber,
			"orderBy":orderBy,
			"remark":remark,
			"isCheck":isCheckTemp
		},
		success : function(data) {
			$('#expressServiceProviderGrid').datagrid("reload");
			$('#addExpressServiceProvider').window('close');
			$.messager.show({
                title:'提示',
                msg:'<div class="messager-icon messager-info"></div>'+data.msg,
                timeout:1500,
                showType:'slide'
			});
			clearFormData();
		},
		error : function(data) {
			$.messager.show({
                title:'提示',
                msg:'<div class="messager-icon messager-info"></div>'+data.msg,
                timeout:1500,
                showType:'slide'
		  });
		  }
	});
	
}


function saveForm() {
	var id = $('#expressServiceProviderId').val();
	var name = $('#name').val();
	var contacts = $('#contacts').val();
	var phoneNumber = $('#phoneNumber').val();
	var orderBy = $('#orderBy').val();
	var remark = $('#remark').val();
	
	var isCheckTemp = "";
	if ($(":checked[name=isCheck]").val() == undefined) {
		isCheckTemp = "NO";
	} else {
		isCheckTemp = $(":checked[name=isCheck]").val();
	}

	$.ajax({
		url : contextPath+"/pages/system/addExpressServiceProvider.light",
		type: "POST",
		dataType:'json',
		data:
		{
			"name":name,
			"contacts":contacts,
			"phoneNumber":phoneNumber,
			"orderBy":orderBy,
			"remark":remark,
			"isCheck":isCheckTemp
		},
		success : function(data) {
			$('#expressServiceProviderGrid').datagrid("reload");
			$('#addExpressServiceProvider').window('close');
			$.messager.show({
                title:'提示',
                msg:'<div class="messager-icon messager-info"></div>'+data.msg,
                timeout:1500,
                showType:'slide'
			});
			clearFormData();
		},
		error : function(data) {
			$.messager.show({
                title:'提示',
                msg:'<div class="messager-icon messager-info"></div>'+data.msg,
                timeout:1500,
                showType:'slide'
		  });
		  }
	});
	
}

//查询患者信息
function searchExpressInfo() {
	var queryParams = $("#queryParams").val();
	queryParams = encodeURI(queryParams);
	var obj = {
		"queryParams" : queryParams
	};
	$('#expressServiceProviderGrid').datagrid("loadData", []);
	$('#expressServiceProviderGrid').datagrid("clearSelections");
	//		
	$('#expressServiceProviderGrid').datagrid({
		url : contextPath+ "/pages/system/queryShopInfos.light?queryParams="+ queryParams 
	});

	var paper = $('#expressServiceProviderGrid').datagrid('getPager');
	$(paper).pagination('refresh', {
		pageNumber : 1
	});
	
}

function addExpressServiceProvider() {
	$('#addExpressServiceProvider').window('open');
}

function formatItem(row){
	var ip = $("#arceCodeList").parent().find('.combo').children().eq(1);
	var comb = $(this).combobox('options');
	$(ip).click(function(){
		$('#arceCodeList').combo('showPanel');	
	});
    var s = '<span style="font-weight:bold">' + row.desc + '</span><br/>' +
            '<span style="color:#888">' + row.text + '</span>';
    return s;
}

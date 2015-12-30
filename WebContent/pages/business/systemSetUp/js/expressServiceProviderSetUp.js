var operatingTag=null;
var operatingContactsTag=null;
var loginName = null;
var textMsg = null;
$(document).ready(function() {
		loginName = getUrlParam("loginName");
		if (loginName === 'admin') {
			textMsg = "新增服务商";
		} else {
			textMsg = "普通工号无法新增或修改服务商基础信息";
		}
		$("#saveBtn").click(function() {
			if (operatingTag) {
				modifyForm();
			} else {
				saveForm();
			}
		});
		
		$("#saveContactsBtn").click(function() {
			if (operatingContactsTag) {
				modifyContactsForm();
			} else {
				saveContactsForm();
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

		$('#addServiceProviderContacts').window({
			title:'新增联系人',
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
		
		$("#cancelContactsBtn").click(function(){
			$('#addServiceProviderContacts').window('close');
		});
		
		//输入框按回车
		$("#queryParams").bind("keydown",function(e){
			var keycode = e.which;
			if(keycode == 13){
				searchExpressInfo();
				e.preventDefault();						
			}
		});
		
		$('#expressServiceProviderContactsGrid').datagrid({
			dataType : 'json',
//			url : contextPath + '/pages/system/getExpressServiceProviderList.light',
//			width : $(window).width() * 0.33,
			height :268,
			singleSelect : true,
			rownumbers : true,
			pagination : true,
			striped : true,
			idField : 'ID',
			pageSize : 20,
			toolbar: [
	        {
				text:'新增联系人',
				iconCls: 'icon-search',
				handler: function(){
					clearContactsFormData();
					addServiceProviderContacts();
					operatingContactsTag = false;
				}
			}],
			columns : [ [{
				field : 'NAME',
				title : '联系人',
				width : 110,
				align : 'center'
			},{
				field : 'PHONE_NUMBER',
				title : '联系电话',
				width : 120,
				align : 'center',
			},{
				field : 'REMARK',
				title : '备注',
				width : 240,
				align : 'center'
			},{
				field : 'opara',
				title : '删除',
				width : 60,
				align : 'center',
				formatter : function(value, row, index) {
					return "<button style='width: inherit;' id='deleteContacts' onclick=\"deleteProviderContactsById('"+row.ID+"')\">删除</button>";
				}
			}] ],
			onLoadSuccess : function(data) {

			},
			onLoadError : function() {
				parent.location.href=contextPath+'/pages/system/welcome.light';
			},
			onDblClickRow : function(rowIndex, rowData) {
				openWindowwithContacts(rowIndex, rowData);
				operatingContactsTag = true;
			},
			loadFilter : pagerFilter
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
			pageSize : 20,
			queryParams: {
				batchNumber: ''
			},
			toolbar: [
	        {
				text:textMsg,
				iconCls: 'icon-search',
				handler: function(){
					if (loginName === 'admin') {
						clearFormData();
						addExpressServiceProvider();
						operatingTag = false;
					} 
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
			},{
				field : 'opara',
				title : '联系人',
				width : 120,
				align : 'center',
				hidden : false,
				formatter : function(value, row, index) {
					return "<button style='width: inherit;' id='contacts' onclick=\"getContacts('"+row.ID+"','"+row.NAME+"')\">查看联系人信息</button>";

				}
			}] ],
			onLoadSuccess : function(data) {

			},
			onLoadError : function() {
				parent.location.href=contextPath+'/pages/system/welcome.light';
			},
			onDblClickRow : function(rowIndex, rowData) {
				if (loginName === 'admin') {
					openWindow(rowIndex, rowData);
					operatingTag = true;
				}
				
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

function clearContactsFormData() {
//	$('#providerId').val('');
	$('#providerContacts').val('');
	$('#providerPhoneNumber').val('');
	$('#providerRemark').val('');
}

function deleteProviderContactsById(id) {
	$.ajax({
		url : contextPath + "/pages/system/deleteProviderContactsById.light",
		type : "POST",
		dataType : 'json',
		data : {
			"id" : id,
		},
		success : function(data) {
			$('#expressServiceProviderContactsGrid').datagrid("reload");
		},
		error : function(data) {
		}
	});
}

function getContacts(providerId,name) {
	$('#expressServiceProviderContacts').show();
	$('#expressServiceProviderContactsGrid').datagrid({
		url : contextPath+ "/pages/system/queryExpressServiceProviderContactsList.light",
		queryParams: {
			providerId: providerId
		}
	});
	
	$('#providerId').val(providerId);
	$('#expressServiceProviderContacts').window({
		title:name+'->联系人列表',
	    width:580,
	    height:302,
	    modal:true,
	    closed:true,
	    left:340,    
	    top:30,
	    onBeforeClose:function(){
	    }
	});
	$('#expressServiceProviderContacts').window('open');
//	unblock("expressServiceProviderContactsGrid");
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

function openWindowwithContacts(rowIndex, rowData) {
	clearContactsFormData();
	var id = rowData.ID;
	var name = rowData.NAME;
	var remark = rowData.REMARK;
	var phoneNumber = rowData.PHONE_NUMBER;

	$('#expressServiceProviderContactsId').val(id);
	$('#providerContacts').val(name);
	$('#providerPhoneNumber').val(phoneNumber);
	$('#providerRemark').val(remark);
	$('#addServiceProviderContacts').window('open');
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

function modifyContactsForm() {
	var id = $('#expressServiceProviderContactsId').val();
	var providerContacts = $('#providerContacts').val();
	var providerPhoneNumber = $('#providerPhoneNumber').val();
	var providerRemark = $('#providerRemark').val();
	if ($.trim(providerContacts).length == 0) {
		$.messager.show({
            title:'提示',
            msg:'<div class="messager-icon messager-info"></div>'+'请填写联系人姓名',
            timeout:3800,
            showType:'slide'
		});
		return;
	}
	if (!isPhoneNmuber(providerPhoneNumber)) {
		$.messager.show({
            title:'提示',
            msg:'<div class="messager-icon messager-info"></div>'+'手机或座机号码填写不正确',
            timeout:3800,
            showType:'slide'
		});
		return;
	}
	$.ajax({
		url : contextPath+"/pages/system/modifyServiceProviderContacts.light",
		type: "POST",
		dataType:'json',
		data:
		{
			"id":id,
			"providerContacts":providerContacts,
			"providerPhoneNumber":providerPhoneNumber,
			"providerRemark":providerRemark
		},
		success : function(data) {
			$('#expressServiceProviderContactsGrid').datagrid("reload");
			$('#addServiceProviderContacts').window('close');
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

function saveContactsForm() {
	var providerContacts = $('#providerContacts').val();
	var providerPhoneNumber = $('#providerPhoneNumber').val();
	var providerRemark = $('#providerRemark').val();
	var providerId = $('#providerId').val();
	if ($.trim(providerContacts).length == 0) {
		$.messager.show({
            title:'提示',
            msg:'<div class="messager-icon messager-info"></div>'+'请填写联系人姓名',
            timeout:3800,
            showType:'slide'
		});
		return;
	}
	if (!isPhoneNmuber(providerPhoneNumber)) {
		$.messager.show({
            title:'提示',
            msg:'<div class="messager-icon messager-info"></div>'+'手机或座机号码填写不正确',
            timeout:3800,
            showType:'slide'
		});
		return;
	}
	
	$.ajax({
		url : contextPath+"/pages/system/addServiceProviderContacts.light",
		type: "POST",
		dataType:'json',
		data:
		{
			"providerContacts":providerContacts,
			"providerPhoneNumber":providerPhoneNumber,
			"providerRemark":providerRemark,
			"providerId":providerId
		},
		success : function(data) {
			$('#expressServiceProviderContactsGrid').datagrid("reload");
			$('#addServiceProviderContacts').window('close');
			$.messager.show({
                title:'提示',
                msg:'<div class="messager-icon messager-info"></div>'+data.msg,
                timeout:1500,
                showType:'slide'
			});
			clearContactsFormData();
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

function addServiceProviderContacts() {
	$('#addServiceProviderContacts').window('open');
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

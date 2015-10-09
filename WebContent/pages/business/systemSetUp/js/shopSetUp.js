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
		
		$('#addShop').window({
			title:'新增网点',
		    width:580,
		    height:462,
		    modal:true,
		    closed:true,
		    left:340,    
		    top:30,
		    onBeforeClose:function(){
		    }
		}); 
		
		$("#cancelBtn").click(function(){
			$('#addShop').window('close');
		});
		
		//输入框按回车
		$("#queryParams").bind("keydown",function(e){
			var keycode = e.which;
			if(keycode == 13){
				searchExpressInfo();
				e.preventDefault();						
			}
		});
		
		$('#shopGrid').datagrid({
			dataType : 'json',
			url : contextPath + '/pages/system/getShopInfoList.light',//getNotOutExpressInfoByFilterConditions
			width : $(window).width() * 1,
			height :($(window).height()-30)*0.99,
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
				text:'新增网点',
				iconCls: 'icon-search',
				handler: function(){
					addShop();
					operatingTag = false;
				}
			}],
			columns : [ [ {
				field : 'AREA_CODE',
				title : '区域编号',
				width : 150,
				align : 'center'
			},{
				field : 'AREA_NAME',
				title : '区域名称',
				width : 120,
				align : 'center',
				hidden : false
			},{
				field : 'SHOP_CODE',
				title : '网点编号',
				width : 120,
				align : 'center',
				hidden : false
			},{
				field : 'NAME',
				title : '网点名称',
				width : 150,
				align : 'center'
			},{
				field : 'SHOP_ADDRESS',
				title : '网点地址',
				width : 150,
				align : 'center'
			},{
				field : 'SHOP_CONTACTS',
				title : '网点联系人',
				width : 150,
				align : 'center'
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

function clearFormData() {
	$('#shopName').val('');
	$('#shopAddress').val('');
	$('#areaName').val('');
	$('#shopContacts').val('');
	$('#shopCode').val('');
	$('#shopId').val('');
	$('#arceCodeList').combobox('setValue', '');
	$("input[id='isCheck']").prop("checked",false);
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
	var rows = $('#shopGrid').datagrid('getSelections');
	return rows;
}
function openWindow(rowIndex, rowData) {
	clearFormData();
	var id = rowData.ID;
	var name = rowData.NAME;
	var areaCode = rowData.AREA_CODE;
	var isCheck = rowData.TYPE;
	
	var shopAddress = rowData.SHOP_ADDRESS;
	var shopContacts = rowData.SHOP_CONTACTS;

	$('#shopId').val(id);
	$('#shopName').val(name);
	$('#areaCode').val(areaCode);
	$('#shopAddress').val(shopAddress);
	$('#shopContacts').val(shopContacts);
	if (isCheck === '1') {
		$("input[id='isCheck']").prop("checked",true);
	}
	$('#arceCodeList').combobox('setValue', areaCode);
	$('#addShop').window('open');
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
	var id = $('#shopId').val();
	var shopCode = $('#shopCode').val();
	var areaCode= $('#arceCodeList').combo('getText');
	var shopName = $('#shopName').val();
	var shopAddress = $('#shopAddress').val();
	var shopContacts = $('#shopContacts').val();
	var isCheckTemp = "";
	if ($(":checked[name=isCheck]").val() == undefined) {
		isCheckTemp = "NO";
	} else {
		isCheckTemp = $(":checked[name=isCheck]").val();
	}
	if (areaCode== '' || shopName == '') {
		$('#af-showreq').click();  
		return;
	}

	$.ajax({
		url : contextPath+"/pages/system/modifyShop.light",
		type: "POST",
		dataType:'json',
		data:
		{
			"id":id,
			"shopCode":shopCode,
			"areaCode":areaCode,
			"shopName":shopName,
			"shopAddress":shopAddress,
			"shopContacts":shopContacts,
			"isCheck":isCheckTemp
		},
		success : function(data) {
			$('#shopGrid').datagrid("reload");
			$('#addShop').window('close');
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
	//getShopCode();
	var shopCode = $('#shopCode').val();
	var areaCode= $('#arceCodeList').combo('getText');
	var shopName = $('#shopName').val();
	var shopAddress = $('#shopAddress').val();
	var shopContacts = $('#shopContacts').val();
//	var isCheck = $('#isCheck').val();
	var isCheckTemp = "";
	if ($(":checked[name=isCheck]").val() == undefined) {
		isCheckTemp = "NO";
	} else {
		isCheckTemp = $(":checked[name=isCheck]").val();
	}
	if (areaCode== '' || shopName == '') {
		$('#af-showreq').click();  
		return;
	}

	$.ajax({
		url : contextPath+"/pages/system/addShop.light",
		type: "POST",
		dataType:'json',
		data:
		{
			"shopCode":shopCode,
			"areaCode":areaCode,
			"shopName":shopName,
			"shopAddress":shopAddress,
			"shopContacts":shopContacts,
			"isCheck":isCheckTemp
		},
		success : function(data) {
			$('#shopGrid').datagrid("reload");
			$('#addShop').window('close');
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

function searchExpressInfo() {
	var queryParams = $("#queryParams").val();
	queryParams = encodeURI(queryParams);
	var obj = {
		"queryParams" : queryParams
	};
	$('#shopGrid').datagrid("loadData", []);
	$('#shopGrid').datagrid("clearSelections");
	//		
	$('#shopGrid').datagrid({
		url : contextPath+ "/pages/system/queryShopInfos.light?queryParams="+ queryParams 
	});

	var paper = $('#shopGrid').datagrid('getPager');
	$(paper).pagination('refresh', {
		pageNumber : 1
	});
	
}

function addShop() {
	$('#addShop').window('open');
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

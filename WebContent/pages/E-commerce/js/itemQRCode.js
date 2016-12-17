var operatingTag=null;
var operatingContactsTag=null;
var loginName = null;
var textMsg = null;
$(document).ready(function() {
		window.onload = function() {
			obj = document.getElementById("HWPenSign");
			obj.HWSetBkColor(0xFFF5EE);
			obj.HWSetCtlFrame(0, 0x000000);
		};
		
		$('#signatureRegion').window({
			title:'支付二维码',
			width:560,
			height:395,
		    modal:true,
		    closed:true,
		    maximizable:false
		});
//		loginName = getUrlParam("loginName");
//		if (loginName === 'admin') {
//			textMsg = "新增商品";
//		} else {
//			textMsg = "普通工号无法新增或修改商品基础信息";
//		}
		$("#saveBtn").click(function() {
			if (operatingTag) {
				modifyForm();
			} else {
				saveForm();
			}
		});
		$("#number").click(function() {
			$("#qrcode").empty();
		});
		
		$("#reloadQRcode").click(function() {
			var temp = $("#payMoney").html();
			var tempMoney = temp.replace("￥", "");
			var money = parseFloat(tempMoney);
			if (money == 0.00 || money < 0) {
				$.messager.show({
	                title:'提示',
	                msg:'<div class="messager-icon messager-info"></div>支付金额不正确',
	                timeout:1500,
	                showType:'slide'
			  });
			} else {
				reloadPaymenCodeGeneration(money, $("#title").html());
			}
		});
		
		$('#expressServiceProviderContacts').window({
			title:'支付二维码',
		    width:780,
		    height:320,
		    modal:true,
		    closed:true,
		    left:340,    
		    top:30,
		    onBeforeClose:function(){
		    }
		});
		$("#saveContactsBtn").click(function() {
			if (operatingContactsTag) {
				modifyContactsForm();
			} else {
				saveContactsForm();
			}
		});
		
		$('#addMerchandise').window({
			title:'新增商品',
		    width:680,
		    height:410,
		    modal:true,
		    closed:true,
		    left:340,    
		    top:30,
		    onBeforeClose:function(){
		    }
		});
		
		$("#cancelBtn").click(function(){
			$('#addMerchandise').window('close');
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
		
		$('#expressServiceProviderGrid').datagrid({
			dataType : 'json',
			url : contextPath + '/pages/system/getMerchandiseList.light',
			width : $(window).width() * 0.99,
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
				text:"新增商品",
				iconCls: 'icon-add',
				handler: function(){
//					if (loginName === 'admin') {
						clearFormData();
						addMerchandise();
						operatingTag = false;
//					} 
				}
			}],
			columns : [ [{
				field : 'NAME',
				title : '商品名称',
				width : 180,
				align : 'center'
			},{
				field : 'PRODUCTION_DATE',
				title : '生产日期',
				width : 140,
				align : 'center',
				hidden : false
			},{
				field : 'UNIT_PRICE',
				title : '单价',
				width : 80,
				align : 'center',
				formatter : function(value, row, index)
				{
					if (value != null && value != 0) 
					{
						return "￥"+(value/100);
					} else {
						return value;
					}
				}
			},{
				field : 'DESCRIPTION',
				title : '商品描述',
				width : 240,
				align : 'center'
			},{
				field : 'REMARK',
				title : '备注',
				width : 260,
				align : 'center'
			},{
				field : 'opara',
				title : '操作',
				width : 120,
				align : 'center',
				hidden : false,
				formatter : function(value, row, index) {
					return "<button style='width: inherit;' id='contacts' onclick=\"paymenCodeGeneration('"+row.UNIT_PRICE+"','"+row.NAME+"','"+row.DESCRIPTION+"')\">支付二维码</button>";

				}
			}] ],
			onLoadSuccess : function(data) {

			},
			onLoadError : function() {
				parent.location.href=contextPath+'/pages/system/welcome.light';
			},
			onDblClickRow : function(rowIndex, rowData) {
//				if (loginName === 'admin') {
					openWindow(rowIndex, rowData);
					operatingTag = true;
//				}
				
			},
			loadFilter : pagerFilter
		});
	});

function clearFormData() {
	$('#inventory').val('');
	$('#name').val('');
	$('#unitPrice').val('');
	$('#productionDate').val('');
	$('#validity').val('');
	$('#remark').val('');
	$("#description").val('');
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

function paymenCodeGeneration(unitPrice,name,description) {
	$('#expressServiceProviderContacts').window('open');
	block('expressServiceProviderContacts', null);
	$('#reloadQRcode').hide();
	$("#qrcode").empty();
	$.ajax({
		url : contextPath + "/pages/system/getWeixinPayCodeURL.light",
		type : "POST",
		dataType : 'json',
		data : {
			"name" : name,
			"fee" : checkUnitsFee(unitPrice)
		},
		success : function(data) {
			if (navigator.appName.indexOf("Microsoft") != -1) {
				$("#qrcode").qrcode({
					render : "table", // 设置渲染方式canvas/table
					width : 275, // 宽度
					height : 275, // 高度
					correctLevel : QRErrorCorrectLevel.H,
					text : data.codeUrl
				});
			} else {
				$("#qrcode").qrcode({
					render : "canvas", // 设置渲染方式canvas/table
					width : 275, // 宽度
					height : 275, // 高度
					correctLevel : QRErrorCorrectLevel.H,
					text : data.codeUrl
				});
			}
			$("#desc").html(description);
			$("#title").html(name);
			$("#unitPrice").html(unitPrice/100);
			$("#money").html(unitPrice/100);
			$("#payMoney").html("￥"+unitPrice/100);
			$("#number").val(1);
			unblock('expressServiceProviderContacts');
			$('#carContainer').show();

		},
		error : function(data) {
		}
	});

}

function checkUnitsFee(money) {
	return money/100;
}

function reloadPaymenCodeGeneration(money, name) {
	block('expressServiceProviderContacts', null);
	$("#qrcode").empty();
	$.ajax({
		url : contextPath + "/pages/system/getWeixinPayCodeURL.light",
		type : "POST",
		dataType : 'json',
		data : {
			"name" : name,
			"fee" : money
		},
		success : function(data) {
			if (navigator.appName.indexOf("Microsoft") != -1) {
				$("#qrcode").qrcode({
					render : "table", // 设置渲染方式canvas/table
					width : 275, // 宽度
					height : 275, // 高度
					correctLevel : QRErrorCorrectLevel.H,
					text : data.codeUrl
				});
			} else {
				$("#qrcode").qrcode({
					render : "canvas", // 设置渲染方式canvas/table
					width : 275, // 宽度
					height : 275, // 高度
					correctLevel : QRErrorCorrectLevel.H,
					text : data.codeUrl
				});
			}
			$('#reloadQRcode').hide();
			unblock('expressServiceProviderContacts');
		},
		error : function(data) {
			$.messager.show({
				title : '错误',
				msg : '<div class="messager-icon messager-info"></div>接口错误',
				timeout : 2000,
				showType : 'slide'
			});
		}
	});

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
	var inventory = rowData.INVENTORY;
	var unitPrice = rowData.UNIT_PRICE;
	var remark = rowData.REMARK;
	var productionDate = rowData.PRODUCTION_DATE;
	var validity = rowData.VALIDITY;
	var description = rowData.DESCRIPTION;

	$('#merchandiseId').val(id);
	$('#name').val(name);
	$('#inventory').val(inventory);
	$('#unitPrice').val(unitPrice/100);
	$('#productionDate').val(productionDate);
	$('#validity').val(validity);
	$('#description').val(description);
	$('#remark').val(remark);
	$('#addMerchandise').window('open');
	
}

function modifyForm() {
	var id = $('#merchandiseId').val();
	var name = $('#name').val();
	var unitPrice = $('#unitPrice').val();
	var productionDate = $('#productionDate').val();
	var description = $('#description').val();
	var remark = $('#remark').val();
	
	if (checkTime(productionDate) == false) {
		$.messager.show({
            title:'提示',
            msg:'<div class="messager-icon messager-info"></div>'+"生产日期格式不正确",
            timeout:3800,
            showType:'slide'
		});
		return;
	} 

	$.ajax({
		url : contextPath+"/pages/system/modifyMerchandiseInfo.light",
		type: "POST",
		dataType:'json',
		data:
		{
			"id":id,
			"name":name,
			"unitPrice":unitPrice*100,
			"productionDate":productionDate,
			"description":description,
			"remark":remark
		},
		success : function(data) {
			$('#expressServiceProviderGrid').datagrid("reload");
			$('#addMerchandise').window('close');
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
	var name = $('#name').val();
	var inventory = $('#inventory').val();
	var unitPrice = $('#unitPrice').val();
	var productionDate = $('#productionDate').val();
	var validity = $('#validity').val();
	var description = $('#description').val();
	var remark = $('#remark').val();
	if (!$.isNumeric(unitPrice)) {
		$.messager.show({
            title:'提示',
            msg:'<div class="messager-icon messager-info"></div>'+"单价填写不正确",
            timeout:3800,
            showType:'slide'
		});
		return;
	}

	if (checkTime(productionDate) == false) {
		$.messager.show({
            title:'提示',
            msg:'<div class="messager-icon messager-info"></div>'+"生产日期格式不正确",
            timeout:3800,
            showType:'slide'
		});
		return;
	} 
	
	$.ajax({
		url : contextPath+"/pages/system/addMerchandise.light",
		type: "POST",
		dataType:'json',
		data:
		{
			"name":name,
			"inventory":inventory,
			"unitPrice":unitPrice*100,
			"productionDate":productionDate,
			"validity":validity,
			"description":description,
			"remark":remark
		},
		success : function(data) {
			$('#expressServiceProviderGrid').datagrid("reload");
			$('#addMerchandise').window('close');
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

function addMerchandise() {
	$('#addMerchandise').window('open');
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

function calculatedPaymentAmount(num) {
    var fv = parseFloat(num);
	if (fv <= 0) {
		$.messager.show({
            title:'提示',
            msg:'<div class="messager-icon messager-info"></div>数量填写不正确',
            timeout:1500,
            showType:'slide'
	  });
		return;
	}
	var unitPrice = $("#money").html();
	var temp = changeTwoDecimal(fv*unitPrice);
	$("#payMoney").html("￥"+temp);
	reloadCode();
}

function changeTwoDecimal(v) {
    if (isNaN(v)) {//参数为非数字
        return 0;
    }
    var fv = parseFloat(v);
    fv = Math.round(fv * 100) / 100; //四舍五入，保留两位小数
    var fs = fv.toString();
    var fp = fs.indexOf('.');
    if (fp < 0) {
        fp = fs.length;
        fs += '.';
    }
    while (fs.length <= fp + 2) { //小数位小于两位，则补0
        fs += '0';
    }
    return fs;
}

function checkTime(timeText) {
	var regTime = /^(\d{4})-(0\d{1}|1[0-2])-(0\d{1}|[12]\d{1}|3[01])$/;
	var result = false;
	if (regTime.test(timeText)) {
		result = true;
	}
	return result;
}

function reloadCode(){
	var temp = $("#payMoney").html();
	var tempMoney = temp.replace("￥", "");
	var money = parseFloat(tempMoney);
	if (money == 0.00 || money < 0) {
		$.messager.show({
            title:'提示',
            msg:'<div class="messager-icon messager-info"></div>支付金额不正确',
            timeout:1500,
            showType:'slide'
	  });
	} else {
		reloadPaymenCodeGeneration(money, $("#title").html());
	}
	
}

function loadBase64Stream(stream){
	obj.HWLoadBase64Stream(stream);
	$('#signatureRegion').window('open');
}

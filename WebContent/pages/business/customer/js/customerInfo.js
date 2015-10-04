var operatingTag=null;
var genderMap = {};
var whetherHaveCarMap = {};
var ageSectionMap = {};
var shopNameMap = {};
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
		initShopNameMap();
		initGender();
		initWhetherHaveCar();
		initAgeSection();
		//输入框按回车
		$("#queryParams").bind("keydown",function(e){
			var keycode = e.which;
			if(keycode == 13){//输入回车判定
				searchExpressInfo();
				e.preventDefault();						
			}
		});
		
		$('#addUser').window({
			title:'新增用户',
		    width:580,
		    height:460,
		    modal:true,
		    closed:true,
		    left:340,    
		    top:30,
		    onBeforeClose:function(){
		    }
		}); 
		
		$("#cancelBtn").click(function(){
			$('#addUser').window('close');
		});
		$("#saveBtn").click(function() {
				modifyForm();
		});
		
		$('#customerGrid').datagrid({
			dataType : 'json',
			url : contextPath + '/pages/system/customer/getCustomerList.light',
			width : $(window).width() * 1,
			height :($(window).height()-30)*0.99,
			singleSelect : true,
			rownumbers : true,
			pagination : true,
			striped : true,
			idField : 'ID',
			pageSize : 30,
			queryParams: {
				batchNumber: ''
			},
//			toolbar: [
//	        {
//				text:'新增客户',
//				iconCls: 'icon-search',
//				handler: function(){
//					addUser();
//					operatingTag = false;
//				}
//			}],
			columns : [ [ {
				field : 'NAME',
				title : '姓名',
				width : 120,
				align : 'center',
				hidden : false
			},{
				field : 'GENDER',
				title : '性别',
				width : 60,
				align : 'center',
				hidden : false,
				formatter : function(value, row, index){
					return formatGenderColumnTitle(value);
				}
			},{
				field : 'AGE_SECTION',
				title : '年龄段',
				width : 100,
				align : 'center',
				hidden : false,
				formatter : function(value, row, index){
					return formatAgeSectionColumnTitle(value);
				}
			},{
				field : 'WHETHER_HAVE_CAR',
				title : '是否有车',
				width : 60,
				align : 'center',
				hidden : false,
				formatter : function(value, row, index){
					return formatWhetherHaveCarColumnTitle(value);
				}
			},{
				field : 'PHONE_NUMBER',
				title : '联系电话',
				width : 120,
				align : 'center',
				hidden : false,
				formatter : function(value, row, index){
					return formatPhoneNumber(value);
				}
			},{
				field : 'SERVICE_SHOP_CODE',
				title : '服务网点',
				width : 120,
				align : 'center',
				hidden : false,
				formatter : function(value, row, index){
					return formatShopCodeColumnTitle(value);
				}
			},{
				field : 'eCount',
				title : '收件数',
				width : 100,
				align : 'center',
				hidden : false
			},{
				field : 'sCount',
				title : '寄件数',
				width : 100,
				align : 'center',
				hidden : false
			},{
				field : 'WEIXIN_ID',
				title : '微信号',
				width : 240,
				align : 'center',
				hidden : false
			},{
				field : 'ADDRESS',
				title : '地址',
				width : 220,
				align : 'center',
				hidden : false
			}] ],
			onLoadSuccess : function(data) {
			},
			onDblClickRow : function(rowIndex, rowData) {
				openWindow(rowIndex, rowData);
				operatingTag = true;
			},
			loadFilter : pagerFilter
		});
//		 $('#loginName').keydown(function(e){
//		        if(e.keyCode != 13 && e.keyCode != 9){
//		        	var endChar = String.fromCharCode(e.keyCode);
//					var loginName = $("#loginName").val();
//					var name = loginName+endChar.toLowerCase();
//					checkLoginNameUniqueness(name);
//		        }
//		 });
});

function formatPhoneNumber(value){
	var t=value.substring(0,3);
	var firstTemp = value.substring(3,7);
	var lastTemp = value.substring(7,11);
	var allTemp = t+"-"+firstTemp+'-'+'<span style="font-weight: bolder;">'+lastTemp+'</span>';
	return '<span style="font-size:1.1em;">'+allTemp+'</span>';
}

function initShopNameMap() {
	$.ajax({
		url : contextPath + "/pages/system/pageconfig/getServiceShopName.light",
		type : "POST",
		dataType : 'json',
		data : {
			"shop_code" : ""
		},
		success : function(data) {
			$.each(data, function(i) {    
				shopNameMap[data[i].SHOP_CODE] = data[i].NAME;
		     });
		},
		error : function(data) {
		}
	});
}

function initGender() {
	$.ajax({
		url : contextPath + "/pages/system/pageconfig/getPageGender.light",
		type : "POST",
		dataType : 'json',
		data : {
			"shop_code" : ""
		},
		success : function(data) {
			$.each(data, function(i) {    
				genderMap[data[i].value] = data[i].text;
		     });
		},
		error : function(data) {
		}
	});
}

function initWhetherHaveCar() {
	$.ajax({
		url : contextPath + "/pages/system/pageconfig/getWhetherHaveCar.light",
		type : "POST",
		dataType : 'json',
		data : {
			"shop_code" : ""
		},
		success : function(data) {
			$.each(data, function(i) {    
				whetherHaveCarMap[data[i].value] = data[i].text;
		     });
		},
		error : function(data) {
		}
	});
}

function initAgeSection() {
	$.ajax({
		url : contextPath + "/pages/system/pageconfig/getAgeSection.light",
		type : "POST",
		dataType : 'json',
		data : {
			"shop_code" : ""
		},
		success : function(data) {
			$.each(data, function(i) {    
				ageSectionMap[data[i].value] = data[i].text;
		     });
		},
		error : function(data) {
		}
	});
}

function formatShopCodeColumnTitle(value){
	return shopNameMap[value];
}

function formatGenderColumnTitle(value){
	return genderMap[value];
}

function formatWhetherHaveCarColumnTitle(value){
	return whetherHaveCarMap[value];
}

function formatAgeSectionColumnTitle(value){
	return ageSectionMap[value];
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

//function checkLoginNameUniqueness(loginName) {
//	if (checkAdminLoginName(loginName)) {
//		return;
//	}
//	$.ajax({
//		url : contextPath + "/pages/system/checkLoginNameUniqueness.light",
//		type : "POST",
//		dataType : 'json',
//		sync:false,
//		data : {
//			"loginName" : loginName
//		},
//		success : function(data) {
//			if (data.UNIQUE === 'false') {
////				alert('已存在登录名');
//				$('#saveBtn').hide();
//			} else  {
//				$('#saveBtn').show();
//			}
//		},
//		error : function(data) {
//		}
//	});
//}

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
			$('#barimg').attr('src', contextPath + data.PATH);
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
	var rows = $('#userGrid').datagrid('getSelections');
	return rows;
}
function openWindow(rowIndex, rowData) {
	clearFormData();
	var id = rowData.ID;
	var name = rowData.NAME;
	var gender = rowData.GENDER;
	var ageSection = rowData.AGE_SECTION;
	var whetherHaveCar = rowData.WHETHER_HAVE_CAR;
//	var isCheck = rowData.TYPE;
	var phoneNumber = rowData.PHONE_NUMBER;
	$('#customerId').val(id);
	$('#gender').combobox('setValue', gender);
	$('#ageSection').combobox('setValue', ageSection);
	$('#whetherHaveCar').combobox('setValue', whetherHaveCar);
	
	$('#name').val(name);
	$('#phoneNumber').val(phoneNumber);
//	if (isCheck === '1') {
//		$("input[id='isCheck']").prop("checked",true);
//	}
//	$('#shopCodeList').combobox('setValue', serviceShopCode);
	$('#addUser').window('open');
	
}

function clearFormData() {
	$('#name').val('');
	$('#gender').combobox('setValue', '');
	$('#ageSection').combobox('setValue', '');
	$('#whetherHaveCar').combobox('setValue', '');
	$('#phoneNumber').val('');
	$('#address').val('');
	
//	$("input[id='isCheck']").prop("checked",false);
}

//查询患者信息
function searchExpressInfo() {
	var queryParams = $("#queryParams").val();
	queryParams = encodeURI(queryParams);
	var obj = {
		"queryParams" : queryParams
	};
	$('#userGrid').datagrid("loadData", []);
	$('#userGrid').datagrid("clearSelections");
	//		
	$('#userGrid').datagrid({
		url : contextPath+ "/pages/system/queryUserInfos.light?queryParams="+ queryParams 
	});

	var paper = $('#userGrid').datagrid('getPager');
	$(paper).pagination('refresh', {
		pageNumber : 1
	});
	
}

//function checkAdminLoginName(loginName) {
//	if (loginName === 'admin') {
//		$.messager.show({
//            title:'提示',
//            msg:'<div class="messager-icon messager-info"></div>'+loginName +'为管理员工号不能添加',
//            timeout:3800,
//            showType:'slide'
//		});
//		$('#saveBtn').hide();
//		return true;
//	} else {
//		return false;
//	}
//}

function saveForm() {
	var loginName = $('#loginName').val();
	if (checkAdminLoginName(loginName)) {
		return;
	}
	var shopCode= $('#shopCodeList').combo('getText');
	var nickName = $('#nickName').val();
	var password = $('#password').val();
	var phoneNumber = $('#phoneNumber').val();
//	var isCheck = $('#isCheck').val();
	var isCheckTemp = "";
	if ($(":checked[name=isCheck]").val() == undefined) {
		isCheckTemp = "NO";
	} else {
		isCheckTemp = $(":checked[name=isCheck]").val();
	}
	if (shopCode == '' || loginName== '' || password == '') {
		$('#af-showreq').click();  
		return;
	}

	$.ajax({
		url : contextPath+"/pages/system/addUser.light",
		type: "POST",
		dataType:'json',
		data:
		{
			"loginName":loginName,
			"shopCode":shopCode,
			"nickName":nickName,
			"password":password,
			"phoneNumber":phoneNumber,
			"isCheck":isCheckTemp
		},
		success : function(data) {
			if (data === 'NEED_LOGIN') {
				$.ajax({
					url : contextPath+"/pages/system/welcome.light",
					type: "POST",
					dataType:'json',
					data:{
						"name":''
					}
				});
			}

			$('#userGrid').datagrid("reload");
			$('#addUser').window('close');
			$.messager.show({
                title:'提示',
                msg:'<div class="messager-icon messager-info"></div>'+data.msg,
                timeout:1500,
                showType:'slide'
			});
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

function modifyForm() {
	var id = $('#customerId').val();
	var name = $('#name').val();
	var address = $('#address').val();
	var phoneNumber = $('#phoneNumber').val();
	var gender= $('#gender').combo('getValue');
	var ageSection= $('#ageSection').combo('getValue');
	var whetherHaveCar= $('#whetherHaveCar').combo('getValue');
	
	$.ajax({
		url : contextPath+"/pages/system/modifyCustomer.light",
		type: "POST",
		dataType:'json',
		data:
		{
			"id":id,
			"name":name,
			"address":address,
			"phoneNumber":phoneNumber,
			"gender":gender,
			"ageSection":ageSection,
			"whetherHaveCar":whetherHaveCar
		},
		success : function(data) {
			$('#customerGrid').datagrid("reload");
			$('#addUser').window('close');
			$.messager.show({
                title:'提示',
                msg:'<div class="messager-icon messager-info"></div>'+data.msg,
                timeout:1500,
                showType:'slide'
			});
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

function addUser() {
	$('#addUser').window('open');
}

function formatItem(row){
	var ip = $("#shopCodeList").parent().find('.combo').children().eq(1);
	var comb = $(this).combobox('options');
	$(ip).click(function(){
		$('#shopCodeList').combo('showPanel');	
	});
    var s = '<span style="font-weight:bold">' + row.desc + '</span><br/>' +
            '<span style="color:#888">' + row.text + '</span>';
    return s;
}

function formatGender(row){
	var ip = $("#gender").parent().find('.combo').children().eq(1);
	var comb = $(this).combobox('options');
	$(ip).click(function(){
		$('#gender').combo('showPanel');	
	});
    return row.text;
}

function formatAgeSection(row){
	var ip = $("#ageSection").parent().find('.combo').children().eq(1);
	var comb = $(this).combobox('options');
	$(ip).click(function(){
		$('#ageSection').combo('showPanel');	
	});
    return row.text;
}

function formatWhetherHaveCar(row){
	var ip = $("#whetherHaveCar").parent().find('.combo').children().eq(1);
	var comb = $(this).combobox('options');
	$(ip).click(function(){
		$('#whetherHaveCar').combo('showPanel');	
	});
    return row.text;
}

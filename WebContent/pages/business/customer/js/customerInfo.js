var operatingTag=null;
var genderMap = {};
var whetherHaveCarMap = {};
var ageSectionMap = {};
var shopNameMap = {};
$(document).ready(function(){
		loginName = getUrlParam("loginName");
		if (loginName === 'admin') {
			$('#shopSpan').show();
		} else {
			$('#shopSpan').hide();
		}
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
		    height:520,
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
			width : $(window).width()*0.99,
			height :($(window).height()-28)*0.99,
			singleSelect : true,
			rownumbers : true,
			pagination : true,
			striped : true,
			method : 'post',
			idField : 'ID',
			pageSize : 20,
			queryParams: {
				queryParams: ''
			},
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
				field : 'IS_INTEREST',
				title : '是否已关注微信',
				width : 100,
				align : 'center',
				hidden : false,
				formatter : function(value, row, index){
					if (value == 'Y') {
						return '<span style="color:green;">已关注</span>';
					} else {
						return '<span style="color:#EC920F;">未关注</span>';
					}
					
				}
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
			onLoadError : function() {
				parent.location.href=contextPath+'/pages/system/welcome.light';
			},
			onDblClickRow : function(rowIndex, rowData) {
				openWindow(rowIndex, rowData);
				operatingTag = true;
			}
		});
		
		$('#expressServiceId').combobox({
			url : contextPath + "/pages/system/getShopInfoForSelect.light",
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
			},
			onSelect: function(row){
				searchExpressInfo();
	        }
		});
		
		$("#downloadTemplateUsingClass").click(function(){
            com.fla.common.downloadExcelTemplate({
                    ctx:contextPath,
                    excelMapper:"reviseImport-investment-psn"
            });
        });
        
        $("#exportUsingClass").click(function(){
            com.fla.common.exportExcel({
                ctx:contextPath,
                excelMapper:"reviseImport-investment-psn",
                targetFileName:"reviseImport-investment-export",
                queryParams: [
                              {name: "id", value: $("#id").val()},
                              {name: "operateTag", value:"2"}
                ]
            });
        });
        
        $("#importUsingClass").click(function(){
            com.fla.common.importExcel({
                ctx:contextPath,
                excelMapper:"reviseImport-investment-psn",
                queryParams:
                {
                	id : "f1f",
                	taskTitle : "fff4f",
                	year : "f30f",
                	batchNumber : "f345f",
                	remark : "fddsdvsdwf"
                },
                callbackFun:importExcelCallback
            });
        });
        var importExcelCallback = function(resultData){
            //导入后返回json对象，自行处理这个返回结果
            var result = "状态:"+resultData.succeed +  "\n";
            result += "消息:" + resultData.message + "\n";
            result += "错误明细：\n";
            $.each(resultData.errorList,function (index, val){
                result += index + ":" + val + "\n";
            });
            if (resultData.succeed == false) {
            	alert(result);
			} else {
				
			}
        }

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
	var address = rowData.ADDRESS;
	var isCheck = rowData.IS_INTEREST;
	
	if (isCheck === 'Y') {
		$("input[id='isInterest']").prop("checked",true);
	}if (isCheck === 'N') {
		$("input[id='isInterest']").prop("checked",false);
	}
	var phoneNumber = rowData.PHONE_NUMBER;
	$('#customerId').val(id);
	$('#gender').combobox('setValue', gender);
	$('#ageSection').combobox('setValue', ageSection);
	$('#whetherHaveCar').combobox('setValue', whetherHaveCar);
	$('#name').val(name);
	$('#phoneNumber').val(phoneNumber);
	$('#address').val(address);
	$('#addUser').window('open');
	
}

function clearFormData() {
	$('#name').val('');
	$('#gender').combobox('setValue', '');
	$('#ageSection').combobox('setValue', '');
	$('#whetherHaveCar').combobox('setValue', '');
	$('#phoneNumber').val('');
	$('#address').val('');
	$("input[id='isInterest']").prop("checked",false);
}

function searchExpressInfo() {
	var queryParams = $("#queryParams").val();
	queryParams = encodeURI(queryParams);
//	var obj = {
//		"queryParams" : queryParams
//	};
	$('#customerGrid').datagrid("loadData", []);
	$('#customerGrid').datagrid("clearSelections");
	var shopCode = $("#expressServiceId").combobox('getText');
	$('#customerGrid').datagrid({
		url : contextPath+ "/pages/system/customer/getCustomerList.light",
		queryParams: {
			queryParams: queryParams,
			shopCode: shopCode
		}
	});

	var paper = $('#customerGrid').datagrid('getPager');
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
	var isInterest = "";
	if ($(":checked[name=isInterest]").val() == undefined) {
		isInterest = "N";
	} else {
		isInterest = $(":checked[name=isInterest]").val();
	}
	if (!isPhoneNmuber(phoneNumber)) {
		$.messager.show({
            title:'提示',
            msg:'<div class="messager-icon messager-info"></div>'+'手机或座机号码填写不正确',
            timeout:3800,
            showType:'slide'
		});
		return;
	}
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
			"whetherHaveCar":whetherHaveCar,
			"isInterest":isInterest
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

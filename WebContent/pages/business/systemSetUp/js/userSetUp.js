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
			if (operatingTag) {
				modifyForm();
			} else {
				saveForm();
			}
		});
		
		$('#userGrid').datagrid({
			dataType : 'json',
			url : contextPath + '/pages/system/getUserInfoList.light',//getNotOutExpressInfoByFilterConditions
			width : $(window).width() * 1,
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
				text:'新增用户',
				iconCls: 'icon-search',
				handler: function(){
					clearFormData();
					addUser();
					operatingTag = false;
				}
			}],
			columns : [ [ {
				field : 'LOGIN_NAME',
				title : '登录名',
				width : 120,
				align : 'center',
				hidden : false
			},{
				field : 'NICK_NAME',
				title : '昵称',
				width : 120,
				align : 'center',
				hidden : false
			},{
				field : 'PHONE_NUMBER',
				title : '联系电话',
				width : 120,
				align : 'center',
				hidden : false
			},{
				field : 'AREA_CODE',
				title : '区域编号',
				width : 120,
				align : 'center',
				hidden : true
			},{
				field : 'SERVICE_SHOP_CODE',
				title : '网点编号',
				width : 120,
				align : 'center',
				hidden : true
			},{
				field : 'SERVICE_SHOP_NAME',
				title : '网点名称',
				width : 120,
				align : 'center',
				hidden : false
			},{
				field : 'OPEN_IM',
				title : '是否开通网点间通信',
				width : 120,
				align : 'center',
				hidden : false
			},{
				field : 'CREATE_DATE',
				title : '创建时间',
				width : 120,
				align : 'center',
				hidden : false
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
		 $('#loginName').keydown(function(e){
		        if(e.keyCode != 13 && e.keyCode != 9){
		        	var endChar = String.fromCharCode(e.keyCode);
					var loginName = $("#loginName").val();
					var name = loginName+endChar.toLowerCase();
					checkLoginNameUniqueness(name);
		        }
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

function checkLoginNameUniqueness(loginName) {
	if (checkAdminLoginName(loginName)) {
		return;
	}
	$.ajax({
		url : contextPath + "/pages/system/checkLoginNameUniqueness.light",
		type : "POST",
		dataType : 'json',
		sync:false,
		data : {
			"loginName" : loginName
		},
		success : function(data) {
			if (data.UNIQUE === 'false') {
//				alert('已存在登录名');
				$('#saveBtn').hide();
			} else  {
				$('#saveBtn').show();
			}
		},
		error : function(data) {
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
	var serviceShopCode = rowData.SERVICE_SHOP_CODE;
	var loginName = rowData.LOGIN_NAME;
	var nickName = rowData.NICK_NAME;
	var password = rowData.PASSWORD;
	var phoneNumber = rowData.PHONE_NUMBER;
	var isCheck = rowData.TYPE;
	var isOpenIM = rowData.OPEN_IM;
	
	$('#userId').val(id);
	$('#loginName').val(loginName);
	$('#nickName').val(nickName);
//	$('#password').val(password);
	$('#phoneNumber').val(phoneNumber);
	if (isCheck === '1') {
		$("input[id='isCheck']").prop("checked",true);
	}
	if (isOpenIM === 'C') {
		$("input[id='isOpenIM']").prop("checked",false);
	} else {
		$("input[id='isOpenIM']").prop("checked",true);
	}
	$('#shopCodeList').combobox('setValue', serviceShopCode);
	$('#addUser').window('open');
	
}

function clearFormData() {
	$('#loginName').val('');
	$('#userId').val('');
	$('#nickName').val('');
	$('#password').val('');
	$('#phoneNumber').val('');
	$('#shopCodeList').combobox('setValue', '');
	$("input[id='isCheck']").prop("checked",false);
	$("input[id='isOpenIM']").prop("checked",false);
}

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
	var loginName = $('#loginName').val();
	if (checkAdminLoginName(loginName)) {
		return;
	}
	var shopCode= $('#shopCodeList').combo('getText');
	var nickName = $('#nickName').val();
	var password = $('#password').val();
	var phoneNumber = $('#phoneNumber').val();
	if (!isPhoneNmuber(phoneNumber)) {
		$.messager.show({
            title:'提示',
            msg:'<div class="messager-icon messager-info"></div>'+'手机或座机号码填写不正确',
            timeout:3800,
            showType:'slide'
		});
		return;
	}
//	var isCheck = $('#isCheck').val();
	var isCheckTemp = "";
	if ($(":checked[name=isCheck]").val() == undefined) {
		isCheckTemp = "NO";
	} else {
		isCheckTemp = $(":checked[name=isCheck]").val();
	}
	
	var isOpenIMTemp = "";
	if ($(":checked[name=isOpenIM]").val() == undefined) {
		isOpenIMTemp = "NO";
	} else {
		isOpenIMTemp = $(":checked[name=isOpenIM]").val();
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
			"isCheck":isCheckTemp,
			"isOpenIM":isOpenIMTemp
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
	var id = $('#userId').val();
	var loginName = $('#loginName').val();
	if (checkAdminLoginName(loginName)) {
		return;
	}
	var shopCode= $('#shopCodeList').combo('getText');
	var nickName = $('#nickName').val();
	var password = $('#password').val();
	var phoneNumber = $('#phoneNumber').val();
	if (!isPhoneNmuber(phoneNumber)) {
		$.messager.show({
            title:'提示',
            msg:'<div class="messager-icon messager-info"></div>'+'手机或座机号码填写不正确',
            timeout:3800,
            showType:'slide'
		});
		return;
	}
//	var isCheck = $('#isCheck').val();
	var isCheckTemp = "";
	if ($(":checked[name=isCheck]").val() == undefined) {
		isCheckTemp = "NO";
	} else {
		isCheckTemp = $(":checked[name=isCheck]").val();
	}
	
	var isOpenIMTemp = "";
	if ($(":checked[name=isOpenIM]").val() == undefined) {
		isOpenIMTemp = "NO";
	} else {
		isOpenIMTemp = $(":checked[name=isOpenIM]").val();
	}
	if (shopCode == '' || loginName== '') {
		$('#af-showreq').click();  
		return;
	}

	$.ajax({
		url : contextPath+"/pages/system/modifyUser.light",
		type: "POST",
		dataType:'json',
		data:
		{
			"id":id,
			"loginName":loginName,
			"shopCode":shopCode,
			"nickName":nickName,
			"password":password,
			"phoneNumber":phoneNumber,
			"isCheck":isCheckTemp,
			"isOpenIM":isOpenIMTemp
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

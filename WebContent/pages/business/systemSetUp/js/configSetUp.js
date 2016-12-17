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
		
		//输入框按回车
		$("#queryParams").bind("keydown",function(e){
			var keycode = e.which;
			if(keycode == 13){//输入回车判定
				searchExpressInfo();
				e.preventDefault();						
			}
		});
		
		$('#addConfig').window({
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
			$('#addConfig').window('close');
		});
		$("#saveBtn").click(function() {
			if (operatingTag) {
				modifyForm();
			} else {
				saveForm();
			}
		});
		
		$('#configGrid').treegrid({
			dataType : 'json',
			url : contextPath + '/pages/system/getConfigInfoList.light',
			width : $(window).width() * 0.99,
			height :($(window).height()-32)*0.99,
			singleSelect : true,
			rownumbers : true,
			pagination : true,
			animate : true,
			striped : true,
			showFooter:true,
			iconCls:'icon-ok',
			idField : 'ID',
			treeField:'CONFIG_NAME',
			pageSize : 20,
			queryParams: {
				batchNumber: ''
			},
			columns : [ [{
				field : 'CONFIG_NAME',
				title : '参数名称',
				width : 120,
				align : 'center',
				editor:{type:'text'},
				hidden : false
			},{
				field : 'VAlUE_ID',
				title : 'VAlUE_ID',
				width : 120,
				align : 'center',
				hidden : true
			},{
				field : 'VAlUE',
				title : '值',
				width : 120,
				align : 'center',
				editor:{type:'text'},
				hidden : false
			},{
				field : 'ORDER_NUMBER',
				title : '顺序',
				width : 60,
				align : 'center',
				editor:{type:'text'},
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
			},{
				field:'action',title:'操作选项',width:150,align:'center',
                formatter:function(value,row,index){
                    if (row.editing){
                        var s = '<a href="#" onclick="saverow('+index+')">保存</a> ';
                        var c = '<a href="#" onclick="cancelrow('+index+')">取消</a>';
                        return s+c;
                    } else {
                        var e = '<a href="#" onclick="editrow('+index+')">编辑</a> ';
                        var d = '<a href="#" onclick="deleterow('+index+')">删除</a>';
                        return e+d;
                    }
                }
            }]],
			onDblClickRow : function(rowIndex, rowData) {
				openWindow(rowIndex, rowData);
				operatingTag = true;
			},onLoadError : function() {
				parent.location.href=contextPath+'/pages/system/welcome.light';
			},
			onClickCell : function(rowIndex, rowData) {
				$('#configGrid').treegrid('beginEdit', rowIndex);
			}
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

function editrow(index){
    alert(index);
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
	var rows = $('#configGrid').datagrid('getSelections');
	return rows;
}
function openWindow(rowIndex, rowData) {
	$('#addConfig').window('open');
	
}

function clearFormData() {
	$('#loginName').val('');
	$('#userId').val('');
	$('#nickName').val('');
	$('#password').val('');
	$('#phoneNumber').val('');
	$('#shopCodeList').combobox('setValue', '');
	$("input[id='isCheck']").prop("checked",false);
}

function searchExpressInfo() {
	var queryParams = $("#queryParams").val();
	queryParams = encodeURI(queryParams);
	var obj = {
		"queryParams" : queryParams
	};
	$('#configGrid').datagrid("loadData", []);
	$('#configGrid').datagrid("clearSelections");
	//		
	$('#configGrid').datagrid({
		url : contextPath+ "/pages/system/queryUserInfos.light?queryParams="+ queryParams 
	});

	var paper = $('#configGrid').datagrid('getPager');
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

			$('#configGrid').datagrid("reload");
			$('#addConfig').window('close');
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
			"isCheck":isCheckTemp
		},
		success : function(data) {
			$('#configGrid').datagrid("reload");
			$('#addConfig').window('close');
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

function addConfig() {
	$('#addConfig').window('open');
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

$(document).ready(function() {
//		$('#id').prop('readonly', true);
//		$("#name").bind("keydown",function(e){
//			var keycode = e.which;
//			//输入回车判定
//			if(keycode == 13){
//				e.preventDefault();						
//			}
//		});
		
		$("#saveBtn").click(function() {
			saveRole();
		});
		$("#cancelBtn").click(function(){
			$('#addRole').window('close');
		});

		$('#addRole').window({
			title:'新增角色',
		    width:620,
		    height:300,
		    modal:true,
		    closed:true,
		    left:340,    
		    top:30,
		    onBeforeClose:function(){
		    }
		});
		
		$('#menuTreeContainer').window({
			title:'编辑菜单',
		    width:350,
		    height:500,
		    modal:true,
		    closed:true,
		    maximizable:false,
		    resizable:false,
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
		
		$('#roleGrid').datagrid({
			dataType : 'json',
			url : contextPath + '/pages/system/role/getRoleList.light',
			width : $(window).width() * 0.99,
			height :($(window).height()-32)*0.99,
			singleSelect : true,
			rownumbers : true,
			pagination : true,
			striped : true,
			idField : 'id',
			pageSize : 20,
			queryParams: {
				batchNumber: ''
			},
			toolbar: [
	        {
				text:'新增角色',
				iconCls: 'icon-add',
				handler: function(){
					clearFormData();
					addRole();
				}
			}],
			columns : [[{
				field : 'id',
				title : 'ID',
				width : 60,
				hidden : false
			},{
				field : 'name',
				title : '角色名称',
				width : 120,
				align : 'center',
				hidden : false
			},{
				field : 'displayOrder',
				title : '排序号',
				width : 50,
				align : 'center',
				hidden : false
			},{
				field : 'status',
				title : '是否启用',
				width : 65,
				align : 'center',
				hidden : false,
				formatter : function(value, row, index) {
					if (row.status == 'Y') {
						return "<img title='已启用' style='width: 22px;' src='"+contextPath+"/pages/images/icon/check.png'/>";
					} else {
						return "<img title='未启用' style='width: 22px;' src='"+contextPath+"/pages/images/icon/unCheck.png'/>";
					}
				}
			},{
				field : 'opara',
				title : '编辑菜单',
				width : 100,
				align : 'center',
				hidden : false,
				formatter : function(value, row, index) {
					return "<button style='width: inherit;' onclick=\"oparaMenu("+row.id+",'"+row.name+"','"+row.status+"')\">编辑菜单</button>";
				}
			}] ],
			onLoadSuccess : function(data) {

			},
			onLoadError : function() {
				parent.location.href=contextPath+'/pages/system/welcome.light';
			},
			onDblClickRow : function(rowIndex, rowData) {
				openWindow(rowIndex, rowData);
			},
			loadFilter : pagerFilter
		});
	});
	
function addRole(){
	$('#addRole').window('open');
}
function oparaMenu(id,name,status){
//	var row = $('#roleGrid').datagrid('getSelected');
	$('#menuTreeGrid').treegrid({
		url : contextPath + '/pages/system/role/getMenuTree.light',
	    idField:'id',
	    treeField:'name',
//	    pagination: true,
//		pageSize: 2,
	    height:620,
//		pageList: [10,20,30,40,50],
	    queryParams: {
			roleId: id
		},
	    columns:[[
			{
				field : 'parentId',
				title : '父级菜单',
				width : 150,
				hidden : true
			},{
				title:'菜单名称',
				field:'name',
				width:220
			},{
				field : 'roleId',
				title : '是否延期',
				width : 100,
				align : 'center',
				hidden : true,
				formatter : function(value, row, index){
					row['roleId'] = id;
				}
			},{
				title:'是否添加',
				field:'isSelect',
				width:80,
				align:'center',
				formatter : function(value, row, index) {
					if (row.isSelect == 'Y') {
						return "<button style='width: inherit;background: #80b6cd;' onclick=\"menuOutRole("+row.id+",'"+row.parentId+"','"+row.roleId+"')\">移除</button>";
					} else {
						return "<button style='width: inherit;background: #dedede;' onclick=\"menuInRole("+row.id+",'"+row.parentId+"','"+row.roleId+"')\">添加</button>";
					}
				}
			}
	    ]]
	});
	$('#menuTreeContainer').window('open');
}

function menuOutRole(id,parentId,roleId){
//	var row = $('#roleGrid').datagrid('getSelected');
	$.ajax({
		url : contextPath+"/pages/system/role/menuOutRole.light",
		type: "POST",
		dataType:'json',
		data:
		{
			"menuId":id,
			"parentId":parentId,
			"roleId":roleId
		},
		success : function(data) {
			$('#menuTreeGrid').treegrid("reload");
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

function menuInRole(id,parentId,roleId){
//	var row = $('#roleGrid').datagrid('getSelected');
	$.ajax({
		url : contextPath+"/pages/system/role/menuInRole.light",
		type: "POST",
		dataType:'json',
		data:
		{
			"menuId":id,
			"parentId":parentId,
			"roleId":roleId
		},
		success : function(data) {
			$('#menuTreeGrid').treegrid("reload");
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

function saveRole() {
	var id = $('#id').val();
	var name = $('#name').val();
	var displayOrder = $('#displayOrder').val();
	var isStatusTemp = "";
	if ($(":checked[name=status]").val() == undefined) {
		isStatusTemp = "N";
	} else {
		isStatusTemp = $(":checked[name=status]").val();
	}
	if (name == '') {
		$('#af-showreq').click();
		return;
	}

	$.ajax({
		url : contextPath+"/pages/system/role/addRole.light",
		type: "POST",
		dataType:'json',
		data:
		{
			"id":id,
			"name":name,
			"displayOrder":displayOrder,
			"status":isStatusTemp
		},
		success : function(data) {
			$('#roleGrid').datagrid("reload");
			$('#addRole').window('close');
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
	var rows = $('#roleGrid').datagrid('getSelections');
	return rows;
}

function openWindow(rowIndex, rowData) {
	clearFormData();
	var id = rowData.id;
	var name = rowData.name;
	var displayOrder = rowData.displayOrder;
	var status = rowData.status;
	$('#id').val(id);
	$('#name').val(name);
	$('#displayOrder').val(displayOrder);
	if (status === 'Y') {
		$("input[id='status']").prop("checked",true);
	}
	$('#addRole').window('open');
}

function clearFormData() {
	$('#id').val("");
	$('#name').val("");
	$('#displayOrder').val("");
	$("input[id='status']").prop("checked",false);
}

function searchExpressInfo() {
	var queryParams = $("#queryParams").val();
	queryParams = encodeURI(queryParams);
	var obj = {
		"queryParams" : queryParams
	};
	$('#roleGrid').datagrid("loadData", []);
	$('#roleGrid').datagrid("clearSelections");
	//		
	$('#roleGrid').datagrid({
		url : contextPath+ "/pages/system/role/getRoleList.light?queryParams="+ queryParams 
	});

	var paper = $('#roleGrid').datagrid('getPager');
	$(paper).pagination('refresh', {
		pageNumber : 1
	});
	
}

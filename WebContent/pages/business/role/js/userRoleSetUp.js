
$(document).ready(function() {
//		$('#id').prop('readonly', true);
//		$("#name").bind("keydown",function(e){
//			var keycode = e.which;
//			//输入回车判定
//			if(keycode == 13){
//				e.preventDefault();						
//			}
//		});
	
	$("#saveConfigBtn").click(function() {
		var users = getUserGridSelections();
		var usersArray = new Array();
		$.each(users, function(i, item){
			usersArray.push(item.id);
		});
		var roles = getRoleGridSelections();
		var rolesArray = new Array();
		$.each(roles, function(i, item){
			rolesArray.push(item.id);
		});
		if (users == [] || roles == []) {
			$.messager.show({
                title:'提示',
                msg:'<div class="messager-icon messager-info"></div>'+data.msg,
                timeout:1500,
                showType:'slide'
			});
			return;
		} else {
			$.ajax({
				url : contextPath+"/pages/system/userRole/saveConfig.light",
				type: "POST",
				dataType:'json',
				data:
				{
					"users":usersArray.join(','),
					"roles":rolesArray.join(',')
				},
				success : function(data) {
					$('#userRoleGrid').datagrid("reload");
					$('#userRoleContainer').window('close');
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
	});
	$("#queryUserBtn").click(function() {
		reloadUserGrid($('#userSearch').val());
	});
	$("#queryRoleBtn").click(function() {
		reloadRoleGrid($('#roleSearch').val());
	});
	
		$("#saveBtn").click(function() {
			saveRole();
		});
		$("#cancelBtn").click(function(){
			$('#addRole').window('close');
		});

		$('#addUserRole').window({
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
		
		$('#userRoleContainer').window({
			title:'新增用户角色',
		    width:650,
		    height:476,
		    modal:true,
		    closed:true,
//		    maximizable:false,
//		    resizable:false,
		    left:340,    
		    top:30,
		    onBeforeClose:function(){
		    }
		});
		
		$("#queryUserName").bind("keydown",function(e){
			var keycode = e.which;
			if(keycode == 13){//输入回车判定
				searchExpressInfo();
				e.preventDefault();
			}
		});
		
		$("#queryRoleName").bind("keydown",function(e){
			var keycode = e.which;
			if(keycode == 13){//输入回车判定
				searchExpressInfo();
				e.preventDefault();
			}
		});
		
		$('#userRoleGrid').datagrid({
			dataType : 'json',
			url : contextPath + '/pages/system/userRole/getUserRoleList.light',
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
				text:'新增用户角色',
				iconCls: 'icon-add',
				handler: function(){
					clearFormData();
					userRoleContainer();
				}
			}],
			columns : [[{
				field : 'id',
				title : 'ID',
				width : 60,
				hidden : true
			},{
				field : 'userId',
				title : 'userId',
				width : 120,
				align : 'center',
				hidden : true
			},{
				field : 'nickName',
				title : '姓名',
				width : 120,
				align : 'center',
				hidden : false
			},{
				field : 'loginName',
				title : '登录名',
				width : 120,
				align : 'center',
				hidden : false
			},{
				field : 'roleId',
				title : 'roleId',
				width : 50,
				align : 'center',
				hidden : true
			},{
				field : 'roleName',
				title : '角色名',
				width : 160,
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
				width : 120,
				align : 'center',
				hidden : false,
				formatter : function(value, row, index) {
					return "<button style='width: inherit;' onclick=\"deleteUserRole("+row.id+",'"+row.userId+"','"+row.roleId+"')\">删除</button>";
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
	
function userRoleContainer(){
	$('#userGrid').datagrid({
		dataType : 'json',
		url : contextPath + '/pages/system/userRole/getUserList.light',
		width : 636,
		height :205,
		singleSelect : false,
		rownumbers : true,
		pagination : true,
		striped : true,
		idField : 'id',
		pageSize : 20,
		queryParams: {
			userSearch: $('#userSearch').val()
		},
//		toolbar: [
//		{
//			text:'用户名:<input id="userSearch" name="userSearch" style="width: 150px;height:25px;border-style: solid;border-color: antiquewhite;" placeholder="用户名称">',
//			iconCls: 'icon-search'
//		},{
//			text:'查询用户',
//			iconCls: 'icon-search',
//			handler: function(){
//				$('#userGrid').datagrid("reload");
//				$('#userSearch').val("");
//			}
//		}],
		columns : [[{
			field : 'id',
			title : 'ID',
			width : 60,
			hidden : true
		},{
			field : 'loginName',
			title : '登录名',
			width : 90,
			align : 'center',
			hidden : false
		},{
			field : 'nickName',
			title : '用户名',
			width : 120,
			align : 'center',
			hidden : false
		},{
			field : 'phoneNumber',
			title : '电话号码',
			width : 125,
			align : 'center',
			hidden : false
		},{
			field : 'shopName',
			title : '网点',
			width : 130,
			align : 'center',
			hidden : false
		},{
			field : 'areaName',
			title : '区域',
			width : 120,
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
		},
		loadFilter : pagerFilter
	});
	
	$('#roleGrid').datagrid({
		dataType : 'json',
		url : contextPath + '/pages/system/userRole/getRoleList.light',
		width : 636,
		height :185,
		singleSelect : false,
		rownumbers : true,
		pagination : true,
		striped : true,
		idField : 'id',
		pageSize : 20,
		queryParams: {
			roleSearch: $('#roleSearch').val()
		},
//		toolbar: [
//		{
//			text:'角色名:<input id="roleSearch" name="roleSearch" style="width: 150px;height:25px;border-style: solid;border-color: antiquewhite;" placeholder="角色名称">',
//			iconCls: 'icon-search'
//		},{
//			text:'查询角色',
//			iconCls: 'icon-search',
//			handler: function(){
//				$('#roleGrid').datagrid("reload");
//				$('#roleSearch').val("");
//			}
//		}],
		columns : [[{
			field : 'id',
			title : 'ID',
			width : 60,
			hidden : true
		},{
			field : 'name',
			title : '角色名',
			width : 180,
			align : 'center',
			hidden : false
		},{
			field : 'code',
			title : '编号',
			width : 180,
			align : 'center',
			hidden : false
		},{
			field : 'status',
			title : '是否启用',
			width : 100,
			align : 'center',
			hidden : false,
			formatter : function(value, row, index) {
				if (row.status == 'Y') {
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
		},
		loadFilter : pagerFilter
	});
	$('#userRoleContainer').window('open');
}

function deleteUserRole(id,userId,roleId){
	$.messager.confirm('确认', '您确认想要删除记录吗？', function(r) {
		if (r) {
			$.ajax({
				url : contextPath+"/pages/system/userRole/deleteUserRole.light",
				type: "POST",
				dataType:'json',
				data:
				{
					"id":id,
					"userId":userId,
					"roleId":roleId
				},
				success : function(data) {
					$('#userRoleGrid').datagrid("reload");
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
		} else {
			return false;
		}
	});
}

function getSelections() {
	var rows = $('#userRoleGrid').datagrid('getSelections');
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
	if (status === 'Y') {
		$("input[id='status']").prop("checked",true);
	}
	$('#addRole').window('open');
}

function clearFormData() {
	$('#id').val("");
	$('#name').val("");
	$("input[id='status']").prop("checked",false);
}

function searchExpressInfo() {
	var queryUserName = $("#queryUserName").val();
	var queryRoleName = $("#queryRoleName").val();
	queryRoleName = encodeURI(queryRoleName);
	queryUserName = encodeURI(queryUserName);
//	var obj = {
//		"queryParams" : queryRoleName
//	};
	$('#userRoleGrid').datagrid("loadData", []);
	$('#userRoleGrid').datagrid("clearSelections");
	//		
	$('#userRoleGrid').datagrid({
		url : contextPath+ "/pages/system/userRole/getUserRoleList.light?queryUserName="+ queryUserName+"&queryRoleName="+queryRoleName
	});

	var paper = $('#userRoleGrid').datagrid('getPager');
	$(paper).pagination('refresh', {
		pageNumber : 1
	});
	
}

function reloadUserGrid(userSearch){
	$('#userGrid').datagrid({
		dataType : 'json',
		url : contextPath + '/pages/system/userRole/getUserList.light',
		width : 636,
		height :205,
		singleSelect : false,
		rownumbers : true,
		pagination : true,
		striped : true,
		idField : 'id',
		pageSize : 20,
		queryParams: {
			userSearch: userSearch
		},
		columns : [[{
			field : 'id',
			title : 'ID',
			width : 60,
			hidden : true
		},{
			field : 'loginName',
			title : '登录名',
			width : 90,
			align : 'center',
			hidden : false
		},{
			field : 'nickName',
			title : '用户名',
			width : 120,
			align : 'center',
			hidden : false
		},{
			field : 'phoneNumber',
			title : '电话号码',
			width : 125,
			align : 'center',
			hidden : false
		},{
			field : 'shopName',
			title : '网点',
			width : 130,
			align : 'center',
			hidden : false
		},{
			field : 'areaName',
			title : '区域',
			width : 120,
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
		},
		loadFilter : pagerFilter
	});
}

function reloadRoleGrid(roleSearch){
	$('#roleGrid').datagrid({
		dataType : 'json',
		url : contextPath + '/pages/system/userRole/getRoleList.light',
		width : 636,
		height :185,
		singleSelect : false,
		rownumbers : true,
		pagination : true,
		striped : true,
		idField : 'id',
		pageSize : 20,
		queryParams: {
			roleSearch: roleSearch
		},
		columns : [[{
			field : 'id',
			title : 'ID',
			width : 60,
			hidden : true
		},{
			field : 'name',
			title : '角色名',
			width : 180,
			align : 'center',
			hidden : false
		},{
			field : 'code',
			title : '编号',
			width : 180,
			align : 'center',
			hidden : false
		},{
			field : 'status',
			title : '是否启用',
			width : 100,
			align : 'center',
			hidden : false,
			formatter : function(value, row, index) {
				if (row.status == 'Y') {
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
		},
		loadFilter : pagerFilter
	});
}

function getUserGridSelections(){
	return $('#userGrid').datagrid('getSelections');

}

function getRoleGridSelections(){
	return $('#roleGrid').datagrid('getSelections');
}

var operatingTag=null;
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
			saveMenu();
		});
		$("#cancelBtn").click(function(){
			$('#addMenu').window('close');
		});
		
		$("#twoChildSaveBtn").click(function() {
			saveChildMenu();
		});
		$("#twoChildCancelBtn").click(function(){
			$('#addChildMenu').window('close');
		});
		
		$("#thirdLevelSaveBtn").click(function() {
			saveThreeChildMenu();
		});
		$("#thirdLevelCancelBtn").click(function(){
			$('#addThreeChildMenu').window('close');
		});
		
		
		
		$('#addMenu').window({
			title:'新增菜单',
		    width:620,
		    height:412,
		    modal:true,
		    closed:true,
		    left:340,    
		    top:30,
		    onBeforeClose:function(){
		    }
		});
		
		$('#childMenuList').window({
			title:'新增菜单',
		    width:652,
		    height:412,
		    modal:true,
		    closed:true,
		    left:360,    
		    top:40,
		    onBeforeClose:function(){
		    }
		}); 
		
		
		$('#addChildMenu').window({
			title:'新增菜单',
		    width:620,
		    height:465,
		    modal:true,
		    closed:true,
		    left:340,    
		    top:30,
		    onBeforeClose:function(){
		    }
		});
		
		$('#twoChildMenuList').window({
			title:'新增菜单',
		    width:662,
		    height:412,
		    modal:true,
		    closed:true,
		    left:400,    
		    top:60,
		    onBeforeClose:function(){
		    }
		});
		
		$('#addThreeChildMenu').window({
			title:'子菜单列表',
		    width:662,
		    height:470,
		    modal:true,
		    closed:true,
		    left:400,    
		    top:60,
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
		
		$('#menuGrid').datagrid({
			dataType : 'json',
			url : contextPath + '/pages/system/menu/getMenuList.light',
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
				text:'新增菜单',
				iconCls: 'icon-add',
				handler: function(){
					clearFormData();
					addMenu();
					operatingTag = false;
				}
			}],
			columns : [ [ {
				field : 'id',
				title : 'ID',
				width : 150,
				hidden : true
			},{
				field : 'name',
				title : '菜单名称',
				width : 120,
				align : 'center',
				hidden : false
			},{
				field : 'level',
				title : '层级',
				width : 120,
				align : 'center',
				hidden : true
			},{
				field : 'displayOrder',
				title : '排序号',
				width : 120,
				align : 'center',
				hidden : false
			},{
				field : 'url',
				title : 'URL',
				width : 280,
				align : 'center',
				hidden : false
			},{
				field : 'abstractLevel',
				title : '是否可点击',
				width : 120,
				align : 'center',
				hidden : false,
				formatter : function(value, row, index) {
					if (row.abstractLevel == 'N') {
						return "<img title='是' style='width: 22px;' src='"+contextPath+"/pages/images/icon/check.png'/>";
					} else {
						return "<img title='否' style='width: 22px;' src='"+contextPath+"/pages/images/icon/unCheck.png'/>";
					}
				}
			},{
				field : 'status',
				title : '是否启用',
				width : 60,
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
				title : '操作',
				width : 100, 
				align : 'center',
				hidden : false,
				formatter : function(value,row,index){
					if (row.abstractLevel == 'N') {
						return "";
					} else {
						return "<button style='width: inherit;' onclick=\"checkChild("+row.id+",'"+row.level+"','"+row.status+"','"+row.name+"')\">查看子菜单</button>";
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
	
function addMenu(){
	$('#level').val(1);
	$('#addMenu').window('open');
}

function newChildMenu(){
	$('#childLevel').val(2);
	$('#parentName').combobox({
		url : contextPath + "/pages/system/menu/getMenuListByCombobox.light?abstractLevel=Y",
		valueField : "id",
		textField : "text",
		panelWitdh : 320,
		panelHeight : 200,
		width : 320,
		height : 30,
		value : "",
		formatter :  function(row){
			var ip = $("#parentName").parent().find('.combo').children().eq(1);
			var comb = $(this).combobox('options');
			$(ip).click(function(){
				$('#parentName').combo('showPanel');	
			});
		    var s = '<span style="font-weight:bold">' + row.text + '</span><br/>' +
		            '<span style="color:#888">' + row.desc + '</span>';
		    return s;
		},
		onLoadSuccess:function(){
            $('#parentName').combobox('setValue',$('#parentId').val());
        }
	});
	$('#addChildMenu').window('open');
}

function addThreeChildMenu(){
	$('#threeChildLevel').val(3);
	$('#twoParentName').combobox({
		url : contextPath + "/pages/system/menu/getMenuListByCombobox.light?abstractLevel=Y",
		valueField : "id",
		textField : "text",
		panelWitdh : 320,
		panelHeight : 200,
		width : 320,
		height : 30,
		value : "",
		formatter :  function(row){
			var ip = $("#twoParentName").parent().find('.combo').children().eq(1);
			var comb = $(this).combobox('options');
			$(ip).click(function(){
				$('#twoParentName').combo('showPanel');	
			});
		    var s = '<span style="font-weight:bold">' + row.text + '</span><br/>' +
		            '<span style="color:#888">' + row.desc + '</span>';
		    return s;
		},
		onLoadSuccess:function(){
            $('#twoParentName').combobox('setValue',$('#childParentId').val());
        }
	});
	$('#addThreeChildMenu').window('open');
}



function checkChild(id,level,status,name) {
	$('#parentId').val(id);
	$('#childMenuList').window('open');
	$('#childMenuGrid').datagrid({
		dataType : 'json',
		url : contextPath + '/pages/system/menu/getMenuListByParentId.light',
//		width : $(window).width() * 1,
		height :377,
		singleSelect : true,
		rownumbers : true,
		pagination : true,
		striped : true,
		idField : 'id',
		pageSize : 20,
		queryParams: {
			parentId: id
		},
		toolbar: [
        {
			text:'新增菜单',
			iconCls: 'icon-add',
			handler: function(){
				clearChildFormData();
				newChildMenu();
			}
		}],
		columns : [ [ {
			field : 'id',
			title : 'ID',
			width : 150,
			hidden : true
		},{
			field : 'name',
			title : '菜单名称',
			width : 120,
			align : 'center',
			hidden : false
		},{
			field : 'level',
			title : '层级',
			width : 120,
			align : 'center',
			hidden : true
		},{
			field : 'displayOrder',
			title : '排序号',
			width : 50,
			align : 'center',
			hidden : false
		},{
			field : 'url',
			title : 'URL',
			width : 280,
			align : 'center',
			hidden : false
		},{
			field : 'abstractLevel',
			title : '是否可点击',
			width : 110,
			align : 'center',
			hidden : true,
			formatter : function(value, row, index) {
				if (row.abstractLevel == 'N') {
					return "<img title='是' style='width: 22px;' src='"+contextPath+"/pages/images/icon/check.png'/>";
				} else {
					return "<img title='否' style='width: 22px;' src='"+contextPath+"/pages/images/icon/unCheck.png'/>";
				}
			}
		},{
			field : 'status',
			title : '是否启用',
			width : 60,
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
			title : '操作',
			width : 100, 
			align : 'center',
			hidden : false,
			formatter : function(value,row,index){
				if (row.abstractLevel == 'N') {
					return "末级菜单";
				} else {
					return "<button style='width: inherit;' onclick=\"checkTwoChild("+row.id+",'"+row.level+"','"+row.status+"')\">查看子菜单</button>";
				}
			}
		}] ],
		onLoadSuccess : function(data) {

		},
		onLoadError : function() {
			parent.location.href=contextPath+'/pages/system/welcome.light';
		},
		onDblClickRow : function(rowIndex, rowData) {
			openChildWindow(rowIndex, rowData);
		},
		loadFilter : pagerFilter
	});
}

function checkTwoChild(id,level,status) {
	$('#childParentId').val(id);
	$('#twoChildMenuList').window('open');
	$('#twoChildMenuGrid').datagrid({
		dataType : 'json',
		url : contextPath + '/pages/system/menu/getMenuListByParentId.light',
//		width : $(window).width() * 1,
		height :377,
		singleSelect : true,
		rownumbers : true,
		pagination : true,
		striped : true,
		idField : 'id',
		pageSize : 20,
		queryParams: {
			parentId: id
		},
		toolbar: [
        {
			text:'新增三级菜单',
			iconCls: 'icon-add',
			handler: function(){
				clearThreeChildFormData();
				addThreeChildMenu();
				operatingTag = false;
			}
		}],
		columns : [ [ {
			field : 'id',
			title : 'ID',
			width : 150,
			hidden : true
		},{
			field : 'name',
			title : '菜单名称',
			width : 120,
			align : 'center',
			hidden : false
		},{
			field : 'level',
			title : '层级',
			width : 120,
			align : 'center',
			hidden : true
		},{
			field : 'displayOrder',
			title : '排序号',
			width : 50,
			align : 'center',
			hidden : false
		},{
			field : 'url',
			title : 'URL',
			width : 280,
			align : 'center',
			hidden : false
		},{
			field : 'abstractLevel',
			title : '是否可点击',
			width : 110,
			align : 'center',
			hidden : false,
			formatter : function(value, row, index) {
				if (row.abstractLevel == 'N') {
					return "<img title='是' style='width: 22px;' src='"+contextPath+"/pages/images/icon/check.png'/>";
				} else {
					return "<img title='否' style='width: 22px;' src='"+contextPath+"/pages/images/icon/unCheck.png'/>";
				}
			}
		},{
			field : 'status',
			title : '是否启用',
			width : 60,
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
			openThreeChildWindow(rowIndex, rowData);
		},
		loadFilter : pagerFilter
	});
}

function saveMenu() {
	var id = $('#id').val();
	var url = $('#url').val();
	var name = $('#name').val();
	var displayOrder = $('#displayOrder').val();
//	var abstractLevel = $('#abstractLevel').val();
//	var status = $('#status').val();
	var isStatusTemp = "";
	var isAbstractLevelTemp = "";
	var level = 1;
	if ($(":checked[name=status]").val() == undefined) {
		isStatusTemp = "N";
	} else {
		isStatusTemp = $(":checked[name=status]").val();
	}
	if ($(":checked[name=abstractLevel]").val() == undefined) {
		isAbstractLevelTemp = "Y";
	} else {
		isAbstractLevelTemp = $(":checked[name=abstractLevel]").val();
	}
	if (name == '') {
		$('#af-showreq').click();
		return;
	}

	$.ajax({
		url : contextPath+"/pages/system/menu/saveMenu.light",
		type: "POST",
		dataType:'json',
		data:
		{
			"id":id,
			"name":name,
			"url":url,
			"displayOrder":displayOrder,
			"abstractLevel":isAbstractLevelTemp,
			"status":isStatusTemp,
			"level":level
		},
		success : function(data) {
			$('#menuGrid').datagrid("reload");
			$('#addMenu').window('close');
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

function saveChildMenu() {
	var id = $('#childId').val();
	var parentId = $('#parentName').combobox('getValue');
	var url = $('#childUrl').val();
	var name = $('#childName').val();
	var displayOrder = $('#childDisplayOrder').val();
//	var abstractLevel = $('#childAbstractLevel').val();
//	var status = $('#childStatus').val();
	var isStatusTemp = "";
	var isAbstractLevelTemp = "";
	var level = 2;
	if ($(":checked[name=childStatus]").val() == undefined) {
		isStatusTemp = "N";
	} else {
		isStatusTemp = $(":checked[name=childStatus]").val();
	}
	if ($(":checked[name=childAbstractLevel]").val() == undefined) {
		isAbstractLevelTemp = "Y";
	} else {
		isAbstractLevelTemp = $(":checked[name=childAbstractLevel]").val();
	}
	if (name == '') {
		$('#af-showreq').click();
		return;
	}
	
	$.ajax({
		url : contextPath+"/pages/system/menu/saveMenu.light",
		type: "POST",
		dataType:'json',
		data:
		{
			"id":id,
			"name":name,
			"url":url,
			"displayOrder":displayOrder,
			"abstractLevel":isAbstractLevelTemp,
			"status":isStatusTemp,
			"level":level,
			"parentId":parentId
		},
		success : function(data) {
			$('#childMenuGrid').datagrid("reload");
			$('#addChildMenu').window('close');
			$.messager.show({
                title:'提示',
                msg:'<div class="messager-icon messager-info"></div>'+data.msg,
                timeout:1500,
                showType:'slide'
			});
			clearChildFormData();
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

function saveThreeChildMenu() {
	var id = $('#threeChildId').val();
	var parentId = $('#twoParentName').combobox('getValue');
	
	var url = $('#threeChildUrl').val();
	var name = $('#threeChildName').val();
	var displayOrder = $('#threeChildDisplayOrder').val();
//	var abstractLevel = $('#threeChildAbstractLevel').val();
//	var status = $('#threeChildStatus').val();
	var isStatusTemp = "";
	var isAbstractLevelTemp = "";
	var level = 3;
	if ($(":checked[name=threeChildStatus]").val() == undefined) {
		isStatusTemp = "N";
	} else {
		isStatusTemp = $(":checked[name=threeChildStatus]").val();
	}
	if ($(":checked[name=threeChildAbstractLevel]").val() == undefined) {
		isAbstractLevelTemp = "Y";
	} else {
		isAbstractLevelTemp = $(":checked[name=threeChildAbstractLevel]").val();
	}
	if (name == '') {
		$('#af-showreq').click();
		return;
	}
	
	$.ajax({
		url : contextPath+"/pages/system/menu/saveMenu.light",
		type: "POST",
		dataType:'json',
		data:
		{
			"id":id,
			"name":name,
			"url":url,
			"displayOrder":displayOrder,
			"abstractLevel":isAbstractLevelTemp,
			"status":isStatusTemp,
			"level":level,
			"parentId":parentId
		},
		success : function(data) {
			$('#twoChildMenuGrid').datagrid("reload");
			$('#addThreeChildMenu').window('close');
			$.messager.show({
                title:'提示',
                msg:'<div class="messager-icon messager-info"></div>'+data.msg,
                timeout:1500,
                showType:'slide'
			});
			clearThreeChildFormData();
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
	var rows = $('#menuGrid').datagrid('getSelections');
	return rows;
}

function openWindow(rowIndex, rowData) {
	clearFormData();
	var id = rowData.id;
	var url = rowData.url
	var name = rowData.name;
	var displayOrder = rowData.displayOrder;
	var abstractLevel = rowData.abstractLevel;
	var status = rowData.status;
	$('#id').val(id);
	$('#name').val(name);
	$('#url').val(url);
	$('#displayOrder').val(displayOrder);
	if (abstractLevel === 'N') {
		$("input[id='abstractLevel']").prop("checked",true);
	}
	if (status === 'Y') {
		$("input[id='status']").prop("checked",true);
	}
	$('#addMenu').window('open');
}

function openChildWindow(rowIndex, rowData) {
	clearChildFormData();
	var id = rowData.id;
	var url = rowData.url
	var name = rowData.name;
	var displayOrder = rowData.displayOrder;
	var abstractLevel = rowData.abstractLevel;
	var status = rowData.status;
	$('#childId').val(id);
	$('#childName').val(name);
	$('#childUrl').val(url);
	$('#childDisplayOrder').val(displayOrder);
	if (abstractLevel === 'N') {
		$("input[id='childAbstractLevel']").prop("checked",true);
	}
	if (status === 'Y') {
		$("input[id='childStatus']").prop("checked",true);
	}
	$('#parentName').combobox({
		url : contextPath + "/pages/system/menu/getMenuListByCombobox.light?abstractLevel=Y",
		valueField : "id",
		textField : "text",
		panelWitdh : 320,
		panelHeight : 200,
		width : 320,
		height : 30,
		value : "",
		formatter :  function(row){
			var ip = $("#parentName").parent().find('.combo').children().eq(1);
			var comb = $(this).combobox('options');
			$(ip).click(function(){
				$('#parentName').combo('showPanel');	
			});
		    var s = '<span style="font-weight:bold">' + row.text + '</span><br/>' +
		            '<span style="color:#888">' + row.desc + '</span>';
		    return s;
		},
		onLoadSuccess:function(){
            $('#parentName').combobox('setValue',$('#parentId').val());
        }
	});
	$('#addChildMenu').window('open');
}

function openThreeChildWindow(rowIndex, rowData) {
	clearThreeChildFormData();
	var id = rowData.id;
	var url = rowData.url
	var name = rowData.name;
	var displayOrder = rowData.displayOrder;
	var abstractLevel = rowData.abstractLevel;
	var status = rowData.status;
	$('#threeChildId').val(id);
	$('#threeChildName').val(name);
	$('#threeChildUrl').val(url);
	$('#threeChildDisplayOrder').val(displayOrder);
	if (abstractLevel === 'N') {
		$("input[id='threeChildAbstractLevel']").prop("checked",true);
	}
	if (status === 'Y') {
		$("input[id='threeChildStatus']").prop("checked",true);
	}
	$('#twoParentName').combobox({
		url : contextPath + "/pages/system/menu/getMenuListByCombobox.light?abstractLevel=Y",
		valueField : "id",
		textField : "text",
		panelWitdh : 320,
		panelHeight : 200,
		width : 320,
		height : 30,
		value : "",
		formatter :  function(row){
			var ip = $("#twoParentName").parent().find('.combo').children().eq(1);
			var comb = $(this).combobox('options');
			$(ip).click(function(){
				$('#twoParentName').combo('showPanel');	
			});
		    var s = '<span style="font-weight:bold">' + row.text + '</span><br/>' +
		            '<span style="color:#888">' + row.desc + '</span>';
		    return s;
		},
		onLoadSuccess:function(){
            $('#twoParentName').combobox('setValue',$('#childParentId').val());
        }
	});
	$('#addThreeChildMenu').window('open');
}

function clearChildFormData() {
	$('#childId').val("");
	$('#childName').val("");
	$('#childUrl').val("");
	$('#childDisplayOrder').val("");
	$("input[id='childAbstractLevel']").prop("checked",false);
	$("input[id='childStatus']").prop("checked",false);
}

function clearThreeChildFormData() {
	$('#threeChildId').val("");
	$('#twoParentName').val("");
	$('#threeChildName').val("");
	$('#threeChildUrl').val("");
	$('#threeChildDisplayOrder').val("");
	$("input[id='threeChildAbstractLevel']").prop("checked",false);
	$("input[id='threeChildStatus']").prop("checked",false);
}

function clearFormData() {
	$('#id').val("");
	$('#name').val("");
	$('#url').val("");
	$('#displayOrder').val("");
	$("input[id='abstractLevel']").prop("checked",false);
	$("input[id='status']").prop("checked",false);
}

function searchExpressInfo() {
	var queryParams = $("#queryParams").val();
	queryParams = encodeURI(queryParams);
	var obj = {
		"queryParams" : queryParams
	};
	$('#menuGrid').datagrid("loadData", []);
	$('#menuGrid').datagrid("clearSelections");
	//		
	$('#menuGrid').datagrid({
		url : contextPath+ "/pages/system/menu/getMenuList.light?queryParams="+ queryParams 
	});

	var paper = $('#menuGrid').datagrid('getPager');
	$(paper).pagination('refresh', {
		pageNumber : 1
	});
	
}

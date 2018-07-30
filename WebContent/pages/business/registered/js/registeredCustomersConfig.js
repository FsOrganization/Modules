$(document).ready(function() {
					// 输入框按回车
					$("#queryParams").bind("keydown", function(e) {
						var keycode = e.which;
						if (keycode == 13) {// 输入回车判定
							searchRegisteredCustomers();
							e.preventDefault();
						}
					});

					$("#saveBtn").click(function() {
						setCustomerBeProviderContacts();
					});
					$("#cancelBtn").click(function() {
						$('#setConfig').window('close');
					});

					$('#setConfig').window({
						title : '新增设置',
						width : 580,
						height : 245,
						modal : true,
						closed : true,
						left : 340,
						top : 30,
						onBeforeClose : function() {
						}
					});

					$('#expressServiceId').combobox({
						url : contextPath + "/pages/system/getExpressServiceProviderInfo.light",
						valueField : "id",
						textField : "text",
						panelWitdh : 180,
						panelHeight : 260,
						width : 280,
						height : 30,
						value : "",
						formatter : function(row) {
							var ip = $("#expressServiceId").parent().find('.combo').children().eq(1);
							var comb = $(this).combobox('options');
							$(ip).click(function() {
								$('#expressServiceId').combo('showPanel');
							});
							var s = '<span style="font-weight:bold">'
									+ row.text
									+ '</span><br/>'
									+ '<span style="color:#888">'
									+ row.desc + '</span>';
							return s;
						}
					});
					$('#customerContainer').window({
						title : '信息',
						width : 650,
						height : 300,
						modal : true,
						closed : true,
						maximizable : false,
						resizable : false,
						left : 220,
						top : 90,
						onBeforeClose : function() {
							currWinName = "";
						}
					});

					$('#statisticalArea').combotree({
						url : contextPath + "/pages/app/config/getShopGroupByArea.light",
						panelWitdh : 180,
						panelHeight : 245,
						width : 180,
						height : 30,
						required : false,
						onClick : function(node) {
//							console.info($('#statisticalArea').combotree('getValues'));
							searchRegisteredCustomers(node.type, node.code);
							tempType = node.type;
							tempCode = node.code;
						}
					});

					$("#queryParams").bind("keydown", function(e) {
						var keycode = e.which;
						if (keycode == 13) {// 输入回车判定
						// searchExpressInfo();
							e.preventDefault();
						}
					});

					$('#registeredCustomersConfigGrid')
							.datagrid(
									{
										dataType : 'json',
										url : contextPath
												+ '/pages/app/config/registeredCustomers.light',
										width : $(window).width() * 0.99,
										height : ($(window).height() - 32) * 0.99,
										singleSelect : true,
										rownumbers : true,
										pagination : true,
										striped : true,
										idField : 'id',
										pageSize : 50,
										// queryParams: {
										// batchNumber: ''
										// },
										toolbar : [],
										columns : [ [
												{
													field : 'id',
													title : 'ID',
													width : 60,
													hidden : false
												},
												{
													field : 'name',
													title : '姓名',
													width : 120,
													align : 'center',
													hidden : false
												},
												{
													field : 'phoneNumber',
													title : '手机号码',
													width : 150,
													align : 'center',
													hidden : false
												},
												{
													field : 'loginName',
													title : '登录名',
													width : 125,
													align : 'center',
													hidden : false
												},
												{
													field : 'shopName',
													title : '网点',
													width : 125,
													align : 'center',
													hidden : false,
													formatter : function(value,
															row, index) {
														return row.shopName
																+ '('
																+ row.shopCode
																+ ')';
													}
												},
												{
													field : 'areaName',
													title : '区域',
													width : 125,
													align : 'center',
													hidden : false,
													formatter : function(value,
															row, index) {
														return row.areaName
																+ '('
																+ row.areaCode
																+ ')';
													}
												},
												{
													field : 'loginFrequency',
													title : '登录频率',
													width : 95,
													align : 'center',
													hidden : false
												},
												{
													field : 'opara',
													title : '编辑',
													width : 150,
													align : 'center',
													hidden : false,
													formatter : function(value, row, index) {
														if (row.providerContacts.length == 0) {
															return "<button style='width: inherit;' onclick=\"setConfig('"
																	+ row.phoneNumber
																	+ "','"
																	+ row.shopCode
																	+ "','"
																	+ row.areaCode
																	+ "','"
																	+ row.name
																	+ "')\">设置为快递联系人</button>";
														} else {
															return "<button style='width: inherit;' onclick=\'registeredCustomerWindow("+JSON.stringify(row)+")\'>查看信息</button>";
														}
													}
												} ] ],
										onLoadSuccess : function(data) {

										},
										onLoadError : function() {
											parent.location.href = contextPath
													+ '/pages/system/welcome.light';
										},
										onDblClickRow : function(rowIndex, rowData) {
											openWindow(rowIndex, rowData);
										},
										loadFilter : pagerFilter
									});

					// $('#registeredCustomersConfigGrid').pagination({
					// onSelectPage:function(pageNumber, pageSize){
					// alert('pageNumber:'+pageNumber+',pageSize:'+pageSize);
					// }
					// });
				});

function setConfig(phoneNumber, shopCode, areaCode, name) {
	clearFormData();
	$('#customerName').val(name);
	$('#areaCode').val(areaCode);
	$('#shopCode').val(shopCode);
	$('#phoneNumber').val(phoneNumber);
	$('#setConfig').window('open');
}
function registeredCustomerWindow(row) {
	getdata(row.phoneNumber, row.shopCode, row.areaCode);
	$("#customerContainer").panel({title:row.name+'的快递公司信息'});
	$('#customerContainer').window('open');
}

function getSelections() {
	var rows = $('#registeredCustomersConfigGrid').datagrid('getSelections');
	return rows;
}

function openWindow(rowIndex, rowData) {
	clearFormData();
	$('#customerName').val(rowData.name);
	$('#areaCode').val(rowData.areaCode);
	$('#shopCode').val(rowData.shopCode);
	$('#phoneNumber').val(rowData.phoneNumber);
	$('#setConfig').window('open');
}

function clearFormData() {
//	$('#expressServiceId').combo('setValue',null);
	$('#customerName').val("");
	$('#areaCode').val("");
	$('#shopCode').val("");
	$('#phoneNumber').val("");
	$('#expressServiceId').combo('setValue', "").combo('setText', "请选择快递服务商");
}

function searchRegisteredCustomers(type, code) {	
	$('#registeredCustomersConfigGrid').datagrid("loadData", []);
	$('#registeredCustomersConfigGrid').datagrid("clearSelections");
	$('#registeredCustomersConfigGrid').datagrid({
		dataType : 'json',
		url : contextPath
				+ '/pages/app/config/registeredCustomers.light',
		width : $(window).width() * 0.99,
		height : ($(window).height() - 32) * 0.99,
		singleSelect : true,
		rownumbers : true,
		pagination : true,
		striped : true,
		idField : 'id',
		pageSize : 50,
		queryParams : {
			phoneNumber : $('#queryParams').val(),
			shopCode : code
		},
		toolbar : [],
		columns : [ [
				{
					field : 'id',
					title : 'ID',
					width : 60,
					hidden : false
				},
				{
					field : 'name',
					title : '姓名',
					width : 120,
					align : 'center',
					hidden : false
				},
				{
					field : 'phoneNumber',
					title : '手机号码',
					width : 150,
					align : 'center',
					hidden : false
				},
				{
					field : 'loginName',
					title : '登录名',
					width : 125,
					align : 'center',
					hidden : false
				},
				{
					field : 'shopName',
					title : '网点',
					width : 125,
					align : 'center',
					hidden : false,
					formatter : function(value, row, index) {
						return row.shopName + '('
								+ row.shopCode + ')';
					}
				},
				{
					field : 'areaName',
					title : '区域',
					width : 125,
					align : 'center',
					hidden : false,
					formatter : function(value, row, index) {
						return row.areaName + '('
								+ row.areaCode + ')';
					}
				},
				{
					field : 'loginFrequency',
					title : '登录频率',
					width : 95,
					align : 'center',
					hidden : false
				},
				{
					field : 'opara',
					title : '编辑',
					width : 150,
					align : 'center',
					hidden : false,
					formatter : function(value, row, index) {
						if (row.providerContacts.length == 0) {
							return "<button style='width: inherit;' onclick=\"setConfig('"+ row.phoneNumber+ "','"+ row.shopCode+ "','"+ row.areaCode+ "','"+ row.name+ "')\">设置为快递联系人</button>";
						} else {
							return "<button style='width: inherit;' onclick=\'registeredCustomerWindow("+JSON.stringify(row)+")\'>查看信息</button>";
						}
					}
				} ] ],
		onLoadSuccess : function(data) {

		},
		onLoadError : function() {
			parent.location.href = contextPath
					+ '/pages/system/welcome.light';
		},
		onDblClickRow : function(rowIndex, rowData) {
		},
		loadFilter : pagerFilter
	});
	var paper = $('#registeredCustomersConfigGrid').datagrid('getPager');

}

function getdata(phoneNumber, shopCode, areaCode) {
	$.ajax({
		url : contextPath + "/pages/app/config/getCustomerProviderContacts.light",
		type : "POST",
		dataType : 'json',
		data : {
			"areaCode" : areaCode,
			"shopCode" : shopCode,
			"phoneNumber" : phoneNumber
		},
		success : function(data) {
			let customerContainer = $("#customerContainer");
			$(customerContainer).empty();
			$.each(data, function(i) {
				let infolis = $('<ul id="infolis" class="ul-main-cs"></ul>');
				let li = $("<li class='ul-li-cs'>"+data[i].phoneNumber+","+data[i].shopName+","+data[i].providerName+"</li>");
				$(infolis).append(li);
				$(customerContainer).append(infolis);
			});
		},
		error : function(data) {
			$.messager.show({
				title : '提示',
				msg : '<div class="messager-icon messager-info"></div>' + data,
				timeout : 1500,
				showType : 'slide'
			});
		}
	});
}

function setCustomerBeProviderContacts() {
	let name = $('#customerName').val();
	let areaCode = $('#areaCode').val();
	let shopCode = $('#shopCode').val();
	let phoneNumber = $('#phoneNumber').val();
	let expressServiceId = $('#expressServiceId').combo('getValue');
	if (expressServiceId == '') {
		$('#af-showreq').click();
		return;
	}
	$.ajax({
		url : contextPath
				+ "/pages/app/config/setCustomerBeProviderContacts.light",
		type : "POST",
		dataType : 'json',
		data : {
			"name" : name,
			"areaCode" : areaCode,
			"shopCode" : shopCode,
			"phoneNumber" : phoneNumber,
			"providerId" : expressServiceId
		},
		success : function(data) {
			$('#registeredCustomersConfigGrid').datagrid("reload");
			$('#setConfig').window('close');
			$.messager.show({
				title : '提示',
				msg : '<div class="messager-icon messager-info"></div>'
						+ data.msg,
				timeout : 1500,
				showType : 'slide'
			});
			clearFormData();
		},
		error : function(data) {
			clearFormData();
			$.messager.show({
				title : '提示',
				msg : '<div class="messager-icon messager-info"></div>'
						+ data.msg,
				timeout : 1500,
				showType : 'slide'
			});
		}
	});

}

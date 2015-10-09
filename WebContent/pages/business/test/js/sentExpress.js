var obj;
var tempIds;
var expressServiceMap = {};
var operaTag = null;
function queryData() {
	searchExpressInfo();
}

function modifyExpress() {
	$.ajax({
		url : contextPath+"/pages/system/editDataById.light",
		type: "POST",
		dataType:'json',
		data:{
			"id":$('#id').val(),
			"logistics":$('#modify_logistics').val(),
			"recipientName":$('#modify_recipientName').val(),
			"phoneNumber":$('#modify_phoneNumber').val(),
			"expressLocation":$('#modify_expressLocation').val()
		},
		success : function(data) {
			$('#sentExpressGrid').datagrid('reload');
			$.messager.show({
                title:'提示',
                msg:'<div class="messager-icon messager-info"></div>'+data.msg,
                timeout:3800,
                showType:'slide'
		  });
		$('#detail').dialog('close');
		},
		error : function(data) {
			$.messager.show({
                title:'提示',
                msg:'<div class="messager-icon messager-info"></div>'+data.msg,
                timeout:3800,
                showType:'slide'
		  });
		  }
	});
}

$(document).ready(function(){
		$('#id').prop('readonly', true);
		$("#name").bind("keydown",function(e){
			var keycode = e.which;
			
			//输入回车判定
			if(keycode == 13){
				submitForm();
				e.preventDefault();						
			}
		});
		
		$("#saveBtn").click(function(){
			submitForm();
		});
		
		
		$("#cancelBtn").click(function(){
			$('#dataForm').window('close');
		});
		
		//输入框按回车
		$("#queryParams").bind("keydown",function(e){
			var keycode = e.which;
			if(keycode == 13){//输入回车判定
				queryData();
				e.preventDefault();						
			}
		});	
		
		$("#modifyBtn").click(function() {
			modifyExpress();
		});
		
		
		$('#signatureRegion').window({
			title:'取件确认',
			width:560,
			height:395,
		    modal:true,
		    closed:true,
		    maximizable:false
		});
		initExpressServiceProviders();
		
		$('#dataForm').window({
			title:'新增寄件',
		    width:580,
		    height:468,
		    modal:true,
		    closed:true,
		    left:340,    
		    top:30,
		    onBeforeClose:function(){
		    },
		    onOpen:function(){
//		    	$('#formExpressServiceId').combobox('clear');
		    }
		}); 
		
		
		$('#senderNumber').combobox({
			url : contextPath + "/pages/system/getCustomeInfoList.light",
			valueField : "text",
			textField : "id",
			panelWitdh : 180,
			panelHeight : 160,
			width : 280,
			height : 35,
			value : "",
			hasDownArrow : false,
			formatter :  function(row){
				var ip = $("#senderNumber").parent().find('.combo').children().eq(1);
				var comb = $(this).combobox('options');
				$(ip).click(function(){
					$('#senderNumber').combo('showPanel');	
				});
				$(ip).bind("keydown",function(e){
					$(this).css("background-color", "#D6D6FF");
					$(this).css("font-weight", "bolder");
				});
			    var s = '<span style="font-weight:bold">' + row.text + '</span><br/>' +
			            '<span style="color:#888">' + row.desc + '</span>';
			    return s;
			},
			onSelect:function(row){
				$('#senderName').val(row.text);
//				$('.combo-panel').hide();
			}
		});
		
		$('#sentExpressGrid').datagrid({
//			dataType : 'json',
			url : contextPath + '/pages/system/getSentExpressInfo.light',
			width : $(window).width() * 0.98,
			height :($(window).height()-32)*0.99,
			singleSelect : false,
			rownumbers : true,
			pagination : true,
			striped : true,
			idField : 'ID',
			pageSize : 20,
			showFooter: true,
			toolbar: [
	        {
				text:'查询快件',
				iconCls: 'icon-search',
				handler: function()
				{
					searchExpressInfo();
				}
			},{
				text:'新增寄件',
				iconCls: 'icon-search',
				handler: function()
				{
					operaTag = 'new';
					$('#sentExpressId').val('');
					$('#logistics').val('');
//					$('#phoneNumber').val('');
					$('#recipientName').val('');
//					$('#destination').val('');
					$('#senderNumber').combobox('setValue','');
					$('#senderName').val('');
					$('#address').val('');
					$('#res').val('');
					$('#price').val('');
					
					$('#formExpressServiceId').combobox('setValue','');
					$('#dataForm').window('open');
				}
			}],
			columns : [ [{
				field : 'ID',
				title : 'ID',
				width : 100,
				align : 'center',
				hidden : true
			},{
				field : 'LOGISTICS',
				title : '快件运单号',
				width : 180,
				align : 'center'
			},{
				field : 'RES',
				title : '物品',
				width : 100,
				align : 'center',
				hidden : false
			},{
				field : 'RECIPIENT_NAME',
				title : '收件人姓名',
				width : 120,
				align : 'center',
				hidden : true
			},{
				field : 'PHONE_NUMBER',
				title : '收件手机号码',
				width : 120,
				align : 'center',
				hidden : false
			},{
				field : 'DESTINATION',
				title : '收件地址',
				width : 100,
				align : 'center',
				hidden : true
			},{
				field : 'SENDER_NAME',
				title : '寄件人姓名',
				width : 120,
				align : 'center',
				hidden : false
			},{
				field : 'SENDER_NUMBER',
				title : '寄件人手机号码',
				width : 120,
				align : 'center',
				hidden : false,
				formatter : function(value, row, index){
					return formatPhoneNumber(value);
				}
			},{
				field : 'ADDRESS',
				title : '寄件地址',
				width : 100,
				align : 'center',
				hidden : true
			},{
				field : 'EXPRESS_SERVICE_ID',
				title : '快件服务商',
				width : 100,
				align : 'center',
				hidden : false,
				formatter : function(value, row, index){
					return formatColumnTitle(value);
				}
			},{
				field : 'PRICE',
				title : '价格',
				width : 100,
				sum: 'true',
				align : 'center',
				hidden : false,
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
				field : 'OPERATOR',
				title : '操作人员',
				width : 100,
				align : 'center',
				hidden : false
			},{
				field : 'OPERA_TIME',
				title : '创建时间',
				width : 200,
				align : 'center',
				hidden : false
			},{
				field : 'REMARK',
				title : '备注',
				width : 200,
				align : 'center',
				hidden : true
			}] ],
			onLoadSuccess : function(data){
				$('#expressServiceId').combobox({
					url : contextPath + "/pages/system/getExpressServiceProviderInfo.light",
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
					}
				});
				
				$("#queryParams").bind("click",function(e){
					$("#queryParams").focus();
				});
				 $('#sentExpressGrid').datagrid('statistics');
				//totalPrice();
			},onLoadError : function() {
				parent.location.href=contextPath+'/pages/system/welcome.light';
			},
			onDblClickRow : function(rowIndex, rowData) {
//				sentExpressId
				operaTag = 'modify';
				openWindow(rowIndex, rowData);
			},
			loadFilter : pagerFilter
		});
	});

$.extend($.fn.datagrid.methods, { statistics : function(jq) {
		var opt = $(jq).datagrid('options').columns;
		var rows = $(jq).datagrid("getRows");
		var footer = new Array();
		footer['sum'] = "";
		for (var i = 0; i < opt[0].length; i++) {
			if (opt[0][i].sum) {
				footer['sum'] = footer['sum'] + sum(opt[0][i].field, 1) + ',';
			}
		}
		var footerObj = new Array();
		if (footer['sum'] != "") {
			var tmp = '{'
					+ footer['sum'].substring(0, footer['sum'].length - 1)
					+ "}";
			var obj = eval('(' + tmp + ')');
			if (obj[opt[0][0].field] == undefined) {
				footer['sum'] += '"' + opt[0][0].field + '":"<b>合计:</b>"';
				// 第0列显示为合计
				obj = eval('({' + footer['sum'] + '})');
			} else {
				obj[opt[0][0].field] = "<b>合计:</b>" + obj[opt[0][0].field];
			}
			footerObj.push(obj);
		}
		if (footerObj.length > 0) {
			$(jq).datagrid('reloadFooter', footerObj);
		}
		function sum(filed) {
			var sumNum = 0;
			var str = "";
			for (var i = 0; i < rows.length; i++) {
				var num = rows[i][filed];
				sumNum += Number(num);
			}
			return '"' + filed + '":"' + sumNum.toFixed(2) + '"';
		}
	}
});

function totalPrice(){
	$('#sentExpressGrid').datagrid('appendRow', {
        Saler: '<span class="subtotal">价格合计</span>',
        TotalPriceCount: '<span class="subtotal">' + compute("PRICE") + '</span>'
    });
}

function compute(colName) {
    var rows = $('#sentExpressGrid').datagrid('getRows');
    var total = 0;
    for (var i = 0; i < rows.length; i++) {
    	if (rows[i][colName] != null && rows[i][colName] != '') {
    		total += parseFloat(rows[i][colName]);
		}
    	continue;
        
    }
    return total;
}

function getBarCode(LOGISTICS, name) {
	$.ajax({
		url : contextPath + "/pages/system/getBarCode.light",
		type : "POST",
		dataType : 'json',
		data : {
			"id" : LOGISTICS,
			"name" : name
		},
		success : function(data) {
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
	

function extractCodenotify(PHONE_NUMBER, RECIPIENT_NAME) {

}
	

function initExpressServiceProviders() {
	$.ajax({
		url : contextPath + "/pages/system/initExpressServiceProviders.light",
		type : "POST",
		dataType : 'json',
		sync:false,
		data : {
			"shop_code" : ""
		},
		success : function(data) {
			$.each(data, function(i) {
				// alert(data[i].text);
				expressServiceMap[data[i].id] = data[i].text;
			});
		},
		error : function(data) {
		}
	});
}
	
	function formatColumnTitle(value){
		return expressServiceMap[value];
	}
	
	function formatPhoneNumber(value){
		if (value === undefined) {
			return;
		}
		var t=value.substring(0,3);
		var firstTemp = value.substring(3,7);
		var lastTemp = value.substring(7,11);
		var allTemp = t+"-"+firstTemp+'-'+'<span style="font-weight: bolder;">'+lastTemp+'</span>';
		return '<span style="font-size:1.1em;">'+allTemp+'</span>';
	}
	
	function deleteRow(id,name){
		$.ajax({
			url : contextPath+"/pages/system/deleteCode.light",
			type: "POST",
			dataType:'json',
			data:{"id":id,"name":name},
			success : function(data){
				$('#barimg').attr('src',contextPath+data.PATH);
				openBarCodeWindow(id);
			},
			error : function(data){
				$.messager.show({
	                title:'错误',
	                msg:'<div class="messager-icon messager-info"></div>'+data.PATH,
	                timeout:2000,
	                showType:'slide'
				});
			  }
		});
	}

	function getSelections(){
		var rows = $('#sentExpressGrid').datagrid('getSelections');
		return rows;
	}
	function openWindow(rowIndex, rowData) {
		clearDataForm();
		var id = rowData.ID;
		var logistics = rowData.LOGISTICS;
		var recipientName = rowData.RECIPIENT_NAME;
		var phoneNumber = rowData.PHONE_NUMBER;
		
//		var destination = rowData.DESTINATION;
		var senderNumber = rowData.SENDER_NUMBER;
		var senderName = rowData.SENDER_NAME;
//		var address = rowData.ADDRESS;
		var res = rowData.RES;
		var price = rowData.PRICE;
		
		var expressLocation = rowData.EXPRESS_lOCATION;
		var expressServiceId = rowData.EXPRESS_SERVICE_ID;
		$('#sentExpressId').val(id);
		$('#logistics').val(logistics);
		$('#recipientName').val(recipientName);
		$('#phoneNumber').val(phoneNumber);
		$('#expressLocation').val(expressLocation);
		$("#formExpressServiceId").combobox('setValue',expressServiceId);
		
//		$('#destination').val(destination);
		$('#senderNumber').combobox('setText',senderNumber);
		$('#senderName').val(senderName);
//		$('#address').val(address);
		$('#res').val(res);
		if (price != null && price != 0) {
			$('#price').val(price/100);
		} else {
			$('#price').val(price);
		}
		
		
		$('#dataForm').window('open');
	};
	
	function openBarCodeWindow(id){
		$('#imgDetail').dialog('open');
	}
	
	function submitForm(){
		var expressService = $("#formExpressServiceId").combobox('getValue');
		var logistics = $('#logistics').val();
		var phoneNumber = $('#phoneNumber').val();
		var recipientName = $('#recipientName').val();
//		var destination = $('#destination').val();
		var senderNumber = $('#senderNumber').combobox('getText');
		var senderName = $('#senderName').val();
//		var address = $('#address').val();
		var res = $('#res').val();
		var price = $('#price').val();
		if (!$.isNumeric(price)) {
			$.messager.show({
                title:'提示',
                msg:'<div class="messager-icon messager-info"></div>'+"价格填写不正确",
                timeout:3800,
                showType:'slide'
			});
			return;
		}
		
		var urlStr = "";
		var sentExpressId = "";
		if (operaTag === 'new') {
			urlStr = contextPath+"/pages/system/addSentExpressInfo.light";
		} else if(operaTag === 'modify'){
			urlStr = contextPath+"/pages/system/modifySentExpressInfo.light";
			sentExpressId = $('#sentExpressId').val();
		} else {
			
		}
		if (expressService == '') {
			$.messager.show({
	            title:'提示',
	            msg:'<div class="messager-icon messager-info"></div>'+'请选择快件服务商',
	            timeout:3800,
	            showType:'slide'
			});
			return;
		}
		$.ajax({
			url : urlStr,
			type: "POST",
			dataType:'json',
			data:
			{
				"id":sentExpressId,
				"logistics":logistics,
				"phoneNumber":phoneNumber,
				"recipientName":recipientName,
//				"destination":destination,
				"senderNumber":senderNumber,
				"senderName":senderName,
//				"address":address,
				"res":res,
				"price":price,
				"expressServiceId":expressService
			},
			success : function(data){
				$('#sentExpressGrid').datagrid('reload');
				$.messager.show({
	                title:'提示',
	                msg:'<div class="messager-icon messager-info"></div>'+data.msg,
	                timeout:3800,
	                showType:'slide'
				});
				//$('#detail').dialog('close');
				$('#dataForm').window('close');
			},
			error : function(data){
				$.messager.show({
	                title:'提示',
	                msg:'<div class="messager-icon messager-info"></div>'+data.msg,
	                timeout:3800,
	                showType:'slide'
			  });
			  }
		});
	}
	
	function clearDataForm(){
		$('#sentExpressId').val('');
		$('#logistics').val('');
		$('#phoneNumber').val('');
		$('#recipientName').val('');
//		$('#destination').val('');
		$('#senderNumber').combobox('setValue','');
		$('#senderName').val('');
//		$('#address').val('');
		$('#res').val('');
		$('#price').val('');
		
		$('#formExpressServiceId').combobox('setValue','');
	}
	
	function getSelectRows(){
		var selectedRows = $('#sentExpressGrid').datagrid('getSelections');
		if (selectedRows.length != 0){
			var ids = '';
			for(var i = 0; i < selectedRows.length; i ++){
				if(ids != ''){
					ids += ",";
				}
				ids += selectedRows[i].ID;
			}
		}
		return ids;
	}
	
	function getPhoneNumberBySelectRows(){
		var selectedRows = $('#sentExpressGrid').datagrid('getSelections');
		if (selectedRows.length != 0){
			var cInfo = '';
			for(var i = 0; i < selectedRows.length; i ++){
				if(cInfo != ''){
					cInfo += ",";
				}
				cInfo += selectedRows[i].RECIPIENT_NAME+','+selectedRows[i].PHONE_NUMBER;
			}
		}
		return cInfo;
	}
	
	function searchExpressInfo(){
		var endDate = $("#endDateId").val();
		var startDate = $("#startDateId").val();
		var queryParams = $("#queryParams").val();
		queryParams = encodeURI(queryParams);
		var expressService = $("#expressServiceId").combobox('getValue');
		var obj = 
		{
				"endDate":endDate,
				"startDate":startDate,
				"queryParams":queryParams,
				"expressService":expressService
		};
		$('#sentExpressGrid').datagrid("loadData",[]);
		$('#sentExpressGrid').datagrid("clearSelections");
//		
		$('#sentExpressGrid').datagrid({
			url : contextPath + "/pages/system/getSentExpressInfo.light?endDate="+endDate+"&startDate="+startDate+"&queryParams="+queryParams+"&expressService="+expressService
		});
		
//		$("#number").focus();
		var paper = $('#sentExpressGrid').datagrid('getPager');  
		$(paper).pagination('refresh',{ pageNumber: 1 });
	}
	
	//确认
	function signComplete(){
//		saveSignatureImg();
		var stream = saveSignature();
		if (stream == null || stream == '' || stream.length == 0) {
			$.messager.show({
                title:'提示',
                msg:'<div class="messager-icon messager-info"></div>'+'请签名',
                timeout:3800,
                showType:'slide'
			});
			clearSignatureRegion();
			return;
		}
		$.ajax({
			url : contextPath + "/pages/system/letExpressOutStorehouse.light",
			type : "POST",
			dataType : 'json',
			data :{
				"ids" : tempIds,
				"signatureImg" : stream,
				"type" : "1"
			},
			success : function(result){
				$('#sentExpressGrid').datagrid('clearSelections');
				$('#sentExpressGrid').datagrid("reload");
				$('#signatureRegion').window('close');
				closeSignatureRegion();
			}
		});
		
	}

	
	function formatItem(row){
		var ip = $("#formExpressServiceId").parent().find('.combo').children().eq(1);
		var comb = $(this).combobox('options');
		$(ip).click(function(){
			$('#formExpressServiceId').combo('showPanel');
		});
	    var s = '<span style="font-weight:bold">' + row.text + '</span><br/>' +
	            '<span style="color:#888">' + row.desc + '</span>';
	    return s;
	}
	
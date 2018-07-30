var obj;
var tempIds;
var expressServiceMap = {};
var operaTag = null;
var ajaxTag = true;
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
			"expressLocation":$('#modify_expressLocation').val(),
			"idNumber":$('#modify_idNumber').val()
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
//		$('#id').prop('readonly', true);
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
		$('#formExpressServiceId').combobox({
			url : contextPath + "/pages/system/getExpressServiceProviderInfo.light",
			valueField : "id",
			textField : "text",
			panelWitdh : 180,
			panelHeight : 260,
			width : 280,
			height : 30,
			value : "",
			formatter :  function(row){
				var ip = $("#formExpressServiceId").parent().find('.combo').children().eq(1);
				var comb = $(this).combobox('options');
				$(ip).click(function(){
					$('#formExpressServiceId').combo('showPanel');	
				});
			    var s = '<span style="font-weight:bold">' + row.text + '</span><br/>' +
			            '<span style="color:#888">' + row.desc + '</span>';
			    return s;
			}
		});
		
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
		
		$("#senderNumber").bind("keydown",function(e){
			var keyCode = e.which;
			var realkey = String.fromCharCode(e.which);
			if (realkey==' ') {
				return false;
			}
			if (keyCode == 8 || keyCode == 46) {
				ajaxTag = true;
			}
			
		});
		
		$('#senderNumber').keyup(function(event) {
			var key = event.keyCode;
			if ((key >= 48 && key <= 57) || (key >= 96 && key <= 105) || (key >= 65 && key <= 90) || key == 8 || key == 46) {
			var searchTxt = $('#senderNumber').val();
			if (searchTxt != '' && searchTxt.length >2 && ajaxTag) {
				$('#cat').show();
				$.ajax({
					type : "POST",
					url : contextPath + "/pages/system/customer/getCustomerListByTxt.light",
					data : "queryTxt=" + $('#senderNumber').val(),
					success: function (data) { //请求成功后处理函数。
					changeCoords();
					var objData = eval("(" + data + ")");
					var height = 50;
					if (objData.length > 0) {
						if (objData.length <10) {
							height = 112;
						} else {
							height = 175;
						}
						$('#searchresult').css('height',height);
						$('#searchresult').css('overflow-y','scroll');
						$('#searchresult').css('overflow-x','hidden');
						var layer = "";
						layer = "<table id='aa' style='width: inherit;'>";
						$.each(objData, function (idx, item) {
							layer += "<tr class='line' style='line-height:22px;'>" +
									"<input type='hidden' name='phone' value='"+item.PHONE_NUMBER+"' />"+
									"<input type='hidden' name='name' value='"+item.NAME+"' />"+
									"<td class='std'>" + item.PHONE_NUMBER +','+item.NAME+ "</td></tr>";
						});
						layer += "</table>";
						//将结果添加到div中    
						$("#searchresult").empty();
						$("#searchresult").append(layer);
						$(".line:first").addClass("hover");
						$("#searchresult").css("display", "");
						//鼠标移动事件
						$(".line").hover(function () {
						$(".line").removeClass("hover");
						$(this).addClass("hover");
						}, function () {
						$(this).removeClass("hover");
						//$("#searchresult").css("display", "none");
						});
						//鼠标点击事件
						$(".line").click(function () {
							$("#senderNumber").val($(this).find('input[name=phone]').val());
							$("#senderName").val($(this).find('input[name=name]').val());
							$("#searchresult").css("display", "none");
						});
					} else {
						ajaxTag = false;
						$("#searchresult").empty();
						$("#searchresult").css("display", "none");
					}
					$('#cat').hide(500);
				},
				error : function() {
					alert("服务器异常请稍后再试！");
				}
				});
			}
			} else if (key== 38) {//上箭头
				$('#aa tr.hover').prev().addClass("hover");
				$('#aa tr.hover').next().removeClass("hover");
				$('#senderNumber').val($('#aa tr.hover').text());
			} else if (key== 40) {//下箭头
				$('#aa tr.hover').next().addClass("hover");
				$('#aa tr.hover').prev().removeClass("hover");
				$('#senderNumber').val($('#aa tr.hover').text());
			} else if (key== 13) {//回车
//			 	event.preventDefault();
//			 	event.stopPropagation();
				$('#senderNumber').val($('#aa tr.hover').text());
				$("#senderNumber").val($('#aa tr.hover').find('input[name=phone]').val());
				$("#senderName").val($('#aa tr.hover').find('input[name=name]').val());
				$("#searchresult").empty();
				$("#searchresult").css("display", "none");
				return false;
			} else {
				$("#searchresult").empty();
				$("#searchresult").css("display", "none");
			}
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
		    },
		    onClose:function(){
		    	clearDataForm();
		    },
		}); 

		$('#sentExpressGrid').datagrid({
			dataType : 'json',
			url : contextPath + '/pages/system/getSentExpressInfo.light',
			width : $(window).width()*0.99,
			height :($(window).height()-26)*0.99,
			singleSelect:true,
			rownumbers : true,
			pagination : true,
			striped : true,
			method : 'post',
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
				iconCls: 'icon-add',
				handler: function()
				{
					operaTag = 'new';
					$('#sentExpressId').val('');
					$('#logistics').val('');
//					$('#phoneNumber').val('');
					$('#recipientName').val('');
//					$('#destination').val('');
					$('#senderNumber').val('');
					$('#senderName').val('');
					$('#address').val('');
					$('#res').val('');
					$('#price').val('');
					$('#idNumber').val('');
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
				field : 'ID_NUMBER',
				title : '寄件人身份证号',
				width : 140,
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
				$("#queryParams").bind("click",function(e){
					$("#queryParams").focus();
				});
				$('#sentExpressGrid').datagrid('statistics');
				//totalPrice();
			},onLoadError : function() {
				parent.location.href=contextPath+'/pages/system/welcome.light';
			},
			onDblClickRow : function(rowIndex, rowData) {
				operaTag = 'modify';
				openWindow(rowIndex, rowData);
			}
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
		if (value === undefined || value == '' || value == null) {
			return value;
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
		var idNumber = rowData.ID_NUMBER;
		
		var expressLocation = rowData.EXPRESS_lOCATION;
		var expressServiceId = rowData.EXPRESS_SERVICE_ID;
		$('#sentExpressId').val(id);
		$('#logistics').val(logistics);
		$('#recipientName').val(recipientName);
		$('#phoneNumber').val(phoneNumber);
		$('#expressLocation').val(expressLocation);
		$("#formExpressServiceId").combobox('setValue',expressServiceId);
		
//		$('#destination').val(destination);
		$('#senderNumber').val(senderNumber);
		$('#senderName').val(senderName);
		$('#idNumber').val(idNumber);
		
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
		var senderNumber = $('#senderNumber').val();
		var senderName = $('#senderName').val();
		var idNumber = $('#idNumber').val();
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
		if (idNumber.length==0 || idNumber.length >20) {
			$.messager.show({
                title:'提示',
                msg:'<div class="messager-icon messager-info"></div>'+"身份证号填写不正确",
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
				"idNumber":idNumber,
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
		$('#senderNumber').val('');
		$('#senderName').val('');
//		$('#address').val('');
		$('#res').val('');
		$('#price').val('');
		$('#idNumber').val('');
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
//		$('#sentExpressGrid').datagrid("loadData",[]);
		$('#sentExpressGrid').datagrid('loadData', { total: 0, rows: [] })
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
	
	function changeCoords() {
	    var left = $("#senderNumber").position().left - 1; //获取距离最左端的距离，像素，整型
	    var top = $("#senderNumber").position().top + 36; ; //获取距离最顶端的距离，像素，整型（20为搜索输入框的高度）
	    $("#searchresult").css("left", left + "px"); //重新定义CSS属性
	    $("#searchresult").css("top", top + "px"); //同上
	}
	
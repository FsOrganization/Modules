
var obj;
var tempIds;
var expressServiceMap = {};
var shopNameMap = {};
function queryData() {
	searchExpressInfo();
}

function formatShopCodeColumnTitle(value){
	return shopNameMap[value];
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

function modifyExpress() {
	$.ajax({
		url : contextPath+"/pages/system/editDataById.light",
		type: "POST",
		dataType:'json',
		data:{"id":$('#id').val(),"logistics":$('#modify_logistics').val(),"recipientName":$('#modify_recipientName').val(),"phoneNumber":$('#modify_phoneNumber').val(),"expressLocation":$('#modify_expressLocation').val()},
		success : function(data) {
			$('#areaCodeGrid').datagrid('reload');
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
		
		//输入框按回车
		$("#queryParams").bind("keydown",function(e){
			var keycode = e.which;
			if(keycode == 13){//输入回车判定
				queryData();
				e.preventDefault();						
			}
		});
		
		initShopNameMap();
		
		$("#submitBtn").click(function(){
			submitForm();
		});
		
		$("#modifyBtn").click(function() {
			modifyExpress();
		});
		
		$("#getExpressBtn").click(function() {
			$('#signatureRegion').window('open');
			initializationSignatureRegion();
	    	closeBPopup();
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
		window.onload = function(){
			obj = document.getElementById("HWPenSign");
			obj.HWSetBkColor(0xFFF5EE);
			obj.HWSetCtlFrame(0, 0x000000);
		};
		
		$('#areaCodeGrid').datagrid({
			dataType : 'json',
			url : contextPath + '/pages/system/getExpressInfoList.light',
//			width : $(window).width() * 0.98,
			height :($(window).height()-32)*0.99,
			singleSelect : false,
			rownumbers : true,
			pagination : true,
			striped : true,
			idField : 'ID',
			pageSize : 30,
			queryParams:{
				batchNumber: ''
			},
			toolbar: [
	        {
				text:'查询快件',
				iconCls: 'icon-search',
				handler: function(){
					searchExpressInfo();
				}
			},{
				text:'批量取件',
				iconCls: 'icon-search',
				handler: function(){
					batchLetExpressOutStorehouse();
				}
			},{
				text:'打印出库单',
				iconCls: 'icon-print',
				handler: function(){
					var gridView = $(".datagrid-view");
					$(gridView).jqprint({operaSupport: true});
				}
			}],
			columns : [ [{
				field : 'ID',
				title : 'ID',
				width : 100,
				align : 'center',
				hidden : true
			},{  
				field : 'opara', 
				title : '操作',
				width : 100, 
				align : 'center',
				formatter : function(value,row,index){
					return "<button style='width: inherit;' id='expressOpara' onclick=\"letExpressOutStorehouse("+row.ID+",'"+row.RECIPIENT_NAME+"','"+row.PHONE_NUMBER+"','"+row.EXPRESS_lOCATION+"','"+row.EXPRESS_SERVICE_ID+"','"+row.GENDER+"')\">取件</button>";
				}
			},{
				field : 'LOGISTICS',
				title : '快件运单号',
				width : 220,
				align : 'center'
			},{
				field : 'RECIPIENT_NAME',
				title : '收件人',
				width : 120,
				align : 'center',
				hidden : false
			},{
				field : 'GENDER',
				title : '性别',
				width : 120,
				align : 'center',
				hidden : true
			},{
				field : 'PHONE_NUMBER',
				title : '手机号码',
				width : 120,
				align : 'center',
				hidden : false,
				formatter : function(value, row, index){
					return formatPhoneNumber(value);
				}
			},{
				field : 'EXPRESS_lOCATION',
				title : '货位',
				width : 100,
				align : 'center',
				hidden : false
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
				field : 'ADDRESS',
				title : '地址',
				width : 200,
				align : 'center',
				hidden : true
			},{
				field : 'OPERATOR',
				title : '操作人员',
				width : 100,
				align : 'center',
				hidden : false
			},{
				field : 'OPERA_TIME',
				title : '入库时间',
				width : 200,
				align : 'center',
				hidden : false
			},{
				field : 'BATCH_NUMBER',
				title : '入库批次号',
				width : 85,
				align : 'center',
				hidden : false
			},{
				field : 'REMARK',
				title : '备注',
				width : 200,
				align : 'center',
				hidden : true
			},{
				field : 'SERVICE_SHOP_CODE',
				title : '网点',
				width : 200,
				align : 'center',
				hidden : true
			},{  
				field : 'WEIXIN_ID',
				title : '推送微信通知',
				width : 100, 
				align : 'center',
				hidden : false,
				formatter : function(value,row,index) {
					if (row.WEIXIN_ID === '' || row.WEIXIN_ID ==null) {
						return value;
					} else {
						return "<button onclick=\"pushWechatNotification('"+row.WEIXIN_ID+"','"+formatShopCodeColumnTitle(row.SERVICE_SHOP_CODE)+"','"+formatColumnTitle(row.EXPRESS_SERVICE_ID)+"','"+row.LOGISTICS+"','"+row.OPERA_TIME+"')\">推送微信通知</button>";
					}
				}
			},{  field : 'showBarCode', 
				title : '查看条码',
				width : 100, 
				align : 'center',
				hidden : true,
				formatter : function(value,row,index){
// 					return '<button onclick="getBarCode('+row.id+')" >查看条码</button>';
					return "<button onclick=\"getBarCode("+row.LOGISTICS+",'"+row.RECIPIENT_NAME+"')\">查看条码</button>";
				}
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
			},
			onDblClickRow : function(rowIndex, rowData) {
				openWindow(rowIndex, rowData);
			}
		});
		$('#my-button').bind('click', function(e) {
            e.preventDefault();
            $('#element_to_pop_up').bPopup({
                onOpen: function() {
                	//$('#genderSpan').hide();
                }, 
                onClose: function() {
                	$('#genderSpan').hide();
                },
                modalClose: false,
                opacity: 0.6,
                positionStyle: 'fixed', //'fixed' or 'absolute'
                follow: [false, false], //x, y
                position: [395, 45] //x, y
            }, 
            function() {
                //alert('Callback fired');
            });

        });
		
	});

	function closeBPopup(){
		$('#genderSpan').hide();
		var bPopup = $('#element_to_pop_up').bPopup();
		bPopup.close();
	}
	function getBarCode(LOGISTICS,name){
		$.ajax({
			url : contextPath+"/pages/system/getBarCode.light",
			type: "POST",
			dataType:'json',
			data:{"id":LOGISTICS,"name":name},
			success : function(data){
// 				$.messager.show({
// 	                title:'提示',
// 	                msg:'<div class="messager-icon messager-info"></div>'+data.PATH,
// 	                timeout:3000,
// 	                showType:'slide'
// 				});
				$('#barimg').attr('src',contextPath+data.PATH);
// 				$('#fileName').html(id);
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
	
	function pushWechatNotification(WEIXIN_ID,SHOP_NAME,EXPRESS_SERVICE_NAME,LOGISTICS,OPERA_TIME){
		
		$.messager.confirm('确认对话框', '您确定推送吗？', function(r){
			if (r)
			{
				$.messager.progress(); 
				var msg = '尊敬的客户 您的快递'+LOGISTICS+'('+EXPRESS_SERVICE_NAME+')'+'已于['+OPERA_TIME+']到达 '+SHOP_NAME+' 幸福快递网点 请尽快领取';
				$.ajax({
					url : contextPath + "/pages/system/wechat/sendMessage.light",
					type : "POST",
					dataType : 'json',
					data : {
						"msg" : msg,
						"touser":WEIXIN_ID
					},
					success : function(data){
						$.messager.progress('close');
						$.messager.alert('服务器消息','推送成功,'+'{errcode:'+data.errcode+'errmsg:'+data.errmsg+'}','info');
					},
					error : function(data) {
					}
				});
			}
		});

	}
	
	function initExpressServiceProviders() {
		$.ajax({
			url : contextPath + "/pages/system/initExpressServiceProviders.light",
			type : "POST",
			dataType : 'json',
			data : {
				"shop_code" : ""
			},
			success : function(data) {
				$.each(data, function(i) {    
			           //alert(data[i].text);
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
// 				$.messager.show({
// 	                title:'提示',
// 	                msg:'<div class="messager-icon messager-info"></div>'+data.PATH,
// 	                timeout:3000,
// 	                showType:'slide'
// 				});
				$('#barimg').attr('src',contextPath+data.PATH);
// 				$('#fileName').html(id);
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
		var rows = $('#areaCodeGrid').datagrid('getSelections');
		return rows;
	}
	function openWindow(rowIndex, rowData) {
		var id = rowData.ID;
		var logistics = rowData.LOGISTICS;
		var recipientName = rowData.RECIPIENT_NAME;
		var phoneNumber = rowData.PHONE_NUMBER;
		var expressLocation = rowData.EXPRESS_lOCATION;
		$('#id').val(id);
		$('#modify_logistics').val(logistics);
		$('#modify_recipientName').val(recipientName);
		$('#modify_phoneNumber').val(phoneNumber);
		$('#modify_expressLocation').val(expressLocation);
		$('#detail').dialog('open');
	};
	
	function openBarCodeWindow(id){
		$('#imgDetail').dialog('open');
	}
	
	function submitForm(){
		$.ajax({
			url : contextPath+"/pages/system/editDataById.light",
			type: "POST",
			dataType:'json',
			data:{"id":$('#id').val(),"logistics":$('#logistics').val(),"recipientName":$('#recipientName').val(),"phoneNumber":$('#phoneNumber').val(),"expressLocation":$('#expressLocation').val()},
			success : function(data){
				$('#areaCodeGrid').datagrid('reload');
				$.messager.show({
	                title:'提示',
	                msg:'<div class="messager-icon messager-info"></div>'+data.msg,
	                timeout:3800,
	                showType:'slide'
			  });
			$('#detail').dialog('close');
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
	
	function getSelectRows(){
		var selectedRows = $('#areaCodeGrid').datagrid('getSelections');
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
	
	function getFirstSelectRowGender(){
		var selectedRows = $('#areaCodeGrid').datagrid('getSelections');
		if (selectedRows.length != 0){
			return selectedRows[0].GENDER;
		} else {
			return null;
		}
	}
	
	function getPhoneNumberBySelectRows(){
		var selectedRows = $('#areaCodeGrid').datagrid('getSelections');
		if (selectedRows.length != 0){
			var cInfo = '';
			for(var i = 0; i < selectedRows.length; i ++){
//				if(cInfo != ''){
//					cInfo += ",";
//				}
				cInfo += selectedRows[i].RECIPIENT_NAME+':'+selectedRows[i].PHONE_NUMBER+',货位:'+selectedRows[i].EXPRESS_lOCATION+" ("+formatColumnTitle(selectedRows[i].EXPRESS_SERVICE_ID)+")"+"</br></br>";
			}
		}
		return cInfo;
	}
	
	function batchLetExpressOutStorehouse(){
		$('#genderSpan').hide();
		var ids = null;
		ids = getSelectRows();
		if (ids===undefined ){
			showMsg("请选择快件...", "提示");
			return;
		} else{
			var tempValue = getFirstSelectRowGender();
			if (tempValue != null && tempValue ==='unknown') {
				$('#genderSpan').show();
			}
			var cInfo = getPhoneNumberBySelectRows();
			var titleInfo = "请核对取件人: "+'</br></br>';
			$('#content').empty();
			$('#content').append(titleInfo+cInfo);
			$('#my-button').click();
			tempIds = null;
	    	tempIds = ids;
//			$.messager.confirm('确认',titleInfo+cInfo,function(r){
//			    if (r){
//			    	$('#signatureRegion').window('open');
//					initializationSignatureRegion();
//			    	tempIds = null;
//			    	tempIds = ids;
//			    }    
//			});  
		}

	}

	function letExpressOutStorehouse(id,name,phone,expressLocation,expressServiceId,gender){
		if (gender === 'unknown') {
			$('#genderSpan').show();
		}
		var cInfo = '请核对取件人: '+'</br></br>'+name+','+phone+",货位:"+expressLocation+" ("+formatColumnTitle(expressServiceId)+")" +"</br></br>";
		$('#content').empty();
		$('#content').append(cInfo);
		$('#my-button').click();
		tempIds = null;
    	tempIds = id;
//		$.messager.confirm('确认',cInfo,function(r){
//		    if (r){
//		    	$('#signatureRegion').window('open');
//				initializationSignatureRegion();
//		    	tempIds = null;
//		    	tempIds = id;
//		    }    
//		});

	}
	
//	function getSignatureRegionWindow(){
//		var obj;
//		obj = document.getElementById("HWPenSign"); 
//        obj.HWSetBkColor(0xE0F8E0);  
//        obj.HWSetCtlFrame(2, 0x000000);
//	}
	
//	function getOutStorehouseBatchNumber(){
//		var batchNumber =null;
//		 $.ajax({
//				url : contextPath + "/pages/system/getOutStorehouseBatchNumber.light",
//				type : "POST",
//				dataType : 'json',
////				sync:false,
//				success: function(data){
//					return data.temporaryId;
//				}
//		});
//		
//	}
	
	//查询患者信息
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
		$('#areaCodeGrid').datagrid("loadData",[]);
		$('#areaCodeGrid').datagrid("clearSelections");
//		
		$('#areaCodeGrid').datagrid({
			url : contextPath + "/pages/system/getNotOutExpressInfoByFilterConditions.light?endDate="+endDate+"&startDate="+startDate+"&queryParams="+queryParams+"&expressService="+expressService
		});
		
//		$("#number").focus();
		var paper = $('#areaCodeGrid').datagrid('getPager');  
		$(paper).pagination('refresh',{ pageNumber: 1 });
	}
	//初始化设备
	function initializationSignatureRegion(){
		  res = obj.HWInitialize();
	}
	
	//关闭设备
	function closeSignatureRegion(){
		   var stream;
		   stream = obj.HWFinalize();
	}
	
	//重新签名
	function clearSignatureRegion(){
		   obj.HWClearPenSign();
	}
	
	function signRestart(){
		;
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
				$('#areaCodeGrid').datagrid('clearSelections');
				$('#areaCodeGrid').datagrid("reload");
				$('#signatureRegion').window('close');
				closeSignatureRegion();
			}
		});
		
	}
	
	function saveSignature(){
		   var stream;
		   stream = obj.HWGetBase64Stream(2);
//		   var imgStr = "<img src='"+stream+"' />";
//		   $('#sssff').attr("src", 'data:image/jpg;base64,'+stream);
		   return stream;
	}
	
	//保存图片到磁盘
	function saveSignatureToDisk(){
		   obj.HWSetFilePath("e:\\sign.jpg");
		   obj.HWSaveFile();
	}
	
	//保存图片到数据库
	function saveSignatureToDisk(){
		   obj.HWSetFilePath("e:\\sign.jpg");
		   obj.HWSaveFile();
	}
	
	function saveSignatureByBase64Code(){
		$.ajax({
			url : contextPath + "/pages/system/saveSignature.light",
			type : "POST",
			dataType : 'json',
			sync:false,
			success: function(data){
				return data.temporaryId;
			}
		});
	}
	
	
	
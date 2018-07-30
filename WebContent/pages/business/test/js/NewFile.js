var isBind=false;
var obj;
var tempIds;
var expressServiceMap = {};
var shopNameMap = {};
var tempSpeDate = '2016-3-31';
var cancelSignatureTag = null;//cancelSignatureTag

function queryData() {
	searchExpressInfo();
}
$.extend($.fn.datagrid.methods, {
	keyCtrl : function (jq) {
        return jq.each(function () {
            var grid = $(this);
            var gridObject = grid.datagrid('getPanel').panel('panel').attr('tabindex', 1);
			if (!isBind) {
				gridObject.bind('keydown', function(e) {
					switch (e.keyCode) {
					case 38: // up
						var selected = grid.datagrid('getSelected');
						if (selected) {
							var index = grid.datagrid('getRowIndex', selected);
							if (index == 0) {
								var rows = grid.datagrid('getRows');
								grid.datagrid('selectRow', rows.length - 1);
							} else {
								grid.datagrid('selectRow', index - 1);
							}
						} else {
							var rows = grid.datagrid('getRows');
							grid.datagrid('selectRow', rows.length - 1);
						}
						break;
					case 40: // down
						var selected = grid.datagrid('getSelected');
						if (selected) {
							var index = grid.datagrid('getRowIndex', selected);
							var rows = grid.datagrid('getRows');
							if (index == rows.length - 1) {
								grid.datagrid('selectRow', 0);
							} else {
								grid.datagrid('selectRow', index + 1);
							}
						} else {
							grid.datagrid('selectRow', 0);
						}
						break;
					case 65:
						if (e.ctrlKey) {
							grid.datagrid('selectAll');
						}
						
						break;
					}
				});
				isBind=true;
			} 
        });
    }
});

function formatShopCodeColumnTitle(value){
	return shopNameMap[value];
}

function initShopNameMap() {
	$.ajax({
		url : contextPath + "/pages/system/pageconfig/getServiceShopName.light",
		type : "POST",
		dataType : 'json',
		sync:false,
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
	if (!isPhoneNmuber($('#modify_phoneNumber').val())) {
		$.messager.show({
            title:'提示',
            msg:'<div class="messager-icon messager-info"></div>'+'手机或座机号码填写不正确',
            timeout:3800,
            showType:'slide'
		});
		return;
	}
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
		}, error : function(data) {
			$.messager.show({
                title:'提示',
                msg:'<div class="messager-icon messager-info"></div>'+data.msg,
                timeout:3800,
                showType:'slide'
		  });
		  }
	});
}

function updateCustomerGender(){
	var sex = $("input[name='sex']:checked").val();
	if (sex === undefined) {
		return ;
	} else {
		var phoneNumber = $('#hiddenPhoneNumber').val();
		$.ajax({
			url : contextPath+"/pages/system/customer/updateCustomerGender.light",
			type: "POST",
			dataType:'json',
			data:{"phoneNumber":phoneNumber,"sex":sex},
			success : function(data) {
				$('#hiddenPhoneNumber').val("");
				$("#sex").attr("checked",false);
			}, 
			error : function(data) {
				$('#hiddenPhoneNumber').val("");
				$("#sex").attr("checked",false);
			}
		});
	}
}

function getDays(strDateStart, strDateEnd) {
	var strSeparator = "-"; // 日期分隔符
	var oDate1;
	var oDate2;
	var days;
	oDate1 = strDateStart.split(strSeparator);
	oDate2 = strDateEnd.split(strSeparator);
//	alert(strDateStart+","+strDateEnd);
	var strDateS = new Date(oDate1[0], oDate1[1] - 1, oDate1[2]);
	var strDateE = new Date(oDate2[0], oDate2[1] - 1, oDate2[2]);
	days = parseInt(Math.abs(strDateS-strDateE)/1000/60/60/24);//把相差的毫秒数转换为天数
	return days;
}
var lateFeeLimitUpper=null;

$(document).ready(function(){
//		lateFeeLimitUpper = getUrlParam("lateFeeLimitUpper");
		getLateFeeLimitUpper();
		initCancelSignatureTag();
		initExpressServiceProviders();
		//输入框按回车
		$("#queryParams").bind("keydown",function(e){
			var keycode = e.which;
			if(keycode == 13){//输入回车判定
				queryData();
				e.preventDefault();						
			}
		});
		
		$("#barCode").bind("keydown",function(e){
			var keycode = e.which;
			if(keycode == 13){//输入回车判定
				searchExpressInfoByBarCode();
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
//			alert(tempIds+","+cancelSignatureTag);
			if(cancelSignatureTag == true){
				$.ajax({
					url : contextPath + "/pages/system/letExpressOutStorehouse.light",
					type : "POST",
					dataType : 'json',
					data :{
						"ids" : tempIds,
						"signatureImg" : "",
						"type" : "1"
					}, success : function(result){
						$('#areaCodeGrid').datagrid('clearSelections');
						$('#areaCodeGrid').datagrid("reload");
						closeBPopup();
					}
				});
			} else {
				$('#signatureRegion').window('open');
				initializationSignatureRegion();
		    	closeBPopup();
		    	updateCustomerGender();
			}
			
	    	
		});
		
		$('#signatureRegion').window({
			title:'取件确认',
			width:560,
			height:395,
		    modal:true,
		    closed:true,
		    maximizable:false
		});
		
		window.onload = function(){
			obj = document.getElementById("HWPenSign");
			obj.HWSetBkColor(0xFFF5EE);
			obj.HWSetCtlFrame(0, 0x000000);
		};
		
		$('#ss').slider({
		    mode: 'h',
		    showTip:true,
		    value:3, 
		    min:1, 
		    max:9,
		    height:100,
		    rule:[1,'|',3,'|',5,'|',7,'|',9],
		    tipFormatter: function(value){
		        return value + '天';
		    }
		});
		
		$('#areaCodeGrid').datagrid({
			dataType : 'json',
			url : contextPath + '/pages/system/getExpressInfoList.light',
			width : $(window).width()*0.99,
			height :($(window).height()-26)*0.99,
			singleSelect:true,
			rownumbers : true,
			pagination : true,
			striped : true,
			method : 'post',
			idField : 'ID',
			pageSize : 20,
			queryParams:{
				batchNumber: ''
			},
			rowStyler: function(index,row){
				var expressDate = row.OPERA_TIME.split(' ');
				var count = getDays(expressDate[0],getCurrDateFormat());
				var tag = checkSpeDate(tempSpeDate, expressDate[0]);
				var delayDays = parseInt(count);
				var finalDays = delayDays;
				if (tag) {
					finalDays = 0;
				}
				var isInterest = row.IS_INTEREST;
				if (isInterest == 'Y') {
					finalDays = finalDays -1;
				}
				if (0<finalDays){
					return 'color:#EC920F;';
				} 
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
				iconCls: 'icon-large-smartart',
				handler: function(){
					batchLetExpressOutStorehouse();
				}
			}
//			{
//				text:'查询超时件',
//				iconCls: 'icon-filter',
//				handler: function(){
//					timeOutExpressInfo();
//				}
//			}
//			,{
//				text:'打印出库单',
//				iconCls: 'icon-print',
//				handler: function(){
//					var gridView = $(".datagrid-view");
//					$(gridView).jqprint({operaSupport: true});
//				}
//			}
			],
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
				width : 140,
				align : 'center',
				hidden : false,
				formatter : function(value, row, index){
					var expressDate = row.OPERA_TIME.split(' ');
					var tempDate = expressDate[0];
//					var tag = checkSpeDate(tempSpeDate, expressDate[0]);
					if (getDays(tempDate,getCurrDateFormat())>0){
						var delayDays = getDays(tempDate,getCurrDateFormat());
						var finalDays = delayDays;
						if (finalDays <=0) {
							return value;
						} else {
							return "<span title='"+finalDays+"天未取件!'>"+value+"</span>";
						}
						
					} else {
						return value;
					}
					
				}
			},{
				field : 'BATCH_NUMBER',
				title : '入库批次号',
				width : 85,
				align : 'center',
				hidden : true
			},{
				field : 'REMARK',
				title : '备注',
				width : 200,
				align : 'center',
				hidden : false
			},{
				field : 'SERVICE_SHOP_CODE',
				title : '网点',
				width : 200,
				align : 'center',
				hidden : true
			},{
				field : 'IS_INTEREST',
				title : '是否关注微信',
				width : 80,
				align : 'center',
				hidden : true,
				formatter : function(value, row, index){
					if (value == 'Y') {
						return '已关注';
					} else {
						return '未关注';
					}
					
				}
			},{  
				field : 'WEIXIN_ID',
				title : '推送微信通知',
				width : 100, 
				align : 'center',
				hidden : true,
				formatter : function(value,row,index) {
					if (row.WEIXIN_ID === '' || row.WEIXIN_ID ==null) {
						return value;
					} else {
						return "<button onclick=\"pushWechatNotification('"+row.WEIXIN_ID+"','"+formatShopCodeColumnTitle(row.SERVICE_SHOP_CODE)+"','"+formatColumnTitle(row.EXPRESS_SERVICE_ID)+"','"+row.LOGISTICS+"')\">推送微信通知</button>";
					}
				}
			},{  field : 'showBarCode', 
				title : '查看条码',
				width : 100, 
				align : 'center',
				hidden : true,
				formatter : function(value,row,index){
					return "<button onclick=\"getBarCode("+row.LOGISTICS+",'"+row.RECIPIENT_NAME+"')\">查看条码</button>";
				}
			},{
				field : 'delayDay',
				title : '是否延期',
				width : 100,
				align : 'center',
				hidden : false,
				formatter : function(value, row, index){
					var expressDate = row.OPERA_TIME.split(' ');
					var tag = checkSpeDate(tempSpeDate, expressDate[0]);
					var tempDate = expressDate[0];
					var msgTemp = '延期';
					var delayDays = parseInt(getDays(tempDate,getCurrDateFormat()));
					var finalDays = delayDays;
//					alert(finalDays);
					var isInterest = row.IS_INTEREST;
					if (isInterest == 'Y') {
						finalDays = finalDays -1;
					}
//					if (finalDays < 0) {
//						finalDays = 0;
//					}
					if (tag) {
						finalDays = 0;
					}
					if (finalDays < 0) {
						finalDays = 0;
					}
					row['delayDay'] = finalDays;
					
					if (finalDays >0 ) {
						return '延期';//+'('+finalDays+'天)';
					} else {
						return '未延期 ';
					}
				}
			},{  
				field : 'opara', 
				title : '操作',
				width : 100, 
				align : 'center',
				formatter : function(value,row,index){
					return "<button style='width: inherit;' id='expressOpara' onclick=\"letExpressOutStorehouse("+row.ID+",'"+row.RECIPIENT_NAME+"','"+row.PHONE_NUMBER+"','"+row.EXPRESS_lOCATION+"','"+row.EXPRESS_SERVICE_ID+"','"+row.GENDER+"','"+row.delayDay+"')\">取件</button>";
				}
			}] ],
			onLoadSuccess : function(){
				$('#areaCodeGrid').datagrid('clearSelections');
				$('#areaCodeGrid').datagrid("keyCtrl");
				$('#expressServiceId').combobox({
					url : contextPath + "/pages/system/getExpressServiceProviderInfo.light",
					valueField : "id",
					textField : "text",
					panelWitdh : 180,
					panelHeight : 245,
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
			onLoadError : function() {
				parent.location.href=contextPath+'/pages/system/welcome.light';
			},
			onDblClickRow : function(index,row) {
				openWindow(index,row);
			},
			onClickRow : function(index,row) {
				if (index != selectIndexs.firstSelectRowIndex&& !inputFlags.isShiftDown) {
					selectIndexs.firstSelectRowIndex = index;
				}
				if (inputFlags.isShiftDown) {
					$('#areaCodeGrid').datagrid('clearSelections');
					selectIndexs.lastSelectRowIndex = index;
					var tempIndex = 0;
					if (selectIndexs.firstSelectRowIndex > selectIndexs.lastSelectRowIndex) {
						tempIndex = selectIndexs.firstSelectRowIndex;
						selectIndexs.firstSelectRowIndex = selectIndexs.lastSelectRowIndex;
						selectIndexs.lastSelectRowIndex = tempIndex;
					}
					for (var i = selectIndexs.firstSelectRowIndex; i <= selectIndexs.lastSelectRowIndex; i++) {
						$('#areaCodeGrid').datagrid('selectRow',i);
					}
				}
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
			sync:false,
			data : {
				"shop_code" : ""
			},
			success : function(data) {
				$.each(data, function(i) {    
					expressServiceMap[data[i].id] = data[i].text;
			     });
			},
			error : function(data) {
			}
		});
	}
	
	function initCancelSignatureTag() {
		$.ajax({
			url : contextPath + "/pages/system/initCancelSignatureTag.light",
			type : "POST",
			dataType : 'json',
			sync:false,
			data : {
				"shop_code" : ""
			},
			success : function(data) {
				cancelSignatureTag = data.cancelSignatureTag;
			},
			error : function(data) {
				cancelSignatureTag = false;
			}
		});
	}
	
	function formatColumnTitle(value){
		return expressServiceMap[value];
	}
	
	function checkSpeDate(speDate,expressDate) {
		var d1 = getDateForStr(speDate);
		var d2 = getDateForStr(expressDate);
		if (expressDate == speDate) {
			return true;
		}
		var date3 = d1.getTime()-d2.getTime();  //时间差的毫秒数
		//计算出相差天数
		var days=Math.floor(date3/(24*3600*1000));
		if (days > 0) {
			return true;
		} else {
			return false;
		}
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
	
	function getFirstSelectRowPhoneNumber(){
		var selectedRows = $('#areaCodeGrid').datagrid('getSelections');
		if (selectedRows.length != 0){
			return selectedRows[0].PHONE_NUMBER;
		} else {
			return null;
		}
	}
	
	function getPhoneNumberBySelectRows(){
		var selectedRows = $('#areaCodeGrid').datagrid('getSelections');
		if (selectedRows.length != 0){
			var cInfo = '';
			for(var i = 0; i < selectedRows.length; ++i){
				if (selectedRows[i].delayDay >0) {
					cInfo += selectedRows[i].RECIPIENT_NAME+':'+selectedRows[i].PHONE_NUMBER+',货位:'+selectedRows[i].EXPRESS_lOCATION+" ("+formatColumnTitle(selectedRows[i].EXPRESS_SERVICE_ID)+")"+"	<span style='color:red;'>延迟"+selectedRows[i].delayDay+"天</span>"+"</br></br>";
				} else {
					cInfo += selectedRows[i].RECIPIENT_NAME+':'+selectedRows[i].PHONE_NUMBER+',货位:'+selectedRows[i].EXPRESS_lOCATION+" ("+formatColumnTitle(selectedRows[i].EXPRESS_SERVICE_ID)+")"+"</br></br>";
				}
			}
		}
		return cInfo;
	}
	
	function getSelectRowsDelayDays(){
		var dataArray = new Array();
		var selectedRows = $('#areaCodeGrid').datagrid('getSelections');
		if (selectedRows.length != 0){
			var money = parseInt("0");
			var delayDays = parseInt("0");
			for(var i = 0; i < selectedRows.length; ++i){
				var day = parseInt(selectedRows[i].delayDay);
				delayDays += parseInt(day);
				if (parseInt(day) > parseInt(lateFeeLimitUpper)) {
					day = lateFeeLimitUpper;
				}
				money += parseInt(day);
			}
			dataArray.push(money);
			dataArray.push(delayDays);
		}
		return dataArray;
	}
	
	function batchLetExpressOutStorehouse(){
		$('#genderSpan').hide();
		var ids = null;
		ids = getSelectRows();
		if (ids===undefined ){
			showMsg("请选择快件...", "提示");
			return;
		} else {
			var tempValue = getFirstSelectRowGender();
			if (tempValue != null && tempValue ==='unknown') {
				$('#genderSpan').show();
			}
			var cInfo = getPhoneNumberBySelectRows();
			var titleInfo = "请核对取件人: "+'</br></br>';
			$('#content').empty();
			$('#content').append(titleInfo+cInfo);
			$('#my-button').click();
			var delayDays = getSelectRowsDelayDays();
			var mTemp = delayDays[0];
			var dTemp = delayDays[1];
//			var finalDays = delayDays-2;
			if (dTemp >0) {
//				if (parseInt(delayDays) > parseInt(lateFeeLimitUpper)) {
//					$('#delayDayDiv').append('<H2 id="delayDayContent"></H2>');
//					$('#delayDayContent').empty();
//					$('#delayDayContent').append('<span style="text-decoration:underline;color:#0086D0;"> 延迟'+delayDays+"天，"+"请付￥"+lateFeeLimitUpper+"元 (封顶)。谢谢！</span>");
//				} else {
					$('#delayDayDiv').append('<H2 id="delayDayContent"></H2>');
					$('#delayDayContent').empty();
					$('#delayDayContent').append('<span style="text-decoration:underline;color:#0086D0;"> 延迟'+dTemp+"天，"+"请付￥"+mTemp+"元。谢谢！</span>");
//				}
			} else {
				var dc = document.getElementById("delayDayContent");
			    if (dc != null)
			    	dc.parentNode.removeChild(dc);
			}
			tempIds = null;
	    	tempIds = ids;
		}

	}

	
	function timeOutExpressInfo() {
		$('#query').dialog('open');
	}

	function letExpressOutStorehouse(id,name,phone,expressLocation,expressServiceId,gender,delayDays){
		var cInfo = "";
		if (gender === 'unknown') {
			$('#genderSpan').show();
		}
		if (delayDays > 0) {
			if (parseInt(delayDays) > parseInt(lateFeeLimitUpper)) {
				cInfo = '请核对取件人: '+'</br></br>'+name+','+phone+",货位:"+expressLocation+" ("+formatColumnTitle(expressServiceId)+")"+"	<span style='color:red;'>延期"+delayDays+"天</span>"+"</br></br>";
				$('#delayDayDiv').append('<H2 id="delayDayContent"></H2>');
				$('#delayDayContent').empty();
				$('#delayDayContent').append('<span style="text-decoration:underline;color:#0086D0;"> 延期'+delayDays+"天，"+"请付￥"+lateFeeLimitUpper+"元 (封顶)。谢谢！</span>");
			} else {
				cInfo = '请核对取件人: '+'</br></br>'+name+','+phone+",货位:"+expressLocation+" ("+formatColumnTitle(expressServiceId)+")"+"	<span style='color:red;'>延期"+delayDays+"天</span>"+"</br></br>";
				$('#delayDayDiv').append('<H2 id="delayDayContent"></H2>');
				$('#delayDayContent').empty();
				$('#delayDayContent').append('<span style="text-decoration:underline;color:#0086D0;"> 延期'+delayDays+"天，"+"请付￥"+delayDays+"元。谢谢！</span>");
			}
		} else {
			cInfo = '请核对取件人: '+'</br></br>'+name+','+phone+",货位:"+expressLocation+" ("+formatColumnTitle(expressServiceId)+")" +"</br></br>";
			var dc = document.getElementById("delayDayContent");
		    if (dc != null)
		    	dc.parentNode.removeChild(dc);
		}
		
		$('#content').empty();
		$('#content').append(cInfo);
		$('#my-button').click();
		
		
		tempIds = null;
    	tempIds = id;
    	$('#hiddenPhoneNumber').val(phone);
    	
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
//		$('#areaCodeGrid').datagrid("loadData",[]);
		$('#areaCodeGrid').datagrid('loadData', { total: 0, rows: [] })
		$('#areaCodeGrid').datagrid("clearSelections");
		$('#areaCodeGrid').datagrid({
			url : contextPath + "/pages/system/getNotOutExpressInfoByFilterConditions.light?endDate="+endDate+"&startDate="+startDate+"&queryParams="+queryParams+"&expressService="+expressService
		});
		
//		$("#number").focus();
		var paper = $('#areaCodeGrid').datagrid('getPager');
		$(paper).pagination('refresh',{ pageNumber: 1 });
//		$('.datagrid-body').focus();
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
	
	function searchExpressInfoByBarCode(){
		var barCode = $("#barCode").val();
		var obj = 
		{
				"barCode":barCode
		};
		$('#areaCodeGrid').datagrid('loadData', { total: 0, rows: [] })
		$('#areaCodeGrid').datagrid("clearSelections");
		$('#areaCodeGrid').datagrid({
			url : contextPath + "/pages/system/searchExpressInfoByBarCode.light?barCode="+barCode
		});
		
//		$("#number").focus();
		var paper = $('#areaCodeGrid').datagrid('getPager');
		$(paper).pagination('refresh',{ pageNumber: 1 });
//		$('.datagrid-body').focus();
	}
	
	function getLateFeeLimitUpper() {
		$.ajax({
			url : contextPath + "/pages/system/getLateFeeLimitUpper.light",
			type : "POST",
			dataType : 'json',
			sync:false,
			data : {},
			success : function(data) {
//				alert(data);
				lateFeeLimitUpper = data;
			},
			error : function(data) {
			}
		});
	}
	
	
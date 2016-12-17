var queryPollingTag = null;
Date.prototype.format = function(format) {
    var o = {
            "M+" : this.getMonth() + 1, //month
            "d+" : this.getDate(), //day
            "h+" : this.getHours(), //hour
            "m+" : this.getMinutes(), //minute
            "s+" : this.getSeconds(), //second
            "q+" : Math.floor((this.getMonth()+3)/3), //quarter
            "S"  : this.getMilliseconds() //millisecond
    }
    if (/(y+)/.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        }
    for ( var k in o) if (new RegExp("(" + k + ")").test(format)) {
        format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]: ("00" + o[k]).substr(("" + o[k]).length));
    }
        return format;
}
$(document).ready(function() {
		$("#submitBtn").click(function() {
			submitForm();
		});
		$("#saveBtn").click(function() {
			saveForm();
		});
		
		$("#cancelBtn").click(function(){
			$('#expressListDetail').window('close');
		});
		
		queryPollingTag = setInterval(searchExpressInfo,16000);
		
		$('#expressListDetail').window({
			title:'详细信息',
		    width:650,
		    height:399,
		    modal:true,
		    closed:true,
		    left:340,    
		    top:30,
		    onBeforeClose:function(){
		    	$('#expressDetailGrid').datagrid("clearSelections");
		    }
		});
		
		$('#signatureDetail').dialog({
			title:"",
		    width: 280,    
		    height: 120,    
		    closed: true,    
		    resizable: false,     
		    modal: false
		});

		$("#queryParams").bind("keydown",function(e){
			var keycode = e.which;
			if(keycode == 13){//输入回车判定
				searchExpressInfo();
				e.preventDefault();
			}
		});
		
		$('#expressBarCodeGrid').datagrid({
			dataType : 'json',
			url : contextPath + '/pages/system/takeByQR/getBarCodeByExpress.light',//getNotOutExpressInfoByFilterConditions
			width : $(window).width() * 0.99,
			height :($(window).height()-32)*0.99,
			singleSelect : true,
			rownumbers : true,
			pagination : true,
			striped : true,
			idField : 'ID',
			pageSize : 20,
			queryParams: {
				barCode: ''
			},
			columns : [ [ {
				field : 'barCode',
				title : '取件二维码',
				width : 220,
				align : 'center'
			},{
				field : 'remark',
				title : '货位列表',
				width : 380,
				align : 'center'
			},{
				field : 'basketNumber',
				title : '编号',
				width : 100,
				align : 'center'
			},{
				field : 'operaTime',
				title : '打印时间',
				width : 190,
				align : 'center',
				formatter : function(value,row,index){
					return getLocalTime(value.time);
				}
			}
//			,{  
//				field : 'opara',
//				title : '操作',
//				width : 100, 
//				align : 'center',
//				hidden : false,
//				formatter : function(value,row,index){
//					return "<button style='width: inherit;' onclick=\"letExpressOutStorehouse("+row.ID+",'"+row.RECIPIENT_NAME+"','"+row.PHONE_NUMBER+"')\">查看详细信息</button>";
//				}
//			}
			] ],
			onLoadSuccess : function(data) {
				if(data.length == 1){
					openWindow(null, data);
				}
			},
			onLoadError : function() {
				parent.location.href=contextPath+'/pages/system/welcome.light';
			},
			onDblClickRow : function(rowIndex, rowData) {
				openWindow(rowIndex, rowData);
				operatingTag = true;
			},
			onRowContextMenu : function(e, rowIndex, rowData) {
//				var annArray = getBarcodeLocationAnnotationList(rowData.barCode);
				var annArray = new Array();
				$.ajax({
					url : contextPath + "/pages/system/takeByQR/getBarcodeLocationAnnotationList.light",
					type : "POST",
					dataType : 'json',
					data :{
						"barCode" : rowData.barCode
					},
					sync:false,
					success : function(result){
						$.each(result.annList, function(i,val){
							annArray.push(val.expressLocation);
						});
						$('#right_menu').empty();
						var selected=$("#expressBarCodeGrid").datagrid('getRows'); //获取所有行集合对象
						selected[rowIndex].id; //index为当前右键行的索引，指向当前行对象
						
			            var divNode = document.createElement('div');
			            divNode.innerHTML = rowData.barCode;
			            divNode.setAttribute("id","m-refresh");
			            divNode.setAttribute("style","margin: 4px;font-size: 14px;");
			            
			            $('#right_menu').append(divNode);
			            
			            var menusep = document.createElement('div');
			            menusep.setAttribute("style","margin: 0px 2px 5px 0px;width: inherit;");
			            menusep.setAttribute("class","menu-sep");
			            $('#right_menu').append(menusep);
						var remarks = rowData.remark.split(",");
						$.each(remarks, function(i,val){
							var annindex = $.inArray(val, annArray);  //返回 3,如果不包含在数组中,则返回 -1;
//							var mCloseCurrentLabel = document.createElement('div');
//							mCloseCurrentLabel.setAttribute("id","m-close-currentLabel");
//							mCloseCurrentLabel.innerHTML = val;
//							$('#right_menu').append(mCloseCurrentLabel);
//							var button = document.createElement('button');
//							button.setAttribute("style","height: 50%;width: "+60+"px;font-weight: bold;color: #384141;cursor: default;");
//							var t = document.createTextNode(val);
//							button.appendChild(t);
//							button.appendChild(addLable(val));
							var toggleDiv = document.createElement('div');
							toggleDiv.setAttribute("style","width: 60px;float:left;");
							var h2 = document.createElement('h2');
						    h2.innerHTML = val;
						    toggleDiv.appendChild(h2);
							toggleDiv.appendChild(addLable(rowData.barCode,val,annindex));
							$('#right_menu').append(toggleDiv);
						});
						var inputDiv = document.createElement('div');
						inputDiv.setAttribute("style","margin: 10px 0px;");
//						var label = document.createElement('label');
//						label.setAttribute("class","af-show");
//						label.setAttribute("for","asdfasdf");
//						label.setAttribute("style","width: 120px;margin: 125px 1px;height: 30px;padding: 6px;");
//						label.innerHTML = "给二维码编个号！";
//						input.setAttribute("type","text");
//						input.setAttribute("id","asdfasdf");
//						inputDiv.appendChild(label);
//						inputDiv.appendChild(nButton);
						
						var menusep2 = document.createElement('div');
						menusep2.setAttribute("style","margin: 85px 10px 9px 8px;border-bottom-color: #cbdca6;border-top-color: white;");
						menusep2.setAttribute("class","menu-sep");
						var basketNumber = result.annNum;
						var menusepspe = document.createElement('div');
						menusepspe.innerHTML = "编号:<span id='menusep' value='"+basketNumber+"' style='font-size: 14px;font-weight: 700;color: #2d4c4e;margin: 4px;'>"+basketNumber+"</span>";
						menusepspe.setAttribute("style","font-size: 14px;font-weight: bold;margin: 5px 0px;");
						menusep2.appendChild(menusepspe);
			            $('#right_menu').append(menusep2);
			            var annNumArray = new Array();
			            $.each(result.annNumList, function(i,val){
			            	annNumArray.push(val.basketNumber);
						});
						addButton(inputDiv,rowData.barCode,annNumArray);
						$('#right_menu').append(inputDiv);
						
						
//						$('#menusep').val(basketNumber);
//						$('#menusep').html(basketNumber);
						
						$('#right_menu').menu('show', {
							hideOnUnhover:false,
				            left: e.pageX,
				            top: e.pageY
//				            minWidth:remarks.length*63
				        }).data("tabTitle", rowData.barCode);
					}
				});
				e.preventDefault();
			},
			loadFilter : pagerFilter
		});
	});

function addLable(barCode,expressLocation,annindex){
    var label = document.createElement('label');
    label.setAttribute("class","toggle-checkbox");
    var div = document.createElement('div');
    div.setAttribute("class","toggle-bg");
    var toggleBtnDiv = document.createElement('div');
    if(annindex == -1) {
    	toggleBtnDiv.setAttribute("class","toggle-btn");
	} else {
		toggleBtnDiv.setAttribute("class","toggle-btn");
	}
    
    var input = document.createElement('input');
    input.setAttribute("type","checkbox");
    var isChecked = false;
    if(annindex != -1) {
    	input.setAttribute("checked","checked");
    	isChecked = true;
	}
    
    input.setAttribute("onclick","annotationLocation('"+barCode+"','"+expressLocation+"',"+isChecked+")");
    input.setAttribute("id",expressLocation);
    div.appendChild(toggleBtnDiv);
    label.appendChild(input);
    label.appendChild(div);
    
    return label;
}

function modifyNumber(nButton,basketNumber,barCode){
	$.ajax({
		url : contextPath + "/pages/system/takeByQR/numberingBarcode.light",
		type : "POST",
		dataType : 'json',
		data :{
			"barCode" : barCode,
			"basketNumber" : basketNumber
		},
		sync:false,
		success : function(result){
			$('#menusep').val(basketNumber);
			$('#menusep').html(basketNumber);
			nButton.setAttribute("disabled","disabled");
			nButton.setAttribute("style","height: 25px;width:25px;margin: 4px;background: #ffffff;");
		}
	});
}

function addButton(inputDiv,barCode,annNumArray){
	
	var nButton = document.createElement('button');
	var valueText1 = "1";
	var annindex1 = $.inArray(valueText1, annNumArray);
	if(annindex1 != -1) {
		nButton.setAttribute("disabled","disabled");
		nButton.setAttribute("style","height: 25px;width:25px;margin: 4px;background: #ffffff;");
	} else {
		nButton.setAttribute("style","height: 25px;width:25px;margin: 4px;background: #e8d6e1;");
	}
	nButton.setAttribute("id","button_"+valueText1);
	nButton.setAttribute("value",valueText1);
	nButton.setAttribute("checktype","on");
	nButton.setAttribute("onclick","modifyNumber(this,this.value,'"+barCode+"')");
	var tff = document.createTextNode(valueText1);
	nButton.appendChild(tff);
	
	var nButton2 = document.createElement('button');
	var valueText2 = "2";
	var annindex2 = $.inArray(valueText2, annNumArray);
	if(annindex2 != -1) {
		nButton2.setAttribute("disabled","disabled");
		nButton2.setAttribute("style","height: 25px;width:25px;margin: 4px;background: #ffffff;");
	} else {
		nButton2.setAttribute("style","height: 25px;width:25px;margin: 4px;background: #e8d6e1;");
	}
	nButton2.setAttribute("id","button_"+valueText2);
	nButton2.setAttribute("value",valueText2);
	nButton2.setAttribute("onclick","modifyNumber(this,this.value,'"+barCode+"')");
	var tff2 = document.createTextNode(valueText2);
	nButton2.appendChild(tff2);
	
	var nButton3 = document.createElement('button');
	var valueText3 = "3";
	var annindex3 = $.inArray(valueText3, annNumArray);
	if(annindex3 != -1) {
		nButton3.setAttribute("disabled","disabled");
		nButton3.setAttribute("style","height: 25px;width:25px;margin: 4px;background: #ffffff;");
	} else {
		nButton3.setAttribute("style","height: 25px;width:25px;margin: 4px;background: #e8d6e1;");
	}
	nButton3.setAttribute("id","button_"+valueText3);
	nButton3.setAttribute("value",valueText3);
	nButton3.setAttribute("onclick","modifyNumber(this,this.value,'"+barCode+"')");
	var tff3 = document.createTextNode(valueText3);
	nButton3.appendChild(tff3);
	
	var nButton4 = document.createElement('button');
	var valueText4 = "4";
	var annindex4 = $.inArray(valueText4, annNumArray);
	if(annindex4 != -1) {
		nButton4.setAttribute("disabled","disabled");
		nButton4.setAttribute("style","height: 25px;width:25px;margin: 4px;background: #ffffff;");
	} else {
		nButton4.setAttribute("style","height: 25px;width:25px;margin: 4px;background: #e8d6e1;");
	}
	nButton4.setAttribute("id","button_"+valueText4);
	nButton4.setAttribute("value",valueText4);
	nButton4.setAttribute("onclick","modifyNumber(this,this.value,'"+barCode+"')");
	var tff4 = document.createTextNode(valueText4);
	nButton4.appendChild(tff4);
	
	var nButton5 = document.createElement('button');
	var valueText5 = "5";
	var annindex5 = $.inArray(valueText5, annNumArray);
	if(annindex5 != -1) {
		nButton5.setAttribute("disabled","disabled");
		nButton5.setAttribute("style","height: 25px;width:25px;margin: 4px;background: #ffffff;");
	} else {
		nButton5.setAttribute("style","height: 25px;width:25px;margin: 4px;background: #e8d6e1;");
	}
	nButton5.setAttribute("id","button_"+valueText5);
	nButton5.setAttribute("value",valueText5);
	nButton5.setAttribute("onclick","modifyNumber(this,this.value,'"+barCode+"')");
	var tff5 = document.createTextNode(valueText5);
	nButton5.appendChild(tff5);
	
	var nButton6 = document.createElement('button');
	var valueText6 = "6";
	var annindex6 = $.inArray(valueText6, annNumArray);
	if(annindex6 != -1) {
		nButton6.setAttribute("disabled","disabled");
		nButton6.setAttribute("style","height: 25px;width:25px;margin: 4px;background: #ffffff;");
	} else {
		nButton6.setAttribute("style","height: 25px;width:25px;margin: 4px;background: #e8d6e1;");
	}
	nButton6.setAttribute("id","button_"+valueText6);
	nButton6.setAttribute("value",valueText6);
	nButton6.setAttribute("onclick","modifyNumber(this,this.value,'"+barCode+"')");
	var tff6 = document.createTextNode(valueText6);
	nButton6.appendChild(tff6);
	
	var nButton7 = document.createElement('button');
	var valueText7 = "7";
	var annindex7 = $.inArray(valueText7, annNumArray);
	if(annindex7 != -1) {
		nButton7.setAttribute("disabled","disabled");
		nButton7.setAttribute("style","height: 25px;width:25px;margin: 4px;background: #ffffff;");
	} else {
		nButton7.setAttribute("style","height: 25px;width:25px;margin: 4px;background: #e8d6e1;");
	}
	var tff7 = document.createTextNode("7");
	nButton7.setAttribute("id","button_"+valueText7);
	nButton7.setAttribute("value",valueText7);
	nButton7.setAttribute("onclick","modifyNumber(this,this.value,'"+barCode+"')");
	var tff7 = document.createTextNode(valueText7);
	nButton7.appendChild(tff7);
	
	var nButton8 = document.createElement('button');
	var valueText8 = "8";
	var annindex8 = $.inArray(valueText8, annNumArray);
	if(annindex8 != -1) {
		nButton8.setAttribute("disabled","disabled");
		nButton8.setAttribute("style","height: 25px;width:25px;margin: 4px;background: #ffffff;");
	} else {
		nButton8.setAttribute("style","height: 25px;width:25px;margin: 4px;background: #e8d6e1;");
	}
	nButton8.setAttribute("id","button_"+valueText8);
	nButton8.setAttribute("value",valueText8);
	nButton8.setAttribute("onclick","modifyNumber(this,this.value,'"+barCode+"')");
	var tff8 = document.createTextNode(valueText8);
	nButton8.appendChild(tff8);
	
	var nButton9 = document.createElement('button');
	var valueText9 = "9";
	var annindex9 = $.inArray(valueText9, annNumArray);
	if(annindex9 != -1) {
		nButton9.setAttribute("disabled","disabled");
		nButton9.setAttribute("style","height: 25px;width:25px;margin: 4px;background: #ffffff;");
	} else {
		nButton9.setAttribute("style","height: 25px;width:25px;margin: 4px;background: #e8d6e1;");
	}
	nButton9.setAttribute("id","button_"+valueText9);
	nButton9.setAttribute("value",valueText9);
	nButton9.setAttribute("onclick","modifyNumber(this,this.value,'"+barCode+"')");
	var tff9 = document.createTextNode(valueText9);
	nButton9.appendChild(tff9);
	
	var nButton10 = document.createElement('button');
	var valueText10 = "10";
	var annindex10 = $.inArray(valueText10, annNumArray);
	if(annindex10 != -1) {
		nButton10.setAttribute("disabled","disabled");
		nButton10.setAttribute("style","height: 25px;width:25px;margin: 4px;background: #ffffff;");
	} else {
		nButton10.setAttribute("style","height: 25px;width:25px;margin: 4px;background: #e8d6e1;");
	}
	nButton10.setAttribute("id","button_"+valueText10);
	nButton10.setAttribute("value",valueText10);
	nButton10.setAttribute("onclick","modifyNumber(this,this.value,'"+barCode+"')");
	var tff10 = document.createTextNode(valueText10);
	nButton10.appendChild(tff10);
	
	var nButton11 = document.createElement('button');
	var valueText11 = "11";
	var annindex11 = $.inArray(valueText11, annNumArray);
	if(annindex11 != -1) {
		nButton11.setAttribute("disabled","disabled");
		nButton11.setAttribute("style","height: 25px;width:25px;margin: 4px;background: #ffffff;");
	} else {
		nButton11.setAttribute("style","height: 25px;width:25px;margin: 4px;background: #e8d6e1;");
	}
	nButton11.setAttribute("id","button_"+valueText11);
	nButton11.setAttribute("value",valueText11);
	nButton11.setAttribute("onclick","modifyNumber(this,this.value,'"+barCode+"')");
	var tff11 = document.createTextNode(valueText11);
	nButton11.appendChild(tff11);
	
	var nButton12 = document.createElement('button');
	var valueText12 = "12";
	var annindex12 = $.inArray(valueText12, annNumArray);
	if(annindex12 != -1) {
		nButton12.setAttribute("disabled","disabled");
		nButton12.setAttribute("style","height: 25px;width:25px;margin: 4px;background: #ffffff;");
	} else {
		nButton12.setAttribute("style","height: 25px;width:25px;margin: 4px;background: #e8d6e1;");
	}
	nButton12.setAttribute("id","button_"+valueText12);
	nButton12.setAttribute("value",valueText12);
	nButton12.setAttribute("onclick","modifyNumber(this,this.value,'"+barCode+"')");
	var tff12 = document.createTextNode(valueText12);
	nButton12.appendChild(tff12);
	
	inputDiv.appendChild(nButton);
	inputDiv.appendChild(nButton2);
	inputDiv.appendChild(nButton3);
	inputDiv.appendChild(nButton4);
	inputDiv.appendChild(nButton5);
	inputDiv.appendChild(nButton6);
	inputDiv.appendChild(nButton7);
	inputDiv.appendChild(nButton8);
	inputDiv.appendChild(nButton9);
	inputDiv.appendChild(nButton10);
	inputDiv.appendChild(nButton11);
	inputDiv.appendChild(nButton12);
	
	
}

function getLocalTime(ds) {  
    var date = new Date(ds);
    var year = date.getFullYear();
    var month = date.getMonth()+1;
    var day = date.getDate();
    var hour = date.getHours();
    var minu = date.getMinutes();
    var sec = date.getSeconds();
    return year+"-"+month+"-"+day + " " +hour+":" +minu;
}

function expressListDetail() {
	$('#expressListDetail').window('open');
}

function getSelections() {
	var rows = $('#expressBarCodeGrid').datagrid('getSelections');
	return rows;
}

function openWindow(rowIndex, rowData) {
	clearFormData();
	var barCode = rowData.barCode;
	$.ajax({
		url : contextPath+"/pages/system/takeByQR/getExpressByBarCode.light",
		type: "POST",
		dataType:'json',
		data:
		{
			"barCode":barCode,
		},
		success : function(data) {
			var exp = new Array();
			$.each(data,function (index,ele){
				exp.push(eval(ele));
			});
			$('#expressDetailGrid').datagrid({
				dataType : 'json',
				singleSelect : false,
				rownumbers : true,
				pagination : true,
				height :366,
				striped : true,
				idField : 'id',
				pageSize : 20,
				data:data,
				queryParams: {
					barCode: ''
				},
				toolbar: 
				[
			  		{
			  			text:"<span style='font-size: 16px;font-size: 18px;font-family:Roboto Condensed;'>"+"客户手机号码："+exp[0].phoneNumber+"</span>",
			  			iconCls: 'icon-man'
			  		},{
						text:'|'
					},{
						text:'批量取件',
						iconCls: 'icon-large-smartart',
						handler: function(){
							batchLetExpressOutStorehouse(barCode);
						}
					}
			  	],
				columns : [ [ {
					field : 'id',
					title : 'ID',
					width : 100,
					align : 'center',
					hidden : true
				},{
					field : 'expressLocation',
					title : '货位',
					width : 85,
					align : 'center',
					formatter : function(value,row,index){
						return "<span style='font-weight: 100;color: #333;font-size: 18px;font-family:Roboto Condensed;'>"+value+"</span>";
					}
				},{
					field : 'logistics',
					title : '单号',
					width : 165,
					align : 'center'
				},{
					field : 'recipientName',
					title : '收件人姓名',
					width : 85,
					align : 'center'
				},{
					field : 'expressServiceName',
					title : '快递商',
					width : 105,
					align : 'center'
				},{
					field : 'operaTime',
					title : '入库时间',
					width : 165,
					align : 'center'
				}] ],
				onLoadError : function() {
					parent.location.href=contextPath+'/pages/system/welcome.light';
				},
				loadFilter : pagerFilter
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
	$('#expressListDetail').window('open');
}

function clearFormData() {
	$('#barCode').val('');
}

function searchExpressInfo() {
	var queryParams = $("#queryParams").val();
//	queryParams = encodeURI(queryParams);
	$('#expressBarCodeGrid').datagrid("loadData", []);
	$('#expressBarCodeGrid').datagrid("clearSelections");

	$('#expressBarCodeGrid').datagrid({
		url : contextPath+ "/pages/system/takeByQR/getBarCodeByExpress.light",
		queryParams: {
			barCode: queryParams
		},
	});

	var paper = $('#expressBarCodeGrid').datagrid('getPager');
	$(paper).pagination('refresh', {
		pageNumber : 1
	});
	
}

function batchLetExpressOutStorehouse(barCode){
	block('expressListDetail','请稍后...');
	var ids = null;
	ids = getSelectRows();
	if (ids === undefined ){
		$.messager.show({
            title:'提示',
            msg:'<div class="messager-icon messager-info"></div>'+'请选择要操作的快件！',
            timeout:3800,
            showType:'fade'
		});
		unblock('expressListDetail');
		return;
	} else {
		$.messager.confirm("确认", "确认后快件将出库，确定要批量取件？", function (r) {
	        if (r) {
	        	$.ajax({
	    			url : contextPath + "/pages/system/letExpressOutStorehouse.light",
	    			type : "POST",
	    			dataType : 'json',
	    			data :{
	    				"ids" : ids,
	    				"signatureImg" : "empty",
	    				"type" : "1"
	    			},
	    			success : function(result){
	    				$('#expressListDetail').window('close');
	    				$('#expressDetailGrid').datagrid('clearSelections');
	    				$('#expressDetailGrid').datagrid("reload");
	    				searchExpressInfo();
	    				unblock('expressListDetail');
	    				$.ajax({
	    	    			url : contextPath + "/pages/system/takeByQR/releaseNumberingByBarcode.light",
	    	    			type : "POST",
	    	    			dataType : 'json',
	    	    			data :{
	    	    				"barCode" : barCode
	    	    			},
	    	    			success : function(result){
	    	    				
	    	    			}
	    	    		});
	    			}
	    		});
	        } else {
	        	unblock('expressListDetail');
	        }
	    });
		
	}

}

function annotationLocation(barCode,expressLocation,isChecked){
	if (isChecked) {
		$.ajax({
			url : contextPath + "/pages/system/takeByQR/deleteBarcodeLocationAnnotation.light",
			type : "POST",
			dataType : 'json',
			data :{
				"barCode" : barCode,
				"expressLocation" : expressLocation
			},
			success : function(result){
//				unblock('expressListDetail');
			}
		});
	} else {
		$.ajax({
			url : contextPath + "/pages/system/takeByQR/newBarcodeLocationAnnotation.light",
			type : "POST",
			dataType : 'json',
			data :{
				"barCode" : barCode,
				"expressLocation" : expressLocation
			},
			success : function(result){
//				unblock('expressListDetail');
			}
		});
	}
	
}

function getBarcodeLocationAnnotationList(barCode){
	var annArray = new Array();
	$.ajax({
		url : contextPath + "/pages/system/takeByQR/getBarcodeLocationAnnotationList.light",
		type : "POST",
		dataType : 'json',
		data :{
			"barCode" : barCode
		},
		sync:false,
		success : function(result){
			$.each(result, function(i,val){
				annArray.push(val.expressLocation);
			});
			return annArray;
		}
	});
	
}

function getSelectRows(){
	var selectedRows = $('#expressDetailGrid').datagrid('getSelections');
	if (selectedRows.length != 0){
		var ids = '';
		for(var i = 0; i < selectedRows.length; i ++){
			if(ids != ''){
				ids += ",";
			}
			ids += selectedRows[i].id;
		}
	}
	return ids;
}

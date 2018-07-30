var batchNumber=null;
var obj;
var tempIds;
var expressServiceMap = {};
var seq_locationCodeByExpressType_X=null;
var seq_locationCodeByExpressType_S=null;
var seq_locationCodeByExpressType_M=null;
var seq_locationCodeByExpressType_D=null;
var seq_locationCodeByExpressType_H=null;
var seq_locationCodeByExpressType_Q=null;
var seq_locationCodeByExpressType_R=null;
var seq_locationCodeByExpressType_Y=null;
var toggleSwitchT = true;
var toggleSwitchF = false;
var ajaxTag = true;
var clicktag = 0;
var getBatchNumber = function(){
	 $.ajax({
			url : contextPath + "/pages/system/getTemporaryStorage.light",
			type: "post",
			dataType : 'json',
			success: function(data){
				batchNumber = data.temporaryId;
				$('#batchNumber').val(batchNumber);
			}
	});
};

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

function queryData() {
	var bn = $('#batchNumber').val();
	if (bn == null || bn == '') {
		bn = $('#query_batchNumber').val();
	}
	$('#infinishedGrid').datagrid({
		url : contextPath + '/pages/system/getExpressByBatchNumber.light',
		queryParams: {
			"batchNumber": bn
		}
	});
	batchNumber = $('#query_batchNumber').val();
}

var getExpressLocationCode = function(type){
	 $.ajax({
			url : contextPath + "/pages/system/getLocationCodeByExpressType.light?type="+type,
			type: "post",
			dataType : 'json',
			success: function(data){
				$("span[id=expressLocationTitle]").html(type+data.temporaryId);
				$('#expressLocation').val(type+data.temporaryId);
				checkType(type, type+data.temporaryId);
				unblock("expressLocationDiv");
			},error : function(data) {
				parent.location.href=contextPath+'/pages/system/welcome.light';
			}
	});
};

function checkType(type,val) {
	if (type==='D'){
		seq_locationCodeByExpressType_D = val;
	} else if (type==='X'){
		seq_locationCodeByExpressType_X = val;
	} else if (type==='M'){
		seq_locationCodeByExpressType_M = val;
	} else if (type==='S'){
		seq_locationCodeByExpressType_S = val;
	} else if (type==='H'){
		seq_locationCodeByExpressType_H = val;
	} else if (type==='Q'){
		seq_locationCodeByExpressType_Q = val;
	} else if (type==='R'){
		seq_locationCodeByExpressType_R = val;
	} else if (type==='Y'){
		seq_locationCodeByExpressType_Y = val;
	}
}

function clearType(){
	seq_locationCodeByExpressType_X=null;
	seq_locationCodeByExpressType_S=null;
	seq_locationCodeByExpressType_M=null;
	seq_locationCodeByExpressType_D=null;
	seq_locationCodeByExpressType_H=null;
	seq_locationCodeByExpressType_Q=null;
	seq_locationCodeByExpressType_R=null;
	seq_locationCodeByExpressType_Y=null;
}

function clearOneType(type){
	if (type==='D'){
		seq_locationCodeByExpressType_D = null;
	} else if (type==='X'){
		seq_locationCodeByExpressType_X = null;
	} else if (type==='M'){
		seq_locationCodeByExpressType_M = null;
	} else if (type==='S'){
		seq_locationCodeByExpressType_S = null;
	} else if (type==='H'){
		seq_locationCodeByExpressType_H = null;
	} else if (type==='Q'){
		seq_locationCodeByExpressType_Q = null;
	} else if (type==='R'){
		seq_locationCodeByExpressType_R = null;
	} else if (type==='Y'){
		seq_locationCodeByExpressType_Y = null;
	}
	
	$("#"+type).attr("checked",false);
	$("span[id=expressLocationTitle]").html("");
}



$(document).ready(function() {
	$("#showBatchNumber").click(function(){
		  $("#batchNumberDiv").toggle(50,
				  function() {
					  if ($("#batchNumberDiv").is(":hidden")) {
						  $("#showBatchNumber").text('*显示批次号');
						  $('#presentSelfForm').panel('resize',{
							  	width:580,
							    height:413,
						  });
					  } else {
						  $("#showBatchNumber").text('*隐藏批次号');
						  $('#presentSelfForm').panel('resize',{
							  	width:580,
							    height:468,
						  });
					  }
			  	  }
		  );
	});
	
	$("#importantNote").click(function(){
		$.messager.prompt('备注', '快递的重要备注：', function(r){
			if (r){
				$("#remark").val(r);
			}
		});
	});
	
	$("#phoneNumber").bind("keydown",function(e){
		var keyCode = e.which;
		var realkey = String.fromCharCode(e.which);
		if (realkey==' ') {
			return false;
		}
		if (keyCode == 8 || keyCode == 46) {
			ajaxTag = true;
		}
	});
	
	$('#phoneNumber').keyup(function(event) {
		var key = event.keyCode;
		if ((key >= 48 && key <= 57) || (key >= 96 && key <= 105) || (key >= 65 && key <= 90) || key == 8 || key == 46) {
		var searchTxt = $('#phoneNumber').val();
		if (searchTxt != '' && searchTxt.length >2 && ajaxTag) {
			$('#cat').show();
			$.ajax({
				type : "POST",
				url : contextPath + "/pages/system/customer/getCustomerListByTxt.light",
				data : "queryTxt=" + $('#phoneNumber').val(),
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
						$("#phoneNumber").val($(this).find('input[name=phone]').val());
						$("#recipientName").val($(this).find('input[name=name]').val());
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
		$('#phoneNumber').val($('#aa tr.hover').text());
		} else if (key== 40) {//下箭头
		$('#aa tr.hover').next().addClass("hover");
		$('#aa tr.hover').prev().removeClass("hover");
		$('#phoneNumber').val($('#aa tr.hover').text());
		} else if (key== 13) {//回车
//		 	event.preventDefault();
//		 	event.stopPropagation();
		$('#phoneNumber').val($('#aa tr.hover').text());
		$("#phoneNumber").val($('#aa tr.hover').find('input[name=phone]').val());
		$("#recipientName").val($('#aa tr.hover').find('input[name=name]').val());
		$("#searchresult").empty();
		$("#searchresult").css("display", "none");
		return false;
		} else {
		$("#searchresult").empty();
		$("#searchresult").css("display", "none");
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
	
	//输入框按回车
	$("#query_batchNumber").bind("keydown",function(e){
		var keycode = e.which;		
		if(keycode == 13){//输入回车判定
			queryData();
			e.preventDefault();						
		}
	});	
	
	initExpressServiceProviders();
	
	$("input[name=expressType]").click(function(e){
		block("expressLocationDiv");
		var type = $("input[name='expressType']:checked").val();
		if (type === undefined) {
			unblock("expressLocationDiv");
			return false;
		} else {
//			var val = $('#expressLocation').val();
			if (type==='D'){
				if (seq_locationCodeByExpressType_D == null) {
					getExpressLocationCode(type);
				} else {
					$('#expressLocation').val(seq_locationCodeByExpressType_D);
					$("span[id=expressLocationTitle]").html(seq_locationCodeByExpressType_D);
					unblock("expressLocationDiv");
				}
			} else if (type==='X') {
				if (seq_locationCodeByExpressType_X == null) {
					getExpressLocationCode(type);
				} else {
					$('#expressLocation').val(seq_locationCodeByExpressType_X);
					$("span[id=expressLocationTitle]").html(seq_locationCodeByExpressType_X);
					unblock("expressLocationDiv");
				}
			} else if (type==='M') {
				if (seq_locationCodeByExpressType_M == null) {
					getExpressLocationCode(type);
				} else {
					$('#expressLocation').val(seq_locationCodeByExpressType_M);
					$("span[id=expressLocationTitle]").html(seq_locationCodeByExpressType_M);
					unblock("expressLocationDiv");
				}
			} else if (type==='S') {
				if (seq_locationCodeByExpressType_S == null) {
					getExpressLocationCode(type);
				} else {
					$('#expressLocation').val(seq_locationCodeByExpressType_S);
					$("span[id=expressLocationTitle]").html(seq_locationCodeByExpressType_S);
					unblock("expressLocationDiv");
				}
			} else if (type==='H') {
				if (seq_locationCodeByExpressType_H == null) {
					getExpressLocationCode(type);
				} else {
					$('#expressLocation').val(seq_locationCodeByExpressType_H);
					$("span[id=expressLocationTitle]").html(seq_locationCodeByExpressType_H);
					unblock("expressLocationDiv");
				}
			} else if (type==='Q') {
				if (seq_locationCodeByExpressType_Q == null) {
					getExpressLocationCode(type);
				} else {
					$('#expressLocation').val(seq_locationCodeByExpressType_Q);
					$("span[id=expressLocationTitle]").html(seq_locationCodeByExpressType_Q);
					unblock("expressLocationDiv");
				}
			} else if (type==='R') {
				if (seq_locationCodeByExpressType_R == null) {
					getExpressLocationCode(type);
				} else {
					$('#expressLocation').val(seq_locationCodeByExpressType_R);
					$("span[id=expressLocationTitle]").html(seq_locationCodeByExpressType_R);
					unblock("expressLocationDiv");
				}
			} else if (type==='Y') {
				if (seq_locationCodeByExpressType_Y == null) {
					getExpressLocationCode(type);
				} else {
					$('#expressLocation').val(seq_locationCodeByExpressType_Y);
					$("span[id=expressLocationTitle]").html(seq_locationCodeByExpressType_Y);
					unblock("expressLocationDiv");
				}
			}
		}
		
	});
	
//	$('#phoneNumber').combobox({
//		url : contextPath + "/pages/system/getCustomeInfoList.light",
//		valueField : "text",
//		textField : "id",
//		panelWitdh : 180,
//		panelHeight : 160,
//		width : 280,
//		height : 35,
//		value : "",
//		hasDownArrow : false,
//		formatter :  function(row){
//			var ip = $("#phoneNumber").parent().find('.combo').children().eq(1);
//			var comb = $(this).combobox('options');
//			$(ip).click(function(){
//				$('#phoneNumber').combo('showPanel');	
//			});
//			$(ip).bind("keydown",function(e){
//				$(this).css("background-color", "#D6D6FF");
//				$(this).css("font-weight", "bolder");
//			});
//		    var s = '<span style="font-weight:bold">' + row.text + '</span><br/>' +
//		            '<span style="color:#888">' + row.desc + '</span>';
//		    return s;
//		},
//		onSelect:function(row){
//			$('#recipientName').val(row.text);
////			$('.combo-panel').hide();
//		},
//		onBeforeLoad: function(param){
//			$('#phoneNumber').combobox('setValue', '');
//		},
//		filter: function(q, row){
//			var opts = $(this).combobox('options');
//			if (q.length == 4) {
//				var ph = row[opts.textField];
//				var tailNumber = reverse(ph);
//				tailNumber = tailNumber.substr(0, 4);
//				tailNumber = reverse(tailNumber);
//				if (tailNumber === q || row[opts.textField].indexOf(q) == 0) {
//					return true;
//				} else {
//					return false;
//				}
//			} else {
//				return row[opts.textField].indexOf(q) == 0;
//			}
//		}
//	});
	
	window.onload = function(){
		obj = document.getElementById("HWPenSign");
		obj.HWSetBkColor(0xFFF5EE);
		obj.HWSetCtlFrame(0, 0x000000);
	};
	
	$('#signatureRegion').window({
		title:'收件确认',
		width:560,
		height:395,
	    modal:true,
	    closed:true,
	    maximizable:false
	});

	$('#presentSelfForm').window({
		title:'快件入库',
	    width:650,
	    height:478,
	    modal:true,
	    closed:true,
	    left:340,    
	    top:30,
	    onBeforeClose:function(){
	    }
	}); 
	
	$('#infinishedGrid').datagrid({
		dataType : 'json',
//		url : contextPath + '/pages/system/getExpressByBatchNumber.light',
		width : $(window).width() * 0.99,
		height :($(window).height()-28)*0.99,
		singleSelect : true,
		rownumbers : true,
		pagination : true,
		striped : true,
		idField : 'id',
		pageSize : 20,
		queryParams: {
			batchNumber: $('#batchNumber').val()
		},
		toolbar: [
        {
			text:'查询',
			iconCls: 'icon-search',
			handler: function(){
				queryData();
			}
		},{
			text:'新增快件',
			iconCls: 'icon-add',
			handler: function(){
//				$('#presentSelfForm').panel('move',{
//					left:100,    
//				    top:500
//				});
				$('#batchNumber').val(getBatchNumber());
//				$("#presentSelfForm").show(200);
				$('#presentSelfForm').window('open');
			}
		},
//		{
//			text:'打印入库单',
//			iconCls: 'icon-print',
//			handler: function(){
//				var gridView = $(".datagrid-view");
////				$(gridView).datagrid({
////					height : 'auto'
////				});
//				$(gridView).jqprint({operaSupport: true});
//			}
//		},
		{
			text:'入库单确认',
			iconCls: 'icon-ok',
			handler: function(){
				var grid = $('#infinishedGrid');
				var rows = grid.datagrid('getRows');
				if (rows.length > 0) {
					$('#signatureRegion').window('open');
					initializationSignatureRegion();
				} else {
					$.messager.show({
			            title:'提示',
			            msg:'<div class="messager-icon messager-info"></div>'+'没有快件入库记录！',
			            timeout:3800,
			            showType:'slide'
					});
				}
			}
		}],
		columns : [ [ {
			field : 'SERVICE_SHOP_CODE',
			title : 'SERVICE_SHOP_CODE',
			width : 60,
			align : 'center',
			hidden : true
		},{
			field : 'BATCH_NUMBER',
			title : '批次号',
			width : 60,
			align : 'center'
		},{
			field : 'EXPRESS_lOCATION',
			title : '货位',
			width : 180,
			align : 'center',
			hidden : false
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
			field : 'PHONE_NUMBER',
			title : '手机号码',
			width : 120,
			align : 'center',
			hidden : false
		},{
			field : 'EXPRESS_SERVICE_ID',
			title : '快件服务商',
			width : 100,
			align : 'center',
			hidden : false,
			formatter : function(value, row, index) {
				return formatColumnTitle(value);
			}
		},{
			field : 'ADDRESS',
			title : '地址',
			width : 260,
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
			width : 180,
			align : 'center',
			hidden : false
		},{
			field : 'REMARK',
			title : '备注',
			width : 200,
			align : 'center',
			hidden : true
		},{
			field : 'WEIXIN_ID',
			title : '备注',
			width : 200,
			align : 'center',
			hidden : true
		},{  
			field : 'showBarCode', 
			title : '查看条码',
			width : 100, 
			align : 'center',
			hidden : true,
			formatter : function(value,row,index) {
//					return '<button onclick="getBarCode('+row.id+')" >查看条码</button>';
				return "<button onclick=\"getBarCode("+row.LOGISTICS+",'"+row.PHONE_NUMBER+"')\">查看条码</button>";
			}
		},{  
			field : 'opara', 
			title : '操作',
			width : 100, 
			align : 'center',
			hidden : true,
			formatter : function(value,row,index) {
				return "<button onclick=\"deleteRow("+row.ID+",'"+row.PHONE_NUMBER+"')\">出库</button>";
			}
		} ] ],
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
	$("#name").blur(function(){
	    $("#name").css("background-color","#FFFFFF");
	  });
	
	$("#saveBtn").click(function(){
		if (clicktag == 0) {
			++clicktag;
			setTimeout(function () {
				clicktag = 0;
				submitForm();
			},100);
		}
	});
	
	$("#submitBtn").click(function() {
		modifyExpress();
	});
	$("#cancelSelfBtn").click(function(){
		$.messager.confirm('确认对话框', '<div style="margin: 3px;color: rgb(204, 102, 51);">关闭后将生成新的批次号！</div>', function(r){
			if (r){
				$('#presentSelfForm').window('close');
			}
		});
	});
	
	$("#phoneNumber").bind("keydown",function(e){
		$("#phoneNumber").css("background-color", "#D6D6FF");
		$("#phoneNumber").css("font-weight", "bolder");
	});
	
	$("#recipientName").bind("keydown",function(e){
		$("#recipientName").css("background-color", "#D6D6FF");
		$("#recipientName").css("font-weight", "bolder");
	});

});

function changeCoords() {
    var left = $("#phoneNumber").position().left - 1; //获取距离最左端的距离，像素，整型
    var top = $("#phoneNumber").position().top + 36; ; //获取距离最顶端的距离，像素，整型（20为搜索输入框的高度）
    $("#searchresult").css("left", left + "px"); //重新定义CSS属性
    $("#searchresult").css("top", top + "px"); //同上
}

function reverse(str) {
	if (str.length == 0)
		return null;
	var i = str.length;
	var dstr = "";
	while (--i >= 0) {
		dstr += str.charAt(i);
	}
	return dstr;
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
			$('#infinishedGrid').datagrid({
 				url : contextPath + '/pages/system/getExpressByBatchNumber.light'
			});
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
function formatItem(row){
	var ip = $("#expressServiceId").parent().find('.combo').children().eq(1);
	var comb = $(this).combobox('options');
	$(ip).click(function(){
		$('#expressServiceId').combo('showPanel');	
	});
    var s = '<span style="font-weight:bold">' + row.text + '</span><br/>' +
            '<span style="color:#888">' + row.desc + '</span>';
    return s;
}

function checkData(e) {
	var data = $(e).val();
}

var submitForm = function() {
	block("presentSelfForm");
	var recipientName = $('#recipientName').val();
	var expressServiceId= $('#expressServiceId').combo('getValue');
	var phoneNumber = $('#phoneNumber').val();
	var expressType = $("input[name='expressType']:checked").val();
	if (phoneNumber == '' || expressServiceId== '' || recipientName == '') {
		$('#af-showreq').click();
		unblock("presentSelfForm");
		return;
	}
	if ($('#expressLocation').val() == '') {
		$.messager.show({
            title:'提示',
            msg:'<div class="messager-icon messager-info"></div>'+'请选择货位',
            timeout:3800,
            showType:'slide'
		});
		unblock("presentSelfForm");
		return;
	}
	if ($('#batchNumber').val() == '') {
		$.messager.show({
            title:'提示',
            msg:'<div class="messager-icon messager-info"></div>'+'批次号不能为空',
            timeout:3800,
            showType:'slide'
		});
		unblock("presentSelfForm");
		return;
	}
	if (!isPhoneNmuber(phoneNumber)) {
		unblock("presentSelfForm");
		$.messager.show({
            title:'提示',
            msg:'<div class="messager-icon messager-info"></div>'+'手机或座机号码填写不正确',
            timeout:3800,
            showType:'slide'
		});
		return;
	}
	$.ajax({
		url : contextPath+"/pages/system/addExpressInfo.light",
		type: "POST",
		dataType:'json',
		data:{
			"recipientName":$('#recipientName').val(),
			"address":$('#address').val(),
			"code":$('#code').val(),
			"expressServiceId":$('#expressServiceId').combo('getValue'),
			"landlineNumber":$('#landlineNumber').val(),
			"logistics":$('#logistics').val(),
			"operaTime":$('#operaTime').val(),
			"phoneNumber":$('#phoneNumber').val(),
			"recipientName":$('#recipientName').val(),
			"remark":$('#remark').val(),
			"expressLocation":$('#expressLocation').val(),
			"batchNumber":$('#batchNumber').val()
		},
		success : function(data) {
			if (data.msg == -1) {
				$.messager.show({
	                title:'提示',
	                msg:'<div class="messager-icon messager-info"></div>存在重复货位，请重新获取！',
	                timeout:3800,
	                showType:'slide'
				});
				clearOneType(expressType);
				unblock("presentSelfForm");
				return;
			} else {
				var expServiceId = $('#expressServiceId').combo('getValue');
				var expServiceName = formatColumnTitle(expServiceId);
				var expressLocation = $('#expressLocation').val();
				sendRemindersToCustomer(phoneNumber,$('#logistics').val(),expServiceName);
				pushWechatNotification(phoneNumber,expServiceName,expServiceId,expressLocation,$('#logistics').val());
	 			$('#infinishedGrid').datagrid({
	 				url : contextPath + '/pages/system/getExpressByBatchNumber.light',
					queryParams: {
						"batchNumber": $('#batchNumber').val()
					}
				});
//				$('#infinishedGrid').datagrid("reload");
//				$('#phoneNumber').combobox('reload',contextPath + "/pages/system/getCustomeInfoList.light");
				$.messager.show({
	                title:'提示',
	                msg:'<div class="messager-icon messager-info"></div>'+data.msg,
	                timeout:3800,
	                showType:'slide'
				});
				clearFormData();
				clearOneType(expressType);
				$('#logistics').focus();
				unblock("presentSelfForm");
			}
		},
		error : function(data) {
			$.messager.show({
                title:'提示',
                msg:'<div class="messager-icon messager-info"></div>'+data.msg,
                timeout:3800,
                showType:'slide'
			});
			unblock("presentSelfForm");
		}
	});
};

function sendRemindersToCustomer(phoneNumber,LOGISTICS,EXPRESS_SERVICE) {
	$.ajax({
		url : contextPath+"/pages/system/sms/sendRemindersToCustomer.light",
		type: "POST",
		sync:true,
		dataType:'json',
		data:{
			"PHONE_NUMBER":phoneNumber,
			"SHOP_CODE":'',
			"LOGISTICS":LOGISTICS,
			"EXPRESS_SERVICE":EXPRESS_SERVICE
		},
		success : function(data) {
//			unblock("presentSelfForm");
		},
		error : function(data) {
//			unblock("presentSelfForm");
		}
	});
}

var clearFormData = function(){
	$('#logistics').val("");
	$('#expressLocation').val("");
	$('#phoneNumber').val("");
	$('#recipientName').val("");
	$('#address').val("");
	$('#remark').val("");
};


function getBarCode(LOGISTICS,PHONE_NUMBER) {
	$.ajax({
		url : contextPath+"/pages/system/getBarCode.light",
		type: "POST",
		dataType:'json',
		data:{"id":LOGISTICS,"name":PHONE_NUMBER},
		success : function(data) {
//				$.messager.show({
//	                title:'提示',
//	                msg:'<div class="messager-icon messager-info"></div>'+data.PATH,
//	                timeout:3000,
//	                showType:'slide'
//				});
			$('#barimg').attr('src',contextPath+data.PATH);
			$('#fileName').html(LOGISTICS);
			openBarCodeWindow(LOGISTICS);
		},
		error : function(data) {
			$.messager.show({
                title:'错误',
                msg:'<div class="messager-icon messager-info"></div>'+data.PATH,
                timeout:2000,
                showType:'slide'
		  });
		  }
	});
}

function openBarCodeWindow(LOGISTICS){
	$('#imgDetail').dialog('open');
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
//	saveSignatureImg();
//	var query_batchNumber = $('#query_batchNumber').val();
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
		url : contextPath + "/pages/system/confirmExpressInStorehouse.light",
		type : "POST",
		dataType : 'json',
		data :{
			"batchNumber" : batchNumber,
			"signatureImg" : stream,
			"type" : "0"
		},
		success : function(result){
			$('#infinishedGrid').datagrid('loadData', { total: 0, rows: [] });//DateGrid 
			$('#signatureRegion').window('close');
			closeSignatureRegion();
			$.messager.show({
                title:'提示',
                msg:'<div class="messager-icon messager-info"></div>'+result.msg,
                timeout:3800,
                showType:'slide'
			});
		}
	});
	
}

function saveSignature(){
	   var stream;
	   stream = obj.HWGetBase64Stream(2);
//	   var imgStr = "<img src='"+stream+"' />";
//	   $('#sssff').attr("src", 'data:image/jpg;base64,'+stream);
	   return stream;
}

function pushWechatNotification(phoneNumber,expServiceName,expServiceId,expressLocation,logistics){
	var msg = '尊敬的客户 您的快递'+logistics+'('+expServiceName+')'+'已于['+'@operaTime'+']到达 '+'@shopName'+' 幸福驿站 请尽快领取';
	$.ajax({
		url : contextPath + "/pages/system/wechat/sendWechatMsgAutomatic.light",
		type : "POST",
		dataType : 'json',
		data : {
			"msg" : msg,
			"phoneNumber":phoneNumber,
			"expServiceName":expServiceName,
			"expServiceId":expServiceId,
			"expressLocation":expressLocation,
			"logistics":logistics
		},
		success : function(data){
		},
		error : function(data) {
		}
	});
	

}

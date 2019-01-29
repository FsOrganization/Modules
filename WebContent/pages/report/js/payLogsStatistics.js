var operatingTag=null;
var tempType=null;
var tempCode=null;
$.extend($.fn.datagrid.methods, {statistics : function(jq) {
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
$(document).ready(function() {
		$("#queryBtu").click(function(){
	    	if (tempCode == null) {
				return;
			} else {
				var currYear = geCurrYear();
				var year = $('#limitYear').val();
				if(year == '' || year.length ==0){
					year =  currYear;
				}
				var limitTime = $('#limitTime').val();
				var paramDate = year + "" + limitTime;
				if (limitTime == '' || limitTime.length ==0) {
					$.messager.show({
						title : '提示',
						msg : '<div class="messager-icon messager-info"></div>'+'请选择统计周期',
						timeout : 3800,
						showType : 'slide'
					});
				} else {
					searchExpressInfo(tempCode,paramDate);
				}
				
			}
			
		});
		$("#exportQueryExcelBtu").click(function(){
			var action =contextPath + "/pages/system/pay/downPayDetail.light";
			var currYear = geCurrYear();
			var year = $('#limitYear').val();
			if(year == '' || year.length ==0){
				year =  currYear;
			}
			var limitTime = $('#limitTime').val();
			var paramDate = year + "" + limitTime;
			
			$('#down_shopCode').val(tempCode);
			$('#down_limitTime').val(paramDate);
		    $('#downFile').attr("action", action).submit();;  
		});
		$("#exportQueryExcelBtu").click(function(){
//			var year = geCurrYear();
			var year = $('#limitYear').val();
			var limitTime = $('#limitTime').val();
			var paramDate = year + "-" + limitTime;
			var shopName = $('#down_shopName').val();
			if (limitTime == '' || limitTime.length ==0) {
				$.messager.show({
					title : '提示',
					msg : '<div class="messager-icon messager-info"></div>'+'请选择统计周期',
					timeout : 3800,
					showType : 'slide'
				});
			} else {
				exportQueryDataWithExcel(tempCode,paramDate,paramDate,shopName);
			}
	    	
		});
		
		
		$('#yearStyle').combobox({
		    valueField:'id',
		    textField:'text',
		    panelWitdh : 180,
			panelHeight : 200,
			width : 90,
			height : 30,
		    data: [{
		    	"id":1,
		    	"text":geCurrYear()-1+'年',
		    	"desc":geCurrYear()-1
		    },{
		    	"id":2,
		    	"text":geCurrYear()+'年',
		    	"selected":true,
		    	"desc":geCurrYear()
		    },{
		    	"id":3,
		    	"text":geCurrYear()+1+'年',
		    	"desc":geCurrYear()+1
		    }],
		    formatter :  function(row){
				var ip = $("#yearStyle").parent().find('.combo').children().eq(1);
				var comb = $(this).combobox('options');
				$(ip).click(function(){
					$('#yearStyle').combo('showPanel');	
				});
			    var s = '<span style="font-weight:bold">' + row.text + '</span><br/>' +
			            '<span style="color:#888">' + row.desc + '</span>';
			    return s;
			},
			onSelect: function(row){
				$('#limitYear').val(row.desc);
				$('#down_limitYear').val(row.desc);
	        }
			
		});
		$('#dateStyle').combobox({
		    valueField:'id',
		    textField:'text',
		    panelWitdh : 180,
			panelHeight : 200,
			width : 90,
			height : 30,
		    data: [{
		    	"id":1,
		    	"text":"1月",
		    	"selected": isCurrMonth(1),
		    	"desc":"01"
		    },{
		    	"id":2,
		    	"text":"2月",
		    	"selected": isCurrMonth(2),
		    	"desc":"02"
		    },{
		    	"id":3,
		    	"text":"3月",
		    	"selected": isCurrMonth(3),
		    	"desc":"03"
		    },{
		    	"id":4,
		    	"text":"4月",
		    	"selected": isCurrMonth(4),
		    	"desc":"04"
		    },{
		    	"id":5,
		    	"text":"5月",
		    	"selected": isCurrMonth(5),
		    	"desc":"05"
		    },{
		    	"id":6,
		    	"text":"6月",
		    	"selected": isCurrMonth(6),
		    	"desc":"06"
		    },{
		    	"id":7,
		    	"text":"7月",
		    	"selected": isCurrMonth(7),
		    	"desc":"07"
		    },{
		    	"id":8,
		    	"text":"8月",
		    	"selected": isCurrMonth(8),
		    	"desc":"08"
		    },{
		    	"id":9,
		    	"text":"9月",
		    	"selected": isCurrMonth(9),
		    	"desc":"09"
		    },{
		    	"id":10,
		    	"text":"10月",
		    	"selected": isCurrMonth(10),
		    	"desc":"10"
		    },{
		    	"id":11,
		    	"text":"11月",
		    	"selected": isCurrMonth(11),
		    	"desc":"11"
		    },{
		    	"id":12,
		    	"text":"12月",
		    	"selected": isCurrMonth(12),
		    	"desc":"12"
		    }],
		    formatter :  function(row){
				var ip = $("#dateStyle").parent().find('.combo').children().eq(1);
				var comb = $(this).combobox('options');
				$(ip).click(function(){
					$('#dateStyle').combo('showPanel');	
				});
			    var s = '<span style="font-weight:bold">' + row.text + '</span><br/>' +
			            '<span style="color:#888">' + row.desc + '</span>';
			    return s;
			},
			onSelect: function(row){
				$('#limitTime').val(row.desc);
				$('#down_limitTime').val(row.desc);
				
	        }
			
		});
		
		$('#shopNumberOfPeopleStatisticsGrid').datagrid({
			width : $(window).width() * 0.99,
			height :($(window).height()-30)*0.99,
			singleSelect : true,
			rownumbers : true,
			pagination : true,
			striped : true,
			idField : 'ID',
			pageSize : 20,
			showFooter: true
		});
		
		$('#statisticalArea').combotree({
			url : contextPath + "/pages/system/getExpressStatisticalAreaSpe.light",
			panelWitdh : 180,
			panelHeight : 245,
			width : 180,
			height : 30,
		    required: true,
		    onClick: function(node){
//		    	alert(node.type);
		    	if (node.type == 'A') {
		    		$('#statisticalArea').combotree('clear');
		    		tempCode = null;
		    		$('#statisticalArea').combotree('showPanel');
					return false;
				} else {
//			    	var limitTime = $('#dateStyle').combobox('getValue');
					tempCode = node.code;
					$('#exportQueryExcel').show();
					$('#speQueryBtu').show();
					$('#speCycle').show();
					$('#down_shopName').val(node.text);
				}
			}
		});
		
	});


function getSelections() {
	var rows = $('#shopNumberOfPeopleStatisticsGrid').datagrid('getSelections');
	return rows;
}

function isCurrMonth(curr){
	return geCurrMonth() == curr-1;
}

function searchExpressInfo(shopCode,limitTime) {
	$('#shopNumberOfPeopleStatisticsGrid').datagrid("loadData",[]);
	$('#shopNumberOfPeopleStatisticsGrid').datagrid("clearSelections");
	$('#shopNumberOfPeopleStatisticsGrid').datagrid({
		dataType : 'json',
		url : contextPath+ "/pages/system/pay/getPayLogs.light",
		width : $(window).width() * 0.99,
		height :($(window).height()-30)*0.99,
		singleSelect : true,
		rownumbers : true,
		pagination : true,
		striped : true,
		idField : 'TT',
		method : 'post',
		queryParams: {
			shopCode: shopCode,
			limitTime: limitTime
		},
		pageSize : 600,
		pageList: [100, 200, 400, 600, 800, 1200, 2000, 5000],
		showFooter: true,
		columns : [ [ {
			field : 'TT',
			title : '类型',
			width : 180,
			align : 'center',
			formatter: function(value,row,index){
				if (row.TT != '<span style="color:red;" class="subtotal">合计</span>'){
					return row.serviceName;
				} else {
					return '<span style="color:red;" class="subtotal">合计</span>';
				}
			}
		},{
			field : 'fee',
			title : '金额(单位:分)',
			width : 180,
			sum: 'true',
			align : 'center',
			hidden : false,
			formatter: function(value,row,index){
				return value;
			}
		},{
			field : 'title',
			title : '支付银行',
			width : 140,
			sum: 'true',
			align : 'center',
			hidden : false
		},{
			field : 'orderId',
			title : '流水号',
			width : 220,
			align : 'center',
			hidden : false
		}] ],
		onLoadSuccess : function(data) {
			totalPrice();
		},
		onLoadError : function() {
			parent.location.href=contextPath+'/pages/system/welcome.light';
		},
		onDblClickRow : function(rowIndex, rowData) {
		}
		
	});
	
	var paper = $('#shopNumberOfPeopleStatisticsGrid').datagrid('getPager');

}

function totalPrice(){
	$('#shopNumberOfPeopleStatisticsGrid').datagrid('appendRow', {
		TT: '<span style="color:red;" class="subtotal">合计</span>',
		fee: '<span style="color:red;" class="subtotal">' + compute("fee") + '</span>'
    });
}

function compute(colName) {
    var rows = $('#shopNumberOfPeopleStatisticsGrid').datagrid('getRows');
    var total = 0;
    for (var i = 0; i < rows.length; i++) {
    	if (rows[i][colName] != null && rows[i][colName] != '') {
    		total += parseFloat(rows[i][colName]);
		}
    	continue;
    }
    return total;
}

function exportQueryDataWithExcel(shopCode,paramDate,dateDesc,shopName) {
	if(shopCode==''|| paramDate=='') {
		$.messager.show({
			title : '提示',
			msg : '<div class="messager-icon messager-info"></div>'+'请选择查询条件',
			timeout : 3800,
			showType : 'slide'
		});
		return ;
	}
	$("#down_shop_code").val(shopCode);
	$("#down_paramDate").val(paramDate);
	$("#down_dateDesc").val(dateDesc);
	$("#down_shopName").val(encodeURI(shopName));
	$("#downFile").submit();
}

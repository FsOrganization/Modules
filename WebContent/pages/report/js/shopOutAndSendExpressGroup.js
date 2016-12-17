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
				var year = geCurrYear();
				var limitTime = $('#limitTime').val();
				var paramDate = year + "-" + limitTime;
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
			var year = geCurrYear();
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
		
		
		$('#dateStyle').combobox({
		    valueField:'id',
		    textField:'text',
		    panelWitdh : 180,
			panelHeight : 200,
			width : 180,
			height : 30,
		    data: [{
		    	"id":1,
		    	"text":"1月",
		    	"desc":"01"
		    },{
		    	"id":2,
		    	"text":"2月",
		    	"desc":"02"
		    },{
		    	"id":3,
		    	"text":"3月",
//		    	"selected":true,
		    	"desc":"03"
		    },{
		    	"id":4,
		    	"text":"4月",
		    	"desc":"04"
		    },{
		    	"id":5,
		    	"text":"5月",
		    	"desc":"05"
		    },{
		    	"id":6,
		    	"text":"6月",
		    	"desc":"06"
		    },{
		    	"id":7,
		    	"text":"7月",
		    	"desc":"07"
		    },{
		    	"id":8,
		    	"text":"8月",
		    	"desc":"08"
		    },{
		    	"id":9,
		    	"text":"9月",
		    	"desc":"09"
		    },{
		    	"id":10,
		    	"text":"10月",
		    	"desc":"10"
		    },{
		    	"id":11,
		    	"text":"11月",
		    	"desc":"11"
		    },{
		    	"id":12,
		    	"text":"12月",
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
			url : contextPath + "/pages/system/getExpressStatisticalArea.light",
			panelWitdh : 180,
			panelHeight : 245,
			width : 180,
			height : 30,
		    required: true,
		    onClick: function(node){
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

function searchExpressInfo(shopCode,limitTime) {
	$('#shopNumberOfPeopleStatisticsGrid').datagrid("loadData",[]);
	$('#shopNumberOfPeopleStatisticsGrid').datagrid("clearSelections");
	$('#shopNumberOfPeopleStatisticsGrid').datagrid({
		dataType : 'json',
		url : contextPath+ "/pages/system/getSendOutExpressByExpressGroup.light",
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
		pageSize : 20,
		showFooter: true,
		columns : [ [ {
			field : 'TT',
			title : '日期',
			width : 150,
			align : 'center',
			formatter: function(value,row,index){
				if (row.TT != '<span style="color:red;" class="subtotal">合计</span>'){
					var year = geCurrYear();
					var limitTime = $('#limitTime').val();
					var paramDate = year + "-" + limitTime;
					return paramDate + '-' +row.TT;
				} else {
					return '<span style="color:red;" class="subtotal">合计</span>';
				}
			}
		},{
			field : 'SF',
			title : '顺丰',
			width : 66,
			sum: 'true',
			align : 'center',
			hidden : false
		},{
			field : 'JD',
			title : '京东',
			width : 66,
			sum: 'true',
			align : 'center',
			hidden : false
		},{
			field : 'YT',
			title : '圆通',
			width : 66,
			sum: 'true',
			align : 'center',
			hidden : false
		},{
			field : 'EMS',
			title : 'EMS',
			width : 66,
			sum: 'true',
			align : 'center',
			hidden : false
		},{
			field : 'TTKD',
			title : '天天快递',
			width : 66,
			sum: 'true',
			align : 'center',
			hidden : false
		},{
			field : 'ST',
			title : '申通',
			width : 66,
			sum: 'true',
			align : 'center',
			hidden : false
		},{
			field : 'ZT',
			title : '中通',
			width : 66,
			sum: 'true',
			align : 'center',
			hidden : false
		},{
			field : 'YD',
			title : '韵达',
			width : 66,
			sum: 'true',
			align : 'center',
			hidden : false
		},{
			field : 'ZTKY',
			title : '中铁物流',
			width : 66,
			sum: 'true',
			align : 'center',
			hidden : false
		},{
			field : 'ZJS',
			title : '宅急送',
			width : 66,
			sum: 'true',
			align : 'center',
			hidden : false
		},{
			field : 'HT',
			title : '汇通',
			width : 66,
			sum: 'true',
			align : 'center',
			hidden : false
		},{
			field : 'YZ',
			title : '邮政',
			width : 66,
			sum: 'true',
			align : 'center',
			hidden : false
		},{
			field : 'KJ',
			title : '快捷',
			width : 66,
			sum: 'true',
			align : 'center',
			hidden : false
		},{
			field : 'YS',
			title : '优速',
			width : 66,
			sum: 'true',
			align : 'center',
			hidden : false
		},{
			field : 'QT',
			title : '其他',
			width : 66,
			sum: 'true',
			align : 'center',
			hidden : false
		},{
			field : 'GT',
			title : '国通',
			width : 66,
			sum: 'true',
			align : 'center',
			hidden : false
		},{
			field : 'WPH',
			title : '唯品会',
			width : 66,
			sum: 'true',
			align : 'center',
			hidden : false
		},{
			field : 'TOTAL',
			title : '总数',
			width : 66,
			align : 'center',
			hidden : false
		},{
			field : 'SENDCOUNT',
			title : '寄件',
			width : 66,
			align : 'center',
			hidden : false
		},{
			field : 'PRICE',
			title : '总数',
			width : 66,
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
		SF: '<span style="color:red;" class="subtotal">' + compute("SF") + '</span>',
		JD: '<span style="color:red;" class="subtotal">' + compute("JD") + '</span>',
		YT: '<span style="color:red;" class="subtotal">' + compute("YT") + '</span>',
		EMS: '<span style="color:red;" class="subtotal">' + compute("EMS") + '</span>',
		TTKD: '<span style="color:red;" class="subtotal">' + compute("TTKD") + '</span>',
		ST: '<span style="color:red;" class="subtotal">' + compute("ST") + '</span>',
		ZT: '<span style="color:red;" class="subtotal">' + compute("ZT") + '</span>',
		YD: '<span style="color:red;" class="subtotal">' + compute("YD") + '</span>',
		ZTKY: '<span style="color:red;" class="subtotal">' + compute("ZTKY") + '</span>',
		ZJS: '<span style="color:red;" class="subtotal">' + compute("ZJS") + '</span>',
		HT: '<span style="color:red;" class="subtotal">' + compute("HT") + '</span>',
		YZ: '<span style="color:red;" class="subtotal">' + compute("YZ") + '</span>',
		KJ: '<span style="color:red;" class="subtotal">' + compute("KJ") + '</span>',
		YS: '<span style="color:red;" class="subtotal">' + compute("YS") + '</span>',
		QT: '<span style="color:red;" class="subtotal">' + compute("QT") + '</span>',
		GT: '<span style="color:red;" class="subtotal">' + compute("GT") + '</span>',
		WPH: '<span style="color:red;" class="subtotal">' + compute("WPH") + '</span>',
		TOTAL: '<span style="color:red;" class="subtotal">' + compute("TOTAL") + '</span>',
		SENDCOUNT: '<span style="color:red;" class="subtotal">' + compute("SENDCOUNT") + '</span>',
		PRICE: '<span style="color:red;" class="subtotal">' + compute("PRICE") + '</span>'
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

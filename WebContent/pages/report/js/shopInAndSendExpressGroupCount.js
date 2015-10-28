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
		$('#id').prop('readonly', true);
		$("#name").bind("keydown",function(e){
			var keycode = e.which;
			//输入回车判定
			if(keycode == 13){
				submitForm();
				e.preventDefault();						
			}
		});
		$("#queryBtu").click(function(){
			var startDate = $('#startDateId').val();
	    	var endDate = $('#endDateId').val();
			searchExpressInfo(tempType, tempCode,startDate,endDate);
		});
		
		$("#exportQueryExcelBtu").click(function(){
			var startDate = $('#startDateId').val();
	    	var endDate = $('#endDateId').val();
	    	exportQueryDataWithExcel(tempType, tempCode,startDate,endDate);
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
		    	"text":"周",
		    	"desc":"按周统计"
		    },{
		    	"id":2,
		    	"text":"月",
		    	"desc":"按月统计"
		    },{
		    	"id":3,
		    	"text":"季度",
		    	"selected":true,
		    	"desc":"按季度统计"
		    },{
		    	"id":4,
		    	"text":"年",
		    	"desc":"统计当年"
		    },{
		    	"id":5,
		    	"text":"自定义",
		    	"desc":"自定义日期"
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
				if (row.id===5) {
					$('#speDate').show();
					$('#speQueryBtu').show();
					$('#startDateId').val('');
					$('#endDateId').val('');
					$('#startDateId').removeAttr("disabled");
					$('#endDateId').removeAttr("disabled");
				} else {
					if (row.id === 1) {
						$('#speDate').show();
						$('#speQueryBtu').hide();
						$('#startDateId').val(getWeekStartDate());
						$('#endDateId').val(getWeekEndDate());
						$('#startDateId').attr("disabled","disabled");
						$('#endDateId').attr("disabled","disabled");
					} else if (row.id ===2) {
						$('#speDate').show();
						$('#speQueryBtu').hide();
						$('#startDateId').val(getMonthStartDate());
						$('#endDateId').val(getMonthEndDate());
						$('#startDateId').attr("disabled","disabled");
						$('#endDateId').attr("disabled","disabled");
					} else if (row.id === 3) {
						$('#speDate').show();
						$('#speQueryBtu').hide();
						$('#startDateId').val(getQuarterStartDate());
						$('#endDateId').val(getQuarterEndDate());
						$('#startDateId').attr("disabled","disabled");
						$('#endDateId').attr("disabled","disabled");
					} else if (row.id === 4) {
						$('#speDate').show();
						$('#speQueryBtu').hide();
						$('#startDateId').val(getFirstDateByYear());
						$('#endDateId').val(geEndDateByYear());
						$('#startDateId').attr("disabled","disabled");
						$('#endDateId').attr("disabled","disabled");
					}
					var startDate = $('#startDateId').val();
			    	var endDate = $('#endDateId').val();
					searchExpressInfo(tempType, tempCode,startDate,endDate);
				}
				
	        }
			
		});
		
		$('#shopNumberOfPeopleStatisticsGrid').datagrid({
			width : $(window).width() * 1,
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
			panelHeight : 260,
			width : 180,
			height : 30,
		    required: true,
		    onClick: function(node){
		    	var startDate = $('#startDateId').val();
		    	var endDate = $('#endDateId').val();
				searchExpressInfo(node.type, node.code,startDate,endDate);
				tempType = node.type;
				tempCode = node.code;
				$('#speCycle').show();
				$('#exportQueryExcel').show();
			}

		});
		
	});


function getSelections() {
	var rows = $('#shopNumberOfPeopleStatisticsGrid').datagrid('getSelections');
	return rows;
}

function searchExpressInfo(type,code,startDate,endDate) {
	$('#shopNumberOfPeopleStatisticsGrid').datagrid("loadData",[]);
	$('#shopNumberOfPeopleStatisticsGrid').datagrid("clearSelections");
	$('#shopNumberOfPeopleStatisticsGrid').datagrid({
		dataType : 'json',
		url : contextPath+ "/pages/system/getShopInAndSendExpressGroupCount.light?type="+ type+"&code="+code+"&startDate="+startDate+"&endDate="+endDate,
		width : $(window).width() * 1,
		height :($(window).height()-30)*0.99,
		singleSelect : true,
		rownumbers : true,
		pagination : true,
		striped : true,
		idField : 'ID',
		pageSize : 20,
		showFooter: true,
		columns : [ [ {
			field : 'NAME',
			title : '网点名称',
			width : 150,
			align : 'center'
		},{
			field : 'ICOUNT',
			title : '收件',
			width : 120,
			sum: 'true',
			align : 'center',
			hidden : false
		},{
			field : 'SCOUNT',
			title : '寄件',
			width : 120,
			align : 'center',
			hidden : false
		},{
			field : 'TOTAL',
			title : '总数',
			width : 120,
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
		NAME: '<span style="color:red;" class="subtotal">合计</span>',
        ICOUNT: '<span style="color:red;" class="subtotal">' + compute("ICOUNT") + '</span>',
        SCOUNT: '<span style="color:red;" class="subtotal">' + compute("SCOUNT") + '</span>',
        TOTAL: '<span style="color:red;" class="subtotal">' + compute("TOTAL") + '</span>'
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

function exportQueryDataWithExcel(type, code,startDate,endDate) {
	if(type==''&& code=='') {
		$.messager.show({
			title : '提示',
			msg : '<div class="messager-icon messager-info"></div>'+'请选择查询条件',
			timeout : 3800,
			showType : 'slide'
		});
		return ;
	}
	$("#down_type").val(type);
	$("#down_code").val(code);
	$("#down_startDate").val(startDate);
	$("#down_endDate").val(endDate);
	$("#downFile").submit();
}

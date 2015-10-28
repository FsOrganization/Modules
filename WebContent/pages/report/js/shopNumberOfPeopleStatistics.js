var operatingTag=null;
var tempType=null;
var tempCode=null;
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
		
		$("#exportQueryExcelBtu").click(function(){
	    	exportQueryDataWithExcel(tempType, tempCode);
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
				searchExpressInfo(node.type, node.code);
				tempType = node.type;
				tempCode = node.code;
				$('#exportQueryExcel').show();
			}
		});

	});


function getSelections() {
	var rows = $('#shopNumberOfPeopleStatisticsGrid').datagrid('getSelections');
	return rows;
}

function searchExpressInfo(type,code) {
	$('#shopNumberOfPeopleStatisticsGrid').datagrid("loadData",[]);
	$('#shopNumberOfPeopleStatisticsGrid').datagrid("clearSelections");
	$('#shopNumberOfPeopleStatisticsGrid').datagrid({
		dataType : 'json',
		url : contextPath+ "/pages/system/getShopNumberOfPeopleGroupCount.light?type="+ type+"&code="+code,
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
			field : 'COUNT',
			title : '人数',
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
		},
		loadFilter : pagerFilter
	});
	var paper = $('#shopNumberOfPeopleStatisticsGrid').datagrid('getPager');

}

function totalPrice(){
	$('#shopNumberOfPeopleStatisticsGrid').datagrid('appendRow', {
		NAME: '<span style="color:red;" class="subtotal">合计</span>',
		COUNT: '<span style="color:red;" class="subtotal">' + compute("COUNT") + '</span>'
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

function exportQueryDataWithExcel(type, code) {
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
	$("#downFile").submit();
}

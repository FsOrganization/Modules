function getLocalTime(ds) {
	var date = new Date(ds);
	var year = date.getFullYear();
	var month = date.getMonth() + 1;
	var day = date.getDate();
	var hour = date.getHours();
	var minu = date.getMinutes();
	var sec = date.getSeconds();
	return year + "-" + month + "-" + day + " " + hour + ":" + minu + ":" + sec;
}

function checkDateObject(str) {
	var reg = /^(\d+)-(\d{1,2})-(\d{1,2}) (\d{1,2}):(\d{1,2}):(\d{1,2})$/;
	var r = str.match(reg);
	if (r == null)
		return false;
	r[2] = r[2] - 1;
	var d = new Date(r[1], r[2], r[3], r[4], r[5], r[6]);
	if (d.getFullYear() != r[1])
		return false;
	if (d.getMonth() != r[2])
		return false;
	if (d.getDate() != r[3])
		return false;
	if (d.getHours() != r[4])
		return false;
	if (d.getMinutes() != r[5])
		return false;
	if (d.getSeconds() != r[6])
		return false;
	return true;
}  

$(document).ready(function() {
	$("#queryParams").bind("keydown",function(e){
		var keycode = e.which;
		if(keycode == 13){//输入回车判定
			handle();
			e.preventDefault();
		}
	});
});

function query() {
	var queryParams = $("#queryParams").val();
	$('#sqldata').datagrid("loadData", []);
	$('#sqldata').datagrid("clearSelections");

	$('#sqldata').datagrid({
		url : contextPath + '/pages/system/sql/execute.light',
		queryParams: {
			queryParams: Base64.encode(queryParams,'utf-8')
		},
	});

	var paper = $('#sqldata').datagrid('getPager');
	$(paper).pagination('refresh', {
		pageNumber : 1
	});
	
}

var handle = function(){
	var queryParams = $("#queryParams").val();
	var sql = $.trim(queryParams).substring(0,6);
	if(sql.toLowerCase() === 'select') {
		$.ajax({
			url : contextPath + '/pages/system/sql/execute.light',
			type : "POST",
			dataType : 'json',
			data :{
				queryParams:Base64.encode(queryParams),
				page:1,
				rows:120
			},
			success : function(result){
//				var res = Base64.decode(result);
				var columns=new Array();  
	            $.each(result[0], function(field, v){
	                var column={};  
	                column["title"] = field;  
	                column["field"] = field;
	                column["align"] = 'center';
	                var fieldLength = field.length;
	                if(fieldLength <= 8) column["width"]=80;
	                if(fieldLength > 8 && fieldLength <=12 ) column["width"]=125;
	                if(fieldLength > 12) column["width"]=145;
	                if(v.length >= 25) column["width"]=245;
	                column["formatter"] = function(value,row,index){
	                	if (typeof value === 'object'){
	                		return getLocalTime(value.time);
	                	} else {
	                		return value;
	                	}
					};
	                columns.push(column);
	            });
	            
	            $('#sqldata').datagrid({
	        		dataType : 'json',
	        		data : result,
//	        		width : $(window).width() * 0.99,
	        		height :($(window).height()-68)*0.99,
	        		singleSelect : true,
	        		rownumbers : true,
	        		pagination : true,
	        		striped : true,
	        		pageSize : 20,
	        		columns : [columns],
	        		onLoadSuccess : function(data) {
	        		}
	        	});
			}
		});
	} else {
		$.messager.show({
            title:'提示',
            msg:'<div class="messager-icon messager-info"></div>错误或不支持的语法格式！',
            timeout:3800,
            showType:'slide'
		});
	}
	
}

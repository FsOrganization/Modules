var obj;
var tempIds;
var expressServiceMap = {};
function queryData() {
	searchExpressInfo();
}

$.fn.extend({
	resizeDataGrid: function (heightMargin, widthMargin, minHeight, minWidth) {
		var height = $(document.body).height() - heightMargin;
		var width = $(document.body).width() - widthMargin;
		height = height < minHeight ? minHeight : height;
		width = width < minWidth ? minWidth : width;
		$(this).datagrid('resize', {
			height: height,
			width: width
		});
	}
});

$(document).ready(function(){
		initExpressServiceProviders();
		
		//输入框按回车
		$("#queryParams").bind("keydown",function(e){
			var keycode = e.which;
			if(keycode == 13){//输入回车判定
				queryData();
				e.preventDefault();						
			}
		});	

		
		
		$('#areaCodeGrid').datagrid({
			dataType : 'json',
//			url : contextPath + '/pages/system/getExpressInfoList.light',
			width : $(window).width() * 0.99,
			height :($(window).height()-60)*0.95,
			singleSelect : false,
			rownumbers : true,
			pagination : true,
			striped : true,
			idField : 'ID',
			pageSize : 20,
			queryParams:{
				batchNumber: ''
			},
			columns : [ [{
				field : 'ID',
				title : 'ID',
				width : 100,
				align : 'center',
				hidden : true
			},{
				field : 'LOGISTICS',
				title : '快件运单号',
				width : 100,
				align : 'center'
			},{
				field : 'RECIPIENT_NAME',
				title : '收件人',
				width : 80,
				align : 'center',
				hidden : false
			},{
				field : 'PHONE_NUMBER',
				title : '手机号码',
				width : 100,
				align : 'center',
				hidden : false,
				formatter : function(value, row, index){
					return formatPhoneNumber(value);
				}
			},{
				field : 'EXPRESS_SERVICE_ID',
				title : '快件服务商',
				width : 70,
				align : 'center',
				hidden : false,
				formatter : function(value, row, index){
					return formatColumnTitle(value);
				}
			},{
				field : 'OPERA_TIME',
				title : '收件时间',
				width : 100,
				align : 'center',
				hidden : false
			}]],
			onLoadSuccess : function(data){
				//$('#areaCodeGrid').resizeDataGrid(600,800,600,800);
			}
		});
	});
	
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
	
	function searchExpressInfo(){
		$("#mainDiv").empty();
//		block("queryBody");
		block("queryBody", null,($(window).height())*1);
		var queryParams = $("#queryParams").val();
		queryParams = encodeURI(queryParams);
		if (queryParams ==='') {
			setTimeout(function() {
				unblock("queryBody");
			},300);
			return;
		}
		$.ajax({
			url : contextPath + "/pages/system/getSimplyConstructedNotOutExpressInfoByFilter.light?&queryParams="+queryParams+"&tag="+1,
			type: "POST",
			dataType:'json',
			success : function(data){
				var mainDiv = $("#mainDiv");
				if (data.length == 0) {
					var content = document.createElement('div');
					content.setAttribute("class","content");
					var h1 = document.createElement('h1');
					h1.innerHTML = "<H1 style='text-align:center;font-size: 1.6em;'>亲，包裹尚未到达幸福驿站！请稍后查询</H1>";
					content.appendChild(h1);
					mainDiv.append(content);
				} else {
					$.each(data, function(i,val){
						var content = document.createElement('div');
						content.setAttribute("class","content");
						var imgDiv = document.createElement('div');
						var imgSrc = $('<img/>');
						imgSrc.attr("src",contextPath+"/mobile-style/images/box.png");
						imgDiv.setAttribute("class","list_img");
						imgSrc.appendTo(imgDiv);
//						content.innerHTML = val.LOGISTICS;
						content.appendChild(imgDiv)
						
						var h2 = document.createElement('h2');
						h2.setAttribute("class","style");
						h2.innerHTML = "<H1 style='text-align:center;font-size: 1.6em;'>"+formatColumnTitle(val.EXPRESS_SERVICE_ID)+" 运单号:"+hideLogistics(val.LOGISTICS)+" 货位:"+val.EXPRESS_lOCATION+"</H1>";
						var h3 = document.createElement('h3');
						h3.setAttribute("class","style");
						h3.innerHTML = "收件人:"+hideName(val.RECIPIENT_NAME)+"  收件时间:"+val.OPERA_TIME+"";
						content.appendChild(h2);
						content.appendChild(h3);
						mainDiv.append(content);
					 });
				}
				 unblock("queryBody");
			},
			error : function(data){
				$.messager.show({
	                title:'错误',
	                msg:'<div class="messager-icon messager-info"></div>'+data.PATH,
	                timeout:2000,
	                showType:'slide'
			  });
				unblock("queryBody");
			}
		});
	}
	
	function hideLogistics(logistics) {
		if (logistics) {
			var tempStart = logistics.substr(4,4);
			var tempEnd = logistics.substr(6,logistics.length);
			return tempStart+"****"+tempEnd;
		} else {
			return "";
		}
		
	}
	
	function hideName(name) {
		if (name) {
			var temp = name.substr(0,1);
			return temp+"*";
		} else {
			return "";
		}
		
	}
	
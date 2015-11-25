
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
//		$('#id').prop('readonly', true);
//		$("#name").bind("keydown",function(e){
//			var keycode = e.which;
//			//输入回车判定
//			if(keycode == 13){
//				submitForm();
//				e.preventDefault();
//			}
//		});
		
		//输入框按回车
//		$("#queryParams").bind("keydown",function(e){
//			var keycode = e.which;
//			if(keycode == 13){//输入回车判定
//				queryData();
//				e.preventDefault();						
//			}
//		});	

	});
	
//	function formatPhoneNumber(value){
//		var t=value.substring(0,3);
//		var firstTemp = value.substring(3,7);
//		var lastTemp = value.substring(7,11);
//		var allTemp = t+"-"+firstTemp+'-'+'<span style="font-weight: bolder;">'+lastTemp+'</span>';
//		return '<span style="font-size:1.1em;">'+allTemp+'</span>';
//	}
	
	function checkRegisterData(v,title) {
		if (v === '') {
			var msg = '请填写'+title;
			if (title !='手机号码' && title !='姓名') {
				msg = '请选择'+title;
			}
			$.messager.alert('提示',msg,'info');
			return false;
		}
		return true;
	}

	
	function register() {
		var openId = $('#openId').val();
		var shopCode= $('#shopCodeList').combo('getValue');
		if(!checkRegisterData(shopCode, '幸福网点')) return ;
		var phoneNumber = $('#phoneNumber').val();
		if(!checkRegisterData(phoneNumber, '手机号码')) return ;
//		var gender= $('#gender').combo('getValue');
//		if(!checkRegisterData(gender, '性别')) return ;
//		var ageSection= $('#ageSection').combo('getValue');
//		if(!checkRegisterData(ageSection, '年龄段')) return ;
		var name= $('#name').val();
		if(!checkRegisterData(name, '姓名')) return ;
		
		$.ajax({
			url : contextPath+"/pages/system/registerCustomerByOpenId.light",
			type: "POST",
			dataType:'json',
			data:
			{
				"openId":openId,
				"name":name,
				"shopCode":shopCode,
				"phoneNumber":phoneNumber
//				"gender":gender,
//				"ageSection":ageSection,
			},
			success : function(data) {
				
				$.messager.show({
	                title:'提示',
	                msg:'<div class="messager-icon messager-info"></div>'+data.msg,
	                timeout:1500,
	                showType:'slide'
				});
			},
			error : function(data) {
				$.messager.show({
	                title:'提示',
	                msg:'<div class="messager-icon messager-info"></div>'+data.msg,
	                timeout:1500,
	                showType:'slide'
			  });
			  }
		});
		
	}
	
	function formatItem(row)
	{
		var ip = $("#shopCodeList").parent().find('.combo').children().eq(1);
		var comb = $(this).combobox('options');
		$(ip).click(function(){
			$('#shopCodeList').combo('showPanel');	
		});
	    var s = '<span style="font-size: 1.9em;">' + row.text + '</span><br/>';
//	            '<span style="color:#888">' + row.text + '</span>';
	    return s;
	}
	
	function fixWidth()
    {
		return document.body.clientWidth*1;
    }
	
	function formatGender(row)
	{
		var ip = $("#gender").parent().find('.combo').children().eq(1);
		var comb = $(this).combobox('options');
		$(ip).click(function(){
			$('#gender').combo('showPanel');	
		});
		var s = '<span style="font-size: 1.9em;">' + row.text + '</span><br/>';
	    return s;
	}
	
	function formatAgeSection(row){
		var ip = $("#ageSection").parent().find('.combo').children().eq(1);
		var comb = $(this).combobox('options');
		$(ip).click(function(){
			$('#ageSection').combo('showPanel');	
		});
		var s = '<span style="font-size: 1.9em;">' + row.text + '</span><br/>';
	    return s;
	}
	
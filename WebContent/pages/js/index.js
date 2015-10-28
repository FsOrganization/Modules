$(document).ready(function() {
	$('#tabs').height(($(window).height()-78)*0.99);
	$('#tabs').width($(window).width()*0.91);
	$(window).bind('resize', function(e) {
			clearTimeout(window.resizeEvt);
//			alert($(window).height());
			window.resizeEvt = setTimeout(function() {
				$('#tabs').height(($(window).height()-78)*0.99);
				$('#tabs').width($(window).width()*0.91);
//				$('#searchList').datagrid('resize',{
//					width : '100%',
//					height: 180
//				});
			}, 30);
	});
	var loginName = $('#loginTag').val();
	if ( loginName != 'admin') {
		$('#statistics').hide();
		$('#configSeting').hide();
//		$('#customerSeting').hide();
		
		
	}
	
	$("#modfiyPassWord").click(function(){
		$('#modfiyPassWordWindow').window('open');
	});
	
	$("#saveBtn").click(function() {
		modifyForm();
	});
	
	$("#cancelBtn").click(function(){
		$('#modfiyPassWordWindow').window('close');
	});
	
	$('#modfiyPassWordWindow').window({
		title:'修改密码',
	    width:395,
	    height:180,
	    modal:true,
	    closed:true,
	    left:380,    
	    top:160,
	    onBeforeClose:function(){
	    }
	});
	
	$("#pw").bind("keydown",function(e){
		$('#pw').css('background-color','white');
	});
	
	$("#confirmPw").bind("keydown",function(e){
		$('#confirmPw').css('background-color','white');
	});
	
	
});

function modifyForm() {
	var loginName = $('#loginName').val();
	var pw = $('#pw').val();
	var confirmPw = $('#confirmPw').val();
	if (pw === '' || confirmPw === '') {
		$.messager.show({
            title:'提示',
            msg:'<div class="messager-icon messager-info"></div>'+'密码不能为空',
            timeout:3800,
            showType:'slide'
		});
		return;
	}
	if (pw != confirmPw) {
		$('#pw').css('background-color','lemonchiffon');
		$('#confirmPw').css('background-color','lemonchiffon');
		//$("img").attr("width","180");
		$.messager.show({
            title:'提示',
            msg:'<div class="messager-icon messager-info"></div>'+'密码输入不一致',
            timeout:3800,
            showType:'slide'
		});
		return;
	}
	
	$.ajax({
		url : contextPath+"/pages/system/modifyUserPassWord.light",
		type: "POST",
		dataType:'json',
		data:
		{
			"loginName":loginName,
			"password":pw,
			"confirmPw":confirmPw
		},
		success : function(data) {
			$('#modfiyPassWordWindow').window('close');
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
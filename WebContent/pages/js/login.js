$(document).ready(function() {
//	if ($('#name').val() == '') {
//		 return;
//	}
	$("#name").bind("keydown",function(e){
		var keycode = e.which;
		//输入回车判定
		if(keycode == 13){
			if ($.trim($("#name").val()) != '') {
				$("#password").focus();
				$("#useruname-error").show();
				$("#msg").val(" ");
			} else {
				$("#useruname-error").show();
				$("#msg").val("请输入用户名");
				
			}
			e.preventDefault();						
		}
	});
	
	$("#password").bind("keydown",function(e){
		var keycode = e.which;
		//输入回车判定
		if(keycode == 13){
			if ($.trim($("#password").val()) != '') {
				$("#loginForm").submit();
				$("#useruname-error").show();
				$("#msg").val(" ");
			} else {
				$("#useruname-error").show();
				$("#msg").val("请输入密码");
			}
			e.preventDefault();
		}
	});
	
	if (msgType === "-1") {
		$("#useruname-error").show();
		$("#codeDiv").show();
		getVerificationCode();
	}
	
	$("#loginFormCommit").click(function(){
//		$("#useruname-error").hide();
//		$("#msg").val("");
		$("#loginForm").submit();
	});
	
	
	$("form").submit(function(e){
		var inputCode = $('#code').val();
		var hiddenCode = $('#hiddenCode').val();
		
		if (hiddenCode != inputCode) {
			$("#msg").html('验证码错误!');
			getVerificationCode();
			$(":text[id=code]").focus();  
			e.preventDefault();//此处阻止提交表单  
		} 
	});
	
	$("#VerificationCode").click(function(){
		getVerificationCode();
	});
	 
});

function check()
{
    var url = window.location.href;
    var par = url.substring(url.indexOf('?')+1);
    if(par == "msg-type=-1"){
        $("#useruname-error").html("<p style='color:red'>用户名或密码错误，或权限已到期！</p>");
    }
}

function getVerificationCode() {
	$.ajax({
		url : contextPath+"/pages/system/getVerificationCode.light",
		type: "POST",
		dataType:'json',
		data:{"id":"","name":""},
		success : function(data) {
			$('#hiddenCode').val(data.CODE);
			$('#VerificationCode').attr("src", 'data:image/jpg;base64,'+data.PATH);
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
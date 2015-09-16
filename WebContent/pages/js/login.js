$(document).ready(function() {
	if ($('#name').val() == '') {
		 return;
	}
	if (msgType == -1) {
		$("#useruname-error").show();
		$("#codeDiv").show();
		getVerificationCode();
	}
	
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
			$('#VerificationCode').attr('src',contextPath+data.PATH);
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
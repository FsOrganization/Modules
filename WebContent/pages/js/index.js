$(document).ready(function() {
	$('#mainBody').height(($(window).height()-85)*1);
	$('#tabs').height(($(window).height()-75)*1);
	$('#tabs').width(($(window).width()-132)*1);
	$(window).bind('resize', function(e) {
			clearTimeout(window.resizeEvt);
			window.resizeEvt = setTimeout(function() {
				$('#mainBody').height(($(window).height()-85)*1);
				$('#tabs').height(($(window).height()-75)*1);
				$('#tabs').width(($(window).width()-132)*1);
				$('#searchList').datagrid('resize',{
					width : '100%',
					height: 180
				});
			}, 30);
	});
	var loginName = $('#loginTag').val();
	var redirectTag = $('#redirectTag').val();
//	alert(redirectTag)
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
	
	$("#m-close-currentLabel").click(function() {
		var name = $("#currentName").val();
		closeTabs(name);
	});
	
	$("#m-close-all").click(function() {
		closeAllTabs();
	});
	
	$("#m-close-otherlabel").click(function() {
		var name = $("#currentName").val();
		closeTabsExcept(name);
	});
	
	$("#m-copyLabelName").click(function(){
		var name = $("#currentName").val();
		var userAgent = navigator.userAgent; // 取得浏览器的userAgent字符串
		if (userAgent.indexOf("Chrome") > -1) {
			var clip = new ZeroClipboard.Client();    
            clip.setHandCursor(true);
            var obj = $(this);
            var id = $(this).attr("id");  
            clip.glue(id);
            clip.setText(name);
            $.messager.show({
	            title:'提示',
	            msg:'<div class="messager-icon messager-info"></div>复制成功',
	            timeout:3800,
	            showType:'slide'
			});
		} else {
			window.clipboardData.setData("Text",name);
			$.messager.show({
	            title:'提示',
	            msg:'<div class="messager-icon messager-info"></div>复制成功',
	            timeout:3800,
	            showType:'slide'
			});
		}
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
	    zIndex:99999999,
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
	
	$('#tabs').tabs({
		onContextMenu: function(e, title,index){
			var _tabs_temp = document.getElementById ('tabs');
			_tabs_temp.oncontextmenu = function ()
	        {
	            return false;
	        }
			var target = this;
			$('#right_menu').menu('show', {
				hideOnUnhover:true,
	            left: e.pageX,
	            top: e.pageY
	        }).data("tabTitle", title);
			$('.menu-line').hide();
			$('#currentIndex').val(index);
			$('#currentName').val(title);
			
		  }
	});
});

var closeTabs = function(name){
	var opts = $('#tabs').tabs('options');
	var bc = opts.onBeforeClose;
	opts.onBeforeClose = function(){
	};
	$('#tabs').tabs('close',name);
	opts.onBeforeClose = bc;
};

var closeAllTabs = function(){
	$(".tabs li").each(function(i, n){
        var title = $(n).text();
        if (title === '特别说明') {
        	return true;
		} else {
			$('#tabs').tabs('close',title);    
		}
        
    });
};

var closeTabsExcept = function(name){
	$(".tabs li").each(function(i, n){
        var title = $(n).text();
        if (title === name) {
        	return true;
		} else {
			$('#tabs').tabs('close',title);    
		}
    });
};

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
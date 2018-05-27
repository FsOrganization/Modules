/**
 * 根据element id 锁屏
 */
var block = function (e ,msg){
	if (msg === "" || msg === undefined || msg === null) {
		msg = '加载中...';
	}
	$('#'+e).addClass('blockTempStyle');
	$('#'+e).block({
	    message: '<span style="font-size: 18;">'+msg+'</span>',
	    css: 
	    {
	        border: 'none',
	        padding: '15px',
	        backgroundColor: '#000',
	        '-webkit-border-radius': '10px',
	        '-moz-border-radius': '10px',
	        opacity: .5,
	        color: '#fff',
	        cursor: 'default'
	    },
	    overlayCSS: 
	    {
	    	backgroundColor: 'rgb(189, 189, 189)',
	    	height: '180%'
	    },
	    onUnblock: function(){
	    	$('#'+e).removeClass('blockTempStyle');
	    }
	});
};

var block = function (e,msg,height){
	if (msg === "" || msg === undefined || msg === null) {
		msg = '加载中...';
	}
	$('#'+e).addClass('blockTempStyle');
	$('#'+e).block({
	    message: '<span style="font-size: 18;">'+msg+'</span>',
	    css: 
	    {
	        border: 'none',
	        padding: '15px',
	        backgroundColor: '#000',
	        '-webkit-border-radius': '10px',
	        '-moz-border-radius': '10px',
	        opacity: .5,
	        color: '#fff',
	        cursor: 'default'
	    },
	    overlayCSS: 
	    {
	    	backgroundColor: 'rgb(189, 189, 189)',
	    	height: height
	    },
	    onUnblock: function(){
	    	$('#'+e).removeClass('blockTempStyle');
	    }
	});
};

/**
 * 根据element id 解锁
 */
var unblock = function(e) {
	$('#'+e).unblock();
};

/**
 * 验证手机座机,松散验证
 * @param phoneNumber
 * @returns {Boolean}
 */
function isPhoneNmuber(phoneNumber) {
	var str = phoneNumber;
	var reg = /[0]([0-9]{2,3})?[0-9]{7,8}|(^[1][3456789][0-9]{9}$)/;
	if (reg.test(str)){
		return true;
	} else {
		return false;
	}
}

var KEY = { 
	SHIFT:16, CTRL:17, ALT:18, DOWN:40, RIGHT:39, UP:38, LEFT:37
};  
var selectIndexs = {
	firstSelectRowIndex:0, lastSelectRowIndex:0
};
var inputFlags = {
	isShiftDown:false, isCtrlDown:false, isAltDown:false
}

/**
 * 响应键盘按下事件
 */
function keyPress(event,gridId) {
	var e = event || window.event;
	var code = e.keyCode | e.which | e.charCode;
	switch (code) {
	case KEY.SHIFT:
		inputFlags.isShiftDown = true;
		$('#'+gridId).datagrid('options').singleSelect = false;
		break;
	case KEY.CTRL:
		inputFlags.isCtrlDown = true;
		$('#'+gridId).datagrid('options').singleSelect = false;
		break;
	/*
	case KEY.ALT:	
	  inputFlags.isAltDown = true;
	  $('#'+gridId).datagrid('options').singleSelect = false;			
	  break;
	 */
	default:
	}
}
/**
 * 响应键盘按键放开的事件
 * @param event
 */
function keyRelease(event,gridId) { 
	var e = event || window.event;
	var code = e.keyCode | e.which | e.charCode;
	switch (code) {
	case KEY.SHIFT:
		inputFlags.isShiftDown = false;
		selectIndexs.firstSelectRowIndex = 0;
		$('#'+gridId).datagrid('options').singleSelect = true;
		break;
	case KEY.CTRL:
		inputFlags.isCtrlDown = false;
		selectIndexs.firstSelectRowIndex = 0;
		$('#'+gridId).datagrid('options').singleSelect = true;
		break;
	/*
	case KEY.ALT:	
	  inputFlags.isAltDown = false;
	  selectIndexs.firstSelectRowIndex = 0;
	  $('#'+gridId).datagrid('options').singleSelect = true;			
	  break;
	 */
	default:
	}
}
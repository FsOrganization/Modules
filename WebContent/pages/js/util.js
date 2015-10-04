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
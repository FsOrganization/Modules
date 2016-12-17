var loginName = null;
var ctx, color = "#000";
$(document).ready(function() {
	loginName = getUrlParam("loginName");
	// setup a new canvas for drawing wait for device init
    setTimeout(function(){
	   newCanvas();
    }, 1000);
	
	// prevent footer to toggle on touch
	$("[data-role=footer]").fixedtoolbar({ tapToggle: false });
	
	// reset palette selection (css) and select the clicked color for canvas strokeStyle
	$(".palette").click(function(){
		$(".palette").css("border-color", "#777");
		$(".palette").css("border-style", "solid");
		$(this).css("border-color", "#fff");
		$(this).css("border-style", "dashed");
		color = $(this).css("background-color");
		ctx.beginPath();
		ctx.strokeStyle = color;
	});
    
	// link the new button with newCanvas() function
	$("#new").click(function() {
		newCanvas();
	});
});

var ws = null;
var url = null;
var transports = [];

function setConnected(connected) {
	document.getElementById('connect').disabled = connected;
	document.getElementById('disconnect').disabled = !connected;
	document.getElementById('echo').disabled = !connected;
}

function connect() {
	if (!url) {
		alert('Select whether to use W3C WebSocket or SockJS');
		return;
	}
	
	ws = new WebSocket('ws://192.168.0.3/Express/webSocket/'+loginName);
	/* (url.indexOf('sockjs') != -1) ? 
	    new SockJS(url, undefined, {protocols_whitelist: transports}) :  */

	ws.onopen = function() {
		setConnected(true);
		log(loginName+',Info: connection opened.');
	};

	ws.onmessage = function(event) {
		alert(event+','+event.data)
		log(event.data);
	};

	ws.onclose = function(event) {
		setConnected(false);
		log('Info: connection closed.');
		log(event);
	};
}

function disconnect() {
	if (ws != null) {
		ws.close();
		ws = null;
	}
	setConnected(false);
}

function echo() {
	if (ws != null) {
		var message = $('#message').val();
		//log("${loginName}：" + message);
		ws.send(loginName +":" + message);
		if (loginName == 'wumy') {
			alert();
		}
	} else {
		alert('connection not established, please connect.');
	}
}

function updateUrl(urlPath) {
	if (urlPath.indexOf('sockjs') != -1) {
		url = urlPath;
		document.getElementById('sockJsTransportSelect').style.visibility = 'visible';
	} else {
		if (window.location.protocol == 'http:') {
			url = 'ws://' + window.location.host + urlPath;
		} else {
			url = 'wss://' + window.location.host + urlPath;
		}
		document.getElementById('sockJsTransportSelect').style.visibility = 'hidden';
	}
}
function updateTransport(transport) {
	transports = (transport == 'all') ? [] : [ transport ];
}

function log(message) {
	var console = document.getElementById('console');
	var p = document.createElement('p');
	p.style.wordWrap = 'break-word';
	p.appendChild(document.createTextNode(message));
	console.appendChild(p);
	while (console.childNodes.length > 25) {
		console.removeChild(console.firstChild);
	}
	console.scrollTop = console.scrollHeight;
}

function newCanvas(){
	//define and resize canvas
    $("#content").height($(window).height()-90);
    var canvas = '<canvas id="canvas" width="'+$(window).width()+'" height="'+($(window).height()-90)+'"></canvas>';
	$("#content").html(canvas);
    
    // setup canvas
	ctx=document.getElementById("canvas").getContext("2d");
	ctx.strokeStyle = color;
	ctx.lineWidth = 5;	
	
	// setup to trigger drawing on mouse or touch
	$("#canvas").drawTouch();
    $("#canvas").drawPointer();
	$("#canvas").drawMouse();
}

// prototype to	start drawing on touch using canvas moveTo and lineTo
$.fn.drawTouch = function() {
	var start = function(e) {
        e = e.originalEvent;
		ctx.beginPath();
		x = e.changedTouches[0].pageX;
		y = e.changedTouches[0].pageY-44;
		ctx.moveTo(x,y);
	};
	var move = function(e) {
		e.preventDefault();
        e = e.originalEvent;
		x = e.changedTouches[0].pageX;
		y = e.changedTouches[0].pageY-44;
		ctx.lineTo(x,y);
		ctx.stroke();
	};
	$(this).on("touchstart", start);
	$(this).on("touchmove", move);	
}; 
    
// prototype to	start drawing on pointer(microsoft ie) using canvas moveTo and lineTo
$.fn.drawPointer = function() {
	var start = function(e) {
        e = e.originalEvent;
		ctx.beginPath();
		x = e.pageX;
		y = e.pageY-44;
		ctx.moveTo(x,y);
	};
	var move = function(e) {
		e.preventDefault();
        e = e.originalEvent;
		x = e.pageX;
		y = e.pageY-44;
		ctx.lineTo(x,y);
		ctx.stroke();
    };
	$(this).on("MSPointerDown", start);
	$(this).on("MSPointerMove", move);
};        

// prototype to	start drawing on mouse using canvas moveTo and lineTo
$.fn.drawMouse = function() {
	var clicked = 0;
	var start = function(e) {
		clicked = 1;
		ctx.beginPath();
		x = e.pageX;
		y = e.pageY-44;
		ctx.moveTo(x,y);
	};
	var move = function(e) {
		if(clicked){
			x = e.pageX;
			y = e.pageY-44;
			ctx.lineTo(x,y);
			ctx.stroke();
		}
	};
	var stop = function(e) {
		clicked = 0;
	};
	$(this).on("mousedown", start);
	$(this).on("mousemove", move);
	$(window).on("mouseup", stop);
};
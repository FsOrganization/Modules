$(document).ready(function(){

	if (navigator.appName.indexOf("Microsoft") != -1) {
		$("#qrcode").qrcode({
			render : "table", // 设置渲染方式canvas/table
			width : 278, // 宽度
			height : 278, // 高度
			correctLevel : QRErrorCorrectLevel.H,
			text : "http://121.41.76.133/Express/pages/system/getWeixinExtractionCode.light"
		});
	} else {
		$("#qrcode").qrcode({
			render : "canvas", // 设置渲染方式canvas/table
			width : 278, // 宽度
			height : 278, // 高度
			correctLevel : QRErrorCorrectLevel.H,
			text : "http://121.41.76.133/Express/pages/system/getWeixinExtractionCode.light"
		});
	}
});
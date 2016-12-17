
/**
 * 初始化设备
 */
function initializationSignatureRegion() {
	res = obj.HWInitialize();
}

/**
 * 关闭设备
 */
function closeSignatureRegion() {
	var stream;
	stream = obj.HWFinalize();
}

/**
 * 重新签名
 */
function clearSignatureRegion() {
	obj.HWClearPenSign();
}

/**
 * 重置签字板
 */
function signRestart() {
	;
}

/**
 * 获取签名base64编码
 * @returns
 */
function saveSignature() {
	var stream;
	stream = obj.HWGetBase64Stream(2);
	return stream;
}

/**
 * 保存图片到磁盘
 */
function saveSignatureToDisk() {
	obj.HWSetFilePath("e:\\sign.jpg");
	obj.HWSaveFile();
}

/**
 * 保存图片到数据库
 */
function saveSignatureToDisk() {
	obj.HWSetFilePath("e:\\sign.jpg");
	obj.HWSaveFile();
}

/**
 * 保存签名
 */
function saveSignatureByBase64Code() {
	$.ajax({
		url : contextPath + "/pages/system/saveSignature.light",
		type : "POST",
		dataType : 'json',
		sync : false,
		success : function(data) {
			return data.temporaryId;
		}
	});
}

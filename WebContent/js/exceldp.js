$.namespace("com.fla.common");

com.fla.common.downloadExcelTemplate = function() {
	var parameters = $.extend(true, {
		ctx : "fla",
		excelMapper : "fla"
	}, arguments[0]);
	var url = parameters.ctx + "/dialog/excelUpLoad/downloadTemplate.light";
	$("<form action='" + url + "' method='get'><input type='hidden' id='mapperFileName' name='mapperFileName'" +
			" value='"+parameters.excelMapper+"'/></form>").appendTo('body').submit().remove();
}

com.fla.common.speImportExcel = function() {
	var parameters = $.extend(true, {
		ctx : "fla",
		excelMapper : "fla"
	}, arguments[0]);
	var successCallback = function(resultData) {
		$("#exceldpSelectFileModal").modal("hide");
		if (parameters.callbackFun) {
			parameters.callbackFun(resultData);
		}
	}

	var url = parameters.ctx + "/dialog/excelUpLoad/speInitImportDialog.light";
	$.get(url, parameters.queryParams, function(data) {
		$(data).appendTo('body');
		$(':file').filestyle({
			size : 'sm',
			buttonBefore : 'true',
			buttonText : '浏览'
		});
		$('#exceldpSelectFileModal #excelMapper').val(parameters.excelMapper);

		$("#exceldpSelectFileModal").on("hidden.bs.modal", function() {
			$("#exceldpSelectFileModal").remove();
		});
		
		$("#exceldpSelectFileModal").draggable({   
		    handle: ".modal-header",   
		    cursor: 'move',   
		    refreshPositions: true  
		});  

		$("#exceldpSelectFileModal form").submit(function() {
			var options = {
				type : 'post',
				dataType : 'json',	
				success : successCallback
			};
			$(this).ajaxSubmit(options);
			return false;
		});
		$("#exceldpSelectFileModal form").submit(function(){
            var options = {type:'post',dataType:'json',success:successCallback};
            $(this).ajaxSubmit(options);
            return false;
        });

        $('#exceldpSelectFileModal').modal({backdrop:'static'});
	});
}

com.fla.common.importExcel = function() {
	var parameters = $.extend(true, {
		ctx : "fla",
		excelMapper : "fla"
	}, arguments[0]);
	var successCallback = function(resultData) {
		$("#exceldpSelectFileModal").modal("hide");
		if (parameters.callbackFun) {
			parameters.callbackFun(resultData);
		}
	}

	var url = parameters.ctx + "/dialog/excelUpLoad/initImportDialog.light";
	$.get(url, parameters.queryParams, function(data) {
		$(data).appendTo('body');
		$(':file').filestyle({
			size : 'sm',
			buttonBefore : 'true',
			buttonText : '浏览'
		});
		$('#exceldpSelectFileModal #excelMapper').val(parameters.excelMapper);

		$("#exceldpSelectFileModal").on("hidden.bs.modal", function() {
			$("#exceldpSelectFileModal").remove();
		});
		
		$("#exceldpSelectFileModal").draggable({   
		    handle: ".modal-header",   
		    cursor: 'move',   
		    refreshPositions: true  
		});  

		$("#exceldpSelectFileModal form").submit(function() {
			var options = {
				type : 'post',
				dataType : 'json',	
				success : successCallback
			};
			$(this).ajaxSubmit(options);
			return false;
		});
		$("#exceldpSelectFileModal form").submit(function(){
            var options = {type:'post',dataType:'json',success:successCallback};
            $(this).ajaxSubmit(options);
            return false;
        });

        $('#exceldpSelectFileModal').modal({backdrop:'static'});
	});
}

com.fla.common.exportExcel = function() {
	var parameters = $.extend(true, {
		ctx : "fla"
	}, arguments[0]);
	//$.commonDialog.showProgress(parameters.ctx, "正在导出，请稍候...")
	var url = parameters.ctx + "/dialog/excelUpLoad/exportExcel.light";
	url += "?excelMapper=" + parameters.excelMapper;
	url += "&targetFileName=" + encodeURIComponent(parameters.targetFileName);
	if (parameters.queryParams != null) {
		$.each(parameters.queryParams, function(index, val) {
			url += "&queryParams[" + index + "].name=" + val.name;
			url += "&queryParams[" + index + "].value=" + val.value;
		});
	}
	$("<form action=" + url + " method='post'/>").appendTo('body').submit().remove();

	com.fla.common.exportListener(parameters.ctx);
}

com.fla.common.exportListener = function(ctx) {
	var url = ctx + "/dialog/excelUpLoad/exportListener.light?random="
			+ Math.random();

	$.getJSON(url, function(result) {
		if (result && result.flag && result.flag == "Y") {
			$.commonDialog.hiddenProgress();
		} else {
			setTimeout(function() {
				com.fla.common.exportListener(ctx);
			}, 500);
		}
	});
}
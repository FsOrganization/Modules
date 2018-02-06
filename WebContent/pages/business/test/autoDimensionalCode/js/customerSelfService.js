	var expressServiceMap = {};
<<<<<<< HEAD
	var tempSpeDate = '2016-03-31';
	var tempNewSpeDate = '2017-05-09';
	var lateFeeLimitUpper = null;
	var expressIdArray;
	var delayDaysArray;
	var tempOperaTimeArray;
	var orderPayStatusTag;
	var serviceCharge = null;
	var systemIsTestingTag = null;
=======
	var tempSpeDate = '2016-3-31';
	var lateFeeLimitUpper = null;
	var expressIdArray;
	var delayDaysArray;
	var orderPayStatusTag;
	var serviceCharge = 1;
>>>>>>> d0b5484a9bee2dc897836974fbc92e4f813785b1
	function queryData() {
		searchExpressInfo();
	}
	$.fn.extend({
		resizeDataGrid: function (heightMargin, widthMargin, minHeight, minWidth) {
			var height = $(document.body).height() - heightMargin;
			var width = $(document.body).width() - widthMargin;
			height = height < minHeight ? minHeight : height;
			width = width < minWidth ? minWidth : width;
			$(this).datagrid('resize', {
				height: height,
				width: width
			});
		}
	});

	$(document).ready(function() {
		lateFeeLimitUpper=$('#lateFeeLimitUpper').val();
		initExpressServiceProviders();
		// 输入框按回车
		$("#queryParams").bind("keydown", function(e) {
			var keycode = e.which;
			if (keycode == 13) {// 输入回车判定
				queryData();
				e.preventDefault();
				$("#queryParams").val("");
			}
		});
		
		$("#queryButton").bind("keydown", function(e) {
			var keycode = e.which;
			if (keycode == 13) {// 输入回车判定
				handleExpress();
				e.preventDefault();
			}
		});
		
		$('#imgDetail').window({
			title: '支付二维码',
<<<<<<< HEAD
		    width:485,
=======
		    width:475,
>>>>>>> d0b5484a9bee2dc897836974fbc92e4f813785b1
		    height:360,
		    modal:true,
		    closed: true,
		    collapsible: false,
		    minimizable:false,
		    maximizable:false,
		    resizable:false,
		    cache: false,
		    left:$(window).width()/3,
		    top:$(window).height()/3,
		    onBeforeClose: function () { //当面板关闭之前触发的事件
//                if (confirm('窗口正在关闭，请确认您当前的操作已保存。\n 是否继续关闭窗口？')) {
                	clearInterval(orderPayStatusTag);
//                    $('#imgDetail').window('close', true);
//                } else {
//                	return false;
//                }
            }
		});
<<<<<<< HEAD
		
		//get systemIsTestingPhase
		//getSystemIsTestingPhase();
		
		//get express service charge
		getExpressServiceCharge();
=======
>>>>>>> d0b5484a9bee2dc897836974fbc92e4f813785b1
	});
	
	function initExpressServiceProviders() {
		$.ajax({
			url : contextPath + "/pages/system/initExpressServiceProviders.light",
			type : "POST",
			dataType : 'json',
			sync:false,
			data : {
				"shop_code" : ""
			},
			success : function(data) {
				$.each(data, function(i) {    
					expressServiceMap[data[i].id] = data[i].text;
			     });
			},
			error : function(data) {
			}
		});
	}
	
	function searchExpressInfo(){
		$("#mainDiv").empty();
//		block("queryBody");
		block("queryBody", null,($(window).height())*1);
		var queryParams = $("#queryParams").val();
//		queryParams = encodeURI(queryParams);
		if (queryParams ==='') {
			setTimeout(function() {
				unblock("queryBody");
			},300);
			return;
		}
		$.ajax({
			url : contextPath + "/pages/system/barcode/getExpressInfoByFilter.light?&queryParams="+queryParams+"&tag="+1,
			type: "POST",
			dataType:'json',
			success : function(data){
				var mainDiv = $("#mainDiv");
				if (data.eList.length == 0) {
					var content = document.createElement('div');
					content.setAttribute("class","content");
					content.setAttribute("style","height: 50px;");
					var h1 = document.createElement('h1');
					h1.innerHTML = "<H1 style='text-align:center;font-size: 1.2em;padding: 15px;'>亲，包裹尚未到达幸福驿站！请稍后查询...</H1>";
					content.appendChild(h1);
					mainDiv.append(content);
				} else {
					var isMember = data.member;
					$.each(data.eList, function(i,val){
						var li = document.createElement('li');
						var content = document.createElement('div');
//						content.setAttribute("onclick","getCheck(this)");
						content.setAttribute("class","grid-photo-box");
						
						var imgSrc = $('<img/>');
						imgSrc.attr("src",contextPath+"/pages/business/test/autoDimensionalCode/img/exp-box.png");
						imgSrc.appendTo(content);
						
						var input = document.createElement('input');
						input.setAttribute("type","checkbox");
						input.setAttribute("name","isCheck");
						input.setAttribute("checked","true");
						
						input.setAttribute("id","isCheck"+i);
						var label = document.createElement('label');
						label.setAttribute("for","isCheck"+i);
						label.setAttribute("style","margin: 0px 15px; font-size:12px;");
						
						var inputHidden = document.createElement('input');
						inputHidden.setAttribute("type","hidden");
						inputHidden.setAttribute("id","expressId");
						inputHidden.setAttribute("value",val.id);
						
<<<<<<< HEAD
						var operaTimeHidden = document.createElement('input');
						operaTimeHidden.setAttribute("type","hidden");
						operaTimeHidden.setAttribute("id","tempOperaTime");
						operaTimeHidden.setAttribute("value",val.tempOperaTime);
=======
>>>>>>> d0b5484a9bee2dc897836974fbc92e4f813785b1
						
						var operaTime = val.operaTime;
						var delayDays = getFinalDays(operaTime,isMember);
						var delayDaysHidden = document.createElement('input');
						delayDaysHidden.setAttribute("type","hidden");
						delayDaysHidden.setAttribute("id","delayDays");
						delayDaysHidden.setAttribute("value",delayDays);
						
						var td = document.createElement('td');
						td.setAttribute("style","padding: 15px;");
						var h2 = document.createElement('h2');
						h2.setAttribute("class","style");
						h2.innerHTML = "<H1 style='text-align:center;font-size: 1.0em;color: #464d51;'>"+formatColumnTitle(val.expressServiceId)+hideLogistics(val.logistics)+","+delayDays+"</H1>";
						content.appendChild(h2);
						content.appendChild(input);
						content.appendChild(label);
						content.appendChild(inputHidden);
<<<<<<< HEAD
						content.appendChild(operaTimeHidden);
=======
>>>>>>> d0b5484a9bee2dc897836974fbc92e4f813785b1
						content.appendChild(delayDaysHidden);
						li.appendChild(content);
						mainDiv.append(li);
					 });
					$('#queryButton').focus();
				}
				 unblock("queryBody");
			},
			error : function(data){
				$.messager.show({
	                title:'错误',
	                msg:'<div class="messager-icon messager-info"></div>'+data.PATH,
	                timeout:2000,
	                showType:'slide'
			  });
				unblock("queryBody");
			}
		});
	}
	
	function hideLogistics(logistics) {
		if (logistics) {
			var tempEnd = logistics.substr(logistics.length-4,logistics.length);
			return "*"+tempEnd;
		} else {
			return "";
		}
		
	}
	
	function hideName(name) {
		if (name) {
			var temp = name.substr(0,1);
			return temp+"*";
		} else {
			return "";
		}
		
	}
	
	function initExpressServiceProviders() {
		$.ajax({
			url : contextPath + "/pages/system/initExpressServiceProviders.light",
			type : "POST",
			dataType : 'json',
			sync:false,
			data : {
				"shop_code" : ""
			},
			success : function(data) {
				$.each(data, function(i) {    
					expressServiceMap[data[i].id] = data[i].text;
			     });
			},
			error : function(data) {
			}
		});
	}
	
	function formatColumnTitle(value){
		return expressServiceMap[value];
	}
	
	var toggleTag = true;
	function checkAllSelected() {
		$("#mainDiv li").each(function() {
			var $li = $(this);
			$li.find("div").find("input[name='isCheck']").prop("checked",toggleTag);
		});
		toggleTag = !toggleTag;
	}
	
	var checkTag = true;
	function getCheck(ele) {
		var $td = $(ele);
		$td.find("input[name='isCheck']").prop("checked",checkTag);
		checkTag = !checkTag;
	}
	
	function openBarCodeWindow(barCode){
		$('#imgDetail').dialog('open');
	}
	
<<<<<<< HEAD
	function isPayment(delayDaysArray,tempOperaTimeArray){
		var paymentArr = new Array();
		var expCount = 0;
=======
	function isPayment(delayDaysArray){
>>>>>>> d0b5484a9bee2dc897836974fbc92e4f813785b1
		var money = parseInt("0");
		$.each(delayDaysArray, function(i, item){
			var itemVal =  parseInt(item);
			if (itemVal > lateFeeLimitUpper) {
				itemVal = lateFeeLimitUpper;
			}
			money += parseInt(itemVal);
<<<<<<< HEAD
			var _tempT = tempOperaTimeArray[i];
			if (checkNewSpeDate(_tempT) >= 0) {
				return;
			} else {
				++expCount;
			}
		});
		paymentArr['money'] = money; //+ parseInt(serviceCharge * expCount);
		paymentArr['expCount'] = expCount;
		paymentArr['delayMoney'] = money;
		paymentArr['expressServiceCharge'] = parseInt(serviceCharge * expCount);
		return paymentArr;
=======
		});
		return money + serviceCharge;
>>>>>>> d0b5484a9bee2dc897836974fbc92e4f813785b1
	}
	
	var LODOP; //声明为全局变量 
	var headLine = "data:image/PNG;base64,";
	function getPrinterName(iPrinterNO) {	
		LODOP=getLodop();  
		return LODOP.GET_PRINTER_NAME(iPrinterNO);	
	};
	
	function getPrintContentCode(code){
		$.ajax({
			url : contextPath + "/pages/system/barcode/getPayBase64Code.light",
			type : "POST",
			dataType : 'json',
			data : {
				"contentCode" : code,
			},
			success : function(data) {
				LODOP=getLodop();
				LODOP.PRINT_INIT("2120TF");//首先一个初始化语句
				LODOP.ADD_PRINT_TEXT(58,58,5,5,data.barBase64Code);//然后多个ADD语句及SET语句
				LODOP.ADD_PRINT_IMAGE(0,0,"100%","100%",headLine+data.barBase64Code);
				LODOP.SET_SHOW_MODE("LANDSCAPE_DEFROTATED", 1);//横向时的正向显示
//				LODOP.PREVIEW();//最后一个打印(或预览、维护、设计)语句
				LODOP.PRINT();
//				window.print(headLine+data.barBase64Code);
			},error : function(data) {
				$.messager.show({
					title : '错误',
					msg : '<div class="messager-icon messager-info"></div>'+data,
					timeout : 2000,
					showType : 'slide'
				});
			}
		});
	}
	
	function handleExpress() {
		block('gridview','请稍后...');
		expressIdArray = new Array();
		delayDaysArray = new Array();
<<<<<<< HEAD
		tempOperaTimeArray = new Array();
=======
>>>>>>> d0b5484a9bee2dc897836974fbc92e4f813785b1
		var ss = $('input[name=isCheck]').val();
		$("input[name='isCheck']").each(function() {
			if ($(this).is(':checked')) {
				$td = $(this).parent();
				var expressId = $td.find("input[id='expressId']").val();
				var delayDays = $td.find("input[id='delayDays']").val();
<<<<<<< HEAD
				var tempOperaTime = $td.find("input[id='tempOperaTime']").val();
				expressIdArray.push(expressId);
				delayDaysArray.push(delayDays);
				tempOperaTimeArray.push(tempOperaTime);
=======
				expressIdArray.push(expressId);
				delayDaysArray.push(delayDays);
>>>>>>> d0b5484a9bee2dc897836974fbc92e4f813785b1
			}
		});
		if (expressIdArray.length == 0) {
			$.messager.alert('提示','没有可选快递.','slide');
			unblock('gridview');
		} else {
<<<<<<< HEAD
			var paymentArr = isPayment(delayDaysArray,tempOperaTimeArray);
			var money = paymentArr['money'];
			var expCount = paymentArr['expCount'];
			var delayMoney = paymentArr['delayMoney'];
			var expressServiceCharge = paymentArr['expressServiceCharge'];
=======
			var money = isPayment(delayDaysArray);
>>>>>>> d0b5484a9bee2dc897836974fbc92e4f813785b1
			if (money > 0) {
				$.ajax({
					url : contextPath + "/pages/system/barcode/getPayCodeURL.light",
					type : "POST",
					dataType : 'json',
					data : {
<<<<<<< HEAD
						"name" : "快递服务费",
=======
						"name" : "快递延超期服务费",
>>>>>>> d0b5484a9bee2dc897836974fbc92e4f813785b1
						"fee" : money
					},
					success : function(data) {
						$('#orderCode').val(data.orderCode);
						$("#qrcode").empty();
						if (data.codeUrl == null || data.codeUrl === '') {
							$.messager.show({
								title : '错误',
								msg : '<div class="messager-icon messager-info"></div>'+"服务器繁忙请重试！",
								timeout : 2000,
								showType : 'slide'
							});
						} else {
							if (navigator.appName.indexOf("Microsoft") != -1) {
								$("#qrcode").qrcode({
									render : "table", // 设置渲染方式canvas/table
									width : 188, // 宽度
									height : 188, // 高度
									correctLevel : QRErrorCorrectLevel.H,
									text : data.codeUrl
								});
							} else {
								$("#qrcode").qrcode({
									render : "canvas", // 设置渲染方式canvas/table
									width : 188, // 宽度
									height : 188, // 高度
									correctLevel : QRErrorCorrectLevel.H,
									text : data.codeUrl
								});
							}
<<<<<<< HEAD
//							if (delayMoney > 0) {
//								$('#paytitle').html("您的快递服务费：￥"+expressServiceCharge+"元，延期费：￥"+delayMoney+" 元！"+"共需支付￥"+money+"元！");
//							} else {
								$('#paytitle').html("您的快递服务费：￥"+money+"元！");
//							}
=======
							$('#paytitle').html("您好！您的快递已超期，请支付超期服务费：￥"+money+" 元！！");
>>>>>>> d0b5484a9bee2dc897836974fbc92e4f813785b1
							$('#paymentType').html("请使用微信扫一扫完成支付！！");
							$('#imgDetail').dialog('open');
							orderPayStatusTag = setInterval(checkOrderPaymentStatus,2100);
							$('#queryButton').blur();
						}
						unblock('gridview');
					},error : function(data) {
						unblock('gridview');
						$.messager.show({
							title : '错误',
							msg : '<div class="messager-icon messager-info"></div>'+data,
							timeout : 2000,
							showType : 'slide'
						});
					}
				});
			} else {
				$.ajax({
					url : contextPath + "/pages/system/barcode/initBarCode.light",
					type : "POST",
					dataType : 'json',
					sync:false,
					success : function(data) {
						var barCode = data.initBarCode;
						$('#barCode').val(barCode);
						getPrintContentCode(barCode);
						saveBarcodeExpress("",barCode);
						$('#barCode').val("");
						unblock('gridview');
					},
				});
			}
			
		}
		
	}
	
	function checkOrderPaymentStatus(){
		var orderCode = $('#orderCode').val();
		$.ajax({
			url : contextPath + "/pages/system/barcode/checkOrderPaymentStatus.light?orderCode="+orderCode,
			type : "POST",
			dataType : 'json',
			sync:false,
			success : function(data) {
				if (data.payment == 'YES') {
					clearInterval(orderPayStatusTag);
					var orderCode = $("#orderCode").val();
<<<<<<< HEAD
=======
//					saveBarcodeExpress(orderCode);
>>>>>>> d0b5484a9bee2dc897836974fbc92e4f813785b1
					$.ajax({
						url : contextPath + "/pages/system/barcode/initBarCode.light",
						type : "POST",
						dataType : 'json',
						sync:false,
						success : function(data) {
							var barCode = data.initBarCode;
							$('#barCode').val(barCode);
							getPrintContentCode(barCode);
							$('#barCode').val("");
							saveBarcodeExpress(orderCode,barCode);
						},
					});
<<<<<<< HEAD
=======
					
					
>>>>>>> d0b5484a9bee2dc897836974fbc92e4f813785b1
				}
			},
			error : function(data) {
				$.messager.show({
					title : '错误',
					msg : '<div class="messager-icon messager-info"></div>'+data,
					timeout : 2000,
					showType : 'slide'
				});
			}
		});
	}
	
	function getDays(strDateStart, strDateEnd) {
		var strSeparator = "-"; // 日期分隔符
		var oDate1;
		var oDate2;
		var days;
		oDate1 = strDateStart.split(strSeparator);
		oDate2 = strDateEnd.split(strSeparator);
//		alert(strDateStart+","+strDateEnd);
		var strDateS = new Date(oDate1[0], oDate1[1] - 1, oDate1[2]);
		var strDateE = new Date(oDate2[0], oDate2[1] - 1, oDate2[2]);
		days = parseInt(Math.abs(strDateS-strDateE)/1000/60/60/24);//把相差的毫秒数转换为天数
		return days;
	}
	
	function getFinalDays(operaTime,isMember){
		var expressDate = operaTime.split(' ');
		var tag = checkSpeDate(tempSpeDate, expressDate[0]);
		var tempDate = expressDate[0];
		var msgTemp = '延期';
		var delayDays = parseInt(getDays(tempDate,getCurrDateFormat()));
<<<<<<< HEAD
		var finalDays = delayDays;
=======
		var finalDays = delayDays-2;
>>>>>>> d0b5484a9bee2dc897836974fbc92e4f813785b1
//		var isInterest = row.IS_INTEREST;
//		alert(isMember);
		if (parseInt(isMember) > 0) {
			finalDays = finalDays -1;
		}
		if (tag) {
			finalDays = 0;
		}
		if (finalDays < 0) {
			finalDays = 0;
		}
		return finalDays;

	}
	
<<<<<<< HEAD
//	function getSelectRowsDelayDays(){
//		var dataArray = new Array();
//		var selectedRows = expressIdArray;
//		if (selectedRows.length != 0){
//			var money = parseInt("0");
//			var delayDays = parseInt("0");
//			for(var i = 0; i < selectedRows.length; ++i){
//				var day = parseInt(selectedRows[i].delayDay);
//				delayDays += parseInt(day);
//				if (parseInt(day) > parseInt(lateFeeLimitUpper)) {
//					day = lateFeeLimitUpper;
//				}
//				money += parseInt(day);
//			}
//			dataArray.push(money);
//			dataArray.push(delayDays);
//		}
//		return dataArray;
//	}
=======
	function getSelectRowsDelayDays(){
		var dataArray = new Array();
		var selectedRows = expressIdArray;
		if (selectedRows.length != 0){
			var money = parseInt("0");
			var delayDays = parseInt("0");
			for(var i = 0; i < selectedRows.length; ++i){
				var day = parseInt(selectedRows[i].delayDay);
				delayDays += parseInt(day);
				if (parseInt(day) > parseInt(lateFeeLimitUpper)) {
					day = lateFeeLimitUpper;
				}
				money += parseInt(day);
			}
			dataArray.push(money);
			dataArray.push(delayDays);
		}
		return dataArray;
	}

	function batchLetExpressOutStorehouse(){
		var ids = expressIdArray.join(",");
		if (ids == '' ){
			showMsg("请选择快件...", "提示");
			return;
		} else {
			var delayDays = getSelectRowsDelayDays();
			var mTemp = delayDays[0];
			var dTemp = delayDays[1];
//			var finalDays = delayDays-2;
			if (dTemp >0) {
//				if (parseInt(delayDays) > parseInt(lateFeeLimitUpper)) {
//					$('#delayDayDiv').append('<H2 id="delayDayContent"></H2>');
//					$('#delayDayContent').empty();
//					$('#delayDayContent').append('<span style="text-decoration:underline;color:#0086D0;"> 延迟'+delayDays+"天，"+"请付￥"+lateFeeLimitUpper+"元 (封顶)。谢谢！</span>");
//				} else {
					$('#delayDayDiv').append('<H2 id="delayDayContent"></H2>');
					$('#delayDayContent').empty();
					$('#delayDayContent').append('<span style="text-decoration:underline;color:#0086D0;"> 延迟'+dTemp+"天，"+"请付￥"+mTemp+"元。谢谢！</span>");
//				}
			} else {
				var dc = document.getElementById("delayDayContent");
			    if (dc != null)
			    	dc.parentNode.removeChild(dc);
			}
			tempIds = null;
	    	tempIds = ids;
		}

	}
>>>>>>> d0b5484a9bee2dc897836974fbc92e4f813785b1
	
	function checkSpeDate(speDate,expressDate) {
		var d1 = getDateForStr(speDate);
		var d2 = getDateForStr(expressDate);
		if (expressDate == speDate) {
			return true;
		}
		var date3 = d1.getTime()-d2.getTime();  //时间差的毫秒数
		//计算出相差天数
		var days=Math.floor(date3/(24*3600*1000));
		if (days > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	function saveBarcodeExpress(orderCode,barCode){
//		block("gridview","系统正在处理...");
		$.ajax({
			url : contextPath + "/pages/system/barcode/saveBarCodeExpress.light?expressIdArrays="+expressIdArray+"&orderCode="+orderCode+"&barCode="+barCode,
			type : "POST",
			dataType : 'json',
			sync:false,
			success : function(data) {
				$('#imgDetail').dialog('close');
				$('#mainDiv').empty();
//				unblock("gridview");
			},
			error : function(data) {
//				unblock("gridview");
			}
		});
	}
	
	function initBarCode(){
		$.ajax({
			url : contextPath + "/pages/system/barcode/initBarCode.light",
			type : "POST",
			dataType : 'json',
			sync:false,
			success : function(data) {
				var barCode = data.initBarCode;
				$('#barCode').val(barCode);
<<<<<<< HEAD
			}
		});
	}
	
	function getExpressServiceCharge(){
		$.ajax({
			url : contextPath + "/pages/system/barcode/getExpressServiceCharge.light",
			type : "POST",
			dataType : 'json',
			sync:false,
			success : function(data) {
				var val = data.val;
				serviceCharge = val;
			}
		});
	}
	
	function getSystemIsTestingPhase(){
		$.ajax({
			url : contextPath + "/pages/system/barcode/getSystemIsTestingTag.light",
			type : "POST",
			dataType : 'json',
			sync:false,
			success : function(data) {
				systemIsTestingTag = data.systemIsTestingTag;
			}
		});
	}
	
	//if > 0 break
	function checkNewSpeDate(tempOperaTime) {
		var strSeparator = "-"; // 日期分隔符
		var oDate1;
		var oDate2;
		var days;
		oDate1 = tempOperaTime.split(strSeparator);
		oDate2 = tempNewSpeDate.split(strSeparator);
		var strDateS = new Date(oDate1[0], oDate1[1] - 1, oDate1[2]);
		var strDateb = new Date(oDate2[0], oDate2[1] - 1, oDate2[2]);
		days = parseInt((strDateb-strDateS)/1000/60/60/24);//把相差的毫秒数转换为天数
		return days;
	}
	
	function checkCurrentDate(tempOperaTime) {
		var strSeparator = "-"; // 日期分隔符
		var oDate1;
		var oDate2;
		var days;
		
		var currDate = new Date();
		currDate.getFullYear();
		currDate.getMonth();
		currDate.getDay();
		
		oDate1 = tempOperaTime.split(strSeparator);
		oDate2 = tempNewSpeDate.split(strSeparator);
		var strDateS = new Date(oDate1[0], oDate1[1] - 1, oDate1[2]);
		var strDateb = new Date(oDate2[0], oDate2[1] - 1, oDate2[2]);
		days = parseInt((strDateb-strDateS)/1000/60/60/24);//把相差的毫秒数转换为天数
		return days;
	}
=======
			},
		});
	}
	
>>>>>>> d0b5484a9bee2dc897836974fbc92e4f813785b1

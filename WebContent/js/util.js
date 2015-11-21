 $(document).ready(function() {
	//设置easyui-messager默认确认和取消按钮的文本显示
	$.messager.defaults={ok:"确认",cancel:"取消"};

	//单选框取消选择功能
	$(":radio").click(function(){
		var name = this.name;
		var value = this.value;
		var $parent = $(this).parent();
		//在父节点上保存key, value
		var radioValue = $parent.data(name);
		if(radioValue == undefined){
			$parent.data(name, value);
			$(this).prop("checked", true);
		}else{
			if(radioValue == value){
				$parent.removeData(name);
				$(this).prop("checked", false);
				//手动触发change事件, 保证取消过程有事件产生
				$(this).change();
			}else{
				$parent.data(name, value);
			}
		}
	}).prop("title", "点击选中的单选框进行取消选择");
});

/**
 * 接收url参数
 */
function getUrlParam(name){
	var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if (r!=null) return unescape(r[2]); return null;
}
/**
 * datagrid 分页
 * @param data
 * @returns
 */
function pagerFilter(data){  
	if (data == null) return {rows : [], originalRows: [], total:0};//防止data为空, 抛出错误
     if (typeof data.length == 'number' && typeof data.splice == 'function'){    // is array  
        data = {  
             total: data.length,  
             rows: data  
         };  
     }  
     var dg = $(this);  
     var opts = dg.datagrid('options');  
     var pager = dg.datagrid('getPager');  
     pager.pagination({  
         onSelectPage:function(pageNum, pageSize){  
             opts.pageNumber = pageNum;  
             opts.pageSize = pageSize;  
             pager.pagination('refresh',{  
                 pageNumber:pageNum,  
                 pageSize:pageSize  
             });  
             dg.datagrid('loadData',data);  
         }  
     });  
     if (!data.originalRows){  
         data.originalRows = (data.rows);  
     }  
     var start = (opts.pageNumber-1)*parseInt(opts.pageSize);  
     var end = start + parseInt(opts.pageSize); 
     //防止rows为空做出判断
     if(data.originalRows == null){
    	 data.rows = new Array;
    	 data.originalRows = new Array;
     }else{
    	 data.rows = (data.originalRows.slice(start, end));  
     }
     
     return data;  
 }  

//各个input下面的验证方式,需要验证的请在input中添加required属性
function validateUnderInputByTd(myForm){
	var tds = myForm.find("td:has(input,textarea), th:has(input,textarea)");
	var flag = true;
	
	//验证非空
	$.each(tds, function(i, td){
		var inputs = $(td).find(":enabled[name].aids-required");
		var tdFlag = true;
		$.each(inputs, function(j,input){
			var inputValue = null;
			if("radio" == input.type){
				inputValue = $.trim($(":radio:checked[name=" + input.name + "]").val());
				if(inputValue == null || inputValue == ""){
					flag = false;
					tdFlag = false;	
				}
			}else if("text" == input.type){
				inputValue = $.trim(input.value);
				if(inputValue == null || inputValue == ""){
					flag = false;
					tdFlag = false;	
				}
			}else if("textarea" == input.type){
				inputValue = $.trim(input.value);
				if(inputValue == null || inputValue == ""){
					flag = false;
					tdFlag = false;	
				}
			}
		});
		if(tdFlag == true){
			$(td).find("span.required").remove();
		}else{
			var hasWrapperLength = $(td).has(".wrap").length;
			var text = $(td).prev().html();
			if(hasWrapperLength == 0){
				$(td).wrapInner(function(){
					return "<div class='wrap'/>";
				});
				$(td).append("<span class='required' style='color:red'>请填写: " +text + "</span>");
			}else{
				var spanRequiredLength = $(td).has("span.required").length;
				if(spanRequiredLength ==0 ){
					$(td).append("<span class='required' style='color:red'>请填写: " +text + "</span>");
				}
			}
		}
	});
	
	//验证数字
	$.each(tds, function(i, td){
		var inputs = $(td).find(":enabled.aids-number");
		var tdFlag = true;
		$.each(inputs, function(j,input){
			var inputValue = null;
			if("text" == input.type){
				inputValue = $.trim(input.value);
				if(isNaN(inputValue)){
					tdFlag = false;
					flag = false;
				}			
			}
		});
		if(tdFlag == true){	
			$(td).find("span.number").remove();
		}else{
			var hasWrapperLength = $(td).has(".wrap").length;
			var text = $(td).prev().html();
			if(hasWrapperLength == 0){
				$(td).wrapInner(function(){
					return "<div class='wrap'/>";
				});
				$(td).append("<span class='number' style='color:red'>填写数字:" +text + "</span>");
			}else{
				var spanNumberLength = $(td).has("span.number").length;
				if(spanNumberLength == 0){
					$(td).append("<span class='number' style='color:red'>填写数字:" +text + "</span>");
				}
			}
		}
	});
	
	$.each(tds, function(i, td){
		var isLegal = $(td).find("span.required,span.number");
		if(isLegal.length == 0){
			var hasWrapperLength = $(td).has(".wrap").length;
			if(hasWrapperLength > 0){
				$(td).children(":first-child").children().unwrap();
			}
		}
	});
	if(flag == false){
		//msgShow("验证失败","一些关键字段未填写, 无法提交");
		$.messager.show({
	        title:'验证失败',
	        msg:'<div class="messager-icon messager-info"></div>一些关键字段验证失败, 无法提交！',
	        timeout:5000,
	        showType:'slide'
	  });
	}
	
	return flag;
}
/***
 * 引入一个js到当前js
 * (function(oWin) {loadJS({src:'test.js',charset:'utf-8'});})(window);
 */
var loadJS = function (params) {    
	var head = document.getElementsByTagName("head")[0];
	var script = document.createElement('script');
	script.charset = params.charset || document.charset || document.characterSet || 'utf-8';
	script.src = params.src;
	try {
		head.appendChild(script);
	} catch (exp) {
	}
};
/**
 *给页面所有的文本框加校验（可自行扩展判断）
 * 
 */
function addValidate(){
	var tag = document.getElementsByTagName('input');
	for (var i = 0; i < tag.length; ++i) {
		if (tag[i].type == "text") {
			$(tag[i]).addClass('easyui-validatebox');
			$(tag[i]).validatebox({    
				required: true,
				missingMessage:'此项不能为空。'
			}); 
		}
	}
}
function clearForm(objE){//清空表单
	//objE为form表单   
    $(objE).find(':input').each(
        function(){
            switch(this.type){    
               case 'passsword':    
                case 'select-multiple':    
                case 'select-one':
                case 'text': 
                	$(this).val('');
                case 'textarea':    
                    $(this).val('');    
                   break;  
                case 'hidden':    
                    $(this).val('');    
                   break;    
                case 'checkbox':    
                case 'radio':    
                    this.checked = false;    
            }    
        }       
    );    
}
/**
 * 根据json的数据自动填充form表单
 * @param form
 * @param json
 */
function autoFillin(form, json){
	clearForm(form);
	var hiddens = form.find("input[type=hidden]");
	var texts = form.find(":text");
	var radios = form.find(":radio");
	var checkboxs = form.find(":checkbox");
	var textareas = form.find("textarea[name]");
	var selects = form.find("select[name]");
	//自动填充表格内容
	$.each(hiddens, function(i, n){
		$(n).val(json[n.name]) ;
	});

	$.each(texts, function(i, n){
		$(n).val(json[n.name]) ;
	});
	$.each(radios, function(i, n){
		
		var value = json[n.name];
	
		//使用强比对, 因为js中, value 为空字符串的话, value == false 等于true
		if (value === true || value === "true"){
			value = "1";
		}else if(value === false || value === "false"){
			value = "0";
		}
		if(n.value == value){
			$(n).prop("checked", true);
		}
	});
	$.each(textareas, function(i, n){
		$(n).val(json[n.name]) ;
	});
	$.each(checkboxs, function(i, n){
		var values = json[n.name];
		try{
			if(values.length > 0){
				var array = json[n.name].split(",");
				if($.inArray(n.value, array) > -1){
					$(n).prop("checked", "checked");
				}
			}
		}catch(e){
			//alert('错误 : ' + e.message + ' 发生在: ' + e.lineNumber);
		}
	});
	$.each(selects, function(i, n){
		var value = json[n.name];
		if(value == true){
			value = "true";
		}else if(value == false){
			value = "false";
		}
		$.each($(n).find("option"), function(i,option ){
			if(option.value == value){
				$(option).prop("selected", value);
			}
		});

	});
}


/**
 * 时间对象的格式化;
 */
Date.prototype.format = function(format,now) {
    /*
     * eg:format="yyyy-MM-dd hh:mm:ss";
     */

    var d = now ? (new Date(Date.parse(now.replace(/-/g,   "/")))) : this;
    var o = {
        "M+" : d.getMonth() + 1, // month
        "d+" : d.getDate(), // day
        "h+" : d.getHours(), // hour
        "m+" : d.getMinutes(), // minute
        "s+" : d.getSeconds(), // second
        "q+" : Math.floor((d.getMonth() + 3) / 3), // quarter
         "N+" : ["星期日","星期一","星期二","星期三","星期四","星期五","星期六"][d.getDay()],
        "S" : d.getMilliseconds()// millisecond
    };

    if (/(y+)/.test(format)) {
        format = format.replace(RegExp.$1, (d.getFullYear() + "").substr(4
                        - RegExp.$1.length));
    }

    for (var k in o) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1
                            ? o[k]
                            : ("00" + o[k]).substr(("" + o[k]).length));
        }
    }
    return format;
};
/**
 * 时间对象的格式化;
 */
Date.prototype.Format = function() {
	/*
	 * eg:format="yyyy-MM-dd hh:mm:ss";
	 */
	var format = 'yyyy-MM-dd';
	var o = {
		"M+" : this.getMonth() + 1, // month
		"d+" : this.getDate(), // day
		"h+" : this.getHours(), // hour
		"m+" : this.getMinutes(), // minute
		"s+" : this.getSeconds(), // second
		"q+" : Math.floor((this.getMonth() + 3) / 3), // quarter
		"N+" : [ "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" ][this.getDay()],
		"S" : this.getMilliseconds()
	// millisecond
	};

	if (/(y+)/.test(format)) {
		format = format.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	}

	for ( var k in o) {
		if (new RegExp("(" + k + ")").test(format)) {
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
					: ("00" + o[k]).substr(("" + o[k]).length));
		}
	}
	return format;
};
/**
 * 
 * @param title
 * @param msg
 * @param position
 */
function msgShow(title,msg){
	if(null == title){
		title='提示';
	}

	$.omMessageTip.show({
		title:title,
		content:msg,
		timeout:5000
	});
	return;
}

/**
 * 数据库端datagrid删除记录
 * @param o easy-ui datagrid 中的删除按钮元素
 * @param tableId easy-ui datagrid 中的tableId
 * @param 删除记录的请求url
 * @param func 需要執行的額外函數
 * 
 */
function deleteRow(button, tableId ,url, func){
	$.messager.confirm('确认','您确认想要删除记录吗？',function(r){    
		if (r){      
			var tr = button.parentElement.parentElement.parentElement;
			var id = tr.firstChild.firstChild.innerHTML;
			$.ajax({
				url: url,
				type:"post",
				async: true,
				cache: false,
				data: {"id": id},
				success: function(msg){
					//$.omMessageTip.show({title : "message",content : msg,timeout : 3500,type : "alert"});
					$.messager.show({
				        title:'信息',
				        msg:'<div class="messager-icon messager-info"></div>' + msg,
				        timeout:5000,
				        showType:'slide'
					});
					$('#' + tableId).datagrid("reload");
					if(tableId=='antiviralDrugFormulationGrid'){
						$('#antiviralDrugUnitGrid').datagrid("reload");
					}
					if(func != undefined){
						func();
					}
				},
				error:function(error){
					alert(error);
				}
			});
			return true;
		}else{
			return false;
		}   
	});
	
}

/**
 * 浏览器端datagrid删除记录
 * @param button easy-ui datagrid 中的删除按钮元素
 * @param tableId easy-ui datagrid 中的tableId
 * 
 */
function deleteRowClientSide(button, tableId){
	$.messager.confirm('确认','您确认想要删除记录吗？',function(r){    
	    if (r){    
	    	var tr = button.parentElement.parentElement.parentElement;
			var index = $(tr).index();
			$('#' + tableId).datagrid("deleteRow",index);  
	    }    
	});
	
}

/**
 * 增加tab
 * @param title tab的名字
 * @param href tab的url链接
 * @param icon 图片
 * @param id tab的Id 
 */
function addTab(title, href, icon, id,iframeId) {
	var content;
	if(id == undefined )
	{
		id = "tabs" ;
	}
	var tt = $('#' + id);
	if(tt.length == 0)
	{
		var parentDocument = parent.document;
		tt = $(parentDocument).find('#' + id);
	}
	if (tt.tabs('exists', title)) 
	{//如果tab已经存在,则选中并刷新该tab          
		tt.tabs('select', title);
		refreshTab({
			tabTitle : title,
			url : href,
			id : id
		});
//		if(tt.tabs('tabs').length>6){
//			var msg = "当前页面打开过多，为保证您的工作流畅，请关闭暂时不需要的页面！";
//				//msgShow(null,msg);
//			$.messager.show({
//		        title:'提示',
//		        msg:msg,
//		        timeout:5000,
//		        showType:'slide'
//		  });
//		}
	} else {
		if (href) {
			if (iframeId) {
				content = '<iframe id="'+iframeId+'" scrolling="yes" frameborder="0" src="' + href + '" style="width:100%;height:100%;" ></iframe>';
			} else {
				content = '<iframe scrolling="yes" frameborder="0" src="' + href + '" style="width:100%;height:100%;" ></iframe>';
			}
		} else {
			content = '未实现';
		}
		tt.tabs('add', {
			title : title,
			closable : true,
			content : content,
			iconCls : icon || 'icon-default'
		});
		return tt.tabs("getTab", title);
	}
}
/**     
 * 刷新tab 
 * @cfg  
 *example: {tabTitle:'tabTitle',url:'refreshUrl'} 
 *如果tabTitle为空，则默认刷新当前选中的tab 
 *如果url为空，则默认以原来的url进行reload 
 */
function refreshTab(cfg) {
	var refresh_tab = cfg.tabTitle ? $('#' + cfg.id).tabs('getTab', cfg.tabTitle) : $('#' + cfg.id).tabs('getSelected');
	if (refresh_tab && refresh_tab.find('iframe').length > 0) {
		var _refresh_ifram = refresh_tab.find('iframe')[0];
		var refresh_url = cfg.url ? cfg.url : _refresh_ifram.src; 
		_refresh_ifram.contentWindow.location.href = refresh_url;
	}
}
/**
 *采用jquery easyui 添加遮罩css效果
 */
function ajaxLoading(){
	var zindex = $("#mzcz").parent().css("z-index");
	var body = $("body");
	$("<div class='datagrid-mask'></div>").css({display:"block",width:"100%",height:body.height()}).css("z-index", parseInt(zindex)+1).appendTo(body);
	$("<div class='datagrid-mask-msg'></div>").html("正在处理，请稍候......").appendTo(body)
	.css("z-index", parseInt(zindex)+1)
	.css("top", " top: 150px;")
	.css("position","fixed")
	.css({display:"block",left:($(document.body).outerWidth(true) - 190)/2});
}
/**
 * 取消遮罩效果
 */
function ajaxLoadEnd(){
	$(".datagrid-mask").remove();
	$(".datagrid-mask-msg").remove();
}
/**
 * 页面视图缓慢滚动到指定对象
 * @param o
 */
function scrollTo(o){
	if(!o || !o.offset()){
		return;
	}
	 $("html, body").animate({
			scrollTop: o.offset().top + "px"
		}, {
			duration: 1500,
			easing: "swing"
	}); 
}
/**********************************************
 * 读 Cookie 有则返回，无则返回null   
 * 
 * +prd:返回时,针对中文进行处理.
 */
function getCookie(name){   
   var cname = name + "=";   
    var dc = document.cookie;   
    if (dc.length > 0){   
        begin = dc.indexOf(cname);   
      if (begin != -1){   
            begin += cname.length;   
          end = dc.indexOf(";", begin);   
            if (end == -1){   
                end = dc.length;   
           }   
             var result= decodeURI(dc.substring(begin, end));
             return decodeURI(result);
        }   
    }
    return null;   
}   

/*****************************************
 * 写Cookie 名=值   
 * 有效时长为一年   
 * 
 * +:prd->对value进行转码处理[防止中文乱码]
 */
function writeCookie(name, value) { 
	value=encodeURI(value);
   var expire = "";    
   var hours = 365;   
   expire = new Date((new Date()).getTime() + hours * 3600000);    
   expire = ";path=/;expires=" + expire.toGMTString();    
   var uriusername = encodeURI(value);
   document.cookie = name + "=" + escape(value) + expire;
} 
 /*****************************************
  * 删除名称为name的Cookie  
  */
 function deleteCookie (name) { 
   var exp = new Date();    
    exp.setTime (exp.getTime() - 1);    
    var cval = getCookie(name);    
    document.cookie = name + "=" + cval + "; expires=" + exp.toGMTString();  
}  

/*****************************************
 * 保存用户名的例子
 * @returns {Boolean}
 */
function setCokie(){
	  var username = document.getElementById("username").value;
	  writeCookie("username",username);  
	  return true;
}
/*****************************************
 * 读取用户名的例子
 */
function displayCokie(){
	 var username = unescape(getCookie("username"));
	 if(username == "null"){
	 	document.getElementById("username").value="";
	 }else{
    	 document.getElementById("username").value= username	;	
     }
}	
//获取页面可见域高度
function getHeight(){
	var scrollHeight,offsetHeight;
	// handle IE 6
	if ($.browser.msie && $.browser.version < 7) {
		scrollHeight = Math.max(
			document.documentElement.scrollHeight,
			document.body.scrollHeight
		);
		offsetHeight = Math.max(
			document.documentElement.offsetHeight,
			document.body.offsetHeight
		);
		if (scrollHeight < offsetHeight) {
			return $(window).height();
		} else {
			return scrollHeight;
		}
	// handle "good" browsers
	} else {
		return $(document).height();
	}
}
//获取页面可见域宽度
function getWidth(){
	var scrollWidth,offsetWidth;
	// handle IE
	if ( $.browser.msie ) {
		scrollWidth = Math.max(
			document.documentElement.scrollWidth,
			document.body.scrollWidth
		);
		offsetWidth = Math.max(
			document.documentElement.offsetWidth,
			document.body.offsetWidth
		);
	
		if (scrollWidth < offsetWidth) {
			return $(window).width();
		} else {
			return scrollWidth;
		}
	// handle "good" browsers
	} else {
		return $(document).width();
	}
}
/**
 * 生成两段的随机数作为ID使用
 */
function numRand() {
	var x = 1; // 上限
	var y = 300; // 下限
	var rand1 = parseInt(Math.random() * (x - y + 1) + y);
	var rand2 = parseInt(Math.random() * (x - y + 1) + y);
	return "rand_" + rand1 + "_" + rand2;
};

//修改datagrid默认列宽度
$.extend($.fn.datagrid.methods, {  
    fixRownumber : function (jq) {  
        return jq.each(function () {  
            var panel = $(this).datagrid("getPanel");  
            var clone = $(".datagrid-cell-rownumber", panel).last().clone();  
            clone.css({  
                "position" : "absolute",  
                left : -1000  
            }).appendTo("body");  
            var width = clone.width("auto").width();  
            if (width > 25) {  
                //多加5个像素,保持一点边距  
                $(".datagrid-header-rownumber,.datagrid-cell-rownumber", panel).width(width + 5);  
                $(this).datagrid("resize");  
                //一些清理工作  
                clone.remove();  
                clone = null;  
            } else {  
                //还原成默认状态  
                $(".datagrid-header-rownumber,.datagrid-cell-rownumber", panel).removeAttr("style");  
            }  
        });  
    }  
});

//消息提示（easyui）
function showMsg(msg, title){
	if(typeof(title) == "undefined"){
		title = "消息提示";
	}
	$.messager.show({
		showType:'show',
		showSpeed:'400',
		width:'280',
		height:'100',
		timeout:3800,
		title:title,
		msg:msg,
		style:{
			right:'',
			bottom:''
		}
	});
}

/**
 * 动态设置part高度
 * @param iframe
 * @param flag 递增标示,防止无限调用
 */
var autoSetHeight = function(iframe, flag){
	flag++;//递增,防止重复调用
	var pHeights = 0;
	var body = $(iframe).contents().find("body");
	$.each(body.find("p"), function(i, n){
		pHeights += $(n).height();
	});
	if(pHeights < 300){
		pHeights = 300;
	}
	if(pHeights != 0 ){
		body.height(pHeights);
		$(iframe).height(pHeights + 10);
		$(iframe).parent().height(pHeights + 10);
	}
	if(flag < 2){
		setTimeout(function(){
			autoSetHeight(iframe, flag);
		}, 10);
	}
}

function isEmpty(v, allowBlank) {
	return v === null || v === undefined || (!allowBlank ? v === "" : false);
}


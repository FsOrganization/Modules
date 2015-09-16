<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
	<style type="text/css">
	body, html{width: 100%;height: 100%;margin:0;font-family:"微软雅黑";}
	#allmap {height: 100%;width:100%;overflow: hidden;}
	#result {width:100%;font-size:12px;}
	dl,dt,dd,ul,li{
		margin:0;
		padding:0;
		list-style:none;
	}
	dt{
		font-size:14px;
		font-family:"微软雅黑";
		font-weight:bold;
		border-bottom:1px dotted #000;
		padding:5px 0 5px 5px;
		margin:5px 0;
	}
	dd{
		padding:5px 0 0 5px;
	}
	li{
		line-height:28px;
	}
	</style>
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=KjRWsSWeGEQGOOmlRMmduX4h"></script>
	<script type="text/javascript" src="http://api.map.baidu.com/library/SearchInfoWindow/1.5/src/SearchInfoWindow_min.js"></script>
	<link rel="stylesheet" href="http://api.map.baidu.com/library/SearchInfoWindow/1.5/src/SearchInfoWindow_min.css" />
	<title>地图信息</title>
</head>
<body>
	<div id="allmap"></div>
	<script type="text/javascript">
	    var map = new BMap.Map('allmap');
	    var marker4 = getMarker('阳光花园','',106.836378,27.537438);
	    var marker2 = getMarker('华诚都汇','罗明会',106.842589,27.552516);
	    var marker1 = getMarker('东欣彩虹城','',106.903178,27.669546);
	    var marker6 = getMarker('黄金时代','袁文博、潘怡',106.840059,27.542235);
	    var marker5 = getMarker('世纪花园','任晓梅，鲁文颖',106.846657,27.561884);
	    var marker3 = getMarker('扬州恬苑','秦素素',106.834249,27.536934);
	    
		map.addOverlay(marker1); //在地图中添加marker
		map.addOverlay(marker2); //在地图中添加marker
		map.addOverlay(marker3); //在地图中添加marker
		map.addOverlay(marker4); //在地图中添加marker
		map.addOverlay(marker5); //在地图中添加marker
		map.addOverlay(marker6); //在地图中添加marker
		function getMarker(title,Contacts,lat,lng){
			var poi = new BMap.Point(lat,lng);
		    map.centerAndZoom(poi, 14);
		    map.enableScrollWheelZoom();
		    var content = '<div style="margin:0;line-height:20px;padding:2px;">' +
		                    '<img src="<%=contextPath%>/map/images/box.png" alt="" style="float:right;zoom:1;overflow:hidden;width:100px;height:100px;margin-left:3px;"/>' +
		                    '地址：幸福快递<br/>联系人：'+Contacts+'<br/>简介：幸福快递。' +
		                  '</div>';
		    //创建检索信息窗口对象
		    var searchInfoWindow = null;
			searchInfoWindow = new BMapLib.SearchInfoWindow(map, content, {
					title  : title,      //标题
					width  : 290,             //宽度
					height : 105,              //高度
					panel  : "panel",         //检索结果面板
					enableAutoPan : true,     //自动平移
					searchTypes   :[
						BMAPLIB_TAB_SEARCH,   //周边检索
						BMAPLIB_TAB_TO_HERE,  //到这里去
						BMAPLIB_TAB_FROM_HERE //从这里出发
					]
				});
			    var marker = new BMap.Marker(poi); //创建marker对象
			    marker.enableDragging(); //marker可拖拽
			    marker.addEventListener("click", function(e){
				    searchInfoWindow.open(marker);
			    })
			    return marker; //在地图中添加marker
		}
		
</script>
</body>
</html>

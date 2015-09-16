<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
	<style type="text/css">
	*{ 
		height: 100%;
	}
	</style>
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=KjRWsSWeGEQGOOmlRMmduX4h"></script>
<!-- 	<script type="text/javascript" src="http://api.map.baidu.com/library/SearchInfoWindow/1.5/src/SearchInfoWindow_min.js"></script> -->
	<script type="text/javascript" src="<%=contextPath%>/map/js/convertor.js"></script>
<!-- 	<link rel="stylesheet" href="http://api.map.baidu.com/library/SearchInfoWindow/1.5/src/SearchInfoWindow_min.css" /> -->
	<title>位置信息</title>
</head>
<body> 
     <div id="allmap" style="width:100%; height:100%"> </div> 
     </body>
     <script type="text/javascript">
     	var EARTH_RADIUS = 6378137.0; //单位M 
     	var PI = Math.PI;
	    var map = new BMap.Map("allmap");// 创建Map实例
	     map.centerAndZoom("成都",16);// 初始化地图,设置城市和地图级别。lat为纬度, lng为经度
	     map.addEventListener("click",function(e){
	         alert(getFlatternDistance(e.point.lat+0.00001,e.point.lng+0.00001,e.point.lat,e.point.lng));
	     });
         function getFlatternDistance(lat1,lng1,lat2,lng2){
             var f = getRad((lat1 + lat2)/2);
             var g = getRad((lat1 - lat2)/2);
             var l = getRad((lng1 - lng2)/2);
             
             var sg = Math.sin(g);
             var sl = Math.sin(l);
             var sf = Math.sin(f);
             
             var s,c,w,r,d,h1,h2;
             var a = EARTH_RADIUS;
             var fl = 1/298.257;
             
             sg = sg*sg;
             sl = sl*sl;
             sf = sf*sf;
             
             s = sg*(1-sl) + (1-sf)*sl;
             c = (1-sg)*(1-sl) + sf*sl;
             w = Math.atan(Math.sqrt(s/c));
             r = Math.sqrt(s*c)/w;
             d = 2*w*a;
             h1 = (3*r -1)/2/c;
             h2 = (3*r +1)/2/s;
             return d*(1 + fl*(h1*sf*(1-sg) - h2*(1-sf)*sg));
         }
         
        function getRad(d){
        	return d*PI/180.0; 
        }
     </script> 
</html>

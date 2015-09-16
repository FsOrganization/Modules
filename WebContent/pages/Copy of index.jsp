<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/base/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="content-type" content="text/html;charset=utf-8" />
	<title>lasys</title>
	<script type="text/javascript" src="<%=contextPath%>/pages/js/menu.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/pages/js/slideshow.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/pages/js/cufon-yui.js"></script>
	<script type="text/javascript" src="<%=contextPath%>/pages/js/Arial.font.js"></script>
	<script type="text/javascript">
		Cufon.replace('h1,h2,h3,h4,h5,#menu,#copy,.blog-date');
	</script>
	<script src="<%=contextPath%>/pages/js/index.js"></script>
<%-- 	<script type="text/javascript" src="<%=contextPath%>/pages/js/fancyzoom.min.js"></script> --%>
	<link rel="stylesheet" type="text/css" href="<%=contextPath %>/pages/css/main.css"/>
<%-- 	<link rel="stylesheet" type="text/css" href="<%=contextPath %>/pages/css/menu.css"/> --%>
</head>
<body>
	
	<div id="bg">
		<div class="wrap">
			
			<!-- logo -->
			<h1><a href="index.html">corporattica</a></h1>
			<!-- /logo -->
			
			<!-- menu -->
<!-- 			<div id="mainmenu"> -->
<!-- 				<ul id="menu"> -->
<!-- 					<li><a class="current" href="index.html">Home</a></li> -->
<!-- 					<li><a href="inner.html">Drop-Down Menu</a> -->
<!-- 						<ul><li><a href="inner.html">Inner Page Example</a></li><li><a href="inner.html">Inner Page Example</a></li><li><a href="inner.html">Inner Page Example</a></li></ul> -->
<!-- 					</li> -->
<!-- 					<li><a href="portfolio.html">Portfolio</a></li> -->
<!-- 					<li><a href="blog.html">Blog</a></li> -->
<!-- 					<li><a href="about.html">About Us</a></li> -->
<!-- 					<li><a href="contact.html">Contact Us</a></li> -->
<!-- 				</ul> -->
<!-- 			</div> -->
			<!-- /menu -->
			
			<!-- pitch -->
			<div id="pitch">
				<div id="slideshow">
					<!-- 1st frame -->
					<div class="active">
						<img src="<%=contextPath%>/pages/images/pitch/pitch1.jpg" alt="" />
						<div class="overlay transparent">
							<h2>Professional lasys</h2>
							<p>Aliquam erat volutpat. Donec a sem consequat tortor posuere dignissim sit amet at ipsum.</p>
						</div>
						<p class="arrow"><a href="#"></a></p>
					</div>
					<!-- /1st frame -->
					
					<!-- 2nd frame -->
					<div>
						<img src="<%=contextPath%>/pages/images/pitch/pitch2.jpg" alt="" />
						<div class="overlay transparent">
							<h2>Precise Methods</h2>
							<p>Aliquam erat volutpat. Donec a sem consequat tortor posuere dignissim sit amet at ipsum.</p>
						</div>
						<p class="arrow"><a href="#"></a></p>
					</div>
					<!-- /2nd frame -->
					
					<!-- 3rd frame -->
					<div>
						<img src="<%=contextPath%>/pages/images/pitch/pitch3.jpg" alt="" />
						<div class="overlay transparent">
							<h2>Mesurable Results</h2>
							<p>Aliquam erat volutpat. Donec a sem consequat tortor posuere dignissim sit amet at ipsum.</p>
						</div>
						<p class="arrow"><a href="#"></a></p>
					</div>
					<!-- 3rd frame -->
					
				</div>
			</div>
			<!-- /pitch -->
			
			<!-- main -->
			<div id="main">
				<div id="intro">
					<h2>Welcome</h2>
					<table id="areaCodeGrid" class="easyui-datagrid" style="height: auto;"></table>
				</div>
			
				<!-- bits -->
				<div id="bits">
					<div class="bit">
						<h4>Approach</h4>
						<div class="photo">
							<a href="javascript:void(0);" onclick='addTab("test", "<%=contextPath%>/pages/business/test/NewFile.jsp","icon-sz");'>
								<img src="<%=contextPath%>/pages/images/thumb1.png" alt="Thumb" />
							</a>
						</div>
						<p>Aliquam erat volutpat. Donec a sem consequat tortor posuere dignissim sit amet at ipsum.</p>
<!-- 						<p class="more"><a href="#">Read More</a></p> -->
<!-- 						<div id="approach"> -->
<%-- 							<img src="<%=contextPath%>/pages/images/portfolio.jpg" alt="Approach" /> --%>
<!-- 						</div> -->
					</div>
					<div class="bit">
						<h4>Methods</h4>
						<div class="photo">
							<a href="#methods"><img src="<%=contextPath%>/pages/images/thumb1.png" alt="Thumb" /></a>
						</div>
						<p>Aliquam erat volutpat. Donec a sem consequat tortor posuere dignissim sit amet at ipsum.</p>
					</div>
					<div class="bit last">
						<h4>Results</h4>
						<div class="photo">
							<a href="#results"><img src="<%=contextPath%>/pages/images/thumb1.png" alt="Thumb" /></a>
						</div>
						<p>Aliquam erat volutpat. Donec a sem consequat tortor posuere dignissim sit amet at ipsum.</p>
<!-- 						<p class="more"><a href="#">Read More</a></p> -->
<!-- 						<div id="results"> -->
<%-- 							<img src="<%=contextPath%>/pages/images/portfolio.jpg" alt="Results" /> --%>
<!-- 						</div> -->
					</div>
					<div class="clear"></div>
				</div>
				<!-- /bits -->
		
			</div>
			<!-- /main -->
			
			<!-- side -->
			<div id="side">
				<h4>Latest News</h4>
				
				<div class="news">
					<h5><a href="#">News title</a></h5>
					<p>Nunc commodo euismod massa quis vestibulum, proin mi nibh, dignissim.</p>
				</div>
				
				<div class="news">
					<h5><a href="#">News title</a></h5>
					<p>Nunc commodo euismod massa quis vestibulum, proin mi nibh, dignissim.</p>
				</div>
				
				<div class="news">
					<h5><a href="#">News title</a></h5>
					<p>Nunc commodo euismod massa quis vestibulum, proin mi nibh, dignissim.</p>
				</div>
				
				<div id="quote">
					<div data-options="region:'center'" style="padding: 0px; border: 0px;width: 100%;">
						<div id="tabs" data-options="fit:true" class="easyui-tabs" style="border: 0px;width: 100%;"></div>
					</div>
				</div>

			</div>
			<!-- /side -->
		</div>
			
		<!-- footer -->
		<div id="footer">
			<div id="footerbg">
				<div class="wrap">
				
					<!-- footer links -->
					<p id="footer_menu">
						<a href="#">Jobs @ corporattica</a>
						<a href="#">Press Releases</a>
						<a href="#">Terms and Conditions</a>
						
						<!-- credit link -->
						<a href="http://likemiddle.wix.com/lightarts" title="光艺映画摄影工作室">Solucija</a>
					</p>
					<!-- /footer links -->
					
					<p id="copy"> <span>光艺软件科技</span></p>
					
					<div class="clear"></div>
				</div>
			</div>
		</div>
		<!-- /footer -->
		
	</div>
	 <div id="detail" class="easyui-dialog" title="Complex Toolbar on Dialog" style="width:400px;height:200px;padding:10px;"
            data-options="
                iconCls: 'icon-save',
                toolbar: '#dlg-toolbar',
                buttons: '#dlg-buttons',closed: true">
       <form id="ff" method="post">
	    <div>
	        <label for="name">id:</label>
	        <input class="easyui-validatebox" type="text" id="id" data-options="required:true" />
	    </div>
	    <div>
	        <label for="email">Name:</label>
	        <input class="easyui-validatebox" type="text" id="name" data-options="required:true" />
	    </div>
	</form>
    </div>
    <div id="dlg-toolbar" style="padding:2px 0">
        <table cellpadding="0" cellspacing="0" style="width:100%">
            <tr>
                <td style="padding-left:2px">
                    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true">Edit</a>
                    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-help',plain:true">Help</a>
                </td>
                <td style="text-align:right;padding-right:2px">
                    <input class="easyui-searchbox" data-options="prompt:'Please input somthing'" style="width:150px"></input>
                </td>
            </tr>
        </table>
    </div>
    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:alert('save')">Save</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#detail').dialog('close')">Close</a>
    </div>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/base/base.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang='zh'>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

<link rel="stylesheet" type="text/css" href="<%=contextPath%>/pages/E-commerce/css/shop.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/form-style/css/demo.css" />
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/common/form-style/css/style3.css" />
<script type="text/javascript" src="<%=contextPath%>/common/form-style/js/modernizr.custom.04022.js"></script>
<script type="text/javascript" src="<%=contextPath%>/pages/E-commerce/temp/jquery.qrcode.js"></script>
<script type="text/javascript" src="<%=contextPath%>/pages/E-commerce/temp/qrcode.js"></script>
<script type="text/javascript" src="<%=contextPath%>/pages/E-commerce/temp/canvasjs.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/pages/E-commerce/temp/jquery.canvasjs.min.js"></script>
<script src="<%=contextPath%>/pages/E-commerce/js/itemQRCode.js" type="text/javascript"></script>
<style type="text/css">
/* latin-ext */
@font-face {
  font-family: 'Lato';
  font-style: normal;
  font-weight: 400;
  src: local('Lato Regular'), local('Lato-Regular'), url(http://fonts.gstatic.com/s/lato/v11/UyBMtLsHKBKXelqf4x7VRQ.woff2) format('woff2');
  unicode-range: U+0100-024F, U+1E00-1EFF, U+20A0-20AB, U+20AD-20CF, U+2C60-2C7F, U+A720-A7FF;
}
/* latin */
@font-face {
  font-family: 'Lato';
  font-style: normal;
  font-weight: 400;
  src: local('Lato Regular'), local('Lato-Regular'), url(http://fonts.gstatic.com/s/lato/v11/1YwB1sO8YE1Lyjf12WNiUA.woff2) format('woff2');
  unicode-range: U+0000-00FF, U+0131, U+0152-0153, U+02C6, U+02DA, U+02DC, U+2000-206F, U+2074, U+20AC, U+2212, U+2215, U+E0FF, U+EFFD, U+F000;
}
/* latin-ext */
@font-face {
  font-family: 'Lato';
  font-style: normal;
  font-weight: 700;
  src: local('Lato Bold'), local('Lato-Bold'), url(http://fonts.gstatic.com/s/lato/v11/ObQr5XYcoH0WBoUxiaYK3_Y6323mHUZFJMgTvxaG2iE.woff2) format('woff2');
  unicode-range: U+0100-024F, U+1E00-1EFF, U+20A0-20AB, U+20AD-20CF, U+2C60-2C7F, U+A720-A7FF;
}
/* latin */
@font-face {
  font-family: 'Lato';
  font-style: normal;
  font-weight: 700;
  src: local('Lato Bold'), local('Lato-Bold'), url(http://fonts.gstatic.com/s/lato/v11/H2DMvhDLycM56KNuAtbJYA.woff2) format('woff2');
  unicode-range: U+0000-00FF, U+0131, U+0152-0153, U+02C6, U+02DA, U+02DC, U+2000-206F, U+2074, U+20AC, U+2212, U+2215, U+E0FF, U+EFFD, U+F000;
}
.wrapper {
  border-top: 0.5em solid #fff;
  border-bottom: 0.25em solid #bdc3c7 !important;
  background: #fff;
  box-shadow: 0 0 0 0.5em rgba(189, 195, 199, 0);
  -moz-transition: box-shadow 0.15s linear;
  -o-transition: box-shadow 0.15s linear;
  -webkit-transition: box-shadow 0.15s linear;
  transition: box-shadow 0.15s linear;
  /*
   * alpha
   */
  /*
   * beta
   */
  /*
   * gamma
   */
  /*
   * delta
   */
}
.wrapper:hover {
  box-shadow: 0 0 0 0.5em rgba(189, 195, 199, 0.6);
}
.wrapper h2 {
  font-size: 2em;
  font-weight: 700;
  margin: 0 0 0.5em 0;
}
.wrapper h3 {
  font-weight: 400;
  margin: 0;
  font-size: 1.2em;
  line-height: 1.2;
}
.wrapper address {
  font-style: normal;
  margin-bottom: 0.5em;
}
.wrapper ul {
  margin: 0;
}
.wrapper li {
  margin-bottom: 0.5em;
  border-bottom: .25em solid #fff;
  padding-bottom: 0.5em;
}
.wrapper li:last-child {
  border: none;
  padding-bottom: 0;
  margin-bottom: 0;
}
.wrapper label {
  cursor: pointer;
}
.wrapper input {
  width: 1.15em;
  height: 1.15em;
  background: #fff;
  margin-right: .25em;
  cursor: pointer;
  box-shadow: inset 0 0 0 .25em #fff;
  vertical-align: text-bottom;
  -moz-appearance: none;
  -webkit-appearance: none;
  -moz-transition: background 0.15s linear;
  -o-transition: background 0.15s linear;
  -webkit-transition: background 0.15s linear;
  transition: background 0.15s linear;
}
.wrapper input[type="number"] {
  width: 1.75em;
  height: 60%;
  border: none;
  padding: 0 0.25em;
  text-align: center;
  font-size: 2em;
  position: relative;
}
.wrapper input[type="number"]:focus {
  outline: none;
  background: #ecf0f1;
}
.wrapper input[type="number"]::-webkit-inner-spin-button, .wrapper input[type="number"]::-webkit-outer-spin-button {
  -webkit-appearance: none;
  cursor: pointer;
  width: .5em;
  height: 100%;
  text-align: center;
  color: #bdc3c7;
}
.wrapper input[type="number"]::-webkit-inner-spin-button:before, .wrapper input[type="number"]::-webkit-outer-spin-button:before {
  position: absolute;
  left: 0;
  top: 0;
  content: '\25B4';
}
.wrapper input[type="number"]::-webkit-inner-spin-button:after, .wrapper input[type="number"]::-webkit-outer-spin-button:after {
  position: absolute;
  left: 0;
  bottom: 0;
  content: '\25BE';
}
.wrapper input:checked {
  background: #ccc;
}
.wrapper img {
  max-width: 100%;
  max-height: 8em;
  border: .25em solid #fff;
}
.wrapper table {
  width: 100%;
  border-spacing: 0 0.25em;
  border-collapse: separate;
  margin-top: -0.25em;
}
.wrapper th {
  text-align: right;
  padding-right: 1em;
}
.wrapper thead {
  background: #e74c3c;
}
.wrapper thead th {
  text-align: left;
  padding: 0.5em;
  color: #fff;
}
.wrapper thead th:last-child {
  text-align: right;
}
.wrapper tr.alpha {
  background: #e74c3c;
  color: #fff;
}
.wrapper tr.alpha > th {
  padding: 0.5em 1em 0.5em 0.5em;
}
.wrapper hr {
  border: none;
  border-bottom: 0.25em solid #fff;
}
.wrapper button {
  border: none;
  padding: 0.5em;
  background: #ecf0f1;
  -moz-appearance: none;
  -webkit-appearance: none;
  -moz-transition: background 0.15s linear;
  -o-transition: background 0.15s linear;
  -webkit-transition: background 0.15s linear;
  transition: background 0.15s linear;
}
.wrapper button:hover {
  background: #bdc3c7;
}
.wrapper button:before {
  content: attr(data-icon);
  padding-right: 0.5em;
}
.wrapper button.alpha:before {
  content: "x";
  color: #e74c3c;
}
.wrapper button.beta {
  background: #1abc9c;
  color: #fff;
  font-weight: 700;
  padding: 1em;
  margin-top: 0.25em;
  font-size: 1.25em;
}
.wrapper button.beta:hover {
  background: #16a085;
}
.wrapper button.beta:before {
  content: '\25B6';
}
.wrapper button.gamma:before {
  content: '\25AA';
  color: #bdc3c7;
}
.wrapper button.gamma:hover:before {
  color: #ecf0f1;
}
.wrapper .text--right {
  text-align: right;
}
.wrapper .helper--alpha {
  padding-left: 1.5em;
}
.wrapper .wrapper--box {
  color: #fff;
  padding: 0.5em;
  list-style-type: none;
}
.wrapper.wrapper__alpha {
  border-color: #27ae60;
}
.wrapper.wrapper__alpha .wrapper--box {
  background: #27ae60;
}
.wrapper.wrapper__alpha h2 {
  color: #2ecc71;
}
.wrapper.wrapper__beta {
  border-color: #2980b9;
}
.wrapper.wrapper__beta .wrapper--box {
  background: #2980b9;
}
.wrapper.wrapper__beta h2 {
  color: #3498db;
}
.wrapper.wrapper__beta li {
  border-color: #3498db;
}
.wrapper.wrapper__beta input:checked {
  background: #3498db;
}
.wrapper.wrapper__gamma {
  border-color: #c0392b;
}
.wrapper.wrapper__gamma .wrapper--box {
  border-top: 0.5em solid #c0392b;
  color: #000;
}
.wrapper.wrapper__gamma h2,
.wrapper.wrapper__gamma h3 {
  color: black;
}
.wrapper.wrapper__gamma hr {
  border-color: #e74c3c;
}
.wrapper.wrapper__delta {
  border-color: #9b59b6;
}
.wrapper.wrapper__delta .wrapper--box {
  background: #8e44ad;
}
.wrapper.wrapper__delta h2 {
  color: #9b59b6;
}
.wrapper.wrapper__delta li {
  border-color: #9b59b6;
}
.wrapper.wrapper__delta input:checked {
  background: #9b59b6;
}
</style>
<script type="text/javascript">
</script>
</head>
<body style="overflow-y: hidden;background: #f2f2f2;margin: 5px 0px;">
	<table id="expressServiceProviderGrid" style="height: auto;"></table>
	<div id="addMerchandise" class="container"
		style="min-height: 300px; overflow: hidden;">
		<section class="af-wrapper">
		<h1></h1>
		<label for="af-showreq" class="af-show" style="margin: -15px 40px;">*突出必填项</label>
		<input id="af-showreq" class="af-show-input" type="checkbox"
			name="showreq" /> <input type="hidden" name="merchandiseId" id="merchandiseId">
		<form class="af-form" id="af-form" novalidate
			style="margin: 16px -15px;">
			<div class="af-outer af-required">
				<div class="af-inner">
					<label for="name">商品名称:</label> <input type="text" name="name" id="name" style="width: 279px;"> <span style="color: red;">
						(*必填项)</span>
				</div>
			</div>
			<div class="af-outer" style="display: none;">
				<div class="af-inner">
					<label for="inventory">数量:</label> <input type="text" name="inventory" id="inventory" style="width: 279px;" value="null"/> 
						<span style="color: red;"> (*必填项)</span>
				</div>
			</div>
			<div class="af-outer af-required">
				<div class="af-inner">
					<label for="unitPrice">单价:</label> <input type="text" name="unitPrice" id="unitPrice" style="width: 279px;" /> <span
						style="color: red;"> (*必填项)</span>
				</div>
			</div>
			<div class="af-outer af-required">
				<div class="af-inner">
					<label for="productionDate">生产日期:</label> <input type="text" name="productionDate" id="productionDate" style="width: 279px;" />
					<span style="color: red;"> (*必填项 yyyy-mm-dd)</span>
				</div>
			</div>
			<div class="af-outer" style="display: none;">
				<div class="af-inner">
					<label for="validity">有效期:</label> <input type="text" name="validity" id="validity" style="width: 279px;" value="null"/> 
					<span style="color: red;"> (*必填项)</span>
				</div>
			</div>
			<div class="af-outer">
				<div class="af-inner">
					<label for="description">商品描述 :</label> <input type="text" name="description" id="description" style="width: 279px;" />
				</div>
			</div>
			<div class="af-outer">
				<div class="af-inner">
					<label for="remark">备注 :</label> <input type="text" name="remark" id="remark" style="width: 279px;" />
				</div>
			</div>
		</form>
		<div style="text-align: center;">
			<input id="saveBtn" type="button" value="保存"
				style="height: 30px; width: 49px;" /> <input id="cancelBtn"
				type="button" value="关闭" style="height: 30px; width: 49px;" />
		</div>
		</section>
	</div>
	<div id="expressServiceProviderContacts" class="container" style="min-height: 300px; overflow: hidden;">
		<div class="pure-g-r" style="text-align: center;display: none;" id="carContainer" >
			<div class="pure-u-1-2">
				<div class="wrapper wrapper__gamma" style="height: 300px;">
					<table>
						<tr>
							<td style="width: 280px;">
								<div id="qrcode"
									style="margin: 2px 2px; float: left; position: relative;"></div>
							</td>
							<td>
								<table style="margin: 0px -1px; padding: 0px 1px;">
									<thead>
										<tr>
											<th style="text-align: center;">商品信息</th>
											<th style="text-align: center;">数量</th>
											<th style="text-align: center;">单价</th>
										</tr>
									</thead>
									<tr class="wrapper--box" style="height: 190px;">
										<td>
											<div class="pure-g">
												<div class="pure-u-1-4">
<!-- 													<img src='./images/banana.jpg'> -->
												</div>
												<div class="pure-u-3-4">
													<div class="helper--alpha">
														<h3 id="title" style="margin: 45px 0px;font-size: 16px;"></h3>
<!-- 														<p id="desc" style="color: #6381D4;margin: 20px -90px;text-align: center;font-size: 12px;"></p> -->
<!-- 														<button class="alpha" id="reloadQRcode">重置收款二维码</button> -->
													</div>
												</div>
										</td>
										<td style="text-align: center;"><input type="number" id="number" onblur="calculatedPaymentAmount(this.value)" style="width: 100px;font-size: 16px;padding: inherit;" placeholder="数量" /></td>
										<td><h3 id="money" style="text-align:center;font-size: 16px;"></h3></td>
									</tr>
									<tr class="alpha">
										<th colspan='2'>支付金额：</th>
										<td id="payMoney"></td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</div>
	<div id="signatureRegion" style="overflow: hidden;">
	</div>
</body>
</html>
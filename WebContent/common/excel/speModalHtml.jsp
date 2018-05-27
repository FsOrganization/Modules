<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- <%@ include file="/base/base.jsp" %> --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
	String contextPath = request.getContextPath();
%>
<link rel="stylesheet" type="text/css" href="<%=contextPath%>/css/bootstrap.min.css" />
<script type="text/javascript" src="<%=contextPath%>/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/js/bootstrap-filestyle.min.js"></script>

<div class="modal fade" id="exceldpSelectFileModal" role="dialog" aria-hidden="true">
    <form id="fla" enctype="multipart/form-data" action="<%=contextPath%>/dialog/excelUpLoad/importExcelHandle.light">
        <div class="modal-dialog" style="width: 29%;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">
                        <span aria-hidden="true">&times;</span>
                        <span class="sr-only">Close</span>
                    </button>
                    <h4 class="modal-title">Excel文件导入</h4>
                </div>
                <div class="modal-body">
                    <input type="hidden" name="type"/>
                    <input type="hidden" id="excelMapper" name="excelMapper">
                    <input type="file" name="excelfile">
                </div>
                <div class="modal-footer">
                    <button id="exceldpSelectFileModal_importButton" type="submit" class="btn btn-default">导入</button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                </div>
            </div>
        </div>
    </form>
</div>
<script>
$(document).ready(function(){
});
</script>


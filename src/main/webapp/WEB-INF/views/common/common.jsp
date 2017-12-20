<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%
	String path = request.getContextPath();
	request.setAttribute("path",request.getContextPath());
%>

<meta charset="utf-8">
<meta name="format-detection" content="telephone=no" />
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<link rel="stylesheet" type="text/css" href="<%=path %>/css/default/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=path %>/css/icon.css">
<link rel="stylesheet" type="text/css" href="<%=path %>/css/custom.css">

<script type="text/javascript" src="<%=path %>/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=path %>/js/jquery.easyui.min.js"></script>

<script type="text/javascript" src="<%=path %>/js/common.js"></script>
<script type="text/javascript" src="<%=path %>/js/navbar.js"></script>
<c:set var="imgbasepath" value="${path}/img/" scope="session"/>
<script type="text/javascript">
	var contextPath = '<%=path%>';
</script>
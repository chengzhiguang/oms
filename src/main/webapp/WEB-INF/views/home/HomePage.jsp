<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%
    String path = request.getContextPath();
    request.setAttribute("path",request.getContextPath());
%>

<!DOCTYPE html>
<html>
<head>
    <title>订单管理</title>
    <c:import url="../common/common.jsp"></c:import>
</head>

<body class="easyui-layout">
<c:import url="../common/footer.jsp"></c:import>
<c:import url="../common/header.jsp"></c:import>
<c:import url="../common/navbar.jsp"></c:import>
<div data-options="region:'center'" >
    <div id="mainTabs" class="easyui-tabs" data-options="fit:true" style="width: 80px">
    </div>
</div>
</body>

</html>
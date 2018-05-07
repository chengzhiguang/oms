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

	function checknum(obj){


		$(obj).val($(obj).val().replace(/[^-0-9.]/g,''));
		$(obj).val($(obj).val().replace('..','.')); //需要优化,如果按着.点键不放开,是有bug的
		var pay_val = obj.value;
		if(pay_val.indexOf(".")>=0) {
			var _pay = pay_val.split(".")[1];
			var _pay_len = _pay.length;

			if (_pay_len > 2) {
				obj.value = pay_val.substring(0, pay_val.length-1);
			} else {
				obj.value = pay_val;
			}
		}
	}

	function checkintnum(obj){


		$(obj).val($(obj).val().replace(/[^-0-9.]/g,''));
		$(obj).val($(obj).val().replace('.','')); //需要优化,如果按着.点键不放开,是有bug的
		var pay_val = obj.value;
		if(pay_val.indexOf(".")>=0) {
			var _pay = pay_val.split(".")[1];
			var _pay_len = _pay.length;

			if (_pay_len > 2) {
				obj.value = pay_val.substring(0, pay_val.length-1);
			} else {
				obj.value = pay_val;
			}
		}
	}
</script>
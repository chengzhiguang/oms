<%@ page language="java" pageEncoding="UTF-8"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%
	String path = request.getContextPath();
%>
<div id="123" data-options="region:'north'" style="height:80px;background:url('${imgbasepath}/backimage.jpg')">
<h2 style="padding-left: 20px;"><font color="white" size="6">订单管理</font></h2>
<div style="position: absolute; right: 20px; bottom: 0px; ">
	<c:if test="${userInfo != null}">
		<div ><font color="white" size="4">你好，${userInfo.userName}</font></div>
	</c:if>
	<div class="panel-body collapse in fixed-height-panel-pre" id="consoleCollapse">
		<pre id="consolePre" class="pre-scrollable"></pre>
	</div>
	<div class="panel-heading">
		<span id="connectionStatus">Connection - Disconnected.</span>
	</div>
	<div  class="easyui-linkbutton" data-options="plain:true"  iconCls="icon-back" onclick="logout();"><font color="white" size="3">退出系统</font></div>
	<div  class="easyui-linkbutton" data-options="plain:true"  iconCls="icon-back" onclick="connectionToggle();"><font color="white" size="3">mqtt</font></div>
	<div  class="easyui-linkbutton" data-options="plain:true"  iconCls="icon-back" onclick="subscribe();"><font color="white" size="3">topic</font></div>

</div>

</div>

<script type="text/javascript" charset="UTF-8">
	function logout(b) {

        window.location.href = '<%=path%>' + '/page/home/logout';
		
	}

	
</script>

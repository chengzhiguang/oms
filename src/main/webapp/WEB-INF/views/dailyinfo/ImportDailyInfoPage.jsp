<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
    String path = request.getContextPath();
    request.setAttribute("path",request.getContextPath());
%>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8">
    <title>新增物品信息</title>
    <c:import url="../common/common.jsp"></c:import>
    <script type="text/javascript" src="<%=path %>/js/jquery.form.js"></script>

    <style>
    </style>
</head>
<body>
<div style="padding:80px 60px;height: 260px;">

    <form  id="import_form" method="post" enctype="multipart/form-data">
        <table class="tableForm"  cellspacing="8">
            <tr  align="right">
                <th align="right">
                    <b id="fileB" style="color: red;width: 4px;float: left;">*</b>附件：
                </th>
                <td>
                    <input class="easyui-filebox" id="exportPayvouchers" name="file"  style="width:300px; height: 26px;"
                           data-options="prompt:'请选择导入的文件',buttonText: '选择文件', buttonAlign:'right'">
                </td>
            </tr>
            <tr>
                <th align="right">

                </th>
                <td><a style="text-align:center;float: left;width: 100px;" href="javascript:void(0)" class="easyui-linkbutton" onclick="JavaScript:submit();">确认提交</a></td>
            </tr>
        </table>
    </form>
</div>
</body>
<script type="text/javascript">
    function submit() {
        $("#import_form").ajaxSubmit({
            type:"post",
            async : false,
            cache : false,
            url: contextPath+'/page/dailyinfo/importDailyInfo',
            success:function (data) {
                jsonData = data;
                var status=jsonData.status;
                if(status=='success'){
                    $.messager.alert('信息提示：','已成功');
                    parent.location = contextPath + "/page/dailyinfo/toDailyManagerPage";

                }else{
                    $.messager.alert('信息提示：',jsonData.message,'info');
                }

            }
        });

    }
</script>
</html>


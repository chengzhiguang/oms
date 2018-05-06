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
    <title>修改物品信息</title>
    <c:import url="../common/common.jsp"></c:import>

    <style>
    </style>
</head>
<body>
<div style="padding:80px 60px;height: 260px;">

    <table class="tableForm"  cellspacing="8">
        <tr  align="right">
            <th>SPU编号：</th>
            <td><input id="spuCode" name="spuCode" class="easyui-textbox" style="width:200px;height:30px" value="${spuInfo.spuCode}" readonly="readonly"></td>
        </tr>
        <tr  align="right">
            <th>商品SPU名称：</th>
            <td><input id="spuName" name="spuName" class="easyui-textbox" style="width:200px;height:30px" value="${spuInfo.spuName}"></td>
        </tr>
        <tr  align="right">
            <th>成本价（斤）：</th>
            <td><input id="spuCost" name="spuCost" style="width:200px;height:30px" value="${spuInfo.spuCost}" onkeyup="javascript:checknum(this);"></td>
        </tr>
        <tr>
            <th></th>
            <td><a style="text-align:center;float: left;width: 100px;" href="javascript:void(0)" class="easyui-linkbutton" onclick="JavaScript:submit();">确认提交</a></td>
        </tr>
    </table>
</div>
</body>
<script type="text/javascript">
    function submit() {
        var check = confirm("确定提交商品SPU信息？");
        if(!check) {
            return;
        }

        var spuCode = $("#spuCode").val();
        var spuName = $("#spuName").val();
        var spuCost = $("#spuCost").val();

        var url = contextPath + "/page/goods/saveSpuInfo";
        $.ajax({
            type:"POST",
            url:url,
            data:{
                "spuCode":spuCode,
                "spuName":spuName,
                "spuCost":spuCost,
            },
            dataType:"json",
            success:function(data) {
                if(data.status == 'success') {
                    parent.location = contextPath + "/page/goods/toSpuManagerPage";
                } else {
                    alert("提交失败，" + data.message);
                }

            },
            error:function(result) {
                alert("提交异常");
            }
        });

    }
</script>
</html>


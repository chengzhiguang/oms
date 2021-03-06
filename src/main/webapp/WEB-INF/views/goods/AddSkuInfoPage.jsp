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

    <style>
    </style>
</head>
<body>
<div style="padding:80px 60px;height: 260px;">

    <table class="tableForm"  cellspacing="8">
        <tr  align="right">
            <th>商品spu：</th>
            <td>
                <select id="spuCode" name="spuCode" class="easyui-combobox" style="width:200px;height:30px">
                    <c:forEach items="${spuList}" var="item">
                        <option  value="${item.spuCode }">${item.spuName}</option>
                    </c:forEach>
                </select>
            </td>
        </tr>
        <tr  align="right">
            <th>商铺：</th>
            <td>
                <select id="storeCode" name="storeCode" class="easyui-combobox" style="width:200px;height:30px">
                    <c:forEach items="${storeList}" var="item">
                        <option  value="${item.storeCode }">${item.storeName}</option>
                    </c:forEach>
                </select>
            </td>
        </tr>
        <tr  align="right">
            <th>商品SKUID：</th>
            <td><input id="skuCode" name="skuCode" class="easyui-textbox" style="width:200px;height:30px" value=""></td>
        </tr>
        <tr  align="right">
            <th>商品SKU名称：</th>
            <td><input id="skuName" name="skuName" class="easyui-textbox" style="width:200px;height:30px" value=""></td>
        </tr>
        <tr  align="right">
            <th>规格（克）：</th>
            <td><input id="skuWeight" name="skuWeight" style="width:200px;height:30px" value="" onkeyup="javascript:checkintnum(this);"></td>
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
        var check = confirm("确定提交SKU信息？");
        if(!check) {
            return;
        }
        var storeCode = $("#storeCode").combobox('getValue');
        var spuCode = $("#spuCode").combobox('getValue');
        var skuCode = $("#skuCode").val();
        var skuName = $("#skuName").val();
        var skuWeight = $("#skuWeight").val();

        var url = contextPath + "/page/goods/saveSkuInfo";
        $.ajax({
            type:"POST",
            url:url,
            data:{
                "storeCode":storeCode,
                "spuCode":spuCode,
                "skuCode":skuCode,
                "skuName":skuName,
                "skuWeight":skuWeight,
            },
            dataType:"json",
            success:function(data) {
                if(data.status == 'success') {
                    parent.location = contextPath + "/page/goods/toSkuManagerPage";
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


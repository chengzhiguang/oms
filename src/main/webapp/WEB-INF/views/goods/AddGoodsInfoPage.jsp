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
            <th>商品编号：</th>
            <td><input id="goodsCode" name="goodsCode" class="easyui-textbox" style="width:200px;height:30px" value=""></td>
        </tr>
        <tr  align="right">
            <th>商品名称：</th>
            <td><input id="goodsName" name="goodsName" class="easyui-textbox" style="width:200px;height:30px" value=""></td>
        </tr>
        <tr  align="right">
            <th>京东价(分)：</th>
            <td><input id="jdPrice" name="jdPrice" class="easyui-textbox" style="width:200px;height:30px" value=""></td>
        </tr>
        <tr  align="right">
            <th>市场价(分)：</th>
            <td><input id="marketPrice" name="marketPrice" class="easyui-textbox" style="width:200px;height:30px" value=""></td>
        </tr>
        <tr  align="right">
            <th>折扣（%）：</th>
            <td><input id="discount" name="discount" class="easyui-textbox" style="width:200px;height:30px" value=""></td>
        </tr>
        <tr  align="right">
            <th>商品毛重（g）：</th>
            <td><input id="goodsWeight" name="goodsWeight" class="easyui-textbox" style="width:200px;height:30px" value=""></td>
        </tr>
        <tr  align="right">
            <th>年营销成本(分)：</th>
            <td><input id="marketingCosts" name="marketingCosts" class="easyui-textbox" style="width:200px;height:30px" value=""></td>
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
        var check = confirm("确定提交商品信息？");
        if(!check) {
            return;
        }

        var goodsCode = $("#goodsCode").val();
        var goodsName = $("#goodsName").val();
        var jdPrice = $("#jdPrice").val();
        var marketPrice = $("#marketPrice").val();
        var discount = $("#discount").val();
        var goodsWeight = $("#goodsWeight").val();
        var marketingCosts = $("#marketingCosts").val();

        var url = contextPath + "/page/goods/saveGoodsInfo";
        $.ajax({
            type:"POST",
            url:url,
            data:{
                "goodsCode":goodsCode,
                "goodsName":goodsName,
                "jdPrice":jdPrice,
                "marketPrice":marketPrice,
                "goodsWeight":goodsWeight,
                "discount":discount,
                "marketingCosts":marketingCosts,
            },
            dataType:"json",
            success:function(data) {
                if(data.status == 'success') {
                    parent.location = contextPath + "/page/goods/toGoodsManagerPage";
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


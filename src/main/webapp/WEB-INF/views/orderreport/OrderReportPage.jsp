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
            <th>年份：</th>
            <th>
                <select id="orderYear" name="orderYear" class="easyui-combobox" style="width:120px;height:30px">
                    <option value="2014">2014年</option>
                    <option value="2015">2015年</option>
                    <option value="2016">2016年</option>
                    <option value="2017" selected="selected">2017年</option>
                    <option value="2018">2018年</option>
                    <option value="2019">2019年</option>
                    <option value="2020">2020年</option>
                </select>
            </th>
        </tr>
        <tr>
            <th></th>
            <td><a style="text-align:center;float: left;width: 100px;" href="javascript:void(0)" class="easyui-linkbutton" onclick="JavaScript:submit();">生成报表</a></td>
        </tr>
    </table>
</div>
</body>
<script type="text/javascript">
    function submit() {
        var check = confirm("请确认订单信息已经导入，商品影响费用已经维护？");
        if(!check) {
            return;
        }

        var orderYear = $("#orderYear").val();

        var url = contextPath + "/page/orderreport/generateOrderReport";
        $.ajax({
            type:"POST",
            url:url,
            data:{
                "orderYear":orderYear,
            },
            dataType:"json",
            success:function(data) {
                if(data.status == 'success') {
                    alert("生成成功");
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


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
    <title>订单管理</title>
    <c:import url="../common/common.jsp"></c:import>
</head>

<body class="easyui-layout">

<div id="toolbar" class="datagrid-toolbar" style="height: auto;display: none;background: none;">
    <fieldset>
        <legend>筛选</legend>
        <table class="tableForm">
            <tr>
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
                <th>订单状态：</th>
                <th>
                    <select id="orderStatus" name="orderStatus" class="easyui-combobox" style="width:120px;height:30px">
                        <option value="完成" selected="selected">完成</option>
                        <%--<option value="等待确认收货">等待确认收货</option>
                        <option value="(删除)完成">(删除)完成</option>
                        <option value="(删除)锁定">(删除)锁定</option>--%>
                    </select>
                </th>
                <th>订单号：</th>
                <td>
                    <input id="orderCode" name="orderCode" class="easyui-textbox" style="width:200px;height:32px">
                </td>
                <td>
                    <a class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="searchFun();" href="javascript:void(0);">查找</a>
                </td>
            </tr>

        </table>
    </fieldset>
</div>

<table id="datagrid"></table>

<script type="text/javascript">
    $(function(){
        datagrid = $('#datagrid').datagrid({
            url : contextPath + '/page/order/searchOrder',
            queryParams: {
                orderYear:2017
            },
            toolbar : '#toolbar',
            title : '',
            iconCls : 'icon-save',
            pagination : true,//控制分页
            pageSize : 10,
            pageList : [ 10, 50, 100 ],
            beforePageText: '第',//页数文本框前显示的汉字
            afterPageText: '页    共 {pages} 页',
            displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',
            fit : true,
            fitColumns : true,
            nowrap : false,
            border : false,
            idField : 'id',
            columns : [ [ {
                title : '序号',
                field : 'index',
                width : 20,
                formatter : function(value, rowData, rowIndex) {
                    return rowIndex+1;
                }
            } ,{
                title : '年份',
                field : 'orderYear',
                width : 40,

            } ,{
                title : '订单号',
                field : 'orderCode',
                width : 40,

            } ,{
                title : '商品名称',
                field : 'googsName',
                width : 40,

            } ,{
                title : '订购数量',
                field : 'totalCount',
                width : 40,

            } ,{
                title : '支付方式',
                field : 'payType',
                width : 40,

            } ,{
                title : '下单时间',
                field : 'orderTimeStr',
                width : 40,
            } ,{
                title : '京东价',
                field : 'jdPrice',
                width : 40,
                formatter : function(value, rowData, rowIndex) {
                    var date = rowData.jdPrice;
                    return date/100;
                }
            }, {
                title : '订单金额',
                field : 'orderAmount',
                width : 40,
                formatter : function(value, rowData, rowIndex) {
                    var date = rowData.orderAmount;
                    return date/100;
                }
            }, {
                title : '结算价',
                field : 'settmentMent',
                width : 40,
                formatter : function(value, rowData, rowIndex) {
                    var date = rowData.settmentMent;
                    return date/100;
                }
            }, {
                title : '余额支付金额',
                field : 'accountAmount',
                width : 40,
                formatter : function(value, rowData, rowIndex) {
                    var date = rowData.accountAmount;
                    return date/100;
                }
            }, {
                title : '应付金额',
                field : 'shouldAmount',
                width : 40,
                formatter : function(value, rowData, rowIndex) {
                    var date = rowData.shouldAmount;
                    return date/100;
                }
            }, {
                title : '订单状态',
                field : 'orderStatus',
                width : 50,
            }, {
                title : '订单类型',
                field : 'billType',
                width : 50,
            }, {
                title : '下单帐号',
                field : 'userCode',
                width : 50,
            }, {
                title : '客户姓名',
                field : 'userName',
                width : 50,
            }, {
                title : '客户地址',
                field : 'userAddress',
                width : 50,
            }, {
                title : '联系电话',
                field : 'userPhone',
                width : 50,
            }, {
                title : '商品名称',
                field : 'googsName',
                width : 40,
            }] ],
            onRowContextMenu : function(e, rowIndex, rowData) {
                e.preventDefault();
                $(this).datagrid('unselectAll');
                $(this).datagrid('selectRow', rowIndex);
                $('#menu').menu('show', {
                    left : e.pageX,
                    top : e.pageY
                });
            }
        });

        var p = datagrid.datagrid('getPager');
        $(p).pagination({
            beforePageText: '第',//页数文本框前显示的汉字
            afterPageText: '页    共 {pages} 页',
            displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',
        });

    });

    function searchFun() {
        datagrid.datagrid('load', {
            orderYear : $('input[name=orderYear]').val(),
            orderCode : $('input[name=orderCode]').val(),
            orderStatus : $('input[name=orderStatus]').val(),
        });
    }


</script>
</body>
</html>
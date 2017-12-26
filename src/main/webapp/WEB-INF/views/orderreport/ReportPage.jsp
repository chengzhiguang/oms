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
                        <option value="2017" selected="selected">2017年</option>
                        <option value="2018">2018年</option>
                        <option value="2019">2019年</option>
                        <option value="2020">2020年</option>
                    </select>
                </th>
                <th>商品编号：</th>
                <td>
                    <input id="goodsCode" name="goodsCode" class="easyui-textbox" style="width:200px;height:32px">
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
            url : contextPath + '/page/orderreport/searchOrder',
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
                title : '商品编号',
                field : 'goodsCode',
                width : 40,

            } ,{
                title : '商品名称',
                field : 'goodsName',
                width : 40,

            } ,{
                title : '客单价',
                field : 'unitPrice',
                width : 40,
                formatter : function(value, rowData, rowIndex) {
                    var date = rowData.unitPrice;
                    return date/100;
                }

            } ,{
                title : '总结算金额',
                field : 'sumSettmentAmount',
                width : 40,
                formatter : function(value, rowData, rowIndex) {
                    var date = rowData.sumSettmentAmount;
                    return date/100;
                }
            } ,{
                title : '总运费',
                field : 'sumLuggageAmount',
                width : 40,
                formatter : function(value, rowData, rowIndex) {
                    var date = rowData.sumLuggageAmount;
                    return date/100;
                }
            } ,{
                title : '营销成本',
                field : 'marketingCosts',
                width : 40,
                formatter : function(value, rowData, rowIndex) {
                    var date = rowData.marketingCosts;
                    return date/100;
                }
            }, {
                title : '总斤数',
                field : 'sumJin',
                width : 40,
            }, {
                title : '每斤运费',
                field : 'luggageJin',
                width : 40,
                formatter : function(value, rowData, rowIndex) {
                    var date = rowData.luggageJin;
                    return date/100;
                }
            }, {
                title : '每斤营销费',
                field : 'marketingFeeJin',
                width : 40,
                formatter : function(value, rowData, rowIndex) {
                    var date = rowData.marketingFeeJin;
                    return date/100;
                }
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
            goodsCode : $('input[name=goodsCode]').val(),
        });
    }


</script>
</body>
</html>
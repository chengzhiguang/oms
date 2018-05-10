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
                <th>商铺：</th>
                <td>
                    <select id="storeCode" name="storeCode" class="easyui-combobox" style="width:200px;height:30px">
                        <c:forEach items="${storeList}" var="item">
                            <option  value="${item.storeCode }">${item.storeName}</option>
                        </c:forEach>
                    </select>
                </td>
                <th>SPU名称：</th>
                <th>
                    <input class="easyui-datebox" style="width:145px" id="date" name="date" data-options="formatter:myformatter,parser:myparser"></input>
                </th>
            </tr>
            <tr>
                <th>
                    <br>
                    <a class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="searchFun();" href="javascript:void(0);">搜索</a>
                </th>
                <th>
                    <br>
                    <a class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="toImportDailyInfoPage();" href="javascript:void(0);">导入汇总数据</a>
                </th>
            </tr>
        </table>
    </fieldset>
</div>

<table id="datagrid"></table>

<div id="myWindow" style="width:300px;height:100px;left:10px;top:10px;" class="easyui-dialog" closed="true"></div>


<script type="text/javascript">
    $(function(){
        var curr_time = new Date();
        $("#date").datebox("setValue",myformatter(curr_time));

        datagrid = $('#datagrid').datagrid({
            url : contextPath + '/page/dailyinfo/searchDailyInfoList',
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
                title : '商铺',
                field : 'storeName',
                width : 40,

            } ,{
                title : '日期',
                field : 'dateStr',
                width : 40,

            } ,{
                title : '浏览量',
                field : 'pvCount',
                width : 40,

            } ,{
                title : '访客数',
                field : 'uvCount',
                width : 40,

            }, {
                title : '客户下单数',
                field : 'userOrderCount',
                width : 40,
            }, {
                title : '下单单量',
                field : 'orderCount',
                width : 40,
            }, {
                title : '下单金额',
                field : 'orderAmount',
                width : 40,
            }, {
                title : '客单价',
                field : 'userPrice',
                width : 40,
            }, {
                title : '状态',
                field : 'statusStr',
                width : 40,
            } , {
                title : '操作',
                field : 'oper',
                width : 80,
                formatter : function(value, rowData, rowIndex) {
                    var mark = '<span style="font-family:\'STHeitiSC-Light\', \'Heiti SC Light\', \'Heiti SC\';font-weight:200;color:#0099FF;" onclick="javascript:toImportDailyDetailPage(\'' + rowData.code + '\');">导入明细</span>';
                    return mark;
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
            storeCode : $("#storeCode").combobox('getValue'),
            date : $("#date").datebox("getValue"),
        });
    }

    function toImportDailyInfoPage() {
        showMyWindow("导入汇总数据", contextPath + "/page/dailyinfo/toImportDailyInfoPage", 600, 500);
    }

    function toImportDailyDetailPage(dailyCode) {
        showMyWindow("导入明细数据", contextPath + "/page/dailyinfo/toImportDailyDetailPage?dailyCode="+dailyCode, 600, 500);
    }

</script>
</body>
</html>
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

                <th>商品编号：</th>
                <th>
                    <input id="goodsCode" name="goodsCode" class="easyui-textbox" style="width:200px;height:32px">
                </th>
                <th>商品名称：</th>
                <td>
                    <input id="goodsName" name="goodsName" class="easyui-textbox" style="width:200px;height:32px">
                </td>
                <td>
                    <a class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="searchFun();" href="javascript:void(0);">查找</a>
                </td>
            </tr>
            <tr>

                <th>
                    <br>
                    <a class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="toAddAuthPage();" href="javascript:void(0);">新增物品</a>
                </th>
            </tr>
        </table>
    </fieldset>
</div>

<table id="datagrid"></table>

<div id="myWindow" style="width:300px;height:100px;left:10px;top:10px;" class="easyui-dialog" closed="true"></div>


<script type="text/javascript">
    $(function(){
        datagrid = $('#datagrid').datagrid({
            url : contextPath + '/page/goods/searchOrder',
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
                title : '商品编号',
                field : 'goodsCode',
                width : 40,

            } ,{
                title : '商品名称',
                field : 'goodsName',
                width : 40,

            },{
                title : '年营销成本',
                field : 'marketingCosts',
                width : 40,
                formatter : function(value, rowData, rowIndex) {
                    var date = rowData.marketingCosts;
                    var show = "";
                    if (date == undefined || date <= 0) {
                        show = "<font color=\"red\">0</font>";
                    } else {
                        show = "<font color=\"green\">"+date/100+"</font>";
                    }
                    return show;
                }
            },{
                title : '京东价',
                field : 'jdPrice',
                width : 40,
                formatter : function(value, rowData, rowIndex) {
                    var date = rowData.jdPrice;
                    return date/100;
                }
            }, {
                title : '市场价',
                field : 'marketPrice',
                width : 40,
                formatter : function(value, rowData, rowIndex) {
                    var date = rowData.marketPrice;
                    return date/100;
                }
            }, {
                title : '折扣(%)',
                field : 'discount',
                width : 40,
            }, {
                title : '商品毛重（单位g）',
                field : 'goodsWeight',
                width : 40,
            } , {
                title : '操作',
                field : 'oper',
                width : 80,
                formatter : function(value, rowData, rowIndex) {
//			    return '<a href="' + contextPath + '/manager/userManagerController/toUserDetailsPage?uid=' + rowData.uid + '">查看</a>';
                    var mark = '<span style="font-family:\'STHeitiSC-Light\', \'Heiti SC Light\', \'Heiti SC\';font-weight:200;color:#0099FF;" onclick="javascript:toModifyPage(\'' + rowData.goodsCode + '\');">修改</span>';
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
            goodsCode : $('input[name=goodsCode]').val(),
            goodsName : $('input[name=goodsName]').val(),
        });
    }

    function toAddAuthPage() {
        showMyWindow("添加商品信息", contextPath + "/page/goods/toAddGoodsInfoPage", 600, 500);
    }

    function toModifyPage(goodsCode) {
        showMyWindow("修改商品信息", contextPath + "/page/goods/toMofyGoodsInfoPage?goodsCode="+goodsCode, 600, 500);
    }

</script>
</body>
</html>
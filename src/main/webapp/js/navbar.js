
function getUrl(noteText) {
	var url = "";
	if(noteText == '订单导入') {
		url = contextPath + '/page/order/toImportOrderPage';
	} else if(noteText == '订单查询') {
		url = contextPath + '/page/order/toOrderManagerPage';
	} else if(noteText == '报表生成') {
        url = contextPath + '/page/orderreport/toOrderReportPage';
    } else if(noteText == '商品SPU维护') {
        url = contextPath + '/page/goods/toSpuManagerPage';
    } else if(noteText == '商品SKU维护') {
        url = contextPath + '/page/goods/toSkuManagerPage';
    } else if(noteText == '报表查看') {
        url = contextPath + '/page/orderreport/toReportPage';
    } else if(noteText == '日期汇总数据管理') {
        url = contextPath + '/page/dailyinfo/toDailyManagerPage';
    } else if(noteText == '商品明细统计报表') {
        url = contextPath + '/page/report/toSkuReportPage';
    } else if(noteText == '商铺维护') {
        url = contextPath + '/page/storeinfo/toStoreManagerPage';
    } else if(noteText == '员工信息管理') {
        url = contextPath + '/page/user/toUserManagerPage';
    }

	return url;
}
  
function openMenuTow(noteText, url) { 
	if(url == '') {
		alert("模块未定义");
    	return;
    }
    //树型菜单的名字   
    var exist_tab = $('#mainTabs').tabs('getTab', noteText);  
    //判断是否已经打开该选项卡  
    if (exist_tab) {  
        $('#mainTabs').tabs('select', noteText);  
        return;  
    } else {  
    	var content = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
    	
        $('#mainTabs').tabs('add', {  
            'id' : 'tab' + noteText,  
                title : noteText,  
                fit : true,  
                content : content,
                closable : true  
        });  
    }  
}  
  

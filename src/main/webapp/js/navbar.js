
function getUrl(noteText) {
	var url = "";
	if(noteText == '订单导入') {
		url = contextPath + '/page/order/toImportOrderPage';
	} else if(noteText == '订单查询') {
		url = contextPath + '/page/order/toOrderManagerPage';
	} else if(noteText == '报表生成') {
        url = contextPath + '/page/orderreport/toOrderReportPage';
    } else if(noteText == '商品维护') {
        url = contextPath + '/page/goods/toGoodsManagerPage';
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
  

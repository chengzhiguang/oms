function showMyWindow(title, href, width, height, modal, minimizable,  
        maximizable) {  
    $('#myWindow').window({  
                        title : title,  
                        width : width === undefined ? 600 : width,  
                        height : height === undefined ? 400 : height,  
                        content : '<iframe scrolling="yes" frameborder="0"  src="'  
                                + href  
                                + '" style="padding:0 0;width:100%;height:98%;"></iframe>',  
                        modal : modal === undefined ? true : modal,  
                        minimizable : minimizable === undefined ? false  
                                : minimizable,  
                        maximizable : maximizable === undefined ? false  
                                : maximizable,  
                        shadow : false,  
                        cache : false,  
                        closed : false,  
                        collapsible : false,  
                        resizable : false,  
                        loadingMessage : '正在加载数据，请稍等片刻......'  
	});
}

/**
     * 格式化日期（不含时间）
     */
    function formatterDate (date) {
        if (date == undefined) {
            return "";
        }
        /*json格式时间转js时间格式*/
//        date = date.substr(1, date.length - 2);
//        var obj = eval('(' + "{Date: new " + date + "}" + ')');
//        var date = obj["Date"];
        var date = new Date(date.replace(/-/g,   "/"));
        if (date.getFullYear() < 1900) {
            return "";
        }
 
        var datetime = date.getFullYear()
                + "-"// "年"
                + ((date.getMonth() + 1) > 10 ? (date.getMonth() + 1) : "0"
                        + (date.getMonth() + 1))
                + "-"// "月"
                + (date.getDate() < 10 ? "0" + date.getDate() : date
                        .getDate());
        return datetime;
    }
    /**
     * 格式化日期（含时间"00:00:00"）
     */
    function formatterDate2 (date) {
        if (date == undefined) {
            return "";
        }
        /*json格式时间转js时间格式*/
//        date = date.substr(1, date.length - 2);
//        var obj = eval('(' + "{Date: new " + date + "}" + ')');
//        var date = obj["Date"];
        var date = new Date(date.replace(/-/g,   "/"));
        if (date.getFullYear() < 1900) {
            return "";
        }
 
        /*把日期格式化*/
        var datetime = date.getFullYear()
                + "-"// "年"
                + ((date.getMonth() + 1) > 10 ? (date.getMonth() + 1) : "0"
                        + (date.getMonth() + 1))
                + "-"// "月"
                + (date.getDate() < 10 ? "0" + date.getDate() : date
                        .getDate()) + " " + "00:00:00";
        return datetime;
    }
    /**
     * 格式化去日期（含时间）
     */
    function formatterDateTime (date) {
        if (date == undefined) {
            return "";
        }
        /*json格式时间转js时间格式*/
//        date = date.substr(1, date.length - 2);
//        var obj = eval('(' + "{Date: new " + date + "}" + ')');
//        var date = obj["Date"];
        var date = new Date(date.replace(/-/g,   "/"));
        if (date.getFullYear() < 1900) {
            return "";
        }
 
        var datetime = date.getFullYear()
                + "-"// "年"
                + ((date.getMonth() + 1) > 10 ? (date.getMonth() + 1) : "0"
                        + (date.getMonth() + 1))
                + "-"// "月"
                + (date.getDate() < 10 ? "0" + date.getDate() : date
                        .getDate())
                + " "
                + (date.getHours() < 10 ? "0" + date.getHours() : date
                        .getHours())
                + ":"
                + (date.getMinutes() < 10 ? "0" + date.getMinutes() : date
                        .getMinutes())
                + ":"
                + (date.getSeconds() < 10 ? "0" + date.getSeconds() : date
                        .getSeconds());
        return datetime;
    }
    
    
    function myformatter(date){
		var y = date.getFullYear();
		var m = date.getMonth()+1;
		var d = date.getDate();
		return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
	}
	function myparser(s){
		if (!s) return new Date();
		var ss = (s.split('-'));
		var y = parseInt(ss[0],10);
		var m = parseInt(ss[1],10);
		var d = parseInt(ss[2],10);
		if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
			return new Date(y,m-1,d);
		} else {
			return new Date();
		}
	}
	
	
	function navbarShowIndex(module) {
		if(module == 'divWorkSurface') {
			$("#navDiv").accordion("select",0);
		} else if(module == 'divUserManager') {
			$("#navDiv").accordion("select",1);
		} else if(module == 'divContract') {
			$("#navDiv").accordion("select",2);
		} else if(module == 'divBease') {
			$("#navDiv").accordion("select",3);
		} else if(module == 'divOtherData') {
			$("#navDiv").accordion("select",4);
		} else if(module == 'divSysSet') {
			$("#navDiv").accordion("select",5);
		}
		
	}
	
	
	
	

	
	$.parser.onComplete = function() {  
        //...... 
//         alert(123);
    }  
    //加载树型菜单   
    $('#mytree').tree( {  
        onSelect : function(node) {  
            openMenuTow(node);  
        }  
    }); 
    $('#WorkSurfaceTree').tree( {  
        onSelect : function(node) {  
            openMenuTow(node);  
        }  
    }); 
  
    function openMenuTow(node) {  
        //树型菜单的名字   
        var noteText = $(".tree-title", node.target).text();  
        var exist_tab = $('#tt').tabs('getTab', noteText);  
        //判断是否已经打开该选项卡  
        if (exist_tab) {  
            $('#tt').tabs('select', noteText);  
            return;  
        } else {  
            $('#tt').tabs('add', {  
                'id' : 'tab',  
                title : noteText,  
                fit : true,  
                content : noteText,  
                closable : true  
            });  
            //获取最后一个tabs 在新加的选项卡后面添加"关闭全部"  
            var li = $(".tabs-wrap ul li:last-child");  
            $("#close").remove();  
            li.after("<li id='close'><a class='tabs-inner' href='javascript:void()' onClick='javascript:closeAll()'>关闭全部</a></li>");  
        }  
    }  
  
    function closeAll() {  
        $(".tabs li").each(function(index, obj) {  
              //获取所有可关闭的选项卡  
              var tab = $(".tabs-closable", this).text();  
              $(".easyui-tabs").tabs('close', tab);  
        });  
        $("#close").remove();//同时把此按钮关闭  
    } 
<%@ page language="java" pageEncoding="UTF-8"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%
	String path = request.getContextPath();
%>

<div data-options="region:'west',split:true" title="导航菜单" style="width:170px;">
	<input id="hiddenName" type="hidden" value="${name }" />
	<div class="easyui-accordion" fit="true"id="navDiv"  data-options="fit:true,border:false">
		<div class="easyui-panel" id="divWorkSurface" data-options="fit:true,border:false"  title="京东订单" style="padding:10px;">
			<ul id="WorkSurfaceTree">
				<li>
					<span>商品SPU维护</span>
				</li>
				<li>
					<span>商品SKU维护</span>
				</li>
                <li>
                    <span>日期汇总数据管理</span>
                </li>
				<li>
					<span>商品明细统计报表</span>
				</li>
				<%--<li>
                    <span>订单查询</span>
                </li>
                <li>
                    <span>报表生成</span>
                </li>
                <li>
                    <span>报表查看</span>
                </li>--%>
            </ul>  
		</div>
		<%--<div class="easyui-panel" id="divGoodsInfo" data-options="fit:true,border:false"  title="商品信息" style="padding:10px;">
			<ul id="GoodsInfoTree">
				<li>
					<span>商品维护</span>
				</li>
			</ul>
		</div>
		<div class="easyui-panel" id="divOrderStatistics" data-options="fit:true,border:false"  title="销售统计" style="padding:10px;">
			<ul id="OrderStatisticsTree">
				<li>
					<span>销售统计</span>
				</li>
			</ul>
		</div>--%>
	</div>
	
</div>

<script type="text/javascript">
		$(function(){
			var noteText = $("#hiddenName").val();
			if(noteText != '') {
				var url = getUrl(noteText);
		        openMenuTow(noteText, url);
			}
	        
		});
		$.parser.onComplete = function() {  
			
		}  
		
		
		
		//加载树型菜单   
		$('#UserManagerTree').tree( {  
		    onSelect : function(node) {  
		        var noteText = $(".tree-title", node.target).text();
		        var url = getUrl(noteText);
		        openMenuTow(noteText, url);
		    }  
		}); 
		$('#WorkSurfaceTree').tree( {  
		    onSelect : function(node) {  
		        var noteText = $(".tree-title", node.target).text();
		        var url = getUrl(noteText);
		        openMenuTow(noteText, url);  
		    }  
		});
		$('#ContractTree').tree( {  
		    onSelect : function(node) {  
		        var noteText = $(".tree-title", node.target).text();
		        var url = getUrl(noteText);
		        openMenuTow(noteText, url);  
		    }  
		});
		$('#BeaseTree').tree( {  
		    onSelect : function(node) {  
		        var noteText = $(".tree-title", node.target).text();
		        var url = getUrl(noteText);
		        openMenuTow(noteText, url);  
		    }  
		});
		$('#OtherDataTree').tree( {  
		    onSelect : function(node) {  
		        var noteText = $(".tree-title", node.target).text();
		        var url = getUrl(noteText);
		        openMenuTow(noteText, url);  
		    }  
		});
		$('#SysSetTree').tree( {  
		    onSelect : function(node) {  
		        var noteText = $(".tree-title", node.target).text();
		        var url = getUrl(noteText);
		        openMenuTow(noteText, url);  
		    }  
		});
		$('#Functions').tree( {  
		    onSelect : function(node) {  
		        var noteText = $(".tree-title", node.target).text();
		        var url = getUrl(noteText);
		        openMenuTow(noteText, url);  
		    }  
		});
    </script>

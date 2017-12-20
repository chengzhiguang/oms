function submit() {
    $("#import_form").ajaxSubmit({
        type:"post",
        async : false,
        cache : false,
        url: contextPath+'/page/order/importOrderInfo',
        success:function (data) {
            jsonData = eval("(" + data + ")");
            var status=jsonData.status;
            if(status=='success'){
                $.messager.alert('信息提示：','已成功');
                $('#list_data').datagrid("reload");
                $('#import-dialog').dialog('close');
            }else{
                $.messager.alert('信息提示：',message,'info');
            }

        }
    });

}
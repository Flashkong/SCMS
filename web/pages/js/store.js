function initTable() {
    $a=$('#goods-table');
    //先销毁表格
    $a.bootstrapTable('destroy');
    //初始化表格,动态从服务器加载数据
    $a.bootstrapTable({
        method: "get",  //使用get请求到服务器获取数据
        url: "/try/show_store", //获取数据的Servlet地址
        toolbar: '#toolbar',
        striped: true,  //表格显示条纹
        pagination: true, //启动分页
        pageSize: 1,  //每页显示的记录数
        pageNumber:1, //当前第几页
        pageList: [1,5, 10, 15, 20, 25],  //记录数可选列表
        search: false,  //是否启用查询
        searchOnEnterKey:true,
        showColumns: true,  //显示下拉框勾选要显示的列
        showRefresh: true,  //显示刷新按钮
        sidePagination: "server", //表示服务端请求
        //设置为undefined可以获取pageNumber，pageSize，searchText，sortName，sortOrder
        //设置为limit可以获取limit, offset, search, sort, order
        queryParamsType : "undefined",

        cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        sortable: false,                     //是否启用排序
        sortOrder: "asc",                   //排序方式
        strictSearch: false,
        minimumCountColumns: 2,             //最少允许的列数
        clickToSelect: true,                //是否启用点击选中行
        height: 550,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
        uniqueId: "ID",                     //每一行的唯一标识，一般为主键列
        showToggle:false,                    //是否显示详细视图和列表视图的切换按钮
        cardView: false,                     //是否显示详细视图
        detailView: false,                   //是否显示父子表


        queryParams: function queryParams(params) {   //设置查询参数
            debugger;
            var param = {
                pageNumber: params.pageNumber,
                pageSize: params.pageSize
            };
            return param;
        },
        onLoadSuccess: function(data){  //加载成功时执行
            debugger;
            if(data["Status"]===200){
                layer.msg("数据加载成功!");
                var s=$.parseJSON(data["message"]);
                $a.bootstrapTable("load",s)
            }else {
                layer.msg(data["message"])
            }
        },
        onLoadError: function(){  //加载失败时执行
            layer.msg("加载数据失败！")
        },
        columns:[
            {
                field : 'Sid',
                title : '库编号',
                editable:false
            },
            {
                field : 'Sname',
                title : '库名称',
                editable:{
                    type: 'text',
                    title: '库名称',
                    validate: function (v) {
                        if (!v) return '库名称不能为空';
                    }
                }
            },
            {
                formatter: function (value, row, index) {
                    return [
                        '<a href="javascript:delPer(' + row.Sid + ')">' +
                        '<i class="glyphicon glyphicon-remove"></i>删除' +
                        '</a>'
                    ].join('');
                },
                title: '操作'
            }
        ],
        onEditableSave: function (field, row, oldValue, $el) {
            //修改时候的操作
            debugger;
            $.ajax({
                type: "POST",
                contentType: "application/json; charset=utf-8",
                url: "/try/modify_store",
                dataType: "json",
                data: JSON.stringify(row),
                success: function (message) {
                    debugger;
                    if(message["Status"]===200){
                        layer.msg("修改成功！");
                        $('#goods-table').bootstrapTable("refresh")
                    }else {
                        layer.msg(message["message"])
                    }
                },
                error: function () {
                    layer.msg("请求超时或服务器出错！")
                },
                complete: function () {

                }
            });
        }
    });

}

function delPer(Sid) {
    //删除时候的操作
    layer.confirm('删除库信息的同时，入库表，出库表，库存现状表，盘库表中有关库的记录将会全部删除，你确定要执行' +
        '此操作?', {icon: 3, title:'提示'}, function(index){
        //do something
        debugger;
        var s=JSON.stringify({"Sid":Sid});
        $.ajax(
            {
                type: "POST",
                url: "/try/delete_store"+"?time="+new Date().getTime(),
                async:true,
                contentType: "application/json; charset=utf-8",
                data: s,
                dataType: "json",
                success:function (message) {
                    debugger;
                    if(message["Status"]===200){
                        layer.msg("删除成功！");
                        $('#goods-table').bootstrapTable("refresh")
                    }else {
                        layer.msg(message["message"])
                    }
                },
                error:function (message) {
                    debugger;
                    layer.msg("请求超时或服务器出错！")
                }
            }
        );

        layer.close(index);
    });
}

function initlayer() {
    //添加新的库

    $("#add-goods-table").hide();
    //当点击查询按钮的时候执行
    $("#btn_add").click(function () {
        //先设置表单的easyform

        index=layer.open({
            type: 1,
            content: $('#add-goods-table')
        });

        countAddAjaxI=0;
        $(".register_form").easyform({
            submitButton : '.register',
            fields :{
                '#Sid' : {
                    error : '请输入库编号'
                },
                '#Sname' : {
                    error : '请输入库名称'
                }
            },
            success : function  () {
                debugger;
                $may=$("input[name='Sid']");
                $mayee=$("input[name='Sname']");
                var Sid=$may.val();
                var Sname=$mayee.val();

                var data={"Sid":Sid,"Sname":Sname};
                var s=JSON.stringify(data);

                //关闭layer
                layer.close(index);
                layer.msg("正在添加，请稍候...");
                debugger;
                if(countAddAjaxI===0){
                    countAddAjaxI++;
                    $.ajax(
                        {
                            type: "POST",
                            url: "/try/add_store"+"?time="+new Date().getTime(),
                            async:true,
                            contentType: "application/json; charset=utf-8",
                            data: s,
                            dataType: "json",
                            success:function (message) {
                                //清除表单字段值
                                $may.val("");
                                $mayee.val("");

                                debugger;
                                if(message["Status"]===200){
                                    layer.msg("添加成功！");
                                    $('#goods-table').bootstrapTable("refresh")
                                }else {
                                    layer.msg(message["message"])
                                }
                            },
                            error:function (message) {
                                //清除表单字段值
                                $may.val("");
                                $mayee.val("");

                                debugger;
                                layer.msg("请求超时或服务器出错！")
                            }
                        }
                    );
                }
                return false;
            }
        });
    });
}
var index;
var countAddAjaxI=0;
$(document).ready(function () {
    $("#user_name a").append(getCookie("name"));

    function getCookie(name)
    {
        var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");

        if(arr=document.cookie.match(reg))

            return unescape(arr[2]);
        else
            return null;
    }

    //调用函数，初始化表格
    initTable();

    initlayer();
});
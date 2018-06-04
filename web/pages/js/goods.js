function initTable() {
    $a=$('#goods-table');
    //先销毁表格
    $a.bootstrapTable('destroy');
    //初始化表格,动态从服务器加载数据
    $a.bootstrapTable({
        method: "get",  //使用get请求到服务器获取数据
        url: "/try/show_goods", //获取数据的Servlet地址
        toolbar: '#toolbar',
        striped: true,  //表格显示条纹
        pagination: true, //启动分页
        pageSize: 1,  //每页显示的记录数
        pageNumber:1, //当前第几页
        pageList: [1,5, 10, 15, 20, 25],  //记录数可选列表
        search: true,  //是否启用查询
        searchOnEnterKey:true,
        searchText:"回车进行搜索",
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
                pageSize: params.pageSize,
                orderNum : $("#orderNum").val(),
                searchText : params.searchText,
                name:nameToIndex[$("#search-name").val()]
            };
            return param;
        },
        onLoadSuccess: function(data){  //加载成功时执行
            debugger;
            if(data["Status"]===200){
                layer.msg("数据加载成功!");
                var s=$.parseJSON(data["message"]);
                s["Tname"]={value:s["Tname"],text:s["Tname"]};
                $a.bootstrapTable("load",s)
            }else {
                layer.msg(data["message"])
            }
        },
        onLoadError: function(){  //加载失败时执行
            layer.msg("加载数据失败！  ")
        },
        columns:[
            {
                field : 'Gid',
                title : '商品编号',
                editable:false
                // editable: {
                //     type: 'text',
                //     title: '商品编号',
                //     validate: function (v) {
                //         if (!v) return '商品编号不能为空';
                //     }
                // }
            },
            {
                field : 'Gname',
                title : '商品名称',
                editable:{
                    type: 'text',
                    title: '商品名称',
                    validate: function (v) {
                        if (!v) return '商品名称不能为空';
                    }
                }
            },
            {
                field : 'Tname',
                title : '商品类别',
                editable:{
                    type: 'select',
                    title: '商品类别',
                    source:[{value:"家用电器",text:"家用电器"},{value:"电子产品",text:"电子产品"},{value:"服装",text:"服装"},
                        {value:"化妆品",text:"化妆品"},{value:"学习用品",text:"学习用品"},{value:"生活用品",text:"生活用品"}]
                }
            },
            {
                field : 'Gproducer',
                title : '商品生产商',
                editable:{
                    type: 'text',
                    title: '商品生产商',
                    validate: function (v) {
                        if (!v) return '商品生产商不能为空';
                    }
                }
            },
            {
                formatter: function (value, row, index) {
                    debugger;
                    return [
                        '<a href="javascript:delPer(' + row.Gid + ')">' +
                        '<i class="glyphicon glyphicon-remove"></i>删除' +
                        '</a>'
                    ].join('');
                },
                title: '操作'
            }
        ],
        onEditableSave: function (field, row, oldValue, $el) {
            debugger;
            $.ajax({
                type: "POST",
                contentType: "application/json; charset=utf-8",
                url: "/try/modify_goods",
                dataType: "json",
                data: JSON.stringify(row),
                success: function (message) {
                    debugger;
                    if(message["Status"]===200){
                        layer.msg("修改成功！");
                        $('#goods-table').bootstrapTable("refresh",{url:"/try/show_goods"})
                    }else {
                        layer.msg(message["message"])
                    }
                },
                error: function () {
                    alert("Error");
                },
                complete: function () {

                }

            });
        }
    });

}

function delPer(Gid) {
    debugger;
    layer.confirm('删除商品信息的同时，入库表，出库表，库存现状表，盘库表中有关商品的记录将会全部删除，你确定要执行' +
        '此操作?', {icon: 3, title:'提示'}, function(myindex){
        //do something
        var s=JSON.stringify({"Gid":Gid});
        $.ajax(
            {
                type: "POST",
                url: "/try/delete_goods"+"?time="+new Date().getTime(),
                async:true,
                contentType: "application/json; charset=utf-8",
                data: s,
                dataType: "json",
                success:function (message) {
                    debugger;
                    if(message["Status"]===200){
                        layer.msg("删除成功！");
                        $('#goods-table').bootstrapTable("refresh",{url:"/try/show_goods"})
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

        layer.close(myindex);
    });
}

function initlayer() {
    $("#add-goods-table").hide();
    //当点击查询按钮的时候执行
    $("#btn_add").click(function () {
        index=layer.open({
            type: 1,
            content: $('#add-goods-table')
        });
        AddButtonIndexI=0;
        //先设置表单的easyform
        $(".register_form").easyform({
            submitButton : '.register',
            fields :{
                '#Gid' : {
                    error : '请输入商品编号'
                },
                '#Gname' : {
                    error : '请输入商品名称'
                },
                '#Gproducer':{
                    error:'请输入商品类别'
                }
            },
            success : function  () {
                debugger;
                var Gid=$("input[name='Gid']").val();
                var Gname=$("input[name='Gname']").val();
                var Tname=$("select[name='Tname']").val();
                var Gproducer=$("input[name='Gproducer']").val();

                var data={"Gid":Gid,"Gname":Gname,"Tname":Tname,"Gproducer":Gproducer};
                var s=JSON.stringify(data);

                //关闭layer
                layer.close(index);
                layer.msg("正在添加，请稍候...");
                debugger;
                if(AddButtonIndexI===0){
                    AddButtonIndexI++;
                    $.ajax(
                        {
                            type: "POST",
                            url: "/try/add_goods"+"?time="+new Date().getTime(),
                            async:true,
                            contentType: "application/json; charset=utf-8",
                            data: s,
                            dataType: "json",
                            success:function (message) {
                                $("input[name='Gid']").val("");
                                $("input[name='Gname']").val("");
                                $("input[name='Gproducer']").val("");

                                debugger;
                                if(message["Status"]===200){
                                    layer.msg("插入成功！");
                                    $('#goods-table').bootstrapTable("refresh",{url:"/try/show_goods"})
                                }else {
                                    layer.msg(message["message"])
                                }
                            },
                            error:function (message) {
                                $("input[name='Gid']").val("");
                                $("input[name='Gname']").val("");
                                $("input[name='Gproducer']").val("");

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
var nameToIndex={
    "商品编号":"Gid",
    "商品名称":"Gname",
    "商品类别":"Tname",
    "商品生产商":"Gproducer"
};
var AddButtonIndexI=0;



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
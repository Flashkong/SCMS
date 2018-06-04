function initTable() {
    $a=$('#goods-table');
    //先销毁表格
    $a.bootstrapTable('destroy');
    //初始化表格,动态从服务器加载数据
    $a.bootstrapTable({
        method: "get",  //使用get请求到服务器获取数据
        url: "/try/show_stock", //获取数据的Servlet地址
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
                $a.bootstrapTable("load",s)
            }else {
                layer.msg(data["message"])
            }
        },
        onLoadError: function(){  //加载失败时执行
            layer.msg("加载数据失败！")
        },
        exportDataType: "all",
        showExport: true,  //是否显示导出按钮
        buttonsAlign:"right",  //按钮位置
        exportTypes:[ 'excel','doc','csv', 'txt' ],  //导出文件类型
        Icons:'glyphicon-export',
        exportOptions:{
            // ignoreColumn: [0,1],  //忽略某一列的索引
            fileName: '库存记录',  //文件名称设置
            worksheetName: 'sheet1',  //表格工作区名称
            tableName: '库存记录'
            // excelstyles: ['background-color', 'color', 'font-size', 'font-weight'], 设置格式
        },
        columns:[
            {
                field : 'Gid',
                title : '商品编号'
            },
            {
                field : 'Gname',
                title : '商品名称'
            },
            {
                field : 'Tname',
                title : '商品类别'
            },
            {
                field : 'Gproducer',
                title : '商品生产商'
            },
            {
                field : 'Sid',
                title : '商品所在库编号'
            },
            {
                field : 'Sname',
                title : '商品所在库名称'
            },
            {
                field : 'Snum',
                title : '商品数目'
            }
        ]
    });

}

var nameToIndex={
    "商品编号":"Gid",
    "商品名称":"Gname",
    "商品类别":"Tname",
    "商品生产商":"Gproducer",
    "商品所在库编号":"Sid",
    "商品所在库名称":"Sname",
    "商品数目":"Snum"
};

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
});
function initTable() {
    $a=$('#goods-table');
    //先销毁表格
    $a.bootstrapTable('destroy');
    //初始化表格,动态从服务器加载数据
    $a.bootstrapTable({
        method: "get",  //使用get请求到服务器获取数据
        url: "/try/show_panstore", //获取数据的Servlet地址
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
                s["Gid"]={value:s["Gid"],text:s["Gid"]};
                s["Sid"]={value:s["Sid"],text:s["Sid"]};
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
                field : 'Pid',
                title : '盘库编号',
                editable:false
            },
            {
                field : 'Gid',
                title : '商品编号',
                editable:{
                    type: 'select',
                    title: '商品编号',
                    source: loadGid()
                }
            },
            {
                field : 'Sid',
                title : '库编号',
                editable:{
                    type: 'select',
                    title: '库编号',
                    source: loadSid()
                }
            },
            {
                field : 'Pnum',
                title : '盘点数量',
                editable:{
                    type: 'text',
                    title: '盘点数量',
                    validate: function (v) {
                        if (!v) return '盘点数量不能为空';
                    }
                }
            },
            {
                field : 'Pdate',
                title : '盘库时间',
                formatter:function(value, row, index){
                    return value;
                },
                editable:{
                    type: 'date',
                    placement: 'left',
                    title: '请选择日期:'
                }
            },
            {
                formatter: function (value, row, index) {
                    debugger;
                    return [
                        '<a href="javascript:delPer(' + row.Pid + ')">' +
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
                url: "/try/modify_panstore",
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

function delPer(Iid) {
    debugger;
    var s=JSON.stringify({"Iid":Iid});
    $.ajax(
        {
            type: "POST",
            url: "/try/delete_panstore"+"?time="+new Date().getTime(),
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
}

function initAddlayer() {
    //当点击添加按钮的时候执行
    $("#btn_add").click(function () {
        //先设置表单的easyform
        $("#Pdate").datetimepicker({
            format: 'yyyy-mm-dd',
            todayBtn: true,
            autoclose: true,//选中自动关闭
            minView: "month",//设置只显示到月份
            language: 'zh-CN'
        });

        if(i===0){
            //设置select的值
            setGidFromHtmlAdd();
            setSidFromHtmlAdd();
            i++;
        }

        index=layer.open({
            type: 1,
            content: $('#add-goods-table')
        });

        //需要先初始化
        countAddAjaxI=0;
        $(".register_form").easyform({
            submitButton : '.register',
            fields :{
                '#Pid' : {
                    error : '请输入盘库编号'
                },
                '#Pnum':{
                    error : '请输入盘点数量'
                }
            },
            success : function  () {
                debugger;
                $setIid=$("input[name='Pid']");
                $setInum=$("input[name='Pnum']");
                $setDate=$("input[name='Pdate']");
                var Iid=$setIid.val();
                var Gid=$("select[name='Gid']").val();
                var Idate=$setDate.val();
                var Sid=$("select[name='Sid']").val();
                var Inum=$setInum.val();

                var data={"Pid":Iid,"Gid":Gid,"Sid":Sid,"Pdate":Idate,"Pnum":Inum};
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
                            url: "/try/add_panstore"+"?time="+new Date().getTime(),
                            async:true,
                            contentType: "application/json; charset=utf-8",
                            data: s,
                            dataType: "json",
                            success:function (message) {
                                //清除原来的输入信息
                                $setIid.val("");
                                $setInum.val("");
                                $setDate.val("");

                                debugger;
                                if(message["Status"]===200){
                                    layer.msg("插入成功！");
                                    $('#goods-table').bootstrapTable("refresh")
                                }else {
                                    layer.msg(message["message"])
                                }
                            },
                            error:function (message) {
                                //清楚原来的输入信息
                                $setIid.val("");
                                $setInum.val("");
                                $setDate.val("");

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

function initComputelayer() {
    $("#btn_compute_profile").click(function () {

        debugger;

        $("#startDate").datetimepicker({
            format: 'yyyy-mm-dd',
            todayBtn: true,
            autoclose: true,//选中自动关闭
            minView: "month",//设置只显示到月份
            language: 'zh-CN'
        });

        $("#endDate").datetimepicker({
            format: 'yyyy-mm-dd',
            todayBtn: true,
            autoclose: true,//选中自动关闭
            minView: "month",//设置只显示到月份
            language: 'zh-CN'
        });

        computeIndex=layer.open({
            type: 1,
            content: $('#compute_profile_table')
        });

        //需要先初始化
        countAddAjaxI=0;
        $(".compute_form").easyform({
            submitButton : '#btn_compute',
            fields :{
            },
            success : function  () {
                debugger;
                $setendDate=$("input[name='endDate']");
                $setstartDate=$("input[name='startDate']");
                var start=$setstartDate.val();
                var end=$setendDate.val();

                if(start===""||end===""){
                    layer.msg("请选择日期！")
                }else {
                    var data={'startDate':start,'endDate':end};
                    var s=JSON.stringify(data);

                    //关闭layer
                    layer.close(computeIndex);
                    layer.msg("正在计算，请稍候...");

                    debugger;
                    if(countAddAjaxI===0){
                        countAddAjaxI++;
                        $("#a-p,#b-p,#c-p,#d-p,#e-p").text("");
                        $("#a-p").append($("<strong></strong>").text("起始时间："));
                        $("#b-p").append($("<strong></strong>").text("终止时间："));
                        $("#c-p").append($("<strong></strong>").text("入库总花费："));
                        $("#d-p").append($("<strong></strong>").text("出库总收入："));
                        $("#e-p").append($("<strong id=\"e-s\"></strong>"));
                        $.ajax(
                            {
                                type: "POST",
                                url: "/try/profile_compute"+"?time="+new Date().getTime(),
                                async:true,
                                contentType: "application/json; charset=utf-8",
                                data: s,
                                dataType: "json",
                                success:function (message) {
                                    //清除原来的输入信息
                                    $setstartDate.val("");
                                    $setendDate.val("");

                                    debugger;
                                    if(message["Status"]===200){
                                        //展示计算的结果
                                        var a=JSON.parse(message["message"]);

                                        $("#a-p").append(start);
                                        $("#b-p").append(end);
                                        $("#c-p").append(a["Incost"]+"元");
                                        $("#d-p").append(a["Outget"]+"元");
                                        if(a["profile"]>=0){
                                            $("#e-s").append("盈利：");
                                            $("#e-p").append(a["profile"]+"元")
                                        }else {
                                            $("#e-s").append("亏损：");
                                            $("#e-p").append(-a["profile"]+"元")
                                        }
                                        layer.open({
                                            type:1,
                                            title:"盈亏计算结果",
                                            content: $("#profile_show"),
                                            btn: ['确定'],
                                            yes: function(index, layero){
                                                layer.closeAll(); //如果设定了yes回调，需进行手工关闭
                                            }
                                        });
                                    }else {
                                        layer.msg(message["message"])
                                    }
                                },
                                error:function (message) {
                                    //清除原来的输入信息
                                    $setstartDate.val("");
                                    $setendDate.val("");

                                    debugger;
                                    layer.msg("计算盈亏时请求超时或服务器出错！")
                                }
                            }
                        );
                    }
                }
                return false;
            }
        });
    });
}
var i=0;
var countAddAjaxI=0;
var index;
var computeIndex;
var nameToIndex={
    "商品编号":"Gid",
    "盘库编号":"Pid",
    "盘库时间":"Pdate",
    "库编号":"Sid"
};
var GidList = [];
var SidList = [];
function loadGid() {
    $.ajax({
        url: '/try/getGid',
        async: false,
        type: "GET",
        data: {},
        success: function (data, status) {
            debugger;
            if(data["Status"]===200){
                var s=$.parseJSON(data["message"]);
                for(var temp in s){
                    GidList.push({value:s[temp]["Gid"],text:s[temp]["Gid"]})
                }
            }else {
                layer.msg("加载商品编号失败！")
            }
        }
    });
    return GidList;
}

function loadSid() {
    $.ajax({
        url: '/try/getSid',
        async: false,
        type: "GET",
        data: {},
        success: function (data, status) {
            debugger;
            if(data["Status"]===200){
                var s=$.parseJSON(data["message"]);
                for(var temp in s){
                    SidList.push({value:s[temp]["Sid"],text:s[temp]["Sid"]})
                }
            }else {
                layer.msg("加载商品编号失败！")
            }
        }
    });
    return SidList;
}

function setSidFromHtmlAdd(){
    $.each(SidList,function (n, value) {
        debugger;
        var temp = document.createElement("option");
        temp.innerHTML=value["text"];
        $("#Sid").append(temp)
    });
}

function setGidFromHtmlAdd(){
    $.each(GidList,function (n, value) {
        debugger;
        var temp = document.createElement("option");
        temp.innerHTML=value["text"];
        $("#Gid").append(temp)
    });

}

$(document).ready(function () {
    $("#add-goods-table").hide();
    $("#compute_profile_table").hide();
    $("#profile_show").hide();

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

    initAddlayer();
    initComputelayer();
});
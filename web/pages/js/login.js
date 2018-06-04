$(document).ready(function () {
    $('.login_form').easyform({
        submitButton : '.login',
        fields : {
            '#name' : {
                error : '请输入用户名'
                // text : {
                //     test : /^[\w]{2,8}/,
                //     message : '用户名长度2-8'
                // }
            },
            '#pwd' : {
                error : '请输入密码'
            }
        },
        success : function  () {
            checklogin();
            return false;
        }
    });
});

function showInfo(str) {
    var text=$("<h3></h3>").text(str);
    $(".show_info").append(text);
}

function checklogin() {
    debugger;
    var name=$("input[name='name']").val();
    var pwd=$("input[name='pwd']").val();

    //更改button字样
    $(".login").text("正在登陆...");
    //向服务器发送请求
    var j={"name":name,"pwd":pwd};
    sendRequestToServer(j);
}

function sendRequestToServer(j){
    var s=JSON.stringify(j);
    $.ajax(
        {
            type: "POST",
            url: "/try/login"+"?time="+new Date().getTime(),
            async:true,
            contentType: "application/json; charset=utf-8",
            data: s,
            dataType: "json",
            success:function (message) {


                debugger;
                if(message["Status"]===200){
                    //更改button字样
                    $(".login").text("登录成功！");
                    //表示注册成功
                    layer.msg(message["message"]);
                    $(window).attr('location','stock.html')
                }else {
                    //更改button字样
                    $(".login").text("登录");
                    //500的，错误信息，并且不跳转页面。
                    layer.msg(message["message"]);
                }

            },
            error:function (message) {
                //更改button字样
                $(".login").text("登录");

                debugger;
                layer.msg("请求服务器失败！");
                $(window).attr('location','register.html')
            }
        }
    );
}
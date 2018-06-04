$(document).ready(function () {
       $('.register_form').easyform({
           submitButton : '.register',
           fields : {
               '#name' : {
                   error : '请输入用户名'
                   // text : {
                   //     test : /^[\w]{2,8}/,
                   //     message : '用户名长度2-8'
                   // }
               },
               '#email' : {
                   error : '请输入邮箱',
                   email : {
                       test : 'email',
                       message : 'email格式错误'
                   }
               },
               '#pwd' : {
                   error : '请输入密码',
                   length : {
                       test :/^[\w]{5,16}$/,
                       message : '密码长度5-16'
                   }
               },
               '#repwd':{
                   error : '请输入确认密码',
                   length : {
                       test :/^[\w]{5,16}$/,
                       message : '密码长度5-16'
                   }
               },
               '#mykey':{
                   error:'请输入密匙'
               }
           },
           success : function  () {
               $(".show_info").children().remove();
               checkForm();
               return false;
           }
       });

});

function checkForm(){
    var name=$("input[name='name']").val();
    var email=$("input[name='email']").val();
    var pwd=$("input[name='pwd']").val();
    var repwd=$("input[name='repwd']").val();
    var mykey=$("input[name='mykey']").val();

    if(repwd!==pwd){
        layer.msg("两次输入密码不一致！");
    }else {
        //更改button字样
        $(".register").text("正在注册...");
        //向服务器发送请求
        var j={"name":name,"email":email,"pwd":pwd,"mykey":mykey};
        sendRequestToServer(j);
    }
}


function sendRequestToServer(j) {
    var s=JSON.stringify(j);
    debugger;
    $.ajax(
        {
            type: "POST",
            url: "/try/register"+"?time="+new Date().getTime(),
            async:true,
            contentType: "application/json; charset=utf-8",
            data: s,
            dataType: "json",
            success:function (message) {


                debugger;
                if(message["Status"]===200){
                    //更改button字样
                    $(".register").text("注册成功！");
                        //表示注册成功
                        layer.msg(message["message"]);
                        $(window).attr('location','login.html')
                }else {
                    //更改button字样
                    $(".register").text("注册");
                    //500的，错误信息，并且不跳转页面。
                    layer.msg(message["message"]);
                }

            },
            error:function (message) {
                //更改button字样
                $(".register").text("注册");

                debugger;
                layer.msg("注册失败！");
            }
        }
    );
}
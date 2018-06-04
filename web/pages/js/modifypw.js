$(document).ready(function () {
    $('.register_form').easyform({
        submitButton : '.register',
        fields : {
            '#pwd' : {
                error : '请输入新密码',
                length : {
                    test :/^[\w]{5,16}$/,
                    message : '密码长度5-16'
                }
            },
            '#repwd': {
                error: '请输入确认新密码',
                length: {
                    test: /^[\w]{5,16}$/,
                    message: '密码长度5-16'
                }
            }
        },
        success : function  () {
            $(".show_info").children().remove();
            checkForm();
            return false;
        }
    });

});

function getCookie(name)
{
    var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");

    if(arr=document.cookie.match(reg))

        return unescape(arr[2]);
    else
        return null;
}

function checkForm(){
    var pwd=$("input[name='pwd']").val();
    var repwd=$("input[name='repwd']").val();

    if(repwd!==pwd){
        layer.msg("两次输入密码不一致！");
    }else {
        //更改button字样
        $(".register").text("正在更改...");
        //向服务器发送请求
        var j={"pwd":pwd,"name":getCookie("name")};
        sendRequestToServer(j);
    }
}


function sendRequestToServer(j) {
    var s=JSON.stringify(j);
    debugger;
    $.ajax(
        {
            type: "POST",
            url: "/try/modifypw"+"?time="+new Date().getTime(),
            async:true,
            contentType: "application/json; charset=utf-8",
            data: s,
            dataType: "json",
            success:function (message) {

                debugger;
                if(message["Status"]===200){
                    //更改button字样
                    $(".register").text("更改成功！");
                        layer.msg(message["message"]);
                        $(window).attr('location','stock.html');
                }else {
                    //更改button字样
                    $(".register").text("更改密码");
                    layer.msg(message["message"]);
                }

            },
            error:function (message) {
                //更改button字样
                $(".register").text("更改密码");

                debugger;
                layer.mag("更改失败！");
            }
        }
    );
}
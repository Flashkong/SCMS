package main.login;

import bean.LoginBean;
import com.alibaba.fastjson.JSON;
import fun.ConnectResponse;
import fun.login.LoginCon;
import utils.JsonUtils;
import utils.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "loginServlet", urlPatterns = {"/login"})
public class loginServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        LoginBean request=(LoginBean) JsonUtils.parseJson(req, LoginBean.class);
        LoginCon connect=new LoginCon();
        ConnectResponse response=connect.DoTask(request);
        if(response.getStatus()==200){
            Cookie cookie = new Cookie("name", request.getUserID());
            // 有效期,秒为单位,设置为五年
            cookie.setMaxAge(5*365*24*60*60);
            // 设置cookie
            resp.addCookie(cookie);
        }
        ServletUtils.resJsonString(resp, JSON.toJSONString(response));
    }

}

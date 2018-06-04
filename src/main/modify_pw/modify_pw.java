package main.modify_pw;

import bean.RegisterBean;
import com.alibaba.fastjson.JSON;
import fun.ConnectResponse;
import fun.modifypw.modify_pw_con;
import fun.register.RegisterCon;
import utils.JsonUtils;
import utils.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "modify_pw", urlPatterns = {"/modifypw"})
public class modify_pw extends HttpServlet {
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
        RegisterBean request=(RegisterBean)JsonUtils.parseJson(req, RegisterBean.class);
        modify_pw_con connect=new modify_pw_con();
        ConnectResponse response=connect.DoTask(request);
        ServletUtils.resJsonString(resp, JSON.toJSONString(response));
    }
}

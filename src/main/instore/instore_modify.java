package main.instore;

import bean.InstoreBean;
import com.alibaba.fastjson.JSON;
import fun.ConnectResponse;
import fun.instore.instore_modify_con;
import utils.JsonUtils;
import utils.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "instore_modify", urlPatterns = {"/modify_instore"})
public class instore_modify extends HttpServlet {
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
        InstoreBean request=(InstoreBean) JsonUtils.parseJson(req, InstoreBean.class);
        instore_modify_con connect=new instore_modify_con();
        ConnectResponse response=connect.DoTask(request);
        ServletUtils.resJsonString(resp, JSON.toJSONString(response));
    }
}

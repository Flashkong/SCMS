package main.outstore;

import bean.InstoreBean;
import bean.OutStoreBean;
import com.alibaba.fastjson.JSON;
import fun.ConnectResponse;
import fun.instore.instore_add_con;
import fun.outstore.outstore_add_con;
import utils.JsonUtils;
import utils.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "outstore_add", urlPatterns = {"/add_outstore"})
public class outstore_add extends HttpServlet {
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
        OutStoreBean request=(OutStoreBean) JsonUtils.parseJson(req, OutStoreBean.class);
        outstore_add_con connect=new outstore_add_con();
        ConnectResponse response=connect.DoTask(request);
        ServletUtils.resJsonString(resp, JSON.toJSONString(response));
    }
}

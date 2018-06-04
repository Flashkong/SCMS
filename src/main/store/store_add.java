package main.store;

import bean.StoreBean;
import com.alibaba.fastjson.JSON;
import fun.ConnectResponse;
import fun.store.store_add_con;
import utils.JsonUtils;
import utils.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "store_add", urlPatterns = {"/add_store"})
public class store_add extends HttpServlet {
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
        StoreBean request=(StoreBean) JsonUtils.parseJson(req, StoreBean.class);
        store_add_con connect=new store_add_con();
        ConnectResponse response=connect.DoTask(request);
        ServletUtils.resJsonString(resp, JSON.toJSONString(response));
    }
}

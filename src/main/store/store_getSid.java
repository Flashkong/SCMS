package main.store;

import com.alibaba.fastjson.JSON;
import fun.ConnectResponse;
import fun.store.store_get_Sid_con;
import utils.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "store_getSid", urlPatterns = {"/getSid"})
public class store_getSid extends HttpServlet {
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
        store_get_Sid_con connect=new store_get_Sid_con();
        ConnectResponse response=connect.DoTask();
        ServletUtils.resJsonString(resp, JSON.toJSONString(response));
    }
}

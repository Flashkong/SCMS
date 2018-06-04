package main.store;

import com.alibaba.fastjson.JSON;
import fun.ConnectResponse;
import fun.store.store_show_con;
import utils.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "store_show", urlPatterns = {"/show_store"})
public class store_show extends HttpServlet {
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
        int pageSize = Integer.parseInt(req.getParameter("pageSize"));
        int pageNumber = Integer.parseInt(req.getParameter("pageNumber"));

        store_show_con connect=new store_show_con();
        ConnectResponse response=connect.DoTask(pageSize,pageNumber);
        ServletUtils.resJsonString(resp, JSON.toJSONString(response));

    }
}

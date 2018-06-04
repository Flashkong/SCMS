package main.panstore;

import bean.PanStoreBean;
import com.alibaba.fastjson.JSON;
import fun.ConnectResponse;
import fun.panstore.panstore_add_con;
import utils.JsonUtils;
import utils.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "panstore_add", urlPatterns = {"/add_panstore"})
public class panstore_add extends HttpServlet {
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
        PanStoreBean request=(PanStoreBean) JsonUtils.parseJson(req, PanStoreBean.class);
        panstore_add_con connect=new panstore_add_con();
        ConnectResponse response=connect.DoTask(request);
        ServletUtils.resJsonString(resp, JSON.toJSONString(response));
    }
}

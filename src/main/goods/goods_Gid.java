package main.goods;

import com.alibaba.fastjson.JSON;
import fun.ConnectResponse;
import fun.goods.goods_get_Gid_con;
import utils.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "goods_Gid", urlPatterns = {"/getGid"})
public class goods_Gid extends HttpServlet {
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
        goods_get_Gid_con connect=new goods_get_Gid_con();
        ConnectResponse response=connect.DoTask();
        ServletUtils.resJsonString(resp, JSON.toJSONString(response));
    }
}

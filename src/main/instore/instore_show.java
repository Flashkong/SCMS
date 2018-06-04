package main.instore;


import bean.SearchBean;
import com.alibaba.fastjson.JSON;
import fun.ConnectResponse;
import fun.goods.goods_search_con;
import fun.instore.instore_search_con;
import fun.instore.instore_show_con;
import utils.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "instore_show", urlPatterns = {"/show_instore"})
public class instore_show extends HttpServlet {
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
        String search_data=req.getParameter("searchText");
        String  field=req.getParameter("name");

        if(search_data.equals("回车进行搜索")||search_data.equals("")){
            instore_show_con connect=new instore_show_con();
            ConnectResponse response=connect.DoTask(pageSize,pageNumber);
            ServletUtils.resJsonString(resp, JSON.toJSONString(response));
        }else {
            SearchBean bean=new SearchBean();
            bean.setField(field);
            bean.setSearch_data(search_data);
            bean.setPageNumber(pageNumber);
            bean.setPageSize(pageSize);
            instore_search_con connect=new instore_search_con();
            ConnectResponse response=connect.DoTask(bean);
            ServletUtils.resJsonString(resp, JSON.toJSONString(response));
        }
    }
}

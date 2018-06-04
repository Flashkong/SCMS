package main.profile;

import bean.ComputeBean;
import bean.StoreBean;
import com.alibaba.fastjson.JSON;
import fun.ConnectResponse;
import fun.profile.compute_profile_con;
import fun.store.store_add_con;
import utils.JsonUtils;
import utils.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "compute_profile", urlPatterns = {"/profile_compute"})
public class compute_profile extends HttpServlet {
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
        ComputeBean request=(ComputeBean) JsonUtils.parseJson(req, ComputeBean.class);
        compute_profile_con connect=new compute_profile_con();
        ConnectResponse response=connect.DoTask(request);
        ServletUtils.resJsonString(resp, JSON.toJSONString(response));
    }
}

package main;

import bean.LoginBean;
import bean.StoreBean;
import com.alibaba.fastjson.JSON;
import fun.ConnectResponse;
import fun.store.store_add_con;
import org.apache.kafka.common.security.auth.Login;
import utils.JsonUtils;
import utils.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "text", urlPatterns = {"/text"})
public class text extends HttpServlet {
    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        // 设置格式
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST");
        response.setHeader("Access-Control-Allow-Headers","x-requested-with,content-type");
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");

        // 获取客户端cookie
        request.setCharacterEncoding("utf-8");
        Cookie[] cookies = request.getCookies();
        if (cookies !=null ) {
            for (Cookie c : cookies) {
                System.out.println(c.getName() + "--->" + c.getValue());
            }
        }
        // 创建Cookie
        //cookie，如果重复创建，那么浏览器会记住最后依次创建的时候，从那时候开始计时
        //如果途中改变了cookie，那么，只要key不变，浏览器的value值就会刷新
        Cookie cookie = new Cookie("java", "lishuia");
        // 有效期,秒为单位
        cookie.setMaxAge(60*60);
        // 设置cookie
        response.addCookie(cookie);
        PrintWriter out = response.getWriter();
        out.println("<h1>" + "写入cookie成功" + "</h1>");

//        ServletUtils.resJsonString(resp, JSON.toJSONString(response));
    }
}

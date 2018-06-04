package fun.login;

import base.jdbc.Config;
import base.jdbc.JDBCBuild;
import bean.LoginBean;
import fun.ConnectResponse;

import java.sql.ResultSet;

public class LoginCon {
    private ConnectResponse response;
    public ConnectResponse DoTask(LoginBean bean){

        //mysql数据库连接
        JDBCBuild jdbcBuild = new JDBCBuild();

        if(!jdbcBuild.buildConnect(Config.DRIVER,Config.DBUrl+"?serverTimezone=GMT", Config.name,Config.password)){
            //如果链接不成功
            return getConnectResponse(jdbcBuild,500,"连接数据库失败！");
        }else {
            //如果连接成功
            ResultSet re=jdbcBuild.executeQuery(String.format("select name,pwd from users where name='%s'",bean.getUserID()));

            try {
                re.next();
                String a=re.getString("name");
                if(a.equals(bean.getUserID())){
                    if(re.getString("pwd").equals(bean.getPwd())){
                        return getConnectResponse(jdbcBuild,200,"登陆成功！");
                    }else {
                        return getConnectResponse(jdbcBuild,500,"密码错误！");
                    }
                }
            }catch (Exception e){
                return getConnectResponse(jdbcBuild,500,"不存在此用户！");
            }
        }
        return response;
    }

    private ConnectResponse getConnectResponse(JDBCBuild jdbcBuild,int code,String mess) {
        response=new ConnectResponse();
        jdbcBuild.close();
        response.setStatus(code);
        response.setMessage(mess);
        return response;
    }
}

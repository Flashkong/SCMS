package fun.register;

import base.jdbc.Config;
import base.jdbc.JDBCBuild;
import bean.RegisterBean;
import fun.ConnectResponse;

import java.sql.ResultSet;


public class RegisterCon{
    private ConnectResponse response;
    public ConnectResponse DoTask(RegisterBean bean){

        //mysql数据库连接
        JDBCBuild jdbcBuild = new JDBCBuild();

        if(!jdbcBuild.buildConnect(Config.DRIVER,Config.DBUrl+"?serverTimezone=GMT", Config.name,Config.password)){
            //如果链接不成功
            return getConnectResponse(jdbcBuild,500,"连接数据库失败！");
        }else {
            //如果连接成功
            ResultSet result=jdbcBuild.executeQuery("select mykey from privatekey");
            try {
                result.next();
                if(bean.getMykey().equals(result.getString("mykey"))){
                    ResultSet re=jdbcBuild.executeQuery(String.format("select name from users where name='%s'",bean.getUserID()));
                    re.next();
                    try {
                        String a=re.getString("name");
                        if(a.equals(bean.getUserID())){
                            return getConnectResponse(jdbcBuild,500,"用户已存在！");
                        }
                    }catch (Exception e){
                        //插入数据，包括账户和密码
                        jdbcBuild.executeUpdate("insert into users values('"+bean.getUserID()+"','"+bean.getUserPwd()+"','"+bean.getUserEmail()+"');");
                    }
                }
                else {
                    return getConnectResponse(jdbcBuild,500,"密匙错误！");
                }
            }catch (Exception e) {
                return getConnectResponse(jdbcBuild,500,"发生未知错误！");
            }finally {
                jdbcBuild.close();
            }

            return getConnectResponse(jdbcBuild,200,"注册成功！");
        }
    }

    private ConnectResponse getConnectResponse(JDBCBuild jdbcBuild, int code, String message) {
        response=new ConnectResponse();
        jdbcBuild.close();
        response.setStatus(code);
        response.setMessage(message);
        return response;
    }
}

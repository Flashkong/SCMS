package fun.modifypw;

import base.jdbc.Config;
import base.jdbc.JDBCBuild;
import bean.RegisterBean;
import fun.ConnectResponse;

import java.sql.ResultSet;

public class modify_pw_con {
    private ConnectResponse response;
    public ConnectResponse DoTask(RegisterBean bean){

        //mysql数据库连接
        JDBCBuild jdbcBuild = new JDBCBuild();

        if(!jdbcBuild.buildConnect(Config.DRIVER,Config.DBUrl+"?serverTimezone=GMT", Config.name,Config.password)){
            //如果链接不成功
            return getConnectResponse(jdbcBuild,500,"连接数据库失败！");
        }else {
            try {
                ResultSet re=jdbcBuild.executeQuery(String.format("select name from users where name='%s'",bean.getUserID()));
                re.next();
                String a=re.getString("name");
                if(a.equals(bean.getUserID())){
                    if( jdbcBuild.executeUpdate(String.format(
                            "update users set pwd='%s' where name='%s'",bean.getUserPwd(),bean.getUserID()
                    ))==-1){
                        return getConnectResponse(jdbcBuild,500,"修改失败！");
                    }else {
                        return getConnectResponse(jdbcBuild,200,"修改成功！");
                    }
                }else {
                    return getConnectResponse(jdbcBuild,500,"没有查询到相关用户！");
                }

            }catch (Exception e) {
                return getConnectResponse(jdbcBuild,500,"发生未知错误！");
            }finally {
                jdbcBuild.close();
            }
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

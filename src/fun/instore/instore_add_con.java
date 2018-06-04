package fun.instore;

import base.jdbc.Config;
import base.jdbc.JDBCBuild;
import bean.InstoreBean;
import fun.ConnectResponse;

import java.sql.ResultSet;

public class instore_add_con {
    private ConnectResponse response;
    public ConnectResponse DoTask(InstoreBean bean){
        //mysql数据库连接
        JDBCBuild jdbcBuild = new JDBCBuild();

        if(!jdbcBuild.buildConnect(Config.DRIVER,Config.DBUrl+"?serverTimezone=GMT", Config.name,Config.password)){
            //如果链接不成功
            return getConnectResponse(jdbcBuild,500,"连接数据库失败！");
        }else{
            try{
                if(jdbcBuild.executeUpdate(
                        String.format(
                                "insert into instore values ('%s','%s',%d,'%s','%s',%.4f)"
                                ,bean.getGid(),bean.getIid(),Integer.parseInt(bean.getInum()),bean.getIdate(),bean.getSid(),Double.parseDouble(bean.getIprice()))
                )==-1){
                    return getConnectResponse(jdbcBuild,500,"插入数据时出错！");
                }else {
                    //先查询stock里面有没有原有的记录
                    ResultSet re=jdbcBuild.executeQuery(
                            String.format("select count(*) num from (select * from stock where Gid='%s' and Sid='%s') aa"
                                    ,bean.getGid(),bean.getSid())
                    );
                    long a=0;
                    try {
                        re.next();
                        a=re.getLong("num");
                    }catch (Exception e){
                        return getConnectResponse(jdbcBuild,500,"插入数据时出错！");
                    }
                    if(a!=0){
                        if(jdbcBuild.executeUpdate(
                                String.format(
                                        "update stock set Snum=Snum+%d where Sid='%s' and Gid='%s'",
                                        Integer.parseInt(bean.getInum()),bean.getSid(),bean.getGid()
                                ))==-1){
                            return getConnectResponse(jdbcBuild,500,"插入数据时出错！");
                        }else {
                            return getConnectResponse(jdbcBuild,200,"插入成功！");
                        }
                    }else {
                        if(jdbcBuild.executeUpdate(
                                String.format(
                                        "insert into stock values('%s','%s',%d)",
                                        bean.getGid(),bean.getSid(),Integer.parseInt(bean.getInum()))
                        )==-1){
                            return getConnectResponse(jdbcBuild,500,"插入数据时出错！");
                        }else{
                            return getConnectResponse(jdbcBuild,200,"插入成功！");
                        }
                    }

                }
            }catch (Exception e){
                return getConnectResponse(jdbcBuild,500,"发生未知错误！");
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

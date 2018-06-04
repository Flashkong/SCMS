package fun.instore;

import base.jdbc.Config;
import base.jdbc.JDBCBuild;
import bean.InstoreBean;
import fun.ConnectResponse;

public class instore_modify_con {
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
                        String.format("update instore set Gid='%s',Sid='%s',Inum=%d,Idate='%s',Iprice=%.4f where Iid='%s'",
                                bean.getGid(),bean.getSid(),Integer.parseInt(bean.getInum()),bean.getIdate(),Double.parseDouble(bean.getIprice()),bean.getIid())
                )==-1){
                    return getConnectResponse(jdbcBuild,500,"修改数据时出错！");
                }else {
                    return getConnectResponse(jdbcBuild,200,"修改成功！");
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

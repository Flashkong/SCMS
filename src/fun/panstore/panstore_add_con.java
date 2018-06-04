package fun.panstore;

import base.jdbc.Config;
import base.jdbc.JDBCBuild;
import bean.PanStoreBean;
import fun.ConnectResponse;

public class panstore_add_con {
    private ConnectResponse response;
    public ConnectResponse DoTask(PanStoreBean bean){
        //mysql数据库连接
        JDBCBuild jdbcBuild = new JDBCBuild();

        if(!jdbcBuild.buildConnect(Config.DRIVER,Config.DBUrl+"?serverTimezone=GMT", Config.name,Config.password)){
            //如果链接不成功
            return getConnectResponse(jdbcBuild,500,"连接数据库失败！");
        }else{
            try{
                if(jdbcBuild.executeUpdate(
                        String.format("insert into panstore values ('%s','%s','%s',%d,'%s')",bean.getPid(),bean.getGid()
                                ,bean.getSid(),Integer.parseInt(bean.getPnum()),bean.getPdate())
                )==-1){
                    return getConnectResponse(jdbcBuild,500,"插入数据时出错！");
                }else {
                    return getConnectResponse(jdbcBuild,200,"插入成功！");
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

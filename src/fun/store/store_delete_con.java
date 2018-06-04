package fun.store;

import base.jdbc.Config;
import base.jdbc.JDBCBuild;
import bean.StoreBean;
import fun.ConnectResponse;

public class store_delete_con {
    private ConnectResponse response;
    public ConnectResponse DoTask(StoreBean bean) {
        //mysql数据库连接
        JDBCBuild jdbcBuild = new JDBCBuild();

        if (!jdbcBuild.buildConnect(Config.DRIVER, Config.DBUrl + "?serverTimezone=GMT", Config.name, Config.password)) {
            //如果链接不成功
            return getConnectResponse(jdbcBuild, 500, "连接数据库失败！");
        } else {
            try {
                if(jdbcBuild.executeUpdate(String.format("delete from store where Sid='%s'",bean.getSid()))==-1){
                    return getConnectResponse(jdbcBuild,500,"删除数据时出错！");
                }else {
                    return getConnectResponse(jdbcBuild,200,"删除成功！");
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

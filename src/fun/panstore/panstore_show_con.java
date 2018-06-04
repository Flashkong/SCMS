package fun.panstore;

import base.jdbc.Config;
import base.jdbc.JDBCBuild;
import fun.ConnectResponse;

import java.sql.ResultSet;

public class panstore_show_con {
    private ConnectResponse response;
    public ConnectResponse DoTask(int pageSize,int pageNumber){
        //mysql数据库连接
        JDBCBuild jdbcBuild = new JDBCBuild();

        if(!jdbcBuild.buildConnect(Config.DRIVER,Config.DBUrl+"?serverTimezone=GMT", Config.name,Config.password)){
            //如果链接不成功
            return getConnectResponse(jdbcBuild,500,"连接数据库失败！");
        }else{
            //如果连接成功
            long total=0;
            ResultSet re=jdbcBuild.executeQuery(
                    "select count(Pid) num from panstore"
            );
            try {
                re.next();
                total=re.getLong("num");
            }catch (Exception e){
                return getConnectResponse(jdbcBuild,500,"加载数据时出错！");
            }

            int []temp=compute(pageSize,pageNumber);
            ResultSet resultSet=jdbcBuild.executeQuery(
                    String.format("select * from panstore " +
                            " LIMIT %d,%d",temp[0],temp[1])
            );
            return new panstore_search_con().handleDataFromDataBase(jdbcBuild,resultSet,total);
        }
    }

    private ConnectResponse getConnectResponse(JDBCBuild jdbcBuild,int code,String mess) {
        response=new ConnectResponse();
        jdbcBuild.close();
        response.setStatus(code);
        response.setMessage(mess);
        return response;
    }

    private int[] compute(int pageSize,int pageNumber){
        return new int[]{pageSize*(pageNumber-1),pageSize};
    }
}

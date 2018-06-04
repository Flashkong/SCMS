package fun.stock;

import base.jdbc.Config;
import base.jdbc.JDBCBuild;
import fun.ConnectResponse;

import java.sql.ResultSet;

public class stock_show_con {
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
                    "select count(*) num from stock"
            );
            try {
                re.next();
                total=re.getLong("num");
            }catch (Exception e){
                return getConnectResponse(jdbcBuild,500,"加载数据时出错！");
            }

            int []temp=compute(pageSize,pageNumber);
            ResultSet resultSet=jdbcBuild.executeQuery(
                    String.format("select goodsinfo.Gid,Gname,Tname,Gproducer,store.Sid,Sname,Snum from goodsinfo,type,store,stock " +
                            "where goodsinfo.Tid=type.Tid and stock.Gid=goodsinfo.Gid " +
                            "and stock.Sid=store.Sid LIMIT %d,%d",temp[0],temp[1])
            );
            return  new stock_search_con().handleDataFromDataBase(jdbcBuild,resultSet,total);
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

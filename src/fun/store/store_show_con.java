package fun.store;

import base.jdbc.Config;
import base.jdbc.JDBCBuild;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import fun.ConnectResponse;

import java.sql.ResultSet;

public class store_show_con {
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
                    "select count(Sid) num from store"
            );
            try {
                re.next();
                total=re.getLong("num");
            }catch (Exception e){
                return getConnectResponse(jdbcBuild,500,"加载数据时出错！");
            }

            int []temp=compute(pageSize,pageNumber);
            ResultSet resultSet=jdbcBuild.executeQuery(
                    String.format("select Sid,Sname from store " +
                            " LIMIT %d,%d",temp[0],temp[1])
            );
            try {
                JSON array=new JSONArray();
                int i=0;
                while (resultSet.next()){
                    JSONObject jsonObject=new JSONObject();
                    jsonObject.put("Sid",resultSet.getString("Sid"));
                    jsonObject.put("Sname",resultSet.getString("Sname"));
                    ((JSONArray) array).add(i++,jsonObject);
                }
                String json = "{\"total\":" + total + ",\"rows\":" + JSON.toJSONString(array) + "}";
                return getConnectResponse(jdbcBuild,200,json);
            }catch (Exception e){
                return getConnectResponse(jdbcBuild,500,"加载数据时出错！");
            }
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

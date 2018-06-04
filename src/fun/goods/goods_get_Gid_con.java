package fun.goods;

import base.jdbc.Config;
import base.jdbc.JDBCBuild;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import fun.ConnectResponse;

import java.sql.ResultSet;

public class goods_get_Gid_con {
    private ConnectResponse response;
    public ConnectResponse DoTask(){
        //mysql数据库连接
        JDBCBuild jdbcBuild = new JDBCBuild();

        if(!jdbcBuild.buildConnect(Config.DRIVER,Config.DBUrl+"?serverTimezone=GMT", Config.name,Config.password)){
            //如果链接不成功
            return getConnectResponse(jdbcBuild,500,"连接数据库失败！");
        }else{
            //如果连接成功
            ResultSet resultSet=jdbcBuild.executeQuery(
                    "select Gid from goodsinfo "
            );
            try {
                JSON array=new JSONArray();
                int i=0;
                while (resultSet.next()){
                    JSONObject jsonObject=new JSONObject();
                    jsonObject.put("Gid",resultSet.getString("Gid"));
                    ((JSONArray) array).add(i++,jsonObject);
                }
                String json = JSON.toJSONString(array);
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
}

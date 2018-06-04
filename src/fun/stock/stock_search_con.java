package fun.stock;

import base.jdbc.Config;
import base.jdbc.JDBCBuild;
import bean.SearchBean;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import fun.ConnectResponse;

import java.sql.ResultSet;

public class stock_search_con {
    public ConnectResponse DoTask(SearchBean bean) {
        //mysql数据库连接
        JDBCBuild jdbcBuild = new JDBCBuild();

        if (!jdbcBuild.buildConnect(Config.DRIVER, Config.DBUrl + "?serverTimezone=GMT", Config.name, Config.password)) {
            //如果链接不成功
            return getConnectResponse(jdbcBuild, 500, "连接数据库失败！");
        } else {
            long total=0;
            int []temp=compute(bean);

            ResultSet re=jdbcBuild.executeQuery(
                    String.format("select count(*) num from (select goodsinfo.Gid,Gname,Tname,Gproducer,store.Sid,Sname,Snum from goodsinfo,type,store,stock " +
                                                               " where goodsinfo.Tid=type.Tid and stock.Gid=goodsinfo.Gid " +
                                                                "and stock.Sid=store.Sid ) aa where %s='%s' "
                    ,bean.getField(), bean.getSearch_data())
            );
            try {
                re.next();
                total=re.getLong("num");
            }catch (Exception e){
                return getConnectResponse(jdbcBuild,500,"搜索数据时出错！");
            }
            ResultSet resultSet=jdbcBuild.executeQuery(
                    String.format("select * from (select goodsinfo.Gid,Gname,Tname,Gproducer,store.Sid,Sname,Snum from goodsinfo,type,store,stock " +
                                    " where goodsinfo.Tid=type.Tid and stock.Gid=goodsinfo.Gid " +
                                    "and stock.Sid=store.Sid ) aa where %s='%s' LIMIT %d,%d"
                            ,bean.getField(), bean.getSearch_data(),temp[0],temp[1])
            );
            return handleDataFromDataBase(jdbcBuild,resultSet,total);
        }
    }

    private ConnectResponse getConnectResponse(JDBCBuild jdbcBuild, int code, String message) {
        ConnectResponse response;
        response=new ConnectResponse();
        jdbcBuild.close();
        response.setStatus(code);
        response.setMessage(message);
        return response;
    }

    private int[] compute(SearchBean bean){
        return new int[]{bean.getPageSize()*(bean.getPageNumber()-1),bean.getPageSize()};
    }

    ConnectResponse handleDataFromDataBase(JDBCBuild jdbcBuild, ResultSet resultSet, long total){
        try {
            JSON array=new JSONArray();
            int i=0;
            while (resultSet.next()){
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("Gid",resultSet.getString("Gid"));
                jsonObject.put("Gname",resultSet.getString("Gname"));
                jsonObject.put("Tname",resultSet.getString("Tname"));
                jsonObject.put("Gproducer",resultSet.getString("Gproducer"));
                jsonObject.put("Sid",resultSet.getString("Sid"));
                jsonObject.put("Sname",resultSet.getString("Sname"));
                jsonObject.put("Snum",resultSet.getString("Snum"));
                ((JSONArray) array).add(i++,jsonObject);
            }
            String json = "{\"total\":" + total + ",\"rows\":" + JSON.toJSONString(array) + "}";
            return getConnectResponse(jdbcBuild,200,json);
        }catch (Exception e){
            return getConnectResponse(jdbcBuild,500,"搜索数据时出错！");
        }
    }
}

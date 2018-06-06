package fun.profile;

import base.jdbc.Config;
import base.jdbc.JDBCBuild;
import bean.ComputeBean;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import fun.ConnectResponse;

import java.math.BigDecimal;
import java.sql.ResultSet;

public class compute_profile_con {
    private ConnectResponse response;
    public ConnectResponse DoTask(ComputeBean bean){
        //mysql数据库连接
        JDBCBuild jdbcBuild = new JDBCBuild();

        if(!jdbcBuild.buildConnect(Config.DRIVER,Config.DBUrl+"?serverTimezone=GMT", Config.name,Config.password)){
            //如果链接不成功
            return getConnectResponse(jdbcBuild,500,"连接数据库失败！");
        }else{
            try {
                double Incost=0,Ountget=0;
                ResultSet InresultSet = jdbcBuild.executeQuery(String.format(
                   "select Inum,Iprice from instore where Idate between '%s' and '%s' ",bean.getStartDate(),bean.getEndDate()
                ));
                while (InresultSet.next()){
                    Incost+=InresultSet.getLong("Inum")*InresultSet.getDouble("Iprice");
                }
                ResultSet OutresultSet = jdbcBuild.executeQuery(String.format(
                    "select Onum,Oprice from outstore where Odate between '%s' and '%s' ",bean.getStartDate(),bean.getEndDate()
                ));
                while (OutresultSet.next()){
                    Ountget+=OutresultSet.getLong("Onum")*OutresultSet.getDouble("Oprice");
                }
                Incost=new BigDecimal(Incost).setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();
                Ountget=new BigDecimal(Ountget).setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("Incost",Incost);
                jsonObject.put("Outget",Ountget);
                jsonObject.put("profile",Ountget-Incost);
                String json = JSON.toJSONString(jsonObject);
                return getConnectResponse(jdbcBuild,200,json);
            }catch (Exception e){
                return getConnectResponse(jdbcBuild,500,"计算时出现未知错误");
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

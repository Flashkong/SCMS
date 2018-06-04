package fun.outstore;

import base.jdbc.Config;
import base.jdbc.JDBCBuild;
import bean.OutStoreBean;
import fun.ConnectResponse;

import java.sql.ResultSet;

public class outstore_add_con {
    private ConnectResponse response;
    public ConnectResponse DoTask(OutStoreBean bean){
        //mysql数据库连接
        JDBCBuild jdbcBuild = new JDBCBuild();

        if(!jdbcBuild.buildConnect(Config.DRIVER,Config.DBUrl+"?serverTimezone=GMT", Config.name,Config.password)){
            //如果链接不成功
            return getConnectResponse(jdbcBuild,500,"连接数据库失败！");
        }else{
            try{
                //先查询stock里面有没有原有的记录,也就是库存有没有存余
                ResultSet re=jdbcBuild.executeQuery(
                        String.format("select count(*) num from (select * from stock where Gid='%s' and Sid='%s') aa"
                                ,bean.getGid(),bean.getSid())
                );
                long a=0;
                try {
                    re.next();
                    a=re.getLong("num");
                }catch (Exception e){
                    return getConnectResponse(jdbcBuild,500,"出库时出错！");
                }
                if(a!=0){
                    //如果在stock里面查询到了记录，库存不为0
                    //这时候检查库存的数量
                    ResultSet resultSet=jdbcBuild.executeQuery(String.format("select Snum from stock where Sid='%s' and Gid='%s'",
                            bean.getSid(),bean.getGid()));
                    long temp=0;
                    try {
                        resultSet.next();
                        temp=resultSet.getLong("Snum");
                    }catch (Exception e){
                        return getConnectResponse(jdbcBuild,500,"出库时出错！");
                    }
                    if(temp>=Integer.parseInt(bean.getOnum())){
                        //如果库存多余
                        if(jdbcBuild.executeUpdate(
                                String.format(
                                        "update stock set Snum=Snum-%d where Sid='%s' and Gid='%s'",
                                        Integer.parseInt(bean.getOnum()),bean.getSid(),bean.getGid()))==-1
                                ||
                            jdbcBuild.executeUpdate(String.format(
                                    "insert into outstore values ('%s','%s','%s',%d,'%s',%.4f)",
                                    bean.getOid(),bean.getGid(),bean.getSid(),Integer.parseInt(bean.getOnum()),bean.getOdate(),Double.parseDouble(bean.getOprice())
                            ))==-1
                                ){
                            return getConnectResponse(jdbcBuild,500,"出库时出错！");
                        }else {
                            return getConnectResponse(jdbcBuild,200,"出库成功！");
                        }
                    }else {
                        //如果库存不足
                        return getConnectResponse(jdbcBuild,500,"出库出错，出库数量大于库存数量！");
                    }

                }else {
                    //如果stock里面没有记录，库存为0
                    return getConnectResponse(jdbcBuild,500,"出库时出错，库存为0！");
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

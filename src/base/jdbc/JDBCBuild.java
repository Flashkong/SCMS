package base.jdbc;
import java.sql.*;

/**
 * @author tomstark
 */
public class JDBCBuild {
    private Connection connection = null;
    private Statement statement = null;

    public boolean buildConnect(String driver, String JDBCUrl, String userName, String userPwd){
        try{
            //初始化驱动程序，这样就可以打开与数据库的通信
            Class.forName(driver);
            //链接数据库
            connection = DriverManager.getConnection(JDBCUrl,userName,userPwd);
            statement = connection.createStatement();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    public int executeUpdate(String sql){
        try{
            return statement.executeUpdate(sql);
        }catch (SQLException e){
            return -1;
        }
    }
    public ResultSet executeQuery(String sql){
        try{
            return statement.executeQuery(sql);
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }
    public void close(){
        try{
            if (connection != null) {
                connection.close();
            }
            if (statement != null) {
                statement.close();
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

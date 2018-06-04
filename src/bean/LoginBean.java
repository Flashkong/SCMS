package bean;

import com.alibaba.fastjson.annotation.JSONField;

public class LoginBean {
    @JSONField(name = "name")
    private String userID;

    @JSONField(name = "pwd")
    private String pwd;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}

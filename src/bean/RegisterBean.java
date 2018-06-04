package bean;

import com.alibaba.fastjson.annotation.JSONField;

public class RegisterBean {
    @JSONField(name = "name")
    private String userID;


    @JSONField(name = "email")
    private String userEmail;

    @JSONField(name = "pwd")
    private String userPwd;

    @JSONField(name = "mykey")
    private String mykey;

    public String getMykey() {
        return mykey;
    }

    public void setMykey(String mykey) {
        this.mykey = mykey;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }
}

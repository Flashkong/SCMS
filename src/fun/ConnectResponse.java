package fun;

import com.alibaba.fastjson.annotation.JSONField;

public class ConnectResponse {
    @JSONField(name = "Status")
    private int status=200;         //"成功"和"失败"

    @JSONField(name ="message" )
    private String message="";

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatus(int status) {

        this.status = status;
    }
}

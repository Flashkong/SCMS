package utils;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Json响应实体
 * @Author zianglei
 */
public class Response {

    // 响应码，取值见Config.java
    @JSONField(name = "状态码")
    private int code = 200;
    // 提示信息，当返回正常结果的时候，此处为空
    @JSONField(name = "提示信息")
    private String errorInfo = "";


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }

}

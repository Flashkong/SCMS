package bean;

import com.alibaba.fastjson.annotation.JSONField;

public class StoreBean {
    @JSONField(name="Sid")
    private String Sid;

    public String getSid() {
        return Sid;
    }

    public void setSid(String sid) {
        Sid = sid;
    }

    public String getSname() {
        return Sname;
    }

    public void setSname(String sname) {
        Sname = sname;
    }

    @JSONField(name = "Sname")
    private String Sname;
}

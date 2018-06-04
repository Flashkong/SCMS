package bean;

import com.alibaba.fastjson.annotation.JSONField;

public class GoodsBean {
    @JSONField(name="Gid")
    private String Gid;

    @JSONField(name = "Gname")
    private String Gname;

    @JSONField(name = "Tname")
    private String Tname;

    @JSONField(name = "Gproducer")
    private String Gproducer;

    public String getGid() {
        return Gid;
    }

    public void setGid(String gid) {
        Gid = gid;
    }

    public String getGname() {
        return Gname;
    }

    public void setGname(String gname) {
        Gname = gname;
    }

    public String getTname() {
        return Tname;
    }

    public void setTname(String tname) {
        Tname = tname;
    }

    public String getGproducer() {
        return Gproducer;
    }

    public void setGproducer(String gproducer) {
        Gproducer = gproducer;
    }
}

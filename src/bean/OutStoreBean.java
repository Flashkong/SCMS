package bean;

import com.alibaba.fastjson.annotation.JSONField;

public class OutStoreBean {
    @JSONField(name = "Gid")
    private String Gid;

    @JSONField(name = "Oid")
    private String Oid;

    public String getGid() {
        return Gid;
    }

    public void setGid(String gid) {
        Gid = gid;
    }

    public String getOid() {
        return Oid;
    }

    public void setOid(String oid) {
        Oid = oid;
    }

    public String getSid() {
        return Sid;
    }

    public void setSid(String sid) {
        Sid = sid;
    }

    public String getOnum() {
        return Onum;
    }

    public void setOnum(String onum) {
        Onum = onum;
    }

    public String getOdate() {
        return Odate;
    }

    public void setOdate(String odate) {
        Odate = odate;
    }

    @JSONField(name = "Oprice")
    private String Oprice;

    public String getOprice() {
        return Oprice;
    }

    public void setOprice(String oprice) {
        Oprice = oprice;
    }

    @JSONField(name = "Sid")

    private String Sid;

    @JSONField(name = "Onum")
    private String Onum;

    @JSONField(name = "Odate")
    private String Odate;
}

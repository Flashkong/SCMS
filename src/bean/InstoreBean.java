package bean;

import com.alibaba.fastjson.annotation.JSONField;

public class InstoreBean {
    @JSONField(name = "Gid")
    private String Gid;

    @JSONField(name = "Iid")
    private String Iid;

    @JSONField(name = "Sid")
    private String Sid;

    @JSONField(name = "Inum")
    private String Inum;

    @JSONField(name = "Idate")
    private String Idate;

    public String getIprice() {
        return Iprice;
    }

    public void setIprice(String iprice) {
        Iprice = iprice;
    }

    @JSONField(name = "Iprice")

    private String Iprice;

    public String getGid() {
        return Gid;
    }

    public void setGid(String gid) {
        Gid = gid;
    }

    public String getIid() {
        return Iid;
    }

    public void setIid(String iid) {
        Iid = iid;
    }

    public String getSid() {
        return Sid;
    }

    public void setSid(String sid) {
        Sid = sid;
    }

    public String getInum() {
        return Inum;
    }

    public void setInum(String inum) {
        Inum = inum;
    }

    public String getIdate() {
        return Idate;
    }

    public void setIdate(String idate) {
        Idate = idate;
    }
}
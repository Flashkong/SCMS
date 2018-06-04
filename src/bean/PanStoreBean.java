package bean;

import com.alibaba.fastjson.annotation.JSONField;

public class PanStoreBean {
    @JSONField(name = "Gid")
    private String Gid;

    @JSONField(name = "Pid")
    private String Pid;

    @JSONField(name = "Sid")
    private String Sid;

    @JSONField(name = "Pnum")
    private String Pnum;

    @JSONField(name = "Pdate")
    private String Pdate;

    public String getGid() {
        return Gid;
    }

    public void setGid(String gid) {
        Gid = gid;
    }

    public String getPid() {
        return Pid;
    }

    public void setPid(String pid) {
        Pid = pid;
    }

    public String getSid() {
        return Sid;
    }

    public void setSid(String sid) {
        Sid = sid;
    }

    public String getPnum() {
        return Pnum;
    }

    public void setPnum(String pnum) {
        Pnum = pnum;
    }

    public String getPdate() {
        return Pdate;
    }

    public void setPdate(String pdate) {
        Pdate = pdate;
    }
}

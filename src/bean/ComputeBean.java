package bean;

import com.alibaba.fastjson.annotation.JSONField;

public class ComputeBean {
    @JSONField(name = "startDate")
    private String startDate;

    @JSONField(name = "endDate")
    private String endDate;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}

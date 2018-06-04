package bean;

import com.alibaba.fastjson.annotation.JSONField;

public class SearchBean {
    @JSONField(name="field")
    private String field;

    @JSONField(name = "search-data")
    private String search_data;

    @JSONField(name = "pageSize")
    private int pageSize;

    @JSONField(name = "pageNumber")
    private int pageNumber;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getSearch_data() {
        return search_data;
    }

    public void setSearch_data(String search_data) {
        this.search_data = search_data;
    }
}

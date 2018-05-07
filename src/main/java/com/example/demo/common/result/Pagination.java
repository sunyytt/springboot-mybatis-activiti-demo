package com.example.demo.common.result;


/**
 * ant design 1.3.0
 */
public class Pagination {
    //当前页数
    private int current;
    //每页的数量
    private int pageSize;
    //总记录数
    private long total;

    public Pagination() {
    }

    public Pagination(int current, int pageSize, long total) {
        this.current = current;
        this.pageSize = pageSize;
        this.total = total;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}

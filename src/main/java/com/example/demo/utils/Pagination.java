package com.example.demo.utils;


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

    private boolean success;

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
        if (current <= 0) {
            current = 1;
        }
        this.current = current;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        if (pageSize < 0) {
            pageSize = 0;
        }
        this.pageSize = pageSize;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}

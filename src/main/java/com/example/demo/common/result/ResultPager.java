package com.example.demo.common.result;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.util.List;

public class ResultPager<T>{
    //结果集
    private List<T> list;
    //分页
    private Pagination pagination;

    public ResultPager(List<T> list){
        PageInfo<T> pageInfo = new PageInfo<T>(list);
        this.pagination = new Pagination();
        this.pagination.setCurrent(pageInfo.getPageNum());
        this.pagination.setPageSize(pageInfo.getPageSize());
        this.pagination.setTotal(pageInfo.getTotal());
        this.list=list;
    }

    /**
     * setPager
     * @param current 当前页数
     * @param pageSize 每页的数量
     */
    public static void setPager(int current,int pageSize){
        PageHelper.startPage(current, pageSize);
    }
    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}

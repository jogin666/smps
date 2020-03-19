package com.zy.smps_admin_service.page;

import java.util.List;

/***
 * 分页的结果
 * @param <T> 分页的类型
 */
public class PageResult<T> {

    private int pageNum; // 当前页码

    private int pageSize;     // 每页数量

    private long totalSize;  //记录总数

    private int totalPages; //总页数

    private List<T> content;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }
}

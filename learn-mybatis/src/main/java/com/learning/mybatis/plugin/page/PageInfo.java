package com.learning.mybatis.plugin.page;

import java.io.Serializable;

/**
 * <p>
 *  分页对象
 * </p>
 *
 * @author harber
 * @version 1.0.0
 * @since 2022/2/26
 */
public class PageInfo implements Serializable {

    // 记录总数
    private Integer totalCount;

    // 每页记录数
    private Integer pageSize = 10;

    // 当前页
    private Integer currentPage = 1;

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    // 获取每页的第一条记录的索引
    public Integer getStart() {
        return (currentPage - 1) * pageSize;
    }
}

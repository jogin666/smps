package com.zy.smps_admin_service.page;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zy.smps_admin_service.model.PageParam;

import java.util.List;

/**
 * 获取分页信息
 */
public class PageBuilder {

    //将插件分好的分页信息封装 PageResult
    public static <T> PageResult<T> getPageResult(PageInfo<T> pageInfo){
        PageResult<T> pageResult = new PageResult<>();
        pageResult.setPageSize(pageInfo.getPageSize());
        pageResult.setPageNum(pageInfo.getPageNum());
        pageResult.setTotalSize(pageInfo.getTotal());
        pageResult.setTotalPages(pageInfo.getPages());
        pageResult.setContent(pageInfo.getList());
        return pageResult;
    }

    //分页
    public static <T> PageResult<T> getPageResult(PageParam pageParam, List<T> messages){
        try {
            int pageNum= pageParam.getPage();
            int pageSize= pageParam.getLimit();
            PageHelper.startPage(pageNum, pageSize);
        }catch (NullPointerException e){
            PageHelper.startPage(1, PageParam.PAGE_NUM);
        }
        return getPageResult(new PageInfo<>(messages));
    }
}

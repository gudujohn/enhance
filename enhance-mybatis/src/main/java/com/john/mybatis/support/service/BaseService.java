package com.john.mybatis.support.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.john.mybatis.criteria.QueryCriteria;
import com.john.mybatis.vo.Model;

/**
 * 通用服务接口
 * 
 * @author JiangGengchao
 * @date 2018年2月4日
 */
public interface BaseService<T extends Model> {

    Integer create(T model);

    Integer deleteById(String id);

    Integer updateSelective(T model);

    T findById(String id);

    List<T> findAll();

    List<T> find(QueryCriteria criteria);

    PageInfo<T> findWithPagination(QueryCriteria criteria, Integer pageNum, Integer pageSize);

}

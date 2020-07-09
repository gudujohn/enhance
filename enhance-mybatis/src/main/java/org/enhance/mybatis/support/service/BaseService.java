package org.enhance.mybatis.support.service;

import java.util.List;

import org.enhance.mybatis.criteria.QueryCriteria;
import org.enhance.mybatis.vo.Model;

import com.github.pagehelper.PageInfo;

/**
 * 通用服务接口
 * 
 * @author JiangGengchao
 * @date 2018年2月4日
 */
public interface BaseService<T extends Model> {

	Integer create(T model);

	Integer deleteById(long id);

	Integer update(T model);

	Integer count(QueryCriteria queryCriteria);

	T findById(long id);

	T findOne(QueryCriteria criteria);

	List<T> findAll();

	List<T> find(QueryCriteria criteria);

	PageInfo<T> findWithPagination(QueryCriteria criteria, Integer pageNum, Integer pageSize);

}

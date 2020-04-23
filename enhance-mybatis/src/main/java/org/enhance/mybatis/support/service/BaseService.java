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

	Integer updateSelective(T model);

	T findById(long id);

	List<T> findAll();

	List<T> find(QueryCriteria criteria);

	PageInfo<T> findWithPagination(QueryCriteria criteria, Integer pageNum, Integer pageSize);

}

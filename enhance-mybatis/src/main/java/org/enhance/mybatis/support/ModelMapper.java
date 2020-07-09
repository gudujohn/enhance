package org.enhance.mybatis.support;

import java.util.List;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.enhance.mybatis.criteria.QueryCriteria;
import org.enhance.mybatis.vo.Model;

/**
 * Sql提供者支持
 * 
 * @author JiangGengchao
 * @date 2018年3月16日
 */
public interface ModelMapper<T> {

	/**
	 * 新增
	 * 
	 * @param model
	 * @return
	 */
	@InsertProvider(type = SqlProvider.class, method = "create")
	Integer create(Model model);

	/**
	 * 通过id删除
	 * 
	 * @param clazz
	 * @param id
	 * @return
	 */
	@DeleteProvider(type = SqlProvider.class, method = "deleteById")
	Integer deleteById(Class<T> clazz, long id);

	/**
	 * 全属性更新
	 * 
	 * @param model
	 * @return
	 */
	@UpdateProvider(type = SqlProvider.class, method = "update")
	Integer update(Model model);

	/**
	 * 增量更新
	 * 
	 * @param model
	 * @return
	 */
	@UpdateProvider(type = SqlProvider.class, method = "updateSelective")
	Integer updateSelective(Model model);

	/**
	 * 计数
	 *
	 * @param queryCriteria
	 * @return
	 */
	@SelectProvider(type = SqlProvider.class, method = "count")
	Integer count(QueryCriteria queryCriteria);

	/**
	 * 通过id查询
	 * 
	 * @param clazz
	 * @param id
	 * @return
	 */
	@SelectProvider(type = SqlProvider.class, method = "findById")
	T findById(Class<T> clazz, long id);

	/**
	 * 通过ids查询
	 * 
	 * @param clazz
	 * @param ids
	 * @return
	 */
	@SelectProvider(type = SqlProvider.class, method = "findByIds")
	List<T> findByIds(Class<T> clazz, long[] ids);

	/**
	 * 查询全部
	 * 
	 * @param clazz
	 * @return
	 */
	@SelectProvider(type = SqlProvider.class, method = "findAll")
	List<T> findAll(Class<T> clazz);

	/**
	 * 条件查询
	 * 
	 * @param clazz
	 * @param queryCriteria
	 * @param ordering
	 * @return
	 */
	@SelectProvider(type = SqlProvider.class, method = "find")
	List<T> find(QueryCriteria queryCriteria);

}

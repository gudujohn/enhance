package org.enhance.mybatis.support.service.impl;

import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.enhance.common.util.Detect;
import org.enhance.common.util.InternalAssertion;
import org.enhance.common.util.ListUtil;
import org.enhance.mybatis.criteria.QueryCriteria;
import org.enhance.mybatis.support.ModelMapper;
import org.enhance.mybatis.support.service.BaseService;
import org.enhance.mybatis.vo.LifeCycleModel;
import org.enhance.mybatis.vo.Model;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 基础service实现
 * 
 * @author JiangGengchao
 * @date 2018年2月5日
 */
public abstract class BaseServiceImpl<T extends Model> implements BaseService<T> {

	@Autowired
	protected ModelMapper<T> mapper;

	@Override
	public Integer create(T model) {
		if (model instanceof LifeCycleModel) {
			LifeCycleModel lifeCycleModel = (LifeCycleModel) model;
			if (Detect.isNull(lifeCycleModel.getCreateDate())) {
				lifeCycleModel.setCreateDate(new Date());
			}
			if (Detect.isNull(lifeCycleModel.getUpdateDate())) {
				lifeCycleModel.setUpdateDate(new Date());
			}
			if (Detect.isEmpty(lifeCycleModel.getCreator())) {
				lifeCycleModel.setCreator("系统管理员");
			}
			if (Detect.isEmpty(lifeCycleModel.getUpdater())) {
				lifeCycleModel.setUpdater("系统管理员");
			}
		}
		return this.mapper.create(model);
	}

	@Override
	public Integer deleteById(long id) {
		return this.mapper.deleteById(this.getTClass(), id);
	}

	@Override
	public Integer update(T model) {
		if (model instanceof LifeCycleModel) {
			((LifeCycleModel) model).setUpdateDate(new Date());
		}
		return this.mapper.updateSelective(model);
	}

	@Override
	public Integer count(QueryCriteria queryCriteria) {
		return this.mapper.count(queryCriteria);
	}

	@Override
	public T findById(long id) {
		return this.mapper.findById(this.getTClass(), id);
	}

	@Override
	public T findOne(QueryCriteria criteria) {
		List<T> records = this.mapper.find(criteria);
		InternalAssertion.isTrue(Detect.isEmpty(records) || records.size() == 1, "found multiple records.");
		return ListUtil.firstOne(records);
	}

	@Override
	public List<T> findAll() {
		return this.mapper.findAll(this.getTClass());
	}

	@Override
	public List<T> find(QueryCriteria criteria) {
		return this.mapper.find(criteria);
	}

	@Override
	public PageInfo<T> findWithPagination(QueryCriteria criteria, Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		if (Objects.isNull(criteria)) {
			criteria = new QueryCriteria(getTClass());
		}
		return new PageInfo<>(this.mapper.find(criteria));
	}

	@SuppressWarnings("unchecked")
	protected Class<T> getTClass() {
		Class<T> tClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		return tClass;
	}

}

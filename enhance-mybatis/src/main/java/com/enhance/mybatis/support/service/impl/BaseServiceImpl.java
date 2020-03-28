package com.enhance.mybatis.support.service.impl;

import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;

import com.enhance.common.util.Detect;
import com.enhance.common.util.UuidUtil;
import com.enhance.mybatis.criteria.QueryCriteria;
import com.enhance.mybatis.support.ModelMapper;
import com.enhance.mybatis.support.service.BaseService;
import com.enhance.mybatis.vo.LifeCycleModel;
import com.enhance.mybatis.vo.Model;
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
		if (!Detect.notEmpty(model.getId())) {
			model.setId(UuidUtil.generateUuid());
		}
		if (model instanceof LifeCycleModel) {
			((LifeCycleModel) model).setCreateDate(new Date());
			((LifeCycleModel) model).setUpdateDate(new Date());
		}
		return this.mapper.create(model);
	}

	@Override
	public Integer deleteById(String id) {
		return this.mapper.deleteById(this.getTClass(), id);
	}

	@Override
	public Integer updateSelective(T model) {
		if (model instanceof LifeCycleModel) {
			((LifeCycleModel) model).setUpdateDate(new Date());
		}
		return this.mapper.updateSelective(model);
	}

	@Override
	public T findById(String id) {
		return this.mapper.findById(this.getTClass(), id);
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

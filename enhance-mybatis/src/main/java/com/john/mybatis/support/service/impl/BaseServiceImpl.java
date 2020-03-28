package com.john.mybatis.support.service.impl;

import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.john.mybatis.criteria.QueryCriteria;
import com.john.mybatis.support.ModelMapper;
import com.john.mybatis.support.service.BaseService;
import com.john.mybatis.util.MybatisDetect;
import com.john.mybatis.util.UuidUtil;
import com.john.mybatis.vo.LifeCycleModel;
import com.john.mybatis.vo.Model;

/** 基础service实现
 * 
 * @author JiangGengchao
 * @date 2018年2月5日 */
public abstract class BaseServiceImpl<T extends Model> implements BaseService<T> {

    @Autowired
    protected ModelMapper<T> mapper;

    @Override
    public Integer create(T model) {
        if (!MybatisDetect.notEmpty(model.getId())) {
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

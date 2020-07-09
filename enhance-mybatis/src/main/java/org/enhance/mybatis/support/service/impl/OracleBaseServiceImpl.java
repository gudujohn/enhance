package org.enhance.mybatis.support.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.enhance.common.util.Detect;
import org.enhance.common.util.InternalAssertion;
import org.enhance.common.util.ObjectUtil;
import org.enhance.mybatis.support.service.OracleBaseService;
import org.enhance.mybatis.vo.LifeCycleModel;
import org.enhance.mybatis.vo.Model;
import org.enhance.mybatis.vo.OracleModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 * 基础oracle service实现
 *
 * @author JiangGengchao
 * @date 2020年07月09日
 */
public abstract class OracleBaseServiceImpl<T extends Model> extends BaseServiceImpl<T> implements OracleBaseService<T> {

	@Autowired(required = false)
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

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
		if (model instanceof OracleModel) {
			String sequenceName = ((OracleModel) model).getSequence();
			long id = sequence(sequenceName);
			model.setId(id);
		}
		return this.mapper.create(model);
	}

	private long sequence(String sequenceName) {
		String namedStatement = "select " + sequenceName + ".nextval from dual";
		List<Map<String, Object>> records = namedParameterJdbcTemplate.queryForList(namedStatement, new HashMap<>());
		InternalAssertion.isTrue(Detect.notEmpty(records) && records.size() <= 1, "Must have only one record");
		Object obj = null;
		for (Map.Entry<String, Object> entry : records.get(0).entrySet()) {
			obj = entry.getValue();
		}
		return ObjectUtil.asPrimitiveLong(obj);
	}
}

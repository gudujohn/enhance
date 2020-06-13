package org.enhance.mybatis.util;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.enhance.common.util.Detect;
import org.enhance.mybatis.constant.SqlConst;
import org.enhance.mybatis.criteria.QueryCriteria;
import org.enhance.mybatis.criteria.criterion.Restrictions;
import org.enhance.mybatis.vo.Model;

public class QueryCriteriaUtil {

	public static QueryCriteria generateQueryCriteria(Map<String, String> formParams, Class<? extends Model> modelClass) {
		QueryCriteria queryCriteria = new QueryCriteria(modelClass);
		if (Detect.notEmpty(formParams)) {
			for (Map.Entry<String, String> formParamEntry : formParams.entrySet()) {
				String originalKey = getOriginalKey(formParamEntry.getKey());
				String suffixKey = getSuffixKey(formParamEntry.getKey());
				String value = formParamEntry.getValue();
				switch (suffixKey) {
				case SqlConst.PROPERTYNAMESUFFIX_LIKE:
					queryCriteria.add(Restrictions.like(originalKey, value));
					break;
				case SqlConst.PROPERTYNAMESUFFIX_ISNULL:
					if (Boolean.valueOf(value)) {
						queryCriteria.add(Restrictions.isNull(originalKey));
					} else {
						queryCriteria.add(Restrictions.isNotNull(originalKey));
					}
					break;
				case SqlConst.PROPERTYNAMESUFFIX_ISNOTNULL:
					if (Boolean.valueOf(value)) {
						queryCriteria.add(Restrictions.isNotNull(originalKey));
					} else {
						queryCriteria.add(Restrictions.isNull(originalKey));
					}
					break;
				case SqlConst.PROPERTYNAMESUFFIX_NOTIN:
					queryCriteria.add(Restrictions.notIn(originalKey, value.split(",")));
					break;
				case SqlConst.PROPERTYNAMESUFFIX_IN:
					queryCriteria.add(Restrictions.in(originalKey, value.split(",")));
					break;
				case SqlConst.PROPERTYNAMESUFFIX_NE:
					queryCriteria.add(Restrictions.ne(originalKey, value));
					break;
				case SqlConst.PROPERTYNAMESUFFIX_LT:
					queryCriteria.add(Restrictions.lt(originalKey, value));
					break;
				case SqlConst.PROPERTYNAMESUFFIX_GT:
					queryCriteria.add(Restrictions.gt(originalKey, value));
					break;
				case SqlConst.PROPERTYNAMESUFFIX_LE:
					queryCriteria.add(Restrictions.le(originalKey, value));
					break;
				case SqlConst.PROPERTYNAMESUFFIX_DESC:
					queryCriteria.addDesc(originalKey);
					break;
				case SqlConst.PROPERTYNAMESUFFIX_ASC:
					queryCriteria.addAsc(originalKey);
					break;
				default:
					queryCriteria.add(Restrictions.eq(originalKey, value));
				}
			}
		}
		return queryCriteria;
	}

	public static String getSuffixKey(String key) {
		String suffixKey = StringUtils.EMPTY;
		// @formatter:off
		String[] suffixes = {
				SqlConst.PROPERTYNAMESUFFIX_LIKE,
				SqlConst.PROPERTYNAMESUFFIX_ISNULL,
				SqlConst.PROPERTYNAMESUFFIX_ISNOTNULL,
				SqlConst.PROPERTYNAMESUFFIX_NOTIN,
				SqlConst.PROPERTYNAMESUFFIX_IN,
				SqlConst.PROPERTYNAMESUFFIX_NE,
				SqlConst.PROPERTYNAMESUFFIX_LT,
				SqlConst.PROPERTYNAMESUFFIX_GT,
				SqlConst.PROPERTYNAMESUFFIX_LE,
				SqlConst.PROPERTYNAMESUFFIX_GE,
				SqlConst.PROPERTYNAMESUFFIX_DESC,
				SqlConst.PROPERTYNAMESUFFIX_ASC
		};
		// @formatter:on
		for (String suffix : suffixes) {
			if (key.endsWith(suffix)) {
				suffixKey = suffix;
				break;
			}
		}
		return suffixKey;
	}

	public static String getOriginalKey(String key) {
		String originalKey = key;
		// @formatter:off
		String[] suffixes = {
				SqlConst.PROPERTYNAMESUFFIX_LIKE,
				SqlConst.PROPERTYNAMESUFFIX_ISNULL,
				SqlConst.PROPERTYNAMESUFFIX_ISNOTNULL,
				SqlConst.PROPERTYNAMESUFFIX_NOTIN,
				SqlConst.PROPERTYNAMESUFFIX_IN,
				SqlConst.PROPERTYNAMESUFFIX_NE,
				SqlConst.PROPERTYNAMESUFFIX_LT,
				SqlConst.PROPERTYNAMESUFFIX_GT,
				SqlConst.PROPERTYNAMESUFFIX_LE,
				SqlConst.PROPERTYNAMESUFFIX_GE,
				SqlConst.PROPERTYNAMESUFFIX_DESC,
				SqlConst.PROPERTYNAMESUFFIX_ASC
		};
		// @formatter:on
		for (String suffix : suffixes) {
			if (key.endsWith(suffix)) {
				originalKey = StringUtils.removeEnd(key, suffix);
				break;
			}
		}
		return originalKey;
	}
}

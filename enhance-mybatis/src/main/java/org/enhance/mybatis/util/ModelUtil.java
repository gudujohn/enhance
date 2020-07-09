package org.enhance.mybatis.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.enhance.common.exception.InternalAssertionException;
import org.enhance.common.util.Detect;
import org.enhance.common.util.ObjectUtil;
import org.enhance.mybatis.vo.Model;

public class ModelUtil {

	private ModelUtil() {
		throw new IllegalStateException("Utility class");
	}

	private static final String ERROR_FORMAT_GET_PROPERTY = "getProperty() error, class=%s, propertyName=%s";

	public static long idOf(Model model) {
		return model == null ? 0 : model.getId();
	}

	public static <T extends Model> long[] idOf(List<T> models) {
		long[] ids = ArrayUtils.EMPTY_LONG_ARRAY;
		if (Detect.notEmpty(models)) {
			for (T model : models) {
				long id = model.getId();
				if (!ArrayUtils.contains(ids, id)) {
					ids = ArrayUtils.add(ids, id);
				}
			}
		}
		return ids;
	}

	public static <T extends Model> Map<Long, T> convertToLongId2ModelMap(List<T> models) {
		if (Detect.notEmpty(models)) {
			Map<Long, T> result = new HashMap<>();
			for (T model : models) {
				if (model != null && Detect.isPositive(model.getId())) {
					result.put(model.getId(), model);
				}
			}
			return result;
		}
		return new HashMap<>();
	}

	public static <T extends Model> long getForeignKeyValue(T model, String foreignKeyCode) {
		Object foreignKeyObject = null;
		try {
			foreignKeyObject = PropertyUtils.getProperty(model, foreignKeyCode);
		} catch (Exception ex) {
			String errorMessage = String.format(ERROR_FORMAT_GET_PROPERTY, model.getClass().getSimpleName(), foreignKeyCode);
			throw new InternalAssertionException(errorMessage, ex);
		}
		return ObjectUtil.asPrimitiveLong(foreignKeyObject);
	}

	public static <T extends Model> Map<Long, List<T>> groupByForeignKeyCode(List<T> entities, String foreignKeyCode) {
		Map<Long, List<T>> entityGroupMap = new LinkedHashMap<>();
		if (Detect.notEmpty(entities)) {
			for (T t : entities) {
				long foreignKeyValue = getForeignKeyValue(t, foreignKeyCode);
				if (Detect.isPositive(foreignKeyValue)) {
					if (!entityGroupMap.containsKey(foreignKeyValue)) {
						entityGroupMap.put(foreignKeyValue, new ArrayList<>());
					}
					entityGroupMap.get(foreignKeyValue).add(t);
				}
			}
		}
		return entityGroupMap;
	}

	public static <T extends Model> List<T> constructList(T model) {
		List<T> list = new ArrayList<>();
		if (model != null) {
			list.add(model);
		}
		return list;
	}

	public static <T extends Model> long[] getForeignKeyValues(Collection<T> models, String foreignKeyCode) {
		if (Detect.notEmpty(models)) {
			long[] resultArray = new long[0];
			for (T model : models) {
				long foreignKeyValue = getForeignKeyValue(model, foreignKeyCode);
				if (Detect.isPositive(foreignKeyValue) && !ArrayUtils.contains(resultArray, foreignKeyValue)) {
					resultArray = ArrayUtils.add(resultArray, foreignKeyValue);
				}
			}
			return resultArray;
		}
		return ArrayUtils.EMPTY_LONG_ARRAY;
	}

	@SuppressWarnings("unchecked")
	public static <T extends Model> Class<T> getModelClassBySimpleName(String modelSimpleName) {
		String className = "com.irm.model." + modelSimpleName;
		try {
			return (Class<T>) Class.forName(className);
		} catch (ClassNotFoundException e) {
			throw new InternalAssertionException(e);
		}
	}

	public static <T extends Model> List<T> unionList(List<T> list1, List<T> list2) {
		Map<Long, T> map = convertToLongId2ModelMap(list1);
		if (Detect.notEmpty(list2)) {
			for (T t : list2) {
				map.put(t.getId(), t);
			}
		}
		return new ArrayList<>(map.values());
	}

	public static <T extends Model> String joinStringProperty(List<T> models, String propertyName) {
		if (Detect.notEmpty(models)) {
			List<String> values = new ArrayList<>();
			for (T model : models) {
				String value;
				try {
					value = ObjectUtil.asString(PropertyUtils.getProperty(model, propertyName));
				} catch (Exception e) {
					String errorMessage = String.format(ERROR_FORMAT_GET_PROPERTY, model.getClass().getSimpleName(), propertyName);
					throw new InternalAssertionException(errorMessage, e);
				}
				if (Detect.notEmpty(value)) {
					values.add(value);
				}
			}
			return StringUtils.join(values, ",");
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static <T extends Model, S> Set<S> getAttributeValueSet(Collection<T> models, String attributeCode) {
		Set<S> resultSet = new LinkedHashSet<>();
		if (Detect.notEmpty(models)) {
			for (T model : models) {
				try {
					resultSet.add((S) PropertyUtils.getProperty(model, attributeCode));
				} catch (Exception ex) {
					String errorMessage = String.format(ERROR_FORMAT_GET_PROPERTY, model.getClass().getSimpleName(), attributeCode);
					throw new InternalAssertionException(errorMessage, ex);
				}
			}
		}
		return resultSet;
	}

	@SuppressWarnings("unchecked")
	public static <T extends Model, S> Set<S> getAttributeValueSet(Collection<T> models, String... attributeCodes) {
		Set<S> resultSet = new LinkedHashSet<>();
		if (Detect.notEmpty(models)) {
			for (T model : models) {
				for (String attributeCode : attributeCodes) {
					try {
						resultSet.add((S) PropertyUtils.getProperty(model, attributeCode));
					} catch (Exception ex) {
						String errorMessage = String.format(ERROR_FORMAT_GET_PROPERTY, model.getClass().getSimpleName(), attributeCode);
						throw new InternalAssertionException(errorMessage, ex);
					}
				}
			}
		}
		return resultSet;
	}

	public static <T extends Model> List<Long> idList(List<T> models) {
		long[] ids = idOf(models);
		return Arrays.asList(ArrayUtils.toObject(ids));
	}

	public static <T extends Model> T getById(List<T> models, long id) {
		if (Detect.notEmpty(models) && Detect.isPositive(id)) {
			for (T model : models) {
				if (id == model.getId()) {
					return model;
				}
			}
		}
		return null;
	}

	public static <T extends Model> List<T> getByIds(List<T> models, long[] ids) {
		if (Detect.isEmpty(models)) {
			return new ArrayList<>();
		}

		if (Detect.isEmpty(ids)) {
			return models;
		}

		List<T> result = new LinkedList<>();
		for (T model : models) {
			if (ArrayUtils.contains(ids, model.getId())) {
				result.add(model);
			}
		}
		return result;
	}

	public static <T extends Model> void sortByIds(List<T> models, long[] ids) {
		if (Detect.notEmpty(models)) {
			models.sort((t1, t2) -> {
				CompareToBuilder compareToBuilder = new CompareToBuilder();
				long index1 = ArrayUtils.indexOf(ids, t1.getId());
				long index2 = ArrayUtils.indexOf(ids, t2.getId());
				compareToBuilder.append(index1, index2);
				return compareToBuilder.toComparison();
			});
		}
	}

}
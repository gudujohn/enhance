package com.john.mybatis.support;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.builder.BuilderException;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.type.JdbcType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.john.mybatis.annotation.ModelColumn;
import com.john.mybatis.annotation.ModelIgnore;
import com.john.mybatis.constant.SqlConst;
import com.john.mybatis.criteria.QueryCriteria;
import com.john.mybatis.util.AnnotationUtil;
import com.john.mybatis.util.AssertionUtil;
import com.john.mybatis.util.MybatisDetect;
import com.john.mybatis.util.MybatisStringUtil;
import com.john.mybatis.util.ReflectionUtil;
import com.john.mybatis.vo.Model;

/**
 * Sql提供者支持
 * 
 * @author JiangGengchao
 * @date 2018年3月16日
 */
public class SqlProvider implements SqlConst {

    private Logger logger = LoggerFactory.getLogger(SqlProvider.class);

    public String create(Model model) {
        SQL sql = new SQL().INSERT_INTO(AnnotationUtil.getTableName(model.getClass()));
        Field[] fields = ReflectionUtil.getDeclaredField(model.getClass());
        List<String> columns = new ArrayList<>();
        List<String> propertys = new ArrayList<>();
        if (fields != null) {
            for (Field field : fields) {
                if (!Modifier.toString(field.getModifiers()).contains("public") && !Modifier.toString(field.getModifiers()).contains("static")) {
                    String columnName = MybatisStringUtil.EMPTY;
                    String property = MybatisStringUtil.EMPTY;
                    boolean hasColumn = field.isAnnotationPresent(ModelColumn.class);
                    boolean hasIgnore = field.isAnnotationPresent(ModelIgnore.class);
                    if (hasColumn) {
                        ModelColumn columnAnnotation = field.getAnnotation(ModelColumn.class);
                        columnName = columnAnnotation.columnName();
                        property = field.getName();
                    } else if (hasIgnore) {
                        continue;
                    } else {
                        columnName = field.getName();
                        property = field.getName();
                    }
                    if (StringUtils.isNotBlank(property) && StringUtils.isNotBlank(columnName)) {
                        columns.add(columnName);
                        propertys.add("#{" + property + "}");
                    }
                }
            }
        }
        return sql.VALUES(MybatisStringUtil.join(columns, ","), MybatisStringUtil.join(propertys, ",")).toString();
    }

    public String updateSelective(Model model) {
        SQL sql = new SQL().UPDATE(AnnotationUtil.getTableName(model.getClass()));
        Field[] fields = ReflectionUtil.getDeclaredField(model.getClass());
        List<String> setSql = new ArrayList<>();
        if (fields != null) {
            for (Field field : fields) {
                if (!Modifier.toString(field.getModifiers()).contains("public") && !Modifier.toString(field.getModifiers()).contains("static")) {
                    String columnName = MybatisStringUtil.EMPTY;
                    String property = MybatisStringUtil.EMPTY;
                    boolean hasColumn = field.isAnnotationPresent(ModelColumn.class);
                    boolean hasIgnore = field.isAnnotationPresent(ModelIgnore.class);
                    if (Objects.equals(field.getName().toUpperCase(), "VERSION")) {
                        setSql.add("version = version+1");
                    } else if (hasColumn) {
                        ModelColumn columnAnnotation = field.getAnnotation(ModelColumn.class);
                        columnName = columnAnnotation.columnName();
                        property = field.getName();
                    } else if (hasIgnore) {
                        continue;
                    } else {
                        columnName = field.getName();
                        property = field.getName();
                    }
                    try {
                        field.setAccessible(true);
                        if (StringUtils.isNotBlank(property) && StringUtils.isNotBlank(columnName) && null != field.get(model)) {
                            setSql.add(columnName + "= #{" + property + "}");
                        }
                    } catch (Exception e) {
                        logger.error(e.getMessage(), e);
                    }
                }
            }
        }
        return sql.SET(MybatisStringUtil.join(setSql, ",")).WHERE("ID = #{id}").toString();
    }

    public String update(Model model) {
        SQL sql = new SQL().UPDATE(AnnotationUtil.getTableName(model.getClass()));
        Field[] fields = ReflectionUtil.getDeclaredField(model.getClass());
        List<String> setSql = new ArrayList<>();
        if (fields != null) {
            for (Field field : fields) {
                if (!Modifier.toString(field.getModifiers()).contains("public") && !Modifier.toString(field.getModifiers()).contains("static")) {
                    String columnName = MybatisStringUtil.EMPTY;
                    String property = MybatisStringUtil.EMPTY;
                    boolean hasColumn = field.isAnnotationPresent(ModelColumn.class);
                    boolean hasIgnore = field.isAnnotationPresent(ModelIgnore.class);
                    if (Objects.equals(field.getName().toUpperCase(), "VERSION")) {
                        setSql.add("version = version+1");
                    } else if (hasColumn) {
                        ModelColumn columnAnnotation = field.getAnnotation(ModelColumn.class);
                        columnName = columnAnnotation.columnName();
                        property = field.getName();
                    } else if (hasIgnore) {
                        continue;
                    } else {
                        columnName = field.getName();
                        property = field.getName();
                    }
                    try {
                        field.setAccessible(true);
                        if (StringUtils.isNotBlank(property) && StringUtils.isNotBlank(columnName)) {
                            setSql.add(columnName + "= #{" + property + "}");
                        }
                    } catch (Exception e) {
                        logger.error(e.getMessage(), e);
                    }
                }
            }
        }
        return sql.SET(MybatisStringUtil.join(setSql, ",")).WHERE("ID = #{id}").toString();
    }

    /**
     * 通用deleteById
     * 
     * @param tableName
     * @param ids
     * @return
     */
    public String deleteById(Class<?> clazz, String id) {
        return new SQL().DELETE_FROM(AnnotationUtil.getTableName(clazz)).WHERE("ID = '" + id + "'").toString();
    }

    /**
     * 通用findById
     * 
     * @param tableName
     * @param ids
     * @return
     */
    public String findById(Class<?> clazz, String id) {
        return new SQL().SELECT("*").FROM(AnnotationUtil.getTableName(clazz)).WHERE("ID = '" + id + "'").toString();
    }

    /**
     * 通用findByIds
     * 
     * @param tableName
     * @param ids
     * @return
     */
    public String findByIds(Class<?> clazz, String[] ids) {
        AssertionUtil.notEmpty(ids, "Parameter 'ids' cannot be null.");
        return new SQL().SELECT("*").FROM(AnnotationUtil.getTableName(clazz)).WHERE("id in ('" + StringUtils.join(ids, "','") + "')").toString();
    }

    public String findAll(Class<?> clazz) {
        return new SQL().SELECT("*").FROM(AnnotationUtil.getTableName(clazz)).toString();
    }

    public String find(QueryCriteria queryCriteria) {
        SQL sql = new SQL().SELECT("*").FROM(AnnotationUtil.getTableName(queryCriteria.getModelClass())).WHERE("1 = 1");
        if (Objects.nonNull(queryCriteria) && MybatisDetect.notEmpty(queryCriteria.getCriteria())) {
            sql.WHERE("(" + queryCriteria.toSql() + ")");
        }
        if (Objects.nonNull(queryCriteria.getOrdering())) {
            sql.ORDER_BY(queryCriteria.getOrdering().orderClause());
        }
        return sql.toString();
    }

    // ================================工具方法=====================================

    public boolean isNotNull(Map<String, Object> params, String key) {
        return params != null && params.containsKey(key) && params.get(key) != null;
    }

    protected boolean isNotEmpty(Map<String, Object> params, String key) {
        return isNotNull(params, key) && StringUtils.isNotBlank(String.valueOf(params.get(key)));
    }

    protected SQL appendEquals(SQL sql, String column, String property, JdbcType jdbcType) {
        String whereSql = column + " = #{" + property;
        if (jdbcType != null) {
            whereSql = whereSql + ", jdbcType=" + jdbcType.name();
        }
        whereSql = whereSql + "}";
        sql.AND().WHERE(whereSql);
        return sql;
    }

    protected SQL appendIn(SQL sql, String column, String property, JdbcType jdbcType) {
        String whereSql = column + " in #{" + property;
        if (jdbcType != null) {
            whereSql = whereSql + ", jdbcType=" + jdbcType.name();
        }
        whereSql = whereSql + "}";
        sql.AND().WHERE(whereSql);
        return sql;
    }

    protected SQL appendEqualsIfNotEmpty(SQL sql, Map<String, Object> params, String column, String property, JdbcType jdbcType) {
        if (isNotEmpty(params, property)) {
            appendEquals(sql, column, property, jdbcType);
        }
        return sql;
    }

    protected SQL appendInIfNotEmpty(SQL sql, Map<String, Object> params, String column, String property, JdbcType jdbcType) {
        if (isNotEmpty(params, property)) {
            appendIn(sql, column, property, jdbcType);
        }
        return sql;
    }

    protected SQL appendLikeIfNotEmpty(SQL sql, Map<String, Object> params, String column, String property, JdbcType jdbcType) {
        if (isNotEmpty(params, property)) {
            appendLike(sql, column, property, jdbcType);
        }
        return sql;
    }

    protected SQL appendLike(SQL sql, String column, String property, JdbcType jdbcType) {
        String whereSql = column + " like '%' || #{" + property;
        if (jdbcType != null) {
            whereSql = whereSql + ", jdbcType=" + jdbcType.name();
        }
        whereSql = whereSql + "} || '%'";
        sql.AND().WHERE(whereSql);
        return sql;
    }

    /**
     * 通过fied获取jdbctype
     * 
     * @param field
     * @return
     */
    protected JdbcType getJdbcType(Field field) {
        if (field != null) {
            String jdbcTypeName = "VARCHAR";
            if (Number.class.isAssignableFrom(field.getType()) || Enum.class.isAssignableFrom(field.getType())) {
                jdbcTypeName = "NUMERIC";
            } else if (Date.class.isAssignableFrom(field.getClass())) {
                jdbcTypeName = "DATE";
            }
            return resolveJdbcType(jdbcTypeName);
        }
        return null;
    }

    /**
     * 通过别名获取jdbctype
     * 
     * @param alias
     * @return
     */
    protected JdbcType resolveJdbcType(String alias) {
        if (alias == null) {
            return null;
        }
        try {
            return JdbcType.valueOf(alias);
        } catch (IllegalArgumentException e) {
            throw new BuilderException("Error resolving JdbcType. Cause: " + e, e);
        }
    }

}

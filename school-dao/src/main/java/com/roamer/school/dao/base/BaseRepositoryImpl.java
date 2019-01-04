package com.roamer.school.dao.base;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Base JpaRepository Implement
 * 其他JpaRepository都应当继承自该类
 *
 * @author roamer
 * @version V1.0
 * @date 2019/1/3 10:33
 */
public class BaseRepositoryImpl<T, ID extends Serializable> {

    private Class<T> entityClass;

    @PersistenceContext
    EntityManager em;

    public Class<T> getEntityClass() {
        return entityClass;
    }

    public BaseRepositoryImpl() {
        // 获取泛型类型(这里就是父类)
        ParameterizedType parameterizedType = null;
        if (getClass().getGenericSuperclass() instanceof ParameterizedType) {
            parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
        } else if (getClass().getGenericInterfaces().length > 0) {
            if (getClass().getGenericInterfaces()[0] instanceof ParameterizedType) {
                parameterizedType = (ParameterizedType) getClass().getGenericInterfaces()[0];
            }
        }
        Optional.ofNullable(parameterizedType).ifPresent(type -> {
            // 获取第一个泛型变量
            Type[] typeVariables = type.getActualTypeArguments();
            entityClass = (Class<T>) typeVariables[0];
        });

    }

    /**
     * hql对象查询，使用泛型变量映射实体类型
     *
     * @param hql      hql查询语句
     * @param paramMap 命名参数
     *
     * @return {@code T}
     */
    protected T findEntityObject(StringBuffer hql, Map<String, Object> paramMap) {
        return findEntityObjectByClass(hql, entityClass, paramMap);
    }

    /**
     * hql对象查询，使用泛型变量映射实体类型
     *
     * @param hql    hql查询语句
     * @param params 占位符参数
     *
     * @return {@code T}
     */
    protected T findEntityObject(StringBuffer hql, Object[] params) {
        return findEntityObjectByClass(hql, entityClass, params);
    }

    /**
     * hql对象查询，需要传入映射实体类型
     *
     * @param hql      hql查询语句
     * @param clazz    映射实体类型
     * @param paramMap 命名参数
     * @param <E>      泛型变量
     *
     * @return {@code E}
     */
    protected <E> E findEntityObjectByClass(StringBuffer hql, Class<E> clazz, Map<String, Object> paramMap) {
        return findEntityObject(hql, clazz, paramMap, null);
    }

    /**
     * hql对象查询，需要传入映射实体类型
     *
     * @param hql    hql查询语句
     * @param clazz  映射实体类型
     * @param params 占位符参数
     * @param <E>    泛型变量
     *
     * @return {@code E}
     */
    protected <E> E findEntityObjectByClass(StringBuffer hql, Class<E> clazz, Object[] params) {
        return findEntityObject(hql, clazz, null, params);
    }

    /**
     * hql对象查询，需要传入映射实体类型
     *
     * @param hql      hql查询语句
     * @param clazz    映射实体类型
     * @param paramMap 命名参数
     * @param params   占位符参数
     * @param <E>      泛型变量
     *
     * @return {@code E}
     */
    private <E> E findEntityObject(StringBuffer hql, Class<E> clazz, Map<String, Object> paramMap, Object[] params) {
        Query query = createQuery(hql, clazz, paramMap, params);
        return (E) query.getSingleResult();
    }

    /**
     * hql对象集合查询，使用泛型变量映射实体类型
     *
     * @param hql      hql查询语句
     * @param paramMap 命名参数
     *
     * @return {@code List<T>}
     */
    protected List<T> findEntityObjects(StringBuffer hql, Map<String, Object> paramMap) {
        return findEntityObjectsByClass(hql, entityClass, paramMap);
    }

    /**
     * hql对象集合查询，使用泛型变量映射实体类型
     *
     * @param hql    hql查询语句
     * @param params 占位符参数
     *
     * @return {@code List<T>}
     */
    protected List<T> findEntityObjects(StringBuffer hql, Object[] params) {
        return findEntityObjectsByClass(hql, entityClass, params);
    }

    /**
     * hql对象集合查询，需要传入映射实体类型
     *
     * @param hql      hql查询语句
     * @param clazz    映射实体类型
     * @param paramMap 命名参数
     * @param <E>      泛型变量
     *
     * @return {@code List<E>}
     */
    protected <E> List<E> findEntityObjectsByClass(StringBuffer hql, Class<E> clazz, Map<String, Object> paramMap) {
        return findEntityObjects(hql, clazz, paramMap, null);
    }

    /**
     * hql对象集合查询，需要传入映射实体类型
     *
     * @param hql    hql查询语句
     * @param clazz  映射实体类型
     * @param params 占位符参数
     * @param <E>    泛型变量
     *
     * @return {@code List<E>}
     */
    protected <E> List<E> findEntityObjectsByClass(StringBuffer hql, Class<E> clazz, Object[] params) {
        return findEntityObjects(hql, clazz, null, params);
    }

    /**
     * hql对象集合查询，需要传入映射实体类型
     *
     * @param hql      hql查询语句
     * @param clazz    映射实体类型
     * @param paramMap 命名参数
     * @param params   占位符参数
     * @param <E>      泛型变量
     *
     * @return {@code List<E>}
     */
    private <E> List<E> findEntityObjects(StringBuffer hql, Class<E> clazz, Map<String, Object> paramMap,
            Object[] params) {
        Query query = createQuery(hql, clazz, paramMap, params);
        return query.getResultList();
    }

    /**
     * hql单条记录查询
     *
     * @param sql      hql查询语句
     * @param paramMap 命名参数
     *
     * @return 单字段、对象查询 {@code List<Object>}，多字段查询 {@code List<Object[]>}
     */
    protected Object findResultObject(StringBuffer sql, Map<String, Object> paramMap) {
        return findResultObject(sql, paramMap, null);
    }

    /**
     * hql单条记录查询
     *
     * @param sql    hql查询语句
     * @param params 占位符参数
     *
     * @return 单字段、对象查询 {@code List<Object>}，多字段查询 {@code List<Object[]>}
     */
    protected Object findResultObject(StringBuffer sql, Object[] params) {
        return findResultObject(sql, null, params);
    }

    /**
     * hql查询单条记录
     *
     * @param hql      hql查询语句
     * @param paramMap 命名参数
     * @param params   占位符参数
     *
     * @return 单字段、对象查询 {@code List<Object>}，多字段查询 {@code List<Object[]>}
     */
    private Object findResultObject(StringBuffer hql, Map<String, Object> paramMap, Object[] params) {
        Query query = createQuery(hql, paramMap, params);
        return query.getSingleResult();
    }

    /**
     * hql多条记录查询
     *
     * @param sql      hql查询语句
     * @param paramMap 命名参数
     *
     * @return 单字段查询 {@code List<Object>}，多字段查询 {@code List<Object[]>}
     */
    protected List<?> findResultObjects(StringBuffer sql, Map<String, Object> paramMap) {
        return findResultObjects(sql, paramMap, null);
    }

    /**
     * hql多条记录查询
     *
     * @param sql    hql查询语句
     * @param params 占位符参数
     *
     * @return 单字段、对象查询 {@code List<Object>}，多字段查询 {@code List<Object[]>}
     */
    protected List<?> findResultObjects(StringBuffer sql, Object[] params) {
        return findResultObjects(sql, null, params);
    }

    /**
     * hql查询多条记录
     *
     * @param hql      hql查询语句
     * @param paramMap 命名参数
     * @param params   占位符参数
     *
     * @return 单字段、对象查询 {@code List<Object>}，多字段查询 {@code List<Object[]>}
     */
    private List<?> findResultObjects(StringBuffer hql, Map<String, Object> paramMap, Object[] params) {
        Query query = createQuery(hql, paramMap, params);
        return query.getResultList();
    }

    /**
     * 创建hql查询器
     *
     * @param hql      hql查询语句
     * @param paramMap 命名参数
     * @param params   占位符参数
     *
     * @return 查询器 {@link javax.persistence.Query}
     */
    private Query createQuery(StringBuffer hql, Map<String, Object> paramMap, Object[] params) {
        Query query = em.createQuery(hql.toString());
        setParameter(query, paramMap, params);
        return query;
    }

    /**
     * 创建hql查询器
     *
     * @param hql      hql查询语句
     * @param clazz    映射实体类型
     * @param paramMap 命名参数
     * @param params   占位符参数
     *
     * @return 查询器 {@link javax.persistence.Query}
     */
    private Query createQuery(StringBuffer hql, Class<?> clazz, Map<String, Object> paramMap, Object[] params) {
        Query query = em.createQuery(hql.toString(), clazz);
        setParameter(query, paramMap, params);
        return query;
    }

    /**
     * sql单条记录查询
     *
     * @param sql      sql查询语句
     * @param paramMap 命名参数
     *
     * @return 单字段查询 {@code Object}，多字段查询 {@code Object[]}
     */
    protected Object findOneResultObjectBySql(StringBuffer sql, Map<String, Object> paramMap) {
        return findOneResultObjectBySql(sql, paramMap, null);
    }

    /**
     * sql单条记录查询
     *
     * @param sql    sql查询语句
     * @param params 占位符参数
     *
     * @return 单字段查询 {@code Object}，多字段查询 {@code Object[]}
     */
    protected Object findOneResultObjectBySql(StringBuffer sql, Object[] params) {
        return findOneResultObjectBySql(sql, null, params);
    }

    /**
     * sql单条记录查询
     *
     * @param sql      sql查询语句
     * @param paramMap 命名参数
     * @param params   占位符参数
     *
     * @return 单字段查询 {@code Object}，多字段查询 {@code Object[]}
     */
    private Object findOneResultObjectBySql(StringBuffer sql, Map<String, Object> paramMap, Object[] params) {
        Query query = createNativeQuery(sql, paramMap, params);
        return query.getSingleResult();
    }

    /**
     * sql多条记录查询
     *
     * @param sql      sql查询语句
     * @param paramMap 命名参数
     *
     * @return 单字段查询 {@code List<Object>}，多字段查询 {@code List<Object[]>}
     */
    protected List<?> findResultObjectsBySql(StringBuffer sql, Map<String, Object> paramMap) {
        return findResultObjectsBySql(sql, paramMap, null);
    }

    /**
     * sql多条记录查询
     *
     * @param sql    sql查询语句
     * @param params 占位符参数
     *
     * @return 单字段查询 {@code List<Object>}，多字段查询 {@code List<Object[]>}
     */
    protected List<?> findResultObjectsBySql(StringBuffer sql, Object[] params) {
        return findResultObjectsBySql(sql, null, params);
    }

    /**
     * sql多条记录查询
     *
     * @param sql      sql查询语句
     * @param paramMap 命名参数
     * @param params   占位符参数
     *
     * @return 单字段查询 {@code List<Object>}，多字段查询 {@code List<Object[]>}
     */
    private List<?> findResultObjectsBySql(StringBuffer sql, Map<String, Object> paramMap, Object[] params) {
        Query query = createNativeQuery(sql, paramMap, params);
        return query.getResultList();
    }

    /**
     * 创建sql查询器
     *
     * @param sql      sql查询语句
     * @param paramMap 命名参数
     * @param params   占位符参数
     *
     * @return 查询器 {@link javax.persistence.Query}
     */
    private Query createNativeQuery(StringBuffer sql, Map<String, Object> paramMap, Object[] params) {
        Query query = em.createNativeQuery(sql.toString());
        setParameter(query, paramMap, params);
        return query;
    }

    /**
     * 设置查询器参数
     * <p>
     * <b>paramMap优先级更高，paramMap不为null，则忽略params</b>
     * </p>
     *
     * @param query    查询器
     * @param paramMap 命名参数
     * @param params   占位符参数
     */
    private void setParameter(Query query, Map<String, Object> paramMap, Object[] params) {
        if (Optional.ofNullable(paramMap).isPresent()) {
            paramMap.forEach(query::setParameter);
        } else if (Optional.ofNullable(params).isPresent()) {
            for (int i = 0; i < params.length; i++) {
                query.setParameter(i, params[i]);
            }
        }
    }

}

package com.roamer.school.dao.base;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 基础JpaRepository接口
 * 其他JpaRepository接口都应当继承自该接口
 *
 * @param <T>  持久化的实体类型
 * @param <ID> 主键类型
 *
 * @author roamer
 * @version V1.0
 * @date 2019/1/3 10:32
 */
@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable> extends PagingAndSortingRepository<T, ID> {

    /**
     * hql对象查询，使用泛型变量映射实体类型
     *
     * @param hql      hql查询语句
     * @param paramMap 命名参数
     *
     * @return {@code T}
     */
    T findEntityObject(StringBuffer hql, Map<String, Object> paramMap);

    /**
     * hql对象查询，使用泛型变量映射实体类型
     *
     * @param hql    hql查询语句
     * @param params 占位符参数
     *
     * @return {@code T}
     */
    T findEntityObject(StringBuffer hql, Object[] params);

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
    <E> E findEntityObjectByClass(StringBuffer hql, Class<E> clazz, Map<String, Object> paramMap);

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
    <E> E findEntityObjectByClass(StringBuffer hql, Class<E> clazz, Object[] params);


    /**
     * hql对象集合查询，使用泛型变量映射实体类型
     *
     * @param hql      hql查询语句
     * @param paramMap 命名参数
     *
     * @return {@code List<T>}
     */
    List<T> findEntityObjects(StringBuffer hql, Map<String, Object> paramMap);

    /**
     * hql对象集合查询，使用泛型变量映射实体类型
     *
     * @param hql    hql查询语句
     * @param params 占位符参数
     *
     * @return {@code List<T>}
     */
    List<T> findEntityObjects(StringBuffer hql, Object[] params);

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
    <E> List<E> findEntityObjectsByClass(StringBuffer hql, Class<E> clazz, Map<String, Object> paramMap);

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
    <E> List<E> findEntityObjectsByClass(StringBuffer hql, Class<E> clazz, Object[] params);


    /**
     * hql单条记录查询
     *
     * @param sql      hql查询语句
     * @param paramMap 命名参数
     *
     * @return 单字段、对象查询 {@code List<Object>}，多字段查询 {@code List<Object[]>}
     */
    Object findResultObject(StringBuffer sql, Map<String, Object> paramMap);

    /**
     * hql单条记录查询
     *
     * @param sql    hql查询语句
     * @param params 占位符参数
     *
     * @return 单字段、对象查询 {@code List<Object>}，多字段查询 {@code List<Object[]>}
     */
    Object findResultObject(StringBuffer sql, Object[] params);


    /**
     * hql多条记录查询
     *
     * @param sql      hql查询语句
     * @param paramMap 命名参数
     *
     * @return 单字段查询 {@code List<Object>}，多字段查询 {@code List<Object[]>}
     */
    List<?> findResultObjects(StringBuffer sql, Map<String, Object> paramMap);

    /**
     * hql多条记录查询
     *
     * @param sql    hql查询语句
     * @param params 占位符参数
     *
     * @return 单字段、对象查询 {@code List<Object>}，多字段查询 {@code List<Object[]>}
     */
    List<?> findResultObjects(StringBuffer sql, Object[] params);


    /**
     * sql单条记录查询
     *
     * @param sql      sql查询语句
     * @param paramMap 命名参数
     *
     * @return 单字段查询 {@code Object}，多字段查询 {@code Object[]}
     */
    Object findOneResultObjectBySql(StringBuffer sql, Map<String, Object> paramMap);

    /**
     * sql单条记录查询
     *
     * @param sql    sql查询语句
     * @param params 占位符参数
     *
     * @return 单字段查询 {@code Object}，多字段查询 {@code Object[]}
     */
    Object findOneResultObjectBySql(StringBuffer sql, Object[] params);

    /**
     * sql多条记录查询
     *
     * @param sql      sql查询语句
     * @param paramMap 命名参数
     *
     * @return 单字段查询 {@code List<Object>}，多字段查询 {@code List<Object[]>}
     */
    List<?> findResultObjectsBySql(StringBuffer sql, Map<String, Object> paramMap);

    /**
     * sql多条记录查询
     *
     * @param sql    sql查询语句
     * @param params 占位符参数
     *
     * @return 单字段查询 {@code List<Object>}，多字段查询 {@code List<Object[]>}
     */
    List<?> findResultObjectsBySql(StringBuffer sql, Object[] params);
}

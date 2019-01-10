package com.roamer.school.dao.base;

import com.sun.istack.internal.NotNull;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.*;

/**
 * Base JpaRepository Implement
 * 其他JpaRepository都应当继承自该类
 *
 * @author roamer
 * @version V1.0
 * @date 2019/1/3 10:33
 */
public abstract class BaseRepositoryImpl<T, ID extends Serializable> {

    private Class<T> entityClass;

    /**
     * PersistenceContext将缓存实例，用以管理实例状态
     * <p>
     * <b>多个PersistenceContext互相之间不能共享缓存</b>
     * 因此，分布式事务需要特别注意，以免出现异常<br>
     *
     * <ul> JPA Entity四种状态:
     *
     * <li> new(新建): 新创建的对象，尚未拥有持久性主键。</li>
     *
     * <li> managed(托管): 已经拥有持久性主键并和持久化建立了上下文环境</li>
     *
     * <li> detached(游离): 拥有持久化主键，但是没有与持久化建立上下文环境</li>
     *
     * <li> removed(删除):  拥有持久化主键，已经和持久化建立上下文环境，但是从数据库中删除</li>
     * <ul/>
     *
     * </p>
     */
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
        }
        // 暂时不支持接口泛型
        /*else if (getClass().getGenericInterfaces().length > 0) {
            if (getClass().getGenericInterfaces()[0] instanceof ParameterizedType) {
                parameterizedType = (ParameterizedType) getClass().getGenericInterfaces()[0];
            }
        }*/
        Optional.ofNullable(parameterizedType).ifPresent(type -> {
            // 获取第一个泛型变量
            Type[] typeVariables = type.getActualTypeArguments();
            entityClass = (Class<T>) typeVariables[0];
        });

    }

    /**
     * 将实例的改变立刻刷新到数据库中
     * <p>当触发Flush这个动作的时候，所有的实例都将会被insert/update/remove到数据库中（注意操作顺序）。
     * 数据库不会触发Commit的操作,即事务并不会因此提交</p>
     */
    public void flush() {
        em.flush();
    }

    /**
     * 分离所有当前正在被管理的实例<br>
     * <p>
     * 因为在事务没有提交前（事务默认在调用堆栈的最后提交，如：方法的返回），
     * 如果调用clear()方法，之前对实例所作的任何改变将会掉失,
     * <b>所以此处先进行flush操作后，而后再进行clear操作</b>
     * </p>
     */
    public void clear() {
        em.flush();
        // 调用该方法之前的所有实例都将处于 detached 状态
        em.clear();
    }

    /**
     * 刷新实例(查询数据库的最新数据)
     *
     * <p>
     * <b>只对 managed 状态的实例有效</b>
     * </p>
     *
     * @param entity 实例对象
     */
    public void refresh(T entity) {
        if (em.contains(entity)) {
            // 操作 new 状态的实例，不会发生任何操作，但有可能会抛出异常
            // 操作 removed 状态的实例，不会发生任何操作
            // 操作 detached 状态的实例，该方法将会抛出异常
            em.refresh(entity);
        }
    }

    /**
     * 新增实例
     *
     * @param entity 实例对象
     *
     * @throws RuntimeException <br>
     *                          操作 detached 状态的实例，该方法会抛出 {@link javax.persistence.EntityExistsException} 异常。
     */
    public void save(T entity) {
        // 操作 new/removed 状态的实例，将会转为 managed 状态
        // 操作 managed 状态的实例，不会发生任何操作
        em.persist(entity);
    }

    /**
     * 批量新增实例
     *
     * @param entities 实例对象
     *
     * @throws RuntimeException <br>
     *                          操作 detached 状态的实例，该方法会抛出 {@link javax.persistence.EntityExistsException} 异常。
     */
    public void save(Collection<T> entities) {
        Optional.ofNullable(entities).ifPresent(es -> es.forEach(this::save));
    }

    /**
     * @param entity
     */
    public void delete(T entity) {
        em.remove(entity);
    }

    public void delete(Collection<T> entities) {
        Optional.ofNullable(entities).ifPresent(es -> es.forEach(this::delete));
    }

    /**
     * 更新/保存实例
     *
     * <p>
     * 如果Entity是新创建的(无持久化主键)，则这个方法类似于persist()这个方法<br>
     * 如果Entity已经存在的(有持久化主键)，则只作为更新操作。
     * <p/>
     *
     * @param entity 实例对象
     *
     * @return 如实例未处于 managed 状态，则会产生新的 managed 状态实例（传入的实例并不会被管理）
     *
     * @throws RuntimeException <br>
     *                          操作 removed 状态的实例，该方法会抛出 {@link IllegalArgumentException} 异常。
     */
    public T update(T entity) {
        if (!em.contains(entity)) {
            // 操作 new/detached 状态的实例，将会产生新的 managed 状态实例
            return em.merge(entity);
        }
        return entity;
    }

    /**
     * 批量更新/保存实例
     *
     * <p>
     * 如果Entity是新创建的(无持久化主键)，则这个方法类似于persist()这个方法<br>
     * 如果Entity已经存在的(有持久化主键)，则只作为更新操作。
     * <p/>
     *
     * @param entities 实例对象
     *
     * @throws RuntimeException <br>
     *                          操作removed(删除)状态的实例，该方法会抛出 {@link IllegalArgumentException} 异常。
     */
    public void update(Collection<T> entities) {
        Optional.ofNullable(entities).ifPresent(es -> es.forEach(this::update));
    }

    /**
     * 记录数查询
     *
     * @param queryStr 查询语句
     * @param paramMap 命名参数
     *
     * @return 记录数
     */
    protected Long findCount(@NotNull StringBuffer queryStr, Map<String, Object> paramMap) {
        return findCount(queryStr, paramMap, null);
    }

    /**
     * 记录数查询
     *
     * @param queryStr 查询语句
     * @param params   占位符参数
     *
     * @return 记录数
     */
    protected Long findCount(@NotNull StringBuffer queryStr, Object[] params) {
        return findCount(queryStr, null, params);
    }

    /**
     * 记录数查询
     *
     * @param queryStr
     * @param paramMap 命名参数
     * @param params   占位符参数
     *
     * @return 记录数
     */
    private Long findCount(@NotNull StringBuffer queryStr, Map<String, Object> paramMap, Object[] params) {
        queryStr.insert(0, "SELECT COUNT(*) FROM ( ");
        queryStr.append(" )");
        BigDecimal object = (BigDecimal) findOneResultObjectBySql(queryStr, paramMap, params);
        return Objects.isNull(object) ? 0L : object.longValue();
    }

    /**
     * hql对象查询，使用泛型变量映射实体类型
     *
     * @param hql      hql查询语句
     * @param paramMap 命名参数
     *
     * @return {@code T}
     */
    protected T findEntity(@NotNull StringBuffer hql, Map<String, Object> paramMap) {
        return findEntityByClass(hql, entityClass, paramMap);
    }

    /**
     * hql对象查询，使用泛型变量映射实体类型
     *
     * @param hql    hql查询语句
     * @param params 占位符参数
     *
     * @return {@code T}
     */
    protected T findEntity(@NotNull StringBuffer hql, Object[] params) {
        return findEntityByClass(hql, entityClass, params);
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
    protected <E> E findEntityByClass(@NotNull StringBuffer hql, Class<E> clazz, Map<String, Object> paramMap) {
        return findEntity(hql, clazz, paramMap, null);
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
    protected <E> E findEntityByClass(@NotNull StringBuffer hql, Class<E> clazz, Object[] params) {
        return findEntity(hql, clazz, null, params);
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
    private <E> E findEntity(@NotNull StringBuffer hql, Class<E> clazz, Map<String, Object> paramMap, Object[] params) {
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
    protected List<T> findEntities(@NotNull StringBuffer hql, Map<String, Object> paramMap) {
        return findEntitiesByClass(hql, entityClass, paramMap);
    }

    /**
     * hql对象集合查询，使用泛型变量映射实体类型
     *
     * @param hql    hql查询语句
     * @param params 占位符参数
     *
     * @return {@code List<T>}
     */
    protected List<T> findEntities(@NotNull StringBuffer hql, Object[] params) {
        return findEntitiesByClass(hql, entityClass, params);
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
    protected <E> List<E> findEntitiesByClass(@NotNull StringBuffer hql, Class<E> clazz, Map<String, Object> paramMap) {
        return findEntities(hql, clazz, paramMap, null);
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
    protected <E> List<E> findEntitiesByClass(@NotNull StringBuffer hql, Class<E> clazz, Object[] params) {
        return findEntities(hql, clazz, null, params);
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
    private <E> List<E> findEntities(@NotNull StringBuffer hql, Class<E> clazz, Map<String, Object> paramMap, Object[] params) {
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
    protected Object findResultObject(@NotNull StringBuffer sql, Map<String, Object> paramMap) {
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
    protected Object findResultObject(@NotNull StringBuffer sql, Object[] params) {
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
    private Object findResultObject(@NotNull StringBuffer hql, Map<String, Object> paramMap, Object[] params) {
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
    protected List<?> findResultObjects(@NotNull StringBuffer sql, Map<String, Object> paramMap) {
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
    protected List<?> findResultObjects(@NotNull StringBuffer sql, Object[] params) {
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
    private List<?> findResultObjects(@NotNull StringBuffer hql, Map<String, Object> paramMap, Object[] params) {
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
    private Query createQuery(@NotNull StringBuffer hql, Map<String, Object> paramMap, Object[] params) {
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
    private Query createQuery(@NotNull StringBuffer hql, Class<?> clazz, Map<String, Object> paramMap,
            Object[] params) {
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
    protected Object findOneResultObjectBySql(@NotNull StringBuffer sql, Map<String, Object> paramMap) {
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
    protected Object findOneResultObjectBySql(@NotNull StringBuffer sql, Object[] params) {
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
    private Object findOneResultObjectBySql(@NotNull StringBuffer sql, Map<String, Object> paramMap, Object[] params) {
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
    protected List<?> findResultObjectsBySql(@NotNull StringBuffer sql, Map<String, Object> paramMap) {
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
    protected List<?> findResultObjectsBySql(@NotNull StringBuffer sql, Object[] params) {
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
    private List<?> findResultObjectsBySql(@NotNull StringBuffer sql, Map<String, Object> paramMap, Object[] params) {
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
    private Query createNativeQuery(@NotNull StringBuffer sql, Map<String, Object> paramMap, Object[] params) {
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

package com.gitee.sunchenbin.mybatis.actable.manager.common;

import com.gitee.sunchenbin.mybatis.actable.command.PageResultCommand;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * 通用增删改查方法
 */
public interface BaseCRUDManager {

    /**
     * 根据实体对象的非Null字段作为Where条件查询结果集，如果对象的属性值都为null则返回全部数据等同于selectAll
     * @param t 实体对象
     * @param <T> 实体对象类型
     * @return List实体对象列表
     */
    <T> List<T> select(T t);

    /**
     * 根据实体对象的@IsKey主键字段的值作为Where条件查询结果，主键字段不能为null
     * @param t 实体对象(只设置主键值即可，其他字段值不会读取)
     * @param <T> 实体对象类型
     * @return 实体对象
     */
    <T> T selectByPrimaryKey(T t);

    /**
     * 查询表全部数据
     * @param clasz 实体对象的class
     * @param <T> 实体对象类型
     * @return List实体对象列表
     */
    <T> List<T> selectAll(Class<T> clasz);

    /**
     * 根据实体对象的非Null字段作为Where条件查询结果集的Count，如果对象的属性值都为null则Count全表
     * @param t 实体对象
     * @param <T> 实体对象类型
     * @return 结果数量
     */
    <T> int selectCount(T t);

    /**
     * 根据实体对象的非Null字段作为Where条件查询结果集，如果对象的属性值都为null则返回结果集的第一条使用的limit 1
     * @param t 实体对象
     * @param <T> 实体对象类型
     * @return 实体对象
     */
    <T> T selectOne(T t);

    /**
     * 根据实体对象的非Null字段作为Where条件进行删除操作，如果对象的属性值都为null则删除表全部数据
     * @param t 实体对象
     * @param <T> 实体对象类型
     * @return 返回成功条数
     */
    <T> int delete(T t);

    /**
     * 根据实体对象的@IsKey主键字段的值作为Where条件进行删除操作，主键字段不能为null
     * @param t 实体对象(只设置主键值即可，其他字段值不会读取)
     * @param <T> 实体对象类型
     * @return 返回成功条数
     */
    <T> int deleteByPrimaryKey(T t);

    /**
     * 根据实体对象的@IsKey主键字段的值作为Where条件查询该数据是否存在，主键字段不能为null
     * @param t 实体对象(只设置主键值即可，其他字段值不会读取)
     * @param <T> 实体对象类型
     * @return true存在，fasle不存在
     */
    <T> boolean existsByPrimaryKey(T t);

    /**
     * 根据实体对象保存一条数据，主键如果没有设置自增属性则必须不能为null
     * @param t 实体对象
     * @param <T> 实体对象类型
     * @return 实体对象
     */
    <T> T insert(T t);

    /**
     * 根据实体对象保存一条数据，如果属性值为null则不插入默认使用数据库的字段默认值，主键如果没有设置自增属性则必须不能为null
     * @param t 实体对象
     * @param <T> 实体对象类型
     * @return 实体对象
     */
    <T> T insertSelective(T t);

    /**
     * 根据实体对象主键作为Where条件更新其他字段数据，主键必须不能为null
     * @param t 实体对象
     * @param <T> 实体对象类型
     * @return 更新结果
     */
    <T> boolean updateByPrimaryKey(T t);

    /**
     * 根据实体对象主键作为Where条件更新其他字段数据，如果其他字段属性值为null则忽略更新，主键必须不能为null
     * @param t 实体对象
     * @param <T> 实体对象类型
     * @return 更新结果
     */
    <T> boolean updateByPrimaryKeySelective(T t);

    /**
     * 直接根据sql查询数据，并根据指定的对象类型转化后返回
     *
     * @param sql 动态sql
     * @param beanClass 返回list对象类型
     * @param <T> 实体对象类型
     * @return list的实体对象类型
     */
    <T> List<T> query(String sql, Class<T> beanClass);

    /**
     * 直接根据sql查询返回数据
     *
     * @param sql 自定义的sql
     * @return list map结构的数据
     */
    List<LinkedHashMap<String, Object>> query(String sql);

    /**
     * 根据实体对象的非Null字段作为Where条件查询结果集，如果对象的属性值都为null则返回全部数据等同于selectAll+分页
     *
     * @param t 实体对象
     * @param currentPage 分页参数查询第几页，默认1
     * @param pageSize 分页参数每页显示的条数，默认10
     * @param orderby 分页使用的排序，有序的Map结构{key(要排序的字段名),value(desc/asc)}
     * @param <T> 实体类型
     * @return PageResultCommand分页对象类型
     */
    <T> PageResultCommand<T> search(T t, Integer currentPage, Integer pageSize,LinkedHashMap<String,String> orderby);

    /**
     * 根据实体对象的非Null字段作为Where条件查询结果集，如果对象的属性值都为null则返回全部数据等同于selectAll+分页
     * @param t 实体对象
     * @param <T> 实体对象类型
     * @return PageResultCommand分页对象类型
     */
    <T> PageResultCommand<T> search(T t);
}

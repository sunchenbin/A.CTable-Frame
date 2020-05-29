package com.gitee.sunchenbin.mybatis.actable.manager.common;

import java.util.LinkedHashMap;
import java.util.List;

import com.gitee.sunchenbin.mybatis.actable.command.PageResultCommand;

/**
 * 已经废弃请勿使用有bug
 */
@Deprecated
public interface BaseMysqlCRUDManager{

	/**
	 * 保存，如果主键有值则进行更新操作
	 * @param <T> model类型
	 * @param t 要保存的model类型数据
	 * @return id 操作数据的id
	 */
	<T> Integer save(T t);
	
	/**
	 * 更新，可以更新null值
	 * @param t
	 * @return
	 */
	<T> Integer updateWithNull(T t);
	/**
	 * 根据传入对象非空的条件删除
	 * @param <T> model类型
	 * @param t 要删除的model类型数据
	 */
	<T> void delete(T t);
	
	/**
	 * 根据传入对象非空的条件进行查询返回值PageResultCommand，适用于返回结果集的场合
	 * @param <T> model类型
	 * @param t 要查询的model类型数据
	 */
	<T> PageResultCommand<T> search(T t);
	
	/**
	 * 废弃，请使用search
	 * 根据传入对象非空的条件进行查询返回值PageResultCommand，适用于返回结果集的场合
	 * @param <T> model类型
	 * @param t 要查询的model类型数据
	 */
	@Deprecated
	<T> PageResultCommand<T> query(T t);
	
	/**
	 * 根据传入对象非空的条件进行查询返回对象，适用于返回单个对象的场合
	 * @param t
	 * @return
	 */
	<T> T findPrimaryBy(T t);
	
	/**
	 * 动态查询方法
	 * @param <T>
	 * @param sql 动态sql
	 * @param beanClass 返回list对象类型，不传默认返回List(Map(String,Object))格式
	 * @return
	 */
	<T> List<T> query(String sql, Class<T> beanClass);
	
	/**
	 * 动态查询方法
	 * @param sql 动态sql
	 * @return
	 */
	List<LinkedHashMap<String, Object>> query(String sql);
}

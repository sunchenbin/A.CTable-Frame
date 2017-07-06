package com.mybatis.enhance.store.manager.common;

import java.util.List;

public interface BaseMysqlCRUDManager<T>{

	/**
	 * 保存，如果主键有值则进行更新操作
	 * @param t
	 */
	void save(T t);
	
	/**
	 * 根据传入对象非空的条件删除
	 * @param t
	 */
	void delete(T t);
	
	/**
	 * 根据传入对象非空的条件进行查询
	 * @param t
	 */
	List<T> query(T t);
}

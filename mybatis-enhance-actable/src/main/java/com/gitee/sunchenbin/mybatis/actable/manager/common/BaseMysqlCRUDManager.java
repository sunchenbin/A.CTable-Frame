package com.gitee.sunchenbin.mybatis.actable.manager.common;

import java.util.List;

import com.gitee.sunchenbin.mybatis.actable.command.PageResultCommand;

public interface BaseMysqlCRUDManager{

	/**
	 * 保存，如果主键有值则进行更新操作
	 * @param <T> model类型
	 * @param t 要保存的model类型数据
	 * @return id 操作数据的id
	 */
	<T> Integer save(T t);
	
	/**
	 * 根据传入对象非空的条件删除
	 * @param <T> model类型
	 * @param t 要删除的model类型数据
	 */
	<T> void delete(T t);
	
	/**
	 * 根据传入对象非空的条件进行查询
	 * @param <T> model类型
	 * @param t 要查询的model类型数据
	 */
	<T> PageResultCommand<T> query(T t);
}

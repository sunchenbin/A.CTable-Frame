package com.mybatis.enhance.store.dao.system;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.mybatis.enhance.store.command.SysMysqlColumns;


/**
 * 创建更新表结构的Mapper
 * @author sunchenbin
 *
 */
public interface CreateMysqlTablesMapper {

	/**
	 * 根据结构注解解析出来的信息创建表
	 * @param tableMap
	 */
	public void createTable(@Param("tableMap") Map<String, List<Object>> tableMap);

	/**
	 * 根据表名查询表在库中是否存在，存在返回1，不存在返回0
	 * @param tableName
	 * @return
	 */
	public int findTableCountByTableName(@Param("tableName") String tableName);
	
	/**
	 * 根据表名查询库中该表的字段结构等信息
	 * @param tableName
	 * @return
	 */
	public List<SysMysqlColumns> findTableEnsembleByTableName(@Param("tableName") String tableName);
	
	/**
	 * 增加字段
	 * @param tableMap
	 */
	public void addTableField(@Param("tableMap") Map<String, Object> tableMap);
	
	/**
	 * 删除字段
	 * @param tableMap
	 */
	public void removeTableField(@Param("tableMap") Map<String, Object> tableMap);
	
	/**
	 * 修改字段
	 * @param tableMap
	 */
	public void modifyTableField(@Param("tableMap") Map<String, Object> tableMap);
	
	/**
	 * 删除主键约束，附带修改其他字段属性功能
	 * @param tableMap
	 */
	public void dropKeyTableField(@Param("tableMap") Map<String, Object> tableMap);
	
	/**
	 * 删除唯一约束字段，不带修改其他字段属性的功能
	 * @param tableMap
	 */
	public void dropUniqueTableField(@Param("tableMap") Map<String, Object> tableMap);
	
	/**
	 * 根据表名删除表
	 * @param tableName
	 */
	public void dorpTableByName(@Param("tableName") String tableName);
}

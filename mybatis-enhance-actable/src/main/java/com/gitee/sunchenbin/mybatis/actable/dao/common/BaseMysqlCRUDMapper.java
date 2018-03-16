package com.gitee.sunchenbin.mybatis.actable.dao.common;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;


/**
 * 创建更新表结构的Mapper
 * @author sunchenbin
 *
 */
public interface BaseMysqlCRUDMapper {

	/**
	 * 保存
	 * @param tableMap 表结构的map
	 */
	public void save(@Param("tableMap") Map<Object, Map<Object, Object>> tableMap);
	
	/**
	 * 更新
	 * @param tableMap 表结构的map
	 */
	public void update(@Param("tableMap") Map<Object, Map<Object, Object>> tableMap);
	
	/**
	 * 删除
	 * @param tableMap 表结构的map
	 */
	public void delete(@Param("tableMap") Map<Object, Map<Object, Object>> tableMap);
	
	/**
	 * 查询
	 * @param tableMap 表结构的map
	 */
	public List<Map<String,Object>> query(@Param("tableMap") Map<Object, Map<Object, Object>> tableMap);

	

}

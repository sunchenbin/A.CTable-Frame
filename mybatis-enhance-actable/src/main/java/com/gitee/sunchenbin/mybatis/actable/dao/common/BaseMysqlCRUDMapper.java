package com.gitee.sunchenbin.mybatis.actable.dao.common;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import com.gitee.sunchenbin.mybatis.actable.command.SaveOrUpdateDataCommand;


/**
 * 创建更新表结构的Mapper
 * @author sunchenbin
 *
 */
@Transactional
public interface BaseMysqlCRUDMapper {

	/**
	 * 保存
	 * @param tableMap 表结构的map
	 */
	public void save(SaveOrUpdateDataCommand saveOrUpdateDataCommand);
	
	/**
	 * 更新
	 * @param tableMap 表结构的map
	 */
	public void update(SaveOrUpdateDataCommand saveOrUpdateDataCommand);
	
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

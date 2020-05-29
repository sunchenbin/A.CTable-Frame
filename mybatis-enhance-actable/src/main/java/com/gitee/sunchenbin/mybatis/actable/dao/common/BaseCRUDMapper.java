package com.gitee.sunchenbin.mybatis.actable.dao.common;

import com.gitee.sunchenbin.mybatis.actable.command.SaveOrUpdateDataCommand;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * 创建更新表结构的Mapper
 * @author sunchenbin
 *
 */
@Transactional
public interface BaseCRUDMapper {

	/**
	 * 根据实体对象的非Null字段作为Where条件查询结果集，如果对象的属性值都为null则返回全部数据等同于selectAll
	 * @param tableMap 表结构的map
	 */
	public List<Map<String,Object>> select(@Param("tableMap") Map<Object, Object> tableMap);

	/**
	 * 根据实体对象的非Null字段作为Where条件查询结果集的Count，如果对象的属性值都为null则Count全表
	 * @param tableMap 表结构的map
	 */
	public int selectCount(@Param("tableMap") Map<Object, Object> tableMap);

	/**
	 * 根据实体对象的非Null字段作为Where条件进行删除操作，如果对象的属性值都为null则删除表全部数据
	 * @param tableMap 表结构的map
	 */
	public int delete(@Param("tableMap") Map<Object, Object> tableMap);

	/**
	 * 根据实体对象保存一条数据，主键如果没有设置自增属性则必须不能为null
	 * @param saveOrUpdateDataCommand id+表结构的map
	 */
	public int insert(SaveOrUpdateDataCommand saveOrUpdateDataCommand);

	/**
	 * 根据实体对象保存一条数据，如果属性值为null则不插入默认使用数据库的字段默认值，主键如果没有设置自增属性则必须不能为null
	 * @param saveOrUpdateDataCommand id+表结构的map
	 */
	public int insertSelective(SaveOrUpdateDataCommand saveOrUpdateDataCommand);

	/**
	 * 根据实体对象主键作为Where条件更新其他字段数据，可以将字段值更新为null，主键必须不能为null
	 * @param saveOrUpdateDataCommand
	 */
	public int updateByPrimaryKey(SaveOrUpdateDataCommand saveOrUpdateDataCommand);

	/**
	 * 根据实体对象主键作为Where条件更新其他字段数据，如果其他字段属性值为null则忽略更新，主键必须不能为null
	 * @param saveOrUpdateDataCommand
	 */
	public int updateByPrimaryKeySelective(SaveOrUpdateDataCommand saveOrUpdateDataCommand);

	/**
	 * 查询
	 * @param value 动态sql
	 */
	public List<LinkedHashMap<String,Object>> query(String value);

}

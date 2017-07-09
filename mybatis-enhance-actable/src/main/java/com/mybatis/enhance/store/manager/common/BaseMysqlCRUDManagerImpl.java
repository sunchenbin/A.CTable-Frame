package com.mybatis.enhance.store.manager.common;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mybatis.enhance.store.annotation.Column;
import com.mybatis.enhance.store.annotation.Table;
import com.mybatis.enhance.store.dao.common.BaseMysqlCRUDMapper;

@Transactional
@Service("baseMysqlCRUDManager")
public class BaseMysqlCRUDManagerImpl implements BaseMysqlCRUDManager<Object>{

	private static final Logger	log	= LoggerFactory.getLogger(BaseMysqlCRUDManagerImpl.class);
	
	private static final String KEYFIELDMAP = "keyFieldMap";

	@Autowired
	private BaseMysqlCRUDMapper	baseMysqlCRUDMapper;

	public void save(Object obj){
		boolean isSave = true;
		Table tableName = obj.getClass().getAnnotation(Table.class);
		if ((tableName == null) || (tableName.name() == null || tableName.name() == "")) {
			log.error("必须使用model中的对象！");
			return;
		}
		Map<Object, Map<Object, Object>> tableMap = new HashMap<Object, Map<Object, Object>>();
		Map<Object, Object> dataMap = new HashMap<Object, Object>();
		Map<String, Object> keyFieldMap = new HashMap<String, Object>();
		Field[] declaredFields = obj.getClass().getDeclaredFields();
		for (Field field : declaredFields){
			try{
				// 私有属性需要设置访问权限
				field.setAccessible(true);
				Column column = field.getAnnotation(Column.class);
				if (column == null) {
					log.info("该field没有配置注解不是表中在字段！");
					continue;
				}

				// 如果是主键，并且不是空的时候，这时候应该是更新操作
				if (column.isKey() && field.get(obj) != null && Integer.parseInt((String) field.get(obj)) > 0) {
					isSave = false;
					keyFieldMap.put(field.getName(), field.get(obj));
				}

				// 如果是自增,并且是保存的场合，不需要添加到map中做保存
				if (isSave && column.isAutoIncrement()) {
					log.info("字段：" + field.getName() + "是自增的不需要添加到map中");
					continue;
				}

				dataMap.put(field.getName(), field.get(obj));
			}catch (IllegalArgumentException e){
				e.printStackTrace();
			}catch (IllegalAccessException e){
				e.printStackTrace();
			}
		}
		if (isSave) {
			tableMap.put(tableName.name(), dataMap);
			// 执行保存操作
			baseMysqlCRUDMapper.save(tableMap);
		}else{
			dataMap.put(KEYFIELDMAP, keyFieldMap);
			tableMap.put(tableName.name(), dataMap);
			// 执行更新操作根据主键
			baseMysqlCRUDMapper.update(tableMap);
		}
	}

	public void delete(Object obj){

		// 得到表名
		Table tableName = obj.getClass().getAnnotation(Table.class);
		if ((tableName == null) || (tableName.name() == null || tableName.name() == "")) {
			log.error("必须使用model中的对象！");
			return;
		}
		Map<Object, Map<Object, Object>> tableMap = new HashMap<Object, Map<Object, Object>>();
		Map<Object, Object> dataMap = new HashMap<Object, Object>();
		
		Field[] declaredFields = obj.getClass().getDeclaredFields();
		
		for (Field field : declaredFields){
			// 设置访问权限
			field.setAccessible(true);
			// 得到字段的配置
			Column column = field.getAnnotation(Column.class);
			if (column == null) {
				log.info("该field没有配置注解不是表中在字段！");
				continue;
			}
			try{
				dataMap.put(column.name(), field.get(obj));
			}catch (IllegalArgumentException e){
				e.printStackTrace();
			}catch (IllegalAccessException e){
				e.printStackTrace();
			}
		}
		tableMap.put(tableName.name(), dataMap);
		baseMysqlCRUDMapper.delete(tableMap);
	}

	public List query(Object obj){
		// 得到表名
		Table tableName = obj.getClass().getAnnotation(Table.class);
		if ((tableName == null) || (tableName.name() == null || tableName.name() == "")) {
			log.error("必须使用model中的对象！");
			return null;
		}
		Map<Object, Map<Object, Object>> tableMap = new HashMap<Object, Map<Object, Object>>();
		Map<Object, Object> dataMap = new HashMap<Object, Object>();
		
		Field[] declaredFields = obj.getClass().getDeclaredFields();
		
		for (Field field : declaredFields){
			// 设置访问权限
			field.setAccessible(true);
			// 得到字段的配置
			Column column = field.getAnnotation(Column.class);
			if (column == null) {
				log.info("该field没有配置注解不是表中在字段！");
				continue;
			}
			try{
				dataMap.put(column.name(), field.get(obj));
			}catch (IllegalArgumentException e){
				e.printStackTrace();
			}catch (IllegalAccessException e){
				e.printStackTrace();
			}
		}
		tableMap.put(tableName.name(), dataMap);
		List<Map<String, Object>> query = baseMysqlCRUDMapper.query(tableMap);
		
		List<Object> list = new ArrayList<Object>();
		try{
			for (Map<String, Object> map : query){
				Object newInstance = obj.getClass().newInstance();
				Field[] declaredFields2 = newInstance.getClass().getDeclaredFields();
				for (Field field : declaredFields2){
					field.setAccessible(true);
					// 得到字段的配置
					Column column = field.getAnnotation(Column.class);
					if (column == null) {
						log.info("该field没有配置注解不是表中在字段！");
						continue;
					}
					String name = field.getName();
					field.set(newInstance, map.get(name));
				}
				list.add(newInstance);
			}
		}catch (InstantiationException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IllegalAccessException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}

}

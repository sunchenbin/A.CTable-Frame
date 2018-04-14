package com.gitee.sunchenbin.mybatis.actable.manager.common;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.command.PageResultCommand;
import com.gitee.sunchenbin.mybatis.actable.command.SaveOrUpdateDataCommand;
import com.gitee.sunchenbin.mybatis.actable.dao.common.BaseMysqlCRUDMapper;

@Transactional
@Service("baseMysqlCRUDManager")
public class BaseMysqlCRUDManagerImpl implements BaseMysqlCRUDManager{

	private static final Logger	log	= LoggerFactory.getLogger(BaseMysqlCRUDManagerImpl.class);
	
	private static final String KEYFIELDMAP = "keyFieldMap";

	@Autowired
	private BaseMysqlCRUDMapper	baseMysqlCRUDMapper;

	public <T> Integer save(T obj){
		boolean isSave = true;
		Table tableName = obj.getClass().getAnnotation(Table.class);
		if ((tableName == null) || (tableName.name() == null || tableName.name() == "")) {
			log.error("必须使用model中的对象！");
			return null;
		}
		Field[] declaredFields = getAllFields(obj);
		Map<Object, Map<Object, Object>> tableMap = new HashMap<Object, Map<Object, Object>>();
		Map<Object, Object> dataMap = new HashMap<Object, Object>();
		Map<String, Object> keyFieldMap = new HashMap<String, Object>();
		Integer updateId = null;
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
				if (column.isKey() && field.get(obj) != null && (new Integer(field.get(obj).toString())) > 0) {
					isSave = false;
					keyFieldMap.put(field.getName(), field.get(obj));
					updateId = (Integer) field.get(obj);
				}

				// 如果是自增,并且是保存的场合，不需要添加到map中做保存
				if (isSave && column.isAutoIncrement()) {
					log.info("字段：" + field.getName() + "是自增的不需要添加到map中");
					continue;
				}

				dataMap.put(column.name(), field.get(obj));
			}catch (IllegalArgumentException e){
				e.printStackTrace();
			}catch (IllegalAccessException e){
				e.printStackTrace();
			}
		}
		if (isSave) {
			tableMap.put(tableName.name(), dataMap);
			SaveOrUpdateDataCommand saveOrUpdateDataCommand = new SaveOrUpdateDataCommand(tableMap);
			// 执行保存操作
			baseMysqlCRUDMapper.save(saveOrUpdateDataCommand);
			return saveOrUpdateDataCommand.getId();
		}else{
			dataMap.put(KEYFIELDMAP, keyFieldMap);
			tableMap.put(tableName.name(), dataMap);
			SaveOrUpdateDataCommand saveOrUpdateDataCommand = new SaveOrUpdateDataCommand(tableMap);
			// 执行更新操作根据主键
			baseMysqlCRUDMapper.update(saveOrUpdateDataCommand);
			return updateId;
		}
	}

	private <T> Field[] getAllFields(T obj) {
		Field[] declaredFields = obj.getClass().getDeclaredFields();
		
		// 递归扫描父类的filed
		declaredFields = recursionParents(obj.getClass(), declaredFields);
		return declaredFields;
	}

	public <T> void delete(T obj){

		// 得到表名
		Table tableName = obj.getClass().getAnnotation(Table.class);
		if ((tableName == null) || (tableName.name() == null || tableName.name() == "")) {
			log.error("必须使用model中的对象！");
			return;
		}
		Field[] declaredFields = getAllFields(obj);
		Map<Object, Map<Object, Object>> tableMap = new HashMap<Object, Map<Object, Object>>();
		Map<Object, Object> dataMap = new HashMap<Object, Object>();
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

	@SuppressWarnings("unchecked")
	public <T> PageResultCommand<T> query(T obj){
		String startKey = "start";
		String sizeKey = "pageSize";
		String currentPageKey = "currentPage";
		String orderFieldKey = "orderField";
		String sortKey = "sortStr";
		Integer startVal = null;
		Integer sizeVal = null;
		Integer currentPageVal = null;
		String orderFieldVal = null;
		String sortVal = null;
		PageResultCommand<T> pageResultCommand = new PageResultCommand<T>();
		// 得到表名
		Table tableName = obj.getClass().getAnnotation(Table.class);
		if ((tableName == null) || (tableName.name() == null || tableName.name() == "")) {
			log.error("必须使用model中的对象！");
			return pageResultCommand;
		}
		Field[] declaredFields = getAllFields(obj);
		Map<Object, Object> tableMap = new HashMap<Object, Object>();
		Map<Object, Object> dataMap = new HashMap<Object, Object>();
		for (Field field : declaredFields){
			try{
				// 设置访问权限
				field.setAccessible(true);
				// 获取分页start和size
				if(startKey.equals(field.getName())) {
					startVal = (Integer) field.get(obj);
				}
				if(sizeKey.equals(field.getName())) {
					sizeVal = (Integer) field.get(obj);
				}
				if(currentPageKey.equals(field.getName())) {
					currentPageVal = (Integer) field.get(obj);
				}
				if(orderFieldKey.equals(field.getName())) {
					orderFieldVal = (String) field.get(obj);
				}
				if(sortKey.equals(field.getName())) {
					sortVal = (String) field.get(obj);
				}
				// 得到字段的配置
				Column column = field.getAnnotation(Column.class);
				if (column == null) {
					log.info("该field没有配置注解不是表中在字段！");
					continue;
				}
				if (field.get(obj) instanceof String && field.get(obj) != null && "".equals(field.get(obj))) {
					dataMap.put(column.name(), null);
				}else {					
					dataMap.put(column.name(), field.get(obj));
				}
			}catch (IllegalArgumentException e){
				e.printStackTrace();
			}catch (IllegalAccessException e){
				e.printStackTrace();
			}
		}
		tableMap.put(tableName.name(), dataMap);
		if(currentPageVal != null && currentPageVal > 0) {
			tableMap.put(startKey, startVal);
			tableMap.put(sizeKey, sizeVal);
		}
		if(orderFieldVal != null && orderFieldVal != "") {
			tableMap.put(orderFieldKey, orderFieldVal);
			tableMap.put(sortKey, sortVal);
		}
		List<Map<String, Object>> query = baseMysqlCRUDMapper.query(tableMap);
		
		List<T> list = new ArrayList<T>();
		try{
			for (Map<String, Object> map : query){
				T newInstance = (T) obj.getClass().newInstance();
				Field[] declaredFields2 = newInstance.getClass().getDeclaredFields();
				for (Field field : declaredFields2){
					field.setAccessible(true);
					// 得到字段的配置
					Column column = field.getAnnotation(Column.class);
					if (column == null) {
						log.info("该field没有配置注解不是表中在字段！");
						continue;
					}
					String name = column.name();
					field.set(newInstance, map.get(name));
				}
				list.add(newInstance);
			}
		}catch (InstantiationException e){
			e.printStackTrace();
		}catch (IllegalAccessException e){
			e.printStackTrace();
		}
		if (null != list) {			
			pageResultCommand.setData(list);
			int queryCount = baseMysqlCRUDMapper.queryCount(tableMap);
			pageResultCommand.setRecordsFiltered(queryCount);
			pageResultCommand.setRecordsTotal(queryCount);
		}
		return pageResultCommand;
	}
	
	/**
	 * 递归扫描父类的fields
	 * @param clas
	 * @param fields
	 */
	@SuppressWarnings("rawtypes")
	private Field[] recursionParents(Class<?> clas, Field[] fields) {
		if(clas.getSuperclass()!=null){
			Class clsSup = clas.getSuperclass();
			fields = (Field[]) ArrayUtils.addAll(fields,clsSup.getDeclaredFields());
			fields = recursionParents(clsSup, fields);
		}
		return fields;
	}

}

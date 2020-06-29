package com.gitee.sunchenbin.mybatis.actable.manager.common;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.gitee.sunchenbin.mybatis.actable.utils.ColumnUtils;
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
import org.springframework.util.StringUtils;

/**
 * 已经废弃请勿使用有bug
 */
@Transactional
@Service("baseMysqlCRUDManager")
@Deprecated
public class BaseMysqlCRUDManagerImpl implements BaseMysqlCRUDManager{

	private static final Logger	log	= LoggerFactory.getLogger(BaseMysqlCRUDManagerImpl.class);
	
	private static final String KEYFIELDMAP = "keyFieldMap";

	@Autowired
	private BaseMysqlCRUDMapper	baseMysqlCRUDMapper;

	@Override
	public <T> Integer save(T obj){
		boolean isSave = true;
		String tableName = ColumnUtils.getTableName(obj.getClass());
		if (StringUtils.isEmpty(tableName)) {
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
					log.debug("该field没有配置注解不是表中在字段！");
					continue;
				}

				// 如果是主键，并且不是空的时候，这时候应该是更新操作
				if (ColumnUtils.isKey(field) && field.get(obj) != null && (new Integer(field.get(obj).toString())) > 0) {
					isSave = false;
					keyFieldMap.put(field.getName(), field.get(obj));
					updateId = (Integer) field.get(obj);
				}

				// 如果是自增,并且是保存的场合，不需要添加到map中做保存
				if (isSave && ColumnUtils.isAutoIncrement(field)) {
					log.debug("字段：" + field.getName() + "是自增的不需要添加到map中");
					continue;
				}

				dataMap.put(ColumnUtils.getColumnName(field), field.get(obj));
			}catch (IllegalArgumentException e){
				e.printStackTrace();
			}catch (IllegalAccessException e){
				e.printStackTrace();
			}
		}
		if (isSave) {
			tableMap.put(tableName, dataMap);
			SaveOrUpdateDataCommand saveOrUpdateDataCommand = new SaveOrUpdateDataCommand(tableMap);
			// 执行保存操作
			baseMysqlCRUDMapper.save(saveOrUpdateDataCommand);
			return saveOrUpdateDataCommand.getId();
		}else{
			dataMap.put(KEYFIELDMAP, keyFieldMap);
			tableMap.put(tableName, dataMap);
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

	@Override
	public <T> void delete(T obj){

		// 得到表名
		String tableName = ColumnUtils.getTableName(obj.getClass());
		if (StringUtils.isEmpty(tableName)) {
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
				log.debug("该field没有配置注解不是表中在字段！");
				continue;
			}
			try{
				dataMap.put(ColumnUtils.getColumnName(field), field.get(obj));
			}catch (IllegalArgumentException e){
				e.printStackTrace();
			}catch (IllegalAccessException e){
				e.printStackTrace();
			}
		}
		tableMap.put(tableName, dataMap);
		baseMysqlCRUDMapper.delete(tableMap);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> PageResultCommand<T> search(T obj){
		String startKey = "start";
		String sizeKey = "pageSize";
		String currentPageKey = "currentPage";
		String orderByKey = "orderBy";
		Integer startVal = null;
		Integer sizeVal = null;
		Integer currentPageVal = null;
		LinkedHashMap<String,String> orderByVal = null;
		PageResultCommand<T> pageResultCommand = new PageResultCommand<T>();
		// 得到表名
		String tableName = ColumnUtils.getTableName(obj.getClass());
		if (StringUtils.isEmpty(tableName)) {
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
				if(orderByKey.equals(field.getName())) {
					orderByVal = (LinkedHashMap<String,String>) field.get(obj);
				}
				// 得到字段的配置
				Column column = field.getAnnotation(Column.class);
				if (column == null) {
					log.debug("该field没有配置注解不是表中在字段！");
					continue;
				}
				if (field.get(obj) instanceof String && field.get(obj) != null && "".equals(field.get(obj))) {
					dataMap.put(ColumnUtils.getColumnName(field), null);
				}else {					
					dataMap.put(ColumnUtils.getColumnName(field), field.get(obj));
				}
			}catch (IllegalArgumentException e){
				e.printStackTrace();
			}catch (IllegalAccessException e){
				e.printStackTrace();
			}
		}
		tableMap.put(tableName, dataMap);
		if(currentPageVal != null && currentPageVal > 0) {
			tableMap.put(startKey, startVal);
			tableMap.put(sizeKey, sizeVal);
		}
		if(orderByVal != null && orderByVal.size() > 0) {
			tableMap.put(orderByKey, orderByVal);
		}
		List<Map<String, Object>> query = baseMysqlCRUDMapper.search(tableMap);
		
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
						log.debug("该field没有配置注解不是表中在字段！");
						continue;
					}
					String name = ColumnUtils.getColumnName(field);
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
			int queryCount = baseMysqlCRUDMapper.searchCount(tableMap);
			pageResultCommand.setRecordsFiltered(queryCount);
			pageResultCommand.setRecordsTotal(queryCount);
		}
		return pageResultCommand;
	}
	
	@Override
	public <T> PageResultCommand<T> query(T t) {
		return search(t);
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
			List<Field> fieldList = new ArrayList<Field>();
			fieldList.addAll(Arrays.asList(fields));
			fieldList.addAll(Arrays.asList(clsSup.getDeclaredFields()));
			fields = new Field[fieldList.size()];
			int i = 0;
			for (Object field : fieldList.toArray()) {
				fields[i] = (Field) field;
				i++;
			}
			fields = recursionParents(clsSup, fields);
		}
		return fields;
	}

	@Override
	public <T> T findPrimaryBy(T t) {
		PageResultCommand<T> query = search(t);
		if (query != null && query.getData() != null && query.getData().size() > 0) {
			return query.getData().get(0);
		}
		return null;
	}

	@Override
	public List<LinkedHashMap<String, Object>> query(String value) {
		log.info(value);
		return baseMysqlCRUDMapper.query(value);
	}
	
	@Override
	public <T> List<T> query(String sql, Class<T> beanClass) {
		if(null == beanClass){
			return null;
		}
		List<LinkedHashMap<String, Object>> query = baseMysqlCRUDMapper.query(sql);
		if(null == query || query.size() == 0){
			return null;
		}
		List<T> list = new ArrayList<T>();
		for(Map<String, Object> map : query){
			try{
			T t = beanClass.newInstance();
			Field[] fields = getAllFields(t);
			for(Field field : fields){
				field.setAccessible(true);
				Column annotation = field.getAnnotation(Column.class);
				String name = (null != annotation && !annotation.name().equals("")) ? annotation.name() : field.getName();
				if(null == map.get(name)){
					continue;
				}
				field.set(t, map.get(name));
			}
			list.add(t);
			}catch(Exception e){
				log.error("map转对象失败",e);
			}
		}
		return list;
	}

    @Override
    public <T> Integer updateWithNull(T obj){
		String tableName = ColumnUtils.getTableName(obj.getClass());
        if (StringUtils.isEmpty(tableName)) {
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
                    log.debug("该field没有配置注解不是表中在字段！");
                    continue;
                }
                // 如果是主键，并且不是空的时候，这时候应该是更新操作
                if (ColumnUtils.isKey(field) && field.get(obj) != null && (new Integer(field.get(obj).toString())) > 0) {
                    keyFieldMap.put(field.getName(), field.get(obj));
                    updateId = (Integer) field.get(obj);
                }
                dataMap.put(ColumnUtils.getColumnName(field), field.get(obj));
            }catch (IllegalArgumentException e){
                e.printStackTrace();
            }catch (IllegalAccessException e){
                e.printStackTrace();
            }
        }
        
        if(keyFieldMap.isEmpty()) {
        	log.error("主键不能更新为null！");
        	return null;
        }
        dataMap.put(KEYFIELDMAP, keyFieldMap);
        tableMap.put(tableName, dataMap);
        SaveOrUpdateDataCommand saveOrUpdateDataCommand = new SaveOrUpdateDataCommand(tableMap);
        // 执行更新操作根据主键
        baseMysqlCRUDMapper.updateWithNull(saveOrUpdateDataCommand);
        return updateId;
    }

}

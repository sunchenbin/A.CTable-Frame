package com.gitee.sunchenbin.mybatis.actable.manager.common;

import com.gitee.sunchenbin.mybatis.actable.command.PageResultCommand;
import com.gitee.sunchenbin.mybatis.actable.command.SaveOrUpdateDataCommand;
import com.gitee.sunchenbin.mybatis.actable.dao.common.BaseCRUDMapper;
import com.gitee.sunchenbin.mybatis.actable.utils.ColumnUtils;
import com.gitee.sunchenbin.mybatis.actable.utils.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.*;

/**
 * 通用增删改查方法
 */
@Service
@Transactional
public class BaseCRUDManagerImpl implements BaseCRUDManager {

    private static final Logger log	= LoggerFactory.getLogger(BaseCRUDManagerImpl.class);

    private static final String KEYFIELDMAP = "keyFieldMap";

    @Autowired
    private BaseCRUDMapper baseCRUDMapper;

    /**
     * 根据实体对象的非Null字段作为Where条件查询结果集，如果对象的属性值都为null则返回全部数据等同于selectAll
     *
     * @param t 实体对象
     * @param <T> 实体类型
     * @return List实体对象列表
     */
    @Override
    public <T> List<T> select(T t) {

        // 得到表名
        String tableName = ColumnUtils.getTableName(t.getClass());
        if (StringUtils.isEmpty(tableName)) {
            log.error("必须使用model中的对象！");
            throw new RuntimeException("必须使用model中的对象！");
        }
        Field[] declaredFields = FieldUtils.getAllFields(t);
        Map<Object, Object> tableMap = new HashMap<Object, Object>();
        Map<Object, Object> dataMap = new HashMap<Object, Object>();
        for (Field field : declaredFields){
            // 设置访问权限
            field.setAccessible(true);
            if (!ColumnUtils.hasColumnAnnotation(field,t.getClass())) {
                log.debug("该field没有配置注解不是表中在字段！");
                continue;
            }
            try{
                dataMap.put(ColumnUtils.getColumnName(field,t.getClass()), field.get(t));
            }catch (Exception e){
                log.error("操作对象的Field出现异常",e);
                throw new RuntimeException("操作对象的Field出现异常");
            }
        }
        tableMap.put(tableName, dataMap);
        List<Map<String, Object>> query = baseCRUDMapper.select(tableMap);

        List<T> list = new ArrayList<T>();
        try{
            for (Map<String, Object> map : query){
                T newInstance = (T) t.getClass().newInstance();
                Field[] declaredFields2 = FieldUtils.getAllFields(newInstance);
                for (Field field : declaredFields2){
                    field.setAccessible(true);
                    if (!ColumnUtils.hasColumnAnnotation(field,t.getClass())) {
                        log.debug("该field没有配置注解不是表中在字段！");
                        continue;
                    }
                    String name = ColumnUtils.getColumnName(field,t.getClass());
                    buildFieldValue(map, newInstance, field, name);
                }
                list.add(newInstance);
            }
            return list;
        }catch (Exception e){
            log.error("结果集转对象失败",e);
            throw new RuntimeException("结果集转对象失败");
        }
    }

    /**
     * 根据实体对象的@IsKey主键字段的值作为Where条件查询结果，主键字段不能为null
     *
     * @param t 实体对象(只设置主键值即可，其他字段值不会读取)
     * @param <T> 实体类型
     * @return 实体对象
     */
    @Override
    public <T> T selectByPrimaryKey(T t) {

        // 得到表名
        String tableName = ColumnUtils.getTableName(t.getClass());
        if (StringUtils.isEmpty(tableName)) {
            log.error("必须使用model中的对象！");
            throw new RuntimeException("必须使用model中的对象！");
        }
        Field keyField = FieldUtils.getKeyField(t);
        if (null == keyField){
            throw new RuntimeException("当前对象没有主键不能使用该方法！");
        }
        Map<Object, Object> tableMap = new HashMap<Object, Object>();
        Map<Object, Object> dataMap = new HashMap<Object, Object>();
        try {
            // 设置访问权限
            keyField.setAccessible(true);
            Object keyValue = keyField.get(t);
            if (null == keyValue){
                throw new RuntimeException("主键字段不能为null");
            }
            dataMap.put(ColumnUtils.getColumnName(keyField,t.getClass()), keyField.get(t));
        } catch (IllegalAccessException e) {
            log.error("操作对象的Field出现异常",e);
            throw new RuntimeException("操作对象的Field出现异常");
        }
        tableMap.put(tableName, dataMap);
        List<Map<String, Object>> query = baseCRUDMapper.select(tableMap);
        if (query == null || query.size() == 0){
            return null;
        }
        Map<String, Object> stringObjectMap = query.get(0);
        try{
            T newInstance = (T) t.getClass().newInstance();
            Field[] declaredFields2 = FieldUtils.getAllFields(newInstance);
            for (Field field : declaredFields2){
                field.setAccessible(true);
                if (!ColumnUtils.hasColumnAnnotation(field,t.getClass())) {
                    log.debug("该field没有配置注解不是表中在字段！");
                    continue;
                }
                String name = ColumnUtils.getColumnName(field,t.getClass());
                buildFieldValue(stringObjectMap, newInstance, field, name);
            }
            return newInstance;
        }catch (Exception e){
            log.error("结果集转对象失败",e);
            throw new RuntimeException("结果集转对象失败");
        }
    }

    /**
     * 查询表全部数据
     *
     * @param clasz 实体对象的class
     * @param <T> 实体类型
     * @return List实体对象列表
     */
    @Override
    public <T> List<T> selectAll(Class<T> clasz) {
        // 得到表名
        String tableName = ColumnUtils.getTableName(clasz);
        if (StringUtils.isEmpty(tableName)) {
            log.error("必须使用model中的对象！");
            throw new RuntimeException("必须使用model中的对象！");
        }
        Map<Object, Object> tableMap = new HashMap<Object, Object>();
        Map<Object, Object> dataMap = new HashMap<Object, Object>();
        tableMap.put(tableName, dataMap);
        List<Map<String, Object>> query = baseCRUDMapper.select(tableMap);

        List<T> list = new ArrayList<T>();
        try{
            for (Map<String, Object> map : query){
                T newInstance = (T) clasz.newInstance();
                Field[] declaredFields2 = FieldUtils.getAllFields(newInstance);
                for (Field field : declaredFields2){
                    field.setAccessible(true);
                    if (!ColumnUtils.hasColumnAnnotation(field,clasz)) {
                        log.debug("该field没有配置注解不是表中在字段！");
                        continue;
                    }
                    String name = ColumnUtils.getColumnName(field,clasz);
                    buildFieldValue(map, newInstance, field, name);
                }
                list.add(newInstance);
            }
            return list;
        }catch (Exception e){
            log.error("结果集转对象失败",e);
            throw new RuntimeException("结果集转对象失败");
        }
    }

    /**
     * 根据实体对象的非Null字段作为Where条件查询结果集的Count，如果对象的属性值都为null则Count全表
     *
     * @param t 实体对象
     * @param <T> 实体类型
     * @return 结果数量
     */
    @Override
    public <T> int selectCount(T t) {
        // 得到表名
        String tableName = ColumnUtils.getTableName(t.getClass());
        if (StringUtils.isEmpty(tableName)) {
            log.error("必须使用model中的对象！");
            throw new RuntimeException("必须使用model中的对象！");
        }
        Field[] declaredFields = FieldUtils.getAllFields(t);
        Map<Object, Object> tableMap = new HashMap<Object, Object>();
        Map<Object, Object> dataMap = new HashMap<Object, Object>();
        for (Field field : declaredFields){
            // 设置访问权限
            field.setAccessible(true);
            if (!ColumnUtils.hasColumnAnnotation(field,t.getClass())) {
                log.debug("该field没有配置注解不是表中在字段！");
                continue;
            }
            try{
                dataMap.put(ColumnUtils.getColumnName(field,t.getClass()), field.get(t));
            }catch (Exception e){
                log.error("操作对象的Field出现异常",e);
                throw new RuntimeException("操作对象的Field出现异常");
            }
        }
        tableMap.put(tableName, dataMap);
        return baseCRUDMapper.selectCount(tableMap);
    }

    /**
     * 根据实体对象的非Null字段作为Where条件查询结果集，如果对象的属性值都为null则返回结果集的第一条使用的limit 1
     *
     * @param t 实体对象
     * @param <T> 实体类型
     * @return 实体对象
     */
    @Override
    public <T> T selectOne(T t) {
        // 得到表名
        String tableName = ColumnUtils.getTableName(t.getClass());
        if (StringUtils.isEmpty(tableName)) {
            log.error("必须使用model中的对象！");
            throw new RuntimeException("必须使用model中的对象！");
        }
        Field[] declaredFields = FieldUtils.getAllFields(t);
        Map<Object, Object> tableMap = new HashMap<Object, Object>();
        Map<Object, Object> dataMap = new HashMap<Object, Object>();
        for (Field field : declaredFields){
            // 设置访问权限
            field.setAccessible(true);
            if (!ColumnUtils.hasColumnAnnotation(field,t.getClass())) {
                log.debug("该field没有配置注解不是表中在字段！");
                continue;
            }
            try{
                dataMap.put(ColumnUtils.getColumnName(field,t.getClass()), field.get(t));
            }catch (Exception e){
                log.error("操作对象的Field出现异常",e);
                throw new RuntimeException("操作对象的Field出现异常");
            }
        }
        tableMap.put(tableName, dataMap);
        List<Map<String, Object>> query = baseCRUDMapper.select(tableMap);
        if (query == null || query.size() == 0){
            return null;
        }
        Map<String, Object> stringObjectMap = query.get(0);
        try{
            T newInstance = (T) t.getClass().newInstance();
            Field[] declaredFields2 = FieldUtils.getAllFields(newInstance);
            for (Field field : declaredFields2){
                field.setAccessible(true);
                if (!ColumnUtils.hasColumnAnnotation(field,t.getClass())) {
                    log.debug("该field没有配置注解不是表中在字段！");
                    continue;
                }
                String name = ColumnUtils.getColumnName(field,t.getClass());
                buildFieldValue(stringObjectMap, newInstance, field, name);
            }
            return newInstance;
        }catch (Exception e){
            log.error("结果集转对象失败",e);
            throw new RuntimeException("结果集转对象失败");
        }
    }

    /**
     * 根据实体对象的非Null字段作为Where条件进行删除操作，如果对象的属性值都为null则删除表全部数据
     *
     * @param t 实体对象
     * @param <T> 实体类型
     * @return 返回成功条数
     */
    @Override
    public <T> int delete(T t) {

        // 得到表名
        String tableName = ColumnUtils.getTableName(t.getClass());
        if (StringUtils.isEmpty(tableName)) {
            log.error("必须使用model中的对象！");
            throw new RuntimeException("必须使用model中的对象！");
        }
        Field[] declaredFields = FieldUtils.getAllFields(t);
        Map<Object, Object> tableMap = new HashMap<Object, Object>();
        Map<Object, Object> dataMap = new HashMap<Object, Object>();
        for (Field field : declaredFields){
            // 设置访问权限
            field.setAccessible(true);
            if (!ColumnUtils.hasColumnAnnotation(field,t.getClass())) {
                log.debug("该field没有配置注解不是表中在字段！");
                continue;
            }
            try{
                dataMap.put(ColumnUtils.getColumnName(field,t.getClass()), field.get(t));
            }catch (Exception e){
                log.error("操作对象的Field出现异常",e);
                throw new RuntimeException("操作对象的Field出现异常");
            }
        }
        tableMap.put(tableName, dataMap);
        return baseCRUDMapper.delete(tableMap);
    }

    /**
     * 根据实体对象的@IsKey主键字段的值作为Where条件进行删除操作，主键字段不能为null
     *
     * @param t 实体对象(只设置主键值即可，其他字段值不会读取)
     * @param <T> 实体类型
     * @return 返回成功条数
     */
    @Override
    public <T> int deleteByPrimaryKey(T t) {
        // 得到表名
        String tableName = ColumnUtils.getTableName(t.getClass());
        if (StringUtils.isEmpty(tableName)) {
            log.error("必须使用model中的对象！");
            throw new RuntimeException("必须使用model中的对象！");
        }
        Field keyField = FieldUtils.getKeyField(t);
        if (null == keyField){
            throw new RuntimeException("当前对象没有主键不能使用该方法！");
        }
        Map<Object, Object> tableMap = new HashMap<Object, Object>();
        Map<Object, Object> dataMap = new HashMap<Object, Object>();
        try {
            // 设置访问权限
            keyField.setAccessible(true);
            Object keyValue = keyField.get(t);
            if (null == keyValue){
                throw new RuntimeException("主键字段不能为null");
            }
            dataMap.put(ColumnUtils.getColumnName(keyField,t.getClass()), keyField.get(t));
        } catch (IllegalAccessException e) {
            log.error("操作对象的Field出现异常",e);
            throw new RuntimeException("操作对象的Field出现异常");
        }
        tableMap.put(tableName, dataMap);
        return baseCRUDMapper.delete(tableMap);
    }

    /**
     * 根据实体对象的@IsKey主键字段的值作为Where条件查询该数据是否存在，主键字段不能为null
     *
     * @param t 实体对象(只设置主键值即可，其他字段值不会读取)
     * @param <T> 实体类型
     * @return true存在，fasle不存在
     */
    @Override
    public <T> boolean existsByPrimaryKey(T t) {
        T result = selectByPrimaryKey(t);
        if(null == result){
            return false;
        }
        return true;
    }

    /**
     * 根据实体对象保存一条数据，允许没有主键，如果有主键的情况下且主键如果没有设置自增属性则必须不能为null
     *
     * @param t 实体对象
     * @param <T> 实体类型
     * @return 实体对象
     */
    @Override
    public <T> T insert(T t) {
        // 得到表名
        String tableName = ColumnUtils.getTableName(t.getClass());
        if (StringUtils.isEmpty(tableName)) {
            log.error("必须使用model中的对象！");
            throw new RuntimeException("必须使用model中的对象！");
        }
        Field[] declaredFields = FieldUtils.getAllFields(t);
        Map<Object, Map<Object, Object>> tableMap = new HashMap<Object, Map<Object, Object>>();
        Map<Object, Object> dataMap = new HashMap<Object, Object>();
        Map<String, Object> keyFieldMap = new HashMap<String, Object>();
        Field keyField = null;
        for (Field field : declaredFields){
            try{
                // 私有属性需要设置访问权限
                field.setAccessible(true);
                if (!ColumnUtils.hasColumnAnnotation(field,t.getClass())) {
                    log.debug("该field没有配置注解不是表中在字段！");
                    continue;
                }
                // 是否主键字段
                boolean isKey = ColumnUtils.isKey(field,t.getClass());
                // 是否自增
                boolean autoIncrement = ColumnUtils.isAutoIncrement(field,t.getClass());
                if(isKey){
                    keyField = field;
                }

                // 如果是主键，但不是自增，那么主键不能为空
                if (isKey && !autoIncrement && field.get(t) == null){
                    log.error("主键非自增的情况下保存时不能为null！");
                    throw new RuntimeException("主键非自增的情况下保存时不能为null！");
                }

                // 如果是自增,不需要添加到map中做保存
                if (autoIncrement) {
                    log.warn("字段：" + field.getName() + "是自增的不需要设置值");
                    continue;
                }

                dataMap.put(ColumnUtils.getColumnName(field,t.getClass()), field.get(t));
            } catch (IllegalAccessException e) {
                log.error("操作对象的Field出现异常",e);
                throw new RuntimeException("操作对象的Field出现异常");
            }
        }
        tableMap.put(tableName, dataMap);
        SaveOrUpdateDataCommand saveOrUpdateDataCommand = new SaveOrUpdateDataCommand(tableMap);
        // 执行保存操作
        int count = baseCRUDMapper.insert(saveOrUpdateDataCommand);
        // 没有保存成功
        if (count == 0){
            return null;
        }
        // 表示没有key直接根据入参对象去查询
        if (keyField == null){
            return selectOne(t);
        }
        // 表示是自增
        if(ColumnUtils.isAutoIncrement(keyField,t.getClass())){
            keyField.setAccessible(true);
            try {
                String type = keyField.getGenericType().toString();
                if (type.equals("class java.lang.Long") || type.equals("long")){
                    keyField.set(t,saveOrUpdateDataCommand.getId());
                }else if(type.equals("class java.lang.Integer") || type.equals("int")){
                    keyField.set(t,saveOrUpdateDataCommand.getId().intValue());
                }else{
                    log.error("自增主键类型超出程序支持的范围目前只支持(Long/Integer/long/int)");
                }
                return selectByPrimaryKey(t);
            } catch (IllegalAccessException e) {
                log.error("将自增主键设置给源对象出现异常",e);
                throw new RuntimeException("将自增主键设置给源对象出现异常");
            }
        }
        // 不是自增的key肯定是外部输入的，直接select
        return selectByPrimaryKey(t);
    }

    /**
     * 根据实体对象保存一条数据，如果属性值为null则不插入默认使用数据库的字段默认值，主键如果没有设置自增属性则必须不能为null
     *
     * @param t 实体对象
     * @param <T> 实体类型
     * @return 实体对象
     */
    @Override
    public <T> T insertSelective(T t) {
        // 得到表名
        String tableName = ColumnUtils.getTableName(t.getClass());
        if (StringUtils.isEmpty(tableName)) {
            log.error("必须使用model中的对象！");
            throw new RuntimeException("必须使用model中的对象！");
        }
        Field[] declaredFields = FieldUtils.getAllFields(t);
        Map<Object, Map<Object, Object>> tableMap = new HashMap<Object, Map<Object, Object>>();
        Map<Object, Object> dataMap = new HashMap<Object, Object>();
        Map<String, Object> keyFieldMap = new HashMap<String, Object>();
        Field keyField = null;
        for (Field field : declaredFields){
            try{
                // 私有属性需要设置访问权限
                field.setAccessible(true);
                if (!ColumnUtils.hasColumnAnnotation(field,t.getClass())) {
                    log.debug("该field没有配置注解不是表中在字段！");
                    continue;
                }
                // 是否主键字段
                boolean isKey = ColumnUtils.isKey(field,t.getClass());
                // 是否自增
                boolean autoIncrement = ColumnUtils.isAutoIncrement(field,t.getClass());
                if(isKey){
                    keyField = field;
                }

                // 如果是主键，但不是自增，那么主键不能为空
                if (isKey && !autoIncrement && field.get(t) == null){
                    log.error("主键非自增的情况下保存时不能为null！");
                    throw new RuntimeException("主键非自增的情况下保存时不能为null！");
                }

                // 如果是自增,不需要添加到map中做保存
                if (autoIncrement) {
                    log.warn("字段：" + field.getName() + "是自增的不需要设置值");
                    continue;
                }

                dataMap.put(ColumnUtils.getColumnName(field,t.getClass()), field.get(t));
            } catch (IllegalAccessException e) {
                log.error("操作对象的Field出现异常",e);
                throw new RuntimeException("操作对象的Field出现异常");
            }
        }
        tableMap.put(tableName, dataMap);
        SaveOrUpdateDataCommand saveOrUpdateDataCommand = new SaveOrUpdateDataCommand(tableMap);
        // 执行保存操作
        int count = baseCRUDMapper.insertSelective(saveOrUpdateDataCommand);
        // 没有保存成功
        if (count <= 0){
            return null;
        }
        // 表示没有key直接根据入参对象去查询
        if (null == keyField){
            return selectOne(t);
        }
        // 表示是自增
        if(ColumnUtils.isAutoIncrement(keyField,t.getClass())){
            keyField.setAccessible(true);
            try {
                String type = keyField.getGenericType().toString();
                if (type.equals("class java.lang.Long") || type.equals("long")){
                    keyField.set(t,saveOrUpdateDataCommand.getId());
                }else if(type.equals("class java.lang.Integer") || type.equals("int")){
                    keyField.set(t,saveOrUpdateDataCommand.getId().intValue());
                }else{
                    log.error("自增主键类型超出程序支持的范围目前只支持(Long/Integer/long/int)");
                }
                return selectByPrimaryKey(t);
            } catch (IllegalAccessException e) {
                log.error("将自增主键设置给源对象出现异常",e);
                throw new RuntimeException("将自增主键设置给源对象出现异常");
            }
        }
        // 不是自增的key肯定是外部输入的，直接select
        return selectByPrimaryKey(t);
    }

    /**
     * 根据实体对象主键作为Where条件更新其他字段数据，可以将字段值更新为null，主键必须不能为null
     *
     * @param t 实体对象
     * @param <T> 实体类型
     * @return 更新结果
     */
    @Override
    public <T> boolean updateByPrimaryKey(T t) {
        // 得到表名
        String tableName = ColumnUtils.getTableName(t.getClass());
        if (StringUtils.isEmpty(tableName)) {
            log.error("必须使用model中的对象！");
            throw new RuntimeException("必须使用model中的对象！");
        }
        Field[] declaredFields = FieldUtils.getAllFields(t);
        Map<Object, Map<Object, Object>> tableMap = new HashMap<Object, Map<Object, Object>>();
        Map<Object, Object> dataMap = new HashMap<Object, Object>();
        Map<String, Object> keyFieldMap = new HashMap<String, Object>();
        for (Field field : declaredFields){
            try{
                // 私有属性需要设置访问权限
                field.setAccessible(true);
                if (!ColumnUtils.hasColumnAnnotation(field,t.getClass())) {
                    log.debug("该field没有配置注解不是表中在字段！");
                    continue;
                }
                // 如果是主键，并且不是空的时候，这时候应该是更新操作
                if (ColumnUtils.isKey(field,t.getClass())) {
                    if (field.get(t) != null){
                        keyFieldMap.put(ColumnUtils.getColumnName(field,t.getClass()), field.get(t));
                    }else{
                        log.error("主键更新的情况下不能为null！");
                        throw new RuntimeException("主键更新的情况下不能为null！");
                    }
                }
                dataMap.put(ColumnUtils.getColumnName(field,t.getClass()), field.get(t));
            } catch (IllegalAccessException e) {
                log.error("操作对象的Field出现异常",e);
                throw new RuntimeException("操作对象的Field出现异常");
            }
        }

        if(keyFieldMap.isEmpty()) {
            log.error("不支持对没有主键的表:{}进行更新！",tableName);
            throw new RuntimeException("不支持对没有主键的表进行更新！");
        }
        dataMap.put(KEYFIELDMAP, keyFieldMap);
        tableMap.put(tableName, dataMap);
        SaveOrUpdateDataCommand saveOrUpdateDataCommand = new SaveOrUpdateDataCommand(tableMap);
        // 执行更新操作根据主键
        int count = baseCRUDMapper.updateByPrimaryKey(saveOrUpdateDataCommand);
        // 没有保存成功
        if (count <= 0){
            return false;
        }
        return true;
    }

    /**
     * 根据实体对象主键作为Where条件更新其他字段数据，如果其他字段属性值为null则忽略更新，主键必须不能为null
     *
     * @param t 实体对象
     * @param <T> 实体类型
     * @return 更新结果
     */
    @Override
    public <T> boolean updateByPrimaryKeySelective(T t) {
        // 得到表名
        String tableName = ColumnUtils.getTableName(t.getClass());
        if (StringUtils.isEmpty(tableName)) {
            log.error("必须使用model中的对象！");
            throw new RuntimeException("必须使用model中的对象！");
        }
        Field[] declaredFields = FieldUtils.getAllFields(t);
        Map<Object, Map<Object, Object>> tableMap = new HashMap<Object, Map<Object, Object>>();
        Map<Object, Object> dataMap = new HashMap<Object, Object>();
        Map<String, Object> keyFieldMap = new HashMap<String, Object>();
        for (Field field : declaredFields){
            try{
                // 私有属性需要设置访问权限
                field.setAccessible(true);
                if (!ColumnUtils.hasColumnAnnotation(field,t.getClass())) {
                    log.debug("该field没有配置注解不是表中在字段！");
                    continue;
                }
                // 如果是主键，并且不是空的时候，这时候应该是更新操作
                if (ColumnUtils.isKey(field,t.getClass())) {
                    if (field.get(t) != null){
                        keyFieldMap.put(ColumnUtils.getColumnName(field,t.getClass()), field.get(t));
                    }else{
                        log.error("主键更新的情况下不能为null！");
                        throw new RuntimeException("主键更新的情况下不能为null！");
                    }
                }
                dataMap.put(ColumnUtils.getColumnName(field,t.getClass()), field.get(t));
            } catch (IllegalAccessException e) {
                log.error("操作对象的Field出现异常",e);
                throw new RuntimeException("操作对象的Field出现异常");
            }
        }

        if(keyFieldMap.isEmpty()) {
            log.error("不支持对没有主键的表:{}进行更新！",tableName);
            throw new RuntimeException("不支持对没有主键的表进行更新！");
        }
        dataMap.put(KEYFIELDMAP, keyFieldMap);
        tableMap.put(tableName, dataMap);
        SaveOrUpdateDataCommand saveOrUpdateDataCommand = new SaveOrUpdateDataCommand(tableMap);
        // 执行更新操作根据主键
        int count = baseCRUDMapper.updateByPrimaryKeySelective(saveOrUpdateDataCommand);
        // 没有保存成功
        if (count <= 0){
            return false;
        }
        return true;
    }

    /**
     * 直接根据sql查询返回数据
     *
     * @param sql 自定义的sql
     * @return list map结构的数据
     */
    @Override
    public List<LinkedHashMap<String, Object>> query(String sql) {
        log.info(sql);
        return baseCRUDMapper.query(sql);
    }

    /**
     * 直接根据sql查询数据，并根据指定的对象类型转化后返回
     *
     * @param sql 动态sql
     * @param beanClass 返回list对象类型
     * @param <T> 实体对象类型
     * @return list的实体对象类型
     */
    @Override
    public <T> List<T> query(String sql, Class<T> beanClass) {
        if(null == beanClass){
            return null;
        }
        List<LinkedHashMap<String, Object>> query = baseCRUDMapper.query(sql);
        if(null == query || query.size() == 0){
            return null;
        }
        List<T> list = new ArrayList<T>();
        for(Map<String, Object> map : query){
            try{
                T t = beanClass.newInstance();
                Field[] fields = FieldUtils.getAllFields(t);
                for(Field field : fields){
                    field.setAccessible(true);
                    String name = ColumnUtils.getColumnName(field,t.getClass());
                    if(null == map.get(name)){
                        continue;
                    }
                    buildFieldValue(map, t, field, name);
                }
                list.add(t);
            }catch(Exception e){
                log.error("map转对象失败",e);
                throw new RuntimeException("map转对象失败");
            }
        }
        return list;
    }

    /**
     * 根据实体对象的非Null字段作为Where条件查询结果集，如果对象的属性值都为null则返回全部数据等同于selectAll+分页
     *
     * @param t 实体对象
     * @param currentPage 分页参数查询第几页，默认1
     * @param pageSize 分页参数每页显示的条数，默认10
     * @param orderby 分页使用的排序，有序的Map结构{key(要排序的字段名),value(desc/asc)}
     * @param <T> 实体类型
     * @return PageResultCommand分页对象类型
     */
    @Override
    public <T> PageResultCommand<T> search(T t, Integer currentPage, Integer pageSize,LinkedHashMap<String,String> orderby) {
        String startKey = "start";
        String sizeKey = "pageSize";
        String currentPageKey = "currentPage";
        String orderByKey = "orderBy";
        Integer sizeVal = (pageSize == null ? 10 : pageSize);
        Integer currentPageVal = (currentPage == null ? 1 : currentPage);
        Integer startVal = (currentPageVal - 1) * sizeVal;
        LinkedHashMap<String,String> orderByVal = orderby;
        PageResultCommand<T> pageResultCommand = new PageResultCommand<T>();

        // 得到表名
        String tableName = ColumnUtils.getTableName(t.getClass());
        if (StringUtils.isEmpty(tableName)) {
            log.error("必须使用model中的对象！");
            throw new RuntimeException("必须使用model中的对象！");
        }
        Field[] declaredFields = FieldUtils.getAllFields(t);
        Map<Object, Object> tableMap = new HashMap<Object, Object>();
        Map<Object, Object> dataMap = new HashMap<Object, Object>();
        for (Field field : declaredFields){
            // 设置访问权限
            field.setAccessible(true);
            try{
                if (!ColumnUtils.hasColumnAnnotation(field,t.getClass())) {
                    log.debug("该field没有配置注解不是表中在字段！");
                    continue;
                }
                dataMap.put(ColumnUtils.getColumnName(field,t.getClass()), field.get(t));
            }catch (Exception e){
                log.error("操作对象的Field出现异常",e);
                throw new RuntimeException("操作对象的Field出现异常");
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
        List<Map<String, Object>> query = baseCRUDMapper.select(tableMap);

        List<T> list = new ArrayList<T>();
        try{
            for (Map<String, Object> map : query){
                T newInstance = (T) t.getClass().newInstance();
                Field[] declaredFields2 = FieldUtils.getAllFields(newInstance);
                for (Field field : declaredFields2){
                    field.setAccessible(true);
                    if (!ColumnUtils.hasColumnAnnotation(field,t.getClass())) {
                        log.debug("该field没有配置注解不是表中在字段！");
                        continue;
                    }
                    String name = ColumnUtils.getColumnName(field,t.getClass());
                    buildFieldValue(map, newInstance, field, name);
                }
                list.add(newInstance);
            }
        }catch (Exception e){
            log.error("结果集转对象失败",e);
            throw new RuntimeException("结果集转对象失败");
        }
        if (null != list && list.size() > 0) {
            pageResultCommand.setData(list);
            int queryCount = selectCount(t);
            pageResultCommand.setRecordsFiltered(queryCount);
            pageResultCommand.setRecordsTotal(queryCount);
        }
        return pageResultCommand;
    }

    /**
     * 根据实体对象的非Null字段作为Where条件查询结果集，如果对象的属性值都为null则返回全部数据等同于selectAll+分页
     *
     * @param t 实体对象(如果要支持分页实体对象可以继承com.gitee.sunchenbin.mybatis.actable.command.BaseModel)
     *          依赖BaseModel中的currentPage：当前要查询的页码BaseModel默认1，pageSize：当前页要查询多少条BaseModel默认10，
     *          start：根据currentPage和pageSize自动算出来的开始行数，orderBy：有序的Map结构{key(要排序的字段名),value(desc/asc)}
     *          注意：如果不想依赖可以在自己的实体类中直接写着三个属性，程序会自动去读
     * @param <T> 实体类型
     * @return PageResultCommand分页对象类型
     */
    @Override
    public <T> PageResultCommand<T> search(T t) {
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
        String tableName = ColumnUtils.getTableName(t.getClass());
        if (StringUtils.isEmpty(tableName)) {
            log.error("必须使用model中的对象！");
            throw new RuntimeException("必须使用model中的对象！");
        }
        Field[] declaredFields = FieldUtils.getAllFields(t);
        Map<Object, Object> tableMap = new HashMap<Object, Object>();
        Map<Object, Object> dataMap = new HashMap<Object, Object>();
        for (Field field : declaredFields){
            // 设置访问权限
            field.setAccessible(true);
            try{
                // 获取分页start和size
                if(startKey.equals(field.getName())) {
                    startVal = (Integer) field.get(t);
                }
                if(sizeKey.equals(field.getName())) {
                    sizeVal = (Integer) field.get(t);
                }
                if(currentPageKey.equals(field.getName())) {
                    currentPageVal = (Integer) field.get(t);
                }
                if(orderByKey.equals(field.getName())) {
                    orderByVal = (LinkedHashMap<String,String>) field.get(t);
                }

                if (!ColumnUtils.hasColumnAnnotation(field,t.getClass())) {
                    log.debug("该field没有配置注解不是表中在字段！");
                    continue;
                }
                dataMap.put(ColumnUtils.getColumnName(field,t.getClass()), field.get(t));
            }catch (Exception e){
                log.error("操作对象的Field出现异常",e);
                throw new RuntimeException("操作对象的Field出现异常");
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
        List<Map<String, Object>> query = baseCRUDMapper.select(tableMap);

        List<T> list = new ArrayList<T>();
        try{
            for (Map<String, Object> map : query){
                T newInstance = (T) t.getClass().newInstance();
                Field[] declaredFields2 = FieldUtils.getAllFields(newInstance);
                for (Field field : declaredFields2){
                    field.setAccessible(true);
                    if (!ColumnUtils.hasColumnAnnotation(field,t.getClass())) {
                        log.debug("该field没有配置注解不是表中在字段！");
                        continue;
                    }
                    String name = ColumnUtils.getColumnName(field,t.getClass());
                    buildFieldValue(map, newInstance, field, name);
                }
                list.add(newInstance);
            }
        }catch (Exception e){
            log.error("结果集转对象失败",e);
            throw new RuntimeException("结果集转对象失败");
        }
        if (null != list && list.size() > 0) {
            pageResultCommand.setData(list);
            int queryCount = selectCount(t);
            pageResultCommand.setRecordsFiltered(queryCount);
            pageResultCommand.setRecordsTotal(queryCount);
        }
        return pageResultCommand;
    }

    private <T> void buildFieldValue(Map<String, Object> map, T newInstance, Field field, String name) throws IllegalAccessException {
        if (field.getGenericType().toString().equals("class java.time.LocalDateTime") && map.get(name) != null) {
            field.set(newInstance, Timestamp.valueOf(map.get(name).toString()).toLocalDateTime());
        } else if (field.getGenericType().toString().equals("class java.time.LocalDate") && map.get(name) != null) {
            field.set(newInstance, Date.valueOf(map.get(name).toString()).toLocalDate());
        } else if (field.getGenericType().toString().equals("class java.time.LocalTime") && map.get(name) != null) {
            field.set(newInstance, Time.valueOf(map.get(name).toString()).toLocalTime());
        } else if (field.getGenericType().toString().equals("class java.lang.Short") && map.get(name) != null) {
            field.set(newInstance, Short.parseShort(map.get(name).toString()));
        } else {
            field.set(newInstance, map.get(name));
        }
    }

}

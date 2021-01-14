package com.gitee.sunchenbin.mybatis.actable.utils;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.*;
import com.gitee.sunchenbin.mybatis.actable.annotation.impl.ColumnImpl;
import com.gitee.sunchenbin.mybatis.actable.command.JavaToMysqlType;
import com.gitee.sunchenbin.mybatis.actable.command.MySqlTypeAndLength;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlCharsetConstant;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlEngineConstant;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import com.google.common.base.CaseFormat;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import javax.persistence.Id;
import java.lang.reflect.Field;
import java.util.Arrays;

public class ColumnUtils {

    public static final String DEFAULTVALUE = "DEFAULT";

    public static String getTableName(Class<?> clasz){
        Table tableName = clasz.getAnnotation(Table.class);
        javax.persistence.Table tableNameCommon = clasz.getAnnotation(javax.persistence.Table.class);
        TableName tableNamePlus = clasz.getAnnotation(TableName.class);
        if (!hasTableAnnotation(clasz)){
            return null;
        }
        if (tableName != null && !StringUtils.isEmpty(tableName.name())){
            return tableName.name();
        }
        if (tableName != null && !StringUtils.isEmpty(tableName.value())){
            return tableName.value();
        }
        if (tableNameCommon != null && !StringUtils.isEmpty(tableNameCommon.name())){
            return tableNameCommon.name();
        }
        if (tableNamePlus != null && !StringUtils.isEmpty(tableNamePlus.value())){
            return tableNamePlus.value();
        }
        // 都为空时采用类名按照驼峰格式转会为表名
        return getBuildLowerName(clasz.getSimpleName());
    }

    public static String getTableComment(Class<?> clasz){
        Table table = clasz.getAnnotation(Table.class);
        TableComment tableComment = clasz.getAnnotation(TableComment.class);
        if (!hasTableAnnotation(clasz)){
            return "";
        }
        if (table != null && !StringUtils.isEmpty(table.comment())){
            return table.comment();
        }
        if (tableComment != null && !StringUtils.isEmpty(tableComment.value())){
            return tableComment.value();
        }
        return "";
    }

    public static MySqlCharsetConstant getTableCharset(Class<?> clasz){
        Table table = clasz.getAnnotation(Table.class);
        TableCharset charset = clasz.getAnnotation(TableCharset.class);
        if (!hasTableAnnotation(clasz)){
            return null;
        }
        if (table != null && table.charset() != MySqlCharsetConstant.DEFAULT){
            return table.charset();
        }
        if (charset != null && !StringUtils.isEmpty(charset.value())){
            return charset.value();
        }
        return null;
    }

    public static MySqlEngineConstant getTableEngine(Class<?> clasz){
        Table table = clasz.getAnnotation(Table.class);
        TableEngine engine = clasz.getAnnotation(TableEngine.class);
        if (!hasTableAnnotation(clasz)){
            return null;
        }
        if (table != null && table.engine() != MySqlEngineConstant.DEFAULT){
            return table.engine();
        }
        if (engine != null && !StringUtils.isEmpty(engine.value())){
            return engine.value();
        }
        return null;
    }

    public static String getColumnName(Field field, Class<?> clasz){
        Column column = getColumn(field, clasz);
        javax.persistence.Column columnCommon = field.getAnnotation(javax.persistence.Column.class);
        TableField tableField = field.getAnnotation(TableField.class);
        TableId tableId = field.getAnnotation(TableId.class);
        if(!hasColumnAnnotation(field, clasz)){
            return null;
        }
        if (column != null && !StringUtils.isEmpty(column.name())){
            return column.name().toLowerCase();
        }
        if (column != null && !StringUtils.isEmpty(column.value())){
            return column.value().toLowerCase();
        }
        if (columnCommon != null && !StringUtils.isEmpty(columnCommon.name())){
            return columnCommon.name().toLowerCase();
        }
        if (tableField != null && !StringUtils.isEmpty(tableField.value()) && tableField.exist()){
            return tableField.value().toLowerCase();
        }
        if (tableId != null && !StringUtils.isEmpty(tableId.value())){
            return tableId.value();
        }
        return getBuildLowerName(field.getName());
    }

    private static String getBuildLowerName(String name) {
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE,
                name).toLowerCase();
    }

    public static boolean isKey(Field field, Class<?> clasz){
        Column column = getColumn(field, clasz);
        if(!hasColumnAnnotation(field,clasz)){
            return false;
        }
        IsKey isKey = field.getAnnotation(IsKey.class);
        Id id = field.getAnnotation(Id.class);
        TableId tableId = field.getAnnotation(TableId.class);
        if(null != isKey){
            return true;
        }else if(column != null && column.isKey()){
            return true;
        }else if(null != id){
            return true;
        }else if(null != tableId){
            return true;
        }
        return false;
    }

    public static boolean isAutoIncrement(Field field, Class<?> clasz){
        Column column = getColumn(field, clasz);
        if(!hasColumnAnnotation(field, clasz)){
            return false;
        }
        IsAutoIncrement isAutoIncrement = field.getAnnotation(IsAutoIncrement.class);
        if(null != isAutoIncrement){
            return true;
        }else if(column != null && column.isAutoIncrement()){
            return true;
        }
        return false;
    }

    public static Boolean isNull(Field field, Class<?> clasz){
        Column column = getColumn(field, clasz);
        javax.persistence.Column columnCommon = field.getAnnotation(javax.persistence.Column.class);
        if(!hasColumnAnnotation(field,clasz)){
            return true;
        }
        IsNotNull isNotNull = field.getAnnotation(IsNotNull.class);
        if(null != isNotNull){
            return false;
        }else if(column != null && column.isNull()){
            return true;
        }else if(column != null && !column.isNull()){
            return false;
        }else if(columnCommon != null && columnCommon.nullable()){
            return true;
        }else if(columnCommon != null && !columnCommon.nullable()){
            return false;
        }
        return true;
    }

    public static String getComment(Field field, Class<?> clasz){
        Column column = getColumn(field, clasz);
        ColumnComment comment = field.getAnnotation(ColumnComment.class);
        if(!hasColumnAnnotation(field,clasz)){
            return null;
        }
        if (column != null && !StringUtils.isEmpty(column.comment())){
            return column.comment();
        }
        if (comment != null && !StringUtils.isEmpty(comment.value())){
            return comment.value();
        }
        return "";
    }

    public static String getDefaultValue(Field field, Class<?> clasz){
        Column column = getColumn(field,clasz);
        DefaultValue defaultValue = field.getAnnotation(DefaultValue.class);
        if(!hasColumnAnnotation(field,clasz)){
            return null;
        }
        if (column != null && !DEFAULTVALUE.equals(column.defaultValue())){
            return column.defaultValue();
        }
        if (defaultValue != null){
            return defaultValue.value();
        }
        return null;
    }

    public static MySqlTypeAndLength getMySqlTypeAndLength(Field field, Class<?> clasz){
        Column column = getColumn(field,clasz);
        javax.persistence.Column columnCommon = field.getAnnotation(javax.persistence.Column.class);
        ColumnType type = field.getAnnotation(ColumnType.class);
        if(!hasColumnAnnotation(field, clasz)){
            throw new RuntimeException("字段名：" + field.getName() +"没有字段标识的注解，异常抛出！");
        }
        if (column != null && column.type() != MySqlTypeConstant.DEFAULT){
            return buildMySqlTypeAndLength(field, column.type().toString().toLowerCase(), column.length(), column.decimalLength());
        }
        if (type != null && type.value() != null && type.value() != MySqlTypeConstant.DEFAULT){
            return buildMySqlTypeAndLength(field, type.value().toString().toLowerCase(), type.length(), type.decimalLength());
        }
        if (type != null && columnCommon != null && type.value() != null  && type.value() != MySqlTypeConstant.DEFAULT){
            return buildMySqlTypeAndLength(field, type.value().toString().toLowerCase(), columnCommon.length(), columnCommon.scale());
        }
        // 类型为空根据字段类型去默认匹配类型
        MySqlTypeConstant mysqlType = JavaToMysqlType.javaToMysqlTypeMap.get(field.getGenericType().toString());
        if (mysqlType == null){
            throw new RuntimeException("字段名：" + field.getName() +"不支持" + field.getGenericType().toString() + "类型转换到mysql类型，仅支持JavaToMysqlType类中的类型默认转换，异常抛出！");
        }
        String sqlType = mysqlType.toString().toLowerCase();
        // 默认类型可以使用column来设置长度
        if (column != null){
            return buildMySqlTypeAndLength(field, sqlType, column.length(), column.decimalLength());
        }
        // 默认类型可以使用type来设置长度
        if (type != null){
            return buildMySqlTypeAndLength(field, sqlType, type.length(), type.decimalLength());
        }
        // 默认类型可以使用columnCommon来设置长度
        if (columnCommon != null){
            return buildMySqlTypeAndLength(field, sqlType, columnCommon.length(), columnCommon.scale());
        }
        return buildMySqlTypeAndLength(field, sqlType, 255, 0);
    }

    private static MySqlTypeAndLength buildMySqlTypeAndLength(Field field, String type, int length, int decimalLength) {
        MySqlTypeAndLength mySqlTypeAndLength = MySqlTypeConstant.mySqlTypeAndLengthMap.get(type);
        if (mySqlTypeAndLength == null) {
            throw new RuntimeException("字段名：" + field.getName() + "使用的" + type + "类型，没有配置对应的MySqlTypeConstant，只支持创建MySqlTypeConstant中类型的字段，异常抛出！");
        }
        MySqlTypeAndLength targetMySqlTypeAndLength = new MySqlTypeAndLength();
        BeanUtils.copyProperties(mySqlTypeAndLength, targetMySqlTypeAndLength);
        if (length != 255) {
            targetMySqlTypeAndLength.setLength(length);
        }
        if (decimalLength != 0) {
            targetMySqlTypeAndLength.setDecimalLength(decimalLength);
        }
        return targetMySqlTypeAndLength;
    }

    public static boolean hasTableAnnotation(Class<?> clasz){
        Table tableName = clasz.getAnnotation(Table.class);
        javax.persistence.Table tableNameCommon = clasz.getAnnotation(javax.persistence.Table.class);
        TableName tableNamePlus = clasz.getAnnotation(TableName.class);
        if (tableName == null && tableNameCommon == null && tableNamePlus == null){
            return false;
        }
        return true;
    }

    public static boolean hasColumnAnnotation(Field field, Class<?> clasz){
        // 是否开启simple模式
        boolean isSimple = isSimple(clasz);
        // 不参与建表的字段
        String[] excludeFields = excludeFields(clasz);
        // 当前属性名在排除建表的字段内
        if (Arrays.asList(excludeFields).contains(field.getName())){
            return false;
        }
        Column column = field.getAnnotation(Column.class);
        javax.persistence.Column columnCommon = field.getAnnotation(javax.persistence.Column.class);
        TableField tableField = field.getAnnotation(TableField.class);
        IsKey isKey = field.getAnnotation(IsKey.class);
        Id id = field.getAnnotation(Id.class);
        TableId tableId = field.getAnnotation(TableId.class);
        if(column == null && columnCommon == null && (tableField == null || !tableField.exist())
                && isKey == null && id == null && tableId == null){
            // 开启了simple模式
            if (isSimple){
                return true;
            }
            return false;
        }
        return true;
    }

    private static Column getColumn(Field field, Class<?> clasz){
        // 不参与建表的字段
        String[] excludeFields = excludeFields(clasz);
        if (Arrays.asList(excludeFields).contains(field.getName())){
            return null;
        }
        Column column = field.getAnnotation(Column.class);
        if (column != null){
            return column;
        }
        // 是否开启simple模式
        boolean isSimple = isSimple(clasz);
        // 开启了simple模式
        if (isSimple){
            return new ColumnImpl();
        }
        return null;
    }

    private static String[] excludeFields(Class<?> clasz) {
        String[] excludeFields = {};
        Table tableName = clasz.getAnnotation(Table.class);
        if (tableName != null){
            excludeFields = tableName.excludeFields();
        }
        return excludeFields;
    }

    private static boolean isSimple(Class<?> clasz) {
        boolean isSimple = false;
        Table tableName = clasz.getAnnotation(Table.class);
        if (tableName != null){
            isSimple = tableName.isSimple();
        }
        return isSimple;
    }
}

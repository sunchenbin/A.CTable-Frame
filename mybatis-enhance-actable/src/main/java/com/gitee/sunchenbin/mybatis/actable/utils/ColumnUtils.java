package com.gitee.sunchenbin.mybatis.actable.utils;

import com.gitee.sunchenbin.mybatis.actable.annotation.*;
import com.google.common.base.CaseFormat;
import org.springframework.util.StringUtils;

import javax.persistence.Id;
import java.lang.reflect.Field;

public class ColumnUtils {

    public static String getTableName(Class<?> clasz){
        Table tableName = clasz.getAnnotation(Table.class);
        javax.persistence.Table tableNameCommon = clasz.getAnnotation(javax.persistence.Table.class);
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

    public static String getColumnName(Field field){
        Column column = field.getAnnotation(Column.class);
        javax.persistence.Column columnCommon = field.getAnnotation(javax.persistence.Column.class);
        if(!hasColumnAnnotation(field)){
            return null;
        }
        if (column != null && !StringUtils.isEmpty(column.name())){
            return column.name().toLowerCase();
        }
        if (columnCommon != null && !StringUtils.isEmpty(columnCommon.name())){
            return columnCommon.name().toLowerCase();
        }
        return getBuildLowerName(field.getName());
    }

    private static String getBuildLowerName(String name) {
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE,
                name).toLowerCase();
    }

    public static boolean isKey(Field field){
        Column column = field.getAnnotation(Column.class);
        javax.persistence.Column columnCommon = field.getAnnotation(javax.persistence.Column.class);
        if(!hasColumnAnnotation(field)){
            return false;
        }
        IsKey isKey = field.getAnnotation(IsKey.class);
        Id id = field.getAnnotation(Id.class);
        if(null != isKey){
            return true;
        }else if(null != id){
            return true;
        }else if(column != null && column.isKey()){
            return true;
        }
        return false;
    }

    public static boolean isAutoIncrement(Field field){
        Column column = field.getAnnotation(Column.class);
        javax.persistence.Column columnCommon = field.getAnnotation(javax.persistence.Column.class);
        if(!hasColumnAnnotation(field)){
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

    public static boolean isNull(Field field){
        Column column = field.getAnnotation(Column.class);
        javax.persistence.Column columnCommon = field.getAnnotation(javax.persistence.Column.class);
        if(!hasColumnAnnotation(field)){
            return false;
        }
        IsNotNull isNotNull = field.getAnnotation(IsNotNull.class);
        if(null != isNotNull){
            return false;
        }else if(column != null && column.isNull()){
            return true;
        }else if(columnCommon != null && columnCommon.nullable()){
            return true;
        }
        return false;
    }

    public static String getComment(Field field){
        Column column = field.getAnnotation(Column.class);
        javax.persistence.Column columnCommon = field.getAnnotation(javax.persistence.Column.class);
        ColumnComment comment = field.getAnnotation(ColumnComment.class);
        if(!hasColumnAnnotation(field)){
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

    public static String getDefaultValue(Field field){
        Column column = field.getAnnotation(Column.class);
        javax.persistence.Column columnCommon = field.getAnnotation(javax.persistence.Column.class);
        DefaultValue defaultValue = field.getAnnotation(DefaultValue.class);
        if(!hasColumnAnnotation(field)){
            return null;
        }
        if (column != null && !StringUtils.isEmpty(column.comment())){
            return column.comment();
        }
        if (defaultValue != null && !StringUtils.isEmpty(defaultValue.value())){
            return defaultValue.value();
        }
        return "NULL";
    }

    public static String getType(Field field){
        Column column = field.getAnnotation(Column.class);
        javax.persistence.Column columnCommon = field.getAnnotation(javax.persistence.Column.class);
        ColumnType type = field.getAnnotation(ColumnType.class);
        if(!hasColumnAnnotation(field)){
            return null;
        }
        if (column != null && !StringUtils.isEmpty(column.type())){
            return column.type();
        }
        if (type != null && !StringUtils.isEmpty(type.value())){
            return type.value();
        }
        return null;
    }

    public static boolean hasColumnAnnotation(Field field){
        Column column = field.getAnnotation(Column.class);
        javax.persistence.Column columnCommon = field.getAnnotation(javax.persistence.Column.class);
        if(column == null && columnCommon == null){
            return false;
        }
        return true;
    }

    public static boolean hasTableAnnotation(Class<?> clasz){
        Table tableName = clasz.getAnnotation(Table.class);
        javax.persistence.Table tableNameCommon = clasz.getAnnotation(javax.persistence.Table.class);
        if (tableName == null && tableNameCommon == null){
            return false;
        }
        return true;
    }
}

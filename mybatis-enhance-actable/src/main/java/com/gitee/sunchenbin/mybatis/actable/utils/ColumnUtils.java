package com.gitee.sunchenbin.mybatis.actable.utils;

import com.gitee.sunchenbin.mybatis.actable.annotation.*;
import com.google.common.base.CaseFormat;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;

public class ColumnUtils {

    public static String getTableName(Class<?> clasz){
        Table tableName = clasz.getAnnotation(Table.class);
        if (tableName == null && StringUtils.isEmpty(tableName.name()) && StringUtils.isEmpty(tableName.value())){
            return null;
        }
        if (!StringUtils.isEmpty(tableName.name())){
            return tableName.name();
        }
        if (!StringUtils.isEmpty(tableName.value())){
            return tableName.value();
        }
        // 都为空时采用类名按照驼峰格式转会为表名
        return getBuildLowerName(clasz.getSimpleName());
    }
    
    public static String getColumnName(Field field){
        Column column = field.getAnnotation(Column.class);
        if(column == null){
            return null;
        }
        if (StringUtils.isEmpty(column.name())){
            return getBuildLowerName(field.getName());
        }
        return column.name().toLowerCase();
    }

    private static String getBuildLowerName(String name) {
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE,
                name).toLowerCase();
    }

    public static boolean isKey(Field field){
        Column column = field.getAnnotation(Column.class);
        if(column == null){
            return false;
        }
        IsKey isKey = field.getAnnotation(IsKey.class);
        if(null != isKey){
            return true;
        }else if(column.isKey()){
            return true;
        }
        return false;
    }

    public static boolean isAutoIncrement(Field field){
        Column column = field.getAnnotation(Column.class);
        if(column == null){
            return false;
        }
        IsAutoIncrement isAutoIncrement = field.getAnnotation(IsAutoIncrement.class);
        if(null != isAutoIncrement){
            return true;
        }else if(column.isAutoIncrement()){
            return true;
        }
        return false;
    }

    public static boolean isNull(Field field){
        Column column = field.getAnnotation(Column.class);
        if(column == null){
            return false;
        }
        IsNotNull isNotNull = field.getAnnotation(IsNotNull.class);
        if(null != isNotNull){
            return false;
        }else if(column.isNull()){
            return true;
        }
        return false;
    }
}

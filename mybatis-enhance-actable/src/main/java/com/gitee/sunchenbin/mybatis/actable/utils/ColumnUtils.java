package com.gitee.sunchenbin.mybatis.actable.utils;

import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.IsAutoIncrement;
import com.gitee.sunchenbin.mybatis.actable.annotation.IsKey;
import com.gitee.sunchenbin.mybatis.actable.annotation.IsNotNull;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;

public class ColumnUtils {
    
    public static String getColumnName(Field field){
        Column column = field.getAnnotation(Column.class);
        if(column == null){
            return null;
        }
        if (StringUtils.isEmpty(column.name())){
            return field.getName();
        }
        return column.name();
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

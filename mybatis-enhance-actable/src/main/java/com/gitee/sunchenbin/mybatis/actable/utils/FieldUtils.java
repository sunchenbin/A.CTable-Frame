package com.gitee.sunchenbin.mybatis.actable.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FieldUtils {

    public static <T> Field getKeyField(T obj) {
        Field[] allFields = getAllFields(obj);
        for (Field field : allFields){
            // 设置访问权限
            field.setAccessible(true);
            if(ColumnUtils.isKey(field,obj.getClass())){
                return field;
            }
        }
        return null;
    }

    public static <T> Field[] getAllFields(T obj) {
        Field[] declaredFields = obj.getClass().getDeclaredFields();

        // 递归扫描父类的filed
        declaredFields = recursionParents(obj.getClass(), declaredFields);
        return declaredFields;
    }

    /**
     * 递归扫描父类的fields
     * @param clas
     * @param fields
     */
    @SuppressWarnings("rawtypes")
    public static  Field[] recursionParents(Class<?> clas, Field[] fields) {
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
}

package com.gitee.sunchenbin.mybatis.actable.command;

import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;

import java.util.HashMap;
import java.util.Map;

public class JavaToMysqlType {
    public static Map<String, MySqlTypeConstant> javaToMysqlTypeMap = new HashMap<String, MySqlTypeConstant>();
    static {
        javaToMysqlTypeMap.put("class java.lang.String", MySqlTypeConstant.VARCHAR);
        javaToMysqlTypeMap.put("class java.lang.Long", MySqlTypeConstant.BIGINT);
        javaToMysqlTypeMap.put("class java.lang.Integer", MySqlTypeConstant.INT);
        javaToMysqlTypeMap.put("class java.lang.Boolean", MySqlTypeConstant.BIT);
        javaToMysqlTypeMap.put("class java.math.BigInteger", MySqlTypeConstant.BIGINT);
        javaToMysqlTypeMap.put("class java.lang.Float", MySqlTypeConstant.FLOAT);
        javaToMysqlTypeMap.put("class java.lang.Double", MySqlTypeConstant.DOUBLE);
        javaToMysqlTypeMap.put("class java.lang.Short", MySqlTypeConstant.SMALLINT);
        javaToMysqlTypeMap.put("class java.math.BigDecimal", MySqlTypeConstant.DECIMAL);
        javaToMysqlTypeMap.put("class java.sql.Date", MySqlTypeConstant.DATE);
        javaToMysqlTypeMap.put("class java.util.Date", MySqlTypeConstant.DATE);
        javaToMysqlTypeMap.put("class java.sql.Timestamp", MySqlTypeConstant.DATETIME);
        javaToMysqlTypeMap.put("class java.sql.Time", MySqlTypeConstant.TIME);
        javaToMysqlTypeMap.put("class java.time.LocalDateTime", MySqlTypeConstant.DATETIME);
        javaToMysqlTypeMap.put("class java.time.LocalDate", MySqlTypeConstant.DATE);
        javaToMysqlTypeMap.put("class java.time.LocalTime", MySqlTypeConstant.TIME);
        javaToMysqlTypeMap.put("long", MySqlTypeConstant.BIGINT);
        javaToMysqlTypeMap.put("int", MySqlTypeConstant.INT);
        javaToMysqlTypeMap.put("boolean", MySqlTypeConstant.BIT);
        javaToMysqlTypeMap.put("float", MySqlTypeConstant.FLOAT);
        javaToMysqlTypeMap.put("double", MySqlTypeConstant.DOUBLE);
        javaToMysqlTypeMap.put("short", MySqlTypeConstant.SMALLINT);
        javaToMysqlTypeMap.put("char", MySqlTypeConstant.VARCHAR);
    }
}

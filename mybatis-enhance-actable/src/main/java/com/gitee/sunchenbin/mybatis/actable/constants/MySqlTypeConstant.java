package com.gitee.sunchenbin.mybatis.actable.constants;

import com.gitee.sunchenbin.mybatis.actable.annotation.LengthCount;


/**
 * 用于配置Mysql数据库中类型，并且该类型需要设置几个长度
 * 这里配置多少个类型决定了，创建表能使用多少类型
 * 例如：varchar(1)
 *     double(5,2)
 *     datetime
 *
 * @author sunchenbin
 * @version 2016年6月23日 下午5:59:33 
 */
public class MySqlTypeConstant {

	@LengthCount
	public static final  String INT = "int";
	
	@LengthCount
	public static final  String VARCHAR = "varchar";
	
	@LengthCount(LengthCount=0)
	public static final  String TEXT = "text";

	@LengthCount(LengthCount=0)
	public static final  String MEDIUMTEXT = "mediumtext";

	@LengthCount(LengthCount=0)
	public static final  String LONGTEXT = "longtext";

	@LengthCount(LengthCount=0)
	public static final  String DATETIME = "datetime";
	
	@LengthCount(LengthCount=2)
	public static final  String DECIMAL = "decimal";
	
	@LengthCount(LengthCount=2)
	public static final  String DOUBLE = "double";
	
	@LengthCount
	public static final  String CHAR = "char";
	
	@LengthCount
	public static final  String BIGINT = "bigint";
	
	@LengthCount(LengthCount=0)
	public static final  String BIT = "bit";
	
	@LengthCount(LengthCount=0)
	public static final  String TIMESTAMP = "timestamp";
	
	@LengthCount(LengthCount=0)
	public static final  String DATE = "date";
	
	@LengthCount(LengthCount=0)
	public static final  String TIME = "time";
	
	@LengthCount(LengthCount=0)
	public static final  String FLOAT = "float";
}

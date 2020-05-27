package com.gitee.sunchenbin.mybatis.actable.constants;

import com.gitee.sunchenbin.mybatis.actable.annotation.LengthCount;
import com.gitee.sunchenbin.mybatis.actable.annotation.LengthDefault;


/**
 * 用于配置Mysql数据库中类型，并且该类型需要设置几个长度
 * 这里配置多少个类型决定了，创建表能使用多少类型
 * 例如：varchar(1)
 *     decimal(5,2)
 *     datetime
 *
 * @author sunchenbin
 * @version 2016年6月23日 下午5:59:33 
 */
public class MySqlTypeConstant {

	@LengthCount
	@LengthDefault(length = 11)
	public static final  String INT = "int";
	
	@LengthCount
	@LengthDefault(length = 255)
	public static final  String VARCHAR = "varchar";

	@LengthCount
	@LengthDefault(length = 1)
	public static final  String BINARY = "binary";

	@LengthCount
	@LengthDefault(length = 255)
	public static final  String CHAR = "char";

	@LengthCount
	@LengthDefault(length = 20)
	public static final  String BIGINT = "bigint";

	@LengthCount
	@LengthDefault(length = 1)
	public static final  String BIT = "bit";

	@LengthCount
	@LengthDefault(length = 4)
	public static final  String TINYINT = "tinyint";

	@LengthCount
	@LengthDefault(length = 6)
	public static final  String SMALLINT = "smallint";

	@LengthCount
	@LengthDefault(length = 9)
	public static final  String MEDIUMINT = "mediumint";

	@LengthCount(LengthCount=2)
	@LengthDefault(length = 10, decimalLength = 2)
	public static final  String DECIMAL = "decimal";

	@LengthCount(LengthCount=0)
	public static final  String DOUBLE = "double";
	
	@LengthCount(LengthCount=0)
	public static final  String TEXT = "text";

	@LengthCount(LengthCount=0)
	public static final  String MEDIUMTEXT = "mediumtext";

	@LengthCount(LengthCount=0)
	public static final  String LONGTEXT = "longtext";

	@LengthCount(LengthCount=0)
	public static final  String DATETIME = "datetime";
	
	@LengthCount(LengthCount=0)
	public static final  String TIMESTAMP = "timestamp";
	
	@LengthCount(LengthCount=0)
	public static final  String DATE = "date";
	
	@LengthCount(LengthCount=0)
	public static final  String TIME = "time";
	
	@LengthCount(LengthCount=0)
	public static final  String FLOAT = "float";

	@LengthCount(LengthCount=0)
	public static final  String  YEAR = "year";

	@LengthCount(LengthCount=0)
	public static final  String BLOB = "blob";

	@LengthCount(LengthCount=0)
	public static final  String LONGBLOB = "longblob";

	@LengthCount(LengthCount=0)
	public static final  String MEDIUMBLOB = "mediumblob";

	@LengthCount(LengthCount=0)
	public static final  String TINYTEXT = "tinytext";

	@LengthCount(LengthCount=0)
	public static final  String TINYBLOB = "tinyblob";
}

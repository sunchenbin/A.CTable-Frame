package com.mybatis.enhance.store.constants;

import com.mybatis.enhance.store.annotation.LengthCount;


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
	public static final  String DATETIME = "datetime";
	
	@LengthCount(LengthCount=2)
	public static final  String DECIMAL = "decimal";
	
	@LengthCount(LengthCount=2)
	public static final  String DOUBLE = "double";
	
	@LengthCount
	public static final  String CHAR = "char";
	
	/**
	 * 等于java中的long
	 */
	@LengthCount
	public static final  String BIGINT = "bigint";
}

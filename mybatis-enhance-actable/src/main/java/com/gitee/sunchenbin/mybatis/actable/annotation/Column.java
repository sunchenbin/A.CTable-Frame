package com.gitee.sunchenbin.mybatis.actable.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 建表的必备注解
 *
 * @author sunchenbin, Spet
 * @version 2019/07/06
 */
// 该注解用于方法声明
@Target(ElementType.FIELD)
// VM将在运行期也保留注释，因此可以通过反射机制读取注解的信息
@Retention(RetentionPolicy.RUNTIME)
// 将此注解包含在javadoc中
@Documented
public @interface Column{

	/**
	 * 字段名
	 * 
	 * @return 字段名
	 */
	String name() default "";

	/**
	 * 字段类型
	 * 
	 * @return 字段类型
	 */
	String type() default "";

	/**
	 * 字段长度，默认是255
	 * 
	 * @return 字段长度，默认是255
	 */
	int length() default 255;

	/**
	 * 小数点长度，默认是2
	 * 
	 * @return 小数点长度，默认是2
	 */
	int decimalLength() default 2;

	/**
	 * 是否为可以为null，true是可以，false是不可以，默认为true
	 * 
	 * @return 是否为可以为null，true是可以，false是不可以，默认为true
	 */
	boolean isNull() default true;

	/**
	 * 是否是主键，默认false
	 * 
	 * @return 是否是主键，默认false
	 */
	boolean isKey() default false;

	/**
	 * 是否自动递增，默认false 只有主键才能使用
	 * 
	 * @return 是否自动递增，默认false 只有主键才能使用
	 */
	boolean isAutoIncrement() default false;

	/**
	 * 默认值，默认为null
	 * 
	 * @return 默认值，默认为null
	 */
	String defaultValue() default "NULL";

	/**
	 * 数据表字段备注
	 *
	 * @return 默认值，默认为空
	 */
	String comment() default "";
	
}

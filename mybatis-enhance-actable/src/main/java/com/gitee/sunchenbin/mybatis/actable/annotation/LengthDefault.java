package com.gitee.sunchenbin.mybatis.actable.annotation;

import java.lang.annotation.*;


/**
 * 针对数据库类型加注解，用来标记该类型长度默认值
 * @author sunchenbin
 * @version 2020年5月26日 下午6:13:15
 */
// 该注解用于方法声明
@Target(ElementType.FIELD)
// VM将在运行期也保留注释，因此可以通过反射机制读取注解的信息
@Retention(RetentionPolicy.RUNTIME)
// 将此注解包含在javadoc中
@Documented
public @interface LengthDefault {

	/**
	 * 字段长度，默认是255
	 *
	 * @return 字段长度，默认是255
	 */
	public int length() default 255;

	/**
	 * 小数点长度，默认是2
	 *
	 * @return 小数点长度，默认是2
	 */
	public int decimalLength() default 2;
	
}

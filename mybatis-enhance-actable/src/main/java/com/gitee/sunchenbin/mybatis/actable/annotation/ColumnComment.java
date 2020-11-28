package com.gitee.sunchenbin.mybatis.actable.annotation;

import java.lang.annotation.*;


/**
 * 字段的备注
 *
 * @author sunchenbin
 * @version 2020年11月09日 下午6:13:37
 */
// 该注解用于方法声明
@Target(ElementType.FIELD)
// VM将在运行期也保留注释，因此可以通过反射机制读取注解的信息
@Retention(RetentionPolicy.RUNTIME)
// 将此注解包含在javadoc中
@Documented
public @interface ColumnComment {

	/**
	 * 字段备注
	 * @return 字段备注
	 */
	String value();
}

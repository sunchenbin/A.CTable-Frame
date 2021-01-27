package com.gitee.sunchenbin.mybatis.actable.annotation;

import java.lang.annotation.*;


/**
 * 字段的默认值
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
public @interface DefaultValue {

	/**
	 * 字段的默认值
	 * @return 字段的默认值
	 */
	String value();

//	/**
//	 * 开启默认值原生模式
//	 * 原生模式介绍：默认是false表示非原生，此时value只支持字符串形式，会将value值以字符串的形式设置到字段的默认值，例如value="aa" 即sql为 DEFAULT "aa"
//	 * 如果设置isNative=true，此时如果value="CURRENT_TIMESTAMP"，即sql为 DEFAULT CURRENT_TIMESTAMP
//	 * @return
//	 */
//	boolean isNative() default false;
}

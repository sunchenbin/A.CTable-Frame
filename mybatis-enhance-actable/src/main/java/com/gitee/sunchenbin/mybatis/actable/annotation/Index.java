package com.gitee.sunchenbin.mybatis.actable.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 设置字段索引
 *
 * @author sunchenbin
 * @version 2019年6月14日 下午6:12:48
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Index {
	
	/**
	 * 索引的名字，不设置默认为{idx_当前标记字段名@Column的name}
	 * @return
	 */
	public String name() default "";
	
	/**
	 * 要建立索引的字段名，不设置默认为当前标记字段名@Column的name
	 * <p>可设置多个建立联合索引{"login_mobile","login_name"}
	 * @return
	 */
	public String[] value() default {};

}


package com.gitee.sunchenbin.mybatis.actable.annotation;

import java.lang.annotation.*;


/**
 * 标志该字段不允许为空
 * 也可通过注解：com.gitee.sunchenbin.mybatis.actable.annotation.Column的isNull属性实现
 * @author sunchenbin
 * @version 2020年5月26日 下午6:13:15
 */
// 该注解用于方法声明
@Target(ElementType.FIELD)
// VM将在运行期也保留注释，因此可以通过反射机制读取注解的信息
@Retention(RetentionPolicy.RUNTIME)
// 将此注解包含在javadoc中
@Documented
public @interface IsNotNull {
}

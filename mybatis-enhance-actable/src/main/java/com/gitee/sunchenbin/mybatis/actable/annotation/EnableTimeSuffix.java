package com.gitee.sunchenbin.mybatis.actable.annotation;

import com.gitee.sunchenbin.mybatis.actable.constants.DateTimeFormatConstant;

import java.lang.annotation.*;

/**
 * 表名时间后缀
 *
 * @author Elphen
 * @version 2021年03月11日 上午11:17:37
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EnableTimeSuffix {
    /**
     * 开启时间后缀
     * @return
     */
    boolean value() default true;

    /**
     * 时间后缀格式
     * <br> 使用常量类 {@link DateTimeFormatConstant}
     * @return
     */
    String pattern() default DateTimeFormatConstant.DATE_MONTH;
}

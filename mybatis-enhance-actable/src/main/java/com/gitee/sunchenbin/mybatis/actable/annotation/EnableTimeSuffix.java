package com.gitee.sunchenbin.mybatis.actable.annotation;

import com.gitee.sunchenbin.mybatis.actable.constants.DateTimeFormatConstant;

import java.lang.annotation.*;

/**
 * <p>
 * 表名时间后缀
 * </p>
 *
 * @author Elphen
 * @version 1.0.0
 * @date 2021-03-11 11:17
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

package com.gitee.sunchenbin.mybatis.actable.annotation;

import com.gitee.sunchenbin.mybatis.actable.constants.MySqlCharsetConstant;

import java.lang.annotation.*;


/**
 * 表字符集
 *
 * @author sunchenbin
 * @version 2020年11月11日 下午6:13:37
 */
//表示注解加在接口、类、枚举等
@Target(ElementType.TYPE)
//VM将在运行期也保留注释，因此可以通过反射机制读取注解的信息
@Retention(RetentionPolicy.RUNTIME)
//将此注解包含在javadoc中
@Documented
public @interface TableCharset {

    /**
     * 表注释
	 * 仅支持com.gitee.sunchenbin.mybatis.actable.constants.MySqlCharsetConstant中的枚举字符集
	 * @return
     */
	MySqlCharsetConstant value();
}

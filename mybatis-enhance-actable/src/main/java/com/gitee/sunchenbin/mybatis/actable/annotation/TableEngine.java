package com.gitee.sunchenbin.mybatis.actable.annotation;

import com.gitee.sunchenbin.mybatis.actable.constants.MySqlEngineConstant;

import java.lang.annotation.*;


/**
 * 表引擎
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
public @interface TableEngine {

    /**
     * 表引擎
	 * 仅支持com.gitee.sunchenbin.mybatis.actable.constants.MySqlEngineConstant中的存储引擎枚举
	 * @return
     */
	MySqlEngineConstant value();
}

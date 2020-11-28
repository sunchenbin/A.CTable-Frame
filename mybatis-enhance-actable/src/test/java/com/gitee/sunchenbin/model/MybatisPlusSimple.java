package com.gitee.sunchenbin.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 完全使用mybatis-plus的注解
 */
@TableName
public class MybatisPlusSimple {

    @TableId
    private Long id;

    @TableField
    private String name;

    @TableField
    private Date createTime;

    @TableField
    private Boolean isTrue;

    @TableField
    private Integer age;

    @TableField
    private BigDecimal price;

    @TableField
    private String identitycard;
}

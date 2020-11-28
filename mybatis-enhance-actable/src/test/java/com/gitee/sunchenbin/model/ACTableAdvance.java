package com.gitee.sunchenbin.model;

import com.gitee.sunchenbin.mybatis.actable.annotation.*;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlCharsetConstant;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlEngineConstant;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 全部采用actable自有的注解
 */
@Table(value = "t_actable_advance", comment = "actable进阶配置")
@TableCharset(MySqlCharsetConstant.UTF8)
@TableEngine(MySqlEngineConstant.InnoDB)
public class ACTableAdvance {

    @IsKey
    @IsAutoIncrement
    private Long id;

    @Column
    @Index(value = "idx_name_shop", columns = {"name","shop"})
    @IsNotNull
    private String name;

    @Column(name = "create_time", type = MySqlTypeConstant.TIMESTAMP, comment = "创建时间")
    private Date createTime;

    @Column
    @DefaultValue("true")
    private Boolean isTrue;

    @Column
    @ColumnComment("年龄")
    @ColumnType(value = MySqlTypeConstant.INT, length = 3)
    private Integer age;

    @Column(length = 10, decimalLength = 4)
    private BigDecimal price;

    @Column
    @Unique("uni_identitycard")
    private String identitycard;

    @Column
    @Unique(columns = {"name","shop"})
    private String shop;

}

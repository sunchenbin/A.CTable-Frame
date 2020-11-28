package com.gitee.sunchenbin.model;

import com.gitee.sunchenbin.mybatis.actable.annotation.*;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlCharsetConstant;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlEngineConstant;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 使用tk.mybatis的Table，Column，Id三个注解，其他的是actable的注解
 */
@Table(name = "t_tkmybatis_advance")
@TableComment("tkmyabts进阶版配置结合actable注解")
@TableCharset(MySqlCharsetConstant.UTF8)
@TableEngine(MySqlEngineConstant.InnoDB)
public class TKMybatisAdvance {

    @Id
    @IsAutoIncrement
    private Long id;

    @Column(length = 50, nullable = false)
    @Index(value = "idx_name_shop", columns = {"name","shop"})
    private String name;

    @Column(name = "create_time")
    @ColumnType(MySqlTypeConstant.TIMESTAMP)
    @ColumnComment("创建时间")
    private Date createTime;

    @Column
    @DefaultValue("0")
    private Boolean isTrue;

    @Column
    @ColumnComment("年龄")
    @ColumnType(value = MySqlTypeConstant.INT, length = 3)
    private Integer age;

    @Column(length = 10, scale = 4)
    private BigDecimal price;

    @Column
    @Unique("uni_identitycard")
    private String identitycard;

    @Column
    @Unique(columns = {"name","shop"})
    private String shop;
}

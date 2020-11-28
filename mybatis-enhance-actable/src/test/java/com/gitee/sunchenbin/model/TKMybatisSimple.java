package com.gitee.sunchenbin.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 完全使用tk.mybatis的注解
 */
@Table
public class TKMybatisSimple {

    @Id
    private Long id;

    @Column
    private String name;

    @Column
    private Date createTime;

    @Column
    private Boolean isTrue;

    @Column
    private Integer age;

    @Column
    private BigDecimal price;

    @Column
    private String identitycard;
}

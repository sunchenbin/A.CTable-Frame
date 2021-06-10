# mybatis-enhance-actable-1.5.0.RELEASE

官方文档：[ACTable官方文档地址](https://www.yuque.com/sunchenbin/actable/nfz097)

开源项目不容易，如果觉得本项目对您的工作还是有帮助的话，请在帮忙点以下Star(页面右上角)，谢谢

作者微信添加时请备注actable：![作者微信](https://images.gitee.com/uploads/images/2020/1123/184824_dcd6730d_688374.png "微信图片_20201123184809.png")

ACTable技术交流QQ群：[746531106](https://jq.qq.com/?_wv=1027&k=xE4i0dWl)

A.C.Table是对Mybatis做的增强功能，支持SpringBoot以及传统的SpringMvc项目结构，简单配置即可，该框架是为了能够使习惯了hibernate框架的开发者能够快速的入手Mybatis， “A.C.Table” 本意是自动建表的意思，A.C.Table是一个基于Spring和Mybatis的Maven项目，增强了Mybatis的功能，过配置model注解的方式来创建表，修改表结构，并且实现了共通的CUDR功能提升开发效率，同时能够兼容tk.mybatis和mybatis-plus，如需使用依赖相关的pom依赖即可，目前仅支持Mysql，后续会扩展针对其他数据库的支持。

[Javadoc文档：https://apidoc.gitee.com/sunchenbin/mybatis-enhance](https://apidoc.gitee.com/sunchenbin/mybatis-enhance)

A.C.Table是采用了Spring、Mybatis技术的Maven结构，详细介绍如下：

 **基本使用规范**

1.需要依赖mybatis-enhance-actable-1.5.0.RELEASE.jar

```
    <dependency>
        <groupId>com.gitee.sunchenbin.mybatis.actable</groupId>
        <artifactId>mybatis-enhance-actable</artifactId>
        <version>1.5.0.RELEASE</version>
    </dependency>

    <!-- 如需使用tk.mybatis同时需要依赖该jar包，之前1.3.1和1.3.0版本默认依赖了，但是有的小伙伴使用mybatis-plus会有冲突，所以这一版本默认不依赖，如需使用自行依赖即可 -->
    <dependency>
        <groupId>tk.mybatis</groupId>
        <artifactId>mapper-spring-boot-starter</artifactId>
        <version>2.1.5</version>
    </dependency>
```

2.需要配置对于actable支持的配置

```
    actable.table.auto=update
    actable.model.pack=com.xxx.store.model(ps:要扫描的用于建表做依据的model目录)
    actable.database.type=mysql
    
    本系统提供四种模式：
    
    2.1 当actable.table.auto=create时，系统启动后，会将所有的表删除掉，然后根据model中配置的结构重新建表，该操作会破坏原有数据。
    
    2.2 当actable.table.auto=update时，系统会自动判断哪些表是新建的，哪些字段要修改类型等，哪些字段要删除，哪些字段要新增，该操作不会破坏原有数据。
    
    2.3 当actable.table.auto=none时，系统不做任何处理。
    
    2.4 当actable.table.auto=add，新增表/新增字段/新增索引/新增唯一约束的功能，不做做修改和删除 (只在版本1.0.9.RELEASE及以上支持)。
    
    2.5 actable.model.pack这个配置是用来配置要扫描的用于创建表的对象的包名
        
    2.6 actable.database.type这个是用来区别数据库的，预计会支持这四种数据库mysql/oracle/sqlserver/postgresql，但目前仅支持mysql

```

3. 支持actable的mybatis配置(必备的配置)

```
    <!-- mybatis的配置文件中需要做两项配置，因为mybatis-enhance-actable项目底层是直接依赖mybatis的规范执行sql的，因此需要将其中的mapping和dao映射到一起 -->
    1. classpath*:com/gitee/sunchenbin/mybatis/actable/mapping/*/*.xml
    2. com.gitee.sunchenbin.mybatis.actable.dao.*
```

4. 扫描actable的包到spring容器中管理(必备的配置)

```
    1. com.gitee.sunchenbin.mybatis.actable.manager.*
```

 **Springboot+Mybatis的项目使用步骤方法**

1. 首先pom文件依赖actable框架

```
    <dependency>
        <groupId>com.gitee.sunchenbin.mybatis.actable</groupId>
        <artifactId>mybatis-enhance-actable</artifactId>
        <version>1.5.0.RELEASE</version>
    </dependency>

    <!-- 如需使用tk.mybatis同时需要依赖该jar包，之前1.3.1和1.3.0版本默认依赖了，但是有的小伙伴使用mybatis-plus会有冲突，所以这一版本默认不依赖，如需使用自行依赖即可 -->
    <dependency>
        <groupId>tk.mybatis</groupId>
        <artifactId>mapper-spring-boot-starter</artifactId>
        <version>2.1.5</version>
    </dependency>
```
    
2. 项目的application.properties文件配置例如下面

```
    # actable的配置信息
    actable.table.auto=update
    actable.model.pack=com.xxx.store.model(ps:要扫描的model目录)
    actable.database.type=mysql
    # mybatis的配置信息，key也可能是：mybatis.mapper-locations
    mybatis.mapperLocations=classpath*:com/gitee/sunchenbin/mybatis/actable/mapping/*/*.xml;自己的mapper.xml没有可不填
```

3. springboot启动类需要做如下配置

```
    1. 通过注解@ComponentScan配置，扫描actable要注册到spring的包路径"com.gitee.sunchenbin.mybatis.actable.manager.*"
    2. 通过注解@MapperScan配置，扫描mybatis的mapper，路径为"com.gitee.sunchenbin.mybatis.actable.dao.*"
```

 **传统Spring+Mybatis的Web项目使用步骤方法** 
 
1. 首先pom文件依赖actable框架

```
    <dependency>
        <groupId>com.gitee.sunchenbin.mybatis.actable</groupId>
        <artifactId>mybatis-enhance-actable</artifactId>
        <version>1.5.0.RELEASE</version>
    </dependency>

    <!-- 如需使用tk.mybatis同时需要依赖该jar包 -->
    <dependency>
        <groupId>tk.mybatis</groupId>
        <artifactId>mapper-spring-boot-starter</artifactId>
        <version>2.1.5</version>
    </dependency>
```

2. 在你的web项目上创建个目录比如config下面创建个文件autoCreateTable.properties文件的内容如下：

```
    actable.table.auto=update
    actable.model.pack=com.xxx.store.model(ps:要扫描的model目录)
    actable.database.type=mysql
```

3. spring的配置文件中需要做如下配置：
```
    <!-- 自动扫描(自动注入mybatis-enhance-actable的Manager)必须要配置，否则扫描不到底层的mananger方法 -->
    <context:component-scan base-package="com.gitee.sunchenbin.mybatis.actable.manager.*" />
    
    <!-- 这是mybatis-enhance-actable的功能开关配置文件,其实就是将上面第2点说的autoCreateTable.properties文件注册到spring中，以便底层的mybatis-enhance-actable的方法能够获取到-->
    <bean id="configProperties" class="org.springframework.beans.factory.config.PropertiesFactoryBean">
        <property name="locations">
            <list>
                <value>classpath*:config/autoCreateTable.properties</value>
            </list>
        </property>
    </bean>
    <bean id="propertyConfigurer" class="org.springframework.beans.factory.config.PreferencesPlaceholderConfigurer">
        <property name="properties" ref="configProperties" />
    </bean>
    
    <!-- mybatis的配置文件中需要做两项配置，因为mybatis-enhance-actable项目底层是直接依赖mybatis的规范执行sql的，因此需要将其中的mapping和dao映射到一起 -->
    1. classpath*:com/gitee/sunchenbin/mybatis/actable/mapping/*/*.xml
    2. com.gitee.sunchenbin.mybatis.actable.dao.*
    
    举例这两个配置配置的详细位置
    
    <!-- myBatis文件 -->
    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <property name="dataSource" ref="dataSource" />
        <!-- 自动扫描entity目录, 省掉Configuration.xml里的手工配置 -->
        <property name="mapperLocations">
            <array>
              <value>classpath*:自己的mappring.xml没有可不填</value>
              <value>classpath*:com/gitee/sunchenbin/mybatis/actable/mapping/*/*.xml</value>
            </array>
        </property>
        <property name="typeAliasesPackage" value="自己的model.*没有可不填" />
        <!-- <property name="configLocation" value="classpath:core/mybatis-configuration.xml" /> -->
    </bean>
    

    <!-- 如果不使用tk.mybatis需要使用这个bean成如下： -->
    <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        <property name="basePackage" value="com.gitee.sunchenbin.mybatis.actable.dao.*;自己的dao.*没有可不填" />
        <property name="sqlSessionFactoryBeanName" value="sqlSessionFactory" />
    </bean>

    <!-- 如果要使用tk.mybatis需要使用这个bean成如下： -->
    <bean class="tk.mybatis.spring.mapper.MapperScannerConfigurer">
        <property name="basePackage" value="com.gitee.sunchenbin.mybatis.actable.dao.*;自己的dao.*没有可不填"/>
        <property name="sqlSessionFactoryBeanName" value="sqlSessionFactory"/>
        <property name="properties">
            <value>
                mappers=tk.mybatis.mapper.common.Mapper,tk.mybatis.mapper.common.special.InsertListMapper
            </value>
        </property>
    </bean>
```
	
**代码用途讲解** 

    1. MySqlCharsetConstant.java这个对象里面配置的是mysql的数据类型，这里配置的类型越多，意味着创建表时能使用的类型越多
    
    2. @Column.java也是一个自定义的注解，用于标记model中的字段上，作为创建表的依据如不标记，不会被扫描到，有几个属性用来设置字段名、字段类型、长度等属性的设置，详细请看代码上的注释
    
    3. @Table.java也是一个自定义的注解，用于标记在model对象上，有一个属性name，用于设置该model生成表后的表名，如不设置该注解，则该model不会被扫描到
    
    4. @Index.java是一个自定义注解，用于标记在model中的字段上，表示为该字段创建索引，有两个属性一个是设置索引名称，一个是设置索引字段，支持多字段联合索引，如果都不设置默认为当前字段创建索引
    
    5. @Unique.java是一个自定义注解，用于标记在model中的字段上，表示为该字段创建唯一约束，有两个属性一个是设置约束名称，一个是设置约束字段，支持多字段联合约束，如果都不设置默认为当前字段创建唯一约束
    
    6. @TableComment用来配置表的注释，可用来替代@Table的comment
    
    7. @IsKey/@IsAutoIncrement/@IsNotNull用来代替 @Column中的isKey/isAutoIncrement/isNull三个属性，当然旧的配置方式仍然是支持的 
    
    8. @ColumnComment字段注释，用来替代@Column中的comment
    
    9. @DefaultValue字段默认值，用来替代@Column中的defaultValue
    
    10.@ColumnType字段类型，用来替代@Column中的type，取值范围MySqlTypeConstant.java中的枚举
    
    11.@TableCharset表字符集，用来替代@Table中的charset，取值范围MySqlCharsetConstant.java中的枚举
    
    12.@TableEngine表引擎类型，用来替代@Table中的engine，取值范围MySqlEngineConstant.java中的枚举
    
    13.支持javax.persistence包中的部分注解，用于对tk.mybatis做支持
    
        javax.persistence.Column         同       com.gitee.sunchenbin.mybatis.actable.annotation.Column
        javax.persistence.Column.name    同       com.gitee.sunchenbin.mybatis.actable.annotation.Column.name
        javax.persistence.Column.length  同       com.gitee.sunchenbin.mybatis.actable.annotation.Column.length
        javax.persistence.Column.scale   同       com.gitee.sunchenbin.mybatis.actable.annotation.Column.decimalLength
        javax.persistence.Table          同       com.gitee.sunchenbin.mybatis.actable.annotation.Table
        javax.persistence.Id             同       com.gitee.sunchenbin.mybatis.actable.annotation.IsKey
    
    13.系统启动后会去自动调用SysMysqlCreateTableManagerImpl.java的createMysqlTable()方法，没错，这就是核心方法了，负责创建、删除、修改表。

 **model的写法例子(这里的@Table和@Column都是用的actable中的，也支持使用javax.persistence包下的@Table和@Column以及@Id)**
```
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "test")
@TableComment("测试表")
@TableEngin(MySqlEngineConstant.InnoDB)
@TableCharset(MySqlCharsetConstant.UTF8MB4)
public class UserEntity extends BaseModel{

    private static final long serialVersionUID = 5199200306752426433L;
    
    // 第一种主键设置方式
    @Column(name = "id",type = MySqlTypeConstant.INT,length = 11,isKey = true,isAutoIncrement = true)
    private Integer	id;
    
    // 第二种简化的主键设置方式
    @IsKey
    @IsAutoIncrement
    @Column
    @ColumnType(MySqlTypeConstant.INT)
    private Integer	id;
    
    // 第一种设置索引的方法，这种方法会在数据库默认创建索引名称为actable_idx_{login_name},索引字段为login_name
    @Index
    // 第二种设置索引的方法，这种方法会在数据库创建索引名称为actable_idx_{t_idx_login_name},索引字段为login_name
    @Index("t_idx_login_name")
    // 第三种设置索引的方法，这种方法会在数据库创建索引名称为actable_idx_{t_idx_login_name},索引字段为login_name
    @Index(value="t_idx_login_name",columns={"login_name"})
    // 第四种设置索引的方法，可以设置联合索引，这种方法会在数据库创建索引名称为actable_idx_{login_name_mobile},索引字段为login_name和mobile
    @Index(columns={"login_name","mobile"})
    // 第五种设置索引的方法，可以设置联合索引，这种方法会在数据库创建索引名称为actable_idx_{login_name_mobile},索引字段为login_name和mobile
    @Index(value="t_idx_login_name_mobile",columns={"login_name","mobile"})
    // 唯一约束的注解的使用方法，跟@Index相同
    @Unique
    @Column(name = "login_name",type = MySqlTypeConstant.VARCHAR,length = 111)
    private String	loginName;
    
    // column的简化写法，不配置默认使用当前属性名作为字段名，当前类型默认转换至mysql支持的类型
    @Column
    private String	mobile;
    
    // column的简化写法，根据需要设置注解属性
    @Column(name = "createTime")
    private Date	create_time;
    
    @Column(name = "update_time",type = MySqlTypeConstant.DATETIME)
    private Date	update_time;
    
    @Column(name = "number",type = MySqlTypeConstant.DECIMAL,length = 10,decimalLength = 2)
    private BigDecimal	number;
    
    // 第一种设置字段非空的方法
    @Column(name = "lifecycle",type = MySqlTypeConstant.CHAR,length = 1,isNull=false)
    // 第二种设置字段非空的方法
    @IsNotNull
    @Column
    private String	lifecycle;
    
    @Column
    @DefaultValue("111")
    @ColumnComment("昵称")
    private String	realName;
}
```
 **@Column不设置类型时的默认转换规则如下（建议类型使用对象类型不要用基本数据类型）**

        javaToMysqlTypeMap.put("class java.lang.String", MySqlTypeConstant.VARCHAR);
        javaToMysqlTypeMap.put("class java.lang.Long", MySqlTypeConstant.BIGINT);
        javaToMysqlTypeMap.put("class java.lang.Integer", MySqlTypeConstant.INT);
        javaToMysqlTypeMap.put("class java.lang.Boolean", MySqlTypeConstant.BIT);
        javaToMysqlTypeMap.put("class java.math.BigInteger", MySqlTypeConstant.BIGINT);
        javaToMysqlTypeMap.put("class java.lang.Float", MySqlTypeConstant.FLOAT);
        javaToMysqlTypeMap.put("class java.lang.Double", MySqlTypeConstant.DOUBLE);
        javaToMysqlTypeMap.put("class java.math.BigDecimal", MySqlTypeConstant.DECIMAL);
        javaToMysqlTypeMap.put("class java.sql.Date", MySqlTypeConstant.DATE);
        javaToMysqlTypeMap.put("class java.util.Date", MySqlTypeConstant.DATE);
        javaToMysqlTypeMap.put("class java.sql.Timestamp", MySqlTypeConstant.DATETIME);
        javaToMysqlTypeMap.put("class java.sql.Time", MySqlTypeConstant.TIME);
        
 **共通的CUDR功能使用**

    1.使用方法很简单，大家在manager或者Controller中直接注入BaseCRUDManager这个接口就可以了
    
    2.旧的BaseMysqlCRUDManager类废弃了请不要使用

    3.注意：接口调用方法时传入的对象必须是modle中用于创建表的对象才可以
    
    4.最新版本1.3.0.RELEASE引入了对tk.mybatis的支持，方便更灵活的CUDR，仅限于使用javax.persistence的注解Column/Table/Id时生效
 
 **AC.Table支持tk.mybatis框架的CUDR方法**
    
    请参考tk.mybatis官方文档使用即可。
    
    import com.gitee.sunchenbin.mybatis.actable.annotation.Index;
    import com.gitee.sunchenbin.mybatis.actable.annotation.IsAutoIncrement;
    import com.gitee.sunchenbin.mybatis.actable.annotation.IsKey;
    import lombok.AllArgsConstructor;
    import lombok.Builder;
    import lombok.Data;
    import lombok.NoArgsConstructor;
    
    import javax.persistence.Column;
    import javax.persistence.Id;
    import javax.persistence.Table;
    import java.io.Serializable;
    import java.util.Date;
    
    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Table(name = "user_entity")
    public class UserEntity extends UserEntity1 implements Serializable {
    
        @Id
        @IsKey
        @IsAutoIncrement
        @Column
        private Long id;
    
        @Column(name = "login_name")
        @Index
        private String loginName;
    
        @Column(name = "nick_name")
        private String nickName;
    
        @Column(name = "real_name")
        private String realName;
    
        @Column(name = "password")
        private String password;
    
        @Column(name = "mobile")
        private String mobile;
    
        @Column(name = "istrue")
        private Boolean isTrue;
    
        @Column(name = "it")
        private Integer it;
    
        @Column
        private Date createTime;
    
        @Column
        private Date modifyTime;
    }
    
    import com.alex.orderapi.dao.entity.UserEntity;
    import tk.mybatis.mapper.common.Mapper;
    
    public interface UserMapper extends Mapper<UserEntity> {
    
    }
    
    @RequestMapping("/select/user1")
    public String select1(HttpServletRequest request) {
        userMapper.insert(UserEntity.builder().loginName("s").createTime(new Date()).realName("v").password("r").mobile("1").build());
        userMapper.insert(UserEntity.builder().loginName("s").createTime(new Date()).realName("v").password("r").mobile("2").build());
        userMapper.insert(UserEntity.builder().loginName("s").createTime(new Date()).realName("v").password("r").mobile("3").build());
        userMapper.insert(UserEntity.builder().loginName("s").createTime(new Date()).realName("v").password("r").mobile("4").build());
        userMapper.insert(UserEntity.builder().loginName("s").createTime(new Date()).realName("v").password("r").mobile("5").build());
        userMapper.insert(UserEntity.builder().loginName("s").createTime(new Date()).realName("v").password("r").mobile("6").build());
        userMapper.insert(UserEntity.builder().loginName("s").createTime(new Date()).realName("v").password("r").mobile("7").build());
        userMapper.insert(UserEntity.builder().loginName("s").createTime(new Date()).realName("v").password("r").mobile("8").build());
        userMapper.insert(UserEntity.builder().loginName("s").createTime(new Date()).realName("v").password("r").mobile("9").build());
        userMapper.insert(UserEntity.builder().loginName("s").createTime(new Date()).realName("v").password("r").mobile("10").build());
        userMapper.insert(UserEntity.builder().loginName("s").createTime(new Date()).realName("v").password("r").mobile("11").build());
        userMapper.insert(UserEntity.builder().loginName("s").createTime(new Date()).realName("v").password("r").mobile("12").build());
        userMapper.insert(UserEntity.builder().loginName("s").createTime(new Date()).realName("v").password("r").mobile("13").build());
        userMapper.insert(UserEntity.builder().loginName("s").createTime(new Date()).realName("v").password("r").mobile("14").build());
        userMapper.insert(UserEntity.builder().loginName("s").createTime(new Date()).realName("v").password("r").mobile("2").build());
        List<UserEntity> userEntities1 = userMapper.selectAll();
        List<UserEntity> select = userMapper.select(UserEntity.builder().mobile("2").build());
        Example example = new Example(UserEntity.class);
        example.createCriteria().andEqualTo("mobile","3").andEqualTo("loginName","s");
        List<UserEntity> userEntities = userMapper.selectByExample(example);
        UserEntity v = userMapper.selectOne(UserEntity.builder().id(30L).build());
        UserEntity userEntity = userMapper.selectByPrimaryKey(10);
        List<UserEntity> userEntities2 = userMapper.selectByRowBounds(UserEntity.builder().build(), new RowBounds(0, 3));
        List<UserEntity> userEntities3 = userMapper.selectByExampleAndRowBounds(Example.builder(UserEntity.class).build(), new RowBounds(3, 3));
        Example example1 = new Example(UserEntity.class);
        example1.setOrderByClause("id desc");
        List<UserEntity> userEntities4 = userMapper.selectByExampleAndRowBounds(example1, new RowBounds(0, 3));
        return "selectAll: " + JSON.toJSONString(userEntities1) +
                "<p> select: " + JSON.toJSONString(select) +
                "<p> selectByExample：" + JSON.toJSONString(userEntities) +
                "<p> selectOne：" + JSON.toJSONString(v) +
                "<p> selectByPrimaryKey：" + JSON.toJSONString(userEntity) +
                "<p> selectByRowBounds：" + JSON.toJSONString(userEntities2) +
                "<p> selectByExampleAndRowBounds：" + JSON.toJSONString(userEntities3) +
                "<p> selectByExampleAndRowBounds：" + JSON.toJSONString(userEntities4);
    }

 **BaseCRUDManager使用代码示例**
```
@Controller
public class TestController{
	
	@Autowired
	private TestManager testManager;
	
	@Autowired
	private BaseCRUDManager baseCRUDManager;
	
	/**
	 * 首页
	 */
	@RequestMapping("/testDate")
	@ResponseBody
	public String testDate(){
        UserEntity insert = baseCRUDManager.insert(UserEntity.builder().loginName("111").build());
        UserEntity insertSelective = baseCRUDManager.insertSelective(UserEntity.builder().loginName("222").build());
        List<UserEntity> userEntities1 = baseCRUDManager.selectAll(UserEntity.class);
        boolean b = baseCRUDManager.updateByPrimaryKey(UserEntity.builder().id(1L).mobile("1111").build());
        boolean b1 = baseCRUDManager.updateByPrimaryKeySelective(UserEntity.builder().id(2L).mobile("1111").build());
        UserEntity userEntity = baseCRUDManager.selectOne(UserEntity.builder().id(1L).mobile("1111").build());
        UserEntity userEntity1 = baseCRUDManager.selectByPrimaryKey(UserEntity.builder().id(8L).mobile("1111").build());
        List<UserEntity> select = baseCRUDManager.select(UserEntity.builder().mobile("1111").build());
        int i = baseCRUDManager.selectCount(UserEntity.builder().build());
        int sss = baseCRUDManager.delete(UserEntity.builder().realName("sss").build());
        int i1 = baseCRUDManager.deleteByPrimaryKey(UserEntity.builder().id(5L).loginName("222").build());
        boolean b2 = baseCRUDManager.existsByPrimaryKey(UserEntity.builder().id(1L).build());
        boolean b3 = baseCRUDManager.existsByPrimaryKey(UserEntity.builder().id(222L).build());
        UserEntity user = new UserEntity();
        user.setCurrentPage(1);
        user.setPageSize(5);
        LinkedHashMap<String, String> ordermap = new LinkedHashMap<>();
        ordermap.put("id",BaseModel.ASC);
        user.setOrderBy(ordermap);
        PageResultCommand<UserEntity> search = baseCRUDManager.search(user);
        PageResultCommand<UserEntity> search3 = baseCRUDManager.search(user, 1, 5, ordermap);
        return "success";
	}
}

```
 **AC.Table支持的共通类BaseCRUDManager的CUDR方法接口文档如下**
 
    /**
      * 根据实体对象的非Null字段作为Where条件查询结果集，如果对象的属性值都为null则返回全部数据等同于selectAll
      * @param t 实体对象
      * @param <T> 实体对象类型
      * @return List实体对象列表
      * @version 支持版本1.1.0.RELEASE
      */
     <T> List<T> select(T t);
 
     /**
      * 根据实体对象的@IsKey主键字段的值作为Where条件查询结果，主键字段不能为null
      * @param t 实体对象(只设置主键值即可，其他字段值不会读取)
      * @param <T> 实体对象类型
      * @return 实体对象
      * @version 支持版本1.1.0.RELEASE
      */
     <T> T selectByPrimaryKey(T t);
 
     /**
      * 查询表全部数据
      * @param clasz 实体对象的class
      * @param <T> 实体对象类型
      * @return List实体对象列表
      * @version 支持版本1.1.0.RELEASE
      */
     <T> List<T> selectAll(Class<T> clasz);
 
     /**
      * 根据实体对象的非Null字段作为Where条件查询结果集的Count，如果对象的属性值都为null则Count全表
      * @param t 实体对象
      * @param <T> 实体对象类型
      * @return 结果数量
      * @version 支持版本1.1.0.RELEASE
      */
     <T> int selectCount(T t);
 
     /**
      * 根据实体对象的非Null字段作为Where条件查询结果集，如果对象的属性值都为null则返回结果集的第一条使用的limit 1
      * @param t 实体对象
      * @param <T> 实体对象类型
      * @return 实体对象
      * @version 支持版本1.1.0.RELEASE
      */
     <T> T selectOne(T t);
 
     /**
      * 根据实体对象的非Null字段作为Where条件进行删除操作，如果对象的属性值都为null则删除表全部数据
      * @param t 实体对象
      * @param <T> 实体对象类型
      * @return 返回成功条数
      * @version 支持版本1.1.0.RELEASE
      */
     <T> int delete(T t);
 
     /**
      * 根据实体对象的@IsKey主键字段的值作为Where条件进行删除操作，主键字段不能为null
      * @param t 实体对象(只设置主键值即可，其他字段值不会读取)
      * @param <T> 实体对象类型
      * @return 返回成功条数
      * @version 支持版本1.1.0.RELEASE
      */
     <T> int deleteByPrimaryKey(T t);
 
     /**
      * 根据实体对象的@IsKey主键字段的值作为Where条件查询该数据是否存在，主键字段不能为null
      * @param t 实体对象(只设置主键值即可，其他字段值不会读取)
      * @param <T> 实体对象类型
      * @return true存在，fasle不存在
      * @version 支持版本1.1.0.RELEASE
      */
     <T> boolean existsByPrimaryKey(T t);
 
     /**
      * 根据实体对象保存一条数据，主键如果没有设置自增属性则必须不能为null
      * @param t 实体对象
      * @param <T> 实体对象类型
      * @return 实体对象
      * @version 支持版本1.1.0.RELEASE
      */
     <T> T insert(T t);
 
     /**
      * 根据实体对象保存一条数据，如果属性值为null则不插入默认使用数据库的字段默认值，主键如果没有设置自增属性则必须不能为null
      * @param t 实体对象
      * @param <T> 实体对象类型
      * @return 实体对象
      * @version 支持版本1.1.0.RELEASE
      */
     <T> T insertSelective(T t);
 
     /**
      * 根据实体对象主键作为Where条件更新其他字段数据，主键必须不能为null
      * @param t 实体对象
      * @param <T> 实体对象类型
      * @return 更新结果
      * @version 支持版本1.1.0.RELEASE
      */
     <T> boolean updateByPrimaryKey(T t);
 
     /**
      * 根据实体对象主键作为Where条件更新其他字段数据，如果其他字段属性值为null则忽略更新，主键必须不能为null
      * @param t 实体对象
      * @param <T> 实体对象类型
      * @return 更新结果
      * @version 支持版本1.1.0.RELEASE
      */
     <T> boolean updateByPrimaryKeySelective(T t);
 
    /**
     * 直接根据sql查询数据，并根据指定的对象类型转化后返回
     *
     * @param sql 动态sql
     * @param beanClass 返回list对象类型
     * @param <T> 实体对象类型
     * @return list的实体对象类型
     * @version 支持版本1.1.0.RELEASE
     */
    <T> List<T> query(String sql, Class<T> beanClass);

    /**
     * 直接根据sql查询返回数据
     *
     * @param sql 自定义的sql
     * @return list map结构的数据
     * @version 支持版本1.1.0.RELEASE
     */
    List<LinkedHashMap<String, Object>> query(String sql);
     
     /**
      * 根据实体对象的非Null字段作为Where条件查询结果集，如果对象的属性值都为null则返回全部数据等同于selectAll+分页
      *
      * @param t 实体对象
      * @param currentPage 分页参数查询第几页，默认1
      * @param pageSize 分页参数每页显示的条数，默认10
      * @param orderby 分页使用的排序，有序的Map结构{key(要排序的字段名),value(desc/asc)}
      * @param <T> 实体类型
      * @return PageResultCommand分页对象类型
      * @version 支持版本1.1.1.RELEASE
      */
     <T> PageResultCommand<T> search(T t, Integer currentPage, Integer pageSize,LinkedHashMap<String,String> orderby);
 
     /**
      * 根据实体对象的非Null字段作为Where条件查询结果集，如果对象的属性值都为null则返回全部数据等同于selectAll+分页
      * @param t 实体对象
      * @param <T> 实体对象类型
      * @return PageResultCommand分页对象类型
      * @version 支持版本1.1.1.RELEASE
      */
     <T> PageResultCommand<T> search(T t);


 **######### mybatis增加功能自动创建表——A.C.Table版本说明################** 
1. 该版本修复了修改主键同时修改其类型引起的error(版本1.0.1)
2. 该版本修复了根据model创建时没有创建父类中的字段的问题（ps：目前只支持扫描一层继承）(版本1.0.1)
3. 该版本增加了对唯一约束的支持(版本1.0.1)
4. 从原有的框架中剥离出来，支持任意结构的spring+mybatis的框架使用(版本1.0.1)
5. 再次声明A.C.Table目前仅支持mysql数据库(版本1.0.1)
6. 修复了不同数据库中有相同表名时，启动报错的问题。(版本1.0.2)
7. 修复了model属性名与表字段名不一致时公共的查询方法查不出数据的问题。(版本1.0.2)
8. 增加了对公共的CUDR方法的优化，保存成功会返回id，query查询时可以设置参数进行分页查询（pageSize:int类型标识每页数量，currentPage:int类型标识当前第几页，start:int类型从第几条开始，orderField：string类型排序字段，sortStr：string类型排序方式(desc,asc)）(版本1.0.3)
9. 增加了对Mysql的longtext和mediumtext两种字段类型的支持，公共的CUDR方法的优化，原query方法更正为search，现query方法支持动态sql查询，原orderField字段只支持单个字段的排序，现修改为orderBy字段，支持数据类型为LinkedHashMap<String, String>，有序，key为字段名，value为排序方式(版本1.0.4)
10. 增加对mysql数据库（timestamp/time/date/float/bit）五种数据类型的支持(版本1.0.5)
11. 增加对springboot框架的支持(版本1.0.5)
12. 删除旧版本在@Colum中实现唯一约束的方式(版本1.0.6.RELEASE)
13. 增加了新的唯一约束实现方式@Unique，支持多字段聚合约束(版本1.0.6.RELEASE)
14. 增加了索引创建方式@Index，支持多字段聚合索引(版本1.0.6.RELEASE)
15. 修复query查询方法无法返回父类字段数据的bug(版本1.0.6.RELEASE)
16. 修复原本是主键，现在依然主键，修改该字段的其他信息时会报multiple primary key defined(例如id为int(11)，改为int(10)后，就可重现次bug)(版本1.0.7.RELEASE)
17. 增加对字段备注的支持，使用方式@Column的comment属性(版本1.0.7.RELEASE)
18. 修复issues/IZ6WQ：bit类型的默认值设置失败，默认值可以使用0、1、true、false(版本1.0.8.1.RELEASE)
19. 修复issues/IYTJ1：使用@Unique进行联合约束，启动项目自动创建表结构后，删除联合约束报错(版本1.0.8.1.RELEASE)
20. 迭代issues/IYW9F:mybatis.model.pack支持多包扫描","或者";"隔开(版本1.0.8.1.RELEASE)
21. 修复issues/I160LP:drop拼写的问题(版本1.0.9.RELEASE)
22. 优化issues/I1IENW:@Index,@Unique创建索引和唯一约束的实现逻辑，默认会给索引名和约束名增加前缀actable_idx_和actable_uni_方便更新删除时只针对这两个前缀的进行删除更新，避免删掉手动创建的字段的索引约束  (版本1.0.9.RELEASE)
23. 修复issues/I16OZQ::@Index,@Unique在只设置了索引名称没有设置索引字段时，报错的bug，并且原有name改为value，原有value改为columns(版本1.0.9.RELEASE)
24. 迭代issues/I1IF5E:增加对tinyint/smallint/mediumint/year/blob/longblob/mediumblob/tinytext/tinyblob/binary字段类型的支持 (版本1.0.9.RELEASE)
25. 迭代issues/I1IF5Q:框架模式新增add模式，本模式下只具备，新增表/新增字段/新增索引/新增唯一约束的功能，不会做修改和删除 (版本1.0.9.RELEASE)
26. 迭代issues/I193FC:@Column的name属性改为非必填，不填默认使用属性名作为表字段名 (版本1.0.9.RELEASE)
27. 迭代issues/I193FC:@Column的type属性改为非必填，不填默认使用属性的数据类型进行转换，转换失败的字段不会添加 (版本1.0.9.RELEASE)
    
    支持java类型转mysql类型如下：
    
        java.lang.String
        java.lang.Long
        java.lang.Integer
        java.lang.Boolean
        java.math.BigInteger
        java.lang.Float
        java.lang.Double
        java.math.BigDecimal
        java.sql.Date
        java.util.Date
        java.sql.Timestamp
        java.sql.Time
        
    本次迭代至1.0.9.RELEASE，极大的简化了注解的使用复杂度，在保留原有复杂的自定义配置能力的同时，增加了更多的默认适配能力
    也就是对于@Column标签如果对字段命名等没有任何要求的情况下，直接使用标签即可，无需配置类型等参数，会默认根据上面支持的类型去进行匹配转换
28. 迭代issues/I1ILS6:@IsKey/@IsAutoIncrement/@IsNotNull用来代替 @Column中的isKey/isAutoIncrement/isNull三个属性，当然旧的配置方式仍然是支持的 (版本1.0.9.RELEASE)
29. 紧急修复1.0.9.RELEASE版本CUDR的bug，请不要使用1.0.9.RELEASE版本(版本1.0.9.1.RELEASE)
30. 迭代issues/I1IVXK:BaseMysqlCRUDManager该工具类废弃，请勿使用，新增工具类BaseCRUDManager，新的insert接口取消了对主键的integer类型的限定，主键可以自由使用类型(版本1.1.0.RELEASE)

    新增工具类BaseCRUDManager的方法列表如下，详细接口文档见文档结尾部分：
    
        <T> List<T> select(T t);
        <T> T selectByPrimaryKey(T t);
        <T> List<T> selectAll(Class<T> clasz);
        <T> int selectCount(T t);
        <T> T selectOne(T t);
        <T> int delete(T t);
        <T> int deleteByPrimaryKey(T t);
        <T> boolean existsByPrimaryKey(T t);
        <T> T insert(T t);
        <T> T insertSelective(T t);
        <T> boolean updateByPrimaryKey(T t);
        <T> boolean updateByPrimaryKeySelective(T t);
        <T> List<T> query(String sql, Class<T> beanClass);
        List<LinkedHashMap<String, Object>> query(String sql);
31. 迭代issues/I1JC91:工具类BaseCRUDManager新增对分页查询的支持，新增两个search接口一个实体中包含分页字段，一个通过方法参数传递分页字段(版本1.1.1.RELEASE)

    新增工具类BaseCRUDManager的方法列表如下，详细接口文档见文档结尾部分：
    
        <T> PageResultCommand<T> search(T t, Integer currentPage, Integer pageSize,LinkedHashMap<String,String> orderby);
        <T> PageResultCommand<T> search(T t);        
32. 建表的字段时如果@Column没有设置字段名，那么默认会读属性的名字，根据驼峰转换逻辑，进行转换例如loginName会转换为login_name作为字段名，如果没有驼峰也就是全是小写字母，那么直接作为字段名，如果设置了Column(name="LOGIN_NAME")那么默认创建的字段会转换为小写，也就是login_name，所以字段名都会强制转换为小写(版本1.2.0.RELEASE，**该版本对于之前建表字段名使用大写的项目不向下兼容，要升级至此版本需谨慎**)
33. 修复索引约束创建完成后，修改字段名的情况下报错的bug(版本1.2.0.RELEASE，**该版本对于之前建表字段名使用大写的项目不向下兼容，要升级至此版本需谨慎**)
34. 为了防止配置信息引起歧义(版本1.2.0.RELEASE，**该版本对于之前建表字段名使用大写的项目不向下兼容，要升级至此版本需谨慎**)

        mybatis.table.auto      变为      actable.table.auto
        mybatis.model.pack      变为      actable.model.pack
        mybatis.database.type   变为      actable.database.type
35. 修复建表时没有读取继承类中的字段信息的问题(版本1.2.0.RELEASE，**该版本对于之前建表字段名使用大写的项目不向下兼容，要升级至此版本需谨慎**)   
36. 迭代issues/I1LUAZ:修复实体对象字段有多个大写，转换为列名时，只转换了第一个下划线，提供对@Table标签的驼峰转换的支持，不填表名默认使用类名驼峰转换(版本1.2.1.RELEASE)
37. 支持使用javax.persistence的Column/Table/Id等注解生成及更新表(版本1.3.0.RELEASE)
    
        javax.persistence.Column = com.gitee.sunchenbin.mybatis.actable.annotation.Column
        javax.persistence.Column.name = com.gitee.sunchenbin.mybatis.actable.annotation.Column.name
        javax.persistence.Column.length = com.gitee.sunchenbin.mybatis.actable.annotation.Column.length
        javax.persistence.Column.scale = com.gitee.sunchenbin.mybatis.actable.annotation.Column.decimalLength
        javax.persistence.Table = com.gitee.sunchenbin.mybatis.actable.annotation.Table
        javax.persistence.Id = com.gitee.sunchenbin.mybatis.actable.annotation.IsKey
38. 引入对tk.mybatis的支持，方便更灵活的CUDR，仅限于使用javax.persistence包的Column/Table/Id等注解时生效，如使用actable的注解则只支持BaseCRUDManager(版本1.3.0.RELEASE)
39. 增加对json数据类型的支持(版本1.3.0.RELEASE)
40. 增加注解@ColumnComment字段注释，用来替代@Column中的comment(版本1.3.0.RELEASE)
41. 增加注解@DefaultValue字段默认值，用来替代@Column中的defaultValue(版本1.3.0.RELEASE)
42. 增加注解@ColumnType字段类型，用来替代@Column中的type，取值范围MySqlTypeConstant中的枚举(版本1.3.0.RELEASE)
43. 增加注解@TableComment用来配置表的注释，可用来替代@Table的comment(版本1.3.0.RELEASE)
44. 迭代issues/I24UU4:增加注解@TableCharset用来配置表的字符集，可用来替代@Table的charset，取值范围MySqlCharsetConstant中的枚举(版本1.3.1.RELEASE)
45. 增加注解@TableEngine用来配置表的注释，可用来替代@Table的engine，取值范围MySqlEngineConstant中的枚举(版本1.3.1.RELEASE)
46. 删除默认依赖tk.mybatis的pom配置，避免跟其他框架冲突，如需使用自行依赖即可，只要依赖tk.mybatis的pom，actable可以完全兼容使用(版本1.3.2.RELEASE)
47. 修复bug，当设置bit类型的默认值时，启动时会一直日志输出修改改字段的日志记录(版本1.4.0.RELEASE，该版本不向下兼容需修改少量代码，谨慎升级)
48. 修复bug，当使用默认的字段类型时，可以通过@Column的length和decimalLength来配置长度(版本1.4.0.RELEASE，该版本不向下兼容需修改少量代码，谨慎升级)
49. @ColumnType增加length和decimalLength两个属性用来配置该类型的长度(版本1.4.0.RELEASE，该版本不向下兼容需修改少量代码，谨慎升级)
50. 优化注解@Column的type字段以及注解@ColumnType的value字段，类型从String改为枚举类型，枚举类为MySqlTypeConstant(版本1.4.0.RELEASE，该版本不向下兼容需修改少量代码，谨慎升级)
51. 优化注解@TableCharset的value和@TableEngine的value字段类型从String改为枚举类型，枚举类为MySqlCharsetConstant和MySqlEngineConstant(版本1.4.0.RELEASE，该版本不向下兼容需修改少量代码，谨慎升级)
52. 修复bug，当id使用Long类型时，采用BaseCRUDManagerImpl中的保存和更新方法时报错的问题(版本1.4.0.RELEASE，该版本不向下兼容需修改少量代码，谨慎升级)
53. 优化引入对mybatis-plus的支持，方便更灵活的CUDR，支持@TableName/@TableField/@TableId三个注解(版本1.4.0.RELEASE，该版本不向下兼容需修改少量代码，谨慎升级)
54. 移除@LengthCount和@LengthDefault两个注解(版本1.4.0.RELEASE，该版本不向下兼容需修改少量代码，谨慎升级)
55. issues/I28XQY：新增java默认转换LocalDateTime/LocalDate/LocalTime数据类型的支持(版本1.4.1.RELEASE)
56. 修正DOUBLE和FLOAT支持设置小数长度(版本1.4.1.RELEASE)
57. Table标签新增属性isSimple表示是否开启简单的配置模式，如果开启，实体的属性可以不打@Column的标签全部按照默认规则驼峰转换创建字段，默认为false不开启(版本1.4.1.RELEASE)
58. Table标签新增属性excludeFields用来标记排除属性名，排除掉的属性不参与建表(版本1.4.1.RELEASE)
59. 检查实体类有重名的表名会抛异常(版本1.4.1.RELEASE)
60. issues/I2BL9O：新增数据包装数据类型Short，删除byte类型的支持(版本1.4.2.RELEASE)
61. 修复bug，当使用@TableEngine和@TableCharset是失效的问题(版本1.4.3.RELEASE)
62. 修复bug，@Column.isNull()和@javax.persistence.Column.nullable()(版本1.4.4.RELEASE)
63. 修复bug，float和double类型默认小数位数为2，如果强制指定0的情况下会失效，修复后效果为默认效数位数0，人工指定则按照人工指定的来(版本1.4.5.RELEASE)
64. issues/I2DU0K，优化默认值指定的缺陷，之前无法指定某些特殊类型的默认值，例如CURRENT_TIMESTAMP这种，现在支持了，会根据字段类型来决定，目前只有String和Boolean采用字符串传参的方式使用的是'#{}'，其他类型均使用原生方式拼接sql使用的是'${}'(版本1.4.6.RELEASE)
65. 新增注解@IsNativeDefValue值的范围是true或者false基于上一版本64条更新日志，系统会自动根据数据类型判断是否使用原生的值作为DEFAULT，这里增加这个注解用来可以手动指定，增加了一些灵活性(版本1.4.7.RELEASE)
66. 新增两个配置项，允许自行指定生成的索引和约束的前缀，不指定默认使用actable_idx_和actable_uni_
        
        actable.index.prefix=自己定义的索引前缀
        actable.unique.prefix=自己定义的唯一约束前缀
67. 如果字段名中包含 ` 关键字会默认忽略掉，因为actable建表时自动给所有字段名增加过了，这个主要是为了避免字段名是数据库的关键字(版本1.4.8.RELEASE)
68. 修复66条自定义索引和唯一约束前缀的功能bug(版本1.4.8.RELEASE)
69. SysMysqlCreateTableManagerImpl.getAllFields方法改为public可被重写(版本1.4.9.RELEASE)
70. issues/I38G7Q：修复避免子类重写付类属性时创建重复字段报错的问题(版本1.4.9.RELEASE)
71. issues/I2CVCS：迭代优化actable.model.pack配置属性支持模糊扫描，支持类似com.bz.**.entity且支持配置多个路径英文逗号或者分号隔开(版本1.4.9.RELEASE)
72. 增加注解@EnableTimeSuffix，支持表名添加时间后缀(版本1.5.0.RELEASE)
73. issues/I3PRVK：添加@IgnoreUpdate注解 标注当前字段参与创建不参与更新，解决使用@DefaultValue("NULL ON UPDATE CURRENT_TIMESTAMP")时当前字段每次重启项目都会进行更新操作的问题(版本1.5.0.RELEASE)
74. issues/I3NGD2：添加@IgnoreTable注解 设置某个model实体不参与建表操作(版本1.5.0.RELEASE)
75. issues/I3TNMB：修复使用mybatisplus时使用了多租户的插件时，使用@ColumnComment注解报错的问题(版本1.5.0.RELEASE)

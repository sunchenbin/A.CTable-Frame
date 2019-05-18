package com.gitee.sunchenbin.mybatis.actable.manager.handler;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.gitee.sunchenbin.mybatis.actable.constants.Constants;
import com.gitee.sunchenbin.mybatis.actable.manager.system.SysMysqlCreateTableManager;
import com.gitee.sunchenbin.mybatis.actable.manager.util.ConfigurationUtil;

/**
 * 启动时进行处理的实现类
 * @author chenbin.sun
 *
 */
@SuppressWarnings("restriction")
@Service
public class StartUpHandlerImpl implements StartUpHandler {
	
	private static final Logger	log	= LoggerFactory.getLogger(StartUpHandlerImpl.class);
	
	@Autowired
	private ConfigurationUtil springContextUtil;
	
	/** 数据库类型：mysql */
	public static String MYSQL = "mysql";
	
	/** 数据库类型：oracle */
	public static String ORACLE = "oracle";
	
	/** 数据库类型：sqlserver */
	public static String SQLSERVER = "sqlserver";
	
	/** 数据库类型：postgresql */
	public static String POSTGRESQL = "postgresql";
	
	/** 数据库类型  */
	private static String databaseType = null;
	
	@Autowired
	private SysMysqlCreateTableManager sysMysqlCreateTableManager;

	@PostConstruct
	public void startHandler() {
		// 获取配置信息
		databaseType = springContextUtil.getConfig(Constants.DATABASE_TYPE_KEY) == null ? MYSQL : springContextUtil.getConfig(Constants.DATABASE_TYPE_KEY);
		
		// 执行mysql的处理方法
		if (MYSQL.equals(databaseType)) {
			
			log.info("databaseType=mysql，开始执行mysql的处理方法");
			
			sysMysqlCreateTableManager.createMysqlTable();
		}else if (ORACLE.equals(databaseType)) {
			
			log.info("databaseType=oracle，开始执行oracle的处理方法");
		}else if (SQLSERVER.equals(databaseType)) {
			
			log.info("databaseType=sqlserver，开始执行sqlserver的处理方法");
		}else if (POSTGRESQL.equals(databaseType)) {
			
			log.info("databaseType=postgresql，开始执行postgresql的处理方法");
		}else{
			
			log.info("没有找到符合条件的处理方法！");
		}
	}
	
	
}

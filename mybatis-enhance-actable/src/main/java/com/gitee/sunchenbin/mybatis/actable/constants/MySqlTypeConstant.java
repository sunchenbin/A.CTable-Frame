package com.gitee.sunchenbin.mybatis.actable.constants;

import com.gitee.sunchenbin.mybatis.actable.command.MySqlTypeAndLength;

import java.util.HashMap;
import java.util.Map;


/**
 * 用于配置Mysql数据库中类型，并且该类型需要设置几个长度
 * 这里配置多少个类型决定了，创建表能使用多少类型
 * 例如：varchar(1)
 *     decimal(5,2)
 *     datetime
 *
 * @author sunchenbin
 * @version 2016年6月23日 下午5:59:33
 */
public enum MySqlTypeConstant {

	DEFAULT(null,null,null),
	INT(1, 11, null),
	VARCHAR(1, 255, null),
	BINARY(1, 1, null),
	CHAR(1, 255, null),
	BIGINT(1, 20, null),
	BIT(1, 1, null),
	TINYINT(1, 4, null),
	SMALLINT(1, 6, null),
	MEDIUMINT(1, 9, null),
	DECIMAL(2, 10, 2),
	DOUBLE(2, 10, 0),
	TEXT(0, null, null),
	MEDIUMTEXT(0, null, null),
	LONGTEXT(0, null, null),
	DATETIME(0, null, null),
	TIMESTAMP(0, null, null),
	DATE(0, null, null),
	TIME(0, null, null),
	FLOAT(2, 10, 0),
	YEAR(0, null, null),
	BLOB(0, null, null),
	LONGBLOB(0, null, null),
	MEDIUMBLOB(0, null, null),
	TINYTEXT(0, null, null),
	TINYBLOB(0, null, null),
	JSON(0, null, null);

	private Integer lengthCount;
	private Integer lengthDefault;
	private Integer decimalLengthDefault;

	public Integer getLengthCount() {
		return lengthCount;
	}

	public Integer getLengthDefault() {
		return lengthDefault;
	}

	public Integer getDecimalLengthDefault() {
		return decimalLengthDefault;
	}

	MySqlTypeConstant(Integer lengthCount, Integer lengthDefault, Integer decimalLengthDefault){
		this.lengthCount = lengthCount;
		this.lengthDefault = lengthDefault;
		this.decimalLengthDefault = decimalLengthDefault;
	}

	// 获取Mysql的类型，以及类型需要设置几个长度，如需扩展改变这个对象的值即可
	/**
	 * 获取Mysql的类型，以及类型需要设置几个长度，这里构建成map的样式
	 * 构建Map(字段名(小写),需要设置几个长度(0表示不需要设置，1表示需要设置一个，2表示需要设置两个))
	 */
	public static Map<String, MySqlTypeAndLength> mySqlTypeAndLengthMap;

	static {
		mySqlTypeAndLengthMap = new HashMap<String, MySqlTypeAndLength>();
		for (MySqlTypeConstant type: MySqlTypeConstant.values()) {
			mySqlTypeAndLengthMap.put(type.toString().toLowerCase(), new MySqlTypeAndLength(type.getLengthCount(), type.getLengthDefault(), type.getDecimalLengthDefault(), type.toString().toLowerCase()));
		}
	}

}

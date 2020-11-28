package com.gitee.sunchenbin.mybatis.actable.command;

import java.util.Map;

public class SaveOrUpdateDataCommand {

	private Long id;

	private Map<Object, Map<Object, Object>> tableMap;

	public SaveOrUpdateDataCommand(){

	}

	public SaveOrUpdateDataCommand(Map<Object, Map<Object, Object>> tableMap){
		this.tableMap = tableMap;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Map<Object, Map<Object, Object>> getTableMap() {
		return tableMap;
	}

	public void setTableMap(Map<Object, Map<Object, Object>> tableMap) {
		this.tableMap = tableMap;
	}

}



package com.gitee.sunchenbin.mybatis.actable.command;

import java.util.Map;

public class SaveOrUpdateDataCommand {

	private Integer id;

	private Map<Object, Map<Object, Object>> tableMap;
	
	public SaveOrUpdateDataCommand(){
		
	}
	
	public SaveOrUpdateDataCommand(Map<Object, Map<Object, Object>> tableMap){
		this.tableMap = tableMap;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Map<Object, Map<Object, Object>> getTableMap() {
		return tableMap;
	}

	public void setTableMap(Map<Object, Map<Object, Object>> tableMap) {
		this.tableMap = tableMap;
	}

}



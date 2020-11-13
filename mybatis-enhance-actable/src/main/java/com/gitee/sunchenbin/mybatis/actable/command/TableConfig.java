package com.gitee.sunchenbin.mybatis.actable.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TableConfig {

    private List<Object> list = new ArrayList<Object>();

    private Map<String,Object> map = new HashMap<String, Object>();

    public TableConfig(List<Object> list, Map<String, Object> map) {
        if (list != null){
            this.list = list;
        }
        if(map != null){
            this.map = map;
        }
    }

    public TableConfig(List<Object> list) {
        if (list != null){
            this.list = list;
        }
    }

    public TableConfig(Map<String, Object> map) {
        this.map = map;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    public List<Object> getList() {
        return list;
    }

    public void setList(List<Object> list) {
        this.list = list;
    }
}

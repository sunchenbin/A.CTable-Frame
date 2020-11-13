package com.gitee.sunchenbin.mybatis.actable.command;

import java.util.Date;

public class SysMysqlTable {

    /**
     * 表注释
     */
    /** 字符集的后缀 */
    public static final String TABLE_COLLATION_SUFFIX = "_general_ci";
    /** 字符集 */
    public static final String TABLE_COLLATION_KEY = "table_collation";
    /** 注释 */
    public static final String TABLE_COMMENT_KEY = "table_comment";
    /** 引擎 */
    public static final String TABLE_ENGINE_KEY = "engine";

    private String table_catalog;
    private String table_schema;
    private String table_name;
    private String table_type;
    private String engine;
    private Long version;
    private String row_format;
    private Long table_rows;
    private Long avg_row_length;
    private Long data_length;
    private Long max_data_length;
    private Long index_length;
    private Long data_free;
    private Long auto_increment;
    private Date create_time;
    private Date update_time;
    private Date check_time;
    private String table_collation;
    private Long checksum;
    private String create_options;
    private String table_comment;

    public String getTable_catalog() {
        return table_catalog;
    }

    public void setTable_catalog(String table_catalog) {
        this.table_catalog = table_catalog;
    }

    public String getTable_schema() {
        return table_schema;
    }

    public void setTable_schema(String table_schema) {
        this.table_schema = table_schema;
    }

    public String getTable_name() {
        return table_name;
    }

    public void setTable_name(String table_name) {
        this.table_name = table_name;
    }

    public String getTable_type() {
        return table_type;
    }

    public void setTable_type(String table_type) {
        this.table_type = table_type;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getRow_format() {
        return row_format;
    }

    public void setRow_format(String row_format) {
        this.row_format = row_format;
    }

    public Long getTable_rows() {
        return table_rows;
    }

    public void setTable_rows(Long table_rows) {
        this.table_rows = table_rows;
    }

    public Long getAvg_row_length() {
        return avg_row_length;
    }

    public void setAvg_row_length(Long avg_row_length) {
        this.avg_row_length = avg_row_length;
    }

    public Long getData_length() {
        return data_length;
    }

    public void setData_length(Long data_length) {
        this.data_length = data_length;
    }

    public Long getMax_data_length() {
        return max_data_length;
    }

    public void setMax_data_length(Long max_data_length) {
        this.max_data_length = max_data_length;
    }

    public Long getIndex_length() {
        return index_length;
    }

    public void setIndex_length(Long index_length) {
        this.index_length = index_length;
    }

    public Long getData_free() {
        return data_free;
    }

    public void setData_free(Long data_free) {
        this.data_free = data_free;
    }

    public Long getAuto_increment() {
        return auto_increment;
    }

    public void setAuto_increment(Long auto_increment) {
        this.auto_increment = auto_increment;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    public Date getCheck_time() {
        return check_time;
    }

    public void setCheck_time(Date check_time) {
        this.check_time = check_time;
    }

    public String getTable_collation() {
        return table_collation;
    }

    public void setTable_collation(String table_collation) {
        this.table_collation = table_collation;
    }

    public Long getChecksum() {
        return checksum;
    }

    public void setChecksum(Long checksum) {
        this.checksum = checksum;
    }

    public String getCreate_options() {
        return create_options;
    }

    public void setCreate_options(String create_options) {
        this.create_options = create_options;
    }

    public String getTable_comment() {
        return table_comment;
    }

    public void setTable_comment(String table_comment) {
        this.table_comment = table_comment;
    }
}

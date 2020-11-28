package com.gitee.sunchenbin.mybatis.actable.command;

public class MySqlTypeAndLength {
    private Integer lengthCount;
    private Integer length;
    private Integer decimalLength;
    private String  type;

    public MySqlTypeAndLength(){

    }

    public MySqlTypeAndLength(Integer lengthCount, Integer length, Integer decimalLength, String type){
        this.lengthCount = lengthCount;
        this.type = type;
        this.length = length;
        this.decimalLength = decimalLength;
    }

    public Integer getLengthCount() {
        return lengthCount;
    }

    public void setLengthCount(Integer lengthCount) {
        this.lengthCount = lengthCount;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getDecimalLength() {
        return decimalLength;
    }

    public void setDecimalLength(Integer decimalLength) {
        this.decimalLength = decimalLength;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

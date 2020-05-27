package com.gitee.sunchenbin.mybatis.actable.command;

import com.gitee.sunchenbin.mybatis.actable.annotation.LengthCount;
import com.gitee.sunchenbin.mybatis.actable.annotation.LengthDefault;
import lombok.Data;

@Data
public class MySqlTypeAndLength {
    private Integer lengthCount;
    private Integer length;
    private Integer decimalLength;

    public MySqlTypeAndLength(LengthCount lengthCount, LengthDefault lengthDefault){
        this.lengthCount = lengthCount.LengthCount();
        if (null != lengthDefault){
            this.length = lengthDefault.length();
            this.decimalLength = lengthDefault.decimalLength();
        }
    }
}

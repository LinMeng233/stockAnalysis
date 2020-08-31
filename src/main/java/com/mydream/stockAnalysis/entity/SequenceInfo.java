package com.mydream.stockAnalysis.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@TableName("cloud.sequence_info")
public class SequenceInfo {
    @TableId("sequence_name")
    private String sequenceName;
    private Long sequenceValue;
    private Long maxValue;
    private Integer sequenceLen;
    private Integer increment;
    private Date updateTime;

    public String getSequenceName() {
        return sequenceName;
    }

    public void setSequenceName(String sequenceName) {
        this.sequenceName = sequenceName;
    }

    public Long getSequenceValue() {
        return sequenceValue;
    }

    public void setSequenceValue(Long sequenceValue) {
        this.sequenceValue = sequenceValue;
    }

    public Long getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Long maxValue) {
        this.maxValue = maxValue;
    }

    public Integer getSequenceLen() {
        return sequenceLen;
    }

    public void setSequenceLen(Integer sequenceLen) {
        this.sequenceLen = sequenceLen;
    }

    public Integer getIncrement() {
        return increment;
    }

    public void setIncrement(Integer increment) {
        this.increment = increment;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}

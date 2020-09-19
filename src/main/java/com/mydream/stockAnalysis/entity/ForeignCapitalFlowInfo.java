package com.mydream.stockAnalysis.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
public class ForeignCapitalFlowInfo {
    //交易时间
    @TableId(value = "transaction_date")
    private String transactionDate;
    //序号
    private Integer serialNumber;
    //沪股通流入金额
    private BigDecimal shInflowAmount;
    //深股通流入金额
    private BigDecimal szInflowAmount;
    //当日沪深流入总金额
    private BigDecimal totalInflowAmount;
    //沪股通剩余金额
    private BigDecimal shBalance;
    //深股通剩余金额
    private BigDecimal szBalance;
    //沪深剩余总金额
    private BigDecimal totalBalance;
    //创建日期时间
    private Date createDatetime;
    //更新日期时间
    private Date updateDatetime;
}

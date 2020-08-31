package com.mydream.stockAnalysis.entity;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Builder
@Data
public class CalculationResultOf255TradingDay {
    //交易日
    private String transactionDate;
    //255个交易日总数
    private BigDecimal sum;
    //255个交易日平均数
    private BigDecimal inflow_amount;
    //255个交易日平均数
    private BigDecimal average;
    //255个交易日方差
    private BigDecimal variance;
    //255个交易日标准差
    private BigDecimal standardDeviation;
    //当日流入总额-255个交易日平均数-2*255个交易日标准差
    private BigDecimal calculationResult;
    //创建日期时间
    private Date createDatetime;
    //更新日期时间
    private Date updateDatetime;
}

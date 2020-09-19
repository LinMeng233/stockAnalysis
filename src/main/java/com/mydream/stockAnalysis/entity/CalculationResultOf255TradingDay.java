package com.mydream.stockAnalysis.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Builder
@Data
public class CalculationResultOf255TradingDay {
    //交易日
    @TableId
    private String transactionDate;
    //255个交易日总数
    private BigDecimal sum;
    //255个交易日平均数
    private BigDecimal inflowAmount;
    //255个交易日平均数
    private BigDecimal average;
    //255个交易日方差
    private BigDecimal variance;
    //255个交易日标准差
    private BigDecimal standardDeviation;
    //当日流入总额-255个交易日平均数-2*255个交易日标准差
    private BigDecimal buyParameter;
    //是否能买
    private String canBuy;
    //当日流入总额-255个交易日平均数+2*255个交易日标准差
    private BigDecimal sellingParameter;
    //是否能卖
    private String canSelling;
    //创建日期时间
    private Date createDatetime;
    //更新日期时间
    private Date updateDatetime;
}

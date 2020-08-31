package com.mydream.stockAnalysis.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
public class CapitalFlowInfo implements Serializable {
    //交易日期
    @NonNull
    private String transactionDate;
    //沪股通
    private BigDecimal shInflowAmount;
    //深股通
    private BigDecimal szInflowAmount;
    //港股通深圳
    private BigDecimal shOutflowAmount;
    //港股通上海
    private BigDecimal szOutflowAmount;
    //北向资金
    private BigDecimal northboundFund;
    //南向资金
    private BigDecimal southboundFund;
}

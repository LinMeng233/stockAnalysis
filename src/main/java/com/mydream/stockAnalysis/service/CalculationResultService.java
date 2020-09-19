package com.mydream.stockAnalysis.service;

import com.mydream.stockAnalysis.entity.CalculationResultOf255TradingDay;

import javax.annotation.Resource;

public interface CalculationResultService {
    public void orderForeignCapitalFlowInfo();

    public CalculationResultOf255TradingDay calculateAllResult(String transactionDate, Integer day);

    public  CalculationResultOf255TradingDay calcuteDayData(String transactionDate);
}

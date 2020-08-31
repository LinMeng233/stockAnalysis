package com.mydream.stockAnalysis.service.impl;

import com.mydream.stockAnalysis.entity.CalculationResultOf255TradingDay;
import com.mydream.stockAnalysis.mapper.CalculationResultOf255TradingDayMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service("calculationResultService")
public class CalculationResultServiceImpl {
    @Resource
    private CalculationResultOf255TradingDayMapper calculationResultOf255TradingDayMapper;

    public void addCalculationResult(CalculationResultOf255TradingDay calculationResultOf255TradingDay){
        String transactionDate = calculationResultOf255TradingDay.getTransactionDate();
        CalculationResultOf255TradingDay history = calculationResultOf255TradingDayMapper.selectById(transactionDate);
        if(history != null){
            calculationResultOf255TradingDayMapper.updateById(calculationResultOf255TradingDay);
            return;
        }
        calculationResultOf255TradingDay.setCreateDatetime(new Date());
        calculationResultOf255TradingDayMapper.insert(calculationResultOf255TradingDay);
    }
}

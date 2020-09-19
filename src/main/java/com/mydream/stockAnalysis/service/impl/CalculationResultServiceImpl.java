package com.mydream.stockAnalysis.service.impl;

import com.mydream.stockAnalysis.dto.CapitalFlowInfo;
import com.mydream.stockAnalysis.dto.TushareCapitalFlowInnerData;
import com.mydream.stockAnalysis.entity.CalculationResultOf255TradingDay;
import com.mydream.stockAnalysis.entity.ForeignCapitalFlowInfo;
import com.mydream.stockAnalysis.mapper.CalculationResultOf255TradingDayMapper;
import com.mydream.stockAnalysis.service.CalculationResultService;
import com.mydream.stockAnalysis.service.ForeignCapitalFlowService;
import com.mydream.stockAnalysis.service.client.TushareClientService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service("calculationResultService")
public class CalculationResultServiceImpl implements CalculationResultService {
    @Resource
    private CalculationResultOf255TradingDayMapper calculationResultOf255TradingDayMapper;
    @Resource
    private ForeignCapitalFlowService foreignCapitalFlowService;

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

    public void orderForeignCapitalFlowInfo(){
        List<ForeignCapitalFlowInfo> foreignCapitalFlowInfoList = foreignCapitalFlowService.queryOrderlyCapitalFlowInfoList();
        int serialNumber = 1;
        for(ForeignCapitalFlowInfo foreignCapitalFlowInfo:foreignCapitalFlowInfoList){
            foreignCapitalFlowInfo.setSerialNumber(serialNumber++);
            foreignCapitalFlowService.updateForeignCapitalFlowInfo(foreignCapitalFlowInfo);
        }
    }

    public CalculationResultOf255TradingDay calculateAllResult(String transactionDate, Integer day) {
        List<ForeignCapitalFlowInfo> foreignCapitalFlowInfoList = foreignCapitalFlowService.queryOrderlyCapitalFlowInfoListByTransactionDateAndLimit(transactionDate, day);
        BigDecimal inflowAmount = foreignCapitalFlowService.queryForeignCapitalFlowInfo(transactionDate).getTotalInflowAmount();
        BigDecimal sum = this.calculateSum(foreignCapitalFlowInfoList);
        BigDecimal average = this.calculateAverage(foreignCapitalFlowInfoList);
        BigDecimal variance = this.calculateVariance(average, foreignCapitalFlowInfoList);
        BigDecimal standardDeviation = this.calculateStandardDeviation(variance, foreignCapitalFlowInfoList);
        BigDecimal buyParameter = this.calculateBuyParameter(inflowAmount, average, standardDeviation);
        String canBuy = buyParameter.compareTo(new BigDecimal(0)) > 0 ? "Y" : "N";
        BigDecimal sellingParameter = this.calculateSellingParameter(inflowAmount, average, standardDeviation);
        String canSelling = sellingParameter.compareTo(new BigDecimal(0)) < 0 ? "Y" : "N";
        CalculationResultOf255TradingDay calculationResultOf255TradingDay = CalculationResultOf255TradingDay.builder().transactionDate(transactionDate).
                sum(sum).average(average).variance(variance).standardDeviation(standardDeviation).inflowAmount(inflowAmount).
                buyParameter(buyParameter).canBuy(canBuy).sellingParameter(sellingParameter).canSelling(canSelling).build();
        this.addCalculationResult(calculationResultOf255TradingDay);
        return calculationResultOf255TradingDay;
    }

    public BigDecimal calculateBuyParameter(BigDecimal inflowAmount,BigDecimal average,BigDecimal standardDeviation){
        return inflowAmount.subtract(average).subtract(standardDeviation).subtract(standardDeviation);
    }

    public BigDecimal calculateSellingParameter(BigDecimal inflowAmount,BigDecimal average,BigDecimal standardDeviation){
        return inflowAmount.subtract(average).add(standardDeviation).add(standardDeviation);
    }

    public void calculateSum(String transactionDate, Integer day){
        List<ForeignCapitalFlowInfo> foreignCapitalFlowInfoList = foreignCapitalFlowService.queryOrderlyCapitalFlowInfoListByTransactionDateAndLimit(transactionDate,day);
        BigDecimal sum = this.calculateSum(foreignCapitalFlowInfoList);
        CalculationResultOf255TradingDay calculationResultOf255TradingDay = CalculationResultOf255TradingDay.builder().transactionDate(transactionDate).sum(sum).build();
        this.addCalculationResult(calculationResultOf255TradingDay);
    }

    private BigDecimal calculateSum(List<ForeignCapitalFlowInfo> foreignCapitalFlowInfos){
        BigDecimal sum = new BigDecimal("0");
        for (ForeignCapitalFlowInfo foreignCapitalFlowInfo:foreignCapitalFlowInfos){
            sum = sum.add(foreignCapitalFlowInfo.getTotalInflowAmount());
        }
        return sum;
    }

    public void calculateAverage(String transactionDate, Integer day){
        List<ForeignCapitalFlowInfo> foreignCapitalFlowInfoList = foreignCapitalFlowService.queryOrderlyCapitalFlowInfoListByTransactionDateAndLimit(transactionDate,day);
        BigDecimal average = this.calculateAverage(foreignCapitalFlowInfoList);
        CalculationResultOf255TradingDay calculationResultOf255TradingDay = CalculationResultOf255TradingDay.builder().transactionDate(transactionDate).average(average).build();
        this.addCalculationResult(calculationResultOf255TradingDay);
    }

    private BigDecimal calculateAverage(List<ForeignCapitalFlowInfo> foreignCapitalFlowInfos){
        BigDecimal sum = new BigDecimal("0");
        for (ForeignCapitalFlowInfo foreignCapitalFlowInfo:foreignCapitalFlowInfos){
            sum = sum.add(foreignCapitalFlowInfo.getTotalInflowAmount());
        }
        return sum.divide(new BigDecimal(foreignCapitalFlowInfos.size()),4, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateVariance(BigDecimal average,List<ForeignCapitalFlowInfo> foreignCapitalFlowInfos){
        if(average == null){
            average = this.calculateAverage(foreignCapitalFlowInfos);
        }
        BigDecimal variance = new BigDecimal("0");
        for(ForeignCapitalFlowInfo foreignCapitalFlowInfo:foreignCapitalFlowInfos) {
            variance = variance.add((foreignCapitalFlowInfo.getTotalInflowAmount().subtract(average)).pow(2).divide(new BigDecimal(foreignCapitalFlowInfos.size()),8,RoundingMode.HALF_UP));
        }
        return variance;
    }

    private BigDecimal calculateStandardDeviation(BigDecimal variance,List<ForeignCapitalFlowInfo> foreignCapitalFlowInfos){
        if(variance == null){
            variance = this.calculateVariance(null,foreignCapitalFlowInfos);
        }
        double varianceDouble = variance.doubleValue();
        return new BigDecimal(String.valueOf(Math.sqrt(varianceDouble)));
    }

    @Resource
    private TushareClientService tushareClientService;
    public  CalculationResultOf255TradingDay calcuteDayData(String transactionDate){
        List<CapitalFlowInfo> capitalFlowInfoList = tushareClientService.getCapitalFlowInfo(transactionDate,transactionDate);
        if(CollectionUtils.isEmpty(capitalFlowInfoList)){
            return null;
        }
        foreignCapitalFlowService.addForeignCapitalTransaction(capitalFlowInfoList.get(0));
        return this.calculateAllResult(transactionDate,252);
    }
}

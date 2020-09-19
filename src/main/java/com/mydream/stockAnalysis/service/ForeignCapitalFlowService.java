package com.mydream.stockAnalysis.service;

import com.mydream.stockAnalysis.dto.CapitalFlowInfo;
import com.mydream.stockAnalysis.entity.ForeignCapitalFlowInfo;

import java.math.BigDecimal;
import java.util.List;

public interface ForeignCapitalFlowService {

    public void addForeignCapitalTransaction(CapitalFlowInfo capitalFlowInfo);

    public ForeignCapitalFlowInfo queryForeignCapitalFlowInfo(String transactionDate);

    public List<ForeignCapitalFlowInfo> queryOrderlyCapitalFlowInfoList();


    public void updateForeignCapitalFlowInfo(ForeignCapitalFlowInfo foreignCapitalFlowInfo);

    public List<ForeignCapitalFlowInfo> queryOrderlyCapitalFlowInfoListByTransactionDateAndLimit(String TransactionDate,Integer limit);
}

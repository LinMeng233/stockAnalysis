package com.mydream.stockAnalysis.service;

import com.mydream.stockAnalysis.dto.CapitalFlowInfo;

import java.math.BigDecimal;

public interface ForeignCapitalFlowService {

    public void addForeignCapitalTransaction(CapitalFlowInfo capitalFlowInfo);
}

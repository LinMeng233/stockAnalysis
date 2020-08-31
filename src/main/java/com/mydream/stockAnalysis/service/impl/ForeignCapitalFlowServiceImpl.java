package com.mydream.stockAnalysis.service.impl;

import com.mydream.stockAnalysis.mapper.ForeignCapitalFlowInfoMapper;
import com.mydream.stockAnalysis.dto.CapitalFlowInfo;
import com.mydream.stockAnalysis.entity.ForeignCapitalFlowInfo;
import com.mydream.stockAnalysis.service.ForeignCapitalFlowService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service("foreignCapitalFlowService")
public class ForeignCapitalFlowServiceImpl implements ForeignCapitalFlowService {
    @Resource
    private ForeignCapitalFlowInfoMapper foreignCapitalFlowInfoMapper;
    @Override
    public void addForeignCapitalTransaction(CapitalFlowInfo capitalFlowInfo){
        String transactionDate = capitalFlowInfo.getTransactionDate();
        ForeignCapitalFlowInfo historyInfo = foreignCapitalFlowInfoMapper.selectById(transactionDate);
        if(historyInfo != null){
            ForeignCapitalFlowInfo updateInfo = ForeignCapitalFlowInfo.builder().transactionDate(transactionDate)
                    .shInflowAmount(capitalFlowInfo.getShInflowAmount()).szInflowAmount(capitalFlowInfo.getSzInflowAmount())
                            .totalInflowAmount(capitalFlowInfo.getNorthboundFund()).build();
            foreignCapitalFlowInfoMapper.updateById(updateInfo);
            return;
        }
        ForeignCapitalFlowInfo insertInfo = ForeignCapitalFlowInfo.builder().transactionDate(transactionDate)
                .shInflowAmount(capitalFlowInfo.getShInflowAmount()).szInflowAmount(capitalFlowInfo.getSzInflowAmount())
                .totalInflowAmount(capitalFlowInfo.getNorthboundFund()).createDatetime(new Date()).build();
        foreignCapitalFlowInfoMapper.insert(insertInfo);
    }


}

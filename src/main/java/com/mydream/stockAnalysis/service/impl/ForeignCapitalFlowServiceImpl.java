package com.mydream.stockAnalysis.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
        int index = foreignCapitalFlowInfoMapper.selectCount(new LambdaQueryWrapper<ForeignCapitalFlowInfo>().lt(ForeignCapitalFlowInfo::getTransactionDate,transactionDate))+1;
        if(historyInfo != null){
            ForeignCapitalFlowInfo updateInfo = ForeignCapitalFlowInfo.builder().transactionDate(transactionDate)
                    .shInflowAmount(capitalFlowInfo.getShInflowAmount()).szInflowAmount(capitalFlowInfo.getSzInflowAmount())
                            .totalInflowAmount(capitalFlowInfo.getNorthboundFund()).serialNumber(index).build();
            foreignCapitalFlowInfoMapper.updateById(updateInfo);
            return;
        }
        ForeignCapitalFlowInfo insertInfo = ForeignCapitalFlowInfo.builder().transactionDate(transactionDate)
                .shInflowAmount(capitalFlowInfo.getShInflowAmount()).szInflowAmount(capitalFlowInfo.getSzInflowAmount())
                .totalInflowAmount(capitalFlowInfo.getNorthboundFund()).createDatetime(new Date()).serialNumber(index).build();
        foreignCapitalFlowInfoMapper.insert(insertInfo);
    }

    public ForeignCapitalFlowInfo queryForeignCapitalFlowInfo(String transactionDate){
        return foreignCapitalFlowInfoMapper.selectById(transactionDate);
    }


    @Override
    public List<ForeignCapitalFlowInfo>
    queryOrderlyCapitalFlowInfoList(){
        return foreignCapitalFlowInfoMapper.selectList(new LambdaQueryWrapper<ForeignCapitalFlowInfo>().orderBy(true,true,ForeignCapitalFlowInfo::getTransactionDate));
    }


    @Override
    public void updateForeignCapitalFlowInfo(ForeignCapitalFlowInfo foreignCapitalFlowInfo){
        foreignCapitalFlowInfoMapper.updateById(foreignCapitalFlowInfo);
    }

    @Override
    public List<ForeignCapitalFlowInfo> queryOrderlyCapitalFlowInfoListByTransactionDateAndLimit(String transactionDate,Integer limit){
        return foreignCapitalFlowInfoMapper.selectList(new LambdaQueryWrapper<ForeignCapitalFlowInfo>().orderBy(true,false,ForeignCapitalFlowInfo::getTransactionDate).
                lt(ForeignCapitalFlowInfo::getTransactionDate,transactionDate).last(String.format("limit %s",limit)));
    }


}

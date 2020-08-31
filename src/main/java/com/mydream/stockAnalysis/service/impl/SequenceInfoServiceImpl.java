package com.mydream.stockAnalysis.service.impl;


import com.mydream.stockAnalysis.entity.SequenceInfo;
import com.mydream.stockAnalysis.mapper.SequenceInfoMapper;
import com.mydream.stockAnalysis.service.SequenceInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

@Service("sequenceInfoService")
public class SequenceInfoServiceImpl implements SequenceInfoService {
    @Resource
    private SequenceInfoMapper sequenceInfoMapper;

    @Transactional(
            propagation = Propagation.REQUIRES_NEW
    )
    @Override
    public long getSequenceValue(String sequenceName){
        SequenceInfo sequenceInfo  = sequenceInfoMapper.selectById(sequenceName);
        if(sequenceInfo == null){
            sequenceInfo = initSequenceInfo(sequenceName);
        }
        updateSequence(sequenceInfo);
        return sequenceInfo.getSequenceValue();
    }

    private SequenceInfo initSequenceInfo(String sequenceName){
        SequenceInfo sequenceInfo = new SequenceInfo();
        sequenceInfo.setSequenceName(sequenceName);
        sequenceInfo.setSequenceValue(10L);
        sequenceInfo.setMaxValue(999999L);
        sequenceInfo.setSequenceLen(6);
        sequenceInfo.setIncrement(1);
        sequenceInfo.setUpdateTime(new Date());
        sequenceInfoMapper.insert(sequenceInfo);
        return sequenceInfo;
    }

    private void updateSequence(SequenceInfo sequenceInfo){
        long seqNextValue = sequenceInfo.getSequenceValue()+sequenceInfo.getIncrement();
        if(seqNextValue > sequenceInfo.getMaxValue()){
            throw new RuntimeException(String.format("%s的id已经用尽",sequenceInfo.getSequenceName()));
        }
        sequenceInfo.setSequenceValue(sequenceInfo.getSequenceValue()+sequenceInfo.getIncrement());
        SequenceInfo update = new SequenceInfo();
        update.setSequenceName(sequenceInfo.getSequenceName());
        update.setSequenceValue(seqNextValue);
        sequenceInfoMapper.updateById(update);
    }
}

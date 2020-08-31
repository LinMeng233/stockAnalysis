package com.mydream.stockAnalysis.service;


import com.mydream.stockAnalysis.entity.SequenceInfo;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

public interface SequenceInfoService {

    public long getSequenceValue(String sequenceName);
}

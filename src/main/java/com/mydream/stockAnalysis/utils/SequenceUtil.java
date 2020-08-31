package com.mydream.stockAnalysis.utils;

import com.mydream.stockAnalysis.core.SpringContextHolder;
import com.mydream.stockAnalysis.core.UniqueIdCode;
import com.mydream.stockAnalysis.service.SequenceInfoService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class SequenceUtil {
    private static Logger logger = LoggerFactory.getLogger(SequenceUtil.class);
    private static final HashMap<String, SequenceInfo> currSeqMap = new HashMap();
    private static final String ID_COMMON = "ID_COMMON";

    public SequenceUtil() {
    }

    public static String getId(UniqueIdCode idCode) {
        StringBuilder stringBuilder = new StringBuilder(19);
        if (StringUtils.isBlank(idCode.getCode()) || 2 != idCode.getCode().length() && 3 != idCode.getCode().length()) {
            throw new IllegalArgumentException("uniqueIdCode format error");
        } else {
            if (2 == idCode.getCode().length()) {
                stringBuilder.append("C").append(idCode.getCode());
            } else {
                stringBuilder.append(idCode.getCode());
            }
            stringBuilder.append(DateUtil.getCurrentTime("yyMMdd"));
            String sequenceName = idCode.getSequenceName();
            if (StringUtils.isBlank(sequenceName)) {
                sequenceName = ID_COMMON;
            }

            long value = getNextValue(sequenceName, 4);
            stringBuilder.append(StringUtils.leftPad(String.valueOf(value), 10,'0'));
            return stringBuilder.toString();
        }
    }




    protected static long getNextValue(String idType, int size) {
        long intPow = (long)Math.pow(10.0D, (double)size);
        SequenceUtil.SequenceInfo sequenceInfo = (SequenceUtil.SequenceInfo)currSeqMap.get(idType);
        if (sequenceInfo == null) {
            synchronized(currSeqMap) {
                sequenceInfo = (SequenceUtil.SequenceInfo)currSeqMap.get(idType);
                if (sequenceInfo == null) {
                    sequenceInfo = new SequenceUtil.SequenceInfo();
                    currSeqMap.put(idType, sequenceInfo);
                }
            }
        }

        long seqValue = sequenceInfo.seqNo.incrementAndGet();
        long oldDbSeq = sequenceInfo.dbSeq;

        while(seqValue >= intPow || oldDbSeq <= 0L) {
            synchronized(sequenceInfo) {
                if (oldDbSeq == sequenceInfo.dbSeq) {
                    sequenceInfo.dbSeq = getDbSeqValue(idType);
                    seqValue = 1L;
                    sequenceInfo.seqNo.set(seqValue);
                } else {
                    seqValue = sequenceInfo.seqNo.incrementAndGet();
                }

                oldDbSeq = sequenceInfo.dbSeq;
            }
        }

        return oldDbSeq * intPow + seqValue;
    }

    private static long getDbSeqValue(String idType) {
        SequenceInfoService sequenceInfoService =  SpringContextHolder.getBean("sequenceInfoService");

        try {
            return sequenceInfoService.getSequenceValue(idType);
        } catch (Exception var8) {
            int i = 0;

            while(i < 3) {
                try {
                    Random random = new Random();
                    long randomMs = (long)(random.nextInt(1001) + 1000);
                    Thread.sleep(randomMs);
                    return sequenceInfoService.getSequenceValue(idType);
                } catch (Exception var7) {
                    logger.error("第" + i + "次获取失败");
                    ++i;
                }
            }

            throw var8;
        }
    }

    static class SequenceInfo {
        long dbSeq = -1L;
        AtomicLong seqNo = new AtomicLong(-1L);

        SequenceInfo() {
        }
    }
}

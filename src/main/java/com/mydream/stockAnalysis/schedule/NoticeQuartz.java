package com.mydream.stockAnalysis.schedule;

import com.mydream.stockAnalysis.core.SpringContextHolder;
import com.mydream.stockAnalysis.entity.CalculationResultOf255TradingDay;
import com.mydream.stockAnalysis.service.CalculationResultService;
import com.mydream.stockAnalysis.service.client.TushareClientService;
import com.mydream.stockAnalysis.utils.DateUtil;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class NoticeQuartz extends QuartzJobBean {
        /**
         * 执行定时任务
         * @param jobExecutionContext
         * @throws JobExecutionException
         */
        @Override
        public void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
            String today = DateUtil.getCurrentDate();
            CalculationResultService calculationResultService  =  SpringContextHolder.getBean("calculationResultService");
            CalculationResultOf255TradingDay calculationResultOf255TradingDay = calculationResultService.calcuteDayData(today);
        }

    }

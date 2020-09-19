package com.mydream.stockAnalysis;


import com.mydream.stockAnalysis.dto.CapitalFlowInfo;
import com.mydream.stockAnalysis.entity.ForeignCapitalFlowInfo;
import com.mydream.stockAnalysis.schedule.NoticeQuartz;
import com.mydream.stockAnalysis.service.CalculationResultService;
import com.mydream.stockAnalysis.service.ForeignCapitalFlowService;
import com.mydream.stockAnalysis.service.client.TushareClientService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.quartz.JobExecutionException;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class StockAnalysisApplicationTests {
	@Resource
	private ForeignCapitalFlowService foreignCapitalFlowService;
	@Resource
	private TushareClientService tushareClientService;
	@Resource
	private CalculationResultService calculationResultService;


	@Test
	void contextLoads() throws JobExecutionException {

		NoticeQuartz noticeQuartz = new NoticeQuartz();
		noticeQuartz.executeInternal(null);

	}

}

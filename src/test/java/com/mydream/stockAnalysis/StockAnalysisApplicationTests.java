package com.mydream.stockAnalysis;


import com.mydream.stockAnalysis.dto.CapitalFlowInfo;
import com.mydream.stockAnalysis.service.ForeignCapitalFlowService;
import com.mydream.stockAnalysis.service.client.TushareClientService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class StockAnalysisApplicationTests {
	@Resource
	private ForeignCapitalFlowService foreignCapitalFlowService;
	@Resource
	private TushareClientService tushareClientService;

	@Test
	void contextLoads() {


	}

}

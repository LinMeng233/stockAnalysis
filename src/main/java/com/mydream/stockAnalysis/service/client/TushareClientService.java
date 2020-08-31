package com.mydream.stockAnalysis.service.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mydream.stockAnalysis.dto.CapitalFlowInfo;
import com.mydream.stockAnalysis.dto.OutClientDto;
import com.mydream.stockAnalysis.dto.TushareCapitalFlowInnerData;
import com.mydream.stockAnalysis.enums.TushareApi;
import com.mydream.stockAnalysis.utils.HttpUtil;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("tushareClientService")
public class TushareClientService {
    private static final String TOKEN = "7e328ab2daaf51e2c113129076e2fc485055bb5824f44775f5a5dbe4";
    private static final String URL = "http://api.waditu.com";
    public List<CapitalFlowInfo> getCapitalFlowInfo(String startDate,String endDate){
        Map<String,Object> inputParm = this.buildCapitalFlowInput(startDate,endDate);
        String outPut = HttpUtil.doPost(URL,inputParm);
        OutClientDto outClientDto = JSON.parseObject(outPut, OutClientDto.class);
        List<Map<String, String>> itemList = this.extraData(outClientDto.getData());
        return this.convertCapitalFlowInfo(itemList);
    }

    private List<CapitalFlowInfo> convertCapitalFlowInfo(List<Map<String, String>> itemList) {
        List<CapitalFlowInfo> capitalFlowInfoList = new ArrayList<>();
        for (Map<String, String> itemMap : itemList) {
            String transactionDate = (String) itemMap.get("trade_date");
            BigDecimal shInflowAmount = new BigDecimal(itemMap.get("hgt"));
            BigDecimal szInflowAmount = new BigDecimal(itemMap.get("sgt"));
            BigDecimal shOutflowAmount = new BigDecimal(itemMap.get("ggt_ss"));
            BigDecimal szOutflowAmount = new BigDecimal(itemMap.get("ggt_sz"));
            BigDecimal northboundFund = new BigDecimal(itemMap.get("north_money"));
            BigDecimal southboundFund = new BigDecimal(itemMap.get("south_money"));
            CapitalFlowInfo capitalFlowInfo = CapitalFlowInfo.builder().transactionDate(transactionDate)
                    .szInflowAmount(szInflowAmount).shInflowAmount(shInflowAmount).szOutflowAmount(szOutflowAmount).shOutflowAmount(shOutflowAmount)
                    .northboundFund(northboundFund).southboundFund(southboundFund).build();
            capitalFlowInfoList.add(capitalFlowInfo);
        }
        return capitalFlowInfoList;
    }

    private List<Map<String, String>> extraData(String data) {
        List<Map<String, String>> itemList = new ArrayList<Map<String, String>>();
        JSONObject jsonObject = JSONObject.parseObject(data);
        JSONArray fields = jsonObject.getJSONArray("fields");
        JSONArray items = jsonObject.getJSONArray("items");
        for (Object item : items) {
            String itemString = item.toString().replaceAll("null","0");
            JSONArray jsonArray = JSONArray.parseArray(itemString);
            Map<String, String> itemMap = new HashMap<>();
            for (int i = 0; i < fields.size(); i++) {
                itemMap.put( fields.get(i).toString(), jsonArray.get(i).toString());
            }
            itemList.add(itemMap);
        }
        return itemList;
    }


    private Map<String,Object> buildCapitalFlowInput(String startDate,String endDate){
        Map<String,Object> inputParm = new HashMap<>();
        inputParm.put("api_name", TushareApi.CAPITAL_FLOW.getApiName());
        inputParm.put("token",TOKEN);
        Map<String,Object> params = new HashMap<>();
        params.put("start_date",startDate);
        params.put("end_date",endDate);
        inputParm.put("params",params);
        return inputParm;
    }
}

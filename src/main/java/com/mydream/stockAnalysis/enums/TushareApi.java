package com.mydream.stockAnalysis.enums;

public enum TushareApi {
    CAPITAL_FLOW("moneyflow_hsgt","沪深港通资金流向"),
    ;

    TushareApi(String apiName, String desc) {
        this.apiName = apiName;
        this.desc = desc;
    }

    private String apiName;
    private String desc;

    public String getApiName() {
        return apiName;
    }

    public String getDesc() {
        return desc;
    }
}

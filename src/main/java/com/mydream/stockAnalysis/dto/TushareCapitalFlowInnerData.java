package com.mydream.stockAnalysis.dto;

import java.util.List;

public class TushareCapitalFlowInnerData {
    private List<Object>  fields;
    private List<List<Object>>  items;

    public List<Object> getFields() {
        return fields;
    }

    public void setFields(List<Object> fields) {
        this.fields = fields;
    }

    public List<List<Object>> getItems() {
        return items;
    }

    public void setItems(List<List<Object>> items) {
        this.items = items;
    }
}

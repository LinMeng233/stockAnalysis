package com.mydream.stockAnalysis.enums;

import com.mydream.stockAnalysis.core.UniqueIdCode;

public enum  TableUniqueIdCode implements UniqueIdCode {
    ID_FOREIGN_CAPITAL_TRANSACTION("01","SEQ_FOREIGN_CAPITAL_TRANSACTION"),;

    private TableUniqueIdCode(String code, String sequenceName) {
        this.code = code;
        this.sequenceName = sequenceName;
    }

    private String code;

    private String sequenceName;

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getSequenceName() {
        return sequenceName;
    }
}

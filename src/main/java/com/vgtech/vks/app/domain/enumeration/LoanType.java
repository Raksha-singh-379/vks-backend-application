package com.vgtech.vks.app.domain.enumeration;

/**
 * The LoanType enumeration.
 */
public enum LoanType {
    SHORT_TERM("Short_term_loan"),
    MID_TERM("Mid_term_loan"),
    LONG_TERM("Long_term_loan");

    private final String value;

    LoanType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

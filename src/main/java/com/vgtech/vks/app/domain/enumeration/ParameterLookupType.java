package com.vgtech.vks.app.domain.enumeration;

/**
 * The ParameterLookupType enumeration.
 */
public enum ParameterLookupType {
    ACCOUNT("Account"),
    RELIGION("Religion"),
    CASTE("Caste"),
    CATEGORY("Category"),
    FARMER("Farmer"),
    RESOLUTION("Resolution"),
    EXECUTIVE_TITLE("Executive_Title"),
    BELONGING("Belonging"),
    INVESTMENT("Investment"),
    MEASURING_UNIT("Measuring_Unit"),
    ACCOUNT_TYPE("Account_Type");

    private final String value;

    ParameterLookupType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

package com.vgtech.vks.app.domain.enumeration;

/**
 * The SocietyAssetsType enumeration.
 */
public enum SocietyAssetsType {
    EQUIPMENT("Equipment"),
    FURNITURE("Furniture");

    private final String value;

    SocietyAssetsType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

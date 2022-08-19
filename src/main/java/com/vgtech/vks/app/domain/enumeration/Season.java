package com.vgtech.vks.app.domain.enumeration;

/**
 * The Season enumeration.
 */
public enum Season {
    KHARIP("Kharip"),
    RABBI("Rabbi"),
    HANGAMI("Hangami");

    private final String value;

    Season(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

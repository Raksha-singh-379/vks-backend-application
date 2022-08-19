package com.vgtech.vks.app.domain.enumeration;

/**
 * The DocumentType enumeration.
 */
public enum DocumentType {
    PROFILE_PICTURE("Profile_Picture"),
    SIGNATURE("Signature"),
    JINDAGI_PATRAK("Jindagi_Patrak"),
    EIGHT_A("Eight_A"),
    SAAT_BARA("Saat_Bara"),
    AADHAR("Aadhar_Card"),
    PAN_CARD("Pan_Card"),
    ASSET_DOC("Assets_Document"),
    Monthly_Meeting("Monthly_meeting"),
    MOM_file,
    DHORAN_DOC("Dhoran_document"),
    GR_DOC("GR_Document"),
    OTHER("Other");

    private final String value;

    DocumentType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

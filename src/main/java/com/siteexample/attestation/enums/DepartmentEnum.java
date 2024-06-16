package com.siteexample.attestation.enums;

public enum DepartmentEnum {
    DEP_DIBT("Служба охраны труда"),
    DEP_P("Служба пути"),
    DEP_V("Служба вагонного хозяйства"),
    DEP_SH("Служба автоматики и телемеханики");


    private String name;

    DepartmentEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

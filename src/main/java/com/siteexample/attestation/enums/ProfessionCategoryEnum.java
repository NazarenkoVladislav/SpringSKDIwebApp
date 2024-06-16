package com.siteexample.attestation.enums;

public enum ProfessionCategoryEnum {
    MANAGER("Руководитель"),
    SPECIALIST("Специалист");


    private String name;

    ProfessionCategoryEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

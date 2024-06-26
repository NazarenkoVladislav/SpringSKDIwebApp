package com.siteexample.attestation.enums;

public enum StructureUnitEnum {
    ORGAN("Орган управления"),
    VCHD5("ВЧДЭ-5 Батайск"),
    VCHD9("ВЧДЭ-9 Минеральные Воды"),
    VCHD11("ВЧДЭ-11 Махачкала"),
    VCHD13("ВЧДЭ-13 Краснодар"),
    ICH1("ИЧ-1 Сочи"),
    ICH2("ИЧ-2 Таганрог"),
    ICH3("ИЧ-3 Ставрополь"),
    PCH1("ПЧ-1 Шахты"),
    PCH3("ПЧ-3 Ростов"),
    PCH4("ПЧ-4 Батайск"),
    PCH6("ПЧ-6 Тихорецкая"),
    PCH7("ПЧ-7 Кавказская"),
    PCH8("ПЧ-8 Армавир"),
    PCH10("ПЧ-10 Минеральные Воды"),
    PCH12("ПЧ-12 Прохладная"),
    PCH15("ПЧ-15 Гудермес"),
    PCH16("ПЧ-16 Махачкала"),
    PCH18("ПЧ-18 Белореченская"),
    PCH19("ПЧ-19 Туапсе"),
    PCH21("ПЧ-21 Краснодар"),
    PCH22("ПЧ-22 Новороссийск"),
    PCH23("ПЧ-23 Старотитаровка"),
    PCH24("ПЧ-24 Тимашевская"),
    PCH26("ПЧ-26 Сальск"),
    PCH27("ПЧ-27 Куберле"),
    PCH30("ПЧ-30 Кизляр"),
    PCH32("ПЧ-32 Горячий Ключ"),
    PCH33("ПЧ-33 Лихая"),
    PCH35("ПЧ-35 Миллерово"),
    PCHISSO1("ПЧИССО-1 Ростов"),
    PCHISSO2("ПЧИССО-2 Адлер"),
    SHCH2("ШЧ-2 Ростов"),
    SHCH3("ШЧ-3 Батайск"),
    SHCH4("ШЧ-4 Тихорецкая"),
    SHCH5("ШЧ-5 Кавказская"),
    SHCH7("ШЧ-7 Туапсе"),
    SHCH8("ШЧ-8 Краснодар"),
    SHCH9("ШЧ-9 Тимашевская"),
    SHCH10("ШЧ-10 Крымская"),
    SHCH12("ШЧ-12 Сальск"),
    SHCH14("ШЧ-14 Минеральные Воды"),
    SHCH15("ШЧ-15 Прохладная"),
    SHCH18("ШЧ-18 Махачкала"),
    SHCH19("ШЧ-19 Лихая");

    private String name;

    StructureUnitEnum(String name) {
        this.name = name;
    }

    public static StructureUnitEnum[] getVagonDepartment() {
        return new StructureUnitEnum[] {VCHD5, VCHD9, VCHD11, VCHD13};
    }

    public String getName() {
        return name;
    }
}

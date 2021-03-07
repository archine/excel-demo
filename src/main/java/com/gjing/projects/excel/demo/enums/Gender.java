package com.gjing.projects.excel.demo.enums;

import lombok.Getter;

/**
 * @author Gjing
 **/
@Getter
public enum Gender {
    /**
     * 性别
     */
    MAN(1,"男"), WO_MAN(2, "女");
    private final int type;
    private final String desc;

    Gender(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public static Gender of(int type) {
        for (Gender gender : Gender.values()) {
            if (gender.type == type) {
                return gender;
            }
        }
        throw new NullPointerException();
    }

    public static Gender of(String desc) {
        for (Gender gender : Gender.values()) {
            if (gender.desc.equals(desc)) {
                return gender;
            }
        }
        return null;
    }
}

package com.roamer.school.enums;

/**
 * 性别枚举
 *
 * @author roamer
 * @version V1.0
 * @date 2019/1/6 15:48
 */
public enum GenderEnum implements BaseEnumInterface {

    /**
     * 未知
     */
    UNKNOWN("0", "未知"),

    /**
     * 男
     */
    MALE("1", "男"),

    /**
     * 女
     */
    FEMALE("2", "女");

    String code;

    String desc;

    GenderEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getDesc() {
        return desc;
    }

}

package com.roamer.school.enums;

/**
 * 用户状态枚举
 *
 * @author roamer
 * @version V1.0
 * @date 2018/12/26 22:31
 */
public enum StatusEnum implements BaseEnumInterface {

    /**
     * 未启用
     */
    DISABLE("0", "未启用"),
    /**
     * 正常
     */
    ENABLE("1", "正常"),
    /**
     * 锁定
     */
    LOCKED("2", "锁定");

    String code;

    String desc;

    StatusEnum(String code, String desc) {
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

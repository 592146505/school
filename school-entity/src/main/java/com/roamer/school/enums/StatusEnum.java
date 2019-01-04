package com.roamer.school.enums;

/**
 * 用户状态枚举
 *
 * @author roamer
 * @version V1.0
 * @date 2018/12/26 22:31
 */
public enum StatusEnum implements BaseEnum {

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
    LOCK("2", "锁定");

    String code;

    String describe;

    StatusEnum(String code, String describe) {
        this.code = code;
        this.describe = describe;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getDescribe() {
        return describe;
    }

}

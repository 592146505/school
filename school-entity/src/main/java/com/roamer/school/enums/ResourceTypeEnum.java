package com.roamer.school.enums;

/**
 * 资源类型枚举
 *
 * @author roamer
 * @version V1.0
 * @date 2019/1/6 20:12
 */
public enum ResourceTypeEnum implements BaseEnumInterface {

    /** 菜单 */
    MENU("0", "菜单"),

    /** 按钮 */
    BUTTON("1", "按钮"),

    /** 链接 */
    LINK("2", "链接");

    String code;

    String desc;

    ResourceTypeEnum(String code, String desc) {
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
    }}

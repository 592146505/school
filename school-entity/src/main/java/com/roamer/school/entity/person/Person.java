package com.roamer.school.entity.person;

import com.roamer.school.entity.base.BaseEntity;
import com.roamer.school.enums.GenderEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;
import java.util.Date;

/**
 * 人员基本信息
 *
 * @author roamer
 * @version V1.0
 * @date 2019/1/6 14:52
 */
@Getter
@Setter
//@Entity
//@Table(name = "tbl_person")
public class Person extends BaseEntity implements Serializable {

    /** 姓名 */
    private String name;

    /**
     * 性别(0:,未知 1:男  2:女)
     */
    @Enumerated(EnumType.ORDINAL)
    private GenderEnum gender;

    /** 身份证号码 */
    private String pid;

    /** 出生年月 */
    private Date birthday;

    /** 电话 */
    private String phone;

    /** 地址 */
    private Address address;

}

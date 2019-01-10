package com.roamer.school.entity.person;

import com.roamer.school.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 地址信息
 *
 * @author roamer
 * @version V1.0
 * @date 2019/1/6 16:18
 */
@Getter
@Setter
//@Entity
//@Table(name = "tbl_address")
public class Address extends BaseEntity implements Serializable {

    /** 省 */
    private String province;

    /** 市 */
    private String city;

    /** 详细地址 */
    private String detail;

    /** 邮编 */
    private String postCode;
}

package com.roamer.school.entity.person;

import com.roamer.school.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * 学生信息
 *
 * @author roamer
 * @version V1.0
 * @date 2019/1/6 14:47
 */
@Getter
@Setter
//@Entity
//@Table(name = "tbl_student")
public class Student extends BaseEntity implements Serializable {

    /**
     * 人员基本信息
     */
    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    /** 学号 */
    private String sno;

    /** 所属班级 */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinTable(name = "ref_grand_person", joinColumns = {@JoinColumn(name = "student_id")},
               inverseJoinColumns = {@JoinColumn(name = "grand_id")})
    private Grand grand;

}

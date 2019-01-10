package com.roamer.school.entity.person;

import com.roamer.school.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.Set;

/**
 * 班级信息
 *
 * @author roamer
 * @version V1.0
 * @date 2019/1/6 17:12
 */
@Getter
@Setter
//@Entity
//@Table(name = "tbl_grand")
public class Grand extends BaseEntity implements Serializable {

    /** 下辖学生 */
    @OneToMany(mappedBy = "grand")
    private Set<Student> students;


}

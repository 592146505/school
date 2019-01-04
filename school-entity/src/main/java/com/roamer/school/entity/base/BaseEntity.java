package com.roamer.school.entity.base;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * 基础实体，所有entity均应当继承
 * {@link MappedSuperclass} 加上此注解的类，属性将被实体继承
 *
 * @author roamer
 * @version V1.0
 * @date 2018/12/26 21:43
 */
@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity {

    /**
     * 主键
     */
    @Id
    @GeneratedValue
    protected Long id;

    /**
     * 创建时间
     */
    @Column(name = "gmt_create")
    protected Date gmtCreate;

    /**
     * 修改时间
     */
    @Column(name = "gmt_modified")
    protected Date gmtModified;

    /**
     * 是否删除
     */
    @Column(name = "is_delete")
    protected Boolean delete = Boolean.FALSE;

}

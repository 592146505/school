package com.roamer.school.entity.core;

import com.roamer.school.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * 权限信息
 *
 * @author roamer
 * @version V1.0
 * @date 2018/12/26 22:28
 */
@Getter
@Setter
@ToString
@Entity
@Table(name = "sys_permission")
public class SysPermission extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 7775701095281011325L;
    /**
     * 功能名
     */
    private String name;

    /**
     * 资源类型，[menu|button]
     */
    @Column(columnDefinition = "enum('menu','button')")
    private String resourceType;


    /**
     * 资源路径
     */
    private String url;

    /**
     * 权限字符串
     * menu 例子：role:*
     * button 例子：role:create,role:update,role:delete,role:view
     */
    private String permission;

    /**
     * 父编号
     */
    private Long parentId;

    /**
     * 父编号列表
     */
    private String parentIds;

    /**
     * 是否启用
     */
    private Boolean available = Boolean.FALSE;

    /**
     * 角色 n<->n 权限
     */
    @ManyToMany
    @JoinTable(name = "sys_role_permission", joinColumns = {@JoinColumn(name = "permission_id")}, inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<SysRole> roles;

}

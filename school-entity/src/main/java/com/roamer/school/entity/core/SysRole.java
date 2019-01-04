package com.roamer.school.entity.core;

import com.roamer.school.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * 角色信息
 *
 * @author roamer
 * @version V1.0
 * @date 2018/12/26 22:14
 */
@Getter
@Setter
@ToString
@Entity
@Table(name = "sys_role")
public class SysRole extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -7693905288305429106L;
    /**
     * 角色标识程序中判断使用,如"admin",这个是唯一的:
     */
    private String role;

    /**
     * 角色描述,UI界面显示使用
     */
    private String description;

    /**
     * 是否可用,如果不可用将不会添加给用户
     */
    private Boolean available = Boolean.FALSE;

    /**
     * 角色n<->n权限
     */
    @ManyToMany
    @JoinTable(name = "sys_role_permission", joinColumns = {@JoinColumn(name = "role_id")}, inverseJoinColumns = {@JoinColumn(name = "permission_id")})
    private Set<SysPermission> permissions;

    /**
     * 用户n<->n角色
     */
    @ManyToMany
    @JoinTable(name = "sys_user_role", joinColumns = {@JoinColumn(name = "role_id")}, inverseJoinColumns = {@JoinColumn(name = "uid")})
    private Set<UserInfo> userInfos;


}

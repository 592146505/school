package com.roamer.school.entity.core;

import com.roamer.school.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 角色权限关系
 *
 * @author roamer
 * @version V1.0
 * @date 2019/1/6 19:19
 */
@Getter
@Setter
@ToString
@Entity
@Table(name = "ref_role_permission")
public class RolePermissionRelation extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -2469736907777084600L;

    /** 隶属角色 */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private SysRole sysRole;

    /** 隶属权限 */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "permission_id")
    private SysPermission sysPermission;
}

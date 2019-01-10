package com.roamer.school.entity.core;

import com.roamer.school.entity.base.BaseEntity;
import com.roamer.school.enums.ResourceTypeEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
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
@Entity
@Table(name = "sys_permission", uniqueConstraints = {@UniqueConstraint(columnNames = "url")})
public class SysPermission extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 7775701095281011325L;

    /** 功能名 */
    private String name;

    /** 资源类型 */
    @Enumerated(EnumType.ORDINAL)
    private ResourceTypeEnum resourceType;

    /**
     * 资源路径
     * <b>唯一约束</b>
     */
    private String url;

    /**
     * 权限字符串
     * menu 例子：role:*
     * button 例子：role:create,role:update,role:delete,role:view
     */
    private String permission;

    /** 父编号 */
    private Long parentId;

    /** 父编号列表 */
    private String parentIds;

    /** 是否启用 */
    private Boolean available = Boolean.TRUE;

    /** 下辖 角色权限 */
    @OneToMany(mappedBy = "sysPermission", fetch = FetchType.LAZY)
    private Set<RolePermissionRelation> rolePermissionRelations = new HashSet<>();

}

package com.roamer.school.entity.core;

import com.roamer.school.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

/**
 * 角色信息
 *
 * @author roamer
 * @version V1.0
 * @date 2018/12/26 22:14
 */
@Getter
@Setter
@ToString(exclude = {"rolePermissionRelations", "userRoleRelations"})
@Entity
@Table(name = "sys_role", uniqueConstraints = {@UniqueConstraint(columnNames = "name")})
public class SysRole extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -7693905288305429106L;
    /**
     * 角色名称,程序中判断使用,如"admin"
     * <b>唯一约束</b>
     */
    private String name;

    /**
     * 角色描述,UI界面显示使用
     */
    private String description;

    /**
     * 是否启用
     * true: 启用, false: 不启用
     */
    private Boolean available = Boolean.TRUE;

    /**
     * 下辖 角色权限
     */
    @OneToMany(mappedBy = "sysRole", fetch = FetchType.LAZY)
    private Set<RolePermissionRelation> rolePermissionRelations = new HashSet<>();


    /**
     * 下辖 用户角色
     */
    @OneToMany(mappedBy = "sysRole", fetch = FetchType.LAZY)
    private Set<UserRoleRelation> userRoleRelations = new HashSet<>();

    /**
     * 获取当前角色下辖权限<br>
     * <b>该方法只应当在对象处于session管理范围之内时才可使用</b>
     *
     * @param exclude 排除无效权限，默认true<br>
     *                true: 排除   false: 不排除
     *
     * @return 可能返回空值 拥有权限
     */
    @Transient
    public Set<SysPermission> getPermissions(Boolean exclude) {
        // 默认为true
        exclude = Optional.ofNullable(exclude).orElse(Boolean.TRUE);
        if (Objects.nonNull(rolePermissionRelations)) {
            boolean finalUse = exclude;
            return rolePermissionRelations.stream().map(RolePermissionRelation::getSysPermission)
                                          .filter(r -> finalUse ? r.getAvailable() : true).collect(toSet());
        }
        return null;
    }
}

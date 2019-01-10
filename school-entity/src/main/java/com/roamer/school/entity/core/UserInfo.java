package com.roamer.school.entity.core;


import com.roamer.school.entity.base.BaseEntity;
import com.roamer.school.enums.StatusEnum;
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
 * 用户主体信息
 *
 * @author roamer
 * @version V1.0
 * @date 2018/12/26 21:21
 */
@Getter
@Setter
@ToString(exclude = "userRoleRelations")
@Entity
@Table(name = "sys_user", uniqueConstraints = {@UniqueConstraint(columnNames = "username")})
public class UserInfo extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -2689684495232780087L;

    /** 昵称 */
    private String nickname;

    /**
     * 用户名
     * <b>唯一约束</b>
     */
    private String username;

    /** 密码 */
    private String password;

    /** 加密盐 */
    private String salt;

    /**
     * 用户状态<br>
     * 0:创建未认证（比如没有激活，没有输入验证码等等）--等待验证的用户<br>
     * 1:正常状态<br>
     * 2：用户被锁定<br>
     * <p>
     * @see Enumerated ,当entity属性使用枚举时需要使用
     */
    @Enumerated(EnumType.ORDINAL)
    private StatusEnum status;

    /** 下辖 用户角色关系 */
    @OneToMany(mappedBy = "userInfo", fetch = FetchType.LAZY)
    private Set<UserRoleRelation> userRoleRelations = new HashSet<>();

    /**
     * 获取salt
     *
     * @return username + salt
     */
    public String getCredentialsSalt() {
        return username.concat(salt);
    }

    /**
     * 获取当前角色下辖权限<br>
     * <b>该方法只应当在对象处于session管理范围之内时才可使用</b>
     *
     * @param exclude 排除无效角色，默认true<br>
     *                true: 排除   false: 排除
     *
     * @return 拥有角色
     */
    @Transient
    public Set<SysRole> getRoles(Boolean exclude) {
        // 默认为true
        exclude = Optional.ofNullable(exclude).orElse(Boolean.TRUE);
        if (Objects.nonNull(userRoleRelations)) {
            boolean finalUse = exclude;
            return userRoleRelations.stream().map(UserRoleRelation::getSysRole)
                                    .filter(r -> finalUse ? r.getAvailable() : true).collect(toSet());
        }
        return null;
    }
}

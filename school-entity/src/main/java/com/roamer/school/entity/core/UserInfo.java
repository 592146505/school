package com.roamer.school.entity.core;


import com.roamer.school.entity.base.BaseEntity;
import com.roamer.school.enums.StatusEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * 用户主体信息
 *
 * @author roamer
 * @version V1.0
 * @date 2018/12/26 21:21
 */
@Getter
@Setter
@ToString(exclude = {"roleList"})
@Entity
@Table(name = "sys_user")
public class UserInfo extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -2689684495232780087L;

    /**
     * 昵称
     */
    private String name;

    private String username;

    private String password;

    /**
     * 加密密码的盐
     */
    private String salt;

    /**
     * 用户状态
     * 0:创建未认证（比如没有激活，没有输入验证码等等）--等待验证的用户
     * 1:正常状态
     * 2：用户被锁定
     * <p>
     *
     * @see Enumerated ,当entity属性使用枚举时，默认按照枚举第一个参数映射数据库
     */
    @Enumerated(EnumType.ORDINAL)
    private StatusEnum status;

    /**
     * 用户角色
     */
    @ManyToMany
    @JoinTable(name = "sys_user_role", joinColumns = {@JoinColumn(name = "uid")}, inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<SysRole> roleList;

    /**
     * 获取salt
     *
     * @return username + salt
     */
    public String getCredentialsSalt() {
        return username.concat(salt);
    }
}

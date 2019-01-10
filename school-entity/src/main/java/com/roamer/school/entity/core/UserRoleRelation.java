package com.roamer.school.entity.core;

import com.roamer.school.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 用户角色关系
 *
 * @author roamer
 * @version V1.0
 * @date 2019/1/6 18:18
 */
@Getter
@Setter
@ToString
@Entity
@Table(name = "ref_user_role")
public class UserRoleRelation extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 6367768624711837758L;

    /** 隶属用户 */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "u_id")
    private UserInfo userInfo;

    /** 隶属角色 */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private SysRole sysRole;
}

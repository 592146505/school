package com.roamer.school.dao.core;

/**
 * 用户信息增强接口
 *
 * @author roamer
 * @version V1.0
 * @date 2019/1/3 16:52
 */
public interface UserInfoSweep {

    /**
     * 查询Id
     *
     * @param username 用户名
     *
     * @return 用户id
     */
    Object findIdByUsername(String username);

    Object findUserById(Long id);
}

package com.roamer.school.service;

import com.roamer.school.entity.core.UserInfo;

/**
 * 用户逻辑接口
 *
 * @author roamer
 * @version V1.0
 * @date 2018/12/27 15:38
 */
public interface UserInfoService {

    /**
     * 根据用户名查询
     *
     * @param username 用户名
     *
     * @return 用户信息
     */
    UserInfo findByUsername(String username);
}

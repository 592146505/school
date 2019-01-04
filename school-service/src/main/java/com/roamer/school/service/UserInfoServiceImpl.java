package com.roamer.school.service;

import com.roamer.school.dao.core.UserInfoDao;
import com.roamer.school.entity.core.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 用户逻辑实现
 *
 * @author roamer
 * @version V1.0
 * @date 2019/1/2 10:52
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoDao userInfoDao;

    @Override
    public UserInfo findByUsername(String username) {
        Object object = userInfoDao.findUserById(1L);
        Optional.ofNullable(object).ifPresent(o -> {
            System.out.println(o);
        });
        return userInfoDao.findByUsername(username);
    }
}

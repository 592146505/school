package com.roamer.school.dao.core;

import com.roamer.school.dao.base.BaseRepositoryImpl;
import com.roamer.school.entity.core.UserInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户数据操作实现
 *
 * @author roamer
 * @version V1.0
 * @date 2019/1/2 10:51
 */
public class UserInfoDaoImpl extends BaseRepositoryImpl<UserInfo, Long> implements UserInfoSweep {

    @Override
    public Object findIdByUsername(String username) {
        StringBuffer sql = new StringBuffer("SELECT id FROM sys_user").append(" WHERE username = :username");
        Map<String, Object> params = new HashMap<>(1);
        params.put("username", username);
        return super.findOneResultObjectBySql(sql, params);
    }

    @Override
    public Object findUserById(Long id) {
        StringBuffer hql = new StringBuffer("FROM UserInfo").append(" WHERE id = :id");
        Map<String, Object> params = new HashMap<>(1);
        params.put("id", id);
        return super.findEntity(hql, params);
    }

    @Override
    public void savee(UserInfo userInfo) {
        save(userInfo);
    }


}

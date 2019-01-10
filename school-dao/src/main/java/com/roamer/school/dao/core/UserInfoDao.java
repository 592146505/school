package com.roamer.school.dao.core;

import com.roamer.school.entity.core.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * 用户数据操作接口
 *
 * @author roamer
 * @version V1.0
 * @date 2019/1/2 10:51
 */
public interface UserInfoDao extends JpaRepository<UserInfo, Long>, UserInfoSweep {

    /**
     * 根据用户名查询
     *
     * @param username 用户名
     *
     * @return 用户信息
     */
    @Query("FROM UserInfo WHERE username = :username")
    UserInfo findByUsername(@Param("username") String username);

}

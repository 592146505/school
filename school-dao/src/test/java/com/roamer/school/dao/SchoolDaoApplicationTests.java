package com.roamer.school.dao;

import com.roamer.school.dao.config.JpaConfiguration;
import com.roamer.school.dao.core.UserInfoDao;
import com.roamer.school.entity.core.UserInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest()
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = {JpaConfiguration.class})
public class SchoolDaoApplicationTests {

    @Autowired
    private UserInfoDao userInfoDao;

    @Test
    public void findByUsername() {
        UserInfo userInfo = userInfoDao.findByUsername("admin");
        Assert.assertNotNull(userInfo);
    }

    @Test
    public void save() {
        UserInfo userInfo = new UserInfo();
        userInfoDao.savee(userInfo);
        userInfo.setNickname("w");
        userInfo.setUsername("wsc");
    }

}


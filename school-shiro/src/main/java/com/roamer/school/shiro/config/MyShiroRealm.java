package com.roamer.school.shiro.config;

import com.roamer.school.entity.core.UserInfo;
import com.roamer.school.service.UserInfoService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

/**
 * 自定义权限匹配和账户匹配
 *
 * @author roamer
 * @version V1.0
 * @date 2018/12/26 21:14
 */
public class MyShiroRealm extends AuthorizingRealm {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyShiroRealm.class);

    @Autowired
    private UserInfoService userInfoService;

    /**
     * 自定义权限匹配
     *
     * @param principals
     *
     * @return 角色和其拥有的权限
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        LOGGER.info("权限配置 --- start ---");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        UserInfo userInfo = (UserInfo) principals.getPrimaryPrincipal();
        userInfo.getRoleList().forEach(r -> {
            // 获取角色
            authorizationInfo.addRole(r.getRole());
            // 获取权限
            r.getPermissions().forEach(p -> authorizationInfo.addStringPermission(p.getPermission()));

        });
        LOGGER.info("权限配置 --- end ---");
        return authorizationInfo;
    }

    /**
     * 自定义账户匹配
     *
     * @param token 登陆凭证
     *
     * @return 校验过后的
     *
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        LOGGER.info("用户配置 --- start ---");

        // 获取账号
        String username = (String) token.getPrincipal();
        LOGGER.info("Login username = {}", username);
        UserInfo userInfo = userInfoService.findByUsername(username);
        if (Objects.isNull(userInfo)) {
            return null;
        }
        LOGGER.info("用户配置 --- end ---");
        return new SimpleAuthenticationInfo(userInfo, userInfo.getPassword(),
                                            ByteSource.Util.bytes(userInfo.getCredentialsSalt()), getName());
    }
}

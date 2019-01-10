package com.roamer.school.shiro.config;

import com.roamer.school.entity.core.UserInfo;
import com.roamer.school.enums.StatusEnum;
import com.roamer.school.service.UserInfoService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
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
     * <p>
     * 授权只能在成功的认证之后执行，因为授权数据（角色、权限等）必须总是与已知的标识相关联。这样的已知身份只能在成功登录时获得。
     * </p>
     *
     * @param principals 身份
     *
     * @return 角色和其拥有的权限
     *
     * @throws AuthorizationException <ul>子类：
     *                                <li>UnauthorizedExceptionb 抛出以指示请求的操作或对请求的资源的访问是不允许的</li>
     *                                <li>UnanthenticatedException 当尚未完成成功认证时，尝试执行授权操作时引发异常</li>
     *                                </ul>
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) throws AuthorizationException {
        LOGGER.info("--- 查询权限 start ---");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        UserInfo userInfo = (UserInfo) principals.getPrimaryPrincipal();
        userInfo.getRoles(true).forEach(r -> {
            // 获取角色
            authorizationInfo.addRole(r.getName());
            // 获取权限
            r.getPermissions(true).forEach(p -> authorizationInfo.addStringPermission(p.getPermission()));

        });
        LOGGER.info("--- 查询权限 end ---");
        return authorizationInfo;
    }

    /**
     * 自定义账户匹配
     *
     * @param token 用户传入登陆凭证
     *
     * @return 数据库中的登陆凭证
     *
     * @throws AuthenticationException <ul>子类：
     *                                 <li>CredentitalsException 凭证异常
     *                                 <ul>
     *                                 <li>IncorrectCredentialsException 不正确的凭证</li>
     *                                 <li>ExpiredCredentialsException 凭证过期</li>
     *                                 </ul>
     *                                 </li>
     *                                 <li>AccountException 账号异常
     *                                 <ul>
     *                                 <li>ConcurrentAccessException 并发访问异常（多个用户同时登录时抛出）</li>
     *                                 <li>UnknownAccountException 未知的账号</li>
     *                                 <li>ExcessiveAttemptsException 认证次数超过限制</li>
     *                                 <li>DisabledAccountException 禁用的账号</li>
     *                                 <li>LockedAccountException 账号被锁定</li>
     *                                 <li>UnsupportedTokenException 使用了不支持的Token</li>
     *                                 </ul>
     *                                 </li>
     *                                 </ul>
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // 获取账号信息
        String username = (String) token.getPrincipal();
        LOGGER.info("Login user username = {}", username);
        UserInfo userInfo = userInfoService.findByUsername(username);
        if (Objects.isNull(userInfo)) {
            // 账号不存在
            throw new UnknownAccountException();
        }
        if (StatusEnum.LOCKED.equals(userInfo.getStatus())) {
            // 账号被锁定
            throw new LockedAccountException();
        }
        // SimpleAuthenticationInfo 将用户信息和密码封装为PrincipalCollection
        // salt = username+sal
        // realm bean name;
        return new SimpleAuthenticationInfo(userInfo, userInfo.getPassword(),
                                            ByteSource.Util.bytes(userInfo.getCredentialsSalt()), getName());
    }

}

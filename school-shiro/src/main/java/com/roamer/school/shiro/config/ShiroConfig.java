package com.roamer.school.shiro.config;

import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * shiro 配置
 *
 * @author roamer
 * @version V1.0
 * @date 2018/12/26 19:51
 */
@Configuration
public class ShiroConfig {


    /**
     * 设置ShiroFilter过滤规则
     * <ul>
     * <li>anon:所有url都都可以匿名访问</li>
     * <li>authc:所有url都必须认证通过才可以访问</li>
     * <p>
     * <p>
     * </ul>
     *
     * @param securityManager SecurityManager
     *
     * @return ShiroFilterFactoryBean
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 设置SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 过滤链定义，从上向下顺序执行，一般将/**放在最为下边
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        // 配置不会过滤的资源
        filterChainDefinitionMap.put("/static/**", "anon");
        // 配置logout过滤器,其中的具体的退出代码Shiro已经替我们实现了
        filterChainDefinitionMap.put("/logout", "logout");
        filterChainDefinitionMap.put("/**", "authc");
        // 如果不设置默认"/login"
        shiroFilterFactoryBean.setLoginUrl("/login");
        // 登录成功后要跳转的链接
        shiroFilterFactoryBean.setSuccessUrl("/index");
        // 未授权界面
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    /**
     * SecurityManager
     * Shiro核心,协调多个组件进行鉴权
     *
     * @param realm 自定义Realm
     *
     * @return SecurityManager
     */
    @Bean
    public SecurityManager securityManager(Realm realm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(realm);
        return securityManager;
    }

    /**
     * 自定义Realm
     * 提供认证和鉴权数据
     *
     * @param credentialsMatcher 认证匹配器
     *
     * @return shiro 自定义Realm
     */
    @Bean
    public Realm myShiroRealm(CredentialsMatcher credentialsMatcher) {
        MyShiroRealm realm = new MyShiroRealm();
        realm.setCredentialsMatcher(credentialsMatcher);
        return realm;
    }

    /**
     * 认证匹配器
     *
     * @return 认证匹配器
     */
    @Bean
    public CredentialsMatcher credentialsMatcher() {
        // 设置加密算法
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher("MD5");
        // 加密次数
        credentialsMatcher.setHashIterations(1024);
        return credentialsMatcher;
    }

}

package com.roamer.school.shiro.config;

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
     * SecurityManager
     * Shiro核心,协调多个组件进行鉴权
     *
     * @param realm 数据供给
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
     * Realm
     * 提供认证和鉴权数据
     *
     * @return shiro Realm
     */
    @Bean
    public Realm myShiroRealm() {
        return new MyShiroRealm();
    }

    /**
     * 设置ShiroFilter过滤规则
     * <p>
     * anon:所有url都都可以匿名访问
     * authc:所有url都必须认证通过才可以访问
     *
     * @param securityManager
     *
     * @return
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
}

package com.tdmh.shiro;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Administrator on 2018/9/14.
 */
@Configuration
public class ShiroConfig {

    @Bean
    public EhCacheManager ehCacheManager()
    {
        EhCacheManager manager = new EhCacheManager();
        manager.setCacheManagerConfigFile("classpath:ehcache-local.xml");
        return manager;
    }
    @Bean
    public SessionManager sessionManager()
    {
        ShiroSessionManager manager = new ShiroSessionManager();
        manager.setSessionDAO(cacheSessionDAO());
        SessionListener sessionListener = new SessionListener();
        Collection<org.apache.shiro.session.SessionListener> sessionListenerList = new ArrayList<org.apache.shiro.session.SessionListener>();
        sessionListenerList.add(sessionListener);
        manager.setSessionListeners(sessionListenerList);
        manager.setSessionIdUrlRewritingEnabled(false);
        manager.setDeleteInvalidSessions(true);
        manager.setGlobalSessionTimeout(60 * 60 * 1000);
        return manager;
    }

    @Bean
    public CacheSessionDAO cacheSessionDAO() {
        CacheSessionDAO cacheSessionDAO = new CacheSessionDAO();
        cacheSessionDAO.setSessionIdGenerator(new JavaUuidSessionIdGenerator());
        cacheSessionDAO.setActiveSessionsCacheName("shiro-activeSessionCache");
        cacheSessionDAO.setCacheManager(ehCacheManager());
        return cacheSessionDAO;
    }

    /**
     *
     * @Title: createMyRealm
     * @Description: 自定义的realm
     * @return
     */
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public CustomAuthorizingRealm createMyRealm() {
        // 加密相关
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        // 散列算法
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        // 散列次数
        hashedCredentialsMatcher.setHashIterations(1);
        CustomAuthorizingRealm myRealm = new CustomAuthorizingRealm();
        myRealm.setCredentialsMatcher(hashedCredentialsMatcher);
        return myRealm;
    }

    /**
     *
     * @Title: securityManager
     * @Description: 注入自定义的realm
     * @Description: 注意方法返回值SecurityManager为org.apache.shiro.mgt.SecurityManager
     *               ,不要导错包
     * @return
     */
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager  securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(createMyRealm());
        securityManager.setCacheManager(ehCacheManager());
        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }

    /**
     *
     * @Title: shirFilter
     * @Description: Shiro 的Web过滤器
     *
     * @return
     */
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilter() {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager());

        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        shiroFilterFactoryBean.setLoginUrl("/login");
        // 登录成功后要跳转的链接,建议不配置,shiro认证成功自动到上一个请求路径
        shiroFilterFactoryBean.setSuccessUrl("/index");
        // 未授权界面,指定没有权限操作时跳转页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");

        // 过滤器
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        // 配置不会被过滤的链接 顺序判断
        // 过虑器链定义，从上向下顺序执行，一般将/**放在最下边
        // 对静态资源设置匿名访问
        // anon:所有url都都可以匿名访问
        filterChainDefinitionMap.put("/login", "anon");
        // 配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了
//        filterChainDefinitionMap.put("/logout", "logout");
        filterChainDefinitionMap.put("/wx/*", "anon");
        filterChainDefinitionMap.put("/dist/*", "anon");
        filterChainDefinitionMap.put("/lyimsstandard/*", "anon");
        // authc:所有url都必须认证通过才可以访问
        filterChainDefinitionMap.put("/*", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        Map<String, Filter> filterMap = new LinkedHashMap<String, Filter>();
//        filterMap.put("authc",new CustomFormAuthenticationFilter());
        filterMap.put("user",new CustomAuthorizationFilter());
        filterMap.put("anon",new LogFilter());
        shiroFilterFactoryBean.setFilters(filterMap);
        return shiroFilterFactoryBean;
    }

    //Shiro生命周期处理器
    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }


    //开启shiro aop注解支持,才能支持注解
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(org.apache.shiro.mgt.SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    @Bean
    @ConditionalOnMissingBean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAAP = new DefaultAdvisorAutoProxyCreator();
        defaultAAP.setProxyTargetClass(true);
        return defaultAAP;
    }
}

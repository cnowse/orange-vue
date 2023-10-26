package cn.cnowse.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.stp.StpUtil;

/**
 * SaToken 配置
 *
 * @author Jeong Geol
 */
@Configuration
public class SaTokenConfig implements WebMvcConfigurer {

    /** 排除路径，不被 SaToken 拦截 */
    private final List<String> excludePath =
            Arrays.asList("/login", "/doc.html", "/*/api-docs", "/*/api-docs/**", "/error");

    /**
     * 注册 Sa-Token 拦截器，打开注解式鉴权功能
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册 Sa-Token 拦截器，打开注解式鉴权功能。校验规则为 StpUtil.checkLogin() 登录校验
        registry.addInterceptor(new SaInterceptor(handle -> StpUtil.checkLogin()))
                .addPathPatterns("/**").excludePathPatterns(excludePath);
    }

}
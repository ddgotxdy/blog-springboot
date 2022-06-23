package com.ddgotxdy.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author: ddgo
 * @description: 跨域，精度配置
 */
@Configuration
public class WebConfig implements WebMvcConfigurer, InitializingBean {

    @Autowired
    private ObjectMapper obj;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //跨域配置，不可设置为*，不安全, 前后端分离项目，可能域名不一致
        //本地测试 端口不一致 也算跨域
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedHeaders("*")
                .allowedOriginPatterns("*")
                .allowedMethods("*");
    }

    /**
     * 定义bean的时候自定义初始化
     * @throws Exception Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        if (obj != null) {
            SimpleModule simpleModule = getSimpleModule();
            obj.registerModule(simpleModule);
        }
    }

    /**
     * 解决后端向前端返回大Long型数据精度丢失
     */
    private SimpleModule getSimpleModule() {
        // 序列换成json时,将所有的long变成string
        // 因为js中得数字类型不能包含所有的java long值
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        return simpleModule;
    }

}
package com.kpi.diploma.smartroads.config;

import com.kpi.diploma.smartroads.config.db.UserCascadeSaveEL;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@EnableMongoAuditing
//@EnableWebMvc
public class RootConfig /*extends WebMvcConfigurerAdapter */{

    @Bean
    public UserCascadeSaveEL userCascadingMongoEventListener() {
        return new UserCascadeSaveEL();
    }

//    @Bean
//    public ViewResolver internalResourceViewResolver() {
//        InternalResourceViewResolver bean = new InternalResourceViewResolver();
//        bean.setViewClass(JstlView.class);
//        bean.setPrefix("/static/jsp/");
//        bean.setSuffix(".jsp");
//        return bean;
//    }
//
//    @Override
//    public void configureDefaultServletHandling(
//            DefaultServletHandlerConfigurer configurer) {
//        configurer.enable();
//    }
//
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry
//                .addResourceHandler("/static/**"/*, "/connect/**"*/)
//                .addResourceLocations("classpath:/static/"/*, "classpath:/connect/"*/);
//        registry.setOrder(0);
//    }
}

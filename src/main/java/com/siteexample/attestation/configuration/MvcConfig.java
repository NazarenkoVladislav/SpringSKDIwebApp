package com.siteexample.attestation.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {

    @Value("${upload.path}")
    private String uploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Подключаем доступ к папке static
        registry
                .addResourceHandler("/static/**", "/img/**", "/file/**")
                .addResourceLocations("classpath:/static/", "file://" + uploadPath + "/img/", "file://" + uploadPath + "/");
    }

}

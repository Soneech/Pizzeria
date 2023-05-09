package org.soneech.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfiguration implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/styles/css/**")
                .addResourceLocations("classpath:/static/css/");
        registry
                .addResourceHandler("/icon/**")
                .addResourceLocations("classpath:/static/icon/");
        registry
                .addResourceHandler("/image/pizza/**")
                .addResourceLocations("classpath:/static/image/pizza/");
    }
}

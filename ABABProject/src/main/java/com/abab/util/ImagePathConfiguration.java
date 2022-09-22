package com.abab.util;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ImagePathConfiguration implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/photos/**").addResourceLocations("file:"+ VideoConstUtil.IMAGE_PATH);
        registry.addResourceHandler("/videos/**").addResourceLocations("file:"+ VideoConstUtil.VIDEO_PATH);
        registry.addResourceHandler("/excels/**").addResourceLocations("file:"+ ExcelAddress.FILE_PATH);

    }
}

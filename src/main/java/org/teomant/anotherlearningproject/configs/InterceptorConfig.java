package org.teomant.anotherlearningproject.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.teomant.anotherlearningproject.interceptors.TestIntercepter;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    TestIntercepter testIntercepter;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(testIntercepter);
    }

}

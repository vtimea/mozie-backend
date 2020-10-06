package com.mozie;

import com.mozie.security.AuthTokenFilter;
import org.modelmapper.ModelMapper;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {
    @Bean
    public FilterRegistrationBean<AuthTokenFilter> loggingFilter() {
        FilterRegistrationBean<AuthTokenFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new AuthTokenFilter());
        registrationBean.addUrlPatterns("/api/*");
        return registrationBean;
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}

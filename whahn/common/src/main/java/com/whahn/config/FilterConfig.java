package com.whahn.config;

import com.whahn.filter.RequestInitFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class FilterConfig {
    @Bean
    public FilterRegistrationBean<RequestInitFilter> requestInitFilter() {
        FilterRegistrationBean<RequestInitFilter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
        filterFilterRegistrationBean.setFilter(new RequestInitFilter());

        return filterFilterRegistrationBean;
    }
}

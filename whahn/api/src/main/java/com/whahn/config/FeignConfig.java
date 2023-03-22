package com.whahn.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@EnableFeignClients(basePackages = "com.whahn.feign")
@Configuration(proxyBeanMethods = false)
public class FeignConfig {
}

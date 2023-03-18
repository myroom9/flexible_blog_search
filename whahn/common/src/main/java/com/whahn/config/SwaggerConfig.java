package com.whahn.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class SwaggerConfig {
    @Bean
    public GroupedOpenApi api() {
        return GroupedOpenApi.builder()
                .group("swagger")
                .pathsToMatch("/v1/**")
                .build();
    }

    @Bean
    public OpenAPI swaggerUiSetting() {
        return new OpenAPI()
                .info(new Info()
                        .title("블로그 / 인기검색어 API SWAGGER DOCS")
                        .description("블로그 / 인기검색어 API LIST")
                        .version("v1.0.0")
                );
    }
}

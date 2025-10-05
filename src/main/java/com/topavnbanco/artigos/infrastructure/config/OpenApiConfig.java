package com.topavnbanco.artigos.infrastructure.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition
@Configuration
@SecurityScheme(name = "bearerAuth", type = SecuritySchemeType.HTTP, scheme = "bearer")
public class OpenApiConfig {

    @Bean
    public OpenAPI CongressoArticleAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Request Congresso and Article API")
                        .description("Request Congresso and Article Reference Project")
                        .version("v0.0.1")
                        .license(new License()
                                .name("Apache 2.0")
                                .url("")));
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public-controllers")
                .packagesToScan("com.topavnbanco.artigos.adapters.inbound")
                .pathsToMatch("/**")
                .build();
    }
}


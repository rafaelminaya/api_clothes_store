package com.rminaya.clothes.store.clothes_store.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Clothes Store API",
                version = "1.0",
                description = "Documentación for endpoints in Clothes Store"))
public class OpenApiConfig {
}

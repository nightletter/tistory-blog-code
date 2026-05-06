package me.nightletter.swaggercustom.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi internal(
            @Qualifier("internalCustomizerFilter") OpenApiCustomizer openApiCustomizer
    ) {
        return GroupedOpenApi.builder()
                .group("private-v1")
                .pathsToMatch("/api/v1/**")
                .addOpenApiCustomizer(openApiCustomizer)
                .build();
    }

    @Bean
    public GroupedOpenApi external(
            @Qualifier("externalCustomizerFilter") OpenApiCustomizer openApiCustomizer
    ) {
        return GroupedOpenApi.builder()
                .group("frontend-v1")
                .pathsToMatch("/api/v1/**")
                .addOpenApiCustomizer(openApiCustomizer)   // 태그·경로 필터
                .build();
    }
}

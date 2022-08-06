package com.alraedah.cycle.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.RequestHandlerSelectors.any;
import static springfox.documentation.spi.DocumentationType.OAS_30;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket appApi() {
        return new Docket(OAS_30)
                .useDefaultResponseMessages(false)
                .select()
                .apis(any())
                .paths(PathSelectors.any())
                .build();
    }
}

package de.reroba.pricecalculator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket ingredientsApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("ingredients-api")
                .select()
                .apis(RequestHandlerSelectors.basePackage("de.reroba.pricecalculator.ingredients"))
                .paths(PathSelectors.any())
                //.apis(RequestHandlerSelectors.basePackage("org.baeldung.web.controller"))
                //.paths(PathSelectors.ant("/foos/*"))
                .build()
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET,
                        newArrayList(new ResponseMessageBuilder()
                                        .code(500)
                                        .message("500 message")
                                        .responseModel(new ModelRef("Error"))
                                        .build(),
                                new ResponseMessageBuilder()
                                        .code(403)
                                        .message("Forbidden!")
                                        .build()));
    }

    public <T> ArrayList<T> newArrayList(T ... elements) {
        return new ArrayList<T>(Arrays.asList(elements));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("My REST API")
                .description("Some custom description of API.")
                .version("API TOS")
                .termsOfServiceUrl("Terms of service")
                .contact(new Contact("John Doe", "www.example.com", "myeaddress@company.com"))
                .license("License of API")
                .licenseUrl("API license URL")
                .extensions(Collections.emptyList())
                .build();
    }

    @Bean
    public Docket errorApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("error-api")
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/error"))
                .build();
    }
}
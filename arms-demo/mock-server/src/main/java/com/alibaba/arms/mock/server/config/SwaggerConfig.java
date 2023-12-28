//package com.alibaba.arms.mock.server.config;
//
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.Contact;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//
///**
// * Swagger配置类
// */
//@Configuration
//public class SwaggerConfig {
//    @Bean
//    public Docket docket() {
//        return new Docket(DocumentationType.OAS_30)
//                .apiInfo(apiInfo()).enable(true)
//                .select()
//                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
//                .paths(PathSelectors.any())
//                .build();
//    }
//
//    private ApiInfo apiInfo() {
//        return new ApiInfoBuilder()
//                .title("ARM_MOCK项目接口文档")
//                .description("用于构造各种调用场景,测试Insights的能力")
//                .contact(new Contact("sanmu", "", ""))
//                .version("1.0")
//                .build();
//    }
//}

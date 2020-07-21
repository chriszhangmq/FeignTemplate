package com.chris.userservice.config;

import com.google.common.base.Predicates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yaoxs@shukun.net
 * @date 2020-01-08 09:57
 */
@Configuration
public class SwaggerConfig {

    @Autowired
    private ApplicationConfig applicationConfig;

    @Bean
    public Docket createRestApi() {
        /*** 添加token认证栏 ***/
//        List<Parameter> pars = new ArrayList<>();
//        // 添加head参数AUTHORIZATION
//        ParameterBuilder tokenPar = new ParameterBuilder();
//        tokenPar.name(SystemConstants.AUTHORIZATION).description("令牌").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
//        pars.add(tokenPar.build());
//        // 添加head参数INSTITUTION_ID
//        ParameterBuilder institutionPar = new ParameterBuilder();
//        institutionPar.name(SystemConstants.INSTITUTION_ID).description("机构id").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
//        pars.add(institutionPar.build());

        // 配置swagger
        Docket result = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(
                        Predicates.or(
                                RequestHandlerSelectors.basePackage("com.chris.userservice.controller")
                        )
                )
                .paths(PathSelectors.any())
                .build();
//                .globalOperationParameters(pars);
        result.enable(applicationConfig.getSwaggerEnable());
        return result;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("User Service 文档")
                .description("简单优雅的restful风格")
                .termsOfServiceUrl("http://localhost:8081")
                .version("1.0")
                .build();
    }
}

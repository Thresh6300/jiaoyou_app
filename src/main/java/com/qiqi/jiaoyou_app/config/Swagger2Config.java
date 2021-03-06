package com.qiqi.jiaoyou_app.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;
 
//参考：http://blog.csdn.net/catoop/article/details/50668896

/**
 * @author xiebq
 *
 */
@Configuration
@EnableSwagger2
@EnableKnife4j
public class Swagger2Config {
 
    @Bean
    public Docket createRestApi() {
        List<Parameter> pars = new ArrayList<Parameter>();
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.qiqi.jiaoyou_app.controller"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(pars)
                .apiInfo(apiInfo());
    }
 
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("社交APP RESTful API")
                .description("社交APP RESTful API")
                .termsOfServiceUrl("")
                .version("1.0")
                .build();
    }
 
}
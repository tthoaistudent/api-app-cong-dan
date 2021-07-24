/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.vnpt.digo.basepad.config;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.service.Contact;

/**
 *
 * @author hp
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("${info.app.name:ServiceTitle}")
    private String title;

    @Value("${info.app.description:ServiceDescription}")
    private String description;

    @Bean("digoDocket")
    public Docket getDocket() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2);
        docket.select().apis(RequestHandlerSelectors.basePackage("vn.vnpt.digo.basepad.api")).build();
        docket.apiInfo(this.apiInfo());
        docket.groupName("digo");
        docket.directModelSubstitute(ObjectId.class, String.class);
        return docket;
    }

    ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(title)
                .description(description)
                .license("")
                .licenseUrl("")
                .termsOfServiceUrl("")
                .version("1.0.0")
                .contact(new Contact("", "", ""))
                .build();
    }

}

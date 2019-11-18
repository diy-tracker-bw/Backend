package com.lambdaschool.usermodel.config;

import com.fasterxml.classmate.TypeResolver;
import com.lambdaschool.usermodel.models.ErrorDetail;
import com.lambdaschool.usermodel.models.TokenModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Pageable;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class Swagger2Config
{
    @Autowired
    private TypeResolver resolver;

    @Bean
    public Docket api()
    {
        return new Docket(DocumentationType.SWAGGER_2).select()
                                                      .apis(RequestHandlerSelectors.basePackage("com.lambdaschool"))
                                                      .paths(PathSelectors.any())
                                                      .build()
                                                      .useDefaultResponseMessages(false) // Allows only my exception responses
                                                      .ignoredParameterTypes(Pageable.class) // allows only my paging parameter list
                                                      .apiInfo(apiEndPointsInfo())
                                                      .pathMapping("/")
                                                      .additionalModels(resolver.resolve(ErrorDetail.class),
                                                                        resolver.resolve(TokenModel.class))
                                                      .ignoredParameterTypes()
                                                      .tags(new Tag("UserEndpoints",
                                                                    "Endpoints driven by users. (Authenticated Users can use these endpoints)"),
                                                            new Tag("ProjectEndPoints",
                                                                    "Endpoints for projects. (Authenticated Users can use these endpoints)"),
                                                            new Tag("RolesEndpoints",
                                                                    "Endpoints driven by roles. (Endpoints that only Admins should be able to used)"),
//                                                            new Tag("UseremailEndpoints",
//                                                                    "Endpoints driven by Useremails. (Endpoints that only Admins should be able to use)"),
                                                            new Tag("LogoutEndpoints",
                                                                    "Endpoint for logging out a user. (Current Token becomes unusable)"),
                                                            new Tag("OpenEndpoints",
                                                                    "Endpoints available to all. (No token required to use these endpoints)"));

    }

    private ApiInfo apiEndPointsInfo()
    {
        return new ApiInfoBuilder().title("DIY Tracker")
                                   .description("Backend Documentation to share and learn DIY Projects")
                                   .contact(new Contact("Patrick Chow",
                                                        "",
                                                        "PatrickChow0803@gmail.com.com"))
                                   .version(("1.0.0"))
                                   .build();
    }
}

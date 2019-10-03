package personal.ciai.vetclinic.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Contact
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
@EnableSwagger2
class SwaggerConfig {
    @Bean
    fun api(): Docket =
        Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.basePackage("personal.ciai.vetclinic"))
            .paths(PathSelectors.any())
            .build().apiInfo(apiEndpointsInfo())

    fun apiEndpointsInfo(): ApiInfo =
        ApiInfoBuilder()
            .title("Spring boot REST API Example for CIAI 19/20").description("CIAI 2019 VetClinic REST API")
            .contact(Contact("Group xx", "toAdd", "...@gmail.com"))
            .license("Apach 2.0")
            .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
            .version("0.0.1")
            .build()
}

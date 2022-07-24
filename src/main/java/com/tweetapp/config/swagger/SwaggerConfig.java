package com.tweetapp.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Anshuman Panda
 * @project TweetApp-JAVA-API
 */

@Component
@EnableSwagger2
public class SwaggerConfig {
	@Bean
	public Docket newsApi() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("Tweet App Service").apiInfo(apiInfo()).select()
				.build();
	}

	/**
	 * Internal method used by Swagger to configure its test user-interface.
	 *
	 * @return Object of various configured application properties.
	 */
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Tweet App Api Service").description("Tweet App - Stay Connected.")
				.termsOfServiceUrl("TBD").contact(new Contact("Anshuman",
						"https://www.linkedin.com/in/anshuman-panda-2b24a118a", "anshumanpanda318@gmail.com"))
				.license("License of API").version("1.0").build();
	}

}
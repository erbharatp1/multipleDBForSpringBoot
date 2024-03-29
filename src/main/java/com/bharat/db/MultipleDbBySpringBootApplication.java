package com.bharat.db;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
public class MultipleDbBySpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(MultipleDbBySpringBootApplication.class, args);
	}

	@Bean
	public Docket oauthApi() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.bharat.db")).paths(PathSelectors.any())

				.apis(RequestHandlerSelectors.any())

				.build().apiInfo(metaData());
	}

	private ApiInfo metaData() {

		return new ApiInfoBuilder().title("MP ONLINE").description("mp online services").termsOfServiceUrl("")
				.contact(new Contact("Bharat", "", "patelbharat519@gmail.com")).license("Apache License Version 2.0")
				.licenseUrl("https://www.apache.org/licenses/LICENSE-2.0").version("2.0").build();

	}
}

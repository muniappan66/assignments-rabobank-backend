package com.assignments.rabobank;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * The Class RaboBankApplication.
 */
@SpringBootApplication
@EnableSwagger2
public class RaboBankApplication {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(RaboBankApplication.class, args);
	}

	@Bean
	public Docket swaggerConfiguration() {
		return new Docket(DocumentationType.SWAGGER_2).select().paths(PathSelectors.ant("/v1.0.0/*"))
				.apis(RequestHandlerSelectors.basePackage("com.assignments")).build().apiInfo(apiDetails());

	}

	private ApiInfo apiDetails() {
		return new ApiInfo("Payment initiation sandbox API" + "", "1.0",
				"This API receives a payment initiation request. The API validates the requ`est and returns transaction status with signature that the client can verify the response.",
				"", new springfox.documentation.service.Contact("", "API License", ""), "", "",
				Collections.emptyList());
	}

}

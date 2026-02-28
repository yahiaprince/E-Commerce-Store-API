package com.yahiaprince.store.common;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;

@Configuration
public class OpenAPIConfiguration {

        @Bean
        public OpenAPI defineOpenApi() {

                final String securitySchemeName = "bearerAuth";

                Server server = new Server();
                server.setUrl("http://localhost:8080");
                server.setDescription("Development");

                Contact myContact = new Contact();
                myContact.setName("Yahia ElPrince");
                myContact.setEmail("yahiapsalah@gmail.com");

                Info information = new Info()
                                .title("E-Commerce Store API")
                                .version("1.0")
                                .description(
                                                "REST API for an e-commerce store: authentication, users, products, carts, orders, and payments.")
                                .contact(myContact);

                return new OpenAPI()
                                .info(information)
                                .servers(List.of(server))
                                .tags(List.of(
                                                new Tag().name("Auth").description("Authentication & tokens"),
                                                new Tag().name("Users").description("User management"),
                                                new Tag().name("Products").description("Product catalog"),
                                                new Tag().name("Carts").description("Shopping cart"),
                                                new Tag().name("Orders").description("Orders & checkout"),
                                                new Tag().name("Admin").description("Admin operations")))
                                .components(new Components()
                                                .addSecuritySchemes(securitySchemeName,
                                                                new SecurityScheme()
                                                                                .name(securitySchemeName)
                                                                                .type(SecurityScheme.Type.HTTP)
                                                                                .scheme("bearer")
                                                                                .bearerFormat("JWT")))
                                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName));
        }
}
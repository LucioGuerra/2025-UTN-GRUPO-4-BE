package org.agiles.bolsaestudiantil.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Unijobs")
                        .version("1.0.0")
                        .description("API de gestión de ofertas, postulaciones y usuarios.")
                        .contact(new Contact()
                                .name("Equipo UTN Grupo 4")
                                .email("contacto@unijobs.com")
                        )
                );
    }
}

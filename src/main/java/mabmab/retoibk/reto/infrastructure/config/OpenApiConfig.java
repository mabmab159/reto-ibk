package mabmab.retoibk.reto.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Reto IBK - API de Productos y Pedidos")
                        .version("1.0.0")
                        .description("API RESTful reactiva para gestión de productos y pedidos con descuentos automáticos")
                        .contact(new Contact()
                                .name("Reto IBK")
                                .email("contact@retoibk.com")));
    }
}
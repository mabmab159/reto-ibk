package mabmab.retoibk.configuration.openapi;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
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
                        .description("API RESTful reactiva construida con Spring WebFlux y R2DBC. " +
                                "Incluye gestión de productos, pedidos con cálculo automático de descuentos " +
                                "(10% si total > $1000, 5% adicional si > 5 productos), " +
                                "control de concurrencia optimista y manejo reactivo de transacciones.")
                        .contact(new Contact()
                                .name("Reto IBK")
                                .email("contact@retoibk.com")));
    }
}
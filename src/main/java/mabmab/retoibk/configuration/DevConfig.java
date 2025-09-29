package mabmab.retoibk.configuration;

import mabmab.retoibk.reto.infrastructure.dataproviders.repositories.PedidoItemRepository;
import mabmab.retoibk.reto.infrastructure.dataproviders.repositories.PedidoRepository;
import mabmab.retoibk.reto.infrastructure.dataproviders.repositories.ProductoRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import static org.mockito.Mockito.mock;

@Configuration
@Profile("dev")
public class DevConfig {

    @Bean
    public PedidoRepository pedidoRepository() {
        return mock(PedidoRepository.class);
    }

    @Bean
    public PedidoItemRepository pedidoItemRepository() {
        return mock(PedidoItemRepository.class);
    }

    @Bean
    public ProductoRepository productoRepository() {
        return mock(ProductoRepository.class);
    }
}
package mabmab.retoibk.reto.application.usecases;

import mabmab.retoibk.reto.application.usecases.implementation.IProductoUseCase;
import mabmab.retoibk.reto.domain.models.Producto;
import mabmab.retoibk.reto.domain.services.ProductoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IProductoServicePortTest {

    @Mock
    private ProductoService productoService;

    @InjectMocks
    private IProductoUseCase productoUseCase;

    private Producto producto;

    @BeforeEach
    void setUp() {
        producto = new Producto(1L, "Laptop", new BigDecimal("1500"), 10, 1L);
    }

    @Test
    void findAll_ShouldReturnAllProductos() {
        when(productoService.findAll()).thenReturn(Flux.just(producto));

        StepVerifier.create(productoUseCase.findAll())
                .expectNext(producto)
                .verifyComplete();

        verify(productoService).findAll();
    }

    @Test
    void findById_ShouldReturnProducto() {
        when(productoService.findById(1L)).thenReturn(Mono.just(producto));

        StepVerifier.create(productoUseCase.findById(1L))
                .expectNext(producto)
                .verifyComplete();

        verify(productoService).findById(1L);
    }

    @Test
    void save_ShouldSaveProducto() {
        when(productoService.save(producto)).thenReturn(Mono.just(producto));

        StepVerifier.create(productoUseCase.save(producto))
                .expectNext(producto)
                .verifyComplete();

        verify(productoService).save(producto);
    }

    @Test
    void update_ShouldUpdateProducto() {
        when(productoService.update(1L, producto)).thenReturn(Mono.just(producto));

        StepVerifier.create(productoUseCase.update(1L, producto))
                .expectNext(producto)
                .verifyComplete();

        verify(productoService).update(1L, producto);
    }

    @Test
    void deleteById_ShouldDeleteProducto() {
        when(productoService.deleteById(1L)).thenReturn(Mono.empty());

        StepVerifier.create(productoUseCase.deleteById(1L))
                .verifyComplete();

        verify(productoService).deleteById(1L);
    }

    @Test
    void findAllWithPageable_ShouldReturnPagedProductos() {
        Pageable pageable = PageRequest.of(0, 10);
        when(productoService.findAll(pageable)).thenReturn(Flux.just(producto));

        StepVerifier.create(productoUseCase.findAll(pageable))
                .expectNext(producto)
                .verifyComplete();

        verify(productoService).findAll(pageable);
    }


}
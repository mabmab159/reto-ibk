package mabmab.retoibk.reto.domain.services;

import mabmab.retoibk.reto.domain.models.Producto;
import mabmab.retoibk.reto.domain.ports.out.IProductoRepositoryPort;
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

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductoServiceTest {

    @Mock
    private IProductoRepositoryPort IProductoRepositoryPort;

    @InjectMocks
    private ProductoService productoService;

    private Producto producto;
    private Producto existingProducto;

    @BeforeEach
    void setUp() {
        producto = new Producto(1L, "Laptop", new BigDecimal("1500"), 10, 1L);
        existingProducto = new Producto(1L, "Old Laptop", new BigDecimal("1000"), 5, 1L);
    }

    @Test
    void findAll_ShouldReturnAllProductos() {
        when(IProductoRepositoryPort.findAll()).thenReturn(Flux.just(producto));

        StepVerifier.create(productoService.findAll())
                .expectNext(producto)
                .verifyComplete();

        verify(IProductoRepositoryPort).findAll();
    }

    @Test
    void findById_ShouldReturnProducto() {
        when(IProductoRepositoryPort.findById(1L)).thenReturn(Mono.just(producto));

        StepVerifier.create(productoService.findById(1L))
                .expectNext(producto)
                .verifyComplete();

        verify(IProductoRepositoryPort).findById(1L);
    }

    @Test
    void save_ShouldSaveProducto() {
        when(IProductoRepositoryPort.save(producto)).thenReturn(Mono.just(producto));

        StepVerifier.create(productoService.save(producto))
                .expectNext(producto)
                .verifyComplete();

        verify(IProductoRepositoryPort).save(producto);
    }

    @Test
    void update_WhenProductoExists_ShouldUpdateAndSave() {
        when(IProductoRepositoryPort.findById(1L)).thenReturn(Mono.just(existingProducto));
        when(IProductoRepositoryPort.save(any(Producto.class))).thenReturn(Mono.just(producto));

        StepVerifier.create(productoService.update(1L, producto))
                .expectNextMatches(updated ->
                        updated.getNombre().equals(producto.getNombre()) &&
                                updated.getPrecio().equals(producto.getPrecio()) &&
                                updated.getStock() == producto.getStock()
                )
                .verifyComplete();

        verify(IProductoRepositoryPort).findById(1L);
        verify(IProductoRepositoryPort).save(any(Producto.class));
    }

    @Test
    void update_WhenProductoNotExists_ShouldReturnEmpty() {
        when(IProductoRepositoryPort.findById(1L)).thenReturn(Mono.empty());

        StepVerifier.create(productoService.update(1L, producto))
                .verifyComplete();

        verify(IProductoRepositoryPort).findById(1L);
        verify(IProductoRepositoryPort, never()).save(any());
    }

    @Test
    void deleteById_ShouldDeleteProducto() {
        when(IProductoRepositoryPort.deleteById(1L)).thenReturn(Mono.empty());

        StepVerifier.create(productoService.deleteById(1L))
                .verifyComplete();

        verify(IProductoRepositoryPort).deleteById(1L);
    }

    @Test
    void findAllWithPageable_ShouldReturnPagedProductos() {
        Pageable pageable = PageRequest.of(0, 10);
        when(IProductoRepositoryPort.findAll(pageable)).thenReturn(Flux.just(producto));

        StepVerifier.create(productoService.findAll(pageable))
                .expectNext(producto)
                .verifyComplete();

        verify(IProductoRepositoryPort).findAll(pageable);
    }


}
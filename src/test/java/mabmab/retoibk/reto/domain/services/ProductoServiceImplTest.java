package mabmab.retoibk.reto.domain.services;

import mabmab.retoibk.reto.domain.models.Producto;
import mabmab.retoibk.reto.domain.ports.out.ProductoRepositoryPort;

import java.math.BigDecimal;
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

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductoServiceImplTest {

    @Mock
    private ProductoRepositoryPort productoRepositoryPort;

    @InjectMocks
    private ProductoServiceImpl productoService;

    private Producto producto;
    private Producto existingProducto;

    @BeforeEach
    void setUp() {
        producto = new Producto(1L, "Laptop", new BigDecimal("1500"), 10, 1L);
        existingProducto = new Producto(1L, "Old Laptop", new BigDecimal("1000"), 5, 1L);
    }

    @Test
    void findAll_ShouldReturnAllProductos() {
        when(productoRepositoryPort.findAll()).thenReturn(Flux.just(producto));

        StepVerifier.create(productoService.findAll())
                .expectNext(producto)
                .verifyComplete();

        verify(productoRepositoryPort).findAll();
    }

    @Test
    void findById_ShouldReturnProducto() {
        when(productoRepositoryPort.findById(1L)).thenReturn(Mono.just(producto));

        StepVerifier.create(productoService.findById(1L))
                .expectNext(producto)
                .verifyComplete();

        verify(productoRepositoryPort).findById(1L);
    }

    @Test
    void save_ShouldSaveProducto() {
        when(productoRepositoryPort.save(producto)).thenReturn(Mono.just(producto));

        StepVerifier.create(productoService.save(producto))
                .expectNext(producto)
                .verifyComplete();

        verify(productoRepositoryPort).save(producto);
    }

    @Test
    void update_WhenProductoExists_ShouldUpdateAndSave() {
        when(productoRepositoryPort.findById(1L)).thenReturn(Mono.just(existingProducto));
        when(productoRepositoryPort.save(any(Producto.class))).thenReturn(Mono.just(producto));

        StepVerifier.create(productoService.update(1L, producto))
                .expectNextMatches(updated -> 
                    updated.getNombre().equals(producto.getNombre()) &&
                    updated.getPrecio().equals(producto.getPrecio()) &&
                    updated.getStock() == producto.getStock()
                )
                .verifyComplete();

        verify(productoRepositoryPort).findById(1L);
        verify(productoRepositoryPort).save(any(Producto.class));
    }

    @Test
    void update_WhenProductoNotExists_ShouldReturnEmpty() {
        when(productoRepositoryPort.findById(1L)).thenReturn(Mono.empty());

        StepVerifier.create(productoService.update(1L, producto))
                .verifyComplete();

        verify(productoRepositoryPort).findById(1L);
        verify(productoRepositoryPort, never()).save(any());
    }

    @Test
    void deleteById_ShouldDeleteProducto() {
        when(productoRepositoryPort.deleteById(1L)).thenReturn(Mono.empty());

        StepVerifier.create(productoService.deleteById(1L))
                .verifyComplete();

        verify(productoRepositoryPort).deleteById(1L);
    }

    @Test
    void findAllWithPageable_ShouldReturnPagedProductos() {
        Pageable pageable = PageRequest.of(0, 10);
        when(productoRepositoryPort.findAll(pageable)).thenReturn(Flux.just(producto));

        StepVerifier.create(productoService.findAll(pageable))
                .expectNext(producto)
                .verifyComplete();

        verify(productoRepositoryPort).findAll(pageable);
    }

    @Test
    void findByNombre_ShouldReturnProductosByNombre() {
        when(productoRepositoryPort.findByNombreContainingIgnoreCase("Laptop")).thenReturn(Flux.just(producto));

        StepVerifier.create(productoService.findByNombre("Laptop"))
                .expectNext(producto)
                .verifyComplete();

        verify(productoRepositoryPort).findByNombreContainingIgnoreCase("Laptop");
    }
}
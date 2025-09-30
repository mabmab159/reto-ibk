package mabmab.retoibk.reto.infrastructure.adapters;

import mabmab.retoibk.reto.domain.models.Producto;
import mabmab.retoibk.reto.infrastructure.dataproviders.entities.ProductoEntity;
import mabmab.retoibk.reto.infrastructure.dataproviders.mappers.ProductoMapper;
import mabmab.retoibk.reto.infrastructure.dataproviders.repositories.ProductoRepository;
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
class ProductoRepositoryAdapterTest {

    @Mock
    private ProductoRepository productoRepository;

    @Mock
    private ProductoMapper productoMapper;

    @InjectMocks
    private IProductoRepositoryAdapter productoRepositoryAdapter;

    private Producto producto;
    private ProductoEntity productoEntity;

    @BeforeEach
    void setUp() {
        producto = new Producto(1L, "Laptop", new BigDecimal("1500"), 10, 1L);
        productoEntity = new ProductoEntity();
        productoEntity.setId(1L);
        productoEntity.setNombre("Laptop");
        productoEntity.setPrecio(new BigDecimal("1500"));
        productoEntity.setStock(10);
    }

    @Test
    void findAll_ShouldReturnAllProductos() {
        when(productoRepository.findAll()).thenReturn(Flux.just(productoEntity));
        when(productoMapper.toDomain(productoEntity)).thenReturn(producto);

        StepVerifier.create(productoRepositoryAdapter.findAll())
                .expectNext(producto)
                .verifyComplete();

        verify(productoRepository).findAll();
        verify(productoMapper).toDomain(productoEntity);
    }

    @Test
    void findById_ShouldReturnProducto() {
        when(productoRepository.findById(1L)).thenReturn(Mono.just(productoEntity));
        when(productoMapper.toDomain(productoEntity)).thenReturn(producto);

        StepVerifier.create(productoRepositoryAdapter.findById(1L))
                .expectNext(producto)
                .verifyComplete();

        verify(productoRepository).findById(1L);
        verify(productoMapper).toDomain(productoEntity);
    }

    @Test
    void save_ShouldSaveProducto() {
        when(productoMapper.toEntity(producto)).thenReturn(productoEntity);
        when(productoRepository.save(productoEntity)).thenReturn(Mono.just(productoEntity));
        when(productoMapper.toDomain(productoEntity)).thenReturn(producto);

        StepVerifier.create(productoRepositoryAdapter.save(producto))
                .expectNext(producto)
                .verifyComplete();

        verify(productoMapper).toEntity(producto);
        verify(productoRepository).save(productoEntity);
        verify(productoMapper).toDomain(productoEntity);
    }

    @Test
    void deleteById_ShouldDeleteProducto() {
        when(productoRepository.deleteById(1L)).thenReturn(Mono.empty());

        StepVerifier.create(productoRepositoryAdapter.deleteById(1L))
                .verifyComplete();

        verify(productoRepository).deleteById(1L);
    }

    @Test
    void findAllWithPageable_ShouldReturnPagedProductos() {
        Pageable pageable = PageRequest.of(0, 10);
        when(productoRepository.findAllBy(pageable)).thenReturn(Flux.just(productoEntity));
        when(productoMapper.toDomain(productoEntity)).thenReturn(producto);

        StepVerifier.create(productoRepositoryAdapter.findAll(pageable))
                .expectNext(producto)
                .verifyComplete();

        verify(productoRepository).findAllBy(pageable);
        verify(productoMapper).toDomain(productoEntity);
    }
}
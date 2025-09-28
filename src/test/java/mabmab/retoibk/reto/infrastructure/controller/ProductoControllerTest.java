package mabmab.retoibk.reto.infrastructure.controller;

import mabmab.retoibk.reto.application.ports.ProductoUseCasePort;
import mabmab.retoibk.reto.domain.models.Producto;
import mabmab.retoibk.reto.infrastructure.controller.dto.ProductoRequest;
import mabmab.retoibk.reto.infrastructure.controller.mappers.ProductoControllerMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductoControllerTest {

    @Mock
    private ProductoUseCasePort productoUseCase;
    
    @Mock
    private ProductoControllerMapper mapper;
    
    @InjectMocks
    private ProductoController productoController;
    
    private Producto producto;
    private ProductoRequest request;
    
    @BeforeEach
    void setUp() {
        producto = new Producto(1L, "Laptop", 1500L, 10);
        request = new ProductoRequest("Laptop", 1500L, 10);
    }
    
    @Test
    void getAllProductos_ShouldReturnAllProductos() {
        when(productoUseCase.findAll(any(PageRequest.class))).thenReturn(Flux.just(producto));
        when(mapper.toResponse(producto)).thenReturn(new mabmab.retoibk.reto.infrastructure.controller.dto.ProductoResponse(1L, "Laptop", 1500L, 10));
        
        StepVerifier.create(productoController.getAllProductos(0, 10))
                .expectNextMatches(response -> response.getStatusCode() == HttpStatus.OK)
                .verifyComplete();
    }
    
    @Test
    void getProductoById_WhenExists_ShouldReturnProducto() {
        when(productoUseCase.findById(1L)).thenReturn(Mono.just(producto));
        when(mapper.toResponse(producto)).thenReturn(new mabmab.retoibk.reto.infrastructure.controller.dto.ProductoResponse(1L, "Laptop", 1500L, 10));
        
        StepVerifier.create(productoController.getProductoById(1L))
                .expectNextMatches(responseEntity -> 
                    responseEntity.getStatusCode() == HttpStatus.OK &&
                    responseEntity.getBody() != null
                )
                .verifyComplete();
    }
    
    @Test
    void getProductoById_WhenNotExists_ShouldReturnNotFound() {
        when(productoUseCase.findById(1L)).thenReturn(Mono.empty());
        
        StepVerifier.create(productoController.getProductoById(1L))
                .expectNextMatches(responseEntity -> 
                    responseEntity.getStatusCode() == HttpStatus.NOT_FOUND
                )
                .verifyComplete();
    }
    
    @Test
    void createProducto_ShouldReturnCreated() {
        when(mapper.toDomain(request)).thenReturn(producto);
        when(productoUseCase.save(producto)).thenReturn(Mono.just(producto));
        when(mapper.toResponse(producto)).thenReturn(new mabmab.retoibk.reto.infrastructure.controller.dto.ProductoResponse(1L, "Laptop", 1500L, 10));
        
        StepVerifier.create(productoController.createProducto(request))
                .expectNextMatches(responseEntity -> 
                    responseEntity.getStatusCode() == HttpStatus.CREATED &&
                    responseEntity.getBody() != null
                )
                .verifyComplete();
    }
    
    @Test
    void updateProducto_WhenExists_ShouldReturnUpdated() {
        when(mapper.toDomain(1L, request)).thenReturn(producto);
        when(productoUseCase.update(1L, producto)).thenReturn(Mono.just(producto));
        when(mapper.toResponse(producto)).thenReturn(new mabmab.retoibk.reto.infrastructure.controller.dto.ProductoResponse(1L, "Laptop", 1500L, 10));
        
        StepVerifier.create(productoController.updateProducto(1L, request))
                .expectNextMatches(responseEntity -> 
                    responseEntity.getStatusCode() == HttpStatus.OK &&
                    responseEntity.getBody() != null
                )
                .verifyComplete();
    }
    
    @Test
    void updateProducto_WhenNotExists_ShouldReturnNotFound() {
        when(mapper.toDomain(1L, request)).thenReturn(producto);
        when(productoUseCase.update(1L, producto)).thenReturn(Mono.empty());
        
        StepVerifier.create(productoController.updateProducto(1L, request))
                .expectNextMatches(responseEntity -> 
                    responseEntity.getStatusCode() == HttpStatus.NOT_FOUND
                )
                .verifyComplete();
    }
    
    @Test
    void deleteProducto_ShouldReturnNoContent() {
        when(productoUseCase.deleteById(1L)).thenReturn(Mono.empty());
        
        StepVerifier.create(productoController.deleteProducto(1L))
                .expectNextMatches(responseEntity -> 
                    responseEntity.getStatusCode() == HttpStatus.NO_CONTENT
                )
                .verifyComplete();
    }
    
    @Test
    void buscarPorNombre_ShouldReturnProductosByNombre() {
        when(productoUseCase.findByNombre("Laptop")).thenReturn(Flux.just(producto));
        when(mapper.toResponse(producto)).thenReturn(new mabmab.retoibk.reto.infrastructure.controller.dto.ProductoResponse(1L, "Laptop", 1500L, 10));
        
        StepVerifier.create(productoController.buscarPorNombre("Laptop"))
                .expectNextMatches(response -> response.getStatusCode() == HttpStatus.OK)
                .verifyComplete();
    }
}
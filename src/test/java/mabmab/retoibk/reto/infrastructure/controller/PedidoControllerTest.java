package mabmab.retoibk.reto.infrastructure.controller;

import mabmab.retoibk.reto.application.ports.PedidoUseCasePort;
import mabmab.retoibk.reto.domain.models.Pedido;
import mabmab.retoibk.reto.infrastructure.controller.dto.PedidoRequest;
import mabmab.retoibk.reto.infrastructure.controller.dto.PedidoResponse;
import mabmab.retoibk.reto.infrastructure.controller.mappers.PedidoControllerMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PedidoControllerTest {

    @Mock
    private PedidoUseCasePort pedidoUseCase;
    
    @Mock
    private PedidoControllerMapper mapper;
    
    @InjectMocks
    private PedidoController pedidoController;
    
    private Pedido pedido;
    private PedidoRequest request;
    private PedidoResponse response;
    
    @BeforeEach
    void setUp() {
        pedido = new Pedido(1L, LocalDate.now(), 1000L, true);
        request = new PedidoRequest(LocalDate.now(), 1000L, true);
        response = new PedidoResponse(1L, LocalDate.now(), 1000L, true);
    }
    
    @Test
    void getAllPedidos_ShouldReturnCollectionWithLinks() {
        when(pedidoUseCase.findAll(any(PageRequest.class))).thenReturn(Flux.just(pedido));
        when(mapper.toResponse(pedido)).thenReturn(response);
        
        StepVerifier.create(pedidoController.getAllPedidos(0, 10))
                .expectNextMatches(responseEntity -> {
                    CollectionModel<PedidoResponse> body = responseEntity.getBody();
                    return responseEntity.getStatusCode() == HttpStatus.OK &&
                           body != null && 
                           body.hasLinks() &&
                           !body.getContent().isEmpty();
                })
                .verifyComplete();
    }
    
    @Test
    void getPedidoById_WhenExists_ShouldReturnPedidoWithLinks() {
        when(pedidoUseCase.findById(1L)).thenReturn(Mono.just(pedido));
        when(mapper.toResponse(pedido)).thenReturn(response);
        
        StepVerifier.create(pedidoController.getPedidoById(1L))
                .expectNextMatches(responseEntity -> 
                    responseEntity.getStatusCode() == HttpStatus.OK &&
                    responseEntity.getBody() != null &&
                    responseEntity.getBody().hasLinks()
                )
                .verifyComplete();
    }
    
    @Test
    void getPedidoById_WhenNotExists_ShouldReturnNotFound() {
        when(pedidoUseCase.findById(1L)).thenReturn(Mono.empty());
        
        StepVerifier.create(pedidoController.getPedidoById(1L))
                .expectNextMatches(responseEntity -> 
                    responseEntity.getStatusCode() == HttpStatus.NOT_FOUND
                )
                .verifyComplete();
    }
    
    @Test
    void createPedido_ShouldReturnCreatedWithLinks() {
        when(mapper.toDomain(request)).thenReturn(pedido);
        when(pedidoUseCase.save(pedido)).thenReturn(Mono.just(pedido));
        when(mapper.toResponse(pedido)).thenReturn(response);
        
        StepVerifier.create(pedidoController.createPedido(request))
                .expectNextMatches(responseEntity -> 
                    responseEntity.getStatusCode() == HttpStatus.CREATED &&
                    responseEntity.getBody() != null &&
                    responseEntity.getBody().hasLinks()
                )
                .verifyComplete();
    }
    
    @Test
    void updatePedido_WhenExists_ShouldReturnUpdatedWithLinks() {
        when(mapper.toDomain(1L, request)).thenReturn(pedido);
        when(pedidoUseCase.update(1L, pedido)).thenReturn(Mono.just(pedido));
        when(mapper.toResponse(pedido)).thenReturn(response);
        
        StepVerifier.create(pedidoController.updatePedido(1L, request))
                .expectNextMatches(responseEntity -> 
                    responseEntity.getStatusCode() == HttpStatus.OK &&
                    responseEntity.getBody() != null &&
                    responseEntity.getBody().hasLinks()
                )
                .verifyComplete();
    }
    
    @Test
    void updatePedido_WhenNotExists_ShouldReturnNotFound() {
        when(mapper.toDomain(1L, request)).thenReturn(pedido);
        when(pedidoUseCase.update(1L, pedido)).thenReturn(Mono.empty());
        
        StepVerifier.create(pedidoController.updatePedido(1L, request))
                .expectNextMatches(responseEntity -> 
                    responseEntity.getStatusCode() == HttpStatus.NOT_FOUND
                )
                .verifyComplete();
    }
    
    @Test
    void deletePedido_ShouldReturnNoContent() {
        when(pedidoUseCase.deleteById(1L)).thenReturn(Mono.empty());
        
        StepVerifier.create(pedidoController.deletePedido(1L))
                .expectNextMatches(responseEntity -> 
                    responseEntity.getStatusCode() == HttpStatus.NO_CONTENT
                )
                .verifyComplete();
    }
    
    @Test
    void buscarPorEstado_ShouldReturnCollectionWithLinks() {
        when(pedidoUseCase.findByEstado(true)).thenReturn(Flux.just(pedido));
        when(mapper.toResponse(pedido)).thenReturn(response);
        
        StepVerifier.create(pedidoController.buscarPorEstado(true))
                .expectNextMatches(responseEntity -> {
                    CollectionModel<PedidoResponse> body = responseEntity.getBody();
                    return responseEntity.getStatusCode() == HttpStatus.OK &&
                           body != null && 
                           body.hasLinks();
                })
                .verifyComplete();
    }
}
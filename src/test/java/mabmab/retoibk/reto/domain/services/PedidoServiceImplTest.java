package mabmab.retoibk.reto.domain.services;

import mabmab.retoibk.reto.domain.models.Pedido;
import mabmab.retoibk.reto.domain.ports.out.PedidoRepositoryPort;
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

import java.time.LocalDate;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PedidoServiceImplTest {

    @Mock
    private PedidoRepositoryPort pedidoRepositoryPort;

    @InjectMocks
    private PedidoServiceImpl pedidoService;

    private Pedido pedido;
    private Pedido existingPedido;

    @BeforeEach
    void setUp() {
        pedido = new Pedido(1L, LocalDate.now(), 1000L, true);
        existingPedido = new Pedido(1L, LocalDate.now().minusDays(1), 500L, false);
    }

    @Test
    void findAll_ShouldReturnAllPedidos() {
        when(pedidoRepositoryPort.findAll()).thenReturn(Flux.just(pedido));

        StepVerifier.create(pedidoService.findAll())
                .expectNext(pedido)
                .verifyComplete();

        verify(pedidoRepositoryPort).findAll();
    }

    @Test
    void findById_ShouldReturnPedido() {
        when(pedidoRepositoryPort.findById(1L)).thenReturn(Mono.just(pedido));

        StepVerifier.create(pedidoService.findById(1L))
                .expectNext(pedido)
                .verifyComplete();

        verify(pedidoRepositoryPort).findById(1L);
    }

    @Test
    void save_ShouldSavePedido() {
        when(pedidoRepositoryPort.save(pedido)).thenReturn(Mono.just(pedido));

        StepVerifier.create(pedidoService.save(pedido))
                .expectNext(pedido)
                .verifyComplete();

        verify(pedidoRepositoryPort).save(pedido);
    }

    @Test
    void update_WhenPedidoExists_ShouldUpdateAndSave() {
        when(pedidoRepositoryPort.findById(1L)).thenReturn(Mono.just(existingPedido));
        when(pedidoRepositoryPort.save(any(Pedido.class))).thenReturn(Mono.just(pedido));

        StepVerifier.create(pedidoService.update(1L, pedido))
                .expectNextMatches(updated -> 
                    updated.getFecha().equals(pedido.getFecha()) &&
                    updated.getTotal().equals(pedido.getTotal()) &&
                    updated.isEstado() == pedido.isEstado()
                )
                .verifyComplete();

        verify(pedidoRepositoryPort).findById(1L);
        verify(pedidoRepositoryPort).save(any(Pedido.class));
    }

    @Test
    void update_WhenPedidoNotExists_ShouldReturnEmpty() {
        when(pedidoRepositoryPort.findById(1L)).thenReturn(Mono.empty());

        StepVerifier.create(pedidoService.update(1L, pedido))
                .verifyComplete();

        verify(pedidoRepositoryPort).findById(1L);
        verify(pedidoRepositoryPort, never()).save(any());
    }

    @Test
    void deleteById_ShouldDeletePedido() {
        when(pedidoRepositoryPort.deleteById(1L)).thenReturn(Mono.empty());

        StepVerifier.create(pedidoService.deleteById(1L))
                .verifyComplete();

        verify(pedidoRepositoryPort).deleteById(1L);
    }

    @Test
    void findAllWithPageable_ShouldReturnPagedPedidos() {
        Pageable pageable = PageRequest.of(0, 10);
        when(pedidoRepositoryPort.findAll(pageable)).thenReturn(Flux.just(pedido));

        StepVerifier.create(pedidoService.findAll(pageable))
                .expectNext(pedido)
                .verifyComplete();

        verify(pedidoRepositoryPort).findAll(pageable);
    }

    @Test
    void findByEstado_ShouldReturnPedidosByEstado() {
        when(pedidoRepositoryPort.findByEstado(true)).thenReturn(Flux.just(pedido));

        StepVerifier.create(pedidoService.findByEstado(true))
                .expectNext(pedido)
                .verifyComplete();

        verify(pedidoRepositoryPort).findByEstado(true);
    }
}
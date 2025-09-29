package mabmab.retoibk.reto.application.usecases;

import mabmab.retoibk.reto.domain.models.Pedido;
import mabmab.retoibk.reto.domain.services.PedidoService;
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
import java.time.LocalDate;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PedidoUseCaseTest {

    @Mock
    private PedidoService pedidoService;

    @InjectMocks
    private PedidoUseCase pedidoUseCase;

    private Pedido pedido;

    @BeforeEach
    void setUp() {
        pedido = new Pedido(1L, LocalDate.now(), new BigDecimal("1000"), true, null, 1L);
    }

    @Test
    void findAll_ShouldReturnAllPedidos() {
        when(pedidoService.findAll()).thenReturn(Flux.just(pedido));

        StepVerifier.create(pedidoUseCase.findAll())
                .expectNext(pedido)
                .verifyComplete();

        verify(pedidoService).findAll();
    }

    @Test
    void findById_ShouldReturnPedido() {
        when(pedidoService.findById(1L)).thenReturn(Mono.just(pedido));

        StepVerifier.create(pedidoUseCase.findById(1L))
                .expectNext(pedido)
                .verifyComplete();

        verify(pedidoService).findById(1L);
    }

    @Test
    void save_ShouldSavePedido() {
        when(pedidoService.save(pedido)).thenReturn(Mono.just(pedido));

        StepVerifier.create(pedidoUseCase.save(pedido))
                .expectNext(pedido)
                .verifyComplete();

        verify(pedidoService).save(pedido);
    }

    @Test
    void update_ShouldUpdatePedido() {
        when(pedidoService.update(1L, pedido)).thenReturn(Mono.just(pedido));

        StepVerifier.create(pedidoUseCase.update(1L, pedido))
                .expectNext(pedido)
                .verifyComplete();

        verify(pedidoService).update(1L, pedido);
    }

    @Test
    void deleteById_ShouldDeletePedido() {
        when(pedidoService.deleteById(1L)).thenReturn(Mono.empty());

        StepVerifier.create(pedidoUseCase.deleteById(1L))
                .verifyComplete();

        verify(pedidoService).deleteById(1L);
    }

    @Test
    void findAllWithPageable_ShouldReturnPagedPedidos() {
        Pageable pageable = PageRequest.of(0, 10);
        when(pedidoService.findAll(pageable)).thenReturn(Flux.just(pedido));

        StepVerifier.create(pedidoUseCase.findAll(pageable))
                .expectNext(pedido)
                .verifyComplete();

        verify(pedidoService).findAll(pageable);
    }

    @Test
    void findByEstado_ShouldReturnPedidosByEstado() {
        when(pedidoService.findByEstado(true)).thenReturn(Flux.just(pedido));

        StepVerifier.create(pedidoUseCase.findByEstado(true))
                .expectNext(pedido)
                .verifyComplete();

        verify(pedidoService).findByEstado(true);
    }
    

}
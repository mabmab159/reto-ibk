package mabmab.retoibk.reto.domain.services;

import mabmab.retoibk.reto.domain.models.Pedido;
import mabmab.retoibk.reto.domain.models.PedidoItem;
import mabmab.retoibk.reto.domain.models.Producto;
import mabmab.retoibk.reto.domain.ports.out.PedidoRepositoryPort;
import mabmab.retoibk.reto.domain.ports.out.ProductoRepositoryPort;
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
import java.util.List;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PedidoServiceImplTest {

    @Mock
    private PedidoRepositoryPort pedidoRepositoryPort;
    
    @Mock
    private ProductoRepositoryPort productoRepositoryPort;
    
    @Mock
    private DescuentoService descuentoService;

    @InjectMocks
    private PedidoServiceImpl pedidoService;

    private Pedido pedido;
    private Pedido existingPedido;
    private PedidoItem pedidoItem;
    private Producto producto;

    @BeforeEach
    void setUp() {
        pedidoItem = new PedidoItem(1L, 1L, 1L, 2, new BigDecimal("100"), new BigDecimal("200"));
        pedido = new Pedido(1L, LocalDate.now(), new BigDecimal("1000"), true, List.of(pedidoItem), 1L);
        existingPedido = new Pedido(1L, LocalDate.now().minusDays(1), new BigDecimal("500"), false, List.of(pedidoItem), 1L);
        producto = new Producto(1L, "Producto Test", new BigDecimal("100"), 10, 1L);
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
        when(productoRepositoryPort.findById(1L)).thenReturn(Mono.just(producto));
        when(descuentoService.calcularTotal(any(Pedido.class))).thenReturn(new BigDecimal("180"));
        when(pedidoRepositoryPort.save(any(Pedido.class))).thenReturn(Mono.just(pedido));

        StepVerifier.create(pedidoService.save(pedido))
                .expectNextMatches(saved -> saved.getTotal() != null)
                .verifyComplete();

        verify(pedidoRepositoryPort).save(any(Pedido.class));
        verify(descuentoService).calcularTotal(any(Pedido.class));
    }

    @Test
    void update_WhenPedidoExists_ShouldUpdateAndSave() {
        when(pedidoRepositoryPort.findById(1L)).thenReturn(Mono.just(existingPedido));
        when(productoRepositoryPort.findById(1L)).thenReturn(Mono.just(producto));
        when(pedidoRepositoryPort.save(any(Pedido.class))).thenReturn(Mono.just(pedido));

        StepVerifier.create(pedidoService.update(1L, pedido))
                .expectNextMatches(updated -> updated.getFecha().equals(pedido.getFecha()))
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
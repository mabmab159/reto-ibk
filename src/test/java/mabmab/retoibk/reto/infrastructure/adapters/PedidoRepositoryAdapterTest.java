package mabmab.retoibk.reto.infrastructure.adapters;

import mabmab.retoibk.reto.domain.models.Pedido;
import mabmab.retoibk.reto.infrastructure.dataproviders.entities.PedidoEntity;
import mabmab.retoibk.reto.infrastructure.dataproviders.entities.PedidoItemEntity;
import mabmab.retoibk.reto.infrastructure.dataproviders.mappers.PedidoMapper;
import mabmab.retoibk.reto.infrastructure.dataproviders.repositories.PedidoItemRepository;
import mabmab.retoibk.reto.infrastructure.dataproviders.repositories.PedidoRepository;
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
class PedidoRepositoryAdapterTest {

    @Mock
    private PedidoRepository pedidoRepository;
    
    @Mock
    private PedidoItemRepository pedidoItemRepository;

    @Mock
    private PedidoMapper pedidoMapper;

    @InjectMocks
    private PedidoRepositoryAdapter pedidoRepositoryAdapter;

    private Pedido pedido;
    private PedidoEntity pedidoEntity;

    @BeforeEach
    void setUp() {
        pedido = new Pedido(1L, LocalDate.now(), new BigDecimal("1000"), true, null, 1L);
        pedidoEntity = new PedidoEntity();
        pedidoEntity.setId(1L);
        pedidoEntity.setFecha(LocalDate.now());
        pedidoEntity.setTotal(new BigDecimal("1000"));
        pedidoEntity.setEstado(true);
    }

    @Test
    void findAll_ShouldReturnAllPedidos() {
        when(pedidoRepository.findAll()).thenReturn(Flux.just(pedidoEntity));
        when(pedidoItemRepository.findByPedidoId(1L)).thenReturn(Flux.empty());
        when(pedidoMapper.toDomain(pedidoEntity)).thenReturn(pedido);

        StepVerifier.create(pedidoRepositoryAdapter.findAll())
                .expectNext(pedido)
                .verifyComplete();

        verify(pedidoRepository).findAll();
        verify(pedidoItemRepository).findByPedidoId(1L);
    }

    @Test
    void findById_ShouldReturnPedido() {
        when(pedidoRepository.findById(1L)).thenReturn(Mono.just(pedidoEntity));
        when(pedidoItemRepository.findByPedidoId(1L)).thenReturn(Flux.empty());
        when(pedidoMapper.toDomain(pedidoEntity)).thenReturn(pedido);

        StepVerifier.create(pedidoRepositoryAdapter.findById(1L))
                .expectNext(pedido)
                .verifyComplete();

        verify(pedidoRepository).findById(1L);
        verify(pedidoItemRepository).findByPedidoId(1L);
    }

    @Test
    void save_ShouldSavePedido() {
        when(pedidoMapper.toEntity(pedido)).thenReturn(pedidoEntity);
        when(pedidoRepository.save(pedidoEntity)).thenReturn(Mono.just(pedidoEntity));
        when(pedidoItemRepository.findByPedidoId(1L)).thenReturn(Flux.empty());
        when(pedidoMapper.toDomain(pedidoEntity)).thenReturn(pedido);

        StepVerifier.create(pedidoRepositoryAdapter.save(pedido))
                .expectNext(pedido)
                .verifyComplete();

        verify(pedidoMapper).toEntity(pedido);
        verify(pedidoRepository).save(pedidoEntity);
    }

    @Test
    void deleteById_ShouldDeletePedido() {
        when(pedidoRepository.deleteById(1L)).thenReturn(Mono.empty());

        StepVerifier.create(pedidoRepositoryAdapter.deleteById(1L))
                .verifyComplete();

        verify(pedidoRepository).deleteById(1L);
    }

    @Test
    void findAllWithPageable_ShouldReturnPagedPedidos() {
        Pageable pageable = PageRequest.of(0, 10);
        when(pedidoRepository.findAllBy(pageable)).thenReturn(Flux.just(pedidoEntity));
        when(pedidoItemRepository.findByPedidoId(1L)).thenReturn(Flux.empty());
        when(pedidoMapper.toDomain(pedidoEntity)).thenReturn(pedido);

        StepVerifier.create(pedidoRepositoryAdapter.findAll(pageable))
                .expectNext(pedido)
                .verifyComplete();

        verify(pedidoRepository).findAllBy(pageable);
        verify(pedidoItemRepository).findByPedidoId(1L);
    }

    @Test
    void findByEstado_ShouldReturnPedidosByEstado() {
        when(pedidoRepository.findByEstado(true)).thenReturn(Flux.just(pedidoEntity));
        when(pedidoItemRepository.findByPedidoId(1L)).thenReturn(Flux.empty());
        when(pedidoMapper.toDomain(pedidoEntity)).thenReturn(pedido);

        StepVerifier.create(pedidoRepositoryAdapter.findByEstado(true))
                .expectNext(pedido)
                .verifyComplete();

        verify(pedidoRepository).findByEstado(true);
        verify(pedidoItemRepository).findByPedidoId(1L);
    }
}
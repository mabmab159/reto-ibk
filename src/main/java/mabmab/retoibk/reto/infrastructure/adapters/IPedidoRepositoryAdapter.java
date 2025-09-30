package mabmab.retoibk.reto.infrastructure.adapters;

import lombok.RequiredArgsConstructor;
import mabmab.retoibk.reto.domain.models.Pedido;
import mabmab.retoibk.reto.domain.ports.out.IPedidoRepositoryPort;
import mabmab.retoibk.reto.infrastructure.dataproviders.entities.PedidoEntity;
import mabmab.retoibk.reto.infrastructure.dataproviders.mappers.PedidoMapper;
import mabmab.retoibk.reto.infrastructure.dataproviders.repositories.PedidoItemRepository;
import mabmab.retoibk.reto.infrastructure.dataproviders.repositories.PedidoRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class IPedidoRepositoryAdapter implements IPedidoRepositoryPort {
    private final PedidoRepository pedidoRepository;
    private final PedidoItemRepository pedidoItemRepository;
    private final PedidoMapper pedidoMapper;

    @Override
    public Flux<Pedido> findAll() {
        return pedidoRepository.findAll()
                .flatMap(this::loadPedidoWithItems);
    }

    @Override
    public Mono<Pedido> findById(Long id) {
        return pedidoRepository.findById(id)
                .flatMap(this::loadPedidoWithItems);
    }

    @Override
    @Transactional
    public Mono<Pedido> save(Pedido pedido) {
        return pedidoRepository.save(pedidoMapper.toEntity(pedido))
                .flatMap(savedEntity -> {
                    if (pedido.getItems() != null && !pedido.getItems().isEmpty()) {
                        return pedidoItemRepository.deleteByPedidoId(savedEntity.getId())
                                .thenMany(Flux.fromIterable(pedido.getItems())
                                        .map(item -> {
                                            item.setPedidoId(savedEntity.getId());
                                            return pedidoMapper.toItemEntity(item);
                                        })
                                        .flatMap(pedidoItemRepository::save))
                                .then(Mono.just(savedEntity));
                    }
                    return Mono.just(savedEntity);
                })
                .flatMap(this::loadPedidoWithItems);
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return pedidoRepository.deleteById(id);
    }

    @Override
    public Flux<Pedido> findAll(Pageable pageable) {
        return pedidoRepository.findAllBy(pageable)
                .flatMap(this::loadPedidoWithItems);
    }

    private Mono<Pedido> loadPedidoWithItems(PedidoEntity entity) {
        return pedidoItemRepository.findByPedidoId(entity.getId())
                .map(pedidoMapper::toItemDomain)
                .collectList()
                .map(items -> {
                    Pedido pedido = pedidoMapper.toDomain(entity);
                    pedido.setItems(items);
                    return pedido;
                });
    }
}
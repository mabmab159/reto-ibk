package mabmab.retoibk.reto.infrastructure.dataproviders.repositories;

import mabmab.retoibk.reto.infrastructure.dataproviders.entities.PedidoItemEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface PedidoItemRepository extends R2dbcRepository<PedidoItemEntity, Long> {
    Flux<PedidoItemEntity> findByPedidoId(Long pedidoId);
    Mono<Void> deleteByPedidoId(Long pedidoId);
}
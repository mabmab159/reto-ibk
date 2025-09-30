package mabmab.retoibk.reto.infrastructure.dataproviders.repositories;

import mabmab.retoibk.reto.infrastructure.dataproviders.entities.PedidoEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface PedidoRepository extends R2dbcRepository<PedidoEntity, Long> {
    Flux<PedidoEntity> findAllBy(Pageable pageable);
}
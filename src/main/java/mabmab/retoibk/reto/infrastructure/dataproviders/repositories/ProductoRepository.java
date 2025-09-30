package mabmab.retoibk.reto.infrastructure.dataproviders.repositories;

import mabmab.retoibk.reto.infrastructure.dataproviders.entities.ProductoEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ProductoRepository extends R2dbcRepository<ProductoEntity, Long> {
    Flux<ProductoEntity> findAllBy(Pageable pageable);
}

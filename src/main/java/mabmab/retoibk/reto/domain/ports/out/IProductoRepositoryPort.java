package mabmab.retoibk.reto.domain.ports.out;

import mabmab.retoibk.reto.domain.models.Producto;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IProductoRepositoryPort {
    Flux<Producto> findAll();
    Flux<Producto> findAll(Pageable pageable);
    Mono<Producto> findById(Long id);
    Mono<Producto> save(Producto producto);
    Mono<Void> deleteById(Long id);
}
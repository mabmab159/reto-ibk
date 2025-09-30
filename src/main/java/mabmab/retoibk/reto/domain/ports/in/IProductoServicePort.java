package mabmab.retoibk.reto.domain.ports.in;

import mabmab.retoibk.reto.domain.models.Producto;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IProductoServicePort {
    Flux<Producto> findAll();
    Mono<Producto> findById(Long id);
    Mono<Producto> save(Producto producto);
    Mono<Producto> update(Long id, Producto producto);
    Mono<Void> deleteById(Long id);
    Flux<Producto> findAll(Pageable pageable);
}
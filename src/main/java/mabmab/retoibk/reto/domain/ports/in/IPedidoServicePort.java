package mabmab.retoibk.reto.domain.ports.in;

import mabmab.retoibk.reto.domain.models.Pedido;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IPedidoServicePort {
    Flux<Pedido> findAll();
    Mono<Pedido> findById(Long id);
    Mono<Pedido> save(Pedido pedido);
    Mono<Pedido> update(Long id, Pedido pedido);
    Mono<Void> deleteById(Long id);
    Flux<Pedido> findAll(Pageable pageable);
}
package mabmab.retoibk.reto.domain.ports.in;

import mabmab.retoibk.reto.domain.models.Pedido;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PedidoUseCase {
    Flux<Pedido> findAll();

    Mono<Pedido> findById(Long id);

    Mono<Pedido> save(Pedido pedido);

    Mono<Pedido> update(Long id, Pedido pedido);

    Mono<Void> deleteById(Long id);

    Flux<Pedido> findByEstado(boolean estado);
}
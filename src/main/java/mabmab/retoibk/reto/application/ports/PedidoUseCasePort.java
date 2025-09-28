package mabmab.retoibk.reto.application.ports;

import mabmab.retoibk.reto.domain.models.Pedido;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PedidoUseCasePort {
    Flux<Pedido> findAll();
    Flux<Pedido> findAll(Pageable pageable);
    Mono<Pedido> findById(Long id);
    Mono<Pedido> save(Pedido pedido);
    Mono<Pedido> update(Long id, Pedido pedido);
    Mono<Void> deleteById(Long id);
    Flux<Pedido> findByEstado(boolean estado);
    Mono<Pedido> confirmarPedido(Long id);
}
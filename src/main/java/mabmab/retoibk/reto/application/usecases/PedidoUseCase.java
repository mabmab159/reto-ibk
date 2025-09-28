package mabmab.retoibk.reto.application.usecases;

import lombok.RequiredArgsConstructor;
import mabmab.retoibk.reto.application.ports.PedidoUseCasePort;
import mabmab.retoibk.reto.domain.models.Pedido;
import mabmab.retoibk.reto.domain.services.PedidoService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class PedidoUseCase implements PedidoUseCasePort {
    
    private final PedidoService pedidoService;
    
    @Override
    public Flux<Pedido> findAll() {
        return pedidoService.findAll();
    }
    
    @Override
    public Mono<Pedido> findById(Long id) {
        return pedidoService.findById(id);
    }
    
    @Override
    public Mono<Pedido> save(Pedido pedido) {
        return pedidoService.save(pedido);
    }
    
    @Override
    public Mono<Pedido> update(Long id, Pedido pedido) {
        return pedidoService.update(id, pedido);
    }
    
    @Override
    public Mono<Void> deleteById(Long id) {
        return pedidoService.deleteById(id);
    }
    
    @Override
    public Flux<Pedido> findAll(Pageable pageable) {
        return pedidoService.findAll(pageable);
    }

    @Override
    public Flux<Pedido> findByEstado(boolean estado) {
        return pedidoService.findByEstado(estado);
    }
}
package mabmab.retoibk.reto.domain.services;

import lombok.RequiredArgsConstructor;
import mabmab.retoibk.reto.domain.models.Pedido;
import mabmab.retoibk.reto.domain.ports.out.PedidoRepositoryPort;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {
    
    private final PedidoRepositoryPort pedidoRepositoryPort;
    
    @Override
    public Flux<Pedido> findAll() {
        return pedidoRepositoryPort.findAll();
    }
    
    @Override
    public Mono<Pedido> findById(Long id) {
        return pedidoRepositoryPort.findById(id);
    }
    
    @Override
    public Mono<Pedido> save(Pedido pedido) {
        return pedidoRepositoryPort.save(pedido);
    }
    
    @Override
    public Mono<Pedido> update(Long id, Pedido pedido) {
        return pedidoRepositoryPort.findById(id)
                .flatMap(existing -> {
                    existing.setFecha(pedido.getFecha());
                    existing.setTotal(pedido.getTotal());
                    existing.setEstado(pedido.isEstado());
                    return pedidoRepositoryPort.save(existing);
                });
    }
    
    @Override
    public Mono<Void> deleteById(Long id) {
        return pedidoRepositoryPort.deleteById(id);
    }
    
    @Override
    public Flux<Pedido> findAll(Pageable pageable) {
        return pedidoRepositoryPort.findAll(pageable);
    }

    @Override
    public Flux<Pedido> findByEstado(boolean estado) {
        return pedidoRepositoryPort.findByEstado(estado);
    }
}
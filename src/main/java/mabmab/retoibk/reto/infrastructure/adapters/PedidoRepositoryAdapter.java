package mabmab.retoibk.reto.infrastructure.adapters;

import lombok.RequiredArgsConstructor;
import mabmab.retoibk.reto.domain.models.Pedido;
import mabmab.retoibk.reto.domain.ports.out.PedidoRepositoryPort;
import mabmab.retoibk.reto.infrastructure.dataproviders.mappers.PedidoMapper;
import mabmab.retoibk.reto.infrastructure.dataproviders.repositories.PedidoRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class PedidoRepositoryAdapter implements PedidoRepositoryPort {
    
    private final PedidoRepository pedidoRepository;
    private final PedidoMapper pedidoMapper;
    
    @Override
    public Flux<Pedido> findAll() {
        return pedidoRepository.findAll().map(pedidoMapper::toDomain);
    }
    
    @Override
    public Mono<Pedido> findById(Long id) {
        return pedidoRepository.findById(id).map(pedidoMapper::toDomain);
    }
    
    @Override
    public Mono<Pedido> save(Pedido pedido) {
        return pedidoRepository.save(pedidoMapper.toEntity(pedido)).map(pedidoMapper::toDomain);
    }
    
    @Override
    public Mono<Void> deleteById(Long id) {
        return pedidoRepository.deleteById(id);
    }
    
    @Override
    public Flux<Pedido> findAll(Pageable pageable) {
        return pedidoRepository.findAllBy(pageable).map(pedidoMapper::toDomain);
    }

    @Override
    public Flux<Pedido> findByEstado(boolean estado) {
        return pedidoRepository.findByEstado(estado).map(pedidoMapper::toDomain);
    }
}
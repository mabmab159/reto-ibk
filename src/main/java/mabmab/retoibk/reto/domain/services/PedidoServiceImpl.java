package mabmab.retoibk.reto.domain.services;

import lombok.RequiredArgsConstructor;
import mabmab.retoibk.reto.domain.models.Pedido;
import mabmab.retoibk.reto.domain.models.Producto;
import mabmab.retoibk.reto.domain.ports.out.PedidoRepositoryPort;
import mabmab.retoibk.reto.domain.ports.out.ProductoRepositoryPort;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {
    
    private final PedidoRepositoryPort pedidoRepositoryPort;
    private final ProductoRepositoryPort productoRepositoryPort;
    private final DescuentoService descuentoService;
    
    @Override
    public Flux<Pedido> findAll() {
        return pedidoRepositoryPort.findAll();
    }
    
    @Override
    public Mono<Pedido> findById(Long id) {
        if (id == null) {
            return Mono.error(new IllegalArgumentException("ID cannot be null"));
        }
        return pedidoRepositoryPort.findById(id);
    }
    
    @Override
    public Mono<Pedido> save(Pedido pedido) {
        return pedidoRepositoryPort.save(pedido);
    }
    
    @Override
    public Mono<Pedido> update(Long id, Pedido pedido) {
        if (id == null) {
            return Mono.error(new IllegalArgumentException("ID cannot be null"));
        }
        if (pedido == null) {
            return Mono.error(new IllegalArgumentException("Pedido cannot be null"));
        }
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
    
    @Override
    @Transactional
    public Mono<Pedido> confirmarPedido(Long id) {
        return pedidoRepositoryPort.findById(id)
                .flatMap(pedido -> {
                    if (pedido.isEstado()) {
                        return Mono.error(new RuntimeException("Pedido ya confirmado"));
                    }
                    
                    // Validar y actualizar stock
                    return validarYActualizarStock(pedido)
                            .then(Mono.fromCallable(() -> {
                                // Calcular total con descuentos
                                pedido.setTotal(descuentoService.calcularTotal(pedido));
                                pedido.setEstado(true);
                                return pedido;
                            }))
                            .flatMap(pedidoRepositoryPort::save);
                });
    }
    
    private Mono<Void> validarYActualizarStock(Pedido pedido) {
        if (pedido.getItems() == null) {
            return Mono.empty();
        }
        
        return Flux.fromIterable(pedido.getItems())
                .flatMap(item -> 
                    productoRepositoryPort.findById(item.getProductoId())
                            .flatMap(producto -> {
                                if (producto.getStock() < item.getCantidad()) {
                                    return Mono.error(new RuntimeException(
                                        "Stock insuficiente para producto: " + producto.getNombre()));
                                }
                                producto.setStock(producto.getStock() - item.getCantidad());
                                return productoRepositoryPort.save(producto);
                            })
                )
                .then();
    }
}
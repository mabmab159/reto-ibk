package mabmab.retoibk.reto.domain.services;

import lombok.RequiredArgsConstructor;
import mabmab.retoibk.reto.domain.exceptions.ConcurrenciaException;
import mabmab.retoibk.reto.domain.models.Pedido;
import mabmab.retoibk.reto.domain.models.PedidoItem;
import mabmab.retoibk.reto.domain.ports.out.PedidoRepositoryPort;
import mabmab.retoibk.reto.domain.ports.out.ProductoRepositoryPort;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepositoryPort pedidoRepositoryPort;
    private final ProductoRepositoryPort productoRepositoryPort;
    private final DescuentoService descuentoService;
    private final StockService stockService;
    private final TransactionalOperator transactionalOperator;

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
        if (pedido.getFecha() == null) {
            pedido.setFecha(LocalDate.now());
        }
        
        return stockService.validarYReservarStock(pedido.getItems())
                .then(calcularTotalConItems(pedido))
                .flatMap(pedidoRepositoryPort::save)
                .as(transactionalOperator::transactional)
                .retryWhen(Retry.backoff(3, Duration.ofMillis(100))
                    .filter(OptimisticLockingFailureException.class::isInstance))
                .onErrorMap(OptimisticLockingFailureException.class, 
                    ex -> new ConcurrenciaException("Error de concurrencia al procesar pedido"));
    }

    @Override
    @Transactional
    public Mono<Pedido> update(Long id, Pedido pedido) {
        if (id == null) {
            return Mono.error(new IllegalArgumentException("ID cannot be null"));
        }
        return pedidoRepositoryPort.findById(id)
                .flatMap(existing -> {
                    if (existing.isEstado()) {
                        return Mono.error(new RuntimeException("No se puede modificar un pedido confirmado"));
                    }
                    existing.setFecha(pedido.getFecha());
                    existing.setEstado(pedido.isEstado());
                    existing.setItems(pedido.getItems());
                    return calcularTotalConItems(existing);
                })
                .flatMap(pedidoRepositoryPort::save);
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

    private Mono<Pedido> calcularTotalConItems(Pedido pedido) {
        if (pedido.getItems() == null || pedido.getItems().isEmpty()) {
            pedido.setTotal(BigDecimal.ZERO);
            return Mono.just(pedido);
        }

        return Flux.fromIterable(pedido.getItems())
                .flatMap(item ->
                        productoRepositoryPort.findById(item.getProductoId())
                                .map(producto -> {
                                    item.setPrecioUnitario(producto.getPrecio());
                                    item.setSubtotal(producto.getPrecio().multiply(BigDecimal.valueOf(item.getCantidad())));
                                    return item;
                                })
                )
                .collectList()
                .map(items -> {
                    pedido.setItems(items);
                    // Aplicar descuentos automáticamente
                    BigDecimal totalConDescuento = descuentoService.calcularTotal(pedido);
                    pedido.setTotal(totalConDescuento);
                    return pedido;
                });
    }


}
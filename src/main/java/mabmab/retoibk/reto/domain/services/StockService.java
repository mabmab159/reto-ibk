package mabmab.retoibk.reto.domain.services;

import lombok.RequiredArgsConstructor;
import mabmab.retoibk.reto.domain.exceptions.StockInsuficienteException;
import mabmab.retoibk.reto.domain.models.PedidoItem;
import mabmab.retoibk.reto.domain.models.Producto;
import mabmab.retoibk.reto.domain.ports.out.ProductoRepositoryPort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StockService {
    
    private final ProductoRepositoryPort productoRepositoryPort;
    
    public Mono<Void> validarYReservarStock(List<PedidoItem> items) {
        return Flux.fromIterable(items)
                .flatMap(item -> 
                    productoRepositoryPort.findById(item.getProductoId())
                        .flatMap(producto -> {
                            if (producto.getStock() < item.getCantidad()) {
                                return Mono.error(new StockInsuficienteException(
                                    "Stock insuficiente para producto: " + producto.getNombre()));
                            }
                            producto.setStock(producto.getStock() - item.getCantidad());
                            return productoRepositoryPort.save(producto);
                        })
                )
                .then();
    }
}
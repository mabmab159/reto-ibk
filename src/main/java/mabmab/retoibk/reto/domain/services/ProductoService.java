package mabmab.retoibk.reto.domain.services;

import lombok.RequiredArgsConstructor;
import mabmab.retoibk.reto.domain.models.Producto;
import mabmab.retoibk.reto.domain.ports.in.IProductoServicePort;
import mabmab.retoibk.reto.domain.ports.out.IProductoRepositoryPort;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProductoService implements IProductoServicePort {

    private final IProductoRepositoryPort IProductoRepositoryPort;

    @Override
    public Flux<Producto> findAll() {
        return IProductoRepositoryPort.findAll();
    }

    @Override
    public Mono<Producto> findById(Long id) {
        if (id == null) {
            return Mono.error(new IllegalArgumentException("ID cannot be null"));
        }
        return IProductoRepositoryPort.findById(id);
    }

    @Override
    @Transactional
    public Mono<Producto> save(Producto producto) {
        return IProductoRepositoryPort.save(producto);
    }

    @Override
    @Transactional
    public Mono<Producto> update(Long id, Producto producto) {
        if (id == null) {
            return Mono.error(new IllegalArgumentException("ID cannot be null"));
        }
        if (producto == null) {
            return Mono.error(new IllegalArgumentException("Producto cannot be null"));
        }
        return IProductoRepositoryPort.findById(id)
                .flatMap(existing -> {
                    existing.setNombre(producto.getNombre());
                    existing.setPrecio(producto.getPrecio());
                    existing.setStock(producto.getStock());
                    return IProductoRepositoryPort.save(existing);
                });
    }

    @Override
    @Transactional
    public Mono<Void> deleteById(Long id) {
        return IProductoRepositoryPort.deleteById(id);
    }

    @Override
    public Flux<Producto> findAll(Pageable pageable) {
        return IProductoRepositoryPort.findAll(pageable);
    }
}
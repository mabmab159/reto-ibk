package mabmab.retoibk.reto.domain.services;

import lombok.RequiredArgsConstructor;
import mabmab.retoibk.reto.domain.models.Producto;
import mabmab.retoibk.reto.domain.ports.out.ProductoRepositoryPort;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {
    
    private final ProductoRepositoryPort productoRepositoryPort;
    
    @Override
    public Flux<Producto> findAll() {
        return productoRepositoryPort.findAll();
    }
    
    @Override
    public Mono<Producto> findById(Long id) {
        return productoRepositoryPort.findById(id);
    }
    
    @Override
    public Mono<Producto> save(Producto producto) {
        return productoRepositoryPort.save(producto);
    }
    
    @Override
    public Mono<Producto> update(Long id, Producto producto) {
        return productoRepositoryPort.findById(id)
                .flatMap(existing -> {
                    existing.setNombre(producto.getNombre());
                    existing.setPrecio(producto.getPrecio());
                    existing.setCantidad(producto.getCantidad());
                    return productoRepositoryPort.save(existing);
                });
    }
    
    @Override
    public Mono<Void> deleteById(Long id) {
        return productoRepositoryPort.deleteById(id);
    }
    
    @Override
    public Flux<Producto> findAll(Pageable pageable) {
        return productoRepositoryPort.findAll(pageable);
    }

    @Override
    public Flux<Producto> findByNombre(String nombre) {
        return productoRepositoryPort.findByNombreContainingIgnoreCase(nombre);
    }
}
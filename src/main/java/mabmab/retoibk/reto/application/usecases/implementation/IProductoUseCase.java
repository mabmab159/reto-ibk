package mabmab.retoibk.reto.application.usecases.implementation;

import lombok.RequiredArgsConstructor;
import mabmab.retoibk.reto.domain.models.Producto;
import mabmab.retoibk.reto.domain.ports.in.IProductoServicePort;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class IProductoUseCase implements mabmab.retoibk.reto.application.usecases.interfaces.IProductoUseCase {

    private final IProductoServicePort productoService;

    @Override
    public Flux<Producto> findAll() {
        return productoService.findAll();
    }

    @Override
    public Mono<Producto> findById(Long id) {
        return productoService.findById(id);
    }

    @Override
    public Mono<Producto> save(Producto producto) {
        return productoService.save(producto);
    }

    @Override
    public Mono<Producto> update(Long id, Producto producto) {
        return productoService.update(id, producto);
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return productoService.deleteById(id);
    }

    @Override
    public Flux<Producto> findAll(Pageable pageable) {
        return productoService.findAll(pageable);
    }


}
package mabmab.retoibk.reto.infrastructure.adapters;

import lombok.RequiredArgsConstructor;
import mabmab.retoibk.reto.domain.models.Producto;
import mabmab.retoibk.reto.domain.ports.out.ProductoRepositoryPort;
import mabmab.retoibk.reto.infrastructure.dataproviders.mappers.ProductoMapper;
import mabmab.retoibk.reto.infrastructure.dataproviders.repositories.ProductoRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ProductoRepositoryAdapter implements ProductoRepositoryPort {

    private final ProductoRepository productoRepository;
    private final ProductoMapper productoMapper;

    @Override
    public Flux<Producto> findAll() {
        return productoRepository.findAll().map(productoMapper::toDomain);
    }

    @Override
    public Mono<Producto> findById(Long id) {
        return productoRepository.findById(id).map(productoMapper::toDomain);
    }

    @Override
    public Mono<Producto> save(Producto producto) {
        return productoRepository.save(productoMapper.toEntity(producto)).map(productoMapper::toDomain);
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return productoRepository.deleteById(id);
    }

    @Override
    public Flux<Producto> findAll(Pageable pageable) {
        return productoRepository.findAllBy(pageable).map(productoMapper::toDomain);
    }

    @Override
    public Flux<Producto> findByNombreContainingIgnoreCase(String nombre) {
        return productoRepository.findByNombreContainingIgnoreCase(nombre).map(productoMapper::toDomain);
    }
}
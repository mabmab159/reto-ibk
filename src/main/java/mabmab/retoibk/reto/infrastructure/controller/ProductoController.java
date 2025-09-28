package mabmab.retoibk.reto.infrastructure.controller;

import lombok.RequiredArgsConstructor;
import mabmab.retoibk.reto.application.ports.ProductoUseCasePort;
import mabmab.retoibk.reto.domain.models.Producto;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {
    
    private final ProductoUseCasePort productoUseCase;

    @GetMapping
    public Flux<Producto> getAllProductos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return productoUseCase.findAll(PageRequest.of(page, size));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Producto>> getProductoById(@PathVariable Long id) {
        return productoUseCase.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<Producto>> createProducto(@RequestBody Producto producto) {
        return productoUseCase.save(producto)
                .map(p -> ResponseEntity.status(HttpStatus.CREATED).body(p));
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Producto>> updateProducto(@PathVariable Long id, @RequestBody Producto producto) {
        return productoUseCase.update(id, producto)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteProducto(@PathVariable Long id) {
        return productoUseCase.deleteById(id)
                .then(Mono.just(ResponseEntity.noContent().<Void>build()));
    }

    @GetMapping("/buscar")
    public Flux<Producto> buscarPorNombre(@RequestParam String nombre) {
        return productoUseCase.findByNombre(nombre);
    }
}

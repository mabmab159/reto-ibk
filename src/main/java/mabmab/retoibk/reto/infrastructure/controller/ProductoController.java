package mabmab.retoibk.reto.infrastructure.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mabmab.retoibk.configuration.documentation.ProductoControllerApi;
import mabmab.retoibk.reto.application.usecases.interfaces.IProductoUseCase;
import mabmab.retoibk.reto.infrastructure.controller.dto.request.ProductoRequest;
import mabmab.retoibk.reto.infrastructure.controller.dto.response.ProductoResponse;
import mabmab.retoibk.reto.infrastructure.controller.mappers.ProductoControllerMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController implements ProductoControllerApi {
    private final IProductoUseCase productoUseCase;
    private final ProductoControllerMapper mapper;

    @GetMapping
    public Flux<ProductoResponse> getAllProductos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return productoUseCase.findAll(PageRequest.of(page, size))
                .map(mapper::toResponse);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<ProductoResponse>> getProductoById(@PathVariable Long id) {
        return productoUseCase.findById(id)
                .map(mapper::toResponse)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<ProductoResponse>> createProducto(@Valid @RequestBody ProductoRequest request) {
        return productoUseCase.save(mapper.toDomain(request))
                .map(mapper::toResponse)
                .map(p -> ResponseEntity.status(HttpStatus.CREATED).body(p));
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<ProductoResponse>> updateProducto(@PathVariable Long id, @Valid @RequestBody ProductoRequest request) {
        return productoUseCase.update(id, mapper.toDomain(id, request))
                .map(mapper::toResponse)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteProducto(@PathVariable Long id) {
        return productoUseCase.deleteById(id)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }

}
package mabmab.retoibk.reto.infrastructure.controller;

import lombok.RequiredArgsConstructor;
import mabmab.retoibk.reto.application.ports.ProductoUseCasePort;
import mabmab.retoibk.reto.infrastructure.controller.dto.ProductoRequest;
import mabmab.retoibk.reto.infrastructure.controller.dto.ProductoResponse;
import mabmab.retoibk.reto.infrastructure.controller.mappers.ProductoControllerMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
@Tag(name = "Productos", description = "API para gestión de productos")
public class ProductoController {
    
    private final ProductoUseCasePort productoUseCase;
    private final ProductoControllerMapper mapper;

    @GetMapping
    @Operation(summary = "Obtener todos los productos", description = "Retorna una lista paginada de productos")
    public Mono<ResponseEntity<CollectionModel<ProductoResponse>>> getAllProductos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return productoUseCase.findAll(PageRequest.of(page, size))
                .map(mapper::toResponse)
                .map(this::addSelfLink)
                .collectList()
                .map(productos -> {
                    CollectionModel<ProductoResponse> collection = CollectionModel.of(productos);
                    collection.add(linkTo(methodOn(ProductoController.class).getAllProductos(page, size)).withSelfRel());
                    collection.add(linkTo(methodOn(ProductoController.class).createProducto(null)).withRel("create"));
                    return ResponseEntity.ok(collection);
                });
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<ProductoResponse>> getProductoById(@PathVariable Long id) {
        return productoUseCase.findById(id)
                .map(mapper::toResponse)
                .map(this::addAllLinks)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear producto", description = "Crea un nuevo producto")
    public Mono<ResponseEntity<ProductoResponse>> createProducto(@Valid @RequestBody ProductoRequest request) {
        return productoUseCase.save(mapper.toDomain(request))
                .map(mapper::toResponse)
                .map(this::addAllLinks)
                .map(p -> ResponseEntity.status(HttpStatus.CREATED).body(p));
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<ProductoResponse>> updateProducto(@PathVariable Long id, @Valid @RequestBody ProductoRequest request) {
        return productoUseCase.update(id, mapper.toDomain(id, request))
                .map(mapper::toResponse)
                .map(this::addAllLinks)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteProducto(@PathVariable Long id) {
        return productoUseCase.deleteById(id)
                .then(Mono.just(ResponseEntity.noContent().<Void>build()));
    }

    @GetMapping("/buscar")
    public Mono<ResponseEntity<CollectionModel<ProductoResponse>>> buscarPorNombre(@RequestParam String nombre) {
        return productoUseCase.findByNombre(nombre)
                .map(mapper::toResponse)
                .map(this::addSelfLink)
                .collectList()
                .map(productos -> {
                    CollectionModel<ProductoResponse> collection = CollectionModel.of(productos);
                    collection.add(linkTo(methodOn(ProductoController.class).buscarPorNombre(nombre)).withSelfRel());
                    collection.add(linkTo(methodOn(ProductoController.class).getAllProductos(0, 10)).withRel("all"));
                    return ResponseEntity.ok(collection);
                });
    }

    private ProductoResponse addSelfLink(ProductoResponse producto) {
        return producto.add(linkTo(methodOn(ProductoController.class).getProductoById(producto.getId())).withSelfRel());
    }

    private ProductoResponse addAllLinks(ProductoResponse producto) {
        return producto
                .add(linkTo(methodOn(ProductoController.class).getProductoById(producto.getId())).withSelfRel())
                .add(linkTo(methodOn(ProductoController.class).updateProducto(producto.getId(), null)).withRel("update"))
                .add(linkTo(methodOn(ProductoController.class).deleteProducto(producto.getId())).withRel("delete"))
                .add(linkTo(methodOn(ProductoController.class).getAllProductos(0, 10)).withRel("all"));
    }
}

package mabmab.retoibk.reto.infrastructure.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mabmab.retoibk.configuration.documentation.PedidoControllerApi;
import mabmab.retoibk.reto.application.ports.PedidoUseCasePort;
import mabmab.retoibk.reto.infrastructure.controller.dto.PedidoRequest;
import mabmab.retoibk.reto.infrastructure.controller.dto.PedidoResponse;
import mabmab.retoibk.reto.infrastructure.controller.mappers.PedidoControllerMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
public class PedidoController implements PedidoControllerApi {
    private final PedidoUseCasePort pedidoUseCase;
    private final PedidoControllerMapper mapper;

    @GetMapping
    public Mono<ResponseEntity<CollectionModel<PedidoResponse>>> getAllPedidos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return pedidoUseCase.findAll(PageRequest.of(page, size))
                .map(mapper::toResponse)
                .map(this::addSelfLink)
                .collectList()
                .map(pedidos -> {
                    CollectionModel<PedidoResponse> collection = CollectionModel.of(pedidos);
                    collection.add(linkTo(methodOn(PedidoController.class).getAllPedidos(page, size)).withSelfRel());
                    collection.add(linkTo(methodOn(PedidoController.class).createPedido(null)).withRel("create"));
                    return ResponseEntity.ok(collection);
                });
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<PedidoResponse>> getPedidoById(@PathVariable Long id) {
        return pedidoUseCase.findById(id)
                .map(mapper::toResponse)
                .map(this::addAllLinks)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<PedidoResponse>> createPedido(@Valid @RequestBody PedidoRequest request) {
        return pedidoUseCase.save(mapper.toDomain(request))
                .map(mapper::toResponse)
                .map(this::addAllLinks)
                .map(p -> ResponseEntity.status(HttpStatus.CREATED).body(p));
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<PedidoResponse>> updatePedido(@PathVariable Long id, @Valid @RequestBody PedidoRequest request) {
        return pedidoUseCase.update(id, mapper.toDomain(id, request))
                .map(mapper::toResponse)
                .map(this::addAllLinks)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deletePedido(@PathVariable Long id) {
        return pedidoUseCase.deleteById(id)
                .then(Mono.just(ResponseEntity.noContent().<Void>build()));
    }

    private PedidoResponse addSelfLink(PedidoResponse pedido) {
        return pedido.add(linkTo(methodOn(PedidoController.class).getPedidoById(pedido.getId())).withSelfRel());
    }

    private PedidoResponse addAllLinks(PedidoResponse pedido) {
        return pedido
                .add(linkTo(methodOn(PedidoController.class).getPedidoById(pedido.getId())).withSelfRel())
                .add(linkTo(methodOn(PedidoController.class).updatePedido(pedido.getId(), null)).withRel("update"))
                .add(linkTo(methodOn(PedidoController.class).deletePedido(pedido.getId())).withRel("delete"))
                .add(linkTo(methodOn(PedidoController.class).getAllPedidos(0, 10)).withRel("all"));
    }
}

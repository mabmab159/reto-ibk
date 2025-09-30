package mabmab.retoibk.reto.infrastructure.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mabmab.retoibk.configuration.documentation.PedidoControllerApi;
import mabmab.retoibk.reto.application.usecases.interfaces.IPedidoUseCase;
import mabmab.retoibk.reto.infrastructure.controller.dto.request.PedidoRequest;
import mabmab.retoibk.reto.infrastructure.controller.dto.response.PedidoResponse;
import mabmab.retoibk.reto.infrastructure.controller.mappers.PedidoControllerMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
public class PedidoController implements PedidoControllerApi {
    private final IPedidoUseCase pedidoUseCase;
    private final PedidoControllerMapper mapper;

    @GetMapping
    public Flux<PedidoResponse> getAllPedidos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return pedidoUseCase.findAll(PageRequest.of(page, size))
                .map(mapper::toResponse);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<PedidoResponse>> getPedidoById(@PathVariable Long id) {
        return pedidoUseCase.findById(id)
                .map(mapper::toResponse)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<PedidoResponse>> createPedido(@Valid @RequestBody PedidoRequest request) {
        return pedidoUseCase.save(mapper.toDomain(request))
                .map(mapper::toResponse)
                .map(p -> ResponseEntity.status(HttpStatus.CREATED).body(p));
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<PedidoResponse>> updatePedido(@PathVariable Long id, @Valid @RequestBody PedidoRequest request) {
        return pedidoUseCase.update(id, mapper.toDomain(id, request))
                .map(mapper::toResponse)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deletePedido(@PathVariable Long id) {
        return pedidoUseCase.deleteById(id)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }

}

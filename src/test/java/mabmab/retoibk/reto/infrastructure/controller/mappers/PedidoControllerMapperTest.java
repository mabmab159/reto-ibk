package mabmab.retoibk.reto.infrastructure.controller.mappers;

import mabmab.retoibk.reto.domain.models.Pedido;
import mabmab.retoibk.reto.infrastructure.controller.dto.PedidoItemRequest;
import mabmab.retoibk.reto.infrastructure.controller.dto.PedidoRequest;
import mabmab.retoibk.reto.infrastructure.controller.dto.PedidoResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class PedidoControllerMapperTest {

    private PedidoControllerMapper mapper;
    private Pedido pedido;
    private PedidoRequest request;

    @BeforeEach
    void setUp() {
        mapper = new PedidoControllerMapper();
        pedido = new Pedido(1L, LocalDate.of(2024, 1, 15), new BigDecimal("1500"), true, null, 1L);
        request = new PedidoRequest(LocalDate.of(2024, 1, 15), true, List.of(new PedidoItemRequest(1L, 2)));
    }

    @Test
    void toResponse_ShouldMapPedidoToPedidoResponse() {
        PedidoResponse response = mapper.toResponse(pedido);

        assertNotNull(response);
        assertEquals(pedido.getId(), response.getId());
        assertEquals(pedido.getFecha(), response.getFecha());
        assertEquals(pedido.getTotal(), response.getTotal());
        assertEquals(pedido.isEstado(), response.isEstado());
    }

    @Test
    void toDomain_FromRequest_ShouldMapRequestToPedido() {
        Pedido result = mapper.toDomain(request);

        assertNotNull(result);
        assertNull(result.getId());
        assertEquals(request.getFecha(), result.getFecha());
        assertEquals(request.getItems().size(), result.getItems().size());
        assertEquals(request.isEstado(), result.isEstado());
    }

    @Test
    void toDomain_FromRequestWithId_ShouldMapRequestToPedidoWithId() {
        Long id = 1L;
        Pedido result = mapper.toDomain(id, request);

        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals(request.getFecha(), result.getFecha());
        assertEquals(request.getItems().size(), result.getItems().size());
        assertEquals(request.isEstado(), result.isEstado());
    }
}
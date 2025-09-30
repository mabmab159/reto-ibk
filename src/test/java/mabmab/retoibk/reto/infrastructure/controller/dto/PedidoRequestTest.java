package mabmab.retoibk.reto.infrastructure.controller.dto;

import mabmab.retoibk.reto.infrastructure.controller.dto.request.PedidoItemRequest;
import mabmab.retoibk.reto.infrastructure.controller.dto.request.PedidoRequest;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PedidoRequestTest {

    @Test
    void constructor_ShouldCreatePedidoRequest() {
        LocalDate fecha = LocalDate.of(2024, 1, 15);
        List<PedidoItemRequest> items = List.of(new PedidoItemRequest(1L, 2));
        PedidoRequest request = new PedidoRequest(fecha, true, items);

        assertEquals(fecha, request.getFecha());
        assertTrue(request.isEstado());
        assertEquals(items, request.getItems());
    }

    @Test
    void noArgsConstructor_ShouldCreateEmptyPedidoRequest() {
        PedidoRequest request = new PedidoRequest();

        assertNull(request.getFecha());
        assertFalse(request.isEstado());
        assertNull(request.getItems());
    }

    @Test
    void settersAndGetters_ShouldWorkCorrectly() {
        PedidoRequest request = new PedidoRequest();
        LocalDate fecha = LocalDate.of(2024, 1, 15);
        List<PedidoItemRequest> items = List.of(new PedidoItemRequest(1L, 2));

        request.setFecha(fecha);
        request.setEstado(true);
        request.setItems(items);

        assertEquals(fecha, request.getFecha());
        assertTrue(request.isEstado());
        assertEquals(items, request.getItems());
    }

    @Test
    void equals_ShouldWorkCorrectly() {
        LocalDate fecha = LocalDate.of(2024, 1, 15);
        List<PedidoItemRequest> items = List.of(new PedidoItemRequest(1L, 2));
        PedidoRequest request1 = new PedidoRequest(fecha, true, items);
        PedidoRequest request2 = new PedidoRequest(fecha, true, items);

        assertEquals(request1, request2);
        assertEquals(request1.hashCode(), request2.hashCode());
    }
}
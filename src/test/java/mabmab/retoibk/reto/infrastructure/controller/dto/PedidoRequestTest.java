package mabmab.retoibk.reto.infrastructure.controller.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PedidoRequestTest {

    @Test
    void constructor_ShouldCreatePedidoRequest() {
        LocalDate fecha = LocalDate.of(2024, 1, 15);
        PedidoRequest request = new PedidoRequest(fecha, 1500L, true);

        assertEquals(fecha, request.getFecha());
        assertEquals(1500L, request.getTotal());
        assertTrue(request.isEstado());
    }

    @Test
    void noArgsConstructor_ShouldCreateEmptyPedidoRequest() {
        PedidoRequest request = new PedidoRequest();

        assertNull(request.getFecha());
        assertNull(request.getTotal());
        assertFalse(request.isEstado());
    }

    @Test
    void settersAndGetters_ShouldWorkCorrectly() {
        PedidoRequest request = new PedidoRequest();
        LocalDate fecha = LocalDate.of(2024, 1, 15);

        request.setFecha(fecha);
        request.setTotal(1500L);
        request.setEstado(true);

        assertEquals(fecha, request.getFecha());
        assertEquals(1500L, request.getTotal());
        assertTrue(request.isEstado());
    }

    @Test
    void equals_ShouldWorkCorrectly() {
        LocalDate fecha = LocalDate.of(2024, 1, 15);
        PedidoRequest request1 = new PedidoRequest(fecha, 1500L, true);
        PedidoRequest request2 = new PedidoRequest(fecha, 1500L, true);

        assertEquals(request1, request2);
        assertEquals(request1.hashCode(), request2.hashCode());
    }
}
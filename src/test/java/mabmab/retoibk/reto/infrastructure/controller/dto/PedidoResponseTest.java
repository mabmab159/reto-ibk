package mabmab.retoibk.reto.infrastructure.controller.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PedidoResponseTest {

    @Test
    void constructor_ShouldCreatePedidoResponse() {
        LocalDate fecha = LocalDate.of(2024, 1, 15);
        List<PedidoItemResponse> items = List.of(new PedidoItemResponse(1L, 1L, "Producto", 2, new BigDecimal("100"), new BigDecimal("200")));
        PedidoResponse response = new PedidoResponse(1L, fecha, new BigDecimal("1500"), true, items);

        assertEquals(1L, response.getId());
        assertEquals(fecha, response.getFecha());
        assertEquals(new BigDecimal("1500"), response.getTotal());
        assertTrue(response.isEstado());
        assertEquals(items, response.getItems());
    }

    @Test
    void noArgsConstructor_ShouldCreateEmptyPedidoResponse() {
        PedidoResponse response = new PedidoResponse();

        assertNull(response.getId());
        assertNull(response.getFecha());
        assertNull(response.getTotal());
        assertFalse(response.isEstado());
        assertNull(response.getItems());
    }

    @Test
    void settersAndGetters_ShouldWorkCorrectly() {
        PedidoResponse response = new PedidoResponse();
        LocalDate fecha = LocalDate.of(2024, 1, 15);
        List<PedidoItemResponse> items = List.of(new PedidoItemResponse(1L, 1L, "Producto", 2, new BigDecimal("100"), new BigDecimal("200")));

        response.setId(1L);
        response.setFecha(fecha);
        response.setTotal(new BigDecimal("1500"));
        response.setEstado(true);
        response.setItems(items);

        assertEquals(1L, response.getId());
        assertEquals(fecha, response.getFecha());
        assertEquals(new BigDecimal("1500"), response.getTotal());
        assertTrue(response.isEstado());
        assertEquals(items, response.getItems());
    }



    @Test
    void equals_ShouldWorkCorrectly() {
        LocalDate fecha = LocalDate.of(2024, 1, 15);
        List<PedidoItemResponse> items = List.of(new PedidoItemResponse(1L, 1L, "Producto", 2, new BigDecimal("100"), new BigDecimal("200")));
        PedidoResponse response1 = new PedidoResponse(1L, fecha, new BigDecimal("1500"), true, items);
        PedidoResponse response2 = new PedidoResponse(1L, fecha, new BigDecimal("1500"), true, items);

        assertEquals(response1, response2);
        assertEquals(response1.hashCode(), response2.hashCode());
    }
}
package mabmab.retoibk.reto.infrastructure.controller.dto;

import org.junit.jupiter.api.Test;
import org.springframework.hateoas.Link;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PedidoResponseTest {

    @Test
    void constructor_ShouldCreatePedidoResponse() {
        LocalDate fecha = LocalDate.of(2024, 1, 15);
        PedidoResponse response = new PedidoResponse(1L, fecha, new BigDecimal("1500"), true);

        assertEquals(1L, response.getId());
        assertEquals(fecha, response.getFecha());
        assertEquals(new BigDecimal("1500"), response.getTotal());
        assertTrue(response.isEstado());
    }

    @Test
    void noArgsConstructor_ShouldCreateEmptyPedidoResponse() {
        PedidoResponse response = new PedidoResponse();

        assertNull(response.getId());
        assertNull(response.getFecha());
        assertNull(response.getTotal());
        assertFalse(response.isEstado());
    }

    @Test
    void settersAndGetters_ShouldWorkCorrectly() {
        PedidoResponse response = new PedidoResponse();
        LocalDate fecha = LocalDate.of(2024, 1, 15);

        response.setId(1L);
        response.setFecha(fecha);
        response.setTotal(new BigDecimal("1500"));
        response.setEstado(true);

        assertEquals(1L, response.getId());
        assertEquals(fecha, response.getFecha());
        assertEquals(new BigDecimal("1500"), response.getTotal());
        assertTrue(response.isEstado());
    }

    @Test
    void addLink_ShouldAddHateoasLink() {
        PedidoResponse response = new PedidoResponse(1L, LocalDate.now(), new BigDecimal("1500"), true);
        Link selfLink = Link.of("/api/pedidos/1").withSelfRel();

        response.add(selfLink);

        assertTrue(response.hasLinks());
        assertTrue(response.hasLink("self"));
    }

    @Test
    void equals_ShouldWorkCorrectly() {
        LocalDate fecha = LocalDate.of(2024, 1, 15);
        PedidoResponse response1 = new PedidoResponse(1L, fecha, new BigDecimal("1500"), true);
        PedidoResponse response2 = new PedidoResponse(1L, fecha, new BigDecimal("1500"), true);

        assertEquals(response1, response2);
        assertEquals(response1.hashCode(), response2.hashCode());
    }
}
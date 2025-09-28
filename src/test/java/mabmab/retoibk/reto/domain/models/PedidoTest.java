package mabmab.retoibk.reto.domain.models;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PedidoTest {

    @Test
    void constructor_ShouldCreatePedido() {
        LocalDate fecha = LocalDate.of(2024, 1, 15);
        Pedido pedido = new Pedido(1L, fecha, new BigDecimal("1500"), true, null, 1L);

        assertEquals(1L, pedido.getId());
        assertEquals(fecha, pedido.getFecha());
        assertEquals(new BigDecimal("1500"), pedido.getTotal());
        assertTrue(pedido.isEstado());
    }

    @Test
    void noArgsConstructor_ShouldCreateEmptyPedido() {
        Pedido pedido = new Pedido();

        assertNull(pedido.getId());
        assertNull(pedido.getFecha());
        assertNull(pedido.getTotal());
        assertFalse(pedido.isEstado());
    }

    @Test
    void settersAndGetters_ShouldWorkCorrectly() {
        Pedido pedido = new Pedido();
        LocalDate fecha = LocalDate.of(2024, 1, 15);

        pedido.setId(1L);
        pedido.setFecha(fecha);
        pedido.setTotal(new BigDecimal("1500"));
        pedido.setEstado(true);

        assertEquals(1L, pedido.getId());
        assertEquals(fecha, pedido.getFecha());
        assertEquals(new BigDecimal("1500"), pedido.getTotal());
        assertTrue(pedido.isEstado());
    }

    @Test
    void equals_ShouldWorkCorrectly() {
        LocalDate fecha = LocalDate.of(2024, 1, 15);
        Pedido pedido1 = new Pedido(1L, fecha, new BigDecimal("1500"), true, null, 1L);
        Pedido pedido2 = new Pedido(1L, fecha, new BigDecimal("1500"), true, null, 1L);

        assertEquals(pedido1, pedido2);
        assertEquals(pedido1.hashCode(), pedido2.hashCode());
    }

    @Test
    void toString_ShouldReturnStringRepresentation() {
        LocalDate fecha = LocalDate.of(2024, 1, 15);
        Pedido pedido = new Pedido(1L, fecha, new BigDecimal("1500"), true, null, 1L);

        String result = pedido.toString();

        assertNotNull(result);
        assertTrue(result.contains("1"));
        assertTrue(result.contains("1500"));
        assertTrue(result.contains("true"));
    }
}
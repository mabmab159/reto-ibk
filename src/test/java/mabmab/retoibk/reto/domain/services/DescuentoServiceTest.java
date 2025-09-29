package mabmab.retoibk.reto.domain.services;

import mabmab.retoibk.reto.domain.models.Pedido;
import mabmab.retoibk.reto.domain.models.PedidoItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DescuentoServiceTest {

    private DescuentoService descuentoService;

    @BeforeEach
    void setUp() {
        descuentoService = new DescuentoService();
    }

    @Test
    void calcularDescuento_SinDescuento_DeberiaRetornarCero() {
        // Pedido con total < 1000 y <= 5 productos
        List<PedidoItem> items = List.of(
                new PedidoItem(1L, 1L, 1L, 1, new BigDecimal("100"), new BigDecimal("100")),
                new PedidoItem(2L, 1L, 2L, 1, new BigDecimal("200"), new BigDecimal("200"))
        );
        Pedido pedido = new Pedido(1L, LocalDate.now(), new BigDecimal("300"), false, items, 1L);

        BigDecimal descuento = descuentoService.calcularDescuento(pedido);

        assertEquals(BigDecimal.ZERO.setScale(2), descuento);
    }

    @Test
    void calcularDescuento_SoloDescuentoPorMonto_DeberiaAplicar10Porciento() {
        // Pedido con total > 1000 pero <= 5 productos
        List<PedidoItem> items = List.of(
                new PedidoItem(1L, 1L, 1L, 1, new BigDecimal("1200"), new BigDecimal("1200"))
        );
        Pedido pedido = new Pedido(1L, LocalDate.now(), new BigDecimal("1200"), false, items, 1L);

        BigDecimal descuento = descuentoService.calcularDescuento(pedido);

        assertEquals(new BigDecimal("120.00"), descuento);
    }

    @Test
    void calcularDescuento_SoloDescuentoPorCantidad_DeberiaAplicar5Porciento() {
        // Pedido con total < 1000 pero > 5 productos diferentes
        List<PedidoItem> items = List.of(
                new PedidoItem(1L, 1L, 1L, 1, new BigDecimal("100"), new BigDecimal("100")),
                new PedidoItem(2L, 1L, 2L, 1, new BigDecimal("100"), new BigDecimal("100")),
                new PedidoItem(3L, 1L, 3L, 1, new BigDecimal("100"), new BigDecimal("100")),
                new PedidoItem(4L, 1L, 4L, 1, new BigDecimal("100"), new BigDecimal("100")),
                new PedidoItem(5L, 1L, 5L, 1, new BigDecimal("100"), new BigDecimal("100")),
                new PedidoItem(6L, 1L, 6L, 1, new BigDecimal("100"), new BigDecimal("100"))
        );
        Pedido pedido = new Pedido(1L, LocalDate.now(), new BigDecimal("600"), false, items, 1L);

        BigDecimal descuento = descuentoService.calcularDescuento(pedido);

        assertEquals(new BigDecimal("30.00"), descuento);
    }

    @Test
    void calcularDescuento_AmbosDescuentos_DeberiaAplicar15Porciento() {
        // Pedido con total > 1000 y > 5 productos diferentes
        List<PedidoItem> items = List.of(
                new PedidoItem(1L, 1L, 1L, 1, new BigDecimal("200"), new BigDecimal("200")),
                new PedidoItem(2L, 1L, 2L, 1, new BigDecimal("200"), new BigDecimal("200")),
                new PedidoItem(3L, 1L, 3L, 1, new BigDecimal("200"), new BigDecimal("200")),
                new PedidoItem(4L, 1L, 4L, 1, new BigDecimal("200"), new BigDecimal("200")),
                new PedidoItem(5L, 1L, 5L, 1, new BigDecimal("200"), new BigDecimal("200")),
                new PedidoItem(6L, 1L, 6L, 1, new BigDecimal("200"), new BigDecimal("200"))
        );
        Pedido pedido = new Pedido(1L, LocalDate.now(), new BigDecimal("1200"), false, items, 1L);

        BigDecimal descuento = descuentoService.calcularDescuento(pedido);

        assertEquals(new BigDecimal("180.00"), descuento); // 10% + 5% = 15%
    }

    @Test
    void calcularTotal_ConDescuentos_DeberiaRestarDescuentoDelSubtotal() {
        List<PedidoItem> items = List.of(
                new PedidoItem(1L, 1L, 1L, 1, new BigDecimal("1200"), new BigDecimal("1200"))
        );
        Pedido pedido = new Pedido(1L, LocalDate.now(), new BigDecimal("1200"), false, items, 1L);

        BigDecimal total = descuentoService.calcularTotal(pedido);

        assertEquals(new BigDecimal("1080.00"), total); // 1200 - 120 (10%)
    }
}
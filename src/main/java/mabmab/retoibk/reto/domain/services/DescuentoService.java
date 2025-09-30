package mabmab.retoibk.reto.domain.services;

import mabmab.retoibk.reto.domain.models.Pedido;
import mabmab.retoibk.reto.domain.models.PedidoItem;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class DescuentoService {

    private static final BigDecimal DESCUENTO_MONTO = new BigDecimal("0.10");
    private static final BigDecimal DESCUENTO_CANTIDAD = new BigDecimal("0.05");
    private static final BigDecimal MONTO_MINIMO = new BigDecimal("1000");
    private static final int CANTIDAD_MINIMA_PRODUCTOS = 5;

    public BigDecimal calcularDescuento(Pedido pedido) {
        BigDecimal subtotal = calcularSubtotal(pedido);
        BigDecimal descuentoTotal = BigDecimal.ZERO;

        if (subtotal.compareTo(MONTO_MINIMO) > 0) {
            descuentoTotal = descuentoTotal.add(subtotal.multiply(DESCUENTO_MONTO));
        }

        if (pedido.getItems() != null && pedido.getItems().size() > CANTIDAD_MINIMA_PRODUCTOS) {
            descuentoTotal = descuentoTotal.add(subtotal.multiply(DESCUENTO_CANTIDAD));
        }

        return descuentoTotal.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal calcularTotal(Pedido pedido) {
        BigDecimal subtotal = calcularSubtotal(pedido);
        BigDecimal descuento = calcularDescuento(pedido);
        return subtotal.subtract(descuento).setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal calcularSubtotal(Pedido pedido) {
        if (pedido.getItems() == null) {
            return BigDecimal.ZERO;
        }

        return pedido.getItems().stream()
                .map(PedidoItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
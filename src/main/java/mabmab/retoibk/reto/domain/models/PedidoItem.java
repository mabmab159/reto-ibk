package mabmab.retoibk.reto.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoItem {
    private Long id;
    private Long pedidoId;
    private Long productoId;
    private int cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;
}
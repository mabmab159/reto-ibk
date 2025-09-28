package mabmab.retoibk.reto.infrastructure.dataproviders.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Data
@Table("pedido_item")
@NoArgsConstructor
@AllArgsConstructor
public class PedidoItemEntity {
    @Id
    private Long id;
    private Long pedidoId;
    private Long productoId;
    private int cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;
}
package mabmab.retoibk.reto.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {
    private Long id;
    private LocalDate fecha;
    private BigDecimal total;
    private boolean estado;
    private List<PedidoItem> items;
    private Long version;
}

package mabmab.retoibk.reto.infrastructure.dataproviders.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Data
@Table("pedido")
@NoArgsConstructor
@AllArgsConstructor
public class PedidoEntity {
    @Id
    private Long id;
    private LocalDate fecha;
    private Long total;
    private boolean estado;
}
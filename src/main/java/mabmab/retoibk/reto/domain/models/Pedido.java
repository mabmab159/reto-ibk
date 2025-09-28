package mabmab.retoibk.reto.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {
    private Long id;
    private LocalDate fecha;
    private Long total;
    private boolean estado;
}

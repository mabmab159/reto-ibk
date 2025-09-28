package mabmab.retoibk.reto.infrastructure.dataproviders.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDTO {
    private Long id;
    private LocalDate fecha;
    private Long total;
    private boolean estado;
}
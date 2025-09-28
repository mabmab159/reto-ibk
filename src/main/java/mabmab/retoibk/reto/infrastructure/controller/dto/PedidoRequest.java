package mabmab.retoibk.reto.infrastructure.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoRequest {
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha;
    
    private Long total;
    private boolean estado;
}
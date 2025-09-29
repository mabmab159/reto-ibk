package mabmab.retoibk.reto.infrastructure.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoResponse {
    private Long id;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha;
    private BigDecimal total;
    private boolean estado;
    private List<PedidoItemResponse> items;
}
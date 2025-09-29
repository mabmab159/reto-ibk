package mabmab.retoibk.reto.infrastructure.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PedidoResponse extends RepresentationModel<PedidoResponse> {
    private Long id;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha;
    private BigDecimal total;
    private boolean estado;
    private List<PedidoItemResponse> items;
}
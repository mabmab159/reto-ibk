package mabmab.retoibk.reto.infrastructure.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PedidoResponse extends RepresentationModel<PedidoResponse> {
    private Long id;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha;
    
    private Long total;
    private boolean estado;
}
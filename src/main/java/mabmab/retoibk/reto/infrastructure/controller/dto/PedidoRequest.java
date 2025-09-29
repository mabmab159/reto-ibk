package mabmab.retoibk.reto.infrastructure.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoRequest {
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha;
    private boolean estado;
    
    @Valid
    @NotEmpty(message = "El pedido debe tener al menos un item")
    private List<PedidoItemRequest> items;
}
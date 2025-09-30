package mabmab.retoibk.reto.infrastructure.controller.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoItemRequest {
    @NotNull(message = "El ID del producto es requerido")
    private Long productoId;

    @Positive(message = "La cantidad debe ser mayor a 0")
    private int cantidad;
}
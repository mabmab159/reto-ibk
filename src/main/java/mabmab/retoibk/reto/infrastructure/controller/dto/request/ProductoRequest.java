package mabmab.retoibk.reto.infrastructure.controller.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoRequest {
    @NotBlank(message = "Nombre is required")
    private String nombre;

    @NotNull(message = "Precio is required")
    @Positive(message = "Precio must be positive")
    private BigDecimal precio;

    @PositiveOrZero(message = "Stock must be positive or zero")
    private int stock;
}
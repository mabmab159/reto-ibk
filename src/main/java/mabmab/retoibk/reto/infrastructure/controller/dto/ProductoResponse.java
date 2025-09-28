package mabmab.retoibk.reto.infrastructure.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ProductoResponse extends RepresentationModel<ProductoResponse> {
    private Long id;
    private String nombre;
    private Long precio;
    private int cantidad;
}
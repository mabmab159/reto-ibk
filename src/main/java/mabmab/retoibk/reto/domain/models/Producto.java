package mabmab.retoibk.reto.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producto {
    private Long id;
    private String nombre;
    private Long precio;
    private int cantidad;
}

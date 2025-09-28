package mabmab.retoibk.reto.infrastructure.dataproviders.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("producto")
@NoArgsConstructor
@AllArgsConstructor
public class ProductoEntity {
    @Id
    private Long id;
    private String nombre;
    private Long precio;
    private int cantidad;
}
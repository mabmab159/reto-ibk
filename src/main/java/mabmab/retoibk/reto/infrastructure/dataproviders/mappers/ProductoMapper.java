package mabmab.retoibk.reto.infrastructure.dataproviders.mappers;

import mabmab.retoibk.reto.domain.models.Producto;
import mabmab.retoibk.reto.infrastructure.dataproviders.entities.ProductoEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductoMapper {
    
    public Producto toDomain(ProductoEntity entity) {
        return new Producto(entity.getId(), entity.getNombre(), entity.getPrecio(), entity.getCantidad());
    }
    
    public ProductoEntity toEntity(Producto domain) {
        return new ProductoEntity(domain.getId(), domain.getNombre(), domain.getPrecio(), domain.getCantidad());
    }
}
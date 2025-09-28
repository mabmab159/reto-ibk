package mabmab.retoibk.reto.infrastructure.dataproviders.mappers;

import mabmab.retoibk.reto.domain.models.Producto;
import mabmab.retoibk.reto.infrastructure.dataproviders.entities.ProductoEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductoMapper {
    
    public Producto toDomain(ProductoEntity entity) {
        if (entity == null) {
            throw new IllegalArgumentException("ProductoEntity cannot be null");
        }
        return new Producto(entity.getId(), entity.getNombre(), entity.getPrecio(), entity.getStock(), entity.getVersion());
    }
    
    public ProductoEntity toEntity(Producto domain) {
        if (domain == null) {
            throw new IllegalArgumentException("Producto cannot be null");
        }
        return new ProductoEntity(domain.getId(), domain.getNombre(), domain.getPrecio(), domain.getStock(), domain.getVersion());
    }
}
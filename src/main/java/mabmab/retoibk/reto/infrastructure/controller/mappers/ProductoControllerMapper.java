package mabmab.retoibk.reto.infrastructure.controller.mappers;

import mabmab.retoibk.reto.domain.models.Producto;
import mabmab.retoibk.reto.infrastructure.controller.dto.ProductoRequest;
import mabmab.retoibk.reto.infrastructure.controller.dto.ProductoResponse;
import org.springframework.stereotype.Component;

@Component
public class ProductoControllerMapper {
    
    public ProductoResponse toResponse(Producto producto) {
        return new ProductoResponse(
            producto.getId(),
            producto.getNombre(),
            producto.getPrecio(),
            producto.getCantidad()
        );
    }
    
    public Producto toDomain(ProductoRequest request) {
        return new Producto(null, request.getNombre(), request.getPrecio(), request.getCantidad());
    }
    
    public Producto toDomain(Long id, ProductoRequest request) {
        return new Producto(id, request.getNombre(), request.getPrecio(), request.getCantidad());
    }
}
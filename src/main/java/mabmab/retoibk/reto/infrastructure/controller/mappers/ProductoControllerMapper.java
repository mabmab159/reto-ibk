package mabmab.retoibk.reto.infrastructure.controller.mappers;

import mabmab.retoibk.reto.domain.models.Producto;
import mabmab.retoibk.reto.infrastructure.controller.dto.ProductoRequest;
import mabmab.retoibk.reto.infrastructure.controller.dto.ProductoResponse;
import org.springframework.stereotype.Component;

@Component
public class ProductoControllerMapper {
    
    public ProductoResponse toResponse(Producto producto) {
        if (producto == null) {
            throw new IllegalArgumentException("Producto cannot be null");
        }
        return new ProductoResponse(
            producto.getId(),
            producto.getNombre(),
            producto.getPrecio(),
            producto.getStock()
        );
    }
    
    public Producto toDomain(ProductoRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("ProductoRequest cannot be null");
        }
        return new Producto(null, request.getNombre(), request.getPrecio(), request.getStock(), null);
    }
    
    public Producto toDomain(Long id, ProductoRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("ProductoRequest cannot be null");
        }
        return new Producto(id, request.getNombre(), request.getPrecio(), request.getStock(), null);
    }
}
package mabmab.retoibk.reto.infrastructure.controller.mappers;

import mabmab.retoibk.reto.domain.models.Pedido;
import mabmab.retoibk.reto.infrastructure.controller.dto.PedidoRequest;
import mabmab.retoibk.reto.infrastructure.controller.dto.PedidoResponse;
import org.springframework.stereotype.Component;

@Component
public class PedidoControllerMapper {
    
    public PedidoResponse toResponse(Pedido pedido) {
        return new PedidoResponse(
            pedido.getId(),
            pedido.getFecha(),
            pedido.getTotal(),
            pedido.isEstado()
        );
    }
    
    public Pedido toDomain(PedidoRequest request) {
        return new Pedido(
            null,
            request.getFecha(),
            request.getTotal(),
            request.isEstado()
        );
    }
    
    public Pedido toDomain(Long id, PedidoRequest request) {
        return new Pedido(
            id,
            request.getFecha(),
            request.getTotal(),
            request.isEstado()
        );
    }
}
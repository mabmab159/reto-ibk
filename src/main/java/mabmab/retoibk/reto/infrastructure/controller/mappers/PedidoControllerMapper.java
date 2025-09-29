package mabmab.retoibk.reto.infrastructure.controller.mappers;

import mabmab.retoibk.reto.domain.models.Pedido;
import mabmab.retoibk.reto.domain.models.PedidoItem;
import mabmab.retoibk.reto.infrastructure.controller.dto.PedidoItemRequest;
import mabmab.retoibk.reto.infrastructure.controller.dto.PedidoItemResponse;
import mabmab.retoibk.reto.infrastructure.controller.dto.PedidoRequest;
import mabmab.retoibk.reto.infrastructure.controller.dto.PedidoResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PedidoControllerMapper {

    public PedidoResponse toResponse(Pedido pedido) {
        List<PedidoItemResponse> itemsResponse = pedido.getItems() != null ?
                pedido.getItems().stream()
                        .map(this::toItemResponse)
                        .collect(Collectors.toList()) : null;
        
        return new PedidoResponse(
                pedido.getId(),
                pedido.getFecha(),
                pedido.getTotal(),
                pedido.isEstado(),
                itemsResponse
        );
    }

    public Pedido toDomain(PedidoRequest request) {
        List<PedidoItem> items = request.getItems() != null ?
                request.getItems().stream()
                        .map(this::toItemDomain)
                        .collect(Collectors.toList()) : null;
        
        return new Pedido(
                null,
                request.getFecha(),
                null, // total se calcula en el servicio
                request.isEstado(),
                items,
                null
        );
    }

    public Pedido toDomain(Long id, PedidoRequest request) {
        List<PedidoItem> items = request.getItems() != null ?
                request.getItems().stream()
                        .map(this::toItemDomain)
                        .collect(Collectors.toList()) : null;
        
        return new Pedido(
                id,
                request.getFecha(),
                null, // total se calcula en el servicio
                request.isEstado(),
                items,
                null
        );
    }
    
    private PedidoItemResponse toItemResponse(PedidoItem item) {
        return new PedidoItemResponse(
                item.getId(),
                item.getProductoId(),
                null, // nombre se obtiene del servicio
                item.getCantidad(),
                item.getPrecioUnitario(),
                item.getSubtotal()
        );
    }
    
    private PedidoItem toItemDomain(PedidoItemRequest request) {
        return new PedidoItem(
                null,
                null, // se asigna en el servicio
                request.getProductoId(),
                request.getCantidad(),
                null, // se obtiene del producto
                null  // se calcula en el servicio
        );
    }
}
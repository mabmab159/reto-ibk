package mabmab.retoibk.reto.infrastructure.dataproviders.mappers;

import mabmab.retoibk.reto.domain.models.Pedido;
import mabmab.retoibk.reto.domain.models.PedidoItem;
import mabmab.retoibk.reto.infrastructure.dataproviders.entities.PedidoEntity;
import mabmab.retoibk.reto.infrastructure.dataproviders.entities.PedidoItemEntity;
import org.springframework.stereotype.Component;

@Component
public class PedidoMapper {

    public Pedido toDomain(PedidoEntity entity) {
        return new Pedido(entity.getId(), entity.getFecha(), entity.getTotal(), entity.isEstado(), null, entity.getVersion());
    }

    public PedidoEntity toEntity(Pedido domain) {
        return new PedidoEntity(domain.getId(), domain.getFecha(), domain.getTotal(), domain.isEstado(), domain.getVersion());
    }
    
    public PedidoItem toItemDomain(PedidoItemEntity entity) {
        return new PedidoItem(entity.getId(), entity.getPedidoId(), entity.getProductoId(), 
                entity.getCantidad(), entity.getPrecioUnitario(), entity.getSubtotal());
    }
    
    public PedidoItemEntity toItemEntity(PedidoItem domain) {
        return new PedidoItemEntity(domain.getId(), domain.getPedidoId(), domain.getProductoId(),
                domain.getCantidad(), domain.getPrecioUnitario(), domain.getSubtotal());
    }
}
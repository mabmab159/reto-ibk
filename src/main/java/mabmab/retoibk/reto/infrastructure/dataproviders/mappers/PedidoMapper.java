package mabmab.retoibk.reto.infrastructure.dataproviders.mappers;

import mabmab.retoibk.reto.domain.models.Pedido;
import mabmab.retoibk.reto.infrastructure.dataproviders.entities.PedidoEntity;
import org.springframework.stereotype.Component;

@Component
public class PedidoMapper {
    
    public Pedido toDomain(PedidoEntity entity) {
        return new Pedido(entity.getId(), entity.getFecha(), entity.getTotal(), entity.isEstado());
    }
    
    public PedidoEntity toEntity(Pedido domain) {
        return new PedidoEntity(domain.getId(), domain.getFecha(), domain.getTotal(), domain.isEstado());
    }
}
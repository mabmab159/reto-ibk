package mabmab.retoibk.configuration.documentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import mabmab.retoibk.reto.infrastructure.controller.dto.request.PedidoRequest;
import mabmab.retoibk.reto.infrastructure.controller.dto.response.PedidoResponse;
import reactor.core.publisher.Flux;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

@Tag(name = "Pedidos", description = "API para gestión de pedidos con cálculo automático de descuentos")
public interface PedidoControllerApi {

    @Operation(summary = "Obtener todos los pedidos", description = "Retorna una lista paginada de pedidos")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de pedidos obtenida exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    Flux<PedidoResponse> getAllPedidos(
            @Parameter(description = "Número de página (inicia en 0)") int page,
            @Parameter(description = "Tamaño de página") int size);

    @Operation(summary = "Obtener pedido por ID", description = "Retorna un pedido específico por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido encontrado"),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    Mono<ResponseEntity<PedidoResponse>> getPedidoById(
            @Parameter(description = "ID del pedido") Long id);

    @Operation(summary = "Crear pedido", description = "Crea un nuevo pedido con lista de productos. El total se calcula automáticamente aplicando descuentos: 10% si total > $1000, 5% adicional si > 5 productos diferentes")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Pedido creado exitosamente con descuentos aplicados"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos o productos no encontrados"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    Mono<ResponseEntity<PedidoResponse>> createPedido(
            @Parameter(description = "Datos del pedido con lista de productos y cantidades") @Valid PedidoRequest request);

    @Operation(summary = "Actualizar pedido", description = "Actualiza un pedido existente con nueva lista de productos. El total se recalcula automáticamente aplicando descuentos")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido actualizado exitosamente con descuentos aplicados"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos o productos no encontrados"),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    Mono<ResponseEntity<PedidoResponse>> updatePedido(
            @Parameter(description = "ID del pedido") Long id,
            @Parameter(description = "Datos actualizados del pedido con lista de productos") @Valid PedidoRequest request);

    @Operation(summary = "Eliminar pedido", description = "Elimina un pedido del sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Pedido eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Pedido no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    Mono<ResponseEntity<Void>> deletePedido(
            @Parameter(description = "ID del pedido") Long id);




}
package mabmab.retoibk.configuration.documentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import mabmab.retoibk.reto.infrastructure.controller.dto.ProductoRequest;
import mabmab.retoibk.reto.infrastructure.controller.dto.ProductoResponse;
import reactor.core.publisher.Flux;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

@Tag(name = "Productos", description = "API para gestión de productos")
public interface ProductoControllerApi {

    @Operation(summary = "Obtener todos los productos", description = "Retorna una lista paginada de productos")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de productos obtenida exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    Flux<ProductoResponse> getAllProductos(
            @Parameter(description = "Número de página (inicia en 0)") int page,
            @Parameter(description = "Tamaño de página") int size);

    @Operation(summary = "Obtener producto por ID", description = "Retorna un producto específico por su ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Producto encontrado"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    Mono<ResponseEntity<ProductoResponse>> getProductoById(
            @Parameter(description = "ID del producto") Long id);

    @Operation(summary = "Crear producto", description = "Crea un nuevo producto en el sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Producto creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    Mono<ResponseEntity<ProductoResponse>> createProducto(
            @Parameter(description = "Datos del producto a crear") @Valid ProductoRequest request);

    @Operation(summary = "Actualizar producto", description = "Actualiza un producto existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Producto actualizado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    Mono<ResponseEntity<ProductoResponse>> updateProducto(
            @Parameter(description = "ID del producto") Long id,
            @Parameter(description = "Datos actualizados del producto") @Valid ProductoRequest request);

    @Operation(summary = "Eliminar producto", description = "Elimina un producto del sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Producto eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    Mono<ResponseEntity<Void>> deleteProducto(
            @Parameter(description = "ID del producto") Long id);


}
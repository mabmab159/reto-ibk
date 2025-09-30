# Reto IBK - Sistema de Gestión de Pedidos

## 📋 Descripción

Sistema reactivo para la gestión de pedidos desarrollado con Spring Boot WebFlux usando arquitectura hexagonal (Clean Architecture). El sistema nos permite realizar la gestión de productos y pedidos.

## 🏗 Arquitectura

El proyecto implementa *Arquitectura Hexagonal (Clean Architecture)* con las siguientes capas:

### 📁 Estructura del Proyecto

src/main/java/mabmab/retoibk/

├── configuration/          # Configuraciones generales
│   ├── documentation/      # Configuración de documentación API
│   ├── exceptions/         # Manejo global de excepciones
│   └── openapi/            # Configuración OpenAPI
├── reto/
│   ├── application/        # Capa de Aplicación
│   │   ├── ports/          # Puertos de entrada
│   │   └── usecases/       # Casos de uso
│   ├── domain/             # Capa de Dominio
│   │   ├── exceptions/     # Excepciones del dominio
│   │   ├── models/         # Entidades del dominio
│   │   ├── ports/          # Puertos del dominio
│   │   └── services/       # Servicios del dominio
│   └── infrastructure/     # Capa de Infraestructura
│       ├── adapters/       # Adaptadores de repositorios
│       ├── controller/     # Controladores REST
│       └── dataproviders/  # Proveedores de datos


## 🛠 Configuración y Ejecución

### Prerrequisitos
- Java 21
- Docker y Docker Compose
- Gradle (incluido wrapper)

### 1. Clonar el Repositorio

git clone <repository-url>
cd retoibk

### 2. Compilar la solución

./gradlew build

### 3. Levantar el ambiente (base de datos + aplicacion)

docker-compose up -d

### 4. Acceder a la Aplicación
- *API*: http://localhost:8080
- *Swagger UI*: http://localhost:8080/swagger-ui.html
- *OpenAPI Docs*: http://localhost:8080/v3/api-docs

## 🧪 Testing

El proyecto incluye testing completo en todas las capas:

### Ejecutar Tests

./gradlew test

## 📡 API Endpoints

### Productos
- GET /api/productos - Listar productos (con paginación)
- GET /api/productos/{id} - Obtener producto por ID
- POST /api/productos - Crear producto
- PUT /api/productos/{id} - Actualizar producto
- DELETE /api/productos/{id} - Eliminar producto

### Pedidos
- GET /api/pedidos - Listar pedidos (con paginación)
- GET /api/pedidos/{id} - Obtener pedido por ID
- POST /api/pedidos - Crear pedido
- PUT /api/pedidos/{id} - Actualizar pedido
- DELETE /api/pedidos/{id} - Eliminar pedido
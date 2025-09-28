package mabmab.retoibk.reto.domain.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductoTest {

    @Test
    void constructor_ShouldCreateProducto() {
        Producto producto = new Producto(1L, "Laptop", 1500L, 10);

        assertEquals(1L, producto.getId());
        assertEquals("Laptop", producto.getNombre());
        assertEquals(1500L, producto.getPrecio());
        assertEquals(10, producto.getCantidad());
    }

    @Test
    void noArgsConstructor_ShouldCreateEmptyProducto() {
        Producto producto = new Producto();

        assertNull(producto.getId());
        assertNull(producto.getNombre());
        assertNull(producto.getPrecio());
        assertEquals(0, producto.getCantidad());
    }

    @Test
    void settersAndGetters_ShouldWorkCorrectly() {
        Producto producto = new Producto();

        producto.setId(1L);
        producto.setNombre("Laptop");
        producto.setPrecio(1500L);
        producto.setCantidad(10);

        assertEquals(1L, producto.getId());
        assertEquals("Laptop", producto.getNombre());
        assertEquals(1500L, producto.getPrecio());
        assertEquals(10, producto.getCantidad());
    }

    @Test
    void equals_ShouldWorkCorrectly() {
        Producto producto1 = new Producto(1L, "Laptop", 1500L, 10);
        Producto producto2 = new Producto(1L, "Laptop", 1500L, 10);

        assertEquals(producto1, producto2);
        assertEquals(producto1.hashCode(), producto2.hashCode());
    }

    @Test
    void toString_ShouldReturnStringRepresentation() {
        Producto producto = new Producto(1L, "Laptop", 1500L, 10);

        String result = producto.toString();

        assertNotNull(result);
        assertTrue(result.contains("1"));
        assertTrue(result.contains("Laptop"));
        assertTrue(result.contains("1500"));
        assertTrue(result.contains("10"));
    }
}
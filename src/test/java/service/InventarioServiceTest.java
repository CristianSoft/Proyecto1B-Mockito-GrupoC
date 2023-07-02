package service;

import  org.example.model.Producto;
import  org.example.dao.ProductoDAO;
import  org.example.service.InventarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
public class InventarioServiceTest {

    @Mock
    private ProductoDAO productoDAO;

    @InjectMocks
    private InventarioService inventarioService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRealizarCompra() {
        // Arrange
        int productoId = 1;
        int cantidadDisponible = 10;
        int cantidadCompra = 5;

        Producto producto = new Producto(productoId, "Producto de prueba", 100.0, cantidadDisponible);
        when(productoDAO.obtenerProductoPorId(productoId)).thenReturn(producto);

        // Act
        inventarioService.realizarCompra(productoId, cantidadCompra);

        // Assert
        assertEquals(cantidadDisponible - cantidadCompra, producto.getCantidadDisponible());
        verify(productoDAO, times(1)).actualizarProducto(producto);
    }

    @Test
    public void testObtenerCantidadDisponible() {
        // Arrange
        int productoId = 1;
        int cantidadDisponible = 10;

        Producto producto = new Producto(productoId, "Producto de prueba", 100.0, cantidadDisponible);
        when(productoDAO.obtenerProductoPorId(productoId)).thenReturn(producto);

        // Act
        int cantidadActual = inventarioService.obtenerCantidadDisponible(productoId);

        // Assert
        assertEquals(cantidadDisponible, cantidadActual);
    }

    @Test
    public void testAplicarDescuento() {
        // Arrange
        int productoId = 1;
        double porcentajeDescuento = 10.0;

        Producto producto = new Producto(productoId, "Producto de prueba", 100.0, 10);
        when(productoDAO.obtenerProductoPorId(productoId)).thenReturn(producto);

        // Act
        inventarioService.aplicarDescuento(productoId, porcentajeDescuento);

        // Assert
        assertEquals(90.0, producto.getPrecio());
        verify(productoDAO, times(1)).actualizarProducto(producto);
    }

}
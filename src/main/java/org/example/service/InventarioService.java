package org.example.service;
import  org.example.model.Producto;
import  org.example.dao.ProductoDAO;
public class InventarioService {
    private ProductoDAO productoDAO;

    public InventarioService(ProductoDAO productoDAO) {
        this.productoDAO = productoDAO;
    }

    /*
    Realiza una compra de un producto, actualizando su cantidad disponible en el inventario. Si la cantidad disponible
    es suficiente para la compra, se decrementa la cantidad y se actualiza el producto en la base de datos.
     */
    public void realizarCompra(int idProducto, int cantidad) {
        Producto producto = productoDAO.obtenerProductoPorId(idProducto);
        if (producto != null) {
            int cantidadActual = producto.getCantidadDisponible();
            if (cantidadActual >= cantidad) {
                producto.decrementarCantidad(cantidad);
                productoDAO.actualizarProducto(producto);
                System.out.println("Compra realizada exitosamente.");
            } else {
                System.out.println("No hay suficiente cantidad disponible para realizar la compra.");
            }
        } else {
            System.out.println("Producto no encontrado en el inventario.");
        }
    }

    /*
    Obtiene la cantidad disponible de un producto en el inventario consultando la base de datos a través de ProductoDAO.
     */
    public int obtenerCantidadDisponible(int idProducto) {
        Producto producto = productoDAO.obtenerProductoPorId(idProducto);
        if (producto != null) {
            return producto.getCantidadDisponible();
        } else {
            System.out.println("Producto no encontrado en el inventario.");
            return 0;
        }
    }

    /*
    Aplica un descuento a un producto en el inventario, actualizando el precio del producto en la base de datos.
     */
    public void aplicarDescuento(int idProducto, double porcentajeDescuento) {
        Producto producto = productoDAO.obtenerProductoPorId(idProducto);
        if (producto != null) {
            double precioActual = producto.getPrecio();
            double descuento = precioActual * porcentajeDescuento / 100;
            producto.setPrecio(precioActual - descuento);
            productoDAO.actualizarProducto(producto);
            System.out.println("Descuento aplicado exitosamente.");
        } else {
            System.out.println("Producto no encontrado en el inventario.");
        }
    }

}
package com.example.ecommerce.controller;

import com.example.ecommerce.model.DetalleOrden;
import com.example.ecommerce.model.Orden;
import com.example.ecommerce.model.Producto;
import com.example.ecommerce.service.ProductoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class HomeController {

    private final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private ProductoService productoService;

    // para almacenar los detalles de las ordenes
    List<DetalleOrden> detalleOrdenes = new ArrayList<DetalleOrden>();

    // datos de la orden
    Orden orden = new Orden();

    @GetMapping("")
    public String home(Model model) {
        model.addAttribute("productos", productoService.findAll());
        return "usuario/home";
    }

    @GetMapping("/productohome/{id}")
    public String productoHome(@PathVariable Integer id, Model model) {
        LOGGER.info("Mostrando producto: {}", id);
        Producto producto = new Producto();
        Optional<Producto> productoOptional = productoService.get(id);
        producto = productoOptional.get();
        model.addAttribute("producto", producto);
        return "usuario/productohome";
    }

    @PostMapping("/cart")
    public String addCart(@RequestParam Integer id, @RequestParam Integer cantidad, Model model) {
        DetalleOrden detalleOrden = new DetalleOrden();
        Producto producto = new Producto();
        double sumaTotal = 0;

        Optional<Producto> productoOptional = productoService.get(id);
        LOGGER.info("Agregando producto: {}", productoOptional.get());
        LOGGER.info("Cantidad: {}", cantidad);
        producto = productoOptional.get();

        detalleOrden.setCantidad(cantidad);
        detalleOrden.setPrecio(producto.getPrecio());
        detalleOrden.setNombre(producto.getNombre());
        detalleOrden.setTotal(producto.getPrecio() * cantidad);
        detalleOrden.setProducto(producto);

        detalleOrdenes.add(detalleOrden);

        sumaTotal = detalleOrdenes.stream().mapToDouble(dt -> dt.getTotal()).sum();

        orden.setTotal(sumaTotal);
        model.addAttribute("cart", detalleOrdenes);
        model.addAttribute("orden", orden);

        return "usuario/carrito";
    }

    // quitar producto del carrito
    @GetMapping("/cart/delete/{id}")
    public String deleteProductoCart(@PathVariable Integer id, Model model) {
        // lista nueva de productos
        List<DetalleOrden> ordenesNueva = new ArrayList<DetalleOrden>();

        for (DetalleOrden detalleOrden : detalleOrdenes) {
            if (detalleOrden.getProducto().getId()!=(id)){
                ordenesNueva.add(detalleOrden);
            }
        }

        // poner la nueva lista en el carrito
        detalleOrdenes = ordenesNueva;

        double sumaToral = 0;
        sumaToral = detalleOrdenes.stream().mapToDouble(dt -> dt.getTotal()).sum();

        orden.setTotal(sumaToral);
        model.addAttribute("cart", detalleOrdenes);
        model.addAttribute("orden", orden);

        return "usuario/carrito";
    }

}

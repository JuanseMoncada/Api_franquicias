package com.franquicias.franquicias_api.Controller;


import com.franquicias.franquicias_api.DTO.ProductosRequest;
import com.franquicias.franquicias_api.DTO.ProductosResponse;
import com.franquicias.franquicias_api.Service.FranquiciasService;
import com.franquicias.franquicias_api.Service.ProductosServices;
import com.franquicias.franquicias_api.Service.SucursalServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProductosController {

    private final ProductosServices productosServices;
    private final SucursalServices sucursalServices;
    private final FranquiciasService franquiciasService;


    //Visualizacion sucursales
    @GetMapping("/productos")
    public String productos(Model model) {
        List<ProductosResponse> productos = productosServices.visualizarProductos();

        model.addAttribute("productos", productos);

        model.addAttribute("sucursales", sucursalServices.visualizarSucursales());

        model.addAttribute("franquicias", franquiciasService.visualizarFranquicias());

        return "productos";
    }


    //Creacion de producto
    @PostMapping("/crearProductos")
    public ResponseEntity<ProductosResponse> crearProductos(@RequestBody ProductosRequest productos) {

        try {
            ProductosResponse response = productosServices.crearProductos(productos);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(response);


        }catch (Exception e){
            e.printStackTrace();

            return ResponseEntity
                    .badRequest()
                    .build();
        }

    }

    // Eliminar producto
    @DeleteMapping("/eliminarProducto/{idProducto}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Integer idProducto) {

        productosServices.eliminarProducto(idProducto);
        return ResponseEntity.ok().build();

    }

    // Actualiza producto
    @PutMapping("/actualizarProducto/{idProducto}")
    public ResponseEntity<Void> actualizarProducto(@PathVariable Integer idProducto, @RequestBody ProductosRequest productos) {

        productosServices.actualizarProducto(idProducto, productos);

        return ResponseEntity.ok().build();

    }

    // Metodo para saber cual es el producto mas consumido dentro de las sucursales que estan apuntando directamente a una franbquicia en expecifico
    @GetMapping("/franquicias/{idFranquicia}/productos-mayor-stock")
    public ResponseEntity<List<ProductosResponse>> productosMayorStock(
            @PathVariable Integer idFranquicia) {

        List<ProductosResponse> productos =
                productosServices.productosMayorStock(idFranquicia);

        return ResponseEntity.ok(productos);
    }

}

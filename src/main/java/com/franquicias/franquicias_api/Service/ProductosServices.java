package com.franquicias.franquicias_api.Service;

import com.franquicias.franquicias_api.DTO.ProductosRequest;
import com.franquicias.franquicias_api.DTO.ProductosResponse;
import com.franquicias.franquicias_api.DTO.SucursalResponse;
import com.franquicias.franquicias_api.Entity.Productos;
import com.franquicias.franquicias_api.Entity.Sucursal;
import com.franquicias.franquicias_api.Repository.ProductosRepository;
import com.franquicias.franquicias_api.Repository.SucursalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductosServices {

    private final ProductosRepository productosRepository;
    private final SucursalRepository sucursalRepository;


    //visuaslizacion productos
    public List<ProductosResponse> visualizarProductos(){

        List<Productos> productos = productosRepository.findAll(
                Sort.by(Sort.Direction.ASC, "productoNombre")
        );

        List<ProductosResponse> respuestas = new ArrayList<>();

        for  (Productos producto : productos) {
            ProductosResponse response = new ProductosResponse();

            response.setIdProducto(producto.getIdProducto());
            response.setNombreProducto(producto.getProductoNombre());
            response.setCantidadProductoStock(producto.getProductosCantidadStock());
            response.setIdSucursal(producto.getSucursal().getSucursalId());
            response.setNombreSucursal(producto.getSucursal().getNombreSucursal());

            respuestas.add(response);
        }

        return respuestas;

    }

    //creacion  de productos los cuales estan ligados a una sucursal
    public ProductosResponse crearProductos(ProductosRequest request){

        Sucursal sucursal = sucursalRepository
                .findById(request.getIdSucursal())
                .orElseThrow(()-> new RuntimeException("Sucursal no encontrado"));

        Productos producto = new Productos();
        producto.setProductoNombre(request.getNombreProducto());
        producto.setProductosCantidadStock(request.getCantidadProductoStock());
        producto.setSucursal(sucursal);

        Productos productoGuardados = productosRepository.save(producto);
        ProductosResponse response = new ProductosResponse();

        response.setIdProducto(producto.getIdProducto());
        response.setNombreProducto(producto.getProductoNombre());
        response.setCantidadProductoStock(producto.getProductosCantidadStock());
        response.setIdSucursal(productoGuardados.getSucursal().getSucursalId());
        response.setNombreSucursal(productoGuardados.getSucursal().getNombreSucursal());

        return response;
    }


    // Eliminar sucursal
    public void eliminarProducto(Integer idProducto) {

        if (!productosRepository.existsById(idProducto)) {
            throw new RuntimeException("No existe el producto con el id: " + idProducto);
        }

        productosRepository.deleteById(idProducto);

    }

    // Actualizar producto
    public void actualizarProducto(Integer idProducto, ProductosRequest request){

        Productos producto = productosRepository
                .findById(idProducto)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        producto.setProductoNombre(request.getNombreProducto());
        producto.setProductosCantidadStock(request.getCantidadProductoStock());
        productosRepository.save(producto);

    }


    // Metodo para saber cual es el producto mas consumido dentro de las sucursales que estan apuntando directamente a una franbquicia en expecifico
    public List<ProductosResponse> productosMayorStock(Integer idFranquicia){

        List<ProductosResponse> respuestas = new ArrayList<>();

        List<Sucursal> sucursales = sucursalRepository.findByFranquicia_FranquiciaId(idFranquicia);

        for (Sucursal sucursal : sucursales) {

            //Se Busca el Producto de la sucursal requerida
            List<Productos> productos = productosRepository.findBySucursal_SucursalId(sucursal.getSucursalId());
            Productos productosMayorStock = null;

            for(Productos producto : productos){

                if (productosMayorStock == null || producto.getProductosCantidadStock()>productosMayorStock.getProductosCantidadStock()) {
                    productosMayorStock = producto;
                }
            }

            if (productosMayorStock != null) {

                ProductosResponse response = new ProductosResponse();

                response.setIdProducto(productosMayorStock.getIdProducto());
                response.setNombreProducto(productosMayorStock.getProductoNombre());
                response.setCantidadProductoStock(productosMayorStock.getProductosCantidadStock());
                response.setIdSucursal(sucursal.getSucursalId());
                response.setNombreSucursal(sucursal.getNombreSucursal());

                respuestas.add(response);

            }

        }

        return respuestas;

    }


}

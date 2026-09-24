package com.franquicias.franquicias_api.DTO;


import lombok.Data;

@Data
public class ProductosResponse {

    private Integer idProducto;
    private String nombreProducto;
    private Integer cantidadProductoStock;
    private Integer idSucursal;
    private String nombreSucursal;

}

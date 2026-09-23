package com.franquicias.franquicias_api.DTO;

import lombok.Data;

@Data
public class SucursalResponse {

    private Integer idSucursal;
    private String nombreSucursal;
    private Integer idFranquicia;
    private String nombreFranquicia;

}

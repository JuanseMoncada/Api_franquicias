package com.franquicias.franquicias_api.DTO;

import com.franquicias.franquicias_api.Entity.Franquicia;
import lombok.Data;

@Data
public class FranquiciaResponse {

    private Integer idFranquicia;
    private String nombreFranquicia;

}

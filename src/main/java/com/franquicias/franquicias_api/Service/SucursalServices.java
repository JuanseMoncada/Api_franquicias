package com.franquicias.franquicias_api.Service;

import com.franquicias.franquicias_api.DTO.FranquiciaRequest;
import com.franquicias.franquicias_api.DTO.SucursalRequest;
import com.franquicias.franquicias_api.DTO.SucursalResponse;
import com.franquicias.franquicias_api.Entity.Franquicia;
import com.franquicias.franquicias_api.Entity.Sucursal;
import com.franquicias.franquicias_api.Repository.FranquiciaRepository;
import com.franquicias.franquicias_api.Repository.SucursalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SucursalServices {

    private final SucursalRepository sucursalRepository;
    private final FranquiciaRepository franquiciaRepository;

    // Visualizacion sucursales
    public List<SucursalResponse> visualizarSucursales(){

        List<Sucursal> sucursales = sucursalRepository.findAll(
                Sort.by(Sort.Direction.ASC, "sucursalId")
        );
        List<SucursalResponse> respuestas = new ArrayList<>();

        for (Sucursal sucursal : sucursales){
            SucursalResponse response = new SucursalResponse();


            response.setIdSucursal(sucursal.getSucursalId());
            response.setNombreSucursal(sucursal.getNombreSucursal());
            response.setIdFranquicia(sucursal.getFranquicia().getFranquiciaId());
            response.setNombreFranquicia(sucursal.getFranquicia().getNombreFranquicia());

            respuestas.add(response);

        }

        return respuestas;
    }


    // Creacion  de sucursales las cuales estan ligadas a una franquicia
    public SucursalResponse crearSucursal(SucursalRequest request){

        Franquicia franquicia = franquiciaRepository
                .findById(request.getIdFranquicia())
                .orElseThrow(()-> new RuntimeException("franquicia no encontrada"));

        Sucursal sucursal = new Sucursal();
        sucursal.setNombreSucursal(request.getNombreSucursal());
        sucursal.setFranquicia(franquicia);

        Sucursal sucursalGuardada = sucursalRepository.save(sucursal);

        SucursalResponse response = new SucursalResponse();
        response.setIdSucursal(sucursal.getSucursalId());
        response.setNombreSucursal(sucursal.getNombreSucursal());
        response.setIdFranquicia(sucursalGuardada.getFranquicia().getFranquiciaId());
        response.setNombreFranquicia(sucursalGuardada.getFranquicia().getNombreFranquicia());

        return response;
    }


    // Eliminar sucursal
    public void eliminarSucursal(Integer idSucursal){

        if (!sucursalRepository.existsById(idSucursal)){
            throw new RuntimeException("sucursal no encontrada");
        }

        sucursalRepository.deleteById(idSucursal);

    }

    // Actualizar sucursal
    public void actualizarSucursal(Integer idSucursal, SucursalRequest request){

        if (!sucursalRepository.existsById(idSucursal)){
            throw new RuntimeException("Sucursal no encontrada");
        }

        Sucursal sucursal = sucursalRepository.findById(idSucursal).get();

        sucursal.setNombreSucursal(request.getNombreSucursal());

        sucursalRepository.save(sucursal);
    }

}

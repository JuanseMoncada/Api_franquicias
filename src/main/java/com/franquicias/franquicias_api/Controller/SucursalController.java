package com.franquicias.franquicias_api.Controller;


import com.franquicias.franquicias_api.DTO.SucursalRequest;
import com.franquicias.franquicias_api.DTO.SucursalResponse;
import com.franquicias.franquicias_api.Entity.Sucursal;
import com.franquicias.franquicias_api.Service.FranquiciasService;
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
public class SucursalController {

    private final SucursalServices sucursalServices;
    private final FranquiciasService  franquiciasService;


    // Visualizacion sucursales
    @GetMapping("/sucursales")
    public String mostrarSucursales(Model model){

        List<SucursalResponse> sucursales = sucursalServices.visualizarSucursales();

        model.addAttribute("sucursales", sucursales);

        model.addAttribute("franquicias", franquiciasService.visualizarFranquicias());

        return "sucursales";
    }


    // Creacion de sucusales
    @PostMapping("/crearSucursales")
    public ResponseEntity<SucursalResponse> crearSucursal(@RequestBody SucursalRequest sucursalRequest) {

        try {
            SucursalResponse response = sucursalServices.crearSucursal(sucursalRequest);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(response);
        } catch (RuntimeException e) {

            return ResponseEntity
                    .badRequest()
                    .build();

        }
    }

    @DeleteMapping("/eliminarSucursal/{idSucursal}")
    public ResponseEntity<Void> eliminarSucursal(@PathVariable Integer idSucursal) {

        sucursalServices.eliminarSucursal(idSucursal);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/actualizarSucursal/{idSucursal}")
    public ResponseEntity<Void> actualizarSucursal(@PathVariable Integer idSucursal,  @RequestBody SucursalRequest sucursalRequest) {

        sucursalServices.actualizarSucursal(idSucursal,sucursalRequest);

        return ResponseEntity.ok().build();
    }

}

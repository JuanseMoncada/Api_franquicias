package com.franquicias.franquicias_api.Controller;

import com.franquicias.franquicias_api.DTO.FranquiciaRequest;
import com.franquicias.franquicias_api.DTO.FranquiciaResponse;
import com.franquicias.franquicias_api.Entity.Franquicia;
import com.franquicias.franquicias_api.Service.FranquiciasService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class FranquiciasController {

    private final FranquiciasService franquiciasService;

    //visualizacion
    @GetMapping("/franquicias")
    public String mostrarFranquicias(Model model) {

        List<FranquiciaResponse> franquicias =
                franquiciasService.visualizarFranquicias();

        model.addAttribute("franquicias", franquicias);

        return "franquicias";
    }

    //creacion
    @PostMapping("/creacionFranquicia")
    public ResponseEntity<Franquicia> crearFranquicia(@RequestBody FranquiciaRequest request) {
        try {

            Franquicia response = franquiciasService.crearFranquicia(request);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(response);

        } catch (RuntimeException e) {

            return ResponseEntity
                    .badRequest()
                    .build();

        }
    }

    //Eliminacion
    @DeleteMapping("/franquicias/{id}")
    public ResponseEntity<Void> eliminarFranquicia(
            @PathVariable Integer id) {

        franquiciasService.eliminarFranquicia(id);

        return ResponseEntity.ok().build();
    }

    //Actualizacion

    @PutMapping("/franquicias/{id}")
    public ResponseEntity<Void> actualizarFranquicia(
            @PathVariable Integer id,
            @RequestBody FranquiciaRequest request) {

        franquiciasService.actualizarFranquicia(id, request);

        return ResponseEntity.ok().build();
    }

}

package com.franquicias.franquicias_api.Service;

import com.franquicias.franquicias_api.DTO.FranquiciaRequest;
import com.franquicias.franquicias_api.DTO.FranquiciaResponse;
import com.franquicias.franquicias_api.Entity.Franquicia;
import com.franquicias.franquicias_api.Repository.FranquiciaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FranquiciasService {

    private final FranquiciaRepository franquiciaRepository ;

    //Visualizacion
    public List<FranquiciaResponse> visualizarFranquicias(){
        List<Franquicia> franquicias = franquiciaRepository.findAll(
                Sort.by(Sort.Direction.ASC, "franquiciaId")
        );
        List<FranquiciaResponse> respuestas = new ArrayList<>();

        for (Franquicia franquicia : franquicias) {
            FranquiciaResponse response = new FranquiciaResponse();

            response.setIdFranquicia(franquicia.getFranquiciaId());

            response.setNombreFranquicia(franquicia.getNombreFranquicia());

            respuestas.add(response);
        }

        return respuestas;
    }

    //Creacion
    public Franquicia crearFranquicia(FranquiciaRequest request) {

        if (franquiciaRepository.existsByNombreFranquicia(request.getNombreFranquicia())){
            throw new RuntimeException("Franquicia Ya Existe");
        }

        Franquicia franquicia = new Franquicia();
        franquicia.setNombreFranquicia(request.getNombreFranquicia());

        Franquicia franquiciaGuardada =
                franquiciaRepository.save(franquicia);

        FranquiciaResponse response = new FranquiciaResponse();
        response.setIdFranquicia(franquiciaGuardada.getFranquiciaId());
        response.setNombreFranquicia(franquiciaGuardada.getNombreFranquicia());

        return franquiciaRepository.save(franquicia);
    }

    //Eliminacion
    public void eliminarFranquicia(Integer idFranquicia) {

        if (!franquiciaRepository.existsById(idFranquicia)){
            throw new RuntimeException("Franquicia No Existe");
        }

        franquiciaRepository.deleteById(idFranquicia);
    }

    //Actualizacion
    public void actualizarFranquicia(Integer idFranquicia, FranquiciaRequest request) {

        if (!franquiciaRepository.existsById(idFranquicia)){
            throw new RuntimeException("Franquicia No Existe Para actualizar");
        }

        Franquicia franquicia = franquiciaRepository.findById(idFranquicia).get();

        franquicia.setNombreFranquicia(request.getNombreFranquicia());

        franquiciaRepository.save(franquicia);

    }


}

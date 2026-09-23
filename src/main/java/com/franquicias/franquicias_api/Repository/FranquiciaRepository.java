package com.franquicias.franquicias_api.Repository;

import com.franquicias.franquicias_api.Entity.Franquicia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FranquiciaRepository
        extends JpaRepository<Franquicia, Integer> {



    boolean existsByNombreFranquicia(String nombreFranquicia);

}

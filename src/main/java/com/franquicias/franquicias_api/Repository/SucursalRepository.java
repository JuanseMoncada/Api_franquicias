package com.franquicias.franquicias_api.Repository;

import com.franquicias.franquicias_api.Entity.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SucursalRepository extends JpaRepository<Sucursal, Integer> {

    boolean existsByNombreSucursal(String nombreSucursal);

    boolean existsByFranquicia_FranquiciaId(Integer franquiciaId);

    List<Sucursal> findByFranquicia_FranquiciaId(Integer idFranquicia);
}

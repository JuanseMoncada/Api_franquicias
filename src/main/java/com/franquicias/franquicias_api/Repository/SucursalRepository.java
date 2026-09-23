package com.franquicias.franquicias_api.Repository;

import com.franquicias.franquicias_api.Entity.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SucursalRepository extends JpaRepository<Sucursal, Integer> {

    boolean existsByNombreSucursal(String nombreSucursal);

    boolean existsByFranquicia_FranquiciaId(Integer franquiciaId);
}

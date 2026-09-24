package com.franquicias.franquicias_api.Repository;

import com.franquicias.franquicias_api.Entity.Productos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductosRepository extends JpaRepository<Productos, Integer> {

    boolean existsByProductoNombre(String nombre);

    boolean existsBySucursal_SucursalId(Integer sucursalId);

    List<Productos> findBySucursal_SucursalId(Integer idSucursal);

}

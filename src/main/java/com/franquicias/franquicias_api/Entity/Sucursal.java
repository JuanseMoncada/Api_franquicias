package com.franquicias.franquicias_api.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Sucursales")
@Data
public class Sucursal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sucursal_id")
    private Integer sucursalId;

    @Column(name = "sucursal_nombre", nullable = false)
    private String nombreSucursal;

    @ManyToOne
    @JoinColumn(name = "franquicia_id", nullable = false)
    private Franquicia franquicia;
}

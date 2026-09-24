package com.franquicias.franquicias_api.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Productos")
@Data
public class Productos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "producto_id")
    private Integer idProducto;

    @Column(name = "producto_nombre", nullable = false)
    private String productoNombre;

    @Column(name = "producto_cantidad_stock", nullable = false)
    private Integer productosCantidadStock;

    @ManyToOne
    @JoinColumn(name = "sucursal_id", nullable = false)
    private Sucursal sucursal;

}

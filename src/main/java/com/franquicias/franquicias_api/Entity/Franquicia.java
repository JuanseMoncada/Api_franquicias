package com.franquicias.franquicias_api.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Franquicias")
@Data
public class Franquicia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer franquiciaId;

    @Column(nullable = false)
    private String nombreFranquicia;

}

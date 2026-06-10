package com.scraper.service.scraper_service.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "ofertas_laborales_raw")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobOffer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El título de la oferta no puede estar vacío")
    @Column(nullable = false, length = 500)
    private String titulo;

    @Column(length = 255)
    private String empresa;

    @Column(name = "url_original", length = 2048)
    private String urlOriginal;

    @Column(name = "fecha_recopilacion", nullable = false)
    private LocalDateTime fechaRecopilacion;

    @Column(name = "procesado_por_ia", nullable = false)
    private boolean procesadoPorIa;

    /**
     * Ciclo de vida de JPA: Antes de que el registro se inserte por primera vez
     * en MySQL, le asignamos la fecha y hora actual del sistema y lo marcamos como falso.
     */
    @PrePersist
    protected void onCreate() {
        this.fechaRecopilacion = LocalDateTime.now();
        this.procesadoPorIa = false;
    }
}

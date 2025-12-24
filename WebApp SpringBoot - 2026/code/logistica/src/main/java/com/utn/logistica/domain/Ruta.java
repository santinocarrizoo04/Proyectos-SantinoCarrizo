package com.utn.logistica.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class Ruta {
    private Long idRuta;
    private Long idTarjetaColaborador;
    private Integer idHeladeraOrigen;
    private Integer idHeladeraDestino;
    private LocalDateTime fechaCreacion;
    private Boolean rutaActiva;
    private List<Traslado> traslados;

    public Ruta(Long idTarjetaColaborador, Integer idHeladeraOrigen, Integer idHeladeraDestino) {
        this.idTarjetaColaborador = idTarjetaColaborador;
        this.idHeladeraOrigen = idHeladeraOrigen;
        this.idHeladeraDestino = idHeladeraDestino;
        this.fechaCreacion = LocalDateTime.now();
        this.rutaActiva = true;
    }

    public Ruta() {
    }

}

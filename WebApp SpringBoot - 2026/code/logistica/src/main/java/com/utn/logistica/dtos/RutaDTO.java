package com.utn.logistica.dtos;

import com.utn.logistica.domain.Traslado;

import java.time.LocalDateTime;
import java.util.List;

public class RutaDTO {
    private Long idRuta;
    private Long idTarjetaColaborador;
    private Integer idHeladeraOrigen;
    private Integer idHeladeraDestino;
    private LocalDateTime fechaCreacion;
    private Boolean rutaActiva;
    private List<Traslado> traslados;
}

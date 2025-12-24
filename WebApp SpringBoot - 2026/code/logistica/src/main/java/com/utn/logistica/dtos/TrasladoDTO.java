package com.utn.logistica.dtos;

import com.utn.logistica.domain.Ruta;
import com.utn.logistica.domain.enums.EstadoTraslado;

import java.time.LocalDateTime;

public class TrasladoDTO {
    private Long idTraslado;
    private String qrVianda;
    private Ruta ruta;
    private EstadoTraslado estado;
    private LocalDateTime fechaCreacionTraslado;
    private LocalDateTime fechaTraslado;
    private Long idTarjetaColaborador;
}

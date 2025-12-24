package com.utn.viandas.dtos;

import com.utn.viandas.domain.enums.EstadoVianda;

import java.time.LocalDateTime;

public class ViandaDTO {
    private Long idVianda;
    private String codigoQR;
    private LocalDateTime fechaElaboracion;
    private EstadoVianda estadoVianda;
    private Long idTarjetaColaborador;
    private Long heladeraID;
}

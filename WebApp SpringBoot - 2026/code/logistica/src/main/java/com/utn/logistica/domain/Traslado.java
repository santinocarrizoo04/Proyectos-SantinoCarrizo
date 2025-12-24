package com.utn.logistica.domain;

import com.utn.logistica.domain.enums.EstadoTraslado;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Traslado {
    private Long idTraslado;
    private String qrVianda;
    private Ruta ruta;
    private EstadoTraslado estado;
    private LocalDateTime fechaCreacionTraslado;
    private LocalDateTime fechaTraslado;
    private Long idTarjetaColaborador;

    public Traslado(String qrVianda, Ruta ruta, EstadoTraslado estado, LocalDateTime fechaTraslado) {
        this.qrVianda = qrVianda;
        this.ruta = ruta;
        this.estado = estado;
        this.fechaCreacionTraslado = LocalDateTime.now();
        this.fechaTraslado = fechaTraslado;
        this.idTarjetaColaborador = 1L;
    }

    public Traslado() {
    }
}

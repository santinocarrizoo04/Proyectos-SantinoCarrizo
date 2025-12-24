package com.utn.viandas.domain;

import com.utn.viandas.domain.enums.EstadoVianda;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Vianda {

    private Long idVianda;
    private String codigoQR;
    private LocalDateTime fechaElaboracion;
    private EstadoVianda estadoVianda;
    private Long idTarjetaColaborador;
    private Long heladeraID;

    public Vianda(String qr, LocalDateTime fechaElaboracion, EstadoVianda estadoVianda,
                  Long idTarjetaColaborador, Long heladeraID) {
        this.codigoQR = qr;
        this.fechaElaboracion = fechaElaboracion;
        this.estadoVianda = estadoVianda;
        this.idTarjetaColaborador = idTarjetaColaborador;
        this.heladeraID = heladeraID;
    }

}

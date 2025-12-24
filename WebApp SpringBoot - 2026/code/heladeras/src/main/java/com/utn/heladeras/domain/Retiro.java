package com.utn.heladeras.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Retiro {

    private Long id;
    private String qrVianda;
    private String tarjeta;
    private LocalDateTime fechaRetiro;
    private Long heladeraID;

    public Retiro(){}
}

package com.utn.heladeras.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Temperatura {

    private Integer idTemperatura;
    private  Integer heladeraId;
    private LocalDateTime fechaMedicion;
    private  Integer temperatura;

    public Temperatura() {}
}

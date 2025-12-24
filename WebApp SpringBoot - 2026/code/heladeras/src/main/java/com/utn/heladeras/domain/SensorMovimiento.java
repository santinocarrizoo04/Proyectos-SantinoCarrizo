package com.utn.heladeras.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SensorMovimiento {

    private Integer idSensor;
    public Boolean estado=Boolean.FALSE;

    public SensorMovimiento(){}
}

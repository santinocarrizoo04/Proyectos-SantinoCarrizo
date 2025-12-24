package com.utn.heladeras.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SensorTemperatura {

    private Integer idSensorTemperatura;
    private Boolean estado= Boolean.FALSE;
    private Integer ntiempo;
    private Integer tempMax;
    private Integer tempMin;

    public SensorTemperatura(){
    }

}
